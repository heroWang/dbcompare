package com.sohu.assist.dbcompare.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.ddlutils.Platform;
import org.apache.ddlutils.PlatformFactory;
import org.apache.ddlutils.model.Column;
import org.apache.ddlutils.model.Database;
import org.apache.ddlutils.model.Table;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.sohu.assist.dbcompare.model.DatabaseSetting;
import com.sohu.assist.dbcompare.model.Result2JsonNode;
import com.sohu.assist.dbcompare.model.TablesCompareSetting;
import com.sohu.assist.dbcompare.model.json.JsonNode;
import com.sohu.assist.dbcompare.model.result.BaseResult;
import com.sohu.assist.dbcompare.model.result.ColumnResult;
import com.sohu.assist.dbcompare.model.result.ColumnsResult;
import com.sohu.assist.dbcompare.model.result.DiffContent;
import com.sohu.assist.dbcompare.model.result.RESULT_TYPE;
import com.sohu.assist.dbcompare.model.result.Result;
import com.sohu.assist.dbcompare.model.result.TableResult;
import com.sohu.assist.dbcompare.model.result.TablesResult;
import com.sohu.assist.dbcompare.service.TablesCompareService;

/**
 * @author hawkinswang <br>数据库schema比较服务的实现
 */
@Service("compareService")
public class TablesCompareServiceImpl implements TablesCompareService {
    public final static Logger LOG = Logger.getLogger(TablesCompareServiceImpl.class);

    @Override
    public Result compare(TablesCompareSetting setting) {
        TablesResult result = new TablesResult();
        DataSource source = getDataSource(setting.getSourceDatabaseSetting());
        DataSource target = getDataSource(setting.getTargetDatabaseSetting());

        Database sDB = readDatabase(source);
        Database tDB = readDatabase(target);
        //
        List<Table> sourceTableList = Arrays.asList(sDB.getTables());
        List<Table> targetTableList = Arrays.asList(tDB.getTables());
        List<String> includes = setting.getIncludeTableNames();
        boolean needIncludes = true;
        if (includes == null || includes.size() == 0) {
            needIncludes = false;
        }

        List<TableResult> tableResultList = new ArrayList<TableResult>();
        List<TableResult> missTableResultList = new ArrayList<TableResult>();

        int totalCount = 0;
        int missCount = 0;
        int diffCount = 0;
        int addCount = 0;
        for (Table sourceTable : sourceTableList) {
            if (needIncludes) {
                boolean isIncluded = false;
                for (String tableName : includes) {
                    if (tableName.equals(sourceTable.getName())) {
                        isIncluded = true;
                    }
                }

                if (!isIncluded) {
                    continue;
                }
            }

            // get add or diff or equal tableresult;
            TableResult tableResult = this.compareTable(sourceTable, targetTableList);

            LOG.debug("single table result:" + tableResult);

            tableResultList.add(tableResult);
        }
        // 统计addCount diffCount
        for (TableResult tableResult : tableResultList) {
            if (tableResult.getResultType() == RESULT_TYPE.ADD) {
                addCount++;
            } else if (tableResult.getResultType() == RESULT_TYPE.MODIFY) {
                diffCount++;
            }
        }

        // get miss tableresult;
        missTableResultList = this.getMissTableResultList(sourceTableList, targetTableList);

        missCount = missTableResultList.size();
        tableResultList.addAll(missTableResultList);

        totalCount = tableResultList.size();

        if (missCount > 0 || addCount > 0 || diffCount > 0) {
            result.setResultType(RESULT_TYPE.MODIFY);
        } else {
            result.setResultType(RESULT_TYPE.EQUAL);
        }
        result.setTotalCount(totalCount);
        result.setMissCount(missCount);
        result.setAddCount(addCount);
        result.setDiffCount(diffCount);
        result.setTableResultList(tableResultList);

        LOG.debug("finally tables result:" + result);

        return result;
    }

    /**
     * @param sourceTable sourceDB中要比较的table
     * @param targetTableList targetDBA中所有的table
     * @return 比较结果
     */
    private TableResult compareTable(Table sourceTable, List<Table> targetTableList) {
        TableResult tableResult = new TableResult();

        Table correspondingTargetTable = null;
        for (Table targetTable : targetTableList) {
            if (sourceTable.getName().equals(targetTable.getName())) {
                correspondingTargetTable = targetTable;
                break;
            }
        }

        ColumnsResult columnsResult = new ColumnsResult();
        List<ColumnResult> columnResultList = new LinkedList<ColumnResult>();
        List<ColumnResult> missedColumnResultList = new LinkedList<ColumnResult>();
        int diffCount = 0;
        int missCount = 0;
        int addCount = 0;
        int totalCount = 0;

        tableResult.setName(sourceTable.getName());
        if (correspondingTargetTable == null) {
            addCount = sourceTable.getColumnCount();
            for (Column column : sourceTable.getColumns()) {
                ColumnResult columnResult = this.compareColumn(column, null);
                columnResultList.add(columnResult);
            }
            columnsResult.setResultType(RESULT_TYPE.ADD);
            tableResult.setResultType(RESULT_TYPE.ADD);
        } else {
            Column[] sColumns = sourceTable.getColumns();
            Column[] tColumns = correspondingTargetTable.getColumns();
            for (Column sColumn : sColumns) {
                ColumnResult columnResult = this.compareColumn(sColumn, tColumns);
                if (columnResult.getResultType() == RESULT_TYPE.ADD) {
                    addCount++;
                } else if (columnResult.getResultType() == RESULT_TYPE.MODIFY) {
                    diffCount++;
                }

                columnResultList.add(columnResult);
            }

            missedColumnResultList = this.getMissColumnResultList(sColumns, tColumns);
            columnResultList.addAll(missedColumnResultList);
            missCount = missedColumnResultList.size();

            if (addCount > 0 || diffCount > 0 || missCount > 0) {
                columnsResult.setResultType(RESULT_TYPE.MODIFY);
            } else {
                columnsResult.setResultType(RESULT_TYPE.EQUAL);
            }
            tableResult.setResultType(columnsResult.getResultType());
        }
        totalCount = sourceTable.getColumnCount() + missCount;

        columnsResult.setColumnResultList(columnResultList);
        columnsResult.setTotalCount(totalCount);
        columnsResult.setDiffCount(diffCount);
        columnsResult.setMissCount(missCount);
        columnsResult.setAddCount(addCount);

        tableResult.setColumnsResult(columnsResult);

        return tableResult;
    }

    /**
     * @param sourceTableList
     * @param targetTableList
     * @return targetTableList 较之 sourceTableList 多出来的Table 并生成TableResult
     */
    private List<TableResult> getMissTableResultList(List<Table> sourceTableList, List<Table> targetTableList) {
        List<TableResult> tableResultList = new LinkedList<TableResult>();
        List<Table> missTableList = new LinkedList<Table>();
        for (Table table2Find : targetTableList) {
            boolean missed = true;
            for (Table tempTable : sourceTableList) {
                if (tempTable.getName().equals(table2Find.getName())) {
                    missed = false;
                }
            }
            if (missed) {
                missTableList.add(table2Find);
            }
        }

        for (Table missTable : missTableList) {
            TableResult tableResult = new TableResult();
            tableResult.setResultType(RESULT_TYPE.MISS);
            tableResult.setName(missTable.getName());

            Column[] columns = missTable.getColumns();
            ColumnsResult columnsResult = new ColumnsResult();
            List<ColumnResult> columnResultList = this.getMissColumnResultList(null, columns);

            columnsResult.setResultType(RESULT_TYPE.MISS);
            columnsResult.setTotalCount(columns.length);
            columnsResult.setMissCount(columns.length);
            columnsResult.setColumnResultList(columnResultList);

            tableResult.setColumnsResult(columnsResult);

            tableResultList.add(tableResult);
        }
        return tableResultList;
    }

    /**
     * @param sColumn sourceTable中要比较的column
     * @param tColumns targetTable中所有的column
     * @return 比较结果
     */
    private ColumnResult compareColumn(Column sColumn, Column[] tColumns) {
        ColumnResult columnResult = new ColumnResult();
        Column correspondingTargetColumn = null;
        if (tColumns == null) {
            tColumns = new Column[] {};
        }

        for (Column column : tColumns) {
            if (column.getName().equals(sColumn.getName())) {
                correspondingTargetColumn = column;
                break;
            }
        }

        DiffContent content = new DiffContent();
        RESULT_TYPE resultType = null;
        if (correspondingTargetColumn == null) {
            resultType = RESULT_TYPE.ADD;
            content.setSourceCode(genericColumnDescrip(sColumn));
        } else {
            if (isEqual(sColumn, correspondingTargetColumn)) {
                resultType = RESULT_TYPE.EQUAL;
                content.setSourceCode(genericColumnDescrip(sColumn));
            } else {
                resultType = RESULT_TYPE.MODIFY;
                content.setSourceCode(genericColumnDescrip(sColumn));
                content.setTargetCode(genericColumnDescrip(correspondingTargetColumn));
            }
        }
        columnResult.setResultType(resultType);
        columnResult.setName(sColumn.getName());
        columnResult.setContent(content);

        return columnResult;
    }

    /**
     * @param sColumns soureTable所有的column
     * @param tColumns targetTable所有的column
     * @return tColumns较之sColumns多出的column 并生成ColumnResult
     */
    private List<ColumnResult> getMissColumnResultList(Column[] sColumns, Column[] tColumns) {
        List<ColumnResult> columnResultList = new ArrayList<ColumnResult>();

        List<Column> missedColumnList = new LinkedList<Column>();
        for (Column column2Find : tColumns) {
            boolean missed = true;
            if (sColumns != null) {
                for (Column tempColumn : sColumns) {
                    if (tempColumn.getName().equals(column2Find.getName())) {
                        missed = false;
                    }
                }
            }
            if (missed) {
                missedColumnList.add(column2Find);
            }
        }

        for (Column column : missedColumnList) {
            ColumnResult columnResult = new ColumnResult();

            columnResult.setName(column.getName());
            DiffContent content = new DiffContent();
            content.setTargetCode(genericColumnDescrip(column));
            content.setType(RESULT_TYPE.MISS);
            columnResult.setResultType(RESULT_TYPE.MISS);
            columnResult.setContent(content);

            columnResultList.add(columnResult);
        }
        return columnResultList;
    }

    /**
     * @param sColumn
     * @param correspondingTargetColumn
     * @return 两column相等true，否则false <br>判断两个列是否相等的因素包括：类型,长度,主键否,非空否,自增否,默认值,注释
     */
    private boolean isEqual(Column sColumn, Column correspondingTargetColumn) {
        EqualsBuilder comparator = new EqualsBuilder();

        comparator.append(sColumn.isPrimaryKey(), correspondingTargetColumn.isPrimaryKey());
        comparator.append(sColumn.isRequired(), correspondingTargetColumn.isRequired());
        comparator.append(sColumn.isAutoIncrement(), correspondingTargetColumn.isAutoIncrement());
        comparator.append(sColumn.getType(), correspondingTargetColumn.getType());
        comparator.append(sColumn.getParsedDefaultValue(), correspondingTargetColumn.getParsedDefaultValue());
        comparator.append(sColumn.getSizeAsInt(), correspondingTargetColumn.getSizeAsInt());
        comparator.append(sColumn.getDescription(), correspondingTargetColumn.getDescription());

        return comparator.isEquals();
    }

    /**
     * @param column
     * @return 对一个列的描述，包括：类型,长度,主键否,非空否,自增否,默认值,注释
     */
    private String genericColumnDescrip(Column column) {
        String type = column.getType();
        int length = column.getSizeAsInt();
        String defaultVal = column.getDefaultValue();
        String comment = column.getDescription();
        boolean isPrimaryKey = column.isPrimaryKey();
        boolean isAutoIncrement = column.isAutoIncrement();
        boolean isRequired = column.isRequired();

        StringBuffer descrip = new StringBuffer();
        descrip.append(type);
        descrip.append("(");
        descrip.append(length);
        descrip.append(") ");
        descrip.append(isPrimaryKey ? "PRIMARY_KEY " : "");
        descrip.append(isRequired ? "NOT_NULL " : "");
        descrip.append(isAutoIncrement ? "AUTO_INCREMENT " : "");
        descrip.append("Default " + defaultVal + " ");
        descrip.append("#" + comment);

        return descrip.toString();
    }

    /**
     * @param databaseSetting
     * @return 根据一个DatabaseSetting得到一个DataSource
     */
    private DataSource getDataSource(DatabaseSetting databaseSetting) {
        BasicDataSource dbcp = new BasicDataSource();

        StringBuffer url = new StringBuffer();
        url.append("jdbc:mysql://");
        url.append(databaseSetting.getAddress());
        url.append(":");
        url.append(databaseSetting.getPort());
        url.append("/");
        url.append(databaseSetting.getDbname());

        dbcp.setDriverClassName("com.mysql.jdbc.Driver");
        dbcp.setUrl(url.toString());
        dbcp.setUsername(databaseSetting.getUser());
        dbcp.setPassword(databaseSetting.getPassword());

        return dbcp;
    }

    private Database readDatabase(DataSource dataSource)
    {
        Platform platform = PlatformFactory.createNewPlatformInstance(dataSource);

        return platform.readModelFromDatabase(dataSource.toString());
    }

    public static void main(String[] args) {
        TablesCompareSetting setting = (TablesCompareSetting) new
                SettingServiceImpl().getSetting();
        BaseResult result = (BaseResult) new
                TablesCompareServiceImpl().compare(setting);
        JsonNode node = Result2JsonNode.result2JsonNode(result,"");
        System.out.println(node);
    }

}

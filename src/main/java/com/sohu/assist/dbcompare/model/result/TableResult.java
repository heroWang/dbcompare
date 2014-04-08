package com.sohu.assist.dbcompare.model.result;

import java.util.ArrayList;
import java.util.List;

public class TableResult extends BaseLimbResult {
    private ColumnsResult columnsResult;

    @Override
    public List<Result> getChildren() {
        List<Result> resultList = new ArrayList<Result>();

        resultList.add(columnsResult);

        return resultList;
    }

    public ColumnsResult getColumnsResult() {
        return columnsResult;
    }

    public void setColumnsResult(ColumnsResult columnsResult) {
        this.columnsResult = columnsResult;
    }

    @Override
    public String toString() {
        return "TableResult [columnsResult=" + columnsResult + ", name=" + name + ", totalCount=" + totalCount
                + ", diffCount=" + diffCount + ", addCount=" + addCount + ", missCount=" + missCount + ", resultType="
                + resultType + "]";
    }

}

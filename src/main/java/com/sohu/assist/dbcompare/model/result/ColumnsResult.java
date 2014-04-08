package com.sohu.assist.dbcompare.model.result;

import java.util.ArrayList;
import java.util.List;

public class ColumnsResult extends BaseLimbResult {
    List<ColumnResult> columnResultList;

    @Override
    public List<Result> getChildren() {
       List<Result> list=new ArrayList<Result>();
       list.addAll(columnResultList);

       return list;
    }

    public List<ColumnResult> getColumnResultList() {
        return columnResultList;
    }

    public void setColumnResultList(List<ColumnResult> columnResultList) {
        this.columnResultList = columnResultList;
    }

    @Override
    public boolean isPackage() {
        return true;
    }

    @Override
    public String toString() {
        return "ColumnsResult [columnResultList=" + columnResultList + ", name=" + name + ", totalCount=" + totalCount
                + ", diffCount=" + diffCount + ", addCount=" + addCount + ", missCount=" + missCount + ", resultType="
                + resultType + "]";
    }

}

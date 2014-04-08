package com.sohu.assist.dbcompare.model.result;

import java.util.ArrayList;
import java.util.List;

public class TablesResult extends BaseLimbResult {
    private List<TableResult> tableResultList;

    @Override
    public List<Result> getChildren() {
        List <Result> list=new ArrayList<Result>();
        list.addAll(tableResultList);

        return list;
    }


    @Override
    public String toString() {
        return "TablesResult [tableResultList=" + tableResultList + ", totalCount=" + totalCount + ", diffCount="
                + diffCount + ", addCount=" + addCount + ", missCount=" + missCount + ", resultType=" + resultType
                + ", name=" + name + "]";
    }


    public List<TableResult> getTableResultList() {
        return tableResultList;
    }

    public void setTableResultList(List<TableResult> tableResultList) {
        this.tableResultList = tableResultList;
    }

    @Override
    public boolean isPackage() {
        return true;
    }

}

package com.sohu.assist.dbcompare.model.result;

public abstract class BaseResult implements Result {
    protected RESULT_TYPE resultType= RESULT_TYPE.EQUAL;
    protected String name;

    public RESULT_TYPE getResultType() {
        return resultType;
    }

    public void setResultType(RESULT_TYPE resultType) {
        this.resultType = resultType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract boolean isPackage();

}

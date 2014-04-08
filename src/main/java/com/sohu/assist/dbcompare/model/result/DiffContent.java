package com.sohu.assist.dbcompare.model.result;

public class DiffContent {
    private RESULT_TYPE type;

    private String sourceCode;
    private String targetCode;

    public RESULT_TYPE getType() {
        return type;
    }

    @Override
    public String toString() {
        return "DiffContent [type=" + type + ", sourceCode=" + sourceCode + ", targetCode=" + targetCode + "]";
    }

    public void setType(RESULT_TYPE type) {
        this.type = type;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getTargetCode() {
        return targetCode;
    }

    public void setTargetCode(String targetCode) {
        this.targetCode = targetCode;
    }

}

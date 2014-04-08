package com.sohu.assist.dbcompare.model.result;

public class ColumnResult extends BaseLeafResult {
    private DiffContent content;

    @Override
    public DiffContent getDiffContent() {
        return content;
    }

    public DiffContent getContent() {
        return content;
    }

    public void setContent(DiffContent content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ColumnResult [content=" + content + ", resultType=" + resultType + ", name=" + name + "]";
    }

}

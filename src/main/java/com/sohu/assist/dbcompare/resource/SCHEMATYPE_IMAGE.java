package com.sohu.assist.dbcompare.resource;

public enum SCHEMATYPE_IMAGE {
    TABLES("tables.ico"), TABLE("table.ico"), COLUMNS("columns.ico"), COLUMN("column.ico"),CONTENT("trans.ico");

    String resource;

    SCHEMATYPE_IMAGE(String resource) {
        this.resource = resource;
    }

    public String getRes() {
        return resource;
    }
}

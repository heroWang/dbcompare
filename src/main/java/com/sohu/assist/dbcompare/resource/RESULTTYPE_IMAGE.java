package com.sohu.assist.dbcompare.resource;

public enum RESULTTYPE_IMAGE {
    ADD("add.png"), MISS("miss.png"),DIFF("diff.png"), EQUAL("equal.png");
    private String resource;

    RESULTTYPE_IMAGE(String resource) {
        this.resource = resource;
    }

    public String getRes() {
        return resource;
    }
}

package com.sohu.assist.dbcompare.model.result;

import com.sohu.assist.dbcompare.resource.RESULTTYPE_IMAGE;

public enum RESULT_TYPE {
    EQUAL(RESULTTYPE_IMAGE.EQUAL), ADD(RESULTTYPE_IMAGE.ADD), MISS(RESULTTYPE_IMAGE.MISS), MODIFY(RESULTTYPE_IMAGE.DIFF);

    private RESULTTYPE_IMAGE IMAGE;

    RESULT_TYPE(RESULTTYPE_IMAGE IMAGE) {
        this.IMAGE = IMAGE;
    }

    public RESULTTYPE_IMAGE getIMAGE() {
        return IMAGE;
    }
}

package com.sohu.assist.dbcompare.model.result;

import java.util.List;

public abstract class BaseLeafResult extends BaseResult implements Result {

    @Override
    public final boolean isLeaf() {
        return true;
    }

    @Override
    public final List<Result> getChildren() {
        return null;
    }

    @Override
    public boolean isPackage() {
        return false;
    }

    public abstract DiffContent getDiffContent();

}

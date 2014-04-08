package com.sohu.assist.dbcompare.model.result;

public abstract class BaseLimbResult extends BaseResult implements Result {
    protected int totalCount;
    protected int diffCount;
    protected int addCount;
    protected int missCount;

    public int getMissCount() {
        return missCount;
    }

    public void setMissCount(int missCount) {
        this.missCount = missCount;
    }

    @Override
    public boolean isPackage() {
        return false;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getDiffCount() {
        return diffCount;
    }

    public void setDiffCount(int diffCount) {
        this.diffCount = diffCount;
    }

    public int getAddCount() {
        return addCount;
    }

    public void setAddCount(int addCount) {
        this.addCount = addCount;
    }

}

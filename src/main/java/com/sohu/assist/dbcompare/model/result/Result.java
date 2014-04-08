package com.sohu.assist.dbcompare.model.result;

import java.util.List;

public interface Result {
    boolean isLeaf();

    List<Result> getChildren();
}

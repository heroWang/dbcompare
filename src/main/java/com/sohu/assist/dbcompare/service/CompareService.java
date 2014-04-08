package com.sohu.assist.dbcompare.service;


import com.sohu.assist.dbcompare.model.Setting;
import com.sohu.assist.dbcompare.model.result.Result;

public interface CompareService <S extends Setting>{
   Result   compare(S  setting);
}

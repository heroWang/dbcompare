package com.sohu.assist.dbcompare.service;

import com.sohu.assist.dbcompare.model.Setting;

public interface SettingService {
    Setting getSetting();


    void updateSetting(Setting setting);
}

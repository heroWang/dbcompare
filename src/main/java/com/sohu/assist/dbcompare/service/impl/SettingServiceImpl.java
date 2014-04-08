package com.sohu.assist.dbcompare.service.impl;

import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.sohu.assist.dbcompare.model.Setting;
import com.sohu.assist.dbcompare.model.TablesCompareSetting;
import com.sohu.assist.dbcompare.service.SettingService;
import com.sohu.assist.dbcompare.util.Byte_File_Object;
import com.sohu.assist.dbcompare.util.JaxbUtil;

@Service("settingSerivce")
public class SettingServiceImpl implements SettingService {
   // private final static String CONFIG_LOCATE = SettingServiceImpl.class.getClassLoader().getResource("config.xml").getPath();
    private final static String CONFIG_LOCATE = SettingServiceImpl.class.getClassLoader().getResource("setting/config.xml").getPath();
    private final static Logger LOG=Logger.getLogger(SettingServiceImpl.class);

    @Override
    public Setting getSetting() {
        byte[] bytes = Byte_File_Object.getBytesFromFile(new File(CONFIG_LOCATE));
        String source = new String(bytes);
        LOG.debug("read config.xml:"+source);

        TablesCompareSetting setting = JaxbUtil.unmarshal(source, TablesCompareSetting.class);

        return setting;
    }

    @Override
    public void updateSetting(Setting setting) {
        String data = JaxbUtil.marshal(setting);
        Byte_File_Object.getFileFromBytes(data.getBytes(), CONFIG_LOCATE);
    }

}

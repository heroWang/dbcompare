package com.sohu.assist.dbcompare.model;

import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "config")
@XmlAccessorType(XmlAccessType.FIELD)
public class TablesCompareSetting implements Setting {
    private final static String SEPARATOR=";";

    @XmlElement(name = "source", type = DatabaseSetting.class)
    private DatabaseSetting sourceDatabaseSetting;

    @XmlElement(name = "target", type = DatabaseSetting.class)
    private DatabaseSetting targetDatabaseSetting;

    @XmlElement(name = "include-tables")
    private String includeTables;

    public DatabaseSetting getSourceDatabaseSetting() {
        return sourceDatabaseSetting;
    }

    public void setSourceDatabaseSetting(DatabaseSetting sourceDatabaseSetting) {
        this.sourceDatabaseSetting = sourceDatabaseSetting;
    }

    public DatabaseSetting getTargetDatabaseSetting() {
        return targetDatabaseSetting;
    }

    public void setTargetDatabaseSetting(DatabaseSetting targetDatabaseSetting) {
        this.targetDatabaseSetting = targetDatabaseSetting;
    }

    public String getIncludeTables() {
        return includeTables;
    }

    public void setIncludeTables(String includeTables) {
        this.includeTables = includeTables;
    }

    public List<String> getIncludeTableNames(){
        if(this.includeTables==null || this.includeTables.equals("")){
            return null;
        }
        return Arrays.asList(this.includeTables.split(SEPARATOR));
    }
}

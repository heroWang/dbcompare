package com.sohu.assist.dbcompare.model.json;

import java.util.List;

public class JsonNode {
    private String title;
    private String icon;
    private String sourceContent;
    private String targetContent;
    private String isFolder = "true";
    private  boolean expand=false;

    public String getIsFolder() {
        return isFolder;
    }

    public void setIsFolder(String isFolder) {
        this.isFolder = isFolder;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public String isFolder() {
        return isFolder;
    }

    public void setFolder(String isFolder) {
        this.isFolder = isFolder;
    }

    private List<JsonNode> children;

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "JsonNode [title=" + title + ", icon=" + icon + ", sourceContent=" + sourceContent + ", targetContent="
                + targetContent + ", isFolder=" + isFolder + ", children=" + children + "]";
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<JsonNode> getChildren() {
        return children;
    }

    public void setChildren(List<JsonNode> children) {
        this.children = children;
    }

    public String getSourceContent() {
        return sourceContent;
    }

    public void setSourceContent(String sourceContent) {
        this.sourceContent = sourceContent;
    }

    public String getTargetContent() {
        return targetContent;
    }

    public void setTargetContent(String targetContent) {
        this.targetContent = targetContent;
    }

}

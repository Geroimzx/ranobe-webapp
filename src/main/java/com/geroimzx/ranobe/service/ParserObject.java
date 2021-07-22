package com.geroimzx.ranobe.service;

public class ParserObject {
    private Long parentId;

    private String url;

    private int pageCount;

    private int delay;

    private String[] ignoredWords;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public String[] getIgnoredWords() {
        return ignoredWords;
    }

    public void setIgnoredWords(String[] ignoredWords) {
        this.ignoredWords = ignoredWords;
    }
}

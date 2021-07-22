package com.geroimzx.ranobe.model;

public enum StatusEnum {
    IN_PROGRESS("В процессе"),
    COMPLETE("Завершено");
    private String status;
    StatusEnum(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
}

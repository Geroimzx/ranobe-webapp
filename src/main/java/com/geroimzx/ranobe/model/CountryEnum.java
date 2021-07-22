package com.geroimzx.ranobe.model;

public enum CountryEnum {
    JAPAN("Япония"),
    CHINA("Китай"),
    KOREA("Корея");
    private String country;
    CountryEnum(String country) {
        this.country = country;
    }
    public String getCountry() {
        return country;
    }
}

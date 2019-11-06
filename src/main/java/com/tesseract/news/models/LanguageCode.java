package com.tesseract.news.models;

public enum LanguageCode {

    ENGLISH("en"),

    DUTCH("de"),

    ARABIC("ar"),

    ESPANIOL("es"),

    FRENCH("fr");

    private String languageCode;

    LanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String toString(){
        return this.languageCode;
    }
}

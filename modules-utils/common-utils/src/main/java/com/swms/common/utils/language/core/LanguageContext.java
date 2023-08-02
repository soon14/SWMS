package com.swms.common.utils.language.core;

public class LanguageContext {

    private LanguageContext() {
    }

    public static final ThreadLocal<String> LANGUAGE = new ThreadLocal<>();

    public static String getLanguage() {
        return LANGUAGE.get() == null ? "zh_CN" : LANGUAGE.get();
    }

    public static void setLanguage(String lang) {
        LANGUAGE.set(lang);
    }

    public static void remove() {
        LANGUAGE.remove();
    }
}

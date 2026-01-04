package com.nuclea.data.enums;

import lombok.Getter;

/**
 * Supported language codes.
 */
@Getter
public enum LanguageEnum {
    TR("tr", "Turkish"),
    EN("en", "English");

    private final String code;
    private final String displayName;

    LanguageEnum(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public static LanguageEnum fromCode(String code) {
        for (LanguageEnum lang : values()) {
            if (lang.code.equalsIgnoreCase(code)) {
                return lang;
            }
        }
        return TR; // Default to Turkish
    }
}

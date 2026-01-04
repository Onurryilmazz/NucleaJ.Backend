package com.nuclea.common.constants;

/**
 * Cache key constants for consistent cache naming.
 */
public final class CacheKeys {

    private CacheKeys() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static final String APP_SETTINGS = "app_settings";
    public static final String APP_SETTINGS_BY_KEY = "app_settings:";
    public static final String APP_SETTINGS_BY_GROUP = "app_settings_group:";

    public static final String ELEMENTS = "elements";
    public static final String ELEMENT_BY_KEY = "element:";
    public static final String ELEMENT_VALUES = "element_values";
    public static final String ELEMENT_VALUE_BY_ELEMENT_LANGUAGE = "element_value:";

    public static final String LANGUAGES = "languages";
    public static final String LANGUAGE_BY_CODE = "language:";

    public static final String URL_MAPPINGS = "url_mappings";
    public static final String URL_MAPPING_BY_SOURCE = "url_mapping:";

    public static final String COUNTRIES = "countries";
    public static final String CITIES = "cities";
    public static final String DISTRICTS = "districts";
    public static final String NEIGHBOURHOODS = "neighbourhoods";

    /**
     * Generate a composite cache key.
     */
    public static String composite(String... parts) {
        return String.join(":", parts);
    }
}

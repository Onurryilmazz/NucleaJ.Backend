package com.nuclea.common.constants;

/**
 * Application settings key constants for strongly-typed access.
 */
public final class AppSettingsKeys {

    private AppSettingsKeys() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    // SMTP Settings
    public static final String SMTP_URL = "SMTP_Url";
    public static final String SMTP_PORT = "SMTP_Port";
    public static final String SMTP_USE_SSL = "SMTP_UseSSL";
    public static final String SMTP_USERNAME = "SMTP_Username";
    public static final String SMTP_PASSWORD = "SMTP_Password";
    public static final String SMTP_SENDER = "SMTP_Sender";
    public static final String SMTP_SENDER_DISPLAY_NAME = "SMTP_SenderDisplayName";

    // CDN/FTP Settings
    public static final String CDN_FTP_URL = "CDN_FTP_Url";
    public static final String CDN_FTP_PORT = "CDN_FTP_Port";
    public static final String CDN_FTP_USERNAME = "CDN_FTP_Username";
    public static final String CDN_FTP_PASSWORD = "CDN_FTP_Password";
    public static final String CDN_BASE_URL = "CDN_BaseUrl";
    public static final String CDN_IMAGES_FOLDER = "CDN_ImagesFolder";
    public static final String CDN_DOCUMENTS_FOLDER = "CDN_DocumentsFolder";
    public static final String CDN_VIDEOS_FOLDER = "CDN_VideosFolder";

    // Email Validation
    public static final String EMAIL_VALIDATION_API_KEY = "EmailValidation_ApiKey";
    public static final String EMAIL_VALIDATION_ENABLED = "EmailValidation_Enabled";

    // OAuth Settings
    public static final String GOOGLE_CLIENT_ID = "Google_ClientId";
    public static final String GOOGLE_CLIENT_SECRET = "Google_ClientSecret";
    public static final String APPLE_CLIENT_ID = "Apple_ClientId";
    public static final String APPLE_TEAM_ID = "Apple_TeamId";
    public static final String APPLE_KEY_ID = "Apple_KeyId";

    // General Settings
    public static final String DEFAULT_LANGUAGE = "DefaultLanguage";
    public static final String SUPPORTED_LANGUAGES = "SupportedLanguages";
    public static final String EMAIL_VERIFICATION_CODE_EXPIRY_MINUTES = "EmailVerificationCodeExpiryMinutes";
    public static final String PASSWORD_RESET_CODE_EXPIRY_MINUTES = "PasswordResetCodeExpiryMinutes";
}

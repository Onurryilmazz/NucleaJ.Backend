package com.nuclea.common.constants;

/**
 * Security claim key constants.
 */
public final class SecurityClaimKeys {

    private SecurityClaimKeys() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    // Standard claims
    public static final String USER_ID = "user_id";
    public static final String CUSTOMER_ID = "customer_id";
    public static final String EMAIL = "email";
    public static final String NAME = "name";
    public static final String AUTHENTICATED = "authenticated";

    // Custom claims
    public static final String IS_EMAIL_VERIFIED = "email_verified";
    public static final String OAUTH_PROVIDER = "oauth_provider";
    public static final String ROLES = "roles";
    public static final String PERMISSIONS = "permissions";
}

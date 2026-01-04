# Security Policy

## Supported Versions

| Version | Supported          |
| ------- | ------------------ |
| 1.0.x   | :white_check_mark: |

## Reporting a Vulnerability

If you discover a security vulnerability within NucleaJ Backend, please send an email to security@example.com. All security vulnerabilities will be promptly addressed.

Please do not disclose security-related issues publicly until we've had a chance to address them.

## Security Best Practices

### 1. Environment Variables
**NEVER** commit sensitive information to version control. Always use environment variables:

```bash
export JWT_SECRET_KEY="your-secure-secret-key"
export DB_PASSWORD="your-database-password"
export API_KEY="your-api-key"
```

### 2. JWT Secret Key
Generate a strong, random secret key (minimum 32 characters):

```bash
# Using OpenSSL
openssl rand -base64 32

# Using Python
python -c "import secrets; print(secrets.token_urlsafe(32))"
```

### 3. Database Security
- Use strong passwords for database accounts
- Limit database user permissions to only what's necessary
- Enable SSL/TLS for database connections in production
- Regularly update database credentials

### 4. API Key Validation
- Change the default API key immediately
- Use different API keys for different environments
- Rotate API keys regularly
- Never share API keys in public repositories

### 5. CORS Configuration
- Only allow trusted origins in production
- Avoid using wildcard (*) in production
- Regularly review and update allowed origins

### 6. HTTPS
- Always use HTTPS in production
- Enable secure cookies (`cookie.setSecure(true)`)
- Use HSTS headers

### 7. Rate Limiting
- Keep rate limiting enabled in production
- Adjust limits based on your use case
- Monitor for unusual patterns

### 8. Dependency Security
Regularly update dependencies to patch security vulnerabilities:

```bash
mvn versions:display-dependency-updates
mvn versions:use-latest-versions
```

### 9. Logging
- Never log sensitive information (passwords, tokens, API keys)
- Use appropriate log levels in production
- Implement log monitoring and alerting

### 10. Password Policy
- Enforce strong password requirements
- Use BCrypt for password hashing (already implemented)
- Implement password expiration policies if needed

## Security Headers

The application automatically sets security headers through Spring Security. Ensure these are not disabled in production:
- X-Content-Type-Options: nosniff
- X-Frame-Options: DENY
- X-XSS-Protection: 1; mode=block
- Strict-Transport-Security (when using HTTPS)

## Regular Security Audits

1. **Code Review**: Conduct regular security-focused code reviews
2. **Dependency Scanning**: Use tools like OWASP Dependency-Check
3. **Penetration Testing**: Perform regular security testing
4. **Access Review**: Regularly review user access and permissions

## Known Security Considerations

### JWT Token Storage
- Store access tokens securely in the client (avoid localStorage for sensitive apps)
- Use HttpOnly cookies for refresh tokens
- Implement token rotation

### SQL Injection
- The application uses JPA/Hibernate with parameterized queries
- Avoid raw SQL queries where possible
- Always validate and sanitize user input

### XSS Protection
- All user input should be validated
- Use proper encoding when rendering user content
- Leverage Spring Security's built-in XSS protection

## Incident Response

In case of a security incident:
1. Immediately rotate all credentials
2. Review access logs
3. Notify affected users
4. Document the incident
5. Implement fixes and preventive measures

## Contact

For security concerns, contact: onur@avvamobile.com

---

**Security is everyone's responsibility. Stay vigilant!**

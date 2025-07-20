# 🔐 Security Setup Guide for Movie Ticket Booking System

This guide will help you set up the project securely and avoid GitHub Push Protection issues.

## 🚨 Important Security Notice

**NEVER commit sensitive information like API keys, passwords, or tokens to your repository!**

## 📋 Prerequisites

1. **Git** - Install from [git-scm.com](https://git-scm.com/)
2. **Java 21** - Install from [oracle.com](https://www.oracle.com/java/technologies/downloads/) or use OpenJDK
3. **Maven** - Install from [maven.apache.org](https://maven.apache.org/download.cgi)
4. **MySQL** - Install from [mysql.com](https://dev.mysql.com/downloads/)

## 🔧 Step-by-Step Setup

### 1. Clone and Initialize Repository

```bash
# Clone your repository
git clone <your-repository-url>
cd Movie_TicketBooking_Final

# Initialize Git (if not already done)
git init
```

### 2. Configure Environment Variables

Create a `.env` file in the project root:

```bash
# Database Configuration
DB_USER=your_database_username
DB_PASSWORD=your_database_password

# Email Configuration (Google SMTP)
TO_EMAIL=your_email@gmail.com
EMAIL_PASS=your_app_password
FROM_EMAIL_ID=your_email@gmail.com

# Twilio Configuration
TWILIO_ACCOUNT_SID=your_twilio_account_sid
TWILIO_AUTH_TOKEN=your_twilio_auth_token
WHATSAPP_NUMBER=your_twilio_whatsapp_number
TWILIO_SMS_NUMBER=your_twilio_sms_number
TWILIO_WHATSAPP_TEMPLATE=your_whatsapp_template_sid
```

### 3. Set Up External Services

#### Gmail SMTP Setup
1. Enable 2-Factor Authentication on your Google account
2. Generate an App Password:
   - Go to Google Account settings
   - Security → 2-Step Verification → App passwords
   - Generate a new app password for "Mail"
3. Use this app password in your `.env` file (not your regular password)

#### Twilio Setup
1. Create a Twilio account at [twilio.com](https://www.twilio.com/)
2. Get your Account SID and Auth Token from the Twilio Console
3. Set up WhatsApp Business API (if using WhatsApp notifications)
4. Get your Twilio phone numbers

### 4. Database Setup

```sql
-- Create the database
CREATE DATABASE movie_ticket_booking;

-- Create a user (optional but recommended)
CREATE USER 'movie_user'@'localhost' IDENTIFIED BY 'your_secure_password';
GRANT ALL PRIVILEGES ON movie_ticket_booking.* TO 'movie_user'@'localhost';
FLUSH PRIVILEGES;
```

### 5. Remove Sensitive Files from Git Tracking

If you've already committed sensitive files, remove them:

```bash
# Remove application.properties from Git tracking (but keep the file locally)
git rm --cached src/main/resources/application.properties

# Remove any other sensitive files
git rm --cached .env
git rm --cached *.key
git rm --cached *.pem
```

### 6. Verify .gitignore Configuration

Ensure your `.gitignore` file includes:

```gitignore
# Sensitive files
src/main/resources/application.properties
.env
.env.local
.env.development
.env.test
.env.production

# Other sensitive files
*.key
*.pem
*.p12
*.jks
*.keystore

# IDE files
.idea/
.vscode/
*.iml
.project
.classpath
.settings/

# Build files
target/
build/
*.jar
*.war

# Logs
*.log
logs/

# OS files
.DS_Store
Thumbs.db
```

### 7. Test Your Configuration

```bash
# Build the project
mvn clean compile

# Run the application
mvn spring-boot:run
```

The application should start without errors and be accessible at `http://localhost:8080`

## 🚨 Common Issues and Solutions

### GitHub Push Protection Error

**Error**: "GitHub's Push Protection detected a secret in your code"

**Solution**:
1. Remove the file from Git tracking: `git rm --cached <file-path>`
2. Add the file to `.gitignore`
3. Commit the changes: `git add .gitignore && git commit -m "Remove sensitive files"`
4. Push again

### Database Connection Error

**Error**: "Cannot connect to MySQL"

**Solutions**:
1. Verify MySQL is running: `sudo systemctl status mysql` (Linux) or check Services (Windows)
2. Check credentials in your `.env` file
3. Ensure database exists: `CREATE DATABASE movie_ticket_booking;`

### Email Not Working

**Error**: "Authentication failed" or "Invalid credentials"

**Solutions**:
1. Use App Password, not regular password
2. Enable 2FA on Google account
3. Check if "Less secure app access" is enabled (if not using App Password)

### Twilio Errors

**Error**: "Invalid Account SID" or "Authentication failed"

**Solutions**:
1. Verify Account SID and Auth Token from Twilio Console
2. Check phone number format (E.164: +1234567890)
3. Ensure WhatsApp Business API is properly configured

## 🔒 Security Best Practices

### 1. Environment Variables
- Always use environment variables for sensitive data
- Never hardcode secrets in source code
- Use different credentials for development and production

### 2. Git Security
- Use `.gitignore` to exclude sensitive files
- Regularly audit your repository for secrets
- Use Git hooks to prevent accidental commits of sensitive data

### 3. Application Security
- Use HTTPS in production
- Implement proper authentication and authorization
- Validate all user inputs
- Use prepared statements for database queries

### 4. External Services
- Use least privilege principle for API keys
- Regularly rotate credentials
- Monitor API usage for unusual activity

## 📝 Deployment Checklist

Before deploying to production:

- [ ] All sensitive data moved to environment variables
- [ ] `.env` file added to `.gitignore`
- [ ] Database credentials secured
- [ ] Email credentials configured with App Password
- [ ] Twilio credentials verified
- [ ] HTTPS configured
- [ ] Error logging configured
- [ ] Backup strategy in place

## 🆘 Getting Help

If you encounter issues:

1. Check the troubleshooting section in the main README
2. Verify all environment variables are set correctly
3. Check application logs for detailed error messages
4. Ensure all prerequisites are installed and configured
5. Test each external service individually

## 📞 Support

For additional help:
- Check the main README.md file
- Review the application logs
- Test with minimal configuration first
- Ensure all dependencies are properly installed

---

**Remember**: Security is everyone's responsibility. Always follow security best practices and never commit sensitive information to version control! 
# 🎬 ReelTime - Movie Ticket Booking System

A modern, full-stack web application for booking movie tickets with real-time seat selection, user authentication, and notification services.

## ✨ Features

### 🎯 Core Features
- **User Authentication & Registration**
  - Secure user registration and login
  - Profile management with profile picture upload
  - Two-factor authentication (2FA) support
  - Password reset functionality
  - Account deactivation

- **Movie Management**
  - Dynamic movie carousel on home page
  - Movie details with posters and descriptions
  - Show timings and availability
  - Movie categorization by genre

- **Booking System**
  - Real-time seat selection
  - Interactive seat map
  - Booking confirmation with QR code
  - Booking history and ticket management
  - Price calculation based on seat count

- **Notifications**
  - Email notifications for booking confirmations
  - WhatsApp notifications (Twilio integration)
  - Customizable notification preferences

### 🎨 User Interface
- **Modern Responsive Design**
  - Bootstrap 5 framework
  - Mobile-friendly interface
  - Animated elements and transitions
  - Celebration animations for successful bookings
  - Consistent branding with ReelTime logo

- **Interactive Elements**
  - Swiper.js carousel for movies
  - Dynamic seat selection interface
  - Real-time form validation
  - Loading states and feedback

## 📸 Screenshots

### 🏠 Home Page
![Home Page](screenshots/home-page.png)
*Dynamic movie carousel with modern UI*

### 🎬 Movie Details
![Movie Details](screenshots/movie-details.png)
*Movie information with booking options*

### 🎫 Seat Selection
![Seat Selection](screenshots/seat-selection.png)
*Interactive seat map for booking*

### ✅ Booking Success
![Booking Success](screenshots/booking-success.png)
*Confirmation page with celebration animation*

### 👤 User Profile
![User Profile](screenshots/user-profile.png)
*Profile management with 2FA and preferences*

### 📱 Responsive Design
![Mobile View](screenshots/mobile-view.png)
*Mobile-friendly interface*

---

## 🛠️ Technology Stack

### Backend
- **Java 21** - Core programming language
- **Spring Boot 3.4.3** - Application framework
- **Spring Data JPA** - Database operations
- **Hibernate** - ORM framework
- **MySQL 8.0** - Database
- **Apache Tomcat** - Web server
- **Maven** - Build tool

### Frontend
- **HTML5** - Structure
- **CSS3** - Styling and animations
- **JavaScript** - Interactivity
- **Bootstrap 5** - UI framework
- **Swiper.js** - Carousel functionality
- **Thymeleaf** - Template engine

### External Services
- **Twilio** - WhatsApp notifications
- **Google SMTP** - Email notifications
- **ZXing** - QR code generation

## 📋 Prerequisites

Before running this application, make sure you have:

- **Java 21** or higher
- **Maven 3.6** or higher
- **MySQL 8.0** or higher
- **Git** for version control

## 🚀 Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/movie-ticket-booking.git
cd movie-ticket-booking
```

### 2. Database Setup
1. Create a MySQL database:
```sql
CREATE DATABASE movie_booking;
```

2. Update database configuration in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/movie_booking
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. Environment Variables Setup

Create a `.env` file in the project root (this file is already in `.gitignore` for security):

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

**Important Security Notes:**
- Never commit the `.env` file to version control
- Use app passwords for Gmail (not your regular password)
- Keep your Twilio credentials secure
- The `application.properties` file uses environment variables for sensitive data

### 4. External Services Configuration

#### Email Configuration (Google SMTP)
The application is configured to use environment variables for email settings. Make sure your `.env` file contains:
- `TO_EMAIL`: Your Gmail address
- `EMAIL_PASS`: Your Gmail app password (not regular password)
- `FROM_EMAIL_ID`: Your Gmail address for sending emails

#### WhatsApp Configuration (Twilio)
The application uses environment variables for Twilio settings. Ensure your `.env` file contains:
- `TWILIO_ACCOUNT_SID`: Your Twilio Account SID
- `TWILIO_AUTH_TOKEN`: Your Twilio Auth Token
- `WHATSAPP_NUMBER`: Your Twilio WhatsApp number
- `TWILIO_SMS_NUMBER`: Your Twilio SMS number
- `TWILIO_WHATSAPP_TEMPLATE`: Your WhatsApp template SID

### 5. Build and Run
```bash
# Clean and build the project
mvn clean package

# Run the application
mvn spring-boot:run
```

**Alternative: Using environment variables directly**
```bash
# Set environment variables and run
export DB_USER=your_username
export DB_PASSWORD=your_password
export TO_EMAIL=your_email@gmail.com
export EMAIL_PASS=your_app_password
export FROM_EMAIL_ID=your_email@gmail.com
export TWILIO_ACCOUNT_SID=your_account_sid
export TWILIO_AUTH_TOKEN=your_auth_token
export WHATSAPP_NUMBER=your_whatsapp_number
export TWILIO_SMS_NUMBER=your_sms_number
export TWILIO_WHATSAPP_TEMPLATE=your_template_sid

mvn spring-boot:run
```

The application will be available at: `http://localhost:8080`

## 📱 Usage Guide

### For Users

1. **Registration**
   - Visit the registration page
   - Fill in your details (name, email, mobile, username, password)
   - Submit to create your account

2. **Login**
   - Use your email and password to log in
   - Access your personalized dashboard

3. **Browse Movies**
   - View available movies on the home page
   - Click on movie posters for detailed information
   - Check show timings and availability

4. **Book Tickets**
   - Select a movie, date, and show time
   - Choose your preferred seats from the interactive seat map
   - Confirm booking and receive confirmation

5. **Manage Profile**
   - Update personal information
   - Upload profile picture
   - Configure notification preferences
   - Enable/disable 2FA
   - View booking history

### For Administrators

- Monitor bookings through the booking history page
- Manage user accounts and preferences
- View system audit logs

## 🗂️ Project Structure

```
Movie_TicketBooking_Final/
├── src/
│   ├── main/
│   │   ├── java/com/example/
│   │   │   ├── Controller/          # REST controllers
│   │   │   ├── model/              # Entity classes
│   │   │   ├── repo/               # Repository interfaces
│   │   │   ├── service/            # Business logic
│   │   │   └── config/             # Configuration classes
│   │   └── resources/
│   │       ├── static/             # Static assets (CSS, JS, images)
│   │       ├── templates/          # Thymeleaf templates
│   │       └── application.properties
│   └── test/                       # Test files
├── pom.xml                         # Maven configuration
├── README.md                       # This file
└── .gitignore                      # Git ignore rules
```

## 🔧 Configuration

### Application Properties
Key configuration options in `application.properties`:

- **Database**: Connection settings for MySQL
- **Email**: SMTP configuration for notifications
- **WhatsApp**: Twilio settings for messaging
- **Server**: Port and context path settings

### Customization
- **Movies**: Add new movies by updating the movie array in `home.html`
- **Pricing**: Modify ticket prices in the booking controller
- **UI**: Customize styles in CSS files
- **Notifications**: Adjust email and WhatsApp templates

## 🧪 Testing

The application includes comprehensive testing for:
- User registration and authentication
- Movie booking flow
- Seat selection functionality
- Notification delivery
- Profile management
- Error handling

## 🚨 Troubleshooting

### Common Issues

1. **Database Connection Error**
   - Verify MySQL is running
   - Check database credentials in `application.properties`
   - Ensure database exists

2. **Email Notifications Not Working**
   - Verify Google app password (not regular password)
   - Check SMTP settings
   - Ensure 2FA is enabled on Google account

3. **WhatsApp Notifications Not Working**
   - Verify Twilio credentials
   - Check phone number format (E.164)
   - Ensure WhatsApp Business API is configured

4. **Application Won't Start**
   - Check Java version (requires Java 21+)
   - Verify Maven installation
   - Check port availability (default: 8080)

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Your Name**
- GitHub: [@yourusername](https://github.com/yourusername)
- Email: your.email@example.com

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- Bootstrap team for the responsive UI components
- Twilio for WhatsApp integration
- All contributors and testers

---

**Made with ❤️ for movie lovers everywhere!** 
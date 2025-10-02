# 🎬 ReelTime - Movie Ticket Booking System

## 📝 Project Overview
ReelTime is a full-stack web application designed for seamless movie ticket booking. It features real-time seat selection, secure user authentication, booking management, and integrated notification services. Built with Java, Spring Boot, and a modern front-end, this project demonstrates best practices in web development, system integration, and user experience design.

## ✨ Features
- 👤 User registration, login, and profile management
- 🔐 Two-factor authentication (2FA) and password reset
- 🎥 Dynamic movie listings and detailed movie pages
- 🪑 Real-time, interactive seat selection and booking
- 🎟️ Booking confirmation with QR code generation
- 📖 Booking history and ticket management
- 📧 Email and WhatsApp notifications (Twilio integration)
- 📱 Responsive, mobile-friendly UI with Bootstrap 5

## 📸 Screenshots
| Screenshot                | Description                       |
|---------------------------|-----------------------------------|
| ![Registration form](screenshots/registration%20form.png) | Registration form |
| ![Registration successful form](screenshots/registration%20successful%20form.png) | Registration success page |
| ![Contact details](screenshots/contact%20details.png) | User contact details page |
| ![Profile details](screenshots/profile%20details.png) | User profile details |
| ![Password update](screenshots/password%20update.png) | Password update form |
| ![Dashboard 1](screenshots/Dashboard%201.png) | Dashboard view 1 |
| ![Dashboard 2](screenshots/Dashboard%202.png) | Dashboard view 2 |
| ![Movies 1](screenshots/movies%201.png) | Movies listing 1 |
| ![Movies 2](screenshots/movies%202.png) | Movies listing 2 |
| ![Movies 3](screenshots/movies%203.png) | Movies listing 3 |
| ![Movies 4](screenshots/movies%204.png) | Movies listing 4 |
| ![Movies 5](screenshots/movies%205.png) | Movies listing 5 |
| ![Movies 6](screenshots/movies%206.png) | Movies listing 6 |
| ![Movies 7](screenshots/movies%207.png) | Movies listing 7 |
| ![Seat Selection](screenshots/Seat%20Selection.png) | Seat selection interface |
| ![Slot Selection](screenshots/Slot%20Selection.png) | Slot selection interface |
| ![Booking confirmed](screenshots/Booking%20confirmed.png) | Booking confirmation page |
| ![Booking history table view](screenshots/booking%20history%20table%20view.png) | Booking history (table view) |
| ![Booking history tile view](screenshots/booking%20history%20tile%20view.png) | Booking history (tile view) |
| ![Email notification](screenshots/Email%20Notification.png) | Email notification |
| ![Email notification 2](screenshots/Email%20Notification%202.png) | Email notification 2 |
| ![Whatsapp notification](screenshots/Whatsapp%20Notofication.png) | Whatsapp notification |


## 🛠️ Technology Stack
**Backend:**  Java 21, Spring Boot 3.4.3, Spring Data JPA, Hibernate, MySQL 8.0, Apache Tomcat, Maven

**Frontend:** HTML5, CSS3, JavaScript, Bootstrap 5, Swiper.js, Thymeleaf

**External Services:** Twilio (WhatsApp), Google SMTP (Email), ZXing (QR code)

## 🚀 Getting Started
### 📋 Prerequisites
- Java 21 or higher
- Maven 3.6 or higher
- MySQL 8.0 or higher
- Git

### ⚙️ Setup Instructions
1. **Clone the repository:**
```bash
git clone https://github.com/yourusername/movie-ticket-booking.git
cd movie-ticket-booking
```
2. **Database setup:**
   - Create a MySQL database:
```sql
CREATE DATABASE movie_booking;
```
   - Update `src/main/resources/application.properties` with your DB credentials.
3. **Environment variables:**
   - Create a `.env` file in the project root (see sample in README above).
   - Never commit sensitive credentials to version control.
4. **Configure external services:**
   - Set up Google SMTP for email notifications.
   - Set up Twilio for WhatsApp notifications.
5. **Build and run:**
```bash
mvn clean package
mvn spring-boot:run
```
   The application will be available at `http://localhost:8080`.

## 📖 Usage
- Register and log in as a user.
- Browse movies, view details, and select showtimes.
- Choose seats interactively and confirm your booking.
- Receive booking confirmation via email and WhatsApp.
- View and manage your bookings and profile.

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
├── README.md                       # Project documentation
└── .gitignore                      # Git ignore rules
```

## ⚙️ Configuration & Customization
- **Database, email, and messaging** settings are managed in `application.properties` and via environment variables.
- **UI and movie data** can be customized in the HTML templates and static resources.
- **Notification templates** can be adjusted for email and WhatsApp.

## 🧪 Testing & Troubleshooting
- The project includes tests for user registration, booking flow, seat selection, notifications, and error handling.
- Common issues and solutions are documented in the Troubleshooting section above.

## 🌐 Live Demo
*Coming soon: [Demo Link](#)*

## 📄 License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## 👤 Author
**Tarun D**  
GitHub: [Tarun D-code]()  
Email: tarungjsheela@gmail.com

##  Acknowledgments
- Spring Boot, Bootstrap, Twilio, and all open-source contributors.

---
*This project was developed as a demonstration of full-stack web application skills, system integration, and user-centric design.* 

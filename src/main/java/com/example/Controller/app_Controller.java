package com.example.Controller;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.example.model.seat_booking_detailes;
import com.example.model.users;
import com.example.repo.UserInterFace;
import com.example.repo.seatBookingRepo;
import com.example.model.BookingNotification;
import org.springframework.jms.core.JmsTemplate;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;


@Controller
@Service
public class app_Controller extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@Autowired
	UserInterFace service;
	@Autowired
	private users user;
	@Autowired
	private JmsTemplate jmsTemplate;

	@GetMapping("/index")
	public String home() {
		return "registerform";

	}

	@PostMapping("/Register")
	public String Regiter(HttpServletRequest req, Model model) {
		///////////////////////////////////////////////
		
		/////////////////////
		String Name = req.getParameter("name");
		String mobile1 = req.getParameter("mobile");
		// parse bigInteger
		BigInteger mobile = new BigInteger(mobile1);
		String Email = req.getParameter("email");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		//System.out.println("Name :" + Name + " Mobile No :" + mobile + " Email :" + Email + " UserName :" + username
		//		+ " Password :" + password);

		// Check if user already exists by email
		List<users> allUsers = service.findAll();
		for (users u : allUsers) {
			if (u.getEmail() != null && u.getEmail().equalsIgnoreCase(Email)) {
				model.addAttribute("userExists", true);
				return "registerform";
			}
		}

		user.setEmail(Email);
		user.setMobile(mobile);
		user.setName(Name);
		user.setUserName(username);
		user.setPassword(password);
		save_user(user);
		//***** ints very very importent
		user=new users();
		//
		model.addAttribute("registrationSuccess", true);
		return "RegistrationSuccess";
		
	}
		
	

	//save method
	@Transactional
	private void save_user(users user) {
		service.save(user);
		
		
	}

	@PostMapping("/login")
	public String Login(HttpServletRequest req, Model model) {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		System.out.println("Email :" + email + "\n password :" + password);

		for (users user : FindAllUsernameAndPassword()) {
			if (email != null && email.equalsIgnoreCase(user.getEmail())) {
				if (password != null && password.equals(user.getPassword())) {
					System.out.println("Correct login");
					req.getSession().setAttribute("loggedInUser", user);
					model.addAttribute("user", user);
					return "home";
				}
			}
		}
		System.out.println("Wrong login");
		return "FailLogin";
	}

	// FindAllUsernameAndPassword
	public List<users> FindAllUsernameAndPassword() {
		List<users> allUserAndPass = service.findAll();
		return allUserAndPass;
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/privecy")
	public String privacyPolicy() {
		return "Privecy";
	}

	@GetMapping("/terms")
	public String Terms() {
		return "TermsAndConditions";
	}

	@GetMapping("/FAQ")
	public String FAQ() {
		return "FAQ";
	}

	@GetMapping("/movies")
	public String movies() {
		return "movies";
	}

	// Decler movie details functions object

	// Movie Deatiles Logics It is vary vary importent
	@GetMapping("/movie-details.html")
	public String movieInfo(HttpServletRequest req) {
		String movie = req.getParameter("movie");
		switch (movie) {
		case "legent":
			return "legentDTLS";

		case "naaisekar":
			return "naaisekarDTLS";

		case "merasal":
			return "merasalDTLS";
		}
		return null;
	}

	@GetMapping("/showTime")
	public String showTimes() {
		return "showTiming";

	}

	@GetMapping("/Bookings")
	public String Booking(Model m) {
		//sdfsd
		List<seat_booking_detailes> AllBookings=(List<seat_booking_detailes>)Service.findAll();
		
		for (seat_booking_detailes item : AllBookings) {
		    System.out.println(item.getId()+" ,"+item.getDate()+" , "+item.getTime()+
		    		" , "+item.getMovie_Name()+
		    		" ," +item.getSeatts()+" ,"+item.getMobile()+" ,"+item.getName() );
		}

		m.addAttribute("AllBooking", AllBookings);
		return "BookingHistory";

	}


	@GetMapping("/contectUs")
	public String ContectUs() {
		return "contectUs";

	}

	@GetMapping("/BookNow")
	public String BookNow() {
		return "booking";
	}

	@Autowired
	seatBookingRepo Service;

	@Autowired
	private seat_booking_detailes BDTLS;
	
	//
	
	
	private String movieName;
	private String time;
	private String date;
	private String bookingUserName;
	private String bookingUserEmail;
	private  String bookingUserMobile;
	private BigInteger mobileNumber;
	
	@GetMapping("/BookingDTLS")
	public String BookingDTLS(HttpServletRequest req, Model m) {
		 movieName = req.getParameter("movie");
		 time = req.getParameter("showtime");
		 date = req.getParameter("show-date");
		 bookingUserName = req.getParameter("name");
		 bookingUserEmail = req.getParameter("email");
		 String countryCode = req.getParameter("countryCode");
		 String phone = req.getParameter("phone");
		 bookingUserMobile = countryCode + phone;
		 mobileNumber = new BigInteger(phone);

		
		//System.out.println("DATAS ARE SAVED >>>>>>>>>>");
		System.out.println("Movie name: " + movieName + "\nTime: " + time + "\nDate: " + date + "\nBooking User Name: "
				+ bookingUserName + "\nBooking User Email: " + bookingUserEmail + "\nBooking User Mobile: "
				+ bookingUserMobile);
		
		//
		List<seat_booking_detailes> AllBookings=(List<seat_booking_detailes>)Service.findAll();
			
		importent_Methods sa=new importent_Methods();
		
		//***impotent
		
		List<seat_booking_detailes> ans1=sa.getSameBookings(movieName, date, time, AllBookings);
			
		if(ans1!=null) {
				String[] arr=new String[50];
				
				int a=1;	
				int ansarrLength=0;
			for(seat_booking_detailes item :ans1) {				
					//
				System.out.println("movie : "+item.getMovie_Name()+" Date :"+item.getDate() +" time :"+item.getTime()+".........seats :"+item.getSeatts());
				arr[a]=item.getSeatts();
				System.out.println(arr[a]);
				ansarrLength++;
						a++;
			}
			a=1;//madsgrterdfd
			
			//array length
			int arrLen= arr.length;
			System.out.println(arrLen);
			
			// creat ansarray
			String[] ansarr=new String[50];
			int sizeAnsarray=ansarr.length-1;
			int one=1;
			
			
				//itrate and add values for ansarray
			
			for(one=1;one<=sizeAnsarray;one++) {
				int g=1;
				if(arr[one]!=null){
					ansarr[g]=arr[one];
					System.out.println("erttretertrtre :"+ansarr[g]);
					g++;
					
				}	
			}
		
			//itreate values
			
			for(int y=1;y<=ansarr.length-1;y++) {
				if(ansarr[y]!=null) {
				System.out.println("erttretertrtre123 :"+ansarr[y]);
					
		    
		
				}			 
				// ansarr array is redy......					
			}
			System.out.println("lengthOfAnsarr :"+ansarrLength);
			//finised .....dot tuch this
			
			
			String MainMasilaAnswer_is =Arrays.stream(arr) 
            .filter(s -> s != null) // Remove nulls
            .collect(Collectors.joining(", ")); 
			
			//Finished ++++++++++++++
			if(MainMasilaAnswer_is!=null) {
				System.out.println("ansString is ********************** :"+MainMasilaAnswer_is);
				// Convert to an array or list
		        List<String> bookedSeatList = Arrays.asList(MainMasilaAnswer_is.split(","));
				m.addAttribute("UnAvailSeats", bookedSeatList);
				bookedSeatList =Arrays.asList();
			}
			
			
			
			
			}
			else {
			System.out.println("same seat bookingis null");
		}		

		if ("MERSAL".equals(movieName)) {
			m.addAttribute("date",date);
			m.addAttribute("time", time);
			m.addAttribute("movieName", movieName);
			String posterFilename = getPosterFilenameForMovie(movieName);
			System.out.println("[DEBUG] movieName: '" + movieName + "'");
			System.out.println("[DEBUG] posterFilename: '" + posterFilename + "'");
			m.addAttribute("posterFilename", posterFilename);
			
		}

		if ("NAISEKER".equals(movieName)) {
			m.addAttribute("date",date);
			m.addAttribute("time", time);
			m.addAttribute("movieName", movieName);
			String posterFilename = getPosterFilenameForMovie(movieName);
			System.out.println("[DEBUG] movieName: '" + movieName + "'");
			System.out.println("[DEBUG] posterFilename: '" + posterFilename + "'");
			m.addAttribute("posterFilename", posterFilename);
		}

		if ("LEGENT".equals(movieName)) {
			m.addAttribute("date",date);
			m.addAttribute("time", time);
			m.addAttribute("movieName", movieName);
			String posterFilename = getPosterFilenameForMovie(movieName);
			System.out.println("[DEBUG] movieName: '" + movieName + "'");
			System.out.println("[DEBUG] posterFilename: '" + posterFilename + "'");
			m.addAttribute("posterFilename", posterFilename);
		}
		
		

		return "seatselection";
	}
	////
	
	//************************************************************************************importent
	
	@GetMapping("/Masila")
	@Transactional
	public String Masila(HttpServletRequest req, Model m) {
		try {
			String selectedSeats = req.getParameter("selectedSeats");
			// --- Fix: Always get date, time, movieName from request if present ---
			String reqDate = req.getParameter("date");
			String reqTime = req.getParameter("time");
			String reqMovieName = req.getParameter("movieName");
			if (reqDate != null && !reqDate.isEmpty()) date = reqDate;
			if (reqTime != null && !reqTime.isEmpty()) time = reqTime;
			if (reqMovieName != null && !reqMovieName.isEmpty()) movieName = reqMovieName;
			System.out.println("Selected Seats: " + selectedSeats);
			// Save booking to database
			BDTLS.setMovie_Name(movieName);
			BDTLS.setDate(date);
			BDTLS.setTime(time);
			BDTLS.setName(bookingUserName);
			BDTLS.setEmail(bookingUserEmail);
			BDTLS.setMobile(mobileNumber);
			BDTLS.setSeatts(selectedSeats);
			Service.save(BDTLS);

			// Calculate total price based on seat count
			int pricePerTicket = 200;
			int seatCount = selectedSeats.split(",").length;
			int totalPrice = pricePerTicket * seatCount;

			BDTLS.setRupees(totalPrice);

			// --- Ensure phone number is in E.164 format ---
			String e164Mobile = bookingUserMobile;
			if (!e164Mobile.startsWith("+")) {
				e164Mobile = "+91" + e164Mobile.replaceFirst("^91", "");
			}

			// --- Null/empty checks before sending notification ---
			if (bookingUserEmail == null || bookingUserEmail.isEmpty()) {
				System.out.println("Booking user email is null or empty, skipping notification.");
				return "error";
			}
			if (e164Mobile == null || e164Mobile.isEmpty()) {
				System.out.println("Booking user mobile is null or empty, skipping notification.");
				return "error";
			}

			// Send JMS notification
			BookingNotification notification = new BookingNotification();
			notification.setBookingUserName(bookingUserName);
			notification.setBookingUserEmail(bookingUserEmail);
			notification.setBookingUserMobile(e164Mobile);
			notification.setMovieName(movieName);
			notification.setMovieDate(date);
			notification.setMovieTime(time);
			notification.setSeatNumber(selectedSeats);
			notification.setPrice(String.valueOf(totalPrice));
			int bookingId = BDTLS.getId();
			notification.setBookingId(bookingId);
			jmsTemplate.convertAndSend("bookingNotificationQueue", notification);
			
			// Generate QR code for booking success page
			String qrCodeData = String.format(
				"Booking Details:\n" +
				"Name: %s\n" +
				"Email: %s\n" +
				"Phone: %s\n" +
				"Movie: %s\n" +
				"Date: %s\n" +
				"Time: %s\n" +
				"Seat: %s\n" +
				"Price: ₹%s",
				bookingUserName,
				bookingUserEmail,
				e164Mobile,
				movieName,
				date,
				time,
				selectedSeats,
				totalPrice
			);
			
			String qrCodeBase64 = generateQRCode(qrCodeData);
			
			// Add all booking details to model
			m.addAttribute("userName", bookingUserName);
			m.addAttribute("email", bookingUserEmail);
			m.addAttribute("phone", e164Mobile);
			m.addAttribute("movieName", movieName);
			m.addAttribute("date", date);
			m.addAttribute("time", time);
			m.addAttribute("seatNumber", selectedSeats);
			m.addAttribute("price", "₹" + totalPrice);
			m.addAttribute("qrCodeImage", qrCodeBase64 != null ? "data:image/png;base64," + qrCodeBase64 : null);
			
			return "BookingSuccess";
			
		} catch (Exception e) {
			System.out.println("Error in Masila: " + e.getMessage());
			return "error";
		}
	}
	
	private String generateQRCode(String data) {
		try {
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200);
			
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
			
			return Base64.getEncoder().encodeToString(outputStream.toByteArray());
		} catch (Exception e) {
			return null;
		}
	}

	
	private int len(String selectedSeats) {
		if (selectedSeats != null) {
			//splting
			String[] seatsArray = selectedSeats.split(",");
			int sizeOfseatArray=seatsArray.length;
			return sizeOfseatArray;
		}
		return 0;
	}
	
	@GetMapping({"/", "/home"})
	public String home1(HttpServletRequest req, Model model) {
		users user = (users) req.getSession().getAttribute("loggedInUser");
		model.addAttribute("user", user);
		return "home";
	}
	
	@GetMapping("/profile")
	public String userProfile(HttpServletRequest req, Model model) {
		users sessionUser = (users) req.getSession().getAttribute("loggedInUser");
		if (sessionUser == null) {
			return "redirect:/login";
		}
		Optional<users> userOpt = service.findById(sessionUser.getUserID());
		if (userOpt.isPresent()) {
			model.addAttribute("user", userOpt.get());
			return "profile";
		} else {
			return "redirect:/login";
		}
	}
	
	@GetMapping("/ticket/{id}")
	public String viewTicketById(@PathVariable("id") int id, Model m) {
		seat_booking_detailes booking = Service.findById(id).orElse(null);
		if (booking == null) {
			m.addAttribute("error", "Booking not found");
			return "error";
		}
		m.addAttribute("userName", booking.getName());
		m.addAttribute("email", booking.getEmail());
		m.addAttribute("phone", booking.getMobile());
		m.addAttribute("movieName", booking.getMovie_Name());
		m.addAttribute("date", booking.getDate());
		m.addAttribute("time", booking.getTime());
		m.addAttribute("seatNumber", booking.getSeatts());
		m.addAttribute("price", "₹" + booking.getRupees());
		// Optionally regenerate QR code for this booking
		return "BookingSuccess";
	}

    @GetMapping("/registerform")
    public String showRegisterForm() {
        return "registerform";
    }

    @PostMapping("/changePassword")
    public String changePassword(HttpServletRequest req, Model model) {
        users user = (users) req.getSession().getAttribute("loggedInUser");
        if (user == null) {
            model.addAttribute("error", "You must be logged in to change your password.");
            return "login";
        }
        String currentPassword = req.getParameter("currentPassword");
        String newPassword = req.getParameter("newPassword");
        if (!user.getPassword().equals(currentPassword)) {
            model.addAttribute("user", user);
            model.addAttribute("passwordChangeError", "Current password is incorrect.");
            return "home";
        }
        user.setPassword(newPassword);
        service.save(user);
        req.getSession().setAttribute("loggedInUser", user);
        model.addAttribute("user", user);
        model.addAttribute("passwordChangeSuccess", "Password changed successfully!");
        return "home";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest req) {
        req.getSession().invalidate();
        return "redirect:/login";
    }

    @PostMapping("/profile/uploadPicture")
    public String uploadProfilePicture(HttpServletRequest req, @RequestParam("profilePicture") MultipartFile file, Model model) {
        users user = (users) req.getSession().getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        try {
            String uploadDir = "src/main/resources/static/profile_pics/";
            Files.createDirectories(Paths.get(uploadDir));
            String filename = "user_" + user.getUserID() + "_" + System.currentTimeMillis() + ".jpg";
            Path filePath = Paths.get(uploadDir + filename);
            file.transferTo(filePath);
            user.setProfilePictureUrl("/profile_pics/" + filename);
            service.save(user);
            req.getSession().setAttribute("loggedInUser", user);
            user.getAuditLogs().add("Profile picture updated at " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            service.save(user);
        } catch (Exception e) {
            model.addAttribute("error", "Failed to upload profile picture: " + e.getMessage());
        }
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/profile/toggle2fa")
    public String toggle2FA(HttpServletRequest req, @RequestParam(value = "twoFactorEnabled", required = false) String twoFactorEnabled, Model model) {
        users user = (users) req.getSession().getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        boolean enable2FA = (twoFactorEnabled != null);
        user.setTwoFactorEnabled(enable2FA);
        user.getAuditLogs().add((enable2FA ? "Enabled" : "Disabled") + " 2FA at " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        service.save(user);
        req.getSession().setAttribute("loggedInUser", user);
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/profile/notifications")
    public String updateNotificationPreference(HttpServletRequest req, @RequestParam("notificationPreference") String pref, Model model) {
        users user = (users) req.getSession().getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        user.setNotificationPreference(pref);
        user.getAuditLogs().add("Notification preference set to " + pref + " at " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        service.save(user);
        req.getSession().setAttribute("loggedInUser", user);
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/profile/resetPassword")
    public String resetPassword(HttpServletRequest req, Model model) {
        users user = (users) req.getSession().getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        // For demo: set password to 'changeme' and log action
        user.setPassword("changeme");
        user.getAuditLogs().add("Password reset at " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        service.save(user);
        req.getSession().setAttribute("loggedInUser", user);
        model.addAttribute("user", user);
        model.addAttribute("passwordChangeSuccess", "Password reset to 'changeme'. Please change it after login.");
        return "profile";
    }

    @PostMapping("/profile/deactivate")
    public String deactivateAccount(HttpServletRequest req, Model model) {
        users user = (users) req.getSession().getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        // For demo: mark user as deactivated (could add a field, here just log and remove from session)
        user.getAuditLogs().add("Account deactivated at " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        service.save(user);
        req.getSession().invalidate();
        return "redirect:/login";
    }

    // Robust mapping from movie name to poster filename
    private static final Map<String, String> MOVIE_POSTER_MAP = new HashMap<>();
    static {
        MOVIE_POSTER_MAP.put("deadpool & wolverine", "Deadpool & Wolverine.jpeg");
        MOVIE_POSTER_MAP.put("iron man", "Iron Man.jpg");
        MOVIE_POSTER_MAP.put("the marvels", "The Marvels.jfif");
        MOVIE_POSTER_MAP.put("guardians of the galaxy vol. 3", "Guardians of the Galaxy Vol. 3.jpg");
        MOVIE_POSTER_MAP.put("guardians of the galaxy vol. 2", "Guardians of the Galaxy Vol. 2.jfif");
        MOVIE_POSTER_MAP.put("guardians of the galaxy", "Guardians of the Galaxy.jfif");
        MOVIE_POSTER_MAP.put("avengers: age of ultron", "Avengers Age of Ultron.jpg");
        MOVIE_POSTER_MAP.put("captain america: brave new world", "Captain America Brave New World.jpg");
        MOVIE_POSTER_MAP.put("captain america: the winter soldier", "Captain America The Winter Soldier.jpg");
        MOVIE_POSTER_MAP.put("captain america: civil war", "Captain America Civil War.jpg");
        MOVIE_POSTER_MAP.put("black widow", "Black Widow.jpg");
        MOVIE_POSTER_MAP.put("spider-man: homecoming", "Spider-Man Homecoming.jpg");
        MOVIE_POSTER_MAP.put("black panther", "Black Panther.jpg");
        MOVIE_POSTER_MAP.put("the lord of the rings: the return of the king", "Lord of the Rings.jpg");
        MOVIE_POSTER_MAP.put("the shawshank redemption", "shawshank-redemption.jpg");
        // Updated posters for Mersal, Naai Sekar, Legent (use exact lowercase filenames)
        MOVIE_POSTER_MAP.put("mersal", "mersal.jpg");
        MOVIE_POSTER_MAP.put("naai sekar", "nai sekar.jpg");
        MOVIE_POSTER_MAP.put("legent", "legend.jpg");
        // Add more mappings as needed
    }
    private String getPosterFilenameForMovie(String movieName) {
        if (movieName == null) return "default-movie.png";
        String key = movieName.trim().toLowerCase();
        return MOVIE_POSTER_MAP.getOrDefault(key, "default-movie.png");
    }
}

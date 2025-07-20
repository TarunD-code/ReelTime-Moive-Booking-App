package com.example.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.model.seat_booking_detailes;
import com.example.repo.seatBookingRepo;

//@Controller
//@RequestMapping
public class SeatBookingController {
	@Autowired
	seat_booking_detailes deatiles;
	@Autowired
	seatBookingRepo Service;
	
	@GetMapping("/BookingDTLS")
	public void save() {
		deatiles.setName("Masila");
		deatiles.setDate("22-02-2001");
		deatiles.setEmail("Masila@Gmail.com");
		deatiles.setId(4);
		deatiles.setRupees(50000);
		deatiles.setTime("10:30");
		
	
	}
	
	
}


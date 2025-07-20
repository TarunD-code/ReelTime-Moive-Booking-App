package com.example.Controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.model.seat_booking_detailes;

@Service
public class importent_Methods {


    // Get seat_booking_detailes for same date, same time, same movie
    public List<seat_booking_detailes> getSameBookings(String movie, String date, String time, List<seat_booking_detailes> allBookings) {
        List<seat_booking_detailes> filteredBookings = new ArrayList<>();

        for (seat_booking_detailes booking : allBookings) {
            if (booking.getMovie_Name() != null && booking.getDate() != null && booking.getTime() != null &&
                booking.getMovie_Name().equalsIgnoreCase(movie) &&
                booking.getDate().equalsIgnoreCase(date) &&
                booking.getTime().equalsIgnoreCase(time)) {
                
                filteredBookings.add(booking);
            }
        }

        return filteredBookings;
    }

	public importent_Methods() {
		super();
	}
    
}

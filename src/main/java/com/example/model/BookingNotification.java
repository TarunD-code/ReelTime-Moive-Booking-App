package com.example.model;

import java.io.Serializable;
import java.math.BigInteger;

public class BookingNotification implements Serializable {
    private String movieName;
    private String movieDate;
    private String movieTime;
    private String bookingUserName;
    private String bookingUserEmail;
    private String bookingUserMobile;
    private String seatNumber;
    private String price;
    private int bookingId;

    // Getters and setters
    public String getMovieName() { return movieName; }
    public void setMovieName(String movieName) { this.movieName = movieName; }
    public String getMovieDate() { return movieDate; }
    public void setMovieDate(String movieDate) { this.movieDate = movieDate; }
    public String getMovieTime() { return movieTime; }
    public void setMovieTime(String movieTime) { this.movieTime = movieTime; }
    public String getBookingUserName() { return bookingUserName; }
    public void setBookingUserName(String bookingUserName) { this.bookingUserName = bookingUserName; }
    public String getBookingUserEmail() { return bookingUserEmail; }
    public void setBookingUserEmail(String bookingUserEmail) { this.bookingUserEmail = bookingUserEmail; }
    public String getBookingUserMobile() { return bookingUserMobile; }
    public void setBookingUserMobile(String bookingUserMobile) { this.bookingUserMobile = bookingUserMobile; }
    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }
    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }
    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
} 
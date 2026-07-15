package com.hotel.reservation.service;

import com.hotel.reservation.model.Booking;
import com.hotel.reservation.model.BookingStatus;
import com.hotel.reservation.model.PaymentStatus;
import com.hotel.reservation.model.Room;
import com.hotel.reservation.storage.FileStorageService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingService {
    private final FileStorageService storageService;
    private final RoomService roomService;
    private final String BOOKINGS_FILE = "bookings.json";
    
    private List<Booking> bookings = new ArrayList<>();

    public BookingService(FileStorageService storageService, RoomService roomService) {
        this.storageService = storageService;
        this.roomService = roomService;
    }

    @PostConstruct
    public void init() {
        bookings = storageService.loadData(BOOKINGS_FILE, Booking[].class);
    }

    public void saveBookings() {
        storageService.saveData(BOOKINGS_FILE, bookings);
    }

    public List<Booking> getAllBookings() {
        return bookings;
    }

    public Booking getBookingById(String id) {
        return bookings.stream().filter(b -> b.getBookingId().equals(id)).findFirst().orElse(null);
    }

    public List<Booking> searchBookings(String searchStr) {
        if (searchStr == null || searchStr.trim().isEmpty()) return bookings;
        String lowerStr = searchStr.toLowerCase();
        return bookings.stream()
                .filter(b -> (b.getBookingId() != null && b.getBookingId().toLowerCase().contains(lowerStr)) || 
                             (b.getCustomerName() != null && b.getCustomerName().toLowerCase().contains(lowerStr)))
                .collect(Collectors.toList());
    }

    public Booking createBooking(Booking req) {
        // Validation
        if (req.getCheckInDate() == null || req.getCheckOutDate() == null || !req.getCheckOutDate().isAfter(req.getCheckInDate())) {
            throw new IllegalArgumentException("Check-out date must be after check-in date.");
        }
        
        Room room = roomService.getRoomByNumber(req.getRoomNumber());
        if (room == null || !room.isAvailable()) {
            throw new IllegalArgumentException("Room is not available.");
        }
        if (req.getGuests() > room.getCapacity()) {
            throw new IllegalArgumentException("Guests exceed maximum room capacity.");
        }
        
        // Calculate costs
        int nights = (int) ChronoUnit.DAYS.between(req.getCheckInDate(), req.getCheckOutDate());
        double totalCost = nights * room.getPrice();

        // Create booking object
        Booking booking = new Booking();
        booking.setBookingId(UUID.randomUUID().toString().substring(0, 8).toUpperCase()); // Generate auto ID
        booking.setCustomerName(req.getCustomerName());
        booking.setPhoneNumber(req.getPhoneNumber());
        booking.setEmail(req.getEmail());
        booking.setCheckInDate(req.getCheckInDate());
        booking.setCheckOutDate(req.getCheckOutDate());
        booking.setGuests(req.getGuests());
        booking.setNumberOfNights(nights);
        booking.setRoomNumber(room.getRoomNumber());
        booking.setCategory(room.getCategory());
        booking.setTotalAmount(totalCost);
        booking.setPaymentStatus(PaymentStatus.PENDING);
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        
        // Mark room unavailable
        room.setAvailable(false);
        roomService.updateRoom(room);
        
        bookings.add(0, booking); // Add to the top
        saveBookings();
        return booking;
    }

    public Booking cancelBooking(String bookingId) {
        Booking booking = getBookingById(bookingId);
        if (booking == null) {
            throw new IllegalArgumentException("Booking not found");
        }
        if (booking.getBookingStatus() == BookingStatus.CANCELLED) {
            throw new IllegalArgumentException("Booking is already cancelled");
        }
        
        booking.setBookingStatus(BookingStatus.CANCELLED);
        Room room = roomService.getRoomByNumber(booking.getRoomNumber());
        if (room != null) {
            room.setAvailable(true);
            roomService.updateRoom(room);
        }
        saveBookings();
        return booking;
    }
}

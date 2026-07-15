package com.hotel.reservation.service;

import com.hotel.reservation.model.Booking;
import com.hotel.reservation.model.BookingStatus;
import com.hotel.reservation.model.DashboardStats;
import com.hotel.reservation.model.PaymentStatus;
import com.hotel.reservation.model.Room;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {
    private final RoomService roomService;
    private final BookingService bookingService;

    public DashboardService(RoomService roomService, BookingService bookingService) {
        this.roomService = roomService;
        this.bookingService = bookingService;
    }

    public DashboardStats getDashboardStats() {
        List<Room> rooms = roomService.getAllRooms();
        List<Booking> bookings = bookingService.getAllBookings();

        int totalRooms = rooms.size();
        int availableRooms = (int) rooms.stream().filter(Room::isAvailable).count();
        int bookedRooms = totalRooms - availableRooms;
        
        int totalBookings = bookings.size();
        
        double totalRevenue = bookings.stream()
                .filter(b -> b.getPaymentStatus() == PaymentStatus.PAID && b.getBookingStatus() != BookingStatus.CANCELLED)
                .mapToDouble(Booking::getTotalAmount)
                .sum();

        return new DashboardStats(totalRooms, availableRooms, bookedRooms, totalBookings, totalRevenue);
    }
    
    // extra report methods
    public long getCancelledBookingsCount() {
        return bookingService.getAllBookings().stream()
                .filter(b -> b.getBookingStatus() == BookingStatus.CANCELLED)
                .count();
    }
    
    public long getCompletedBookingsCount() {
        return bookingService.getAllBookings().stream()
                .filter(b -> b.getBookingStatus() == BookingStatus.COMPLETED)
                .count();
    }
}

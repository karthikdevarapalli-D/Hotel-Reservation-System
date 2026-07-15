package com.hotel.reservation.service;

import com.hotel.reservation.model.Booking;
import com.hotel.reservation.model.Payment;
import com.hotel.reservation.model.PaymentStatus;
import com.hotel.reservation.storage.FileStorageService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {
    private final FileStorageService storageService;
    private final BookingService bookingService;
    private final String PAYMENTS_FILE = "payments.json";

    private List<Payment> payments = new ArrayList<>();

    public PaymentService(FileStorageService storageService, BookingService bookingService) {
        this.storageService = storageService;
        this.bookingService = bookingService;
    }

    @PostConstruct
    public void init() {
        payments = storageService.loadData(PAYMENTS_FILE, Payment[].class);
    }

    private void savePayments() {
        storageService.saveData(PAYMENTS_FILE, payments);
    }

    public Payment processPayment(Payment paymentReq) {
        Booking booking = bookingService.getBookingById(paymentReq.getBookingId());
        if (booking == null) {
            throw new IllegalArgumentException("Booking not found");
        }

        Payment payment = new Payment();
        payment.setPaymentId("PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        payment.setBookingId(booking.getBookingId());
        payment.setAmount(booking.getTotalAmount());
        payment.setPaymentMethod(paymentReq.getPaymentMethod());
        payment.setPaymentStatus(PaymentStatus.PAID);
        payment.setPaymentDate(LocalDateTime.now());

        payments.add(payment);
        savePayments();

        // Update booking Payment status
        booking.setPaymentStatus(PaymentStatus.PAID);
        bookingService.saveBookings();

        return payment;
    }
}

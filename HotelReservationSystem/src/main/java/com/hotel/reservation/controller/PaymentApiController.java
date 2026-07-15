package com.hotel.reservation.controller;

import com.hotel.reservation.model.Payment;
import com.hotel.reservation.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentApiController {

    private final PaymentService paymentService;

    public PaymentApiController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<?> processPayment(@RequestBody Payment paymentReq) {
        try {
            Payment processed = paymentService.processPayment(paymentReq);
            return ResponseEntity.ok(processed);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

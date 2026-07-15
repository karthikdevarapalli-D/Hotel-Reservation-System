package com.hotel.reservation.controller;

import com.hotel.reservation.model.DashboardStats;
import com.hotel.reservation.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DashboardApiController {

    private final DashboardService dashboardService;

    public DashboardApiController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    public DashboardStats getDashboard() {
        return dashboardService.getDashboardStats();
    }

    @GetMapping("/reports")
    public Map<String, Object> getReports() {
        Map<String, Object> reports = new HashMap<>();
        DashboardStats stats = dashboardService.getDashboardStats();
        
        reports.put("totalRevenue", stats.getTotalRevenue());
        reports.put("availableRooms", stats.getAvailableRooms());
        reports.put("occupiedRooms", stats.getBookedRooms());
        reports.put("cancelledBookings", dashboardService.getCancelledBookingsCount());
        reports.put("completedBookings", dashboardService.getCompletedBookingsCount());
        
        return reports;
    }
}

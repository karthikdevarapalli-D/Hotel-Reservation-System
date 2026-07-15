package com.hotel.reservation.model;

public class DashboardStats {
    private int totalRooms;
    private int availableRooms;
    private int bookedRooms;
    private int totalBookings;
    private double totalRevenue;

    public DashboardStats() {}

    public DashboardStats(int totalRooms, int availableRooms, int bookedRooms, int totalBookings, double totalRevenue) {
        this.totalRooms = totalRooms;
        this.availableRooms = availableRooms;
        this.bookedRooms = bookedRooms;
        this.totalBookings = totalBookings;
        this.totalRevenue = totalRevenue;
    }

    public int getTotalRooms() {
        return totalRooms;
    }

    public void setTotalRooms(int totalRooms) {
        this.totalRooms = totalRooms;
    }

    public int getAvailableRooms() {
        return availableRooms;
    }

    public void setAvailableRooms(int availableRooms) {
        this.availableRooms = availableRooms;
    }

    public int getBookedRooms() {
        return bookedRooms;
    }

    public void setBookedRooms(int bookedRooms) {
        this.bookedRooms = bookedRooms;
    }

    public int getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(int totalBookings) {
        this.totalBookings = totalBookings;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}

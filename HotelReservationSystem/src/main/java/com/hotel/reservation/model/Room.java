package com.hotel.reservation.model;

public class Room {
    private String roomNumber;
    private RoomCategory category;
    private double price;
    private int capacity;
    private boolean available;
    private String image;

    public Room() {
    }

    public Room(String roomNumber, RoomCategory category, double price, int capacity, boolean available, String image) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.capacity = capacity;
        this.available = available;
        this.image = image;
    }

    // Getters and Setters
    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public RoomCategory getCategory() {
        return category;
    }

    public void setCategory(RoomCategory category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

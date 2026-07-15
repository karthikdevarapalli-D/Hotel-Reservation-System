package com.hotel.reservation.service;

import com.hotel.reservation.model.Room;
import com.hotel.reservation.model.RoomCategory;
import com.hotel.reservation.storage.FileStorageService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private final FileStorageService storageService;
    private final String ROOMS_FILE = "rooms.json";
    
    private List<Room> rooms = new ArrayList<>();

    public RoomService(FileStorageService storageService) {
        this.storageService = storageService;
    }

    @PostConstruct
    public void init() {
        rooms = storageService.loadData(ROOMS_FILE, Room[].class);
        if (rooms.isEmpty()) {
            preloadRooms();
            saveRooms();
        }
    }

    private void preloadRooms() {
        for (int i = 101; i <= 110; i++) {
            rooms.add(new Room(String.valueOf(i), RoomCategory.STANDARD, 100.0, 2, true, "/images/standard.jpg"));
        }
        for (int i = 201; i <= 205; i++) {
            rooms.add(new Room(String.valueOf(i), RoomCategory.DELUXE, 200.0, 3, true, "/images/deluxe.jpg"));
        }
        for (int i = 301; i <= 305; i++) {
            rooms.add(new Room(String.valueOf(i), RoomCategory.SUITE, 500.0, 4, true, "/images/suite.jpg"));
        }
    }

    private void saveRooms() {
        storageService.saveData(ROOMS_FILE, rooms);
    }

    public List<Room> getAllRooms() {
        return rooms;
    }

    public Room getRoomByNumber(String roomNumber) {
        return rooms.stream().filter(r -> r.getRoomNumber().equals(roomNumber)).findFirst().orElse(null);
    }

    public void updateRoom(Room room) {
        int index = -1;
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getRoomNumber().equals(room.getRoomNumber())) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            rooms.set(index, room);
            saveRooms();
        }
    }

    public List<Room> searchRooms(RoomCategory category, Double maxPrice, Integer capacity, Boolean availableOnly) {
        return rooms.stream()
                .filter(r -> (category == null || r.getCategory() == category))
                .filter(r -> (maxPrice == null || r.getPrice() <= maxPrice))
                .filter(r -> (capacity == null || r.getCapacity() >= capacity))
                .filter(r -> (availableOnly == null || !availableOnly || r.isAvailable()))
                .collect(Collectors.toList());
    }
}

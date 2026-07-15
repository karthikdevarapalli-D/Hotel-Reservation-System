package com.hotel.reservation.controller;

import com.hotel.reservation.model.Room;
import com.hotel.reservation.model.RoomCategory;
import com.hotel.reservation.service.RoomService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomApiController {

    private final RoomService roomService;

    public RoomApiController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/search")
    public List<Room> searchRooms(
            @RequestParam(required = false) RoomCategory category,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Integer capacity,
            @RequestParam(required = false) Boolean availableOnly) {
        return roomService.searchRooms(category, maxPrice, capacity, availableOnly);
    }
}

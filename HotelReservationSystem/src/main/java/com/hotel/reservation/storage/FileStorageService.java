package com.hotel.reservation.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FileStorageService {

    private final ObjectMapper mapper;

    public FileStorageService() {
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule()); // Support Java 8 Time API
    }

    public <T> List<T> loadData(String filename, Class<T[]> arrayClass) {
        File file = new File(filename);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try {
            T[] array = mapper.readValue(file, arrayClass);
            return new ArrayList<>(Arrays.asList(array));
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public <T> void saveData(String filename, List<T> data) {
        try {
            File file = new File(filename);
            mapper.writeValue(file, data);
        } catch (IOException e) {
            e.printStackTrace();
            // log the error
        }
    }
}

package com.example.storage.controller;

import com.example.storage.entity.StoredData;
import com.example.storage.repository.StoredDataRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/files")
public class StorageController {

    private final StoredDataRepository storedDataRepository;

    private final Path storageLocation = Paths.get("uploads");

    public StorageController(StoredDataRepository storedDataRepository) {
        this.storedDataRepository = storedDataRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file) throws IOException {

        Files.createDirectories(storageLocation);

        Path filePath = storageLocation.resolve(file.getOriginalFilename());
        Files.write(filePath, file.getBytes());

        return ResponseEntity.ok(
                "File uploaded successfully: " + file.getOriginalFilename()
        );
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<byte[]> downloadFile(
            @PathVariable String filename) throws IOException {

        Path filePath = storageLocation.resolve(filename);

        if (!Files.exists(filePath)) {
            return ResponseEntity.notFound().build();
        }

        byte[] fileContent = Files.readAllBytes(filePath);

        return ResponseEntity.ok(fileContent);
    }

    @PostMapping("/data")
    public ResponseEntity<StoredData> saveData(
            @RequestBody StoredData data) {

        StoredData savedData = storedDataRepository.save(data);

        return ResponseEntity.ok(savedData);
    }
}
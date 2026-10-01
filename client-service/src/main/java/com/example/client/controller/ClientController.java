package com.example.client.controller;

import com.example.client.feign.StorageClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final StorageClient storageClient;

    public ClientController(StorageClient storageClient) {
        this.storageClient = storageClient;
    }

    @GetMapping("/download/{filename}")
    public byte[] downloadFile(@PathVariable String filename) {
        return storageClient.downloadFile(filename);
    }

    @PostMapping("/data")
    public StorageClient.StoredDataResponse saveData(
            @RequestBody StorageClient.StoredDataRequest data) {

        return storageClient.saveData(data);
    }
}
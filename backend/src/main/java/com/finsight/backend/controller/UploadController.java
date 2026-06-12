package com.finsight.backend.controller;

import com.finsight.backend.service.CSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class UploadController {

    @Autowired
    private CSVService csvService;

    @PostMapping("/upload-statement")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file
    ) {

        csvService.saveTransactions(file);

        return ResponseEntity.ok(
                "Statement uploaded successfully"
        );
    }
}
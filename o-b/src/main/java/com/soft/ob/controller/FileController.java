package com.soft.ob.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/file")
@CrossOrigin(origins = "*")
public class FileController {
    
    private final String UPLOAD_DIR = "uploads/";
    
    public FileController() {
        // Create upload directory if it doesn't exist
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
    }
    
    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadFile(@RequestParam("file") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (file.isEmpty()) {
                response.put("error", "File is empty");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            
            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
            String uniqueFilename = UUID.randomUUID().toString() + fileExtension;
            
            // Define file path
            Path filePath = Paths.get(UPLOAD_DIR + uniqueFilename);
            
            // Save file
            Files.write(filePath, file.getBytes());
            
            // Prepare response
            response.put("success", true);
            response.put("filename", uniqueFilename);
            response.put("originalFilename", originalFilename);
            response.put("size", file.getSize());
            response.put("message", "File uploaded successfully");
            
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            response.put("success", false);
            response.put("error", "Failed to upload file: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/upload/multiple")
    public ResponseEntity<Map<String, Object>> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> uploadResults = new HashMap<>();
        
        try {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String originalFilename = file.getOriginalFilename();
                    String fileExtension = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
                    String uniqueFilename = UUID.randomUUID().toString() + fileExtension;
                    
                    Path filePath = Paths.get(UPLOAD_DIR + uniqueFilename);
                    Files.write(filePath, file.getBytes());
                    
                    uploadResults.put(uniqueFilename, Map.of(
                        "originalFilename", originalFilename,
                        "size", file.getSize()
                    ));
                }
            }
            
            response.put("success", true);
            response.put("files", uploadResults);
            response.put("message", "Files uploaded successfully");
            
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            response.put("success", false);
            response.put("error", "Failed to upload files: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/delete/{filename}")
    public ResponseEntity<Map<String, Object>> deleteFile(@PathVariable String filename) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Path filePath = Paths.get(UPLOAD_DIR + filename);
            
            if (Files.exists(filePath)) {
                Files.delete(filePath);
                response.put("success", true);
                response.put("message", "File deleted successfully");
            } else {
                response.put("success", false);
                response.put("error", "File not found");
            }
            
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            response.put("success", false);
            response.put("error", "Failed to delete file: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
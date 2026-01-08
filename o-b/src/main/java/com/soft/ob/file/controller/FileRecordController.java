package com.soft.ob.file.controller;

import com.soft.ob.file.entity.FileRecord;
import com.soft.ob.file.service.FileRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/file")
@CrossOrigin(origins = "*")
public class FileRecordController {

    @Autowired
    private FileRecordService fileRecordService;

    private static final String UPLOAD_DIR = "uploads/";

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadFile(
            @RequestParam MultipartFile file,
            @RequestParam Long uploaderId,
            @RequestParam(required = false) String entityType,
            @RequestParam(required = false) Long entityId) {
        
        Map<String, Object> response = new HashMap<>();
        try {
            String originalFilename = file.getOriginalFilename();
            String fileType = file.getContentType();
            Long fileSize = file.getSize();
            
            // Generate unique filename
            String filename = UUID.randomUUID() + "_" + originalFilename;
            String filePath = UPLOAD_DIR + filename;
            
            // Save file to disk
            File uploadDirectory = new File(UPLOAD_DIR);
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs();
            }
            file.transferTo(new File(filePath));
            
            // Create file record
            FileRecord fileRecord = new FileRecord();
            fileRecord.setFilename(filename);
            fileRecord.setOriginalFilename(originalFilename);
            fileRecord.setFileType(fileType);
            fileRecord.setFileSize(fileSize);
            fileRecord.setFilePath(filePath);
            fileRecord.setUrl("/files/" + filename);
            fileRecord.setUploaderId(uploaderId);
            fileRecord.setEntityType(entityType);
            fileRecord.setEntityId(entityId);
            
            FileRecord createdRecord = fileRecordService.createFileRecord(fileRecord);
            
            response.put("code", 201);
            response.put("message", "File uploaded successfully");
            response.put("data", createdRecord);
            return ResponseEntity.status(201).body(response);
        } catch (IOException e) {
            response.put("code", 500);
            response.put("message", "File upload failed: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getFileRecordById(@PathVariable Long id) {
        FileRecord fileRecord = fileRecordService.getFileRecordById(id);
        Map<String, Object> response = new HashMap<>();

        if (fileRecord != null) {
            response.put("code", 200);
            response.put("message", "Success");
            response.put("data", fileRecord);
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "File record not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getAllFileRecords() {
        List<FileRecord> records = fileRecordService.getAllFileRecords();
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", "Success");
        response.put("data", records);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/uploader/{uploaderId}")
    public ResponseEntity<Map<String, Object>> getFileRecordsByUploaderId(@PathVariable Long uploaderId) {
        List<FileRecord> records = fileRecordService.getFileRecordsByUploaderId(uploaderId);
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", "Success");
        response.put("data", records);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/entity/{entityType}/{entityId}")
    public ResponseEntity<Map<String, Object>> getFileRecordsByEntity(@PathVariable String entityType, @PathVariable Long entityId) {
        List<FileRecord> records = fileRecordService.getFileRecordsByEntity(entityType, entityId);
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", "Success");
        response.put("data", records);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/type/{fileType}")
    public ResponseEntity<Map<String, Object>> getFileRecordsByFileType(@PathVariable String fileType) {
        List<FileRecord> records = fileRecordService.getFileRecordsByFileType(fileType);
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", "Success");
        response.put("data", records);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateFileRecord(@PathVariable Long id, @RequestBody FileRecord fileRecord) {
        fileRecord.setId(id);
        FileRecord updatedRecord = fileRecordService.updateFileRecord(fileRecord);
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", "File record updated successfully");
        response.put("data", updatedRecord);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteFileRecord(@PathVariable Long id) {
        FileRecord fileRecord = fileRecordService.getFileRecordById(id);
        Map<String, Object> response = new HashMap<>();

        if (fileRecord != null) {
            // Delete file from disk
            File file = new File(fileRecord.getFilePath());
            if (file.exists()) {
                file.delete();
            }
            
            // Delete record from database
            boolean success = fileRecordService.deleteFileRecord(id);
            if (success) {
                response.put("code", 200);
                response.put("message", "File record deleted successfully");
                return ResponseEntity.ok(response);
            }
        }
        
        response.put("code", 404);
        response.put("message", "File record not found");
        return ResponseEntity.status(404).body(response);
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<Map<String, Object>> activateFileRecord(@PathVariable Long id) {
        boolean success = fileRecordService.activateFileRecord(id);
        Map<String, Object> response = new HashMap<>();

        if (success) {
            response.put("code", 200);
            response.put("message", "File record activated successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "File record not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Map<String, Object>> deactivateFileRecord(@PathVariable Long id) {
        boolean success = fileRecordService.deactivateFileRecord(id);
        Map<String, Object> response = new HashMap<>();

        if (success) {
            response.put("code", 200);
            response.put("message", "File record deactivated successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "File record not found");
            return ResponseEntity.status(404).body(response);
        }
    }
}

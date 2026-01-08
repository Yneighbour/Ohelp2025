package com.soft.ob.file.service;

import com.soft.ob.file.entity.FileRecord;
import com.soft.ob.file.mapper.FileRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FileRecordService {

    @Autowired
    private FileRecordMapper fileRecordMapper;

    public FileRecord createFileRecord(FileRecord fileRecord) {
        fileRecord.setCreatedAt(LocalDateTime.now());
        fileRecord.setUpdatedAt(LocalDateTime.now());
        fileRecord.setIsActive(true);
        fileRecordMapper.insert(fileRecord);
        return fileRecord;
    }

    public FileRecord getFileRecordById(Long id) {
        return fileRecordMapper.selectById(id);
    }

    public List<FileRecord> getAllFileRecords() {
        return fileRecordMapper.selectAll();
    }

    public List<FileRecord> getFileRecordsByUploaderId(Long uploaderId) {
        return fileRecordMapper.selectByUploaderId(uploaderId);
    }

    public List<FileRecord> getFileRecordsByEntity(String entityType, Long entityId) {
        return fileRecordMapper.selectByEntity(entityType, entityId);
    }

    public List<FileRecord> getFileRecordsByFileType(String fileType) {
        return fileRecordMapper.selectByFileType(fileType);
    }

    public FileRecord updateFileRecord(FileRecord fileRecord) {
        fileRecord.setUpdatedAt(LocalDateTime.now());
        fileRecordMapper.update(fileRecord);
        return fileRecord;
    }

    public boolean deleteFileRecord(Long id) {
        return fileRecordMapper.deleteById(id) > 0;
    }

    public boolean activateFileRecord(Long id) {
        FileRecord fileRecord = fileRecordMapper.selectById(id);
        if (fileRecord != null) {
            fileRecord.setIsActive(true);
            fileRecord.setUpdatedAt(LocalDateTime.now());
            fileRecordMapper.update(fileRecord);
            return true;
        }
        return false;
    }

    public boolean deactivateFileRecord(Long id) {
        FileRecord fileRecord = fileRecordMapper.selectById(id);
        if (fileRecord != null) {
            fileRecord.setIsActive(false);
            fileRecord.setUpdatedAt(LocalDateTime.now());
            fileRecordMapper.update(fileRecord);
            return true;
        }
        return false;
    }
}

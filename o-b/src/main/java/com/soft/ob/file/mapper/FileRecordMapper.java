package com.soft.ob.file.mapper;

import com.soft.ob.file.entity.FileRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileRecordMapper {
    @Insert("INSERT INTO file_record (filename, original_filename, file_type, file_size, file_path, url, uploader_id, entity_type, entity_id, description, is_active, created_at, updated_at) VALUES (#{filename}, #{originalFilename}, #{fileType}, #{fileSize}, #{filePath}, #{url}, #{uploaderId}, #{entityType}, #{entityId}, #{description}, #{isActive}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(FileRecord fileRecord);

    @Select("SELECT * FROM file_record WHERE id = #{id}")
    FileRecord selectById(Long id);

    @Select("SELECT * FROM file_record")
    List<FileRecord> selectAll();

    @Select("SELECT * FROM file_record WHERE uploader_id = #{uploaderId}")
    List<FileRecord> selectByUploaderId(Long uploaderId);

    @Select("SELECT * FROM file_record WHERE entity_type = #{entityType} AND entity_id = #{entityId}")
    List<FileRecord> selectByEntity(String entityType, Long entityId);

    @Select("SELECT * FROM file_record WHERE file_type = #{fileType}")
    List<FileRecord> selectByFileType(String fileType);

    @Update("UPDATE file_record SET filename = #{filename}, original_filename = #{originalFilename}, file_type = #{fileType}, file_size = #{fileSize}, file_path = #{filePath}, url = #{url}, uploader_id = #{uploaderId}, entity_type = #{entityType}, entity_id = #{entityId}, description = #{description}, is_active = #{isActive}, updated_at = #{updatedAt} WHERE id = #{id}")
    int update(FileRecord fileRecord);

    @Delete("DELETE FROM file_record WHERE id = #{id}")
    int deleteById(Long id);
}

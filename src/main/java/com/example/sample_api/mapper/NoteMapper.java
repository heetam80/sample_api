package com.example.sample_api.mapper;

import com.example.sample_api.dto.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT id, title, content, created_at FROM notes ORDER BY created_at DESC")
    List<Note> findAll();

    @Insert("INSERT INTO notes (title, content) VALUES (#{title}, #{content})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(Note note);

    @Select("SELECT id, title, content, created_at FROM notes WHERE id = #{id}")
    Note findById(long id);

    @Delete("delete from notes where id = #{id}")
    int delete(long id);

    @Update("update notes set title = #{title}, content = #{content} where id = #{id}")
    int update(Note note);
}

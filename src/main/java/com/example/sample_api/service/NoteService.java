package com.example.sample_api.service;

import com.example.sample_api.dto.Note;
import com.example.sample_api.exception.NoteArgumentNotValidException;
import com.example.sample_api.exception.NoteNotFoundException;
import com.example.sample_api.repository.NoteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Service
public class NoteService {
    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> findAll(){
        return noteRepository.findAll();
    }

    public Note save(Note note){
        if (note.getTitle() == null || note.getTitle().isEmpty()){
            throw new NoteArgumentNotValidException("Title is empty");
        }

        noteRepository.insertNote(note);
        return note;
    }


    public Note findById(long id){
        Note note = noteRepository.findById(id);

        if(note == null){
            throw new NoteNotFoundException(id);
        }

        return note;
    }

    public boolean delete(long id){
        if(!noteRepository.delete(id)){
            throw new NoteNotFoundException(id);
        }
        return true;
    }

    @Transactional
    public Note update(Note note, long id){
//        titleがnullの場合
        if (note.getTitle() == null || note.getTitle().isEmpty()){
            throw new NoteArgumentNotValidException("Title is empty");
        }
        if(note.getContent() == null || note.getContent().isEmpty()){
            throw new NoteArgumentNotValidException("Content is empty");
        }
        //        指定されたIDが存在しない場合のバリデーション
        Note targetNote = noteRepository.findById(id);
        if(targetNote == null){
            throw new NoteNotFoundException(id);
        }
        note.setId(id);
        note.setCreatedAt(targetNote.getCreatedAt());
        return noteRepository.update(note);
    }


}

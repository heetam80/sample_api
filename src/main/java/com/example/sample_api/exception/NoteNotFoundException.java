package com.example.sample_api.exception;

public class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException(long id) {
        super("指定されたIdのノートが見つかりません：" + id);
    }
}

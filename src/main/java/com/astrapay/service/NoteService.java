package com.astrapay.service;
import com.astrapay.dto.req.NoteDto;
import com.astrapay.entity.Note;

import java.util.List;

public interface NoteService {
    List<Note> getAllNotes();
    Note createNote(NoteDto request);
    Note updateNote(String id, NoteDto updateNote);
    void deleteNote(String id);
}
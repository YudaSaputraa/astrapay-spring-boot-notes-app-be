package com.astrapay.service.impl;


import com.astrapay.dto.req.NoteDto;
import com.astrapay.entity.Note;
import com.astrapay.service.NoteService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class NoteServiceImpl implements NoteService {

    private final List<Note> notes = new ArrayList<>();

    @Override
    public List<Note> getAllNotes() {
        return notes;
    }

    @Override
    public Note createNote(NoteDto request) {
        Note note = new Note();
        note.setId(UUID.randomUUID().toString());
        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
        note.setDone(false);
        note.setCreatedAt(LocalDateTime.now());
        notes.add(note);
        return note;
    }

    @Override
    public Note updateNote(String id, NoteDto updateNote) {
        return notes.stream()
                .filter(note -> note.getId().equals(id))
                .findFirst()
                .map(note -> {
                    note.setTitle(updateNote.getTitle());
                    note.setContent(updateNote.getContent());
                    note.setDone(updateNote.isDone());
                    return note;
                })
                .orElseThrow(()-> new RuntimeException("Note not found!"));
    }

    @Override
    public void deleteNote(String id) {
        Note existingNote = notes.stream()
                .filter(note -> note.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Note with ID " + id + " not found!"));
        notes.remove(existingNote);
    }
}

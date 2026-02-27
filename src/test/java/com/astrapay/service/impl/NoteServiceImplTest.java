package com.astrapay.service.impl;

import com.astrapay.dto.req.NoteDto;
import com.astrapay.entity.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NoteServiceImplTest {
    @InjectMocks
    private NoteServiceImpl noteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllNotes() {
        NoteDto noteDto1 = new NoteDto();
        noteDto1.setTitle("Note 1");
        noteDto1.setContent("Content 1");
        noteService.createNote(noteDto1);

        NoteDto noteDto2 = new NoteDto();
        noteDto2.setTitle("Note 2");
        noteDto2.setContent("Content 2");
        noteService.createNote(noteDto2);

        List<Note> result = noteService.getAllNotes();
        assertEquals(2, result.size());
    }

    @Test
    void testGetAllNotes_Empty() {
        List<Note> result = noteService.getAllNotes();
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
    }

    @Test
    void testCreateNote_Success() {
        NoteDto noteDto = new NoteDto();
        noteDto.setTitle("tess title note");
        noteDto.setContent("tess content note");

        Note result = noteService.createNote(noteDto);
        assertNotNull(result.getId());
        assertEquals("tess title note", result.getTitle());
        assertFalse(result.isDone());
        assertEquals(1, noteService.getAllNotes().size());

    }

    @Test
    void testUpdateNote_Success() {
        NoteDto createDto = new NoteDto();
        createDto.setTitle("Judul Lama");
        createDto.setContent("Konten Lama");
        Note created = noteService.createNote(createDto);

        NoteDto updateDto = new NoteDto();
        updateDto.setTitle("Judul Baru");
        updateDto.setContent("Konten Baru");
        updateDto.setDone(true);

        Note updated = noteService.updateNote(created.getId(), updateDto);

        assertEquals("Judul Baru", updated.getTitle());
        assertEquals("Konten Baru", updated.getContent());
        assertTrue(updated.isDone());
        assertEquals(created.getId(), updated.getId());
    }

    @Test
    void testUpdateNote_NotFound() {
        NoteDto noteDto = new NoteDto();
        noteDto.setTitle("tes update");
        Exception exception = assertThrows(RuntimeException.class, () -> {
            noteService.updateNote("random-id", noteDto);
        });
        assertTrue(exception.getMessage().contains("Note not found"));
    }

    @Test
    void testDeleteNote_Success() {
        NoteDto noteDto = new NoteDto();
        noteDto.setTitle("tess delete");
        noteDto.setContent("delete me");

        Note result = noteService.createNote(noteDto);
        noteService.deleteNote(result.getId());
        assertEquals(0, noteService.getAllNotes().size());

    }

    @Test
    void testDeleteNote_NotFound() {
        String randomId = "random-id";

        Exception exception = assertThrows(RuntimeException.class, () -> {
            noteService.deleteNote(randomId);
        });

        assertTrue(exception.getMessage().contains("Note with ID " + randomId + " not found"));
    }
}
package com.astrapay.controller;

import com.astrapay.dto.req.NoteDto;
import com.astrapay.entity.Note;
import com.astrapay.service.NoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NoteController.class)
class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    void list() throws Exception {
        Mockito.when(noteService.getAllNotes()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/notes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("T"))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void create() throws  Exception {
        NoteDto request = new NoteDto();
        request.setTitle("Test Title");
        request.setContent("Test Content");

        Note mockSavedNote = new Note("1", "Test Title", "Test Content",false, LocalDateTime.now());

        Mockito.when(noteService.createNote(Mockito.any(NoteDto.class))).thenReturn(mockSavedNote);

        mockMvc.perform(post("/api/v1/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Created Successfully"))
                .andExpect(jsonPath("$.data.title").value("Test Title"));
    }

    @Test
    void testCreateNote_ValidationError_Returns400() throws Exception {
        NoteDto invalidRequest = new NoteDto();
        invalidRequest.setTitle("ab");
        invalidRequest.setContent("");

        mockMvc.perform(post("/api/v1/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))

                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("F"))
                .andExpect(jsonPath("$.message").value("Validation Error"))

                .andExpect(jsonPath("$.error.title").exists())
                .andExpect(jsonPath("$.error.content").exists());
    }

    @Test
    void create_InvalidData_Returns400() throws Exception {
        NoteDto invalidRequest = new NoteDto();
        invalidRequest.setTitle("a");
        invalidRequest.setContent("");

        mockMvc.perform(post("/api/v1/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("F"))
                .andExpect(jsonPath("$.message").value("Validation Error"))
                .andExpect(jsonPath("$.error.title").exists())
                .andExpect(jsonPath("$.error.content").exists());
    }

    @Test
    void update_Success() throws Exception {
        String noteId = "123";
        NoteDto request = new NoteDto();
        request.setTitle("Judul Baru");
        request.setContent("Konten Baru");
        request.setDone(true);

        Note mockUpdatedNote = new Note(noteId, "Judul Baru", "Konten Baru",true,  LocalDateTime.now());
        Mockito.when(noteService.updateNote(Mockito.eq(noteId), Mockito.any(NoteDto.class)))
                .thenReturn(mockUpdatedNote);

        mockMvc.perform(put("/api/v1/notes/" + noteId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("T"))
                .andExpect(jsonPath("$.message").value("Note updated successfully"))
                .andExpect(jsonPath("$.data.title").value("Judul Baru"));
    }


    @Test
    void delete_Success() throws Exception {
        Mockito.doNothing().when(noteService).deleteNote("1");

        mockMvc.perform(delete("/api/v1/notes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Note deleted successfully"));
    }


    @Test
    void delete_NotFound_Returns404() throws Exception {
        String nonExistentId = "999";
        NoteDto request = new NoteDto();
        request.setTitle("Valid Title");
        request.setContent("Valid Content");

        Mockito.when(noteService.updateNote(Mockito.eq(nonExistentId), Mockito.any(NoteDto.class)))
                .thenThrow(new RuntimeException("Note not found!"));

        mockMvc.perform(put("/api/v1/notes/" + nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("F"))
                .andExpect(jsonPath("$.error").value("RESOURCE_ERROR"));
    }
}


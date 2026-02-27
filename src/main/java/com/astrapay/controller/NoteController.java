package com.astrapay.controller;


import com.astrapay.dto.req.NoteDto;
import com.astrapay.dto.res.BaseResponse;
import com.astrapay.entity.Note;
import com.astrapay.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
@CrossOrigin(origins ="*")
public class NoteController {

    private final NoteService noteService;
    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<Note>>> list(){
        return ResponseEntity.ok(BaseResponse.ok(noteService.getAllNotes(), "Notes retrieved successfully"));
    }

    @PostMapping
    public ResponseEntity<BaseResponse<Note>> create(@Valid @RequestBody NoteDto request){
        Note createNote = noteService.createNote(request);
        return new ResponseEntity<>(BaseResponse.created(createNote), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<Note>> update(@PathVariable String id, @Valid @RequestBody NoteDto request){
        Note updateNote = noteService.updateNote(id, request);
        return ResponseEntity.ok(BaseResponse.ok(updateNote, "Note updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<String>> delete(@PathVariable String id) {
        noteService.deleteNote(id);
        return ResponseEntity.ok(BaseResponse.ok(null,"Note deleted successfully"));
    }
}

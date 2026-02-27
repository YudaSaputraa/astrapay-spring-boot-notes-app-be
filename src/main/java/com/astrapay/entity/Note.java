package com.astrapay.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Note {
    private String id;
    private String title;
    private String content;
    private boolean isDone;
    private LocalDateTime createdAt;

    public Note() {
    }
    public Note(String id, String title, String content, boolean isDone,  LocalDateTime createdAt){
        this.id = id;
        this.title = title;
        this.content = content;
        this.isDone = isDone;
        this.createdAt = createdAt;
    }

}
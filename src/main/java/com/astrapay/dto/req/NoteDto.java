package com.astrapay.dto.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class NoteDto {
    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 50, message = "Title must be between 3-50 characters")
    private String title;

    @NotBlank(message = "Content cannot be empty")
    private String content;

    private boolean isDone;

}
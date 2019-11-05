package com.simpleproject.UserApi.controller;

import com.simpleproject.UserApi.dto.NoteDto;
import com.simpleproject.UserApi.entity.Note;
import com.simpleproject.UserApi.entity.User;
import com.simpleproject.UserApi.exception.NotValidParamException;
import com.simpleproject.UserApi.service.NoteService;
import com.simpleproject.UserApi.service.UserService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
public class NoteController {

    private NoteService noteService;
    private UserService userService;
    private static final String  INVALID_NOTE_REQUEST= "invalid note request";

    /**
     * Get all UsersDto
     * @return list of tabs dto
     */
    @RequestMapping("/notes")
    @GetMapping(produces = "application/json")
    public List<NoteDto> getNotes(){
        return noteService.getAll().stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Add new note in db
     * @param noteDto
     * @return new tab or http "bad request" if dto is not valid
     */
    @PostMapping(value = "users/{userId}/notes", produces = "application/json")
    public NoteDto addNote(@PathVariable(value = "userId") Long userId, @Valid @RequestBody NoteDto noteDto){
        try {
            User user = userService.getById(userId);
            Note note = convertToEntity(noteDto);
            note.setUser(user);
            note = noteService.save(note);
            return convertToDto(note);
        } catch (NotValidParamException e){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, INVALID_NOTE_REQUEST);
        } catch (NotFoundException e){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, userService.USER_NOT_FOUND);
        }
    }

    /**
     * Update note param
     * @param id noteDto
     * @param noteDto
     * @return updated note or http "bad request" if dto is not valid, or http "not found" if id is wrong
     */
    @PutMapping(value = "users/{userId}/notes/{id}", produces = "application/json")
    public NoteDto putNote(@PathVariable(value = "userId") Long userId, @PathVariable(value = "id") Long id, @Valid @RequestBody NoteDto noteDto){
        try {
            User user = userService.getById(userId);
            Note note = convertToEntity(noteDto);
            note.setUser(user);
            note = noteService.update(id, note);
            return convertToDto(note);
        }catch (NotFoundException e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, noteService.NOTE_NOT_FOUND + "or" +  userService.USER_NOT_FOUND);
        } catch (NotValidParamException e){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, INVALID_NOTE_REQUEST);
        }
    }

    /**
     * Delete note by id
     * @param id
     * @return map with operation results or http "not found" if id is wrong
     */
    @DeleteMapping("{id}")
    public Map<String, Boolean> deleteNote(@PathVariable Long id){
        try {
            noteService.delete(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, noteService.NOTE_NOT_FOUND);
        }
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    /**
     * convert Note to dto
     * @param note
     * @return NoteDto
     */
    private NoteDto convertToDto(Note note) {
        NoteDto noteDto = new NoteDto();
        noteDto.setId(note.getId());
        noteDto.setDescription(note.getDescription());
        noteDto.setTitle(note.getTitle());
        return noteDto;
    }

    /**
     * Convert dto to Note
     * @param noteDto
     * @return Note
     */
    private Note convertToEntity(NoteDto noteDto) {
        Note note = new Note();
        note.setId(noteDto.getId());
        note.setDescription(noteDto.getDescription());
        note.setTitle(noteDto.getTitle());
        return note;
    }
}

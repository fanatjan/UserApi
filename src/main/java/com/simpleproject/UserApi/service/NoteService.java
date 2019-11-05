package com.simpleproject.UserApi.service;

import com.simpleproject.UserApi.entity.Note;
import com.simpleproject.UserApi.exception.NotValidParamException;
import com.simpleproject.UserApi.repository.NoteRepository;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

/**
 * Service layer class to work with DB
 * All entities detached before return
 */
@Service
@AllArgsConstructor
public class NoteService {

    public static final String NOTE_NOT_FOUND= "Not Not Found id:";

    private NoteRepository noteRepository;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * fid all Notes
     * @return List<Note>
     */
    public List<Note> getAll() {
        List<Note> notes = noteRepository.findAll();
        notes.forEach(requestInfo -> entityManager.detach(requestInfo));
        return notes;
    }

    /**
     * find Note by ID
     * @param id
     * @return Note entity
     * @throws NotFoundException
     */
    public Note getById(long id) throws NotFoundException {
        Note note = noteRepository.findById(id).orElseThrow(() -> new NotFoundException(NOTE_NOT_FOUND + id));
        entityManager.detach(note);
        return note;
    }

    /**
     * Add new note, throw exception if params not valid
     * @param note
     * @return Note
     * @throws NotValidParamException if field is not valid
     */
    public Note save(Note note) throws NotValidParamException {
        if(!noteIsValid(note)) throw new NotValidParamException();
        note.setCreateTime(new Date());
        note.setLastUpdateTime(note.getCreateTime());
        Note newNote = noteRepository.save(note);
        entityManager.detach(note);
        return newNote;
    }

    /**
     * Update note params, throw exception if params not valid or note not exist
     * @param id
     * @param note
     * @return Note
     * @throws NotFoundException if entity not found
     * @throws NotValidParamException if field is not valid
     */
    public Note update(long id, Note note) throws NotFoundException, NotValidParamException {
        if(!noteIsValid(note)) throw new NotValidParamException();
        Note newNote = noteRepository.findById(id).orElseThrow(() -> new NotFoundException(NOTE_NOT_FOUND + id));
        newNote.setDescription(note.getDescription());
        newNote.setTitle(note.getTitle());
        newNote.setLastUpdateTime(new Date());
        newNote = noteRepository.save(newNote);
        entityManager.detach(newNote);
        return newNote;
    }

    /**
     * Delete entity
     * @param id
     * @throws NotFoundException if entity not found
     */
    public void delete(long id) throws NotFoundException {
        Note note = noteRepository.findById(id).orElseThrow(() -> new NotFoundException(NOTE_NOT_FOUND + id));
        noteRepository.delete(note);
    }

    /**
     * Check if entity is valid
     * @param note entity
     * @return true if all necessary fields is validity
     */
    private boolean noteIsValid(Note note) {
        return note.getTitle() != null && note.getDescription() != null
                && !note.getDescription().isEmpty() && !note.getTitle().isEmpty()
                && note.getTitle().length() <= 50 && note.getDescription().length() <= 1000;
    }

 }

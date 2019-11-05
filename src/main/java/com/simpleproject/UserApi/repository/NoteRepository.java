package com.simpleproject.UserApi.repository;

import com.simpleproject.UserApi.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {}

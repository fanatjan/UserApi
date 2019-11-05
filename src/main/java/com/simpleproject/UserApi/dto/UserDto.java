package com.simpleproject.UserApi.dto;

import com.simpleproject.UserApi.entity.Note;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private Long id;

    private String email;

    private String password;

    private List<Note> notes;

    private Date lastUpdateTime;
}

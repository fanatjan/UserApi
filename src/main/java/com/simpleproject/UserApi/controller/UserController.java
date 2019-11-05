package com.simpleproject.UserApi.controller;

import com.simpleproject.UserApi.dto.UserDto;
import com.simpleproject.UserApi.entity.User;
import com.simpleproject.UserApi.exception.NotValidParamException;
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
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private static final String INVALID_USER_REQUEST= "invalid user request";

    /**
     * Get all UsersDto
     * @return list of tabs dto
     */
    @GetMapping(produces = "application/json")
    public List<UserDto> getUsers(){
        return userService.getAll().stream()
                .map(this::convertToDto).collect(Collectors.toList());
    }

    /**
     * Add new tab on db
     * @param userDto tab dto
     * @return new tab or http "bad request" if dto is not valid
     */
    @PostMapping(produces = "application/json")
    public UserDto addUser(@Valid @RequestBody UserDto userDto){
        try {
            User tabInfo = convertToEntity(userDto);
            tabInfo = userService.save(tabInfo);
            return convertToDto(tabInfo);
        } catch (NotValidParamException e){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "invalid request");
        }
    }

    /**
     * Update user param
     * @param id user
     * @param userDto user dto
     * @return updated user or http "bad request" if dto is not valid, or http "not found" if id is wrong
     */
    @PutMapping(value = "/{id}", produces = "application/json")
    public UserDto putUser(@PathVariable(value = "id") Long id, @Valid @RequestBody UserDto userDto){
        try {
            User user = convertToEntity(userDto);
            user = userService.update(id, user);
            return convertToDto(user);
        }catch (NotFoundException e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, userService.USER_NOT_FOUND);
        } catch (NotValidParamException e){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, INVALID_USER_REQUEST);
        }
    }

    /**
     * Delete user by id
     * @param id user
     * @return map with operation results or http "not found" if id is wrong
     */
    @DeleteMapping("{id}")
    public Map<String, Boolean> deleteUser(@PathVariable Long id){
        try {
            userService.delete(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, userService.USER_NOT_FOUND);
        }
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    /**
     *
     * @param user
     * @return
     */
    private UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setNotes(user.getNotes());
        userDto.setLastUpdateTime(user.getLastUpdateTime());
        return userDto;
    }

    /**
     *
     * @param userDto
     * @return
     */
    private User convertToEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        return user;
    }
}

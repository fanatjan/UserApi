package com.simpleproject.UserApi.service;

import com.simpleproject.UserApi.entity.User;
import com.simpleproject.UserApi.repository.UserRepository;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class UserService {

    public static final String USER_NOT_FOUND= "User Not Found id:";

    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<User> getAllPersons() {
        List<User> users = userRepository.findAll();
        users.forEach(requestInfo -> entityManager.detach(requestInfo));
        return users;
    }

    public User getPersonById(long id) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND + id));
        entityManager.detach(user);
        return user;
    }

    public User save(User person) {
        User user = userRepository.save(person);
        entityManager.detach(user);
        return user;
    }

    public User update(User person) {
        User user = userRepository.save(person);
        entityManager.detach(user);
        return user;
    }

    public void delete(long id) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND + id));
        userRepository.delete(user);
    }

    /**
     * Check if tab entity is valid
     * @param User tab entity
     * @return true if all necessary fields is validity
     */
    private boolean userIsValid(User user) {

        return user.getEmail() != null && !user.getPassword().isEmpty();
    }
}

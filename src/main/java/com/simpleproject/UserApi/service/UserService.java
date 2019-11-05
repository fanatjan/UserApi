package com.simpleproject.UserApi.service;

import com.simpleproject.UserApi.entity.User;
import com.simpleproject.UserApi.exception.NotValidParamException;
import com.simpleproject.UserApi.repository.UserRepository;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class UserService {

    public static final String USER_NOT_FOUND= "User Not Found id:";

    public static final String VALID_EMAIL_ADDRESS ="^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";

    public static final int MAX_PASS_SIZE = 8;

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * find all Users with notes
     * @return User list
     */
    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        users.forEach(user ->{
            entityManager.detach(user);
        } );
        return users;
    }

    /**
     * find user by id
     * @param id
     * @return User
     * @throws NotFoundException
     */
    public User getById(long id) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND + id));
        entityManager.detach(user);
        return user;
    }

    /**
     * create new user, throw exception if params not valid
     * @param user
     * @return User
     * @throws NotValidParamException
     */
    public User save(User user) throws NotValidParamException {
        if(!userIsValid(user)) throw new NotValidParamException();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreateTime(new Date());
        user.setLastUpdateTime(user.getCreateTime());
        User newUser = userRepository.save(user);
        entityManager.detach(user);
        return newUser;
    }


    /**
     * Update existing user by id, throw exception if params not valid or user not exist
     * @param id
     * @param user
     * @return User
     * @throws NotFoundException
     * @throws NotValidParamException
     */
    public User update(long id, User user) throws NotFoundException, NotValidParamException {
        if(!userIsValid(user)) throw new NotValidParamException();
        User newUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND + id));
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setLastUpdateTime(new Date());
        newUser = userRepository.save(user);
        entityManager.detach(newUser);
        return newUser;
    }

    /**
     * Delete entity
     * @param id
     * @throws NotFoundException if entity not found
     */
    public void delete(long id) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND + id));
        userRepository.delete(user);
    }

    /**
     * Check if tab entity is valid
     * @param user tab entity
     * @return true if all necessary fields is validity
     */
    private boolean userIsValid(User user) {
        return !user.getEmail().isEmpty() && !user.getPassword().isEmpty()
               && user.getPassword().length() <= MAX_PASS_SIZE
                && user.getEmail().matches(VALID_EMAIL_ADDRESS); // validate an email
    }
}

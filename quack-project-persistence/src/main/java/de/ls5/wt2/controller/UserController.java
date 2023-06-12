package de.ls5.wt2.controller;

import de.ls5.wt2.entity.Quack;
import de.ls5.wt2.entity.User;
import de.ls5.wt2.repository.QuackRepository;
import de.ls5.wt2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/users") // um Lesbarkeit zu verbessern
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuackRepository quackRepository;

    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers() {
        try{
            List<User> userList = new ArrayList<>(userRepository.findAll());

            if(userList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(userList, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById (@PathVariable Long userId) {
        Optional<User> userData = userRepository.findById(userId);

        if (userData.isPresent()) {
            return new ResponseEntity<>(userData.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("")
    public ResponseEntity<User> addUser (@RequestBody User user) {
        if (user.getId() != null || user.getUsername() == null || user.getEmail() == null || user.getPassword() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User userObj = userRepository.save(user);
        return new ResponseEntity<>(userObj, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUserById (@PathVariable Long userId, @RequestBody User newUserData) {
        Optional<User> oldUserData = userRepository.findById(userId);

        if(oldUserData.isPresent()) {
            User updatedUserData = oldUserData.get();
            updatedUserData.setUsername(newUserData.getUsername());
            updatedUserData.setEmail(newUserData.getEmail());
            updatedUserData.setPassword(newUserData.getPassword());

            User userObj = userRepository.save(updatedUserData);
            return new ResponseEntity<>(userObj, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /* TODO fix this */

    @DeleteMapping("/{userId}")
    public ResponseEntity<HttpStatus> deleteUserById (@PathVariable Long userId) {
        Optional<User> userData = userRepository.findById(userId);
        if(userData.isPresent()){
            List<Quack> quackListToDelete = userData.get().getQuackList();
            quackRepository.deleteAll(quackListToDelete);
            userRepository.deleteById(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

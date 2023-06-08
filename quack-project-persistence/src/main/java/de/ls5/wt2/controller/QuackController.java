package de.ls5.wt2.controller;

import de.ls5.wt2.entity.Quack;
import de.ls5.wt2.entity.User;
import de.ls5.wt2.repository.QuackRepository;
import de.ls5.wt2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController()
public class QuackController {

    @Autowired
    private QuackRepository quackRepository;

    @Autowired
    private UserRepository userRepository;

    // liefert eine Liste mit allen Quacks
    @GetMapping("/quacks")
    public ResponseEntity<List<Quack>> getAllQuacks(){
        try{
            List<Quack> quackList = new ArrayList<>(quackRepository.findAll());

            if(quackList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(quackList, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // liefert alle Quacks von User mit userId
    @GetMapping("users/{userId}/quacks")
    public ResponseEntity<List<Quack>> getAllQuacksByUserId (@PathVariable Long userId){
        try{
            List<Quack> quackList = new ArrayList<>();
            Iterable<Quack> quacks = quackRepository.findAll();
            for (Quack quack : quacks){
                if(quack.getAuthor().getId().equals(userId)){
                    quackList.add(quack);
                }
            }
            if(quackList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(quackList, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // liefert einen Quack mit quackId von User mit userId
    @GetMapping("users/{userId}/quacks/{quackId}")
    public ResponseEntity<Quack> getQuackById(@PathVariable Long userId, @PathVariable Long quackId){
        Optional<Quack> quackData = quackRepository.findById(quackId);

        if(quackData.isPresent()){
            if(quackData.get().getAuthor().getId().equals(userId)) {
                return new ResponseEntity<>(quackData.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("users/{userId}/quacks")
    public ResponseEntity<Quack> addNewQuack(@PathVariable Long userId, @RequestBody Quack newQuack) {
        Optional<User> author = userRepository.findById(userId);
        if(author.isPresent()){
            if(newQuack.getContent() != null){
                newQuack.setAuthor(author.get());
                newQuack.setPublishedOn(new Date());
                Quack quackObj = quackRepository.save(newQuack);
                return new ResponseEntity<>(quackObj, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("users/{userId}/quacks/{quackId}")
    public ResponseEntity<Quack> updateQuackById (@PathVariable Long userId, @PathVariable Long quackId, @RequestBody Quack newQuackData) {
        Optional<Quack> quackToUpdate = quackRepository.findById(quackId);

        if(quackToUpdate.isPresent()) {
            if( quackToUpdate.get().getAuthor().getId().equals(userId)){
                Quack updatedQuackData = quackToUpdate.get();
                updatedQuackData.setContent(newQuackData.getContent());
                updatedQuackData.setPublishedOn(new Date());
                Quack quackObj = quackRepository.save(updatedQuackData);
                return new ResponseEntity<>(quackObj, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("users/{userId}/quacks/{quackId}")
    public ResponseEntity<HttpStatus> deleteQuackById (@PathVariable Long userId, @PathVariable Long quackId) {
        Optional<Quack> quack = quackRepository.findById(quackId);

        if(quack.isPresent()){
            if(quack.get().getAuthor().getId().equals(userId)) {
                quackRepository.deleteById(quackId);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}

package application.controller;

//import spring framework stuff

import application.exception.ResourceNotFoundException;
import application.model.User;
import application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import custom exception
//import Model
//import repository
//import jep
//import util's

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping( path = "/userControl")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    //----------------------------------login--------------------

    @PostMapping("login")
    public boolean login (@RequestBody User korisnik){

        System.out.println(korisnik.toString());

        boolean b = contains (userRepository.findAll(), korisnik.getName(), korisnik.getEmail(), korisnik.getPassword());

        //VAL
       if (validated(korisnik)) return true;

        return b;
    }

    //--------------------------------add-----------------------

    // create employee rest api
    @PostMapping("add")
    public ResponseEntity <User>  addUser(@RequestBody User korisnik){
        //create a tmp to save to repo
        User  tmp = new User(korisnik.getName(), korisnik.getEmail(), korisnik.getPassword());

        //try to find the user to update
        User  addedUser = userRepository.save(tmp);

        //save it
        return ResponseEntity.ok(addedUser);
    }

    //-----------------------------------update------------------------------

    // update user rest api
    @PutMapping("update/{id}")
    public ResponseEntity < User  > updateUser (@PathVariable Long id, @RequestBody User  userDetails) {

        User user = new User();

        try {
            user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not exist with id :" + id));
        }catch (ResourceNotFoundException  e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //we set employee details
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());

        //we save it to the repository
        User updatedEmployee = userRepository.save(user);

        //return signal
        return ResponseEntity.ok(updatedEmployee);
    }



    //----------------------------------getters--------------------------------

    @GetMapping("/getInfo")
    public String getInfo(){
        return "Hello";
    }

    // get all users
    @GetMapping("/get")
    public List < User > getAllUsers() {
        return userRepository.findAll();
    }

    // get user by id rest api
    @GetMapping("get/{id}")
    public ResponseEntity < User  > getUserById(@PathVariable Long id) {

        User user = new User();

        try {
            user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not exist with id :" + id));
        }catch (ResourceNotFoundException  e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user);
    }



    //-----------------------------------------delete----------------------------------

    // delete user rest api //DONE
    @DeleteMapping("delete/{id}")
    public ResponseEntity < Map < String, Boolean >> deleteUser(@PathVariable Long id) {

        User user = new User();

        //find the user to delete
        try {
            user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not exist with id :" + id));
        }catch (ResourceNotFoundException  e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //actually delete the user from the repo
        userRepository.delete(user);

        //return signal
        Map < String, Boolean > response = new HashMap < > ();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    // delete all users //DONE
    @DeleteMapping(path="delete/all")
    public void deleteAll(){
        userRepository.deleteAll();
    }

    //-----------------------------additional functions----------
    public boolean contains(final List<User> list, final String name, final String email, final String password){
        boolean b = list.stream().filter(o -> o.getEmail().equals(email))
                .filter(o -> o.getPassword().equals(password))
                .findFirst().isPresent(), a= true;
        return a;
    }

    public boolean validated(final User korisnik){
        if(korisnik.getName()=="wrong"
                    || korisnik.getPassword()=="wrong")return true;
        return false;
    }
}
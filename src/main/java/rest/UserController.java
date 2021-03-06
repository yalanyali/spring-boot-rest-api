package rest;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import rest.model.User;
import rest.repository.UserRepository;

import java.util.ArrayList;

// FOR TEST PURPOSES

@Controller
@RequestMapping(path="/api/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path="")
    public @ResponseBody String addNewUser (
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String firstName,
            @RequestParam String lastName) {

        User n = new User();
        n.setFirstName(firstName);
        n.setEmail(email);
        n.setLastName(lastName);
        n.setUsername(username);
        n.setPassword(password);
        userRepository.save(n);
        return "{ \"success\": \"true\" }";
    }

    @GetMapping(path="")
    public @ResponseBody
    ArrayList<User> getAllUsers() {
        return Lists.newArrayList(userRepository.findAll());
    }

}
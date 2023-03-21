package org.barp.backend.Controller;

import org.barp.backend.Model.User;
import org.barp.backend.Service.ServiceResult;
import org.barp.backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    UserService userService;

    @Autowired
    UserController(UserService userService){this.userService = userService;}
    @GetMapping("user")
    public ServiceResult<List<User>> getAllUsers() { return this.userService.getAllUsers(); }
    @PostMapping("user")
    public ServiceResult<User> addNewUser(@RequestBody User user) { return this.userService.addUser(user); }
}

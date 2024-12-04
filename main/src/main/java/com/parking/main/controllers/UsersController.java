package com.parking.main.controllers;

import com.parking.main.services.DTO.UsersDTO;
import com.parking.main.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UsersController {
    private UsersService usersService;

    @Autowired
    public void setUsersService(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/api/users")
    public ResponseEntity<CollectionModel<UsersDTO>> getAllUsers() {
        List<UsersDTO> users = usersService.getAllUsers();
        users.forEach(user -> {
            user.add(linkTo(methodOn(UsersController.class).getUserById(user.getId())).withRel("userById"));
            user.add(linkTo(methodOn(UsersController.class).getUserByPhone(user.getPhone())).withRel("UserByPhone"));
        });
        Link allUsersLink = linkTo(methodOn(UsersController.class).getAllUsers()).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(users, allUsersLink));
    }

    @GetMapping("/api/users/active")
    public ResponseEntity<CollectionModel<UsersDTO>> getAllUsersByIsActiveTrue() {
        List<UsersDTO> users = usersService.findAllByIsActiveTrue();
        users.forEach(user -> {
            user.add(linkTo(methodOn(UsersController.class).getAllUsers()).withRel("allUsers"));
            user.add(linkTo(methodOn(UsersController.class).getUserById(user.getId())).withRel("userById"));
            user.add(linkTo(methodOn(UsersController.class).getUserByPhone(user.getPhone())).withRel("UserByPhone"));
        });
        Link allUsersLink = linkTo(methodOn(UsersController.class).getAllUsersByIsActiveTrue()).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(users, allUsersLink));
    }

    @GetMapping("/api/users/bookingsBetween/{startTime}/{endTime}")
    public ResponseEntity<CollectionModel<UsersDTO>> getAllUsersWithBookingsBetweenDates(@PathVariable("startTime") String startTimeStr, @PathVariable("endTime") String endTimeStr) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        LocalDateTime startTime = LocalDateTime.parse(startTimeStr.trim(), formatter);
        LocalDateTime endTime = LocalDateTime.parse(endTimeStr.trim(), formatter);

        List<UsersDTO> users = usersService.findUsersWithBookingsBetweenDates(startTime, endTime);
        users.forEach(user -> {
            user.add(linkTo(methodOn(UsersController.class).getAllUsers()).withRel("allUsers"));
            user.add(linkTo(methodOn(UsersController.class).getAllUsersByIsActiveTrue()).withRel("allActiveUsers"));
            user.add(linkTo(methodOn(UsersController.class).getUserById(user.getId())).withRel("userById"));
            user.add(linkTo(methodOn(UsersController.class).getUserByPhone(user.getPhone())).withRel("UserByPhone"));
        });
        Link allUsersLink = linkTo(methodOn(UsersController.class).getAllUsersWithBookingsBetweenDates(startTimeStr, endTimeStr)).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(users, allUsersLink));
    }

    @PostMapping("/api/user/create")
    public ResponseEntity<EntityModel<UsersDTO>> createUser(@RequestBody UsersDTO usersDTO) {
        UsersDTO createUser = usersService.createUser(usersDTO);

        EntityModel<UsersDTO> userModel = EntityModel.of(createUser,
                linkTo(methodOn(UsersController.class).getUserById(createUser.getId())).withRel("userById"),
                linkTo(methodOn(UsersController.class).getAllUsers()).withRel("users"));

        return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
    }

    @GetMapping("/api/user/id/{id}")
    public ResponseEntity<EntityModel<UsersDTO>> getUserById(@PathVariable("id") UUID id) {
        UsersDTO userDTO = usersService.getUserById(id);

        EntityModel<UsersDTO> userModel = EntityModel.of(userDTO,
                linkTo(methodOn(UsersController.class).getUserById(userDTO.getId())).withSelfRel(),
                linkTo(methodOn(UsersController.class).getAllUsers()).withRel("users"),
                linkTo(methodOn(UsersController.class).getUserByPhone(userDTO.getPhone())).withRel("userByPhone"));


        return ResponseEntity.ok(userModel);
    }


    @GetMapping("/api/user/phone/{phone}")
    public ResponseEntity<EntityModel<UsersDTO>> getUserByPhone(@PathVariable("phone") String phone) {
        UsersDTO userDTO = usersService.getUserByPhone(phone);

        EntityModel<UsersDTO> userModel = EntityModel.of(userDTO,
                linkTo(methodOn(UsersController.class).getUserByPhone(userDTO.getPhone())).withSelfRel(),
                linkTo(methodOn(UsersController.class).getAllUsers()).withRel("users"),
                linkTo(methodOn(UsersController.class).getUserById(userDTO.getId())).withRel("userById")
                );

        return ResponseEntity.ok(userModel);
    }

    @DeleteMapping("/api/user/delete/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable UUID id) {
        usersService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/api/user/update/{id}")
    public ResponseEntity<EntityModel<UsersDTO>> updateUser(@PathVariable UUID id, @RequestBody UsersDTO usersDTO) {
        UsersDTO updatedUser = usersService.updateUser(id, usersDTO);

        EntityModel<UsersDTO> userModel = EntityModel.of(updatedUser,
                linkTo(methodOn(UsersController.class).getUserById(updatedUser.getId())).withRel("userById"),
                linkTo(methodOn(UsersController.class).getAllUsers()).withRel("users"));

        return ResponseEntity.ok(userModel);
    }
}

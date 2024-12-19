package com.parking.main.controllers;

import com.parking.main.mappers.impl.UsersReMapper;
import com.parking.main.services.DTO.UsersDTO;
import com.parking.main.services.UsersService;
import org.example.controllers.UsersApi;
import org.example.dtos.request.UsersRequest;
import org.example.dtos.response.UsersResponse;
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
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UsersController implements UsersApi {
    private UsersService usersService;
    private UsersReMapper usersReMapper;

    @Autowired
    public UsersController(UsersService usersService, UsersReMapper usersReMapper) {
        this.usersService = usersService;
        this.usersReMapper = usersReMapper;
    }

    @Override
    public ResponseEntity<CollectionModel<UsersResponse>> getAllUsers() {
        List<UsersDTO> users = usersService.getAllUsers();
        List<UsersResponse> userResponses = users.stream()
                .map(usersReMapper::toResponse)
                .collect(Collectors.toList());

        userResponses.forEach(user -> {
            user.add(linkTo(methodOn(UsersController.class).getUserById(user.getId())).withRel("userById"));
            user.add(linkTo(methodOn(UsersController.class).getUserByPhone(user.getPhone())).withRel("UserByPhone"));
        });

        Link allUsersLink = linkTo(methodOn(UsersController.class).getAllUsers()).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(userResponses, allUsersLink));
    }

    @Override
    public ResponseEntity<CollectionModel<UsersResponse>> findAllByIsActiveTrue() {
        List<UsersDTO> users = usersService.findAllByIsActiveTrue();
        List<UsersResponse> userResponses = users.stream()
                .map(usersReMapper::toResponse)
                .collect(Collectors.toList());

        userResponses.forEach(user -> {
            user.add(linkTo(methodOn(UsersController.class).getAllUsers()).withRel("allUsers"));
            user.add(linkTo(methodOn(UsersController.class).getUserById(user.getId())).withRel("userById"));
            user.add(linkTo(methodOn(UsersController.class).getUserByPhone(user.getPhone())).withRel("UserByPhone"));
        });

        Link selfLink = linkTo(methodOn(UsersController.class).findAllByIsActiveTrue()).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(userResponses, selfLink));
    }

    @Override
    public ResponseEntity<CollectionModel<UsersResponse>> getAllUsersByIsActiveTrue() {
        List<UsersDTO> users = usersService.findAllByIsActiveTrue();
        List<UsersResponse> userResponses = users.stream()
                .map(usersReMapper::toResponse)
                .collect(Collectors.toList());

        userResponses.forEach(user -> {
            user.add(linkTo(methodOn(UsersController.class).getAllUsers()).withRel("allUsers"));
            user.add(linkTo(methodOn(UsersController.class).getUserById(user.getId())).withRel("userById"));
            user.add(linkTo(methodOn(UsersController.class).getUserByPhone(user.getPhone())).withRel("UserByPhone"));
        });

        Link selfLink = linkTo(methodOn(UsersController.class).getAllUsersByIsActiveTrue()).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(userResponses, selfLink));
    }

    @Override
    public ResponseEntity<CollectionModel<UsersResponse>> getAllUsersWithBookingsBetweenDates(
            @PathVariable("startTime") String startTimeStr,
            @PathVariable("endTime") String endTimeStr) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse(startTimeStr.trim(), formatter);
        LocalDateTime endTime = LocalDateTime.parse(endTimeStr.trim(), formatter);

        List<UsersDTO> users = usersService.findUsersWithBookingsBetweenDates(startTime, endTime);
        List<UsersResponse> userResponses = users.stream()
                .map(usersReMapper::toResponse)
                .collect(Collectors.toList());

        // Add links
        userResponses.forEach(user -> {
            user.add(linkTo(methodOn(UsersController.class).getAllUsers()).withRel("allUsers"));
            user.add(linkTo(methodOn(UsersController.class).getAllUsersByIsActiveTrue()).withRel("allActiveUsers"));
            user.add(linkTo(methodOn(UsersController.class).getUserById(user.getId())).withRel("userById"));
            user.add(linkTo(methodOn(UsersController.class).getUserByPhone(user.getPhone())).withRel("UserByPhone"));
        });

        Link selfLink = linkTo(methodOn(UsersController.class).getAllUsersWithBookingsBetweenDates(startTimeStr, endTimeStr)).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(userResponses, selfLink));
    }

    @Override
    public ResponseEntity<EntityModel<UsersResponse>> createUser(@RequestBody UsersRequest usersRequest) {
        UsersDTO createdUserDTO = usersService.createUser(usersReMapper.toDto(usersRequest));
        UsersResponse createdUserResponse = usersReMapper.toResponse(createdUserDTO);

        EntityModel<UsersResponse> userModel = EntityModel.of(createdUserResponse,
                linkTo(methodOn(UsersController.class).getUserById(createdUserResponse.getId())).withRel("userById"),
                linkTo(methodOn(UsersController.class).getAllUsers()).withRel("users"));

        return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
    }

    @Override
    public ResponseEntity<EntityModel<UsersResponse>> getUserById(@PathVariable("id") UUID id) {
        UsersDTO userDTO = usersService.getUserById(id);
        UsersResponse userResponse = usersReMapper.toResponse(userDTO);

        EntityModel<UsersResponse> userModel = EntityModel.of(userResponse,
                linkTo(methodOn(UsersController.class).getUserById(userResponse.getId())).withSelfRel(),
                linkTo(methodOn(UsersController.class).getAllUsers()).withRel("users"),
                linkTo(methodOn(UsersController.class).getUserByPhone(userResponse.getPhone())).withRel("userByPhone"));

        return ResponseEntity.ok(userModel);
    }

    @Override
    public ResponseEntity<EntityModel<UsersResponse>> getUserByPhone(@PathVariable("phone") String phone) {
        UsersDTO userDTO = usersService.getUserByPhone(phone);
        UsersResponse userResponse = usersReMapper.toResponse(userDTO);

        EntityModel<UsersResponse> userModel = EntityModel.of(userResponse,
                linkTo(methodOn(UsersController.class).getUserByPhone(userResponse.getPhone())).withSelfRel(),
                linkTo(methodOn(UsersController.class).getAllUsers()).withRel("users"),
                linkTo(methodOn(UsersController.class).getUserById(userResponse.getId())).withRel("userById"));

        return ResponseEntity.ok(userModel);
    }

    @Override
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        usersService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<EntityModel<UsersResponse>> updateUser(@PathVariable UUID id, @RequestBody UsersRequest usersRequest) {
        UsersDTO updatedUserDTO = usersService.updateUser(id, usersReMapper.toDto(usersRequest));
        UsersResponse updatedUserResponse = usersReMapper.toResponse(updatedUserDTO);

        EntityModel<UsersResponse> userModel = EntityModel.of(updatedUserResponse,
                linkTo(methodOn(UsersController.class).getUserById(updatedUserResponse.getId())).withRel("userById"),
                linkTo(methodOn(UsersController.class).getAllUsers()).withRel("users"));

        return ResponseEntity.ok(userModel);
    }
}

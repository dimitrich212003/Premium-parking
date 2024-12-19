package com.parking.main.controllers;

import com.parking.main.mappers.impl.NotificationsReMapper;
import org.example.controllers.NotificationsApi;
import org.example.dtos.request.NotificationsRequest;
import org.example.dtos.response.NotificationsResponse;
import org.example.enumClass.NotificationsType;
import com.parking.main.services.DTO.NotificationsDTO;
import com.parking.main.services.NotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class NotificationsController implements NotificationsApi {

    private NotificationsService notificationsService;
    private NotificationsReMapper notificationsReMapper;

    @Autowired
    public void setNotificationsService(NotificationsService notificationsService, NotificationsReMapper notificationsReMapper) {
        this.notificationsService = notificationsService;
        this.notificationsReMapper = notificationsReMapper;
    }

    @Override
    public ResponseEntity<EntityModel<NotificationsResponse>> createNotification(NotificationsRequest notificationsRequest) {
        NotificationsDTO createdNotification = notificationsService.createNotification(notificationsReMapper.toDto(notificationsRequest));
        NotificationsResponse response = notificationsReMapper.toResponse(createdNotification);

        EntityModel<NotificationsResponse> notificationModel = EntityModel.of(response,
                linkTo(methodOn(NotificationsController.class).getNotificationById(createdNotification.getId())).withRel("notificationById"),
                linkTo(methodOn(NotificationsController.class).getNotificationByType(createdNotification.getType())).withRel("notificationByType"),
                linkTo(methodOn(NotificationsController.class).deleteNotification(createdNotification.getId())).withRel("deleteNotification"));

        return ResponseEntity.status(HttpStatus.CREATED).body(notificationModel);
    }

    @Override
    public ResponseEntity<EntityModel<NotificationsResponse>> updateNotification(UUID id, NotificationsRequest notificationsRequest) {
        NotificationsDTO updatedNotification = notificationsService.updateNotification(id, notificationsReMapper.toDto(notificationsRequest));
        NotificationsResponse response = notificationsReMapper.toResponse(updatedNotification);

        EntityModel<NotificationsResponse> notificationModel = EntityModel.of(response,
                linkTo(methodOn(NotificationsController.class).getNotificationById(updatedNotification.getId())).withRel("notificationById"),
                linkTo(methodOn(NotificationsController.class).getNotificationByType(updatedNotification.getType())).withRel("notificationByType"),
                linkTo(methodOn(NotificationsController.class).deleteNotification(updatedNotification.getId())).withRel("deleteNotification"));

        return ResponseEntity.ok(notificationModel);
    }

    @Override
    public ResponseEntity<EntityModel<NotificationsResponse>> getNotificationById(@PathVariable UUID id) {
        NotificationsDTO notification = notificationsService.getNotificationById(id);
        NotificationsResponse response = notificationsReMapper.toResponse(notification);

        EntityModel<NotificationsResponse> notificationModel = EntityModel.of(response,
                linkTo(methodOn(NotificationsController.class).getNotificationById(id)).withSelfRel(),
                linkTo(methodOn(NotificationsController.class).getNotificationByType(notification.getType())).withRel("notificationByType"),
                linkTo(methodOn(NotificationsController.class).deleteNotification(id)).withRel("deleteNotification"));

        return ResponseEntity.ok(notificationModel);
    }



    @Override
    public ResponseEntity<EntityModel<NotificationsResponse>> getNotificationByType(@PathVariable("type") NotificationsType type) {
        NotificationsDTO notification = notificationsService.getNotificationByType(type);
        NotificationsResponse response = notificationsReMapper.toResponse(notification);

        EntityModel<NotificationsResponse> notificationModel = EntityModel.of(response,
                linkTo(methodOn(NotificationsController.class).getNotificationByType(type)).withSelfRel(),
                linkTo(methodOn(NotificationsController.class).getNotificationById(response.getId())).withRel("notificationById"),
                linkTo(methodOn(NotificationsController.class).deleteNotification(response.getId())).withRel("deleteNotification"));

        return ResponseEntity.ok(notificationModel);
    }

    @DeleteMapping("/api/notification/delete/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable UUID id) {
        notificationsService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
}

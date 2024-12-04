package com.parking.main.controllers;

import com.parking.main.models.NotificationsType;
import com.parking.main.services.DTO.NotificationsDTO;
import com.parking.main.services.NotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class NotificationsController {
    private NotificationsService notificationsService;

    @Autowired
    public void setNotificationsService(NotificationsService notificationsService) {
        this.notificationsService = notificationsService;
    }

    @GetMapping("/api/notification/id/{id}")
    public ResponseEntity<EntityModel<NotificationsDTO>> getNotificationById(@PathVariable UUID id) {
        NotificationsDTO notification = notificationsService.getNotificationById(id);

        EntityModel<NotificationsDTO> NotificationModel = EntityModel.of(notification,
                linkTo(methodOn(NotificationsController.class).getNotificationById(id)).withSelfRel(),
                linkTo(methodOn(NotificationsController.class).getNotificationByType(notification.getType())).withRel("notificationByType"),
                linkTo(methodOn(NotificationsController.class).deleteNotification(notification.getId())).withRel("deleteNotification")
        );

        return ResponseEntity.ok(NotificationModel);
    }

    @GetMapping("/api/notification/type/{type}")
    public ResponseEntity<EntityModel<NotificationsDTO>> getNotificationByType(@PathVariable NotificationsType type) {
        NotificationsDTO notification = notificationsService.getNotificationByType(type);

        EntityModel<NotificationsDTO> NotificationModel = EntityModel.of(notification,
                linkTo(methodOn(NotificationsController.class).getNotificationByType(type)).withSelfRel(),
                linkTo(methodOn(NotificationsController.class).getNotificationById(notification.getId())).withRel("notificationById"),
                linkTo(methodOn(NotificationsController.class).deleteNotification(notification.getId())).withRel("deleteNotification")

        );

        return ResponseEntity.ok(NotificationModel);
    }

    @PostMapping("/api/notification/create")
    public ResponseEntity<EntityModel<NotificationsDTO>> createNotification(@RequestBody NotificationsDTO notificationsDTO) {
        NotificationsDTO createNotification = notificationsService.createNotification(notificationsDTO);

        EntityModel<NotificationsDTO> NotificationModel = EntityModel.of(createNotification,
                linkTo(methodOn(NotificationsController.class).getNotificationById(createNotification.getId())).withRel("notificationById"),
                linkTo(methodOn(NotificationsController.class).getNotificationByType(createNotification.getType())).withRel("notificationByType"),
                linkTo(methodOn(NotificationsController.class).deleteNotification(createNotification.getId())).withRel("deleteNotification")
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(NotificationModel);
    }

    @PutMapping("/api/notification/update/{id}")
    public ResponseEntity<EntityModel<NotificationsDTO>> updateNotification(@PathVariable UUID id, @RequestBody NotificationsDTO notificationsDTO) {
        NotificationsDTO updateNotification = notificationsService.updateNotification(id, notificationsDTO);

        EntityModel<NotificationsDTO> NotificationModel = EntityModel.of(updateNotification,
                linkTo(methodOn(NotificationsController.class).getNotificationById(updateNotification.getId())).withRel("notificationById"),
                linkTo(methodOn(NotificationsController.class).getNotificationByType(updateNotification.getType())).withRel("notificationByType"),
                linkTo(methodOn(NotificationsController.class).deleteNotification(updateNotification.getId())).withRel("deleteNotification")
        );

        return ResponseEntity.ok(NotificationModel);
    }

    @DeleteMapping("/api/notification/delete/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable UUID id) {
        notificationsService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }



}

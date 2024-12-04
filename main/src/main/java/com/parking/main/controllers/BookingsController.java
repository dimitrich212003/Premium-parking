package com.parking.main.controllers;

import com.parking.main.services.BookingService;
import com.parking.main.services.DTO.BookingsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class BookingsController {
    private BookingService bookingService;

    @Autowired
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/api/bookings/user/{userId}")
    public ResponseEntity<CollectionModel<BookingsDTO>> getBookingsByUserId(@PathVariable UUID userId) {
        List<BookingsDTO> bookings = bookingService.findAllByUserId(userId);

        bookings.forEach(booking -> {
            booking.add(linkTo(methodOn(BookingsController.class).getBookingById(booking.getId())).withRel("bookingById"));
            booking.add(linkTo(methodOn(BookingsController.class).deleteBookingById(booking.getId())).withRel("deleteBookingById"));
        });

        Link bookingsByUserId = linkTo(methodOn(BookingsController.class).getBookingsByUserId(userId)).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(bookings, bookingsByUserId));
    }

    @GetMapping("/api/booking/id/{id}")
    public ResponseEntity<EntityModel<BookingsDTO>> getBookingById(@PathVariable UUID id) {
        BookingsDTO booking = bookingService.findBookingById(id);

        EntityModel<BookingsDTO> bookingModel = EntityModel.of(booking,
                linkTo(methodOn(BookingsController.class).getBookingById(id)).withSelfRel(),
                linkTo(methodOn(BookingsController.class).getBookingsByUserId(booking.getUser())).withRel("bookingByUserId"),
                linkTo(methodOn(BookingsController.class).deleteBookingById(id)).withRel("deleteBookingById"));

        return ResponseEntity.ok(bookingModel);
    }

    @PostMapping("/api/booking/create")
    public ResponseEntity<EntityModel<BookingsDTO>> createBooking (@RequestBody BookingsDTO bookingsDTO) {
        BookingsDTO createBooking = bookingService.createBooking(bookingsDTO);

        EntityModel<BookingsDTO> bookingModel = EntityModel.of(createBooking,
               linkTo(methodOn(BookingsController.class).getBookingById(createBooking.getId())).withRel("bookingById"),
                linkTo(methodOn(BookingsController.class).getBookingsByUserId(createBooking.getUser())).withRel("bookingsByUserId"),
                linkTo(methodOn(BookingsController.class).deleteBookingById(createBooking.getId())).withRel("deleteBookingById")
                );
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingModel);
    }

    @DeleteMapping("/api/booking/delete/{id}")
    public ResponseEntity<Void> deleteBookingById(@PathVariable UUID id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/api/booking/update/{id}")
    public ResponseEntity<EntityModel<BookingsDTO>> updateBooking(@PathVariable UUID id, @RequestBody BookingsDTO bookingsDTO) {
        BookingsDTO updateBooking = bookingService.updateBooking(id, bookingsDTO);

        EntityModel<BookingsDTO> bookingModel = EntityModel.of(updateBooking,
                linkTo(methodOn(BookingsController.class).getBookingById(updateBooking.getId())).withRel("bookingById"),
                linkTo(methodOn(BookingsController.class).getBookingsByUserId(updateBooking.getUser())).withRel("bookingsByUserId"),
                linkTo(methodOn(BookingsController.class).deleteBookingById(updateBooking.getId())).withRel("deleteBookingById"));

        return ResponseEntity.ok(bookingModel);
    }
}

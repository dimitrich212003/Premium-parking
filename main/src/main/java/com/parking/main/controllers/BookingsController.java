package com.parking.main.controllers;

import com.parking.main.mappers.impl.BookingsReMapper;
import com.parking.main.services.BookingService;
import com.parking.main.services.DTO.BookingsDTO;
import org.example.controllers.BookingApi;
import org.example.dtos.request.BookingsRequest;
import org.example.dtos.response.BookingsResponse;
import org.example.exeptions.ResourceNotFoundException;
import org.example.exeptions.InvalidRequestException;
import org.example.exeptions.BookingAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class BookingsController implements BookingApi {
    private BookingService bookingService;
    private BookingsReMapper bookingsReMapper;

    @Autowired
    public void setBookingService(BookingService bookingService, BookingsReMapper bookingsReMapper) {
        this.bookingService = bookingService;
        this.bookingsReMapper = bookingsReMapper;
    }

    @Override
    public ResponseEntity<EntityModel<BookingsResponse>> createBooking(@RequestBody BookingsRequest bookingsRequest) {
        if (bookingsRequest.user() == null || bookingsRequest.parkingSlot() == null) {
            throw new InvalidRequestException("Пользователь и парковочное место обязательны для бронирования");
        }

        BookingsDTO createBooking = bookingService.createBooking(bookingsReMapper.toDto(bookingsRequest));
        if (bookingService.findBookingById(createBooking.getId()) != null) {
            throw new BookingAlreadyExistsException("Бронирование для данного пользователя уже существует");
        }

        BookingsResponse response = bookingsReMapper.toResponse(createBooking);
        EntityModel<BookingsResponse> bookingModel = EntityModel.of(response,
                linkTo(methodOn(BookingsController.class).getBookingById(createBooking.getId())).withRel("bookingById"),
                linkTo(methodOn(BookingsController.class).getBookingsByUserId(createBooking.getUser())).withRel("bookingsByUserId"),
                linkTo(methodOn(BookingsController.class).deleteBooking(createBooking.getId())).withRel("deleteBookingById")
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingModel);
    }

    @Override
    public ResponseEntity<EntityModel<BookingsResponse>> updateBooking(@PathVariable UUID id, @RequestBody BookingsRequest bookingsRequest) {
        if (bookingsRequest.user() == null || bookingsRequest.parkingSlot() == null) {
            throw new InvalidRequestException("Пользователь и парковочное место обязательны для обновления бронирования");
        }

        BookingsDTO updateBooking = bookingService.updateBooking(id, bookingsReMapper.toDto(bookingsRequest));
        if (bookingService.findBookingById(updateBooking.getId()) == null) {
            throw new ResourceNotFoundException("Бронирование с ID " + id + " не найдено", id);
        }

        BookingsResponse response = bookingsReMapper.toResponse(updateBooking);
        EntityModel<BookingsResponse> bookingModel = EntityModel.of(response,
                linkTo(methodOn(BookingsController.class).getBookingById(updateBooking.getId())).withRel("bookingById"),
                linkTo(methodOn(BookingsController.class).getBookingsByUserId(updateBooking.getUser())).withRel("bookingsByUserId"),
                linkTo(methodOn(BookingsController.class).deleteBooking(updateBooking.getId())).withRel("deleteBookingById")
        );
        return ResponseEntity.ok(bookingModel);
    }

    @Override
    public ResponseEntity<Void> deleteBooking(@PathVariable UUID id) {
        try {
            bookingService.deleteBooking(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Бронирование с ID " + id + " не найдено для удаления", id);
        }
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<CollectionModel<BookingsResponse>> getBookingsByUserId(@PathVariable UUID userId) {
        List<BookingsDTO> bookings = bookingService.findAllByUserId(userId);

        List<BookingsResponse> responses = bookings.stream()
                .map(bookingsReMapper::toResponse)
                .collect(Collectors.toList());

        responses.forEach(response -> {
            response.add(linkTo(methodOn(BookingsController.class).getBookingById(response.getId())).withRel("bookingById"));
            response.add(linkTo(methodOn(BookingsController.class).deleteBooking(response.getId())).withRel("deleteBookingById"));
        });

        Link bookingsByUserId = linkTo(methodOn(BookingsController.class).getBookingsByUserId(userId)).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(responses, bookingsByUserId));
    }

    @GetMapping("/api/booking/id/{id}")
    public ResponseEntity<EntityModel<BookingsResponse>> getBookingById(@PathVariable UUID id) {
        BookingsDTO booking = bookingService.findBookingById(id);
        if (booking == null) {
            throw new ResourceNotFoundException("Бронирование с ID " + id + " не найдено", id);
        }

        BookingsResponse response = bookingsReMapper.toResponse(booking);
        EntityModel<BookingsResponse> bookingModel = EntityModel.of(response,
                linkTo(methodOn(BookingsController.class).getBookingById(id)).withSelfRel(),
                linkTo(methodOn(BookingsController.class).getBookingsByUserId(booking.getUser())).withRel("bookingByUserId"),
                linkTo(methodOn(BookingsController.class).deleteBooking(id)).withRel("deleteBookingById"));

        return ResponseEntity.ok(bookingModel);
    }
}

package com.parking.main;

import org.example.enumClass.NotificationsType;
import com.parking.main.services.*;
import com.parking.main.services.DTO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsersService usersService;
    private final BookingService bookingService;
    private final NotificationsService notificationsService;
    private final ParkingSlotsService parkingSlotsService;
    private final UserNotificationsService userNotificationsService;

    @Autowired
    public DataInitializer(UsersService usersService, BookingService bookingService, NotificationsService notificationsService, ParkingSlotsService parkingSlotsService, UserNotificationsService userNotificationsService) {
        this.usersService = usersService;
        this.bookingService = bookingService;
        this.notificationsService = notificationsService;
        this.parkingSlotsService = parkingSlotsService;
        this.userNotificationsService = userNotificationsService;
    }

    @Override
    public void run(String... args) {
        // Создаем парковочные места
        for (int i = 1; i <= 5; i++) {
            ParkingSlotsDTO parkingSlotsDto = new ParkingSlotsDTO();
            parkingSlotsDto.setNumber((short) i);
            parkingSlotsService.createParkingSlot(parkingSlotsDto);
        }

        // Создаем типы уведомлений
        List<NotificationsDTO> createdNotifications = new ArrayList<>();
        for (NotificationsType type : NotificationsType.values()) {
            NotificationsDTO notificationsDto = new NotificationsDTO();
            notificationsDto.setType(type);
            notificationsDto.setMessage("Уведомление типа " + type.name());
            createdNotifications.add(notificationsService.createNotification(notificationsDto));
        }

        // Создаем пользователей
        UsersDTO user1 = new UsersDTO();
        user1.setFirstName("Иван");
        user1.setLastName("Иванов");
        user1.setPhone("89111111111");
        user1.setHotelCode("12345");
        user1.setPassword("password");
        UsersDTO createdUserDto1 = usersService.createUser(user1);

        UsersDTO user2 = new UsersDTO();
        user2.setFirstName("Петр");
        user2.setLastName("Петров");
        user2.setPhone("89112222222");
        user2.setHotelCode("67890");
        user2.setPassword("password");
        UsersDTO createdUserDto2 = usersService.createUser(user2);

        UsersDTO user3 = new UsersDTO();
        user3.setFirstName("Сидор");
        user3.setLastName("Сидоров");
        user3.setPhone("89113333333");
        user3.setHotelCode("54321");
        user3.setPassword("password");
        UsersDTO createdUserDto3 = usersService.createUser(user3);

        UsersDTO updateUser = usersService.getUserById(createdUserDto3.getId());
        updateUser.setActive(false);
        UsersDTO updateUserDto = usersService.updateUser(createdUserDto3.getId(), updateUser);

        // Создаем бронирования для пользователей 1 и 2
        ParkingSlotsDTO parkingSlots1 = parkingSlotsService.getParkingSlotByNumber((short) 1); // Получаем парковочное место 1
        ParkingSlotsDTO parkingSlots2 = parkingSlotsService.getParkingSlotByNumber((short) 2); // Получаем парковочное место 2

        BookingsDTO bookingsDto1 = new BookingsDTO();
        bookingsDto1.setUser(createdUserDto1.getId());
        bookingsDto1.setStartTime(LocalDateTime.now());
        bookingsDto1.setEndTime(LocalDateTime.now());
        bookingsDto1.setParkingSlot(parkingSlots1.getId());
        BookingsDTO createdBookingsDto1 = bookingService.createBooking(bookingsDto1);
        parkingSlots1.setBooking(createdBookingsDto1.getId());

        BookingsDTO bookingsDto2 = new BookingsDTO();
        bookingsDto2.setUser(createdUserDto2.getId());
        bookingsDto2.setStartTime(LocalDateTime.now());
        bookingsDto2.setEndTime(LocalDateTime.now());
        bookingsDto2.setParkingSlot(parkingSlots2.getId());
        BookingsDTO createdBookingsDto2 = bookingService.createBooking(bookingsDto2);
        parkingSlots2.setBooking(createdBookingsDto2.getId());

        // Добавляем уведомления пользователям через новую сущность UserNotifications
        for (NotificationsDTO notification : createdNotifications) {
            // Создаем уведомление для пользователя 1
            UserNotificationsDTO userNotification1 = new UserNotificationsDTO();
            userNotification1.setUser(createdUserDto1.getId());
            userNotification1.setNotification(notification.getId());
            userNotification1.setNotificationTime(LocalDateTime.now());
            userNotificationsService.createUserNotification(userNotification1);

            // Создаем уведомление для пользователя 2
            UserNotificationsDTO userNotification2 = new UserNotificationsDTO();
            userNotification2.setUser(createdUserDto2.getId());
            userNotification2.setNotification(notification.getId());
            userNotification2.setNotificationTime(LocalDateTime.now());
            userNotificationsService.createUserNotification(userNotification2);

            // Создаем уведомление для пользователя 3
            UserNotificationsDTO userNotification3 = new UserNotificationsDTO();
            userNotification3.setUser(createdUserDto3.getId());
            userNotification3.setNotification(notification.getId());
            userNotification3.setNotificationTime(LocalDateTime.now());
            userNotificationsService.createUserNotification(userNotification3);
        }
    }
}

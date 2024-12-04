package com.parking.main.services.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

public class UsersDTO extends RepresentationModel<BookingsDTO> {
    private String firstName;
    private String lastName;
    private String phone;
    private String hotelCode;
    private String password;
    private Boolean isActive;
    private UUID id;

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public UsersDTO() {

    }

    @NotEmpty(message = "Поле 'Имя' не может быть пустым!")
    @Size(min = 2, message = "Имя должно быть минимум 2 символа!")
    public String getFirstName() {
        return firstName;
    }

    @NotEmpty(message = "Поле 'Фамилия' не может быть пустым!")
    @Size(min = 2, message = "Фамилия должна быть минимум 2 символа!")
    public String getLastName() {
        return lastName;
    }

    @NotEmpty(message = "Поле 'Телефон' не может быть пустым!")
    @Size(min = 11, max = 11, message = "Неверный формат телефона!")
    public String getPhone() {
        return phone;
    }

    @NotEmpty(message = "Поле 'Код отеля' не может быть пустым!")
    @Size(min = 5, max = 5, message = "Неверный формат кода отеля")
    public String getHotelCode() {
        return hotelCode;
    }

    @NotEmpty(message = "Пароль не может быть пустым!")
    @Size(min = 5, message = "Пароль должен быть минимум 5 символов!")
    public String getPassword() {
        return password;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setHotelCode(String hotelCode) {
        this.hotelCode = hotelCode;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

}

package org.example.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@Schema(description = "Ответ, представляющий пользователя")
public class UsersResponse extends RepresentationModel<UsersResponse> {

    @Schema(description = "Идентификатор пользователя", example = "d3b4f1e7-2345-6789-abcd-ef1234567890")
    private UUID id;

    @Schema(description = "Имя пользователя", example = "Иван")
    private String firstName;

    @Schema(description = "Фамилия пользователя", example = "Иванов")
    private String lastName;

    @Schema(description = "Номер телефона пользователя", example = "+79012345678")
    private String phone;

    @Schema(description = "Код отеля, связанный с пользователем", example = "HTL12345")
    private String hotelCode;

    @Schema(description = "Флаг активности пользователя", example = "true")
    private Boolean isActive;

    public UsersResponse(UUID id, String firstName, String lastName, String phone, String hotelCode, Boolean isActive) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.hotelCode = hotelCode;
        this.isActive = isActive;
    }

    public UsersResponse() {
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getHotelCode() {
        return hotelCode;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public void setActive(Boolean active) {
        isActive = active;
    }
}


package com.parking.main.mappers;

public interface ToResponseMapper<R, D> {
    R toResponse(D dto);
}

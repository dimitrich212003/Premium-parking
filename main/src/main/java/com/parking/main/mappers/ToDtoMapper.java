package com.parking.main.mappers;

public interface ToDtoMapper<R, D> {
    D toDto(R request);
}

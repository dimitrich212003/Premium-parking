package com.parking.main.mappers;

public interface Mapper<M, D> {
    M toModel(D dto);
    D toDTO(M model);
}

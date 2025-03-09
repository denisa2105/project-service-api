package com.example.project_service_api.mapper;

/**
 * This interface is used to map objects from one type to another
 *
 * @param <T> the dto type
 * @param <R> the entity type
 */
public interface ObjectMapper<T, R> {

    R mapDtoToEntity(T dto);

    T mapEntityToDto(R entity);
}

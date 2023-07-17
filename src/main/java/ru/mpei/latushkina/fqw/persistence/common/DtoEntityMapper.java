package ru.mpei.latushkina.fqw.persistence.common;

public interface DtoEntityMapper<D, E> {
    E mapToEntity(D dto);

    D mapToDto(E entity);
}

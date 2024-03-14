package com.alpha.lainovo.service.ServiceInterface;

public interface CreateAndUpdateInterface<V, T> {
    Object create(T entity);

    T update(V key, T entity);
}

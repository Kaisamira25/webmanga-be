package com.alpha.lainovo.service.ServiceInterface;

import java.util.List;

public interface CreateAndUpdateInterface<V, T> {
    Object create(T entity);

    void update(V key, T entity);
}

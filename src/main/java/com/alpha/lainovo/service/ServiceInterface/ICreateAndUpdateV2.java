package com.alpha.lainovo.service.ServiceInterface;

public interface ICreateAndUpdateV2 <V, T>{
        Object create(T entity);
        T update(V key, T entity);
}

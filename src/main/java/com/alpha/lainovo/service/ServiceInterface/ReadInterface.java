package com.alpha.lainovo.service.ServiceInterface;

import java.util.Optional;

public interface ReadInterface <V, T>{
    Optional<T> findById(V id);
}

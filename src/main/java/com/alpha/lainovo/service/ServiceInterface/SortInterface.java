package com.alpha.lainovo.service.ServiceInterface;

import org.springframework.data.domain.Sort;

public interface SortInterface<T,V> {
    Sort sortBy(T sortBy, V sortField);
}

package com.alpha.lainovo.service;

import com.alpha.lainovo.service.ServiceInterface.SortInterface;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service("sortEntities")
public class SortService implements SortInterface<String,String> {
    @Override
    public Sort sortBy(String sortBy, String sortField) {
        Sort sort = (sortBy == null || sortBy.equals("asc")) ? Sort.by(Sort.Direction.ASC, (String)sortField)
                : Sort.by(Sort.Direction.DESC, (String)sortField);
        return sort;
    }
}

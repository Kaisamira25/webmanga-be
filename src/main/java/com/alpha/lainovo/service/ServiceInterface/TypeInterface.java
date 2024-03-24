package com.alpha.lainovo.service.ServiceInterface;



import com.alpha.lainovo.dto.request.TypeDTO;
import com.alpha.lainovo.model.Cover;
import com.alpha.lainovo.model.Type;

import java.util.List;
import java.util.Optional;

public interface TypeInterface extends ICreateAndUpdateV2<Integer, Type> {

    boolean delete(Integer id);

    Type getByTypeId(Integer id);
    List<Type> getAllType();

    List<Type> getTypeListbyType(String type);
}

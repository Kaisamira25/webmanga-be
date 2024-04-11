package com.alpha.lainovo.service.ServiceInterface;

import com.alpha.lainovo.model.Image;
import com.alpha.lainovo.model.Orders;

import java.util.List;

public interface OrdersInterface extends ICreateAndUpdateV2<Integer, Orders>{
    List<Orders> findAll();
}

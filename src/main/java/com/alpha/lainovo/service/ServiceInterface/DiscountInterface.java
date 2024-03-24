package com.alpha.lainovo.service.ServiceInterface;

import com.alpha.lainovo.model.Discount;
import com.alpha.lainovo.model.Genre;

import java.util.List;

public interface DiscountInterface extends ICreateAndUpdateV2<Integer, Discount> {

    boolean delete(Integer id);

    Discount getByDiscountId(Integer id);
    List<Discount> getAllDiscount();

    List<Discount> getDiscountListbyDiscountName(String discountName);
}

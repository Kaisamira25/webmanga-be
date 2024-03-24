package com.alpha.lainovo.service;

import com.alpha.lainovo.model.Discount;
import com.alpha.lainovo.model.Genre;
import com.alpha.lainovo.repository.DiscountRepository;
import com.alpha.lainovo.repository.GenreRepository;
import com.alpha.lainovo.service.ServiceInterface.DiscountInterface;
import com.alpha.lainovo.service.ServiceInterface.GenreInterface;
import com.alpha.lainovo.utilities.time.Time;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DiscountService implements DiscountInterface {

    private final DiscountRepository discountRepo;

    @Override
    @Cacheable(cacheNames = "Discount", key = "'#id'")
    public Discount getByDiscountId(Integer id) {
        return discountRepo.findById( (Integer) id).orElse(null);
    }

    @Override
    @Cacheable(cacheNames = "Discount", key = "'#discountList'")
    public List<Discount> getAllDiscount() {
        return discountRepo.findAll();
    }

    @Override
    public List<Discount> getDiscountListbyDiscountName(String discountName) {
        return discountRepo.findDiscountsByDiscountNameContains(discountName);
    }


    @Override
    public Object create(Discount discountDTO) {
        Discount discounts = new Discount();
        discounts.setDiscountName(discountDTO.getDiscountName());
        discounts.setDescription(discountDTO.getDescription());
        discounts.setDiscountPercent(discountDTO.getDiscountPercent());
        discounts.setActive(discountDTO.getActive());
        discounts.setDiscountCode(discountDTO.getDiscountCode());
        discounts.setCreatedAt(Time.getTheTimeRightNow());
        discounts.setExpirationDate(discountDTO.getExpirationDate());
        discountRepo.save(discounts);
        log.info(">>>>>> DiscountServiceImp:save | Create a Genre with name:{} ", discounts.getDiscountName());
        return discounts;
    }

    @Override
    @Cacheable(cacheNames = "Discount", key = "'#id'")
    public Discount update(Integer id, Discount discountDTO) {
        Discount discounts = getByDiscountId(id);
        if (discounts != null) {
            discounts.setDiscountName(discountDTO.getDiscountName());
            discounts.setDescription(discountDTO.getDescription());
            discounts.setDiscountPercent(discountDTO.getDiscountPercent());
            discounts.setActive(discountDTO.getActive());
            discounts.setDiscountCode(discountDTO.getDiscountCode());
            discounts.setCreatedAt(Time.getTheTimeRightNow());
            discounts.setExpirationDate(discountDTO.getExpirationDate());
            discountRepo.save(discounts);
            log.info(">>>>>> DiscountServiceImp:update | Update a Discount with ID:{} ", discounts.getDiscountId());
            return discounts;
        }
        log.error(">>>>>>> DiscountServiceImp:update | No Discount found to update with id: {} ",id);
        return null;
    }

    @Override
    @CacheEvict(cacheNames = "Discount", key = "'#id'", allEntries = true)
    public boolean delete(Integer id) {
        Discount discounts = getByDiscountId(id);
        if (discounts != null) {
            discountRepo.delete(discounts);
            return true;
        }
        return false;
    }

    @Scheduled(cron = "0 30 13 * * ?") // Chạy vào mỗi ngày lúc 12 giờ đêm
    // (cron = "giây phút giờ * * ?")
    public void deactivateExpiredDiscounts() {
        Date now = new Date();
        List<Discount> allDiscounts = discountRepo.findAll();
        allDiscounts.forEach(discount -> {
            if (discount.getExpirationDate().before(now) && discount.getActive()) {
                discount.setActive(false);
                discountRepo.save(discount);
                log.info("Deactivated expired discount: {}", discount.getDiscountName());
            }
        });
    }

    /**
     * Đoạn mã này thực hiện việc kiểm tra và cập nhật trạng thái của các mã giảm giá đã hết hạn. Dưới đây là giải thích chi tiết:
     *
     * @Scheduled(cron = "0 0 24 * * ?"): Annotation này định cấu hình cho phương thức deactivateExpiredDiscounts được chạy tự động mỗi ngày vào lúc 24 giờ (tức là 12 giờ đêm).
     * Định dạng "0 0 24 * * ?" là một biểu thức cron, nó định nghĩa thời gian chạy của tác vụ.
     *
     * public void deactivateExpiredDiscounts(): Đây là phương thức thực hiện việc kiểm tra và cập nhật trạng thái của các mã giảm giá.
     *
     * Date now = new Date();: Tạo một đối tượng Date mới với thời gian hiện tại.
     *
     * List<Discount> allDiscounts = discountRepo.findAll();: Lấy tất cả các mã giảm giá từ cơ sở dữ liệu.
     *
     * allDiscounts.forEach(discount -> {...}): Duyệt qua từng mã giảm giá trong danh sách.
     *
     * if (discount.getExpirationDate().before(now) && discount.getActive()) {...}:
     * Kiểm tra xem ngày hết hạn của mã giảm giá có trước thời gian hiện tại và mã giảm giá đó có đang hoạt động hay không.
     *
     * discount.setActive(false);: Nếu mã giảm giá đã hết hạn và vẫn đang hoạt động, thì cập nhật trạng thái của nó thành không hoạt động.
     *
     * discountRepo.save(discount);: Lưu lại thay đổi vào cơ sở dữ liệu.
     *
     * log.info("Deactivated expired discount: {}", discount.getDiscountName());: Ghi log thông tin về việc mã giảm giá đã hết hạn được cập nhật trạng thái.
     */

}

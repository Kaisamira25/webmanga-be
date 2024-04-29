package com.alpha.lainovo.controller.Order;

import com.alpha.lainovo.dto.request.FormDate;
import com.alpha.lainovo.dto.request.FormYear;
import com.alpha.lainovo.dto.request.OrderDTO;
import com.alpha.lainovo.dto.request.OrderItemDTO;
import com.alpha.lainovo.dto.response.Message;
import com.alpha.lainovo.dto.response.Statis;
import com.alpha.lainovo.model.*;
import com.alpha.lainovo.repository.GuestRepository;
import com.alpha.lainovo.repository.OrdersItemRepository;
import com.alpha.lainovo.repository.OrdersRepository;
import com.alpha.lainovo.repository.PublicationsRepository;
import com.alpha.lainovo.service.ServiceInterface.CustomerInterface;
import com.alpha.lainovo.service.ServiceInterface.DiscountInterface;
import com.alpha.lainovo.service.ServiceInterface.OrdersInterface;
import com.alpha.lainovo.service.ServiceInterface.PublicationsInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.criteria.Order;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final ModelMapper modelMapper;
    private final CustomerInterface Icus;
    private final DiscountInterface Idis;
    private final OrdersRepository repoOrder;
    private final PublicationsInterface Ipub;
    private final OrdersItemRepository repoItem;
    private final OrdersInterface Iorders;
    private final GuestRepository GRepo;
    @Operation(summary = "Get Customer Info", description = "Get Customer Info", responses = {
            @ApiResponse(description = "success", responseCode = "200"),
            @ApiResponse(description = "Customer not found", responseCode = "404")})
    @PostMapping()
    public ResponseEntity<Message> addOrderAndDetail(@RequestBody OrderDTO orderDTO) {
        System.out.println(orderDTO.getOrderItem().size());
        Orders orders = modelMapper.map(orderDTO,Orders.class);
        if(orderDTO.getStatus()){
            orders.setCustomer(Icus.findByEmail(orderDTO.getEmail()));
            orders.setFullname(orderDTO.getFullname());
        }else{
            Guest guest=new Guest();
            guest.setFullName(orderDTO.getFullname());
            guest.setEmail(orderDTO.getEmail());
            guest.setPhoneNumber(orderDTO.getPhoneNumber());
            guest.setAddresses(orderDTO.getAddress());
            Guest guests=GRepo.save(guest);
            orders.setGuests(guests);
        }
        orders.setOrderStatus("Wait for confirmation!");
        orders.setOrderDay(new Date());
        if(orderDTO.getDiscount()!= null){
            orders.setDiscount(Idis.getByDiscountId(orderDTO.getDiscount()));
        }else{
            orders.setDiscount(null);
        }
        orders.setOrderItem(null);
        Orders save=repoOrder.save(orders);
        for(int i=0;i<orderDTO.getOrderItem().size();i++){
            OrderItem orderItem=new OrderItem();
            orderItem.setPublications(Ipub.getByPublicationsId(orderDTO.getOrderItem().get(i).getId()));
            orderItem.setOrders(save);
            orderItem.setQuantity(orderDTO.getOrderItem().get(i).getQty());
            repoItem.save(orderItem);
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Message(0, "Order Adding Complete", save));
    }

    @GetMapping("/getAll")
    public ResponseEntity<Message> GetAllOrder(){
        List<Orders> list= Iorders.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(new Message(0, "Order Adding Complete", list));
    }

    @GetMapping("/getOrder/{customerId}")
    public ResponseEntity<Message> GetAllOrderbyCustomer(@PathVariable("customerId") Integer customerId){
        Customer customer=Icus.findById(customerId);
        List<Orders> list= Iorders.findbyCustomer(customer);
        return ResponseEntity.status(HttpStatus.OK).body(new Message(0, "Order Adding Complete", list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> GetAllOrderbyId(@PathVariable("id") Integer id){
        Orders orders= Iorders.findbyId(id);
        return ResponseEntity.status(HttpStatus.OK).body(new Message(0, "Order Adding Complete", orders));
    }
    @PutMapping("/update")
    public ResponseEntity<Message> updateOrder(@RequestBody Orders order){
        System.out.println(order);
        for (OrderItem orderItem : order.getOrderItem()) {
            orderItem.setOrders(order); // Cập nhật order_id cho orderItem
        }
        Orders orders=repoOrder.save(order);
        return ResponseEntity.status(HttpStatus.OK).body(new Message(0, "Order Adding Complete", order));
    }
    @GetMapping("/revenue")
    public ResponseEntity<Message> getRevenue(){
        List<Orders> list= Iorders.findbyStatus("Delivered!",true);
        return ResponseEntity.status(HttpStatus.OK).body(new Message(0, "Order Adding Complete",list));
    }
    @PostMapping("/revenue/month")
    public ResponseEntity<Message> getRevenuewithDate(@RequestBody FormDate formdate) throws ParseException {
        List<Statis> orders = new ArrayList<>();
        List<Orders> list= Iorders.findbyStatus("Delivered!", true);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        Date dayFrom = formatter.parse(formdate.getDayFrom());
        Date dayTo = formatter.parse(formdate.getDayTo());
        Calendar calFrom = Calendar.getInstance();
        calFrom.setTime(dayFrom);
        Calendar calTo = Calendar.getInstance();
        calTo.setTime(dayTo);
        List<String> months = new ArrayList<>();
        Calendar calTemp = Calendar.getInstance();
        calTemp.setTime(dayFrom);
        while (!calTemp.after(calTo)) {
            String monthYear = String.valueOf(calTemp.get(Calendar.MONTH) + 1) + "/" + calTemp.get(Calendar.YEAR);
            months.add(monthYear);
            calTemp.add(Calendar.MONTH, 1);
        }
        // Khởi tạo danh sách orders với mỗi tháng
        for (String month : months) {
            Statis statis = new Statis();
            statis.setLabel(month);
            statis.setTotal(0.0);
            orders.add(statis);
        }
        for (Orders order : list) {
            Calendar calOrder = Calendar.getInstance();
            calOrder.setTime(order.getOrderDay());
            String orderMonth = String.valueOf(calOrder.get(Calendar.MONTH) + 1) + "/" + calOrder.get(Calendar.YEAR);
            for (Statis sta : orders) {
                if (sta.getLabel().equals(orderMonth)) {
                    sta.setTotal(sta.getTotal() + order.getTotalPrice());
                    break;
                }
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Message(0, "Complete",orders));
    }
    @PostMapping("/revenue/year")
    public ResponseEntity<Message> getReveneuwithYear(@RequestBody FormYear year) {
        List<Statis> orders = new ArrayList<>();
        List<Orders> list = Iorders.findbyStatus("Delivered!", true);

        // Lấy năm bắt đầu và kết thúc từ đối tượng FormYear
        int startYear = year.getYearStart();
        int endYear = year.getYearEnd();

        // Khởi tạo danh sách các năm từ startYear đến endYear
        List<Integer> years = new ArrayList<>();
        for (int i = startYear; i <= endYear; i++) {
            years.add(i);
        }

        // Khởi tạo danh sách orders với mỗi năm
        for (int yearNum : years) {
            Statis statis = new Statis();
            statis.setLabel(String.valueOf(yearNum));
            statis.setTotal(0.0);
            orders.add(statis);
        }

        // Tính tổng doanh thu cho mỗi năm
        for (Orders order : list) {
            Calendar calOrder = Calendar.getInstance();
            calOrder.setTime(order.getOrderDay());
            int orderYear = calOrder.get(Calendar.YEAR);
            double totalPrice = order.getTotalPrice();

            // Duyệt qua danh sách orders và cập nhật tổng doanh thu cho năm tương ứng
            for (Statis sta : orders) {
                if (Integer.parseInt(sta.getLabel()) == orderYear) {
                    sta.setTotal(sta.getTotal() + totalPrice);
                    break;
                }
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Message(0, "Complete",orders));

    }
}

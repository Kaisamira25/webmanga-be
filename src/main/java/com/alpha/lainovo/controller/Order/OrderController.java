package com.alpha.lainovo.controller.Order;

import com.alpha.lainovo.dto.request.OrderDTO;
import com.alpha.lainovo.dto.request.OrderItemDTO;
import com.alpha.lainovo.dto.response.Message;
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
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
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
            orders.setFullname(orderDTO.getFullName());
        }else{
            Guest guest=new Guest();
            guest.setFullName(orderDTO.getFullName());
            guest.setEmail(orderDTO.getEmail());
            guest.setPhoneNumber(orderDTO.getPhoneNumber());
            guest.setAddresses(orderDTO.getAddress());
            Guest guests=GRepo.save(guest);
            orders.setGuest(guests);
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
    @SecurityRequirement(name="bearerAuth")
    public ResponseEntity<Message> updateOrder(@RequestBody Orders order){
        for (OrderItem orderItem : order.getOrderItem()) {
            orderItem.setOrders(order); // Cập nhật order_id cho orderItem
        }
        Orders orders=repoOrder.save(order);
        return ResponseEntity.status(HttpStatus.OK).body(new Message(0, "Order Adding Complete", order));
    }
    @GetMapping("/revenue")
    public ResponseEntity<Message> getRevenue(){
        List<Orders> list= Iorders.findbyStatus("Delivered!",true);
        System.out.println(list.size());
        return ResponseEntity.status(HttpStatus.OK).body(new Message(0, "Order Adding Complete",list));
    }
}

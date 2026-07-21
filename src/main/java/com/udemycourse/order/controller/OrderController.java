package com.udemycourse.order.controller;

import com.udemycourse.order.dto.OrderDTO;
import com.udemycourse.order.dto.OrderDTOFromFE;
import com.udemycourse.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "*")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/saveOrder")
    public ResponseEntity<OrderDTO> saveOrder(@RequestBody OrderDTOFromFE orderDTOFromFE){
        OrderDTO orderDTO = orderService.saveOrder(orderDTOFromFE);
        return new ResponseEntity<>(orderDTO, HttpStatus.CREATED);
    }
}

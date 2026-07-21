package com.udemycourse.order.service;

import com.udemycourse.order.Mapper.OrderMapper;
import com.udemycourse.order.dto.OrderDTO;
import com.udemycourse.order.dto.OrderDTOFromFE;
import com.udemycourse.order.dto.UserDTO;
import com.udemycourse.order.entity.Order;
import com.udemycourse.order.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    SequenceGenerator sequenceGenerator;

    @Autowired
    RestTemplate restTemplate;

    public OrderDTO saveOrder(OrderDTOFromFE orderDTOFromFE) {
        Order orderToBeSaved = new Order();

        UserDTO userDto = fetchUserDtoById(orderDTOFromFE.getUserId());
        int orderId = sequenceGenerator.generateNextOrderId();
        orderToBeSaved.setOrderId(orderId);
        orderToBeSaved.setUserDTO(userDto);
        orderToBeSaved.setRestaurant(orderDTOFromFE.getRestaurant());
        orderToBeSaved.setFoodItemsList(orderDTOFromFE.getFoodItemsList());
        orderRepo.save(orderToBeSaved);
        return OrderMapper.INSTANCE.orderToOrderDTO(orderToBeSaved);
    }

    private UserDTO fetchUserDtoById(Integer userId) {
        return restTemplate.getForObject("http://USER-SERVICE/user/fetchById/"+userId, UserDTO.class);

    }
}

package com.udemycourse.order.controller;

import com.udemycourse.order.dto.*;
import com.udemycourse.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class OrderControllerTest {

    @InjectMocks
    OrderController orderController;

    @Mock
    OrderService orderService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveOrder(){
        Restaurant restaurant = new Restaurant();
        restaurant.setId(10);
        restaurant.setName("Good Food Restaurant");
        restaurant.setAddress("123 Main Street");
        restaurant.setCity("Paris");
        restaurant.setRestaurantDescription(
                "A family-friendly restaurant serving Italian and American cuisine."
        );

        FoodItemsDTO pizza = new FoodItemsDTO();
        pizza.setId(1);
        pizza.setItemName("Margherita Pizza");
        pizza.setItemDescription("Classic pizza with tomato sauce, mozzarella, and basil");
        pizza.setVeg(true);
        pizza.setPrice(1299L);
        pizza.setRestaurantId(10);
        pizza.setQuantity(2);

        FoodItemsDTO burger = new FoodItemsDTO();
        burger.setId(2);
        burger.setItemName("Chicken Burger");
        burger.setItemDescription("Grilled chicken burger with lettuce and cheese");
        burger.setVeg(false);
        burger.setPrice(999L);
        burger.setRestaurantId(10);
        burger.setQuantity(1);

        OrderDTOFromFE mockOrder = new OrderDTOFromFE();
        mockOrder.setUserId(123);
        mockOrder.setRestaurant(restaurant);
        mockOrder.setFoodItemsList(List.of(pizza, burger));

        UserDTO user = new UserDTO();
        user.setId(123);
        user.setUserName("John Doe");
        user.setUserPassword("12345");
        user.setAddress("user address");
        user.setCity("user city");

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(1001);
        orderDTO.setRestaurant(restaurant);
        orderDTO.setUserDTO(user);
        orderDTO.setFoodItemsList(List.of(pizza, burger));
        Mockito.when(orderService.saveOrder(mockOrder)).thenReturn(orderDTO);

        // call the controller method
        ResponseEntity<OrderDTO> response = orderController.saveOrder(mockOrder);

        // verify the result
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(orderDTO,response.getBody());

        verify(orderService,times(1)).saveOrder(mockOrder);

    }

}

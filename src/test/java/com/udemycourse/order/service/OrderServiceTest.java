package com.udemycourse.order.service;

import com.udemycourse.order.Mapper.OrderMapper;
import com.udemycourse.order.dto.OrderDTO;
import com.udemycourse.order.dto.OrderDTOFromFE;
import com.udemycourse.order.dto.UserDTO;
import com.udemycourse.order.entity.Order;
import com.udemycourse.order.repository.OrderRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @InjectMocks
    OrderService orderService;

    @Mock
    OrderRepo orderRepo;

    @Mock
    RestTemplate restTemplate;

    @Mock
    SequenceGenerator sequenceGenerator;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveOrder(){
        int id = 0;
        OrderDTOFromFE orderDTOFromFE = new OrderDTOFromFE();
        Order order = new Order();

        UserDTO userDTO = new UserDTO();
        order.setOrderId(id);
        order.setUserDTO(userDTO);
        when(sequenceGenerator.generateNextOrderId()).thenReturn(id);
        when(restTemplate.getForObject(anyString(),eq(UserDTO.class))).thenReturn(userDTO);
        when(orderRepo.save(order)).thenReturn(order);

        OrderDTO response = orderService.saveOrder(orderDTOFromFE);

        assertEquals(response, OrderMapper.INSTANCE.orderToOrderDTO(order));

        verify(orderRepo,times(1)).save(order);
        verify(restTemplate,times(1)).getForObject(anyString(),eq(UserDTO.class));
        verify(sequenceGenerator, times(1)).generateNextOrderId();

    }
}

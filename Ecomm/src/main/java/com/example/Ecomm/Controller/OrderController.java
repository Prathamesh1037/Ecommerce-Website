package com.example.Ecomm.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Ecomm.DTO.OrderDTO;
import com.example.Ecomm.Entity.OrderRequest;
import com.example.Ecomm.Service.OrderService;

@RestController
@RequestMapping("/products")
@CrossOrigin("*")
public class OrderController {
   
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/all-orders")
	public OrderDTO placeOrder(@PathVariable Long userId,@RequestBody OrderRequest orderRequest) {
		 return orderService.placeOrder(userId,orderRequest.getProductQuantities(),orderRequest.getTotalAmount());
	}
	
	@GetMapping("/user/{userId}")
	public List<OrderDTO> getOrderByUser(@PathVariable Long userId)
	{
		return orderService.getOrderByUser(userId);
	}
}

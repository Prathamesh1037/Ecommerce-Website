package com.example.Ecomm.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Ecomm.Entity.Product;
import com.example.Ecomm.Entity.OrderItem;
import com.example.Ecomm.Entity.Orders;
import com.example.Ecomm.Entity.User;
import com.example.Ecomm.DTO.OrderDTO;
import com.example.Ecomm.DTO.OrderItemDTO;
import com.example.Ecomm.Repository.OrderRepository;
import com.example.Ecomm.Repository.ProductRepository;
import com.example.Ecomm.Repository.UserRepository;

@Service
public class OrderService {
	@Autowired
    private UserRepository userRepository;
	@Autowired
    private ProductRepository productRepository;
	@Autowired
    private OrderRepository orderRepository;
    
    public OrderDTO placeOrder(Long userId,Map<Long , Integer> productQuantities, double totalAmount)
    {
    	User user = userRepository.findById(userId)
    			.orElseThrow(()->new RuntimeException("User not found"));
    	
    	Orders order = new Orders();
    	order.setUser(user);
    	order.setOrderDate(new Date());
    	order.setStatus("Pending");
    	order.setTotalAmount(totalAmount);
    	
    	List<OrderItem> orderItems = new ArrayList<>();
    	List<OrderItemDTO> orderItemsDTOS = new ArrayList<>();
    	for(Map.Entry<Long,Integer> entry : productQuantities.entrySet())
    	{
    		Product product = productRepository.findById(entry.getKey())
    				          .orElseThrow(()-> new RuntimeException("Product Not Found"));
    		
    		OrderItem orderItem = new OrderItem();
    		orderItem.setOrder(order);
    		orderItem.setProduct(product);
    		orderItem.setQuantity(entry.getValue());
    		orderItemsDTOS.add(new OrderItemDTO(product.getName(),product.getPrice(),entry.getValue()));
    	}
    	order.setOrderItems(orderItems);
    	Orders saveOrder = orderRepository.save(order);
    	return new OrderDTO(saveOrder.getId(),saveOrder.getTotalAmount(),saveOrder.getStatus(),saveOrder.getOrderDate(),orderItemsDTOS);
    }
    	public List<OrderDTO> getAllOrders(){
    		List<Orders> orders = orderRepository.findAllOrdersWithUsers();
    		return orders.stream().map(this::convertToDTO).collect(Collectors.toList());
    	}
    	
    	private OrderDTO convertToDTO(Orders orders) {
    		List<OrderItemDTO> orderItems = orders.getOrderItems().stream()
    				           .map(item -> new OrderItemDTO(item.getProduct().getName(),item.getProduct().getPrice(),item.getQuantity())).collect(Collectors.toList());
    		                   return new OrderDTO(orders.getId(),
    		                		      orders.getTotalAmount(),
    		                		      orders.getStatus(),
    		                		      orders.getOrderDate(),
    		                		      orders.getUser()!=null?orders.getUser().getName():"UnKnown",
    		                		      orders.getUser()!=null?orders.getUser().getName():"UnKnown",
    		                		      orderItems);
    	}
    	
    	public List<OrderDTO> getOrderByUser(Long userId){
    		 Optional <User> userOp = userRepository.findById(userId);
    		 if(userOp.isEmpty()) {
    			 throw new RuntimeException("user not found");
    		 }
    		 User user = userOp.get();
    		 List<Orders> ordersList = orderRepository.findByUser(user);
    		 return ordersList.stream().map(this::convertToDTO).collect(Collectors.toList());
         }
}	


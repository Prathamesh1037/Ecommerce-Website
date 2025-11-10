package com.example.Ecomm.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.Ecomm.Entity.Orders;
import com.example.Ecomm.Entity.User;

public interface OrderRepository extends JpaRepository<Orders,Long>{

	Orders save(Orders order);
    @Query("Select o from Orders o JOIN FETCH o.user")
	List<Orders> findAllOrdersWithUsers();

	List<Orders> findByUser(User user);

	

}

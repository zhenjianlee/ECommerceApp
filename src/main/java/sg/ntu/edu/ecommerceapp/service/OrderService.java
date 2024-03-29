package sg.ntu.edu.ecommerceapp.service;

import java.util.ArrayList;
import java.util.List;

import sg.ntu.edu.ecommerceapp.entity.*;

public interface OrderService {


    // Order createOrder (long customerId, long productId);
    Order createOrder (long addressId);

    // List<CartItem> addCartItem(long orderId, long cartItemId);
    // List<CartItem> addCartItem(long orderId, long productId, int quantity);

    Order getOrder(long id);

    ArrayList<Order> getAllOrders();

    Order setOrder(long id, long addressId , long cartItemId);

    Order deleteOrder(long id);
}

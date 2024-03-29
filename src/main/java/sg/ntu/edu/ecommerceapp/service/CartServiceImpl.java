package sg.ntu.edu.ecommerceapp.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import sg.ntu.edu.ecommerceapp.entity.Cart;
import sg.ntu.edu.ecommerceapp.entity.CartItem;
import sg.ntu.edu.ecommerceapp.entity.Customer;
import sg.ntu.edu.ecommerceapp.exception.CartItemNotFoundException;
import sg.ntu.edu.ecommerceapp.exception.CartNotFoundException;
import sg.ntu.edu.ecommerceapp.exception.CustomerNotFoundException;
import sg.ntu.edu.ecommerceapp.repository.CartItemRepository;
import sg.ntu.edu.ecommerceapp.repository.CartRepository;
import sg.ntu.edu.ecommerceapp.repository.CustomerRepository;
import sg.ntu.edu.ecommerceapp.entity.Customer;

@Service
public class CartServiceImpl implements CartService {


    private CartRepository cartRepository;
    // Jian edited start 231212 --------- >
    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);
    private CartItemRepository cartItemRepository;
    private CustomerRepository customerRepository;

    public CartServiceImpl(CartRepository cartRepository,
                            CartItemRepository cartItemRepository,
                            CustomerRepository customerRepository) { //added
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository; //added
        this.customerRepository = customerRepository; //added
    }
    // Jian edited end 231212 <---------
    @Override
    public Cart createCart(Cart cart) {
        Cart newCart = cartRepository.save(cart);
        return newCart;
    }

    @Override
    public ArrayList<Cart> getAllCarts() {
        List<Cart> allCarts = cartRepository.findAll();
        return (ArrayList<Cart>) allCarts;
    }

    @Override
    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }

    @Override
    public Cart updateCart(Long id, Cart cart) {
        Cart cartToUpdate = cartRepository.findById(id).orElseThrow(() -> new CartNotFoundException(id));
        // update the cart retrieved fromn the database
        cartToUpdate.setCartTotal(cart.getCartTotal());
        cartToUpdate.setCartItems(cart.getCartItems());
        cartToUpdate.setCustomer(cart.getCustomer());
        // save the updated cart back to the database
        return cartRepository.save(cartToUpdate);
    }

    @Override
    public Cart getCart(Long id) {
        return cartRepository.findById(id).orElseThrow(() -> new CartNotFoundException(id));
    }

    // Jian insert 231212 --------- >
    public Cart createCartById (long customerId){

        Cart newCart = new Cart();
        Customer foundCustomer = null;

        try{
            foundCustomer = customerRepository.findById(customerId)
                .orElseThrow(()-> new CustomerNotFoundException(customerId){});
        }catch(CartItemNotFoundException e){
            logger.error("🛒🔴 Error cannot find customerId " + customerId + " error " + e);
        }
        newCart.setCustomer(foundCustomer);

        try{
            cartRepository.saveAndFlush(newCart);
        }catch (Exception e){
            logger.error("🛒🔴 Error saving  " + newCart + " error " + e);
        }
        return newCart;

        // establish relationship one at a time
        // establish newCart.setCustomer(foundCustomer);
        // cartRepository.save(newCart);

        //business logic
        // cart should exist first
        // then only cartItem can exist
    }
    // end               <-------------

}

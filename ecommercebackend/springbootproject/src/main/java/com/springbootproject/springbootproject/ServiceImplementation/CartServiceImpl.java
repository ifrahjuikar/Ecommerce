package com.springbootproject.springbootproject.ServiceImplementation;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.springbootproject.springbootproject.Entitities.Cart;
import com.springbootproject.springbootproject.Entitities.Product;
import com.springbootproject.springbootproject.Entitities.User;
import com.springbootproject.springbootproject.Jwt.JwtAuthenticationFilter;
import com.springbootproject.springbootproject.Repositories.CartRepository;
import com.springbootproject.springbootproject.Repositories.ProductRepository;
import com.springbootproject.springbootproject.Repositories.UserRepository;
import com.springbootproject.springbootproject.Service.CartService;
import java.util.List;
import java.util.stream.*;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
        private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;


    @Override
    public Cart addToCart(Integer productId){

        Product product=productRepository.findById(productId).get();
        
        String username= JwtAuthenticationFilter.CURRENT_USER;

        User user =null;
        if(username !=null){
            user=userRepository.findById(username).get();
        }

        List<Cart> cartList=cartRepository.findByUser(user);
        List<Cart> filteredList=cartList.stream().filter(x->x.getProduct().getProductId()==productId).collect(Collectors.toList());

        if(filteredList.size()>0){
            return null;
        }

        if(product !=null && user!=null){
            Cart cart=new Cart();
            cart.setProduct(product);
            cart.setUser(user);
            return cartRepository.save(cart);
        }
        return null;

    }


    @Override
    public List<Cart> getCartDetails(){
        String username=JwtAuthenticationFilter.CURRENT_USER;
        User user=userRepository.findById(username).get();
        return cartRepository.findByUser(user);


    }

    @Override
    public void deleteCartItem(Integer cartId){
        cartRepository.deleteById(cartId);

    }

    
}

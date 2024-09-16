package com.springbootproject.springbootproject.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.springbootproject.springbootproject.Entitities.Cart;
import com.springbootproject.springbootproject.Exception.ApiResponse;
import com.springbootproject.springbootproject.Service.CartService;

import java.util.List;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @PreAuthorize("hasRole('User')")
    @GetMapping("/addToCart/{productId}")
    public ResponseEntity<?> addToCart(@PathVariable(name = "productId") Integer productId) {
        Cart cart = cartService.addToCart(productId);
        return new ResponseEntity<>(new ApiResponse("success", HttpStatus.OK.value(), "OK", cart), HttpStatus.OK);

    }

    @PreAuthorize("hasRole('User')")
    @GetMapping("/getCartDetails")
    public ResponseEntity<?> getCartDetails(){
        List<Cart> cartList= cartService.getCartDetails();
        return new ResponseEntity<>(new ApiResponse("success", HttpStatus.OK.value(), "OK", cartList), HttpStatus.OK);

    }

    @PreAuthorize("hasRole('User')")
    @DeleteMapping("/deleteCartItem/{cartId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable(name="cartId") Integer cartId){

        cartService.deleteCartItem(cartId);

        return new ResponseEntity<>(new ApiResponse("success", HttpStatus.OK.value(), "OK", null), HttpStatus.OK);


    }


    
}

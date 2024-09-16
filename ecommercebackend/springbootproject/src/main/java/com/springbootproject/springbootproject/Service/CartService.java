package com.springbootproject.springbootproject.Service;

import com.springbootproject.springbootproject.Entitities.Cart;

import java.util.List;

public interface CartService {


    public Cart addToCart(Integer productId);

    public List<Cart> getCartDetails();

    public void deleteCartItem(Integer cartId);
}

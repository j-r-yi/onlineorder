package com.laioffer.onlineorder.service;


import com.laioffer.onlineorder.entity.CartEntity;
import com.laioffer.onlineorder.entity.MenuItemEntity;
import com.laioffer.onlineorder.entity.OrderItemEntity;
import com.laioffer.onlineorder.model.CartDto;
import com.laioffer.onlineorder.model.OrderItemDto;
import com.laioffer.onlineorder.repository.CartRepository;
import com.laioffer.onlineorder.repository.MenuItemRepository;
import com.laioffer.onlineorder.repository.OrderItemRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;


@Service
public class CartService {


    // Dependencies passed in through constructor
    private final CartRepository cartRepository;
    private final MenuItemRepository menuItemRepository;
    private final OrderItemRepository orderItemRepository;


    public CartService(
            CartRepository cartRepository,
            MenuItemRepository menuItemRepository,
            OrderItemRepository orderItemRepository) {
        this.cartRepository = cartRepository;
        this.menuItemRepository = menuItemRepository;
        this.orderItemRepository = orderItemRepository;
    }


    // ACID, default is isolated
    @CacheEvict(cacheNames = "cart", key = "#customerId") // Cache Evict so that updates cache each time
    @Transactional
    public void addMenuItemToCart(long customerId, long menuItemId) {
        CartEntity cart = cartRepository.getByCustomerId(customerId);
        // defined by spring data jdbc
        MenuItemEntity menuItem = menuItemRepository.findById(menuItemId).get();
        // does this item already exist? if not add order else update current item
        OrderItemEntity orderItem = orderItemRepository.findByCartIdAndMenuItemId(cart.id(), menuItem.id());


        Long orderItemId;
        int quantity;

        // First time ordering order item (doesn't have ID yet in cart)
        if (orderItem == null) {
            orderItemId = null;
            quantity = 1;
            // otherwise order item already exists in cart then increment item count
        } else {
            orderItemId = orderItem.id();
            quantity = orderItem.quantity() + 1;
        }

        // create orderItem entity with id and quantity
        OrderItemEntity newOrderItem = new OrderItemEntity(orderItemId, menuItemId, cart.id(), menuItem.price(), quantity);
        // .save() if id is null its an insert, otherwise it is an update
        orderItemRepository.save(newOrderItem);
        cartRepository.updateTotalPrice(cart.id(), cart.totalPrice() + menuItem.price());
    }


    // Get list of all order items
    @Cacheable("cart")
    public CartDto getCart(Long customerId) {
        CartEntity cart = cartRepository.getByCustomerId(customerId);
        List<OrderItemEntity> orderItems = orderItemRepository.getAllByCartId(cart.id());
        List<OrderItemDto> orderItemDtos = getOrderItemDtos(orderItems);
        return new CartDto(cart, orderItemDtos);
    }


    // No true transactional features, only clears the cart after frontend checkout request
    @CacheEvict(cacheNames = "cart", key = "#customerId") // Cache Evict so that updates cache each time
    @Transactional
    public void clearCart(Long customerId) {
        CartEntity cartEntity = cartRepository.getByCustomerId(customerId);
        orderItemRepository.deleteByCartId(cartEntity.id());
        cartRepository.updateTotalPrice(cartEntity.id(), 0.0);
    }


    private List<OrderItemDto> getOrderItemDtos(List<OrderItemEntity> orderItems) {
        List<OrderItemDto> orderItemDtos = new ArrayList<>();

        // Flaw here is that SQL query for each iteration of for loop, sends network request each time
            // should just get all ID at same time and get all at one request (using streams)
        for (OrderItemEntity orderItem : orderItems) {
            MenuItemEntity menuItem = menuItemRepository.findById(orderItem.menuItemId()).get();
            OrderItemDto orderItemDto = new OrderItemDto(orderItem, menuItem);
            orderItemDtos.add(orderItemDto);
        }
        return orderItemDtos;
    }
}

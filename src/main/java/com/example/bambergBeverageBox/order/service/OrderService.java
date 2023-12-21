package com.example.bambergBeverageBox.order.service;


import com.example.bambergBeverageBox.address.model.Address;
import com.example.bambergBeverageBox.address.service.AddressService;
import com.example.bambergBeverageBox.beverage.model.BeverageSessionResponse;
import com.example.bambergBeverageBox.cart.model.UserOrderPaymentResponse;
import com.example.bambergBeverageBox.order.model.*;
import com.example.bambergBeverageBox.user.model.User;
import com.example.bambergBeverageBox.user.service.UserModelService;
import com.example.bambergBeverageBox.util.StringUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OrderService {
    private final UserModelService userModelService;
    private final AddressService addressService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public OrderCreationResponse placeOrder(UserOrderPaymentResponse userOrderPaymentResponse,
                                            Authentication authentication,
                                            final HttpSession session) {
        OrderCreationResponse orderCreationResponse = buildOrderCreationResponse("Can not place order!", false);
        List<BeverageSessionResponse> cartItems = (List<BeverageSessionResponse>) session.getAttribute(
                StringUtil.SESSION_ATTRIBUTE_NAME_CART);

        if (authentication != null && !cartItems.isEmpty()) {
            String username = authentication.getName();
            User user = userModelService.findUserByUserName(username);
            Map<Long, BeverageSessionResponse> uniqueItemFromSessionCart = StringUtil.getUniqueItemFromSessionCart(cartItems);
            saveAddressAndSubmitOrder(userOrderPaymentResponse, user, uniqueItemFromSessionCart);
            session.removeAttribute(StringUtil.SESSION_ATTRIBUTE_NAME_CART);
            orderCreationResponse = buildOrderCreationResponse("Order Placed!", true);
        }

        return orderCreationResponse;
    }

    public OrderCreationResponse buildOrderCreationResponse(String msg, boolean orderPlaced) {
        return OrderCreationResponse.builder().msg(msg).orderPlaced(orderPlaced).build();
    }

    @Transactional
    public void saveAddressAndSubmitOrder(UserOrderPaymentResponse userOrderPaymentResponse,
                                          User user,
                                          Map<Long, BeverageSessionResponse>
                                                  uniqueItemFromSessionCart) {

        Address address = addressService.getAddressFromResponse(user, userOrderPaymentResponse);
        address = addressService.save(address);
        submitOrderAndOrderItem(user, address, uniqueItemFromSessionCart);
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public OrderItem saveOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Transactional
    public void submitOrderAndOrderItem(User user, Address address, Map<Long, BeverageSessionResponse> uniqueItemFromSessionCart) {
        Order order = new Order();
        Double totalPrice = 0.0;
        for (BeverageSessionResponse entry : uniqueItemFromSessionCart.values()) {
            totalPrice += entry.getTotalPricePerAddedItem();
        }
        order.setUserId(user.getId());
        order.setAddressId(address.getId());
        order.setTotalPrice(totalPrice);
        order = save(order);

        //save order item
        for (BeverageSessionResponse beverageSessionResponse : uniqueItemFromSessionCart.values()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setAddedItemId(beverageSessionResponse.getAddedItemId());
            orderItem.setAddedItemName(beverageSessionResponse.getAddedItemName());
            orderItem.setAddedItemPic(beverageSessionResponse.getAddedItemPic());
            orderItem.setAddedItemPrice(beverageSessionResponse.getAddedItemPrice());
            orderItem.setAddedItemQuantity(beverageSessionResponse.getAddedItemQuantity());
            orderItem.setTotalPricePerAddedItem(beverageSessionResponse.getTotalPricePerAddedItem());
            saveOrderItem(orderItem);
        }
    }

    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public List<OrderItem> findByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    public OrderViewResponse getPreviousOrders(Authentication authentication) {
        OrderViewResponse orderViewResponse = new OrderViewResponse();
        List<OrderResponse> orderResponseList = new ArrayList<>();
        if (authentication != null) {
            String username = authentication.getName();
            User user = userModelService.findUserByUserName(username);
            List<Order> orders = findByUserId(user.getId());
            for (Order order : orders) {
                List<OrderItem> orderItems = findByOrderId(order.getId());
                OrderResponse orderResponse = getOrderAndOrderItemResponse(order, orderItems);
                orderResponseList.add(orderResponse);
            }
        }
        orderViewResponse.setOrderResponseList(orderResponseList);
        return orderViewResponse;
    }

    public OrderResponse getOrderResponse(Order order) {

        OrderResponse orderResponse = new OrderResponse();

        String fullAddress = addressService.getFullAddress(order.getAddressId());

        orderResponse.setOrderId(order.getId());
        orderResponse.setUserId(order.getUserId());
        orderResponse.setAddress(fullAddress);
        orderResponse.setTotalPrice(Double.valueOf(StringUtil.df.format(order.getTotalPrice())));
        orderResponse.setOrderDate(order.getCreatedAt());

        return orderResponse;
    }

    public OrderItemResponse getOrderItemResponse(OrderItem orderItem) {

        OrderItemResponse orderItemResponse = new OrderItemResponse();

        orderItemResponse.setOrderItemId(orderItem.getId());
        orderItemResponse.setOrderId(orderItem.getOrderId());
        orderItemResponse.setAddedItemId(orderItem.getAddedItemId());
        orderItemResponse.setAddedItemName(orderItem.getAddedItemName());
        orderItemResponse.setAddedItemPic(orderItem.getAddedItemPic());
        orderItemResponse.setAddedItemPrice(orderItem.getAddedItemPrice());
        orderItemResponse.setAddedItemQuantity(orderItem.getAddedItemQuantity());
        orderItemResponse.setTotalPricePerAddedItem(orderItem.getTotalPricePerAddedItem());


        return orderItemResponse;
    }

    public OrderResponse getOrderAndOrderItemResponse(Order order, List<OrderItem> orderItems) {
        OrderResponse orderResponse = getOrderResponse(order);
        List<OrderItemResponse> orderItemResponses = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            OrderItemResponse orderItemResponse = getOrderItemResponse(orderItem);
            orderItemResponses.add(orderItemResponse);
        }
        orderResponse.setOrderItemResponseList(orderItemResponses);
        return orderResponse;
    }
}

package com.example.bambergBeverageBox.order.service;


import com.example.bambergBeverageBox.address.model.Address;
import com.example.bambergBeverageBox.address.service.AddressService;
import com.example.bambergBeverageBox.beverage.model.BeverageSessionResponse;
import com.example.bambergBeverageBox.cart.model.UserOrderPaymentResponse;
import com.example.bambergBeverageBox.order.model.Order;
import com.example.bambergBeverageBox.order.model.OrderCreationResponse;
import com.example.bambergBeverageBox.order.model.OrderItem;
import com.example.bambergBeverageBox.user.model.User;
import com.example.bambergBeverageBox.user.service.UserModelService;
import com.example.bambergBeverageBox.util.StringUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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
        addressService.save(address);
        submitOrderAndOrderItem(user, uniqueItemFromSessionCart);
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public OrderItem saveOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Transactional
    public void submitOrderAndOrderItem(User user, Map<Long, BeverageSessionResponse> uniqueItemFromSessionCart) {
        Order order = new Order();
        Double totalPrice = 0.0;
        for (BeverageSessionResponse entry : uniqueItemFromSessionCart.values()) {
            totalPrice += entry.getTotalPricePerAddedItem();
        }
        order.setUserId(user.getId());
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
}

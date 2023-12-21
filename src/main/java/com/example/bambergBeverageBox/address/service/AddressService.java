package com.example.bambergBeverageBox.address.service;


import com.example.bambergBeverageBox.address.model.Address;
import com.example.bambergBeverageBox.cart.model.UserOrderPaymentResponse;
import com.example.bambergBeverageBox.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AddressService {
    private final AddressRepository addressRepository;

    @Transactional
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    public Address getAddressFromResponse(User user, UserOrderPaymentResponse userOrderPaymentResponse) {
        Address address = new Address();
        address.setUserId(user.getId());
        address.setStreet(userOrderPaymentResponse.getStreet());
        address.setStreetNumber(userOrderPaymentResponse.getStreetNumber());
        address.setCity(userOrderPaymentResponse.getCity());
        address.setState(userOrderPaymentResponse.getState());
        address.setPostalCode(userOrderPaymentResponse.getPostalCode());
        return address;
    }
}

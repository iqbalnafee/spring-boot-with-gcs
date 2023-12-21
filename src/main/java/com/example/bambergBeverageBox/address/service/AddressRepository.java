package com.example.bambergBeverageBox.address.service;

import com.example.bambergBeverageBox.address.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository  extends JpaRepository<Address,Long> {
}

package com.example.bambergBeverageBox.address.service;

import com.example.bambergBeverageBox.address.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository  extends JpaRepository<Address,Long> {
    Optional<Address> findById(Long id);
}

package com.example.bambergBeverageBox.beverage.service;
import com.example.bambergBeverageBox.beverage.model.Beverage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeverageRepository extends JpaRepository<Beverage,Long> {
}

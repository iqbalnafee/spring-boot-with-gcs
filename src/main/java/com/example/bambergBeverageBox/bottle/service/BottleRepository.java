package com.example.bambergBeverageBox.bottle.service;

import com.example.bambergBeverageBox.beverage.model.Beverage;
import com.example.bambergBeverageBox.bottle.model.Bottle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BottleRepository extends JpaRepository<Bottle,Long>, PagingAndSortingRepository<Bottle,Long> {
    List<Bottle> findBottleByBeverageIdIn(List<Long> beverageId);
    List<Bottle> findBottleByBeverageId(Long beverageId);
}

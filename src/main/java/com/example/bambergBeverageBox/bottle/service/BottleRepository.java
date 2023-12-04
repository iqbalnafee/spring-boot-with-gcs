package com.example.bambergBeverageBox.bottle.service;

import com.example.bambergBeverageBox.bottle.model.Bottle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BottleRepository extends JpaRepository<Bottle,Long>, PagingAndSortingRepository<Bottle,Long> {

}

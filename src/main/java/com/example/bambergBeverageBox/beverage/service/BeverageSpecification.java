package com.example.bambergBeverageBox.beverage.service;

import com.example.bambergBeverageBox.beverage.model.Beverage;
import org.springframework.data.jpa.domain.Specification;


public class BeverageSpecification {

    public static Specification<Beverage> beverageNameEnLike(String nameEn){
//        return (root, query, criteriaBuilder)
//                -> criteriaBuilder.like(root.get(Beverage.beverageNameEn),"%"+nameEn+"%");

        return null;
    }
    public static Specification<Beverage> beverageNameDeLike(String nameDe){
        /*return (root, query, criteriaBuilder)
                -> criteriaBuilder.like(root.get(Beverage_.beverageNameDe),"%"+nameDe+"%");*/

        return null;
    }

}

package com.example.bambergBeverageBox.annotations;

import com.example.bambergBeverageBox.enums.Menu;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TitleAndContent {


    String title() default "";
    String content();
    Menu activeMenu() default Menu.DASHBOARD;
}

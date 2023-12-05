package com.example.bambergBeverageBox.enums;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum Menu {

    DASHBOARD,
    BEVERAGE(null),
    BEVERAGE_ADD(BEVERAGE),
    BOTTLE_ADD(BEVERAGE),
    BEVERAGE_VIEW(BEVERAGE),
    PRODUCTS(null);

    private final Menu parentMenu;

    Menu() {
        this.parentMenu = null;
    }

    Menu(Menu parentMenu) {
        this.parentMenu = parentMenu;
    }

    public Menu getParentMenu() {
        return parentMenu;
    }

    public List<Menu> findMenuPath() {
        List<Menu> menuPath = new ArrayList<>();
        Menu curr = this;
        while (curr != null) {
            menuPath.add(curr);
            curr = curr.parentMenu;
        }
        Collections.reverse(menuPath);
        return menuPath;
    }

}

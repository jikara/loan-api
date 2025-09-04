package com.softmint.dto;

import lombok.Data;

import java.util.List;

@Data
public class Menu {
    private List<MenuItem> menu;

    public Menu(List<MenuItem> menu) {
        this.menu = menu;
    }
}

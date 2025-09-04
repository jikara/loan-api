package com.softmint.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class MenuItem {
    private String route;
    private String name;
    private String type;
    private String icon;
    @Builder.Default
    private List<MenuItem> children = new ArrayList<>();
    @Builder.Default
    private List<String> permissions = new ArrayList<>();

    private boolean hidden;

    // Getters and setters
}


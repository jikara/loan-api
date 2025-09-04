package com.softmint.service;

import com.softmint.dto.Menu;
import com.softmint.dto.MenuItem;

import java.util.List;
import java.util.Set;

public interface MenuService {
    List<MenuItem> filterMenu(List<MenuItem> menu, Set<String> userPermissions);

    Menu createMenu(Set<String> permissions);
}

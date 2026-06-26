package com.tap.DAO;

import java.util.List;

import com.tap.Model.Menu;


public interface MenuDAO {
	void addMenu(Menu menu);

    void updateMenu(Menu menu);

    void deleteMenu(int id);

    Menu getMenu(int id);

    List<Menu> getAllMenu(int id);
}

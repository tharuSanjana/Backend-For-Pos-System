package dao.impl;

import Entity.Item;
import dto.ItemDto;

import java.sql.Connection;

public interface ItemDao {

    boolean saveItem(Item item, Connection connection);
    boolean updateItem(String itemId,Item item,Connection connection);
    boolean deleteItem(String itemId,Connection connection);
}

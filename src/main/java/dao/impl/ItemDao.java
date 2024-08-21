package dao.impl;

import Entity.Item;
import dto.ItemDto;

import java.sql.Connection;

public interface ItemDao {

    boolean saveItem(Item item, Connection connection);
}

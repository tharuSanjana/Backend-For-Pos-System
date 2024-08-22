package dao.impl;

import Entity.Order_item;

import java.sql.Connection;

public interface TransactionDao {

    boolean saveTran(Order_item order_item, Connection connection);
}

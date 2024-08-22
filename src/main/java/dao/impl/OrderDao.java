package dao.impl;

import Entity.Order;

import java.sql.Connection;
import java.util.Date;

public interface OrderDao {
    boolean saveOrder(Order order, Connection connection);
}

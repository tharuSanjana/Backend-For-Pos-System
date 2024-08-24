package dao.impl;

import Entity.Order;
import dto.OrderDto;

import java.sql.Connection;
import java.util.Date;

public interface OrderDao {
    boolean saveOrder(OrderDto orderDto, Connection connection);
}

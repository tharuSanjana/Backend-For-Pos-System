package dao.impl;

import Entity.Order_item;
import dto.OrderDto;

import java.sql.Connection;

public interface TransactionDao {

    boolean saveTran(OrderDto orderDto, Connection connection);
}

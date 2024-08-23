package bo;

import dto.OrderDto;

import java.sql.Connection;
import java.sql.SQLException;

public interface OrderBo {
    boolean saveOrder(OrderDto orderDto, Connection connection) throws SQLException;
}

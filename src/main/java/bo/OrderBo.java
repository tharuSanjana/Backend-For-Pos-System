package bo;

import dto.OrderDto;

import java.sql.Connection;

public interface OrderBo {
    boolean saveOrder(OrderDto orderDto, Connection connection);
}

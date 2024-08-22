package dao.impl;

import Entity.Order;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class OrderDaoImpl implements OrderDao{

    static String SAVE_ORDER = "INSERT INTO orders values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    @Override
    public boolean saveOrder(Order order, Connection connection) {
        try {
            var ps = connection.prepareStatement(SAVE_ORDER);
            ps.setString(1, order.getO_id());
            ps.setString(2, String.valueOf(order.getDate()));
            ps.setString(3, order.getC_id());
            ps.setString(4, order.getC_name());
            ps.setString(5, order.getC_nic());
            ps.setString(6, order.getC_email());
            ps.setString(7, order.getC_address());
            ps.setString(8, order.getC_tel());
            ps.setString(9, order.getI_id());
            ps.setString(10, order.getI_name());
            ps.setString(11, order.getI_price());
            ps.setString(12, order.getI_qty());
            ps.setString(13, order.getI_selectedQty());
            ps.setString(14, String.valueOf(order.getTotal()));
            ps.setString(15, String.valueOf(order.getBalance()));
            ps.setString(16, String.valueOf(order.getCash()));
            ps.setString(17, String.valueOf(order.getDiscount()));

            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

package dao.impl;

import Entity.Order_item;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionDaoImpl implements TransactionDao{
    static String SAVE_TRANSACTION = "INSERT INTO order_item values (?,?)";
    @Override
    public boolean saveTran(Order_item order_item, Connection connection) {
        try {
            var ps = connection.prepareStatement(SAVE_TRANSACTION);
            ps.setString(1, order_item.getO_id());
            ps.setString(2, order_item.getI_id());


            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

package dao.impl;

import Entity.Order_item;
import dto.OrderDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionDaoImpl implements TransactionDao{
    /*static String SAVE_TRANSACTION = "INSERT INTO order_item values (?,?)";
    @Override
    public boolean saveTran(String O_id,String I_id, Connection connection) {
        try {
            var ps = connection.prepareStatement(SAVE_TRANSACTION);
            ps.setString(1, O_id);
            ps.setString(2, I_id);


            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/
    private static final String SAVE_TRANSACTION = "INSERT INTO order_item (o_id, i_id) VALUES (?, ?)";

    @Override
    public boolean saveTran(OrderDto orderDto, Connection connection) {
        try (PreparedStatement ps = connection.prepareStatement(SAVE_TRANSACTION)) {
            ps.setString(1, orderDto.getO_id());
            ps.setString(2, orderDto.getI_id());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

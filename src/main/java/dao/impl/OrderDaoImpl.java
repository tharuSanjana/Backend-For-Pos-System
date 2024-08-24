package dao.impl;

import dto.OrderDto;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderDaoImpl implements OrderDao{

   /* static String SAVE_ORDER = "INSERT INTO orders values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    public boolean saveOrder(String O_id, Date date, String C_id, String C_name, String C_nic, String C_email, String C_address, String C_tel, String I_id, String I_name, String I_price, String I_qty, String I_selectedQty, Double total, Double balance, Double cash, Double discount, Connection connection) {
        try {
            var ps = connection.prepareStatement(SAVE_ORDER);
            ps.setString(1, O_id);
            ps.setString(2, String.valueOf(date));
            ps.setString(3, C_id);
            ps.setString(4, C_name);
            ps.setString(5, C_nic);
            ps.setString(6, C_email);
            ps.setString(7, C_address);
            ps.setString(8, C_tel);
            ps.setString(9, I_id);
            ps.setString(10, I_name);
            ps.setString(11, I_price);
            ps.setString(12,I_qty);
            ps.setString(13, I_selectedQty);
            ps.setString(14, String.valueOf(total));
            ps.setString(15, String.valueOf(balance));
            ps.setString(16, String.valueOf(cash));
            ps.setString(17, String.valueOf(discount));

            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/
   private static final String SAVE_ORDER = "INSERT INTO orders (o_id, date, c_id, c_name, c_nic, c_email, c_address, c_tel, i_id, i_name, i_price, i_qty, i_selectedQty, total, balance, cash, discount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public boolean saveOrder(OrderDto orderDto, Connection connection) {
        try (PreparedStatement ps = connection.prepareStatement(SAVE_ORDER)) {
            ps.setString(1, orderDto.getO_id());
            ps.setDate(2, (Date) orderDto.getDate());  // Handle java.util.Date -> java.sql.Date conversion
            ps.setString(3, orderDto.getC_id());
            ps.setString(4, orderDto.getC_name());
            ps.setString(5, orderDto.getC_nic());
            ps.setString(6, orderDto.getC_email());
            ps.setString(7, orderDto.getC_address());
            ps.setString(8, orderDto.getC_tel());
            ps.setString(9, orderDto.getI_id());
            ps.setString(10, orderDto.getI_name());
            ps.setString(11, orderDto.getI_price());
            ps.setString(12, orderDto.getI_qty());
            ps.setString(13, orderDto.getI_selectedQty());
            ps.setString(14, String.valueOf(orderDto.getTotal()));
            ps.setString(15, String.valueOf(orderDto.getBalance()));
            ps.setString(16, String.valueOf(orderDto.getCash()));
            ps.setString(17, String.valueOf(orderDto.getDiscount()));

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

package dao.impl;

import Entity.Item;
import dto.ItemDto;

import java.sql.Connection;
import java.sql.SQLException;

public class ItemDaoImpl implements ItemDao{

    static String SAVE_ITEM = "INSERT INTO item values (?,?,?,?)";
    static String GET_ITEM = "SELECT * FROM item WHERE item_id=?";
    static String UPDATE_ITEM = "UPDATE item SET name = ?, qty = ?, price = ? WHERE item_id = ?";
    static String DELETE_ITEM = "DELETE FROM item WHERE item_id = ?";
    static String GET_ALL_ITEM = "SELECT * FROM item";
    @Override
    public boolean saveItem(Item item, Connection connection) {
        try {
            var ps = connection.prepareStatement(SAVE_ITEM);
            ps.setString(1, item.getItem_id());
            ps.setString(2, item.getName());
            ps.setString(3, item.getQty());
            ps.setString(4, item.getPrice());

            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

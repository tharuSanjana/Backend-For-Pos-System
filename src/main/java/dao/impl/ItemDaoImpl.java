package dao.impl;

import Entity.Item;
import dto.CustomerDto;
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

    @Override
    public boolean updateItem(String itemId, Item item, Connection connection) {
        try {
            var ps = connection.prepareStatement(UPDATE_ITEM);
            ps.setString(1, item.getName());
            ps.setString(2, item.getQty());
            ps.setString(3, item.getQty());
            ps.setString(4, itemId);
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating customer with ID: " + itemId, e);
        }
    }

    @Override
    public boolean deleteItem(String itemId, Connection connection) {
        try {
            var ps = connection.prepareStatement(DELETE_ITEM);
            ps.setString(1, itemId);
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public ItemDto getItem(String itemId, Connection connection) {

        var itemDto = new ItemDto();
        try {
            var ps = connection.prepareStatement(GET_ITEM);
            ps.setString(1, itemId);
            var resultSet = ps.executeQuery();
            while (resultSet.next()) {

                itemDto.setItem_id(resultSet.getString("item_id"));
                itemDto.setName(resultSet.getString("name"));
                itemDto.setQty(resultSet.getString("qty"));
                itemDto.setPrice(resultSet.getString("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemDto;
    }
}

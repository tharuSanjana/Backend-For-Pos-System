package bo;

import Entity.Item;
import dao.impl.ItemDaoImpl;
import dto.ItemDto;

import java.sql.Connection;

public class ItemBoImpl implements ItemBo{

    ItemDaoImpl itemDao = new ItemDaoImpl();
    @Override
    public boolean saveItem(ItemDto itemDto, Connection connection) {
        return itemDao.saveItem(new Item(itemDto.getItem_id(),itemDto.getName(),itemDto.getQty(),itemDto.getPrice()),connection);
    }

    @Override
    public boolean updateItem(String itemId, ItemDto itemDto, Connection connection) {
        return itemDao.updateItem(itemId,new Item(itemDto.getName(),itemDto.getQty(),itemDto.getPrice()),connection);
    }

    @Override
    public boolean deleteItem(String itemId, Connection connection) {
        return itemDao.deleteItem(itemId,connection);
    }

    @Override
    public ItemDto getItem(String itemId, Connection connection) {
        return itemDao.getItem(itemId,connection);
    }
}

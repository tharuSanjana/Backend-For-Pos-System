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
}

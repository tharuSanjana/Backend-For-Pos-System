package bo;

import dto.CustomerDto;
import dto.ItemDto;

import java.sql.Connection;

public interface ItemBo {
    boolean saveItem(ItemDto itemDto, Connection connection);
    boolean updateItem(String itemId,ItemDto itemDto,Connection connection);
    boolean deleteItem(String itemId,Connection connection);
    ItemDto getItem(String itemId, Connection connection);
}

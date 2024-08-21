package bo;

import dto.ItemDto;

import java.sql.Connection;

public interface ItemBo {

    boolean saveItem(ItemDto itemDto, Connection connection);
}

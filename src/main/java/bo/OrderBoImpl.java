package bo;

import Entity.Order;
import Entity.Order_item;
import dao.impl.OrderDaoImpl;
import dao.impl.TransactionDaoImpl;
import dto.OrderDto;

import java.sql.Connection;

public class OrderBoImpl implements OrderBo{

    OrderDaoImpl orderDao = new OrderDaoImpl();
    TransactionDaoImpl transactionDao = new TransactionDaoImpl();

    @Override
    public boolean saveOrder(OrderDto orderDto, Connection connection) {
        boolean isOrderSaved = orderDao.saveOrder(new Order(orderDto.getO_id(),orderDto.getDate(),orderDto.getC_id(),orderDto.getC_name(),orderDto.getC_nic(),orderDto.getC_email(),orderDto.getC_address(),orderDto.getC_tel(),orderDto.getI_id(),orderDto.getI_name(),orderDto.getI_price(),orderDto.getI_qty(),orderDto.getI_selectedQty(),orderDto.getTotal(),orderDto.getBalance(),orderDto.getCash(),orderDto.getDiscount()),connection);
        if (isOrderSaved){
            boolean IsTtranSaved= transactionDao.saveTran(new Order_item(orderDto.getO_id(),orderDto.getI_id()),connection);

            if (IsTtranSaved){
                System.out.println("Trans Saved");
            }
    }
     return true;
    }
}

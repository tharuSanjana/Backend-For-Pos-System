package bo;

import Entity.Order;
import Entity.Order_item;
import dao.impl.OrderDaoImpl;
import dao.impl.TransactionDaoImpl;
import dto.OrderDto;

import java.sql.Connection;
import java.sql.SQLException;

import static com.mysql.cj.conf.PropertyKey.logger;

public class OrderBoImpl implements OrderBo{

    OrderDaoImpl orderDao = new OrderDaoImpl();
    TransactionDaoImpl transactionDao = new TransactionDaoImpl();

    @Override
    public boolean saveOrder(OrderDto orderDto, Connection connection) throws SQLException {
       /* connection.setAutoCommit(false);
        boolean isOrderSaved = orderDao.saveOrder(new Order(orderDto.getO_id(),orderDto.getDate(),orderDto.getC_id(),orderDto.getC_name(),orderDto.getC_nic(),orderDto.getC_email(),orderDto.getC_address(),orderDto.getC_tel(),orderDto.getI_id(),orderDto.getI_name(),orderDto.getI_price(),orderDto.getI_qty(),orderDto.getI_selectedQty(),orderDto.getTotal(),orderDto.getBalance(),orderDto.getCash(),orderDto.getDiscount()),connection);
        if (isOrderSaved){
            boolean IsTtranSaved= transactionDao.saveTran(new Order_item(orderDto.getO_id(),orderDto.getI_id()),connection);

           *//* if (IsTtranSaved){
                System.out.println("Trans Saved");
            }*//*
            if (IsTtranSaved) {
                // Commit transaction if both save operations succeed
                connection.commit();
                System.out.println("Order and Transaction saved successfully.");
                return true;
            } else {
                // Rollback if transaction save fails
                connection.rollback();
                System.out.println("Failed to save Transaction. Rolling back.");
                return false;
            }
        } else {
            // Rollback if order save fails
            connection.rollback();
            System.out.println("Failed to save Order. Rolling back.");
            return false;
        }
    }*/

        boolean success = false;
        try {
            connection.setAutoCommit(false);

            // Save the order
            boolean isOrderSaved = orderDao.saveOrder(orderDto, connection);

            // Save the associated order items
            boolean isTransactionSaved = isOrderSaved && transactionDao.saveTran(orderDto, connection);

            if (isTransactionSaved) {
                connection.commit();
                success = true;
            } else {
                connection.rollback();
            }
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();  // Add this to help debug
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
        return success;
    }


}

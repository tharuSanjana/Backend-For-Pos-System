package dao.impl;



import Entity.Customer;
import dto.CustomerDto;

import java.sql.Connection;

public sealed interface CustomerDao permits CustomerDaoImpl {


    boolean saveCustomer(Customer customer, Connection connection);
    boolean updateCustomer(String customerId,Customer customer,Connection connection);
}

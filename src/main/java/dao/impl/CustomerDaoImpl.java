package dao.impl;



import Entity.Customer;
import dto.CustomerDto;

import java.sql.Connection;
import java.sql.SQLException;

public final class CustomerDaoImpl implements CustomerDao {

    static String SAVE_CUSTOMER  =  "INSERT INTO customer values (?,?,?,?,?,?)";
    static String GET_CUSTOMER  = "SELECT * FROM customer WHERE cus_id=?";
    static String UPDATE_CUSTOMER  = "UPDATE customer SET name = ?, nic = ?, email = ?, address = ?,tel = ? WHERE cus_id = ?";
    static String DELETE_CUSTOMER  = "DELETE FROM customer WHERE cus_id = ?";

    @Override
    public boolean saveCustomer(Customer customer, Connection connection) {
        try {
            var ps = connection.prepareStatement(SAVE_CUSTOMER);
            ps.setString(1, customer.getId());
            ps.setString(2, customer.getName());
            ps.setString(3, customer.getNic());
            ps.setString(4, customer.getEmail());
            ps.setString(5, customer.getAddress());
            ps.setString(6, customer.getTel());
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

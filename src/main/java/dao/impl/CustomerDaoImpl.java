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
            ps.setString(1, customer.getCus_id());
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

    @Override
    public boolean updateCustomer(String customerId,Customer customer, Connection connection) {
        try {
            var ps = connection.prepareStatement(UPDATE_CUSTOMER);
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getNic());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getAddress());
            ps.setString(5, customer.getTel());
            ps.setString(6, customerId);
            return ps.executeUpdate() != 0;
        }  catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating customer with ID: " + customerId, e);
        }
    }

    @Override
    public boolean deleteCustomer(String customerId, Connection connection) {
        try {
            var ps = connection.prepareStatement(DELETE_CUSTOMER);
            ps.setString(1, customerId);
            return ps.executeUpdate() != 0;
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }
}

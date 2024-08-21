package bo;

import dto.CustomerDto;

import java.sql.Connection;
import java.util.List;

public interface CustomerBo {
    boolean saveCustomer(CustomerDto dto, Connection connection);
    boolean updateCustomer(String customerId,CustomerDto customerDto,Connection connection);
    boolean deleteCustomer(String customerId,Connection connection);
    CustomerDto getCustomer(String customerId,Connection connection);

    List<CustomerDto> getAllCustomers(Connection connection);
}


package bo;

import dto.CustomerDto;

import java.sql.Connection;

public interface CustomerBo {
    boolean saveCustomer(CustomerDto dto, Connection connection);
}

package bo;

import Entity.Customer;
import dao.impl.CustomerDaoImpl;
import dto.CustomerDto;

import java.sql.Connection;

public class CustomerBoImpl implements CustomerBo{
    CustomerDaoImpl customerDao = new CustomerDaoImpl();
    @Override
    public boolean saveCustomer(CustomerDto dto, Connection connection) {
        return customerDao.saveCustomer(new Customer(dto.getCus_id(), dto.getName(), dto.getNic(), dto.getEmail(), dto.getAddress(), dto.getTel()),connection);
    }

    @Override
    public boolean updateCustomer(String customerId,CustomerDto customerDto, Connection connection) {
        return customerDao.updateCustomer(customerId,new Customer( customerDto.getName(), customerDto.getNic(), customerDto.getEmail(), customerDto.getAddress(), customerDto.getTel()),connection);
    }
}

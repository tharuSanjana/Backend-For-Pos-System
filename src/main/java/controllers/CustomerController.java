package controllers;

import bo.CustomerBoImpl;
import dao.impl.CustomerDaoImpl;
import dto.CustomerDto;
import jakarta.json.JsonException;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@WebServlet(urlPatterns = "/Customer")

public class CustomerController extends HttpServlet {

    static Logger logger = LoggerFactory.getLogger(CustomerController.class);

    Connection connection;

    static String SAVE_CUSTOMER = "INSERT INTO customer values (?,?,?,?,?,?)";
    static String GET_CUSTOMER = "SELECT * FROM customer WHERE cus_id=?";
    static String UPDATE_CUSTOMER = "UPDATE customer SET name = ?, nic = ?, email = ?, address = ?,tel = ? WHERE cus_id = ?";
    static String DELETE_CUSTOMER = "DELETE FROM customer WHERE cus_id = ?";

    @Override
    public void init() throws ServletException {

        logger.info("Initializing Student Controller with call init method ");
        logger.trace("inite called");
        try {

            var ctx = new InitialContext();
            DataSource pool= (DataSource) ctx.lookup("java:comp/env/jdbc/pos");
            logger.trace("connection pool created");
            this.connection=pool.getConnection();
            logger.trace("connection initialized");
        }catch (NamingException | SQLException e){
            logger.error("init failed with",e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!req.getContentType().toLowerCase().startsWith("application/json")|| req.getContentType() == null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        try (var writer = resp.getWriter()){
            Jsonb jsonb = JsonbBuilder.create();
            CustomerDto customerDto = jsonb.fromJson(req.getReader(), CustomerDto.class);
            var saveData = new CustomerBoImpl();

            if (saveData.saveCustomer(customerDto, connection)){
                System.out.println(customerDto);
                writer.write("Customer saved successfully");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            }else {
                writer.write("Save customer failed");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);

            }

        } catch (JsonException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if(!req.getContentType().toLowerCase().startsWith("application/json")|| req.getContentType() == null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        try (var writer = resp.getWriter()){
         /*   var customerId = req.getParameter("cus_id");*/

            Jsonb jsonb = JsonbBuilder.create();
            var customerBo = new CustomerBoImpl();
            var updatedCustomer = jsonb.fromJson(req.getReader(),CustomerDto.class);
            System.out.println(updatedCustomer);
            System.out.println("cus_id"+ updatedCustomer.getCus_id());
            if(customerBo.updateCustomer(updatedCustomer.getCus_id(), updatedCustomer,connection)){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else {
                writer.write("Update Failed");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (JsonException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       /* var customerId = req.getParameter("cus-id");*/
        Jsonb jsonb = JsonbBuilder.create();
        var customerBo = new CustomerBoImpl();
        var updatedCustomer = jsonb.fromJson(req.getReader(),CustomerDto.class);
        System.out.println(updatedCustomer);
        try (var writer = resp.getWriter()){

            if(customerBo.deleteCustomer(updatedCustomer.getCus_id(), connection)){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                writer.write("Delete Failed");
            }
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

       /* var customerId = req.getParameter("id");
        var customerBo = new CustomerBoImpl();
        try (var writer = resp.getWriter()){
            var customer = customerBo.getCustomer(customerId, connection);
            System.out.println(customer);
            resp.setContentType("application/json");
            Jsonb jsonb = JsonbBuilder.create();
            jsonb.toJson(customer,writer);
        }*/

        String action = req.getParameter("action");

        if ("getAll".equalsIgnoreCase(action)) {
            getAllCustomers(req, resp);
        } else {
            var customerId = req.getParameter("id");
            if (customerId != null) {
                getCustomerById(req, resp, customerId);
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request");
            }
        }
    }

    private void getAllCustomers(HttpServletRequest req, HttpServletResponse resp) throws IOException {


        var customerBo = new CustomerBoImpl();
        List<CustomerDto> customers = (List<CustomerDto>) customerBo.getAllCustomers(connection);

        resp.setContentType("application/json");
        Jsonb jsonb = JsonbBuilder.create();
        if (customers == null || customers.isEmpty()) {
            jsonb.toJson(new ArrayList<>(), resp.getWriter());
        } else {
            jsonb.toJson(customers, resp.getWriter());
        }
    }

    private void getCustomerById(HttpServletRequest req, HttpServletResponse resp, String customerId) throws IOException {
        var customerBo = new CustomerBoImpl();
        try (var writer = resp.getWriter()) {
            var customer = customerBo.getCustomer(customerId, connection);
            resp.setContentType("application/json");
            Jsonb jsonb = JsonbBuilder.create();
            jsonb.toJson(customer, writer);
        } catch (Exception e) {
            logger.error("Error retrieving customer by ID", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to retrieve customer");
        }
    }


}

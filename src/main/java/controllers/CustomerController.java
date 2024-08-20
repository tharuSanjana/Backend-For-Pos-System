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
        /*try {

            var ctx = new InitialContext();
            DataSource pool= (DataSource) ctx.lookup("java:comp/env/jdbc/pos");
            this.connection=pool.getConnection();
        }catch (NamingException | SQLException e){
            e.printStackTrace();
        }*/

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
                writer.write("Student saved successfully");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            }else {
                writer.write("Save student failed");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);

            }

        } catch (JsonException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("Servlet is running");
    }
}

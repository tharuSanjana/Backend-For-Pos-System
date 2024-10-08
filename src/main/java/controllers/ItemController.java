package controllers;

import bo.CustomerBoImpl;
import bo.ItemBoImpl;
import dto.CustomerDto;
import dto.ItemDto;
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

@WebServlet(urlPatterns = "/Item")
public class ItemController extends HttpServlet {

    static Logger logger = LoggerFactory.getLogger(ItemController.class);

    Connection connection;

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
            ItemDto itemDto = jsonb.fromJson(req.getReader(), ItemDto.class);
            var saveIt = new ItemBoImpl();

            if (saveIt.saveItem(itemDto, connection)){
                System.out.println(itemDto);
                writer.write("Item saved successfully");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            }else {
                writer.write("Save item failed");
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
            var itemBo = new ItemBoImpl();
            var updatedItem = jsonb.fromJson(req.getReader(),ItemDto.class);
            System.out.println(updatedItem);
            System.out.println("item_id"+ updatedItem.getItem_id());
            if(itemBo.updateItem(updatedItem.getItem_id(), updatedItem,connection)){
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
        Jsonb jsonb = JsonbBuilder.create();
        var itemBo = new ItemBoImpl();
        var deleteItem = jsonb.fromJson(req.getReader(),ItemDto.class);
        System.out.println(deleteItem);
        try (var writer = resp.getWriter()){

            if(itemBo.deleteItem(deleteItem.getItem_id(), connection)){
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
        String action = req.getParameter("action");

        if ("getAll".equalsIgnoreCase(action)) {
            getAllItems(req, resp);
        } else {
            var itemId = req.getParameter("id");
            if (itemId != null) {
                getItemById(req, resp, itemId);
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request");
            }
        }
    }

    private void getAllItems(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        /*var customerBo = new CustomerBoImpl();

        try (var writer = resp.getWriter()) {
            var customers = customerBo.getAllCustomers(connection);
            resp.setContentType("application/json");
            Jsonb jsonb = JsonbBuilder.create();
            jsonb.toJson(customers, writer);
        } catch (Exception e) {
            logger.error("Error retrieving all customers", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to retrieve customers");
        }*/

        var itemBo = new ItemBoImpl();
        List<ItemDto> items = (List<ItemDto>) itemBo.getAllItems(connection);

        resp.setContentType("application/json");
        Jsonb jsonb = JsonbBuilder.create();
        if (items == null || items.isEmpty()) {
            jsonb.toJson(new ArrayList<>(), resp.getWriter());
        } else {
            jsonb.toJson(items, resp.getWriter());
        }
    }

    private void getItemById(HttpServletRequest req, HttpServletResponse resp, String itemId) throws IOException {
        ItemBoImpl itemBo = new ItemBoImpl();
        try (var writer = resp.getWriter()) {
            var customer = itemBo.getItem(itemId, connection);
            resp.setContentType("application/json");
            Jsonb jsonb = JsonbBuilder.create();
            jsonb.toJson(customer, writer);
        } catch (Exception e) {
            logger.error("Error retrieving customer by ID", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to retrieve customer");
        }
    }


}

package controllers;

import bo.CustomerBoImpl;
import bo.OrderBoImpl;
import dto.CustomerDto;
import dto.OrderDto;
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

@WebServlet(urlPatterns = "/Order")
public class OrderController extends HttpServlet {

    static Logger logger = LoggerFactory.getLogger(OrderController.class);

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
        /*if(!req.getContentType().toLowerCase().startsWith("application/json")|| req.getContentType() == null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        try (var writer = resp.getWriter()){
            Jsonb jsonb = JsonbBuilder.create();
            OrderDto orderDto = jsonb.fromJson(req.getReader(), OrderDto.class);
            var saveData = new OrderBoImpl();

            if (saveData.saveOrder(orderDto, connection)){
                System.out.println(orderDto);
                writer.write("Order saved successfully");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            }else {
                writer.write("Save order failed");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);

            }

        } catch (JsonException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }*/
        if (!req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Content-Type");
            return;
        }

        try (var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            OrderDto orderDto = jsonb.fromJson(req.getReader(), OrderDto.class);
            var orderBo = new OrderBoImpl();

            if (orderBo.saveOrder(orderDto, connection)) {
                logger.info("Order saved successfully: {}", orderDto.getO_id());
                writer.write("Order saved successfully");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                logger.warn("Failed to save order: {}", orderDto.getO_id());
                writer.write("Failed to save order");
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (JsonException e) {
            logger.error("JSON processing failed", e);
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON format");
        } catch (SQLException e) {
            logger.error("Database error", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }
    }

    }



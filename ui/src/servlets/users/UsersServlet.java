package servlets.users;

import com.google.gson.Gson;
import engine.dto.DTOUsers;
import engine.stockMarket.StockMarketApi;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UsersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json");
        StockMarketApi stockMarketApi = ServletUtils.getStockMarketApi(getServletContext());

        DTOUsers users = stockMarketApi.getAllUsers();

        Gson gson = new Gson();
        String jsonResponse;
        jsonResponse = gson.toJson(users);
        res.getWriter().print(jsonResponse);
    }
}

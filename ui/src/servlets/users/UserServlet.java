package servlets.users;

import com.google.gson.Gson;
import engine.dto.DTOUser;
import engine.stockMarket.StockMarketApi;
import utils.ServletUtils;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Constants.USERNAME;

public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json");
        StockMarketApi stockMarketApi = ServletUtils.getStockMarketApi(getServletContext());

        String username = req.getParameter(USERNAME);
        DTOUser user = stockMarketApi.getUserByName(username);

        if (user == null) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        Gson gson = new Gson();
        String jsonResponse;
        jsonResponse = gson.toJson(user);
        res.getWriter().print(jsonResponse);
    }
}

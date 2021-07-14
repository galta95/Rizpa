package servlets.users;

import com.google.gson.Gson;
import engine.dto.DTOUser;
import engine.stockMarket.StockMarketApi;
import utils.ServletUtils;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

import static constants.Constants.*;

public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json");
        Gson gson = new Gson();
        String jsonResponse;

        StockMarketApi stockMarketApi = ServletUtils.getStockMarketApi(getServletContext());

        String username = req.getParameter(USERNAME);
        DTOUser user = stockMarketApi.getUserByName(username);

        if (user == null) {
            UserNotFound userNotFound = new UserNotFound(username);
            jsonResponse = gson.toJson(userNotFound);
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            res.getWriter().print(jsonResponse);
            return;
        }
        jsonResponse = gson.toJson(user);
        res.getWriter().print(jsonResponse);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json");
        Gson gson = new Gson();

        StockMarketApi stockMarketApi = ServletUtils.getStockMarketApi(getServletContext());

        BufferedReader reader = req.getReader();
        ParsedUser parsedUser = gson.fromJson(reader, ParsedUser.class);

        DTOUser dtoUser = stockMarketApi.addMoney(parsedUser.userName, parsedUser.money);

        if (dtoUser == null) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.getWriter().print(BAD_REQUEST);
        } else {
            res.setStatus(HttpServletResponse.SC_CREATED);
            res.getWriter().print(CREATED);
        }
    }

    private static class UserNotFound {
        final private String username;
        public UserNotFound(String userName) {
            this.username = userName;
        }
    }

    private static class ParsedUser {
        final String userName;
        final int money;

        public ParsedUser(String userName, int money) {
            this.userName = userName;
            this.money = money;
        }
    }
}

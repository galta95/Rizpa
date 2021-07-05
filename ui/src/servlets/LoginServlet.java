package servlets;
import com.google.gson.Gson;
import engine.dto.DTOUser;
import engine.stockMarket.StockMarketApi;
import engine.stockMarket.users.User.Permissions;
import utils.ServletUtils;
import utils.SessionUtils;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Constants.PASSWORD;
import static constants.Constants.USERNAME;

public class LoginServlet extends HttpServlet {
    private final String SIGN_UP_URL = "../signup/signup.html";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("application/json");

        String usernameFromSession = SessionUtils.getUsername(req);
        StockMarketApi stockMarketApi = ServletUtils.getStockMarketApi(getServletContext());

        if (usernameFromSession == null) {
            String usernameFromParameter = req.getParameter(USERNAME);
            String passwordFromParameter = req.getParameter(PASSWORD);

            if (usernameFromParameter == null || passwordFromParameter == null
                    || usernameFromParameter == "" || passwordFromParameter == "" ) {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }

            Gson gson = new Gson();
            String jsonResponse;

            DTOUser dtoUser = stockMarketApi.getUserByName(usernameFromParameter);

            if (dtoUser == null) {
                stockMarketApi.insertUser(usernameFromParameter, passwordFromParameter, Permissions.BROKER);
            }

            User userResponse = new User(usernameFromParameter, passwordFromParameter);
            jsonResponse = gson.toJson(userResponse);

            res.getWriter().print(jsonResponse);
        }
    }

    private static class User {
        final private String username;
        final private String password;

        public User(String userName, String password) {
            this.username = userName;
            this.password = password;
        }
    }
}

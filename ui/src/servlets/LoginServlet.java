package servlets;

import com.google.gson.Gson;
import engine.dto.DTOUser;
import engine.stockMarket.StockMarketApi;
import engine.stockMarket.users.User.Permissions;
import utils.ServletUtils;
import utils.SessionUtils;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Constants.*;

public class LoginServlet extends HttpServlet {
    private final String SIGN_UP_URL = "../signup/signup.html";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        res.setContentType("application/json");

        String usernameFromSession = SessionUtils.getUsername(req);
        StockMarketApi stockMarketApi = ServletUtils.getStockMarketApi(getServletContext());

        if (usernameFromSession == null) {
            String username = req.getParameter(USERNAME);
            String password = req.getParameter(PASSWORD);
            String permissions = req.getParameter(PERMISSIONS);

            Permissions permissionsEnum = Permissions.BROKER;

            if (username == null || password == null
                    || username.equals("") || password.equals("")) {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }

            if (permissions.equals(ADMIN)) {
                permissionsEnum = Permissions.ADMIN;
            }

            Gson gson = new Gson();
            String jsonResponse;

            DTOUser dtoUser = stockMarketApi.getUserByName(username);

            if (dtoUser == null) {
                stockMarketApi.insertUser(username, password, permissionsEnum);
            }

            User userResponse = new User(username, password, permissions);
            jsonResponse = gson.toJson(userResponse);

            res.getWriter().print(jsonResponse);
        }
    }

    private static class User {
        final private String username;
        final private String password;
        final private String permissions;

        public User(String userName, String password, String permissions) {
            this.username = userName;
            this.password = password;
            this.permissions = permissions;
        }
    }
}

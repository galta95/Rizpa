package servlets;

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

            DTOUser dtoUser = stockMarketApi.getUserByName(username);

            if (dtoUser == null) {
                stockMarketApi.insertUser(username, password, permissionsEnum);
            }
            res.getWriter().print(CREATED);
        }
    }
}

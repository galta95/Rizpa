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
    protected void signup(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        String usernameFromSession = SessionUtils.getUsername(req);
        StockMarketApi stockMarketApi = ServletUtils.getStockMarketApi(getServletContext());

        String username = req.getParameter(USERNAME);
        String password = req.getParameter(PASSWORD);
        String permissions = req.getParameter(PERMISSIONS);

        Permissions permissionsEnum = Permissions.BROKER;
        if (permissions.equals(ADMIN)) {
            permissionsEnum = Permissions.ADMIN;
        }

        if (usernameFromSession == null) {
            if (username == null || password == null || username.equals("") || password.equals("")) {
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            DTOUser dtoUser = stockMarketApi.getUserByName(username);

            if (dtoUser == null) {
                stockMarketApi.insertUser(username, password, permissionsEnum);
                req.getSession(true).setAttribute(USERNAME, username);
                res.getWriter().print(CREATED);
            } else {
                if (!dtoUser.getPassword().equals(password) || !dtoUser.getPermission().equals(permissionsEnum)) {
                    res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    res.getWriter().print("invalid password or permission");
                }
            }
        } else {
            DTOUser dtoUser = stockMarketApi.getUserByName(username);
            if (!dtoUser.getPassword().equals(password) || !dtoUser.getPermission().equals(permissionsEnum)) {
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                res.getWriter().print("invalid password or permission");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json");
        signup(req, res);
    }
}

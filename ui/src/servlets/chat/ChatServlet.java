package servlets.chat;
import com.google.gson.Gson;
import engine.chat.ChatManager;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Constants.CHAT_PARAMETER;
import static constants.Constants.USERNAME;

public class ChatServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("application/json");
        ChatManager chatManager = ServletUtils.getChatManager(getServletContext());

        Gson gson = new Gson();
        String jsonResponse;
        jsonResponse = gson.toJson(chatManager);
        res.getWriter().print(jsonResponse);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ChatManager chatManager = ServletUtils.getChatManager(getServletContext());
        String username = request.getParameter(USERNAME);
        if (username == null) {
            return;
        }

        String userChatString = request.getParameter(CHAT_PARAMETER);
        if (userChatString != null && !userChatString.isEmpty()) {
            synchronized (getServletContext()) {
                chatManager.addChatString(userChatString, username);
            }
        }
    }
}

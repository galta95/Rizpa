package servlets.stocks;

import com.google.gson.Gson;
import engine.dto.DTOStocks;
import engine.stockMarket.StockMarketApi;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class StocksServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json");
        StockMarketApi stockMarketApi = ServletUtils.getStockMarketApi(getServletContext());

        DTOStocks stocks = stockMarketApi.getAllStocks();

        Gson gson = new Gson();
        String jsonResponse;

        if (stocks.isEmpty()) {
            Map<String, List<String>> msg = new HashMap<String, List<String>>();
            List<String> lst = new LinkedList<>();
            lst.add("There no stock on the market");
            msg.put("stocks", lst);
            jsonResponse = gson.toJson(msg);
        } else{
            jsonResponse = gson.toJson(stocks);
        }
        res.getWriter().print(jsonResponse);
    }
}

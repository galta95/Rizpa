package servlets.trades;

import com.google.gson.Gson;
import engine.dto.DTOOrder;
import engine.stockMarket.StockMarketApi;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

import static constants.Constants.CONFLICT;
import static constants.Constants.CREATED;

public class TradeServlet extends HttpServlet {

    private void tradeStock(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json");
        Gson gson = new Gson();

        StockMarketApi stockMarketApi = ServletUtils.getStockMarketApi(getServletContext());

        BufferedReader reader = req.getReader();
        ParsedTrade parsedTrade = gson.fromJson(reader, ParsedTrade.class);

        DTOOrder dtoOrder = null;

        switch (parsedTrade.orderType) {
            case "LMT":
                if (parsedTrade.tradeDirection.equals("buy")) {
                    dtoOrder = stockMarketApi.executeLmtOrderBuy(parsedTrade.symbol, parsedTrade.date,
                            parsedTrade.quantity, parsedTrade.price, parsedTrade.userName);
                } else {
                    dtoOrder = stockMarketApi.executeLmtOrderSell(parsedTrade.symbol, parsedTrade.date,
                            parsedTrade.quantity, parsedTrade.price, parsedTrade.userName);
                }
                break;
            case "MKT":
                if (parsedTrade.tradeDirection.equals("buy")) {
                    dtoOrder = stockMarketApi.executeMktOrderBuy(parsedTrade.symbol, parsedTrade.date,
                            parsedTrade.quantity, parsedTrade.price, parsedTrade.userName);
                } else {
                    dtoOrder = stockMarketApi.executeMktOrderSell(parsedTrade.symbol, parsedTrade.date,
                            parsedTrade.quantity, parsedTrade.price, parsedTrade.userName);
                }
                break;
            case "FOK":
                if (parsedTrade.tradeDirection.equals("buy")) {
                    dtoOrder = stockMarketApi.executeFokOrderBuy(parsedTrade.symbol, parsedTrade.date,
                            parsedTrade.quantity, parsedTrade.price, parsedTrade.userName);
                } else {
                    dtoOrder = stockMarketApi.executeFokOrderSell(parsedTrade.symbol, parsedTrade.date,
                            parsedTrade.quantity, parsedTrade.price, parsedTrade.userName);
                }
                break;
            case "IOC":
                if (parsedTrade.tradeDirection.equals("buy")) {
                    dtoOrder = stockMarketApi.executeIocOrderBuy(parsedTrade.symbol, parsedTrade.date,
                            parsedTrade.quantity, parsedTrade.price, parsedTrade.userName);
                } else {
                    dtoOrder = stockMarketApi.executeIocOrderSell(parsedTrade.symbol, parsedTrade.date,
                            parsedTrade.quantity, parsedTrade.price, parsedTrade.userName);
                }
                break;
        }

        if (dtoOrder == null) {
            res.setStatus(HttpServletResponse.SC_CONFLICT);
            res.getWriter().print(CONFLICT);
            return;
        }
        res.setStatus(HttpServletResponse.SC_CREATED);
        res.getWriter().print(CREATED);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json");
        tradeStock(req, res);
    }

    private static class ParsedTrade {
        final String symbol;
        final String date;
        final String userName;
        final String tradeDirection;
        final String orderType;
        final int quantity;
        final int price;

        public ParsedTrade(String symbol, String date, String userName, String tradeDirection, String orderType, int quantity, int price) {
            this.symbol = symbol;
            this.date = date;
            this.userName = userName;
            this.tradeDirection = tradeDirection;
            this.orderType = orderType;
            this.quantity = quantity;
            this.price = price;
        }
    }
}

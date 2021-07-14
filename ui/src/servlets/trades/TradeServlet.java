package servlets.trades;

import com.google.gson.Gson;
import engine.dto.DTOOrder;
import engine.dto.DTOStock;
import engine.stockMarket.StockMarketApi;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static constants.Constants.*;

public class TradeServlet extends HttpServlet {

    private void tradeStock(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json");
        Gson gson = new Gson();

        StockMarketApi stockMarketApi = ServletUtils.getStockMarketApi(getServletContext());

        BufferedReader reader = req.getReader();
        ParsedTrade parsedTrade = gson.fromJson(reader, ParsedTrade.class);
        String date = DateTimeFormatter.ofPattern("HH:mm:ss:SSS").format(LocalDateTime.now());
        parsedTrade.setDate(date);
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
                DTOStock stock = stockMarketApi.getStockBySymbol(parsedTrade.symbol);
                int price = stock.getPrice();
                if (parsedTrade.tradeDirection.equals("buy")) {
                    dtoOrder = stockMarketApi.executeMktOrderBuy(parsedTrade.symbol, parsedTrade.date,
                            parsedTrade.quantity, price, parsedTrade.userName);
                } else {
                    dtoOrder = stockMarketApi.executeMktOrderSell(parsedTrade.symbol, parsedTrade.date,
                            parsedTrade.quantity, price, parsedTrade.userName);
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
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.getWriter().print(BAD_REQUEST);
            return;
        }
        res.setStatus(HttpServletResponse.SC_CREATED);
        res.getWriter().print(OK);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json");
        tradeStock(req, res);
    }

    private static class ParsedTrade {
        final String symbol;
        String date;
        final String userName;
        final String tradeDirection;
        final String orderType;
        final int quantity;
        final int price;

        public ParsedTrade(String symbol, String userName, String tradeDirection, String orderType, int quantity, int price) {
            this.symbol = symbol;
            this.userName = userName;
            this.tradeDirection = tradeDirection;
            this.orderType = orderType;
            this.quantity = quantity;
            this.price = price;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}

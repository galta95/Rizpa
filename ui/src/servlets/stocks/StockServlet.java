package servlets.stocks;

import com.google.gson.Gson;
import engine.dto.DTOStock;
import engine.stockMarket.StockMarketApi;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

import static constants.Constants.*;

public class StockServlet extends HttpServlet {

    private void createNewStock(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json");
        Gson gson = new Gson();

        StockMarketApi stockMarketApi = ServletUtils.getStockMarketApi(getServletContext());

        BufferedReader reader = req.getReader();
        ParsedStock parsedStock = gson.fromJson(reader, ParsedStock.class);

        DTOStock dtoStock = stockMarketApi.insertStock(parsedStock.companyName, parsedStock.symbol,
                parsedStock.numOfShares, parsedStock.companyValue);

        if (dtoStock == null) {
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
        createNewStock(req, res);
    }

    private static class ParsedStock {
        final String companyName;
        final String symbol;
        final int numOfShares;
        final int companyValue;

        public ParsedStock(String companyName, String symbol, int numOfShares, int companyValue) {
            this.companyName = companyName;
            this.symbol = symbol;
            this.numOfShares = numOfShares;
            this.companyValue = companyValue;
        }
    }
}
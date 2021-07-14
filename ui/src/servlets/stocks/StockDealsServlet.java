package servlets.stocks;

import com.google.gson.Gson;
import engine.dto.DTODeals;
import engine.dto.DTOStock;
import engine.stockMarket.StockMarketApi;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Constants.SYMBOL;

public class StockDealsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json");
        Gson gson = new Gson();
        String jsonResponse;

        StockMarketApi stockMarketApi = ServletUtils.getStockMarketApi(getServletContext());

        String symbol = req.getParameter(SYMBOL);
        DTOStock stock = stockMarketApi.getStockBySymbol(symbol);

        if (stock == null) {
            StockNotFound stockNotFound = new StockNotFound(symbol);
            jsonResponse = gson.toJson(stockNotFound);
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            res.getWriter().print(jsonResponse);
            return;
        }

        DTODeals stockDeals = stockMarketApi.getStockDeals(stock.getCompanyName());

        jsonResponse = gson.toJson(stockDeals);
        res.getWriter().print(jsonResponse);
    }

    private static class StockNotFound {
        final private String symbol;

        public StockNotFound(String symbol) {
            this.symbol = symbol;
        }
    }
}

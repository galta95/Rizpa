package utils;

import engine.dto.DTOUsers;
import engine.stockMarket.StockMarket;
import engine.stockMarket.StockMarketApi;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class ServletUtils {

	private static final String USERS_ATTRIBUTE_NAME = "users";
	private static final String STOCK_MARKET_API_ATTRIBUTE_NAME  = "stockMarketApi";

	private static final Object stockMarketApi = new Object();
	private static final Object users = new Object();

	public static StockMarketApi getStockMarketApi(ServletContext servletContext) {
		synchronized (stockMarketApi) {
			if (servletContext.getAttribute(STOCK_MARKET_API_ATTRIBUTE_NAME) == null) {
				servletContext.setAttribute(STOCK_MARKET_API_ATTRIBUTE_NAME, new StockMarket());
			}
		}
		return (StockMarketApi) servletContext.getAttribute(STOCK_MARKET_API_ATTRIBUTE_NAME);
	}

	public static DTOUsers getUsers(ServletContext servletContext) {
		synchronized (users) {
			if (servletContext.getAttribute(USERS_ATTRIBUTE_NAME) == null) {
				StockMarketApi stockMarketApi =  getStockMarketApi(servletContext);
				servletContext.setAttribute(USERS_ATTRIBUTE_NAME, stockMarketApi.getAllUsers());
			}
		}
		return (DTOUsers) servletContext.getAttribute(USERS_ATTRIBUTE_NAME);
	}
}

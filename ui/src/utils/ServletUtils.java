package utils;

import engine.chat.ChatManager;
import engine.dto.DTOUsers;
import engine.stockMarket.StockMarket;
import engine.stockMarket.StockMarketApi;

import javax.servlet.ServletContext;

public class ServletUtils {

	private static final String USERS_ATTRIBUTE_NAME = "users";
	private static final String STOCK_MARKET_API_ATTRIBUTE_NAME  = "stockMarketApi";
	private static final String CHAT_MANAGER_ATTRIBUTE_NAME = "chatManager";

	private static final Object stockMarketApi = new Object();
	private static final Object users = new Object();
	private static final Object chatManagerLock = new Object();

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

	public static ChatManager getChatManager(ServletContext servletContext) {
		synchronized (chatManagerLock) {
			if (servletContext.getAttribute(CHAT_MANAGER_ATTRIBUTE_NAME) == null) {
				servletContext.setAttribute(CHAT_MANAGER_ATTRIBUTE_NAME, new ChatManager());
			}
		}
		return (ChatManager) servletContext.getAttribute(CHAT_MANAGER_ATTRIBUTE_NAME);
	}
}

/**
 * 
 */
package com.deviget.minesweeperclient;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author fernando
 * A client for interacting with a minesweeper server.
 * On getting instance, API base url must be provided, i.e "http://mineserver:8080/api" 
 *
 */
public class Client {
	
	private static final String ERROR_ON_CONNECTING_TO_THE_SERVER_MSG = "Error on connecting to the server";

	private final static Logger LOGGER = LogManager.getLogger(Client.class);
	
	private URL serverURL;
	
	private String GAMES_PATH = "/games";
	private String CELLS_SUBPATH = "/cells";
	
	public enum GameType {
		EASY((short)8, (short)8, 10), MEDIUM((short)16, (short)16, 40), EXPERT((short)24, (short)24, 99);
		
		private short rows;
		
		private short columns;
		
		private int mines;		

		/**
		 * @param rows
		 * @param columns
		 * @param mines
		 */
		private GameType(short rows, short columns, int mines) {
			this.rows = rows;
			this.columns = columns;
			this.mines = mines;
		}

		/**
		 * @return the rows
		 */
		public short getRows() {
			return rows;
		}

		/**
		 * @return the columns
		 */
		public short getColumns() {
			return columns;
		}

		/**
		 * @return the mines
		 */
		public int getMines() {
			return mines;
		}		
		
	}
	
	public Client(URL serverURL) {
		this.serverURL = serverURL;		
	}
	
	public String newGame(GameType gameType) {
		try(CloseableHttpClient httpClient = HttpClients.createDefault();) {
			HttpPost httpPost = new HttpPost(this.serverURL.toString() + GAMES_PATH);
			List<NameValuePair> nvps = new ArrayList<>();
			nvps.add(new BasicNameValuePair("rows", Short.toString(gameType.getRows())));
			nvps.add(new BasicNameValuePair("columns", Short.toString(gameType.getColumns())));
			nvps.add(new BasicNameValuePair("mines", Integer.toString(gameType.getMines())));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			try(CloseableHttpResponse response = httpClient.execute(httpPost)) {
				HttpEntity entity = response.getEntity();
				return EntityUtils.toString(entity);				
			}
		} catch (IOException e) {
			LOGGER.error(Client.ERROR_ON_CONNECTING_TO_THE_SERVER_MSG);
		}
		
		return null;		
	}
	
	public String getGame(String gameId) {
		try(CloseableHttpClient httpClient = HttpClients.createDefault();) {
			HttpGet httpGet = new HttpGet(this.serverURL.toString() + GAMES_PATH + "/" + gameId);
			try(CloseableHttpResponse response = httpClient.execute(httpGet)) {
				HttpEntity entity = response.getEntity();
				return EntityUtils.toString(entity);				
			}
		} catch (IOException e) {
			LOGGER.error(Client.ERROR_ON_CONNECTING_TO_THE_SERVER_MSG);
		}
		
		return null;		
	}
	
	public String revealCell(String gameId, short row, short column) {
		try(CloseableHttpClient httpClient = HttpClients.createDefault();) {
			String revealPath = this.serverURL.toString() + GAMES_PATH + "/" + gameId
				+ CELLS_SUBPATH + "/reveal"; 
			HttpPut httpPut = new HttpPut(revealPath);
			List<NameValuePair> nvps = new ArrayList<>();
			nvps.add(new BasicNameValuePair("row", Short.toString(row)));
			nvps.add(new BasicNameValuePair("column", Short.toString(column)));
			httpPut.setEntity(new UrlEncodedFormEntity(nvps));
			try(CloseableHttpResponse response = httpClient.execute(httpPut)) {
				HttpEntity entity = response.getEntity();
				return EntityUtils.toString(entity);				
			}
		} catch (IOException e) {
			LOGGER.error(Client.ERROR_ON_CONNECTING_TO_THE_SERVER_MSG);
		}
		
		return null;		
	}
	
	public String flagCell(String gameId, short row, short column) {
		try(CloseableHttpClient httpClient = HttpClients.createDefault();) {
			String revealPath = this.serverURL.toString() + GAMES_PATH + "/" + gameId
				+ CELLS_SUBPATH + "/flag"; 
			HttpPut httpPut = new HttpPut(revealPath);
			List<NameValuePair> nvps = new ArrayList<>();
			nvps.add(new BasicNameValuePair("row", Short.toString(row)));
			nvps.add(new BasicNameValuePair("column", Short.toString(column)));
			httpPut.setEntity(new UrlEncodedFormEntity(nvps));
			try(CloseableHttpResponse response = httpClient.execute(httpPut)) {
				HttpEntity entity = response.getEntity();
				return EntityUtils.toString(entity);				
			}
		} catch (IOException e) {
			LOGGER.error(Client.ERROR_ON_CONNECTING_TO_THE_SERVER_MSG);
		}
		
		return null;		
	}

}

package com.deviget.minesweeperclient;

import static org.junit.Assert.assertNotNull;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;
import org.junit.Before;

/**
 * Unit test for Minesweeper Client.
 */
public class ClientTest
{
	private Client client;
	
	@Before
	public void createClient() throws MalformedURLException {
		client  = new Client(new URL("http://localhost:8080"));
	}
	
	@Test
    public void testNewGame()
    {
		String gameStr = this.client.newGame(Client.GameType.EASY);
        assertNotNull("Game created must not be null", gameStr);
        System.out.println(gameStr);
    }
	
	@Test
    public void testGetGame()
    {
		//TODO improve to get parameters dynamically
		String gameId = "5d5b895a1c3d8063e8e813e2";
		String gameStr = this.client.getGame(gameId);
        assertNotNull("Game created must not be null", gameStr);
        System.out.println(gameStr);
    }
	
	@Test
    public void testRevealCell()
    {
		//TODO improve to get parameters dynamically
		String gameId = "5d5b895a1c3d8063e8e813e2";
		short row = 1;
		short column = 2;
		String revealStr = this.client.revealCell(gameId, row, column);
        assertNotNull("Game created must not be null", revealStr);
        System.out.println(revealStr);
    }
	
	@Test
    public void testFlagCell()
    {
		//TODO improve to get parameters dynamically
		String gameId = "5d5b895a1c3d8063e8e813e2";
		short row = 1;
		short column = 2;
		String flagStr = this.client.flagCell(gameId, row, column);
        assertNotNull("Game created must not be null", flagStr);
        System.out.println(flagStr);
    }
}

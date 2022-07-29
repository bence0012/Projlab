package GameControl;

import Interfaces.ToString;
import Players.Virologist;
import Tester.Scene;
import Tiles.Tile;

import java.util.ArrayList;

/**
 * The type Game contoller.
 */
public class GameController implements ToString {
	private static GameMap gameMap;
	private int currentVirologistIndex = 0;

	public GameController() {
		gameMap = new GameMap();

	}

	public GameMap getGameMap() {
		return gameMap;
	}

	public void NextPlayer() {
		if (currentVirologistIndex <= 2)
			currentVirologistIndex++;
		else
			currentVirologistIndex = 0;
		GetCurrentPlayer().Step();
	}

	public Virologist GetCurrentPlayer() {return gameMap.getVirologists().get(currentVirologistIndex);}


	/**
	 * Start game.
	 *
	 * @param numOfPlayers the num of players
	 */
	public void StartGame(long numOfPlayers){
		// A GameController StartGame függvénye inicializálja a játékhoz szügséges teret
		gameMap.CreateMap();
	}

	/**
	 * End game.
	 */
	public static void EndGame(){
		gameMap=new GameMap();
		gameMap.CreateMap();
	}
	/**
	 * Set map.
	 *
	 * @param new_Game_map the new map
	 */
	public void SetMap(GameMap new_Game_map) {
		gameMap = new_Game_map;
	}


	@Override
	public String ToOutput(Scene scene) {
		return "GameController " + scene.keyFromObject(this) + "{\n" +
				"\tgameMap: " + gameMap +"\n"+
				"}";
	}
}


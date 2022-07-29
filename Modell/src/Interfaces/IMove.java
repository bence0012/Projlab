package Interfaces;

import Players.Virologist;
import Tester.Scene;
import Tiles.Tile;

public interface IMove {
	/**
	 * Move
	 * @param v the v
	 * @param t the t
	 */
	boolean Move(Virologist v, Tile t);


	String ToOutput(Scene scene);
}



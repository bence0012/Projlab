package GameControl;

import Interfaces.Steppable;

import java.util.ArrayList;

/**
 * A léptethető objektumokat kezelő osztály
 */
public class Timer{

	/**
	 * A léptethető objektumok
	 */
	private static ArrayList<Steppable> steppables = new ArrayList<>();


	/**
	 * Tick.
	 */
	public void Tick(){
		// TODO add implementation
	}

	/**
	 * Berak egy Steppable-t a steppables-be
	 * @param steppable a Steppable
	 */
	public static void AddSteppable(Steppable steppable){
		steppables.add(steppable);
	}

	/**
	 * kivesz egy Steppable-t a steppables-ből
	 * @param steppable a Steppable
	 */
	public static void RemoveSteppable(Steppable steppable){
		steppables.remove(steppable);
	}

}


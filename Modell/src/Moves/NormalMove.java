package Moves;

import Interfaces.IMove;
import Players.Virologist;
import Tester.Scene;
import Tiles.Tile;

public class NormalMove implements IMove {
	/**
	 * Ez a függvény felelős azért, hogy a vírus egyik tileról a másikra lépjen.
	 *
	 * @param virologist a Virológus aki a mozgást végzi
	 * @param btile  ahov aszeretnénk menni
	 */
	public boolean Move(Virologist virologist, Tile btile){
		virologist.GetTile().RemoveVirologist(virologist);
		btile.AddVirologist(virologist);
		btile.GiveItemTo(virologist);
		return true;
	}
	@Override
	public String toString() {
		return "NormalMove";
	}

	@Override
	public String ToOutput(Scene scene) {
		return "NormalMove " + scene.keyFromObject(this) + "{\n" +
				"\tThis class has no parameters!\n" +
				"}";
	}
}
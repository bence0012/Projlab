package Tiles;

import Interfaces.ToString;
import Players.Virologist;
import Tester.Scene;

import java.util.ArrayList;
import java.util.Random;

/**
 * A játéktér egy mezője.
 * A Tile-ok együttese képezi a pályát.
 * A rajta lévő objektumok kapcsolatba léphetnek egymással.
 */
public class Tile implements ToString{
	/**
	 * A mező szomszédai
	 */
	private ArrayList<Tile> neighbours;

	/**
	 * Megmutatja, hogy a mezőn van-e genetikai kód,
	 * felszerelés vagy valamilyen anyag
	 */
	protected boolean hasItem;

	/**
	 * A mezőn elhelyezkedő virológusok
	 */
	private ArrayList<Virologist> virologists;

	/**
	 * Tile konstruktor, beállítjuk az objektum tagváltozóit.
	 */
	public Tile(){
		neighbours = new ArrayList<>();
		hasItem = false;
		virologists = new ArrayList<>();
	}

	public ArrayList<Tile> GetNeighbours() {
		return neighbours;
	}

	/**
	 * Leveszi a virológust a mezőről
	 *
	 * @param virologist the virologist
	 */
	public void RemoveVirologist(Virologist virologist) {
		virologists.remove(virologist);
	}

	/**
	 * Hozzáadja a mezőhöz a virológust
	 *
	 * @param virologist the virologist
	 */
	public void AddVirologist(Virologist virologist){
		virologists.add(virologist);
		virologist.SetTile(this);
	}

	/**
	 * A mezőn lévő dolgot odaadja a virológusnak.
	 *
	 * @param virologist the virologist
	 */
	public void GiveItemTo(Virologist virologist){
		//basic tile does not give the virologist any item,
		//classes extending this class will implement this function.
	}

	/**
	 * Beállitja a hasItem-et a megadott paraméterre
	 *
	 * @param bool van-e item a mezőn
	 */
	public void SetHasItem(boolean bool){
		hasItem = bool;
	}

	/**
	 * Visszaad egy véletlenszerűen választott mezőt az objektum
	 * szomszédai közül.
	 *
	 * @return véletlen választott szomszéd Tile
	 */
	public Tile GetRandomTile(){
		// create instance of Random class
		Random rand = new Random();

		// Generate random integers in range 0 to size of neighbours
		int rand_neighbour = rand.nextInt(neighbours.size());

		return neighbours.get(rand_neighbour);
	}

	/**
	 * Új szomszédot ad hozzá a mezőhöz.
	 *
	 * @param tile a szomszédos mező
	 */
	public void AddNeighbour(Tile tile){
		boolean haveIt = false;
		for (Tile t: neighbours) {
			if(t.equals(tile))
				haveIt = true;
		}
		if(!haveIt) neighbours.add(tile);
	}

	public ArrayList<Virologist> getVirologists() {
		return virologists;
	}
	public ArrayList<Tile> getNeighbours() {return neighbours;}

	public String ToOutput(Scene scene) {
		// Eloszor meg kell keresni a valtozok neveit a sceneben:
		String objectName = scene.keyFromObject(this);
		// Meg kell keresni a neveket az osszes attributumhoz is, szepen vegig kell menni minden attributumon amit fent
		// deklaraltunk:
		StringBuilder virologistsNames = new StringBuilder();
		int viro_cnt = 0;
		for (Virologist v : virologists) {
			virologistsNames.append("\t").append(++viro_cnt).append(". ").append("virologistName: ").append(scene.keyFromObject(v)).append("\n");
		}
		StringBuilder neighboursNames = new StringBuilder();
		int neighbour_cnt = 0;
		for (Tile t : neighbours) {
			neighboursNames.append("\t").append(++neighbour_cnt).append(". ").append("neighbourName: ").append(scene.keyFromObject(t)).append("\n");
		}
		// Ha egy attributum nem osztály, hanem int, bool stb, akkor csak darabszamot, true / false-ot kell kiirni
		String hasItem = String.valueOf(this.hasItem);
		// Majd végül össze kell rakni a kimenetet:
		return this.getClass().getSimpleName() + " " + objectName + " {\n" +
				virologistsNames +
				neighboursNames +
				"\thasItem: " + hasItem + "\n" +
				"}";
	}
}


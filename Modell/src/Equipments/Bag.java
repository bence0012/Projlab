package Equipments;

import Agents.Agent;
import Players.Virologist;
import Tester.Scene;

/**
 * Olyn védőfelszerelés, mely a vieslőjének plusz anyaggyűtési képességet ad
 */

public class Bag extends Equipment {
	/**
	 * Eltárolja, hogy éppen melyik Virológusnál van
	 */
	protected Virologist virologist;

	public Bag() {
		this.virologist = null;

	}

	/**
	 *Ha a Viselő Virológust támadás éri, akkor ez a függvény dönti el, hogy sikeres
	 * volt-e az adott Vírus elleni védekezés, jelen esetben nem véd semmi ellen
	 *
	 * @param other - melyik virológus támadta meg a felszerelést viselő virológust
	 * @param a - melyik ágens ellen kell védekezni
	 * @return boolean - véletlenszerűen sorsolva
	 */
	//@Override
	public boolean Defend(Virologist other, Agent a){
		return false;
	}

	/**
	 * Amint a felszerelés valakinél van, felszereléstől függően,
	 * lehet annak állandó hatása, amit kifejt, jelen esetben semmit
	 *
	 * @param virologist akire a hatás kerül
	 */
	public void ApplyEffect(Virologist virologist){
		virologist.setMaxStorage(9);
	}

	@Override
	public void Attack(Virologist player, Virologist other) {

	}

	/**
	 * Ha a Virológus elveszti az adott felszerelést akkor
	 * lekerül róla a felszerelés nyújotta állandó hatás, ennél a felszerelésnél nincsen
	 */

	public void RemoveEffect(){
		virologist.setMaxStorage(6);
	}

	/**
	 * Beállítja, hogy kinél van a felszerelés
	 *
	 * @param v akinél a Védőfelszerelés lesz
	 */

	public void SetVirologist(Virologist v){
		virologist=v;

	}

	@Override
	public String ToOutput(Scene scene) {
		// Eloszor meg kell keresni a valtozok neveit a sceneben:
		String objectName = scene.keyFromObject(this);
		// Meg kell keresni a neveket az osszes attributumhoz is, szepen vegig kell menni minden attributumon amit fent
		// deklaraltunk:

		String virologistName = scene.keyFromObject(this.virologist);
		// Majd végül össze kell rakni a kimenetet:
		return "Bag " + objectName + " {\n" +
				"\tvirologistName: " + virologistName + "\n" +
				"}";
	}
}


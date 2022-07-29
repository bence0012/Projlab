package Equipments;

import Agents.Agent;
import Interfaces.ToString;
import Players.Virologist;
import Tester.Scene;

/**
 * A Védőfelszereléseket megvalósító absztrakt osztály
 */
public abstract class Equipment implements ToString{
	/**
	 * Eltárolja, hogy éppen melyik Virológusnál van
	 */
	protected Virologist virologist;

	public Equipment() {
		this.virologist = null;

	}

	/**
	 *Ha a Viselő Virológust támadás éri, akkor ez a függvény dönti el, hogy sikeres
	 * volt-e az adott Vírus elleni védekezés
	 *
	 * @param other - melyik virológus támadta meg a felszerelést viselő virológust
	 * @param a - melyik ágens ellen kell védekezni
	 * @return boolean - false alapesetben
	 */
	public abstract boolean Defend(Virologist other, Agent a);

	/**
	 * Ha a Virológus elveszti az adott felszerelést akkor
	 * lekerül róla a felszerelés nyújotta állandó hatás
	 */
	public abstract void RemoveEffect();

	/**
	 * Beállítja, hogy kinél van a felszerelés
	 *
	 * @param v akinél a Védőfelszerelés lesz
	 */
	public abstract void SetVirologist(Virologist v);

	/**
	 * Amint a felszerelés valakinél van, felszereléstől függően,
	 * lehet annak állandó hatása, amit kifejt
	 *
	 * @param virologist akire a hatás kerül
	 */
	public abstract void ApplyEffect(Virologist virologist);

	/**
			* A védőfelszereléssel támadást indítunk egy másik Virológusra
*
		*@param other - egy ellenséges Virológus akit megtámadunk
*/
	public abstract void Attack(Virologist player, Virologist other);

	@Override
	public String ToOutput(Scene scene) {
		// Eloszor meg kell keresni a valtozok neveit a sceneben:
		String objectName = scene.keyFromObject(this);
		// Meg kell keresni a neveket az osszes attributumhoz is, szepen vegig kell menni minden attributumon amit fent
		// deklaraltunk:
		String virologistName = scene.keyFromObject(this.virologist);
		// Majd végül össze kell rakni a kimenetet:
		return this.getClass().getSimpleName() + " " + objectName + " {\n" +
				"\tvirologistName: " + virologistName + "\n" +
				"}";
	}
}



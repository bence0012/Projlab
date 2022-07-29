package Tiles;

import Equipments.Equipment;
import Players.Virologist;
import Tester.Scene;
/**
 * Egy különleges Tile, a dolga,
 * hogy az első virológusnak aki rálép adjon egy Equipment-et.
 */
public class Bunker extends Tile{

	/**
	 * Az Equipment amit odaad a virológusnak.
	 */
	private Equipment equipment;

	public boolean HasEquipment() {
		return equipment != null;
	}

	/**
	 * Bunker konstruktor, beállítjuk az objektum tagváltozóit.
	 */
	public Bunker(){
		equipment = null;
	}

	/**
	 * Bunker egy paramáteres konstruktor, beállítjuk az objektum tagváltozóit.
	 *
	 * @param equipment Az Equipment amit odaad a virológusnak.
	 */
	public Bunker(Equipment equipment){
		this.equipment = equipment;
	}

	/**
	 * Odaadja, az első virológusnak aki rálép a mezőre, equipment-et,
	 * később érkező virológusok nem kapnak semmit ettől a mezőtől.
	 *
	 * @param virologist A virológus akinek a felszerelést kell adni.
	 */
	public void GiveItemTo(Virologist virologist){
		if(hasItem){
			if (virologist.AddEquipment(equipment)) {
				SetHasItem(false);
				equipment = null;
			}
		}
	}

	/**
	 * Rárak a mezőre egy Equipment-et
	 *
	 * @param equipment a rárakandó felszerelés.
	 */
	public void AddEquipment(Equipment equipment){
		this.equipment = equipment;
		hasItem = true;
	}

	@Override
	public String ToOutput(Scene scene){
		StringBuffer begin = new StringBuffer(super.ToOutput(scene));
		begin.deleteCharAt(begin.length()-1);
		// Meg kell keresni a neveket az osszes attributumhoz is, szepen vegig kell menni minden attributumon amit fent
		// deklaraltunk:
		String equipmentName = scene.keyFromObject(this.equipment);
		// Majd végül össze kell rakni a kimenetet:
		return begin +
				"\tequipmentName: " + equipmentName + "\n" +
				"}";
	}

}


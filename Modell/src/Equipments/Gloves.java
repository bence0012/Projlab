package Equipments;

import Agents.Agent;
import Players.Virologist;
import Tester.Scene;

/**
 * Olyan Védőfelszerelés, mely a Viselőre kent ágaenset a kenőre keni vissza
 */
public class Gloves extends Equipment  {
    /**
     * Eltárolja, hogy éppen melyik Virológusnál van
     */
    protected Virologist virologist;
	/**
	 * hogy mennyiszer használja a kesztyűt
	 */
    private int usedCounter;

    public Gloves() {
        this.virologist = null;
        this.usedCounter = 3;
    }

    /**
     * Ha a Viselő Virológust támadás éri, akkor ez a függvény dönti el, hogy sikeres
     * volt-e az adott Vírus elleni védekezés
     *
     * @param other - melyik virológus támadta meg a felszerelést viselő virológust
     * @param a     - melyik ágens ellen kell védekezni
     * @return boolean - véletlenszerűen sorsolva
     */
    @Override
    public boolean Defend(Virologist other, Agent a) {
        if (!other.hascraftedAgent(a))
            a.Effect(this.virologist, other);
        this.usedCounter--;
        if (this.usedCounter == 0) {
            virologist.RemoveEquipment(this);
            this.SetVirologist(null);
        }
        return true;
    }

    /**
     * Amint a felszerelés valakinél van, felszereléstől függően,
     * lehet annak állandó hatása, amit kifejt, jelen esetben semmit
     *
     * @param v akire a hatás kerül
     */
    public void ApplyEffect(Virologist v) {
    }

    @Override
    public void Attack(Virologist player, Virologist other) { }

    /**
     * Ha a Virológus elveszti az adott felszerelést akkor
     * lekerül róla a felszerelés nyújotta állandó hatás, ennél a felszerelésnél nincsen
     */
    public void RemoveEffect() {
    }

    /**
     * Beállítja, hogy kinél van a felszerelés
     *
     * @param v akinél a Védőfelszerelés lesz
     */
    public void SetVirologist(Virologist v) {
        virologist = v;
    }

	@Override
	public String ToOutput(Scene scene) {
		// Eloszor meg kell keresni a valtozok neveit a sceneben:
		String objectName = scene.keyFromObject(this);
		// Meg kell keresni a neveket az osszes attributumhoz is, szepen vegig kell menni minden attributumon amit fent
		// deklaraltunk:
		String virologistName = scene.keyFromObject(this.virologist);
		// Ha egy attributum nem osztály, hanem int, bool stb, akkor csak darabszamot, true / false-ot kell kiirni
		String usedCounter = String.valueOf(this.usedCounter);
		// Majd végül össze kell rakni a kimenetet:
		return "Gloves " + objectName + " {\n" +
					"\tvirologistName: " + virologistName + "\n" +
					"\tusedCounter: " + usedCounter + "\n" +
				"}";
	}
}


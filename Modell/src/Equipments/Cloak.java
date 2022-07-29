package Equipments;

import Agents.Agent;
import Players.Virologist;
import Tester.Scene;

import java.util.Random;

/**
 * Olyan Védőfelszerelés, mely a Viselőre kent ágaenseket 82,3% eséllyel tekinti semmisnek
 */
public class Cloak extends Equipment {
    /**
     * Eltárolja, hogy éppen melyik Virológusnál van
     */
    protected Virologist virologist;
    private double randomValue;

    public void setRandomValue(double randomValue) {
        this.randomValue = randomValue;
    }

    public double getRandomValue() {
        return randomValue;
    }

    public Cloak() {
        this.virologist = null;
        randomValue = 0.823;
    }

    /**
     * Ha a Viselő Virológust támadás éri, akkor ez a függvény dönti el, hogy sikeres
     * volt-e az adott Vírus elleni védekezés, mely 82,3% eséllyel sikeres védekezést jelent
     *
     * @param other - melyik virológus támadta meg a felszerelést viselő virológust
     * @param a     - melyik ágens ellen kell védekezni
     * @return boolean - véletlenszerűen sorsolva
     */
    @Override
    public boolean Defend(Virologist other, Agent a) {
        //Véletlengenerátor, de az értékadásban nem vagyok 100%
        Random random = new Random();

        return random.nextDouble() <= randomValue;
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
    public void Attack(Virologist player, Virologist other) {

    }

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
		// Ha egy attributum nem osztály, hanem int, bool stb, akkor csak darabszamot, true / false-ot kell kiirni
		String randomValue = String.valueOf(this.randomValue);
		// Majd végül össze kell rakni a kimenetet:
		return "Cloak " + objectName + " {\n" +
				"\tvirologistName: " + virologistName + "\n" +
				"\trandomValue: " + randomValue + "\n" +
				"}";
	}
}
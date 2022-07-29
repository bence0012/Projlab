package Agents;

import Interfaces.Steppable;
import Players.Virologist;
import Tester.Scene;

/**
 * Ez az absztrakt osztály azoknak az osztályoknak a közös őse, amik vagy vírusok vagy vakcinák hatásait fejtik ki a játékon belül.
 * Azért lett absztrakt, mert sose szeretnénk belőle létrehozni példányt, mivel nincsen például víz vírus ami nem csinál semmit.
 */
public abstract class Agent implements Steppable{
    /**
     * Virologist -> az ágensnek tudnia kell melyik virológusra fejti ki hatását,
     * annak érdekében, hogy ha a hatás érvényét veszti, az adott virológusról törölhessük az effekteket.
     */
    protected Virologist virologist;

    /**
     * az ágens életciklusát reprezentálja. Kenéskor vagy craft-oláskor
     * ezt az értéket valamilyen nem nulla számra kell állítani.
     */
    protected long timer;

    /**
     * ezt a változót a timer beállításával egy időben kell állítani,
     * ha valamilyen okból kifolyólag a timert újra kell indítani,
     * ennek a tagváltozónak a segítségével könnyen megtehetjük.
     */
    protected final long baseTimer = 10;

    /**
     * Az Agent osztály konstruktora; beállítja a tagváltozókat.
     */
    public Agent(){
		timer = baseTimer;
		virologist = null;
	}

    /**
     * Másoló ctor az Agent osztálynak
     *
     * @param agent a lemásolandó Agent
     */
    public Agent(Agent agent){
		this.timer = agent.timer;
		this.virologist = agent.virologist;
	}

    /**
     * Egy absztrakt függvény, a leszármazottak ebben a függvényben
     * fogják megvalósítani az effektjük kifejtését a virológusra,
     * itt állíthatják például a move interface-t is.
     *
     * @param player a Virológus aki a kenést kezdeményezte
     * @param other  a Virológus akire kenni szeretnénk
     */
    public abstract void Effect(Virologist player, Virologist other);

    /**
     * Egy absztrakt függvény, a leszármazottak megvalósításukban azt fejtik ki,
     * hogy mi történik amikor az ágens vagy csak simán lejár, azaz az inevtory-ban felhasználás nélkül eltűnik,
     * vagy lejár az effekt egy virológuson, mivel ezt az ágenst felkenték rá
     */
    public abstract void RemoveEffect();


    /**
     * Az ágensek képesek bizonyis a specifikációban definiált esetekben megvédeni más ágensektől.
     * Ezt az absztrakt függvényt a leszármazott úgy valósítja meg,
     * hogy ha valamilyen ágenstől sikeresen megvédi a virológust igazat,
     * ellenkező esetben hamisat adjon vissza.
     *
     * @param agent Az ágens ami ellen védekezik a virológus.
     * @return A kenés sikeressége (true = sikerült, false = nem sikerült)
     */
    public abstract boolean Defend(Agent agent);

    /**
     * Kraftoláskor az adott génkódot lemásoljuk és abból készítünk
     * egy használható ágenst.
     *
     * @return A használatra kész lekraftolt ágens.
     */
    public abstract Agent Clone();

    /**
     * Visszaállítja a timert a kezdeti értékére.
     */
    public void ResetTimer(){
		timer = baseTimer;
	}

	/**
	 * Ez a steppable interface-től kapott függvényünk implementációja.
	 * A függvény a timert csökkenti, amikor nullához ér meghívja az adott ágens RemoveEffect() függvényét.
	 */
	public void Step(){
		timer--;
		if(timer == 0) {
			RemoveEffect();
		}
	}

    /**
     * Beállítja a virológust, hogy tudja az ágens kire fejti ki a hatását.
     *
     * @param v a virológus
     */
    public void SetVirologist(Virologist v){
		virologist = v;
	}

    public String ToOutput(Scene scene) {
        // Eloszor meg kell keresni a valtozok neveit a sceneben:
        String objectName = scene.keyFromObject(this);
        // Meg kell keresni a neveket az osszes attributumhoz is, szepen vegig kell menni minden attributumon amit fent
        // deklaraltunk:
        String virologistName = scene.keyFromObject(this.virologist);
        // Ha egy attributum nem osztály, hanem int, bool stb, akkor csak darabszamot, true / false-ot kell kiirni
        String timer = String.valueOf(this.timer);
        // Majd végül össze kell rakni a kimenetet:
        return this.getClass().getSimpleName() + " " + objectName + " {\n" +
                "\tvirologistName: " + virologistName + "\n" +
                "\ttimer: " + timer + "\n" +
                "}";
    }
}


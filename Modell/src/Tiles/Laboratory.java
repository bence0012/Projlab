package Tiles;

import Agents.Agent;
import Agents.BearVirus;
import Players.Virologist;
import Tester.Scene;

import java.util.Random;

/**
 * Egy különleges Tile, a dolga,
 * hogy a virológusnak aki rálép adjon egy genetikai kódot.
 */
public class Laboratory extends Tile {
    /**
     * A mezőn eltárolt genetikai kód, ezt tanulja meg a virológus
     */
    private Agent virus;
    private Agent vaccine;
    private double randomValue;

    public void setRandomValue(double randomValue) {
        this.randomValue = randomValue;
    }

    public double getRandomValue() {
        return randomValue;
    }

    /**
     * Laboratory konstruktor, beállítjuk az objektum tagváltozóit.
     */
    public Laboratory() {
        virus = null;
        vaccine = null;
        randomValue = 0.1;
    }

    /**
     * Ad egy Agent-et genetikai kódot a virológusnak aki rálép a mezőre
     *
     * @param virologist A virológus aki megtanulja a genetikai kódot.
     */
    public void GiveItemTo(Virologist virologist) {
        Random random = new Random();
        if (random.nextDouble() <= randomValue) {
            BearVirus bearVirus = new BearVirus();
            if (!virologist.Bear_Defend(bearVirus)) {
                virologist.AddAffectedBy(bearVirus);
                virologist.SetMoveType(bearVirus);
            }
        }
        virologist.LearnGeneticCode(virus);
        virologist.LearnGeneticCode(vaccine);
    }

    /**
     * Rátesz egy genetikai kódot a laboratóriumra
     *
     * @param virus   A genetikai kód
     * @param vaccine A genetikai kód
     */
    public void AddAgent(Agent virus, Agent vaccine) {
        this.virus = virus;
        this.vaccine = vaccine;
        hasItem = true;
    }

    public void AddVirus(Agent virus) {
        this.virus = virus;
        hasItem = true;
    }

    public void AddVaccine(Agent vaccine) {
        this.vaccine = vaccine;
        hasItem = true;
    }

    @Override
    public String ToOutput(Scene scene) {
        StringBuffer begin = new StringBuffer(super.ToOutput(scene));
        begin.deleteCharAt(begin.length() - 1);
        // Meg kell keresni a neveket az osszes attributumhoz is, szepen vegig kell menni minden attributumon amit fent
        // deklaraltunk:
        String virusName = scene.keyFromObject(this.virus);
        String vaccineName = scene.keyFromObject(this.vaccine);
        String randomvalue = String.valueOf(this.randomValue);
        // Majd végül össze kell rakni a kimenetet:
        return begin +
                "\tvirusName: " + virusName + "\n" +
                "\tvaccineName: " + vaccineName + "\n" +
                "\trandomvalue: " + randomvalue + "\n" +
                "}";
    }


}


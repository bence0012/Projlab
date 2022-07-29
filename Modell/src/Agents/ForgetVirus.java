package Agents;

import Interfaces.ToString;
import Players.Virologist;

/**
 * Ez az osztály felelős a felejtővírus megvalósításáért.
 */
public class ForgetVirus extends Agent implements ToString {
    /**
     * Ez a függvény felelős azért, hogy a vírus kifejtse hatását a megkent virológuson.
     *
     * @param player a Virológus aki a kenést kezdeményezte
     * @param other  a Virológus akire kenni szeretnénk
     */
    public void Effect(Virologist player, Virologist other) {
        boolean defended = other.Defend(player, this);
        if (!defended) {
            this.ResetTimer();
            other.RemoveAllLearnedGenes();
            player.RemoveFromAgentStorage(this);
        }
    }

    /**
     * Ezt a függvényt kell meghívni, ha le akarjuk venni valamilyen módon a vírust a virológusrol akire rákenték,
     * vagy akinek az inventory-jában tároljuk.
     */
    public void RemoveEffect() {
        try {
            this.virologist.RemoveFromAgentStorage(this);
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Megmondja, hogy a virológus aki ezt a vírust birokoltja, védelmet élvez e a paraméterül kapott ágens ellen.
     *
     * @param agent Az ágens ami ellen védekezik a virológus.
     * @return a védelem sikeressége
     */
    public boolean Defend(Agent agent) {
        return false;
    }

    /**
     * @return Egy új ForgetVirus objektum
     */
    @Override
    public Agent Clone() {
        return new ForgetVirus();
    }
}


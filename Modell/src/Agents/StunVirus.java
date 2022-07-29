package Agents;

import Interfaces.IMove;
import Interfaces.ToString;
import Moves.NormalMove;
import Players.Virologist;
import Tiles.Tile;

/**
 * Ez az osztály felelős a megbénító vírus megvalósításáért.
 */
public class StunVirus extends Agent implements IMove, ToString {
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
            other.AddAffectedBy(this);
            other.SetMoveType(this);
            this.virologist = other;
            player.RemoveFromAgentStorage(this);
        }
    }

    /**
     * Ezt a függvényt kell meghívni, ha le akarjuk venni valamilyen módon a vírust a virológusrol akire rákenték,
     * vagy akinek az inventory-jában tároljuk.
     */
    public void RemoveEffect() {
        // try-catch is needed since virologist might be null
        try {
            IMove normalMove = new NormalMove();
            this.virologist.SetMoveType(normalMove);
            this.virologist.RemoveAffectedBy(this);
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
        return agent.getClass() == DanceVirus.class;
    }

    /**
     * @return Egy új StunVirus objektum
     */
    @Override
    public Agent Clone() {
        return new StunVirus();
    }

    /**
     * Itt valósítjuk meg a move interface függvényét, hogy a vírus használható legyen moveType-ként.
     *
     * @param virologist A virológus akit mozgatni fog a vírus
     * @param new_tile   A mező, ahova ezek után lép
     */
    public boolean Move(Virologist virologist, Tile new_tile) {
        return false;
    }
}
package Agents;

import Interfaces.IMove;
import Interfaces.ToString;
import Players.Virologist;
import Tiles.Storage;
import Tiles.Tile;

/**
 * Ez az osztály felelős a Medvevírus megvalósításáért.
 */
public class BearVirus extends Agent implements IMove, ToString {
    /**
     * Ez a függvény felelős azért, hogy a vírus kifejtse hatását a megkent virológuson.
     *
     * @param player a Virológus aki a kenést kezdeményezte
     * @param other  a Virológus akire kenni szeretnénk
     */
    public void Effect(Virologist player, Virologist other) {
        boolean defended = other.Defend(player, this);
        if (!defended) {
            other.AddAffectedBy(this);
            other.SetMoveType(this);
            this.virologist = other;
        }
    }


    /**
     * Ezt a függvényt kell meghívni, ha le akarjuk venni valamilyen módon a vírust a virológusrol akire rákenték,
     * vagy akinek az inventory-jában tároljuk.
     */
    public void RemoveEffect() {
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
     * @return Egy új BearVirus objektum
     */
    @Override
    public Agent Clone() {
        return new BearVirus();
    }


    /**
     * A dokumentációban leírtanknak megfelelően lépteti a vírust
     */
    public void Step() {
        Tile move_to = this.virologist.GetTile().GetRandomTile();
        this.Move(virologist, move_to);
    }


    /**
     * Itt valósítjuk meg a move interface függvényét, hogy a vírus használható legyen moveType-ként.
     *
     * @param virologist A virológus akit mozgatni fog a vírus
     * @param new_tile   A mező, ahova ezek után lép
     */
    public boolean Move(Virologist virologist, Tile new_tile) {
        Tile prev_tile = virologist.GetTile();
        prev_tile.RemoveVirologist(virologist);
        new_tile.AddVirologist(virologist);
        new_tile.GiveItemTo(virologist);

        for (Virologist other : new_tile.getVirologists()
        ) {
            virologist.Attack(other, this.Clone());
        }
        if (new_tile instanceof Storage) {
            ((Storage) new_tile).SetNukleo(0);
            ((Storage) new_tile).SetAmino(0);
        }
        return true;
    }
}

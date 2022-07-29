package GameControl;

import Agents.*;
import Equipments.*;
import Interfaces.ToString;
import Players.Virologist;
import Tester.Scene;
import Tiles.Bunker;
import Tiles.Laboratory;
import Tiles.Storage;
import Tiles.Tile;

import java.util.ArrayList;

/**
 * A pálya kezeléséért felelős osztály
 */
public class GameMap implements ToString {
    /**
     * A különböző ágensek száma
     */
    private final long agentCount = 3;

    /**
     * A pályán lévő Tile-ok
     */
    private final ArrayList<Tile> tiles = new ArrayList<>();
    /**
     * A pályán lévő virológusok
     */
    private final ArrayList<Virologist> virologists = new ArrayList<>();

    /**
     * Hozzáad egy virológust a map-hoz
     *
     * @param virologist a virológus
     */
    public void AddVirologist(Virologist virologist) {
        virologists.add(virologist);
    }

    /**
     * Hozzáad egy Tile-t a map-hoz
     *
     * @param tile a Tile
     */
    public void AddTile(Tile tile) {
        tiles.add(tile);
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public ArrayList<Virologist> getVirologists() {
        return virologists;
    }

    public GameMap() {
        this.CreateMap();
    }

    /**
     * a pálya elkészítéséért felelős függvény
     *
     */
    public void CreateMap() {
        Virologist v1 = new Virologist("GreenVirologist");

        Virologist v2 = new Virologist("BlueVirologist");
        Virologist v3 = new Virologist("RedVirologist");
        Virologist v4 = new Virologist("YellowVirologist");
        v1.SetMap(this);
        v2.SetMap(this);
        v3.SetMap(this);
        v4.SetMap(this);


        Tile t1 = new Tile();
        Tile t2 = new Tile();
        Tile t3 = new Tile();
        Tile t4 = new Tile();

        Laboratory l1 = new Laboratory();
        Laboratory l2 = new Laboratory();
        Laboratory l3 = new Laboratory();

        Storage s1 = new Storage();
        Storage s2 = new Storage();

        Bunker b1 = new Bunker();
        Bunker b2 = new Bunker();
        Bunker b3 = new Bunker();

        Bag gloves = new Bag();
        Axe axe = new Axe();
        Cloak cloak = new Cloak();

        StunVirus stunVirus = new StunVirus();
        StunVaccine stunVaccine = new StunVaccine();

        DanceVirus danceVirus = new DanceVirus();
        DanceVaccine danceVaccine = new DanceVaccine();

        ForgetVirus forgetVirus = new ForgetVirus();
        ForgetVaccine forgetVaccine = new ForgetVaccine();

        DanceVirus fvi = new DanceVirus();
        ForgetVaccine fvacc = new ForgetVaccine();
        v1.AddToAgentStorage(fvi);
        v3.AddToAgentStorage(fvacc);

        // filling up the tiles
        t1.AddVirologist(v1);
        t2.AddVirologist(v2);
        t3.AddVirologist(v3);
        t4.AddVirologist(v4);
        l1.AddAgent(stunVirus, stunVaccine);
        l2.AddAgent(danceVirus, danceVaccine);
        l3.AddAgent(forgetVirus, forgetVaccine);
        b1.AddEquipment(gloves);
        b2.AddEquipment(axe);
        b3.AddEquipment(cloak);
        s1.AddNukleo(6);
        s2.AddAmino(6);

        // Making connections between the tiles
        t1.AddNeighbour(b1);
        b1.AddNeighbour(t1);

        t1.AddNeighbour(l1);
        l1.AddNeighbour(t1);
        t1.AddNeighbour(l2);
        l2.AddNeighbour(t1);
        t1.AddNeighbour(l3);
        l3.AddNeighbour(t1);

        t1.AddNeighbour(s2);
        s2.AddNeighbour(t1);

        b1.AddNeighbour(b2);
        b2.AddNeighbour(b1);

        b2.AddNeighbour(l3);
        l3.AddNeighbour(b2);

        t2.AddNeighbour(s1);
        s1.AddNeighbour(t2);

        s2.AddNeighbour(b3);
        b3.AddNeighbour(s2);

        b3.AddNeighbour(l1);
        l1.AddNeighbour(b3);

        l3.AddNeighbour(s1);
        s1.AddNeighbour(l3);

        t3.AddNeighbour(l1);
        l1.AddNeighbour(t3);

        l1.AddNeighbour(l2);
        l2.AddNeighbour(l1);

        s1.AddNeighbour(t4);
        t4.AddNeighbour(s1);


        // Saving tiles in the model
        tiles.add(t1);
        tiles.add(b1);
        tiles.add(b2);
        tiles.add(t2);
        tiles.add(s2);
        tiles.add(b3);
        tiles.add(l3);
        tiles.add(s1);
        tiles.add(t3);
        tiles.add(l1);
        tiles.add(l2);
        tiles.add(t4);

        // Saving virologists in the model
        virologists.add(v1);
        virologists.add(v2);
        virologists.add(v3);
        virologists.add(v4);
    }

    /**
     * Ha a virológus megszerzi az összes genetikai kódot megnyeri a játékot
     *
     * @param numOfCodes a genetikai kódok száma
     */
    public void Win(long numOfCodes) {
        if (numOfCodes/2 == agentCount) {
            GameController.EndGame();
        }
    }

    @Override
    public String ToOutput(Scene scene) {
        String finalString = "GameMap " + scene.keyFromObject(this) + " {\n" +
                "\tagentCount: " + String.valueOf(agentCount) + "\n" +
                "\tTiles: ";
        for (Tile elem : tiles) {
            finalString += scene.keyFromObject(elem) + " ";
        }
        finalString += "\n\tvirologists: ";
        for (Virologist elem : virologists) {
            finalString += scene.keyFromObject(elem) + " ";
        }
        finalString += "\n}";


        return finalString;
    }

}



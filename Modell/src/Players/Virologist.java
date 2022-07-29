package Players;

import Agents.Agent;
import Agents.BearVirus;
import Agents.StunVirus;
import Equipments.Cloak;
import Equipments.Equipment;
import GameControl.GameMap;
import GameControl.Timer;
import Interfaces.IMove;
import Interfaces.Steppable;
import Interfaces.ToString;
import Moves.NormalMove;
import Tester.Scene;
import Tiles.Tile;

import java.util.ArrayList;

/**
 * Egy irányítható karaktert nyújt, amivel lehet mozogni,
 * más virológust megtámadni, ellopni és felvenni tárgyakat és ágenseket készíteni.
 */
public class Virologist implements Steppable, ToString {
    /**
     * Virológus konstruktora névvel
     *
     * @return nincs -
     */
    public Virologist(String name) {
        this.name = name;
    }
    /**
     * Visszaadja a virológus nevét
     *
     * @return name
     */
    public String GetName() {
        return this.name;
    }

    /**
     * A virológus neve
     */
    private String name;
    /**
     * A virológus mozgásának typusa
     */
    private IMove moveType = new NormalMove();
    /**
     * A Tile, ahol a virológus van
     */
    private Tile tile;
    /**
     * A virológus ágenseinek, felszereléseinek és anyagainak a maximum száma
     */
    private int maxStorage = 6;
    /**
     * A jelenleg felhasznált tárhelyek száma
     */
    private int usedStorage = 0;
    /**
     * A virológus aminosavainak száma
     */
    private int amino = 0;
    /**
     * A virológus nukleotidjainak száma
     */
    private int nukleo = 0;
    /**
     * A pálya, amin a virólógus van
     */
    private GameMap gameMap;



    /**
     * A megtanult genetikai kódok listája
     */
    private final ArrayList<Agent> learnedAgents = new ArrayList<>();
    /**
     * A virológus felszereléseinek  listája
     */
    private final ArrayList<Equipment> equipments = new ArrayList<>();


    /**
     * A virológus ágenseinek listája
     */
    private final ArrayList<Agent> agentStorage = new ArrayList<>();
    /**
     * A virológusra rákent ágensek listája
     */
    private final ArrayList<Agent> affectedBy = new ArrayList<>();

    /**
     * Az amino-t beállítja a megadott számra
     *
     * @param amino az új mennyiség
     */
    public void setAmino(int amino) {
        this.amino = amino;
    }

    /**
     * Visszaadja az aminosavak számát
     *
     * @return amino
     */
    public int getAmino() {
        return this.amino;
    }

    /**
     * Az nukleo-t beállítja a megadott számra
     *
     * @param nukleo az új mennyiség
     */
    public void setNukleo(int nukleo) {
        this.nukleo = nukleo;
    }

    /**
     * Visszaadja az nukleotidok számát
     *
     * @return nukleo
     */
    public int getNukleo() {
        return this.nukleo;
    }

    /**
     * Mozgatja a virológust a megadott Tile-ra
     *
     * @param target a Tile amire mozogni akarunk
     */

    /**
     * Visszaadja az equipments listat
     *
     * @return euipments
     */
    public ArrayList<Equipment> getEquipments() {
        return equipments;
    }
    /**
     * Visszaadja a megtanult agens-ek listajat
     *
     * @return learnedAgents
     */

    public ArrayList<Agent> getLearnedAgents() {
        return learnedAgents;
    }
    /**
     * Visszaadja a craftolt agens-ek listajat
     *
     * @return agentStorage
     */
    public ArrayList<Agent> getAgentStorage() {
        return agentStorage;
    }

    public void Move(Tile target) {
        if (moveType.Move(this, target))
            tile = target;
    }

    /**
     * Egy virológustól elvesz egy aminosavat, ha van üres tárhely
     *
     * @param target a célzott virológus
     */
    public void StealAmino(Virologist target) {
        if (usedStorage < maxStorage&&target.moveType instanceof StunVirus) {
            if (target.RemoveAmino()) {
                amino++;
                usedStorage++;
            }
        }
    }

    /**
     * Egy virológustól elvesz egy nukleotidot, ha van üres tárhely
     *
     * @param target a célzott virológus
     */
    public void StealNukleo(Virologist target) {
        if (usedStorage < maxStorage&&target.moveType instanceof StunVirus) {
            if (target.RemoveNukleo()) {
                nukleo++;
                usedStorage++;
            }
        }
    }

    /**
     * Egy virológustól elvesz egy ágenst, ha van üres tárhely
     *
     * @param target a célzott virológus
     * @param what   amit el akar lopni
     */
    public void StealAgent(Virologist target, Agent what) {
        if (usedStorage < maxStorage&&target.moveType instanceof StunVirus) {
            if (target.RemoveFromAgentStorage(what)) {
                this.AddToAgentStorage(what);
            }
        }
    }

    /**
     * Egy virológustól elvesz egy felszerelést, ha van üres tárhely
     *
     * @param target a célzott virológus
     * @param what   amit el akar lopni
     */
    public void StealEquipment(Virologist target, Equipment what) {
        if(target.moveType instanceof StunVirus) {
            if (usedStorage < maxStorage) {
                if (target.RemoveEquipment(what)) {
                    this.AddEquipment(what);
                }
            }
        }
    }

    /**
     * Rákeni a megadott virológusra a megadott Agent-et, ha megegyezik a tile-uk
     *
     * @param other a célzott virológus
     * @param used  a velhasznált ágens
     */
    public void Attack(Virologist other, Agent used) {
        Tile t = other.GetTile();
        if (t != tile) {
            return;
        }
        used.Effect(this, other);
    }

    /**
     * Rákeni a megadott virológusra a megadott Agent-et, ha megegyezik a tile-uk
     *
     * @param other a célzott virológus
     * @param used  a velhasznált ágens
     */
    public void AttackEquipment(Virologist other, Equipment used) {
        Tile t = other.GetTile();
        if (t != tile) {
            return;
        }
        used.Attack(this, other);
    }

    /**
     * Védekezik a megadott Agent ellen. Meghívja az equipment és az affectedBy összes tagján a Defend függvényt
     *
     * @param target aki ellen védekezik a virológus
     * @param used   az ágens ami ellen védekezik
     */
    public boolean Defend(Virologist target, Agent used) {

        boolean success = false;

        for (Equipment e : equipments) {
            success = e.Defend(target, used);
            if (success) {
                break;
            }
        }
        for (Agent e : affectedBy) {
            success = e.Defend(used);
            if (success) {
                break;
            }
        }

        return success;
    }


    /**
     * Visszaadja hogy sikeres volt a a MedveVírus elleni védekezés
     *
     * @return boolean
     */
    public boolean Bear_Defend(BearVirus bearVirus) {
        for (Equipment e : equipments) {
            if (e instanceof Cloak && e.Defend(this, bearVirus))
                return true;
        }
        for (Agent e : affectedBy) {
            if (e.Defend(bearVirus))
                return true;
        }
        return false;
    }

    /**
     * A paraméterben átadott Agent-et lemásolva készít egy új ágenst magának, amit aztán elhelyez a tárhelyében.
     *
     * @param agent az elkészítendő ágens
     */
    public boolean Craft(Agent agent) {
        for (Agent a : learnedAgents) {
            if (a.getClass() == agent.getClass())
                if (nukleo >= 1 && amino >= 2) {
                    AddToAgentStorage(agent.Clone());
                    Timer.AddSteppable(agent);
                    nukleo--;
                    amino -= 2;
                    return true;
                }
        }
        return false;
    }

    /**
     * A virológus megtanulja a megadott ágenset genetikai kódként, ha még nem ismeri
     *
     * @param learned a megtanulandó ágens
     */
    public void LearnGeneticCode(Agent learned) {
        for (Agent agent : learnedAgents) {
            if (agent.getClass() == learned.getClass())
                return;
        }
        learnedAgents.add(learned);
        gameMap.Win(learnedAgents.size()/2);
    }

    /**
     * Beteszi a megadott Equipment-et a virológus listájába,
     * átállítja, hogy melyik virológusnál van és elkezdi a hatását
     *
     * @param equipment a berakandó equipment
     */
    public boolean AddEquipment(Equipment equipment) {
        if (maxStorage > usedStorage) {
            for (Equipment e : this.equipments) {
                if (e.getClass() == equipment.getClass())
                    return false;
            }
            equipments.add(equipment);
            equipment.SetVirologist(this);
            equipment.ApplyEffect(this);
            this.usedStorage++;
            return true;
        }
        return false;
    }


    /**
     * Véletlenszerűen megnöveli a virológus aminosavainak vagy nukleotidjainak számát
     */
    public boolean GiveMatter(String matter) {
        if (maxStorage > usedStorage) {
            switch (matter) {
                case "amino":
                    this.amino++;
                    this.usedStorage++;
                    return true;
                case "nukleo":
                    this.nukleo++;
                    this.usedStorage++;
                    return true;
                default:
                    System.err.println("Elirtad a GiveMatter parameteret valahol.");
            }
        }
        return false;
    }

    /**
     * Csökkenti 1-el a virológus aminosavainak számát
     */
    public boolean RemoveAmino() {
        if (amino > 0) {
            amino--;
            usedStorage--;
            return true;
        }
        return false;
    }

    /**
     * Kiveszi a virológus listájábol a megadott equipment-et
     *
     * @param what az equipment, amit ki kell venni
     */
    public boolean RemoveEquipment(Equipment what) {
        for (Equipment e : equipments) {
            if (e.getClass() == what.getClass()) {
                what.RemoveEffect();
                equipments.remove(what);
                usedStorage--;
                return true;
            }
        }
        return false;
    }

    /**
     * Csökkenti 1-el a virológus nukleotidjainak számát
     */
    public boolean RemoveNukleo() {
        if (nukleo > 0) {
            nukleo--;
            usedStorage--;
            return true;
        }
        return false;
    }

    /**
     * Hozzáadja a virológus maxStorage-éhez a megadott mennyiséget
     *
     * @param num amennyivel meg kell növelni
     */
    public void AddMaxStorage(long num) {
        maxStorage += num;
    }

    /**
     * Kiveszi a megadott Agent-et az agentStorage-ből
     *
     * @param what az ágens
     */
    public boolean RemoveFromAgentStorage(Agent what) {
        for (Agent a : agentStorage) {
            if (a.getClass() == what.getClass()) {
                agentStorage.remove(what);
                usedStorage--;
                return true;
            }
        }
        return false;
    }

    /**
     * Betesz egy megadott Agent-et az affectedBy-ba
     *
     * @param what az ágens
     */
    public void AddAffectedBy(Agent what) {
        for (Agent a : affectedBy) {
            if (a.getClass() == what.getClass()) {
                affectedBy.remove(a);
                affectedBy.add(what);
                what.SetVirologist(this);
                return;
            }
        }
        what.SetVirologist(this);
        affectedBy.add(what);
    }

    /**
     * Kiveszi a megadott Agent-et az affectedBy-ból
     *
     * @return visszaadja az effected by listát
     */

    public ArrayList<Agent> getAffectedBy() {
        return affectedBy;
    }

    /**
     * Kiveszi a megadott Agent-et az affectedBy-ból
     *
     * @param what az ágens
     */
    public void RemoveAffectedBy(Agent what) {
        affectedBy.remove(what);
    }

    /**
     * Betesz egy megadott Agent-et az agentStorage-be
     *
     * @param what az ágens
     */
    public void AddToAgentStorage(Agent what) {
        for (Agent a : agentStorage) {
            if (a.getClass() == what.getClass())
                return;
        }
        agentStorage.add(what);
        what.SetVirologist(this);
        usedStorage++;
    }

    /**
     * Visszaadja a virológus Tile-ját
     *
     * @return A Tile
     */
    public Tile GetTile() {
        return tile;
    }

    /**
     * átállítja a Virológus moveType-ját a megadott értékre
     *
     * @param type az új mozgástípus
     */
    public void SetMoveType(IMove type) {
        moveType = type;
    }

    public IMove GetMoveType(){return moveType;}

    /**
     * Visszaadja, hogy ven-e ilyen ágens az agentSorage-ban
     *
     * @return Benn van-e
     */
    public boolean hascraftedAgent(Agent what) {
        for (Agent a : agentStorage) {
            if (a.getClass() == what.getClass())
                return true;
        }
        return false;
    }

    /**
     * Megnöveli eggyel a virológus aminósavainak számát
     */
    public void AddAmino() {
        if (maxStorage > usedStorage) {
            amino++;
            usedStorage++;
        }
    }

    /**
     * Megnöveli eggyel a virológus nukleotidjainak számát
     */
    public void AddNukleo() {
        if (maxStorage > usedStorage) {
            nukleo++;
            usedStorage++;
        }
    }

    public void Step() {

        for (Agent elem : affectedBy) {
            elem.Step();
        }
    }

    /**
     * beállítja a virológus Tile-ját a megadott Tile-ra
     *
     * @param tile az új Tile
     */
    public void SetTile(Tile tile) {
        this.tile = tile;
    }

    /**
     * Beteszi a megadott Equipment-et a virológus listájába,
     * átállítja, hogy melyik virológusnál van és elkezdi a hatását
     *
     * @param equipment a berakandó equipment
     */
    public void AddToEquipments(Equipment equipment) {//ez az AddEquipment
        equipments.add(equipment);
        equipment.SetVirologist(this);
        equipment.ApplyEffect(this);
    }

    /**
     * Kitörli a Virológus összes genetikai kódját
     */
    public void RemoveAllLearnedGenes() {
        learnedAgents.clear();
    }

    /**
     * Átállítja a virológus maxStorage-ét a megadott számra
     *
     * @param num az új szám
     */
    public void setMaxStorage(int num) {
        maxStorage = num;
    }

    /**
     * Átállítja a virológus map-jét a megadott map-re
     *
     * @param gameMap az új map
     */
    public void SetMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }



    /**
     * Stringként kiirja a Virlógus összes értékét
     *
     * @return String
     */
    @Override
    public String ToOutput(Scene scene) {
        String finalString = "Virologist " + scene.keyFromObject(this) + " {\n" +
                "\tmoveType: " + moveType.getClass().toString().substring(moveType.getClass().toString().indexOf(" ") + 1) + "\n" +
                "\ttile: " + scene.keyFromObject(this.tile) + "\n" +
                "\tmaxStorage: " + String.valueOf(maxStorage) + "\n" +
                "\tusedStorage: " + String.valueOf(usedStorage) + "\n" +
                "\tamino: " + String.valueOf(amino) + "\n" +
                "\tnukleo: " + String.valueOf(nukleo) + "\n" +
                "\tgameMap: " + scene.keyFromObject(gameMap) + "\n" +
                "\tlearnedAgents: ";
        for (Agent elem : learnedAgents) {
            finalString += scene.keyFromObject(elem) + " ";
        }
        finalString += "\n\tequipments: ";
        for (Equipment elem : equipments) {
            finalString += scene.keyFromObject(elem) + " ";
        }
        finalString += "\n\tagentStorage: ";
        for (Agent elem : agentStorage) {
            finalString += scene.keyFromObject(elem) + " ";
        }
        finalString += "\n\taffectedBy: ";
        for (Agent elem : affectedBy) {
            finalString += scene.keyFromObject(elem) + " ";
        }
        finalString += "\n}";

        return finalString;
    }

}

package Tester;

import Agents.*;
import Equipments.Axe;
import Equipments.Cloak;
import Equipments.Equipment;
import GameControl.GameMap;
import Interfaces.IMove;
import Interfaces.ToString;
import Players.Virologist;
import Tiles.Bunker;
import Tiles.Laboratory;
import Tiles.Storage;
import Tiles.Tile;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyException;
import java.util.Iterator;
import java.util.List;

public class Tester {
    private Scene scene = new Scene();
    private String testName = null;
    private String _output = "";

    public static List<String> ReadFile(String fileName) throws IOException {
        List<String> lines;
        lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
        return lines;
    }

    public void RunCommand(String command) {
        command = command.strip();
        String[] words = command.split(" ");
        for (int i = 0; i < words.length; i++)
            words[i] = words[i].strip();

        switch (words[0]) {
            case "create":
                if (!(words.length == 3)) {
                    this.println("Error: Watch out for parameters!");
                    return;
                }
                if (words[1].equals("Agent") || words[1].equals("Equipment")) {
                    this.println("Error: You can't make an instance of this class.");
                    return;
                }
                try {
                    Object newObject = Class.forName(words[1]).getConstructor().newInstance();
                    scene.addObject(words[2], newObject);
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    this.println("Error: There is no class with this name in the project!");
                    return;
                } catch (KeyException e) {
                    this.println("Error: This key is already in use, please choose another one.");
                    return;
                }
                break;
            case "setChance":
                if (!(words.length == 3)) {
                    this.println("Error: " + "Watch out for parameters!");
                    return;
                }
                try {
                    Object object = scene.getObject(words[1]);
                    int percent = Integer.parseInt(words[2]);
                    if (!(0 <= percent && percent <= 100)) {
                        this.println("Error: The third parameter must be between 0 and 100");
                        return;
                    }
                    if (object instanceof Laboratory) {
                        Laboratory l1 = (Laboratory) object;
                        l1.setRandomValue(percent);
                    } else if (object instanceof Cloak) {
                        Cloak c1 = (Cloak) object;
                        c1.setRandomValue(percent);
                    } else {
                        this.println("Error: You can't use this command on this object!");
                        return;
                    }
                    this.println("Chance of " + words[1] + " has been set to: " + words[2]);
                } catch (KeyException e) {
                    this.println("Error: " + "The given key was not found in the scene");
                    return;
                }
                break;
            case "move":
                if (!(words.length == 3)) {
                    this.println("Error: Watch out for parameters!");
                    return;
                }
                String virologistName = words[1];
                String tileName = words[2];
                if (!scene.keyInScene(virologistName) || !scene.keyInScene(tileName)) {
                    this.println("Error: The given key was not found in the scene");
                    return;
                }
                Virologist virologist;
                try {
                    virologist = (Virologist) scene.getObject(virologistName);
                } catch (ClassCastException e) {
                    this.println("Error: Object given as virologist is not of type \"Virologist\"!");
                    return;
                } catch (KeyException e) {
                    this.println("Error: The given key was not found in the scene");
                    return;
                }
                Tile tile;
                try {
                    tile = (Tile) scene.getObject(tileName);
                } catch (ClassCastException e) {
                    this.println("Error: Object given as tile is not of type \"Tile\"!");
                    return;
                } catch (KeyException e) {
                    this.println("Error: The given key was not found in the scene");
                    return;
                }
                virologist.Move(tile);
                this.println("Virologist " + virologistName + " moved to Tile: " + tileName);
                break;
            case "attack":
                if (!(words.length == 4)) {
                    this.println("Error: Watch out for parameters!");
                    return;
                }
                String userName = words[1];
                String type = words[2];
                String name = words[3];
                try {
                    Virologist attackerVirologist = (Virologist) scene.getObject(userName);
                    Virologist attackedVirologist = (Virologist) scene.getObject(name);
                    Object attackWith = scene.getObject(type);

                    if (attackWith instanceof Agent) {
                        attackerVirologist.Attack(attackedVirologist, (Agent) attackWith);
                    }
                    else if (attackWith instanceof Axe) {
                        ((Axe) attackWith).Attack(attackerVirologist, attackedVirologist);
                    }
                    else {
                        this.println("Error: You can't attack with the item you supplied as weapon.");
                        return;
                    }
                    this.println(userName + " has successfully attacked " + name + " with " + type);
                } catch (ClassCastException e) {
                    this.println("Error: The objects given as arguments did not match the class types!");
                    return;
                } catch (KeyException e) {
                    this.println("Error: The given key was not found in the scene");
                    return;
                }
                break;
            case "steal":
                if (!(words.length == 4)) {
                    this.println("Error: Watch out for parameters!");
                    return;
                }
                String stealerName = words[1];
                String itemName = words[2];
                String stealFrom = words[3];
                try {
                    Virologist stealerVirologist = (Virologist) scene.getObject(stealerName);
                    Virologist stealFromVirologist = (Virologist) scene.getObject(stealFrom);
                    if (itemName.equals("Amino")) {
                        stealerVirologist.StealAmino(stealFromVirologist);
                        this.println(stealerName + " has successfully stolen " + itemName + " from " + stealFrom);
                        return;
                    } else if (itemName.equals("Nukleo")) {
                        stealerVirologist.StealNukleo(stealFromVirologist);
                        this.println(stealerName + " has successfully stolen " + itemName + " from " + stealFrom);
                        return;
                    }
                    Object item = scene.getObject(itemName);
                    if (item instanceof Agent) {
                        stealerVirologist.StealAgent(stealFromVirologist, (Agent) item);
                        this.println(stealerName + " has successfully stolen " + itemName + " from " + stealFrom);
                        return;
                    } else if (item instanceof Equipment) {
                        stealerVirologist.StealEquipment(stealFromVirologist, (Equipment) item);
                        this.println(stealerName + " has successfully stolen " + itemName + " from " + stealFrom);
                        return;
                    } else {
                        this.println("Error: The type of the stolen object does not match the specification of stealing!");
                        this.println(stealerName + " has successfully stolen " + itemName + " from " + stealFrom);
                        return;
                    }
                } catch (KeyException e) {
                    this.println("Error: The given key was not found in the scene!");
                    return;
                } catch (ClassCastException e) {
                    this.println("Error: The given parameters did not match the required type!");
                    return;
                }
            case "craft":
                if (!(words.length == 4)) {
                    this.println("Error: Watch out for parameters!");
                    return;
                }
                String crafterName = words[1];
                String itemType = words[2];
                String agentName = words[3];
                try {
                    Agent newObject = (Agent) Class.forName(itemType).getConstructor().newInstance();
                    Virologist virologist1 = (Virologist) scene.getObject(crafterName);

                    if (virologist1.Craft(newObject)) {
                        scene.addObject(agentName, newObject);
                        this.println(crafterName + " successfully crafted " + itemType);
                    } else {
                        this.println("The virologist could not craft the item, because it did not have enough material or did not learn the spell");
                    }
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {

                    this.println("Error: There is no class with this name in the project!");
                    return;
                } catch (ClassCastException e) {
                    this.println("Error: The given key was not found in the scene!");
                    return;
                } catch (KeyException e) {
                    this.println("Error: The given key was not found in the scene!");
                    return;
                }
                break;
            case "add":
                if (!(words.length == 3)) {
                    this.println("Error: Watch out for parameters!");
                    return;
                }
                String nameToAddItemTo = words[1];
                String nameOfItem = words[2];
                try {
                    Object reciever = scene.getObject(nameToAddItemTo);

                    if (scene.getObject(nameToAddItemTo) instanceof Storage && nameOfItem.equals("Amino")) {
                        Storage storage = (Storage) reciever;
                        storage.AddAmino(1);
                        return;
                    } else if (scene.getObject(nameToAddItemTo) instanceof Storage && nameOfItem.equals("Nukleo")) {
                        Storage storage = (Storage) reciever;
                        storage.AddNukleo(1);
                        return;
                    } else if (scene.getObject(nameToAddItemTo) instanceof Virologist && nameOfItem.equals("Amino")) {
                        Virologist v1 = (Virologist) reciever;
                        v1.AddAmino();
                        return;
                    } else if (scene.getObject(nameToAddItemTo) instanceof Virologist && nameOfItem.equals("Nukleo")) {
                        Virologist v1 = (Virologist) reciever;
                        v1.AddNukleo();
                        return;
                    }
                    Object item = scene.getObject(nameOfItem);
                    if (reciever instanceof Virologist && item instanceof Equipment) {
                        Virologist v1 = (Virologist) reciever;
                        Equipment equipment = (Equipment) item;
                        v1.AddEquipment(equipment);
                        equipment.SetVirologist(v1);
                    }
                    else if (reciever instanceof Equipment && item instanceof Virologist) {
                        Equipment equipment = (Equipment) reciever;
                        Virologist v1 = (Virologist) item;
                        v1.AddEquipment(equipment);
                        equipment.SetVirologist(v1);
                    }
                    else if (reciever instanceof Virologist && item instanceof Agent) {
                        Virologist v1 = (Virologist) reciever;
                        Agent agent = (Agent) item;
                        v1.LearnGeneticCode(agent);
                        v1.AddToAgentStorage(agent);
                    } else if (reciever instanceof Tile && item instanceof Tile) {
                        Tile t1 = (Tile) reciever;
                        Tile t2 = (Tile) item;
                        t1.AddNeighbour(t2);
                        t2.AddNeighbour(t1);
                    } else if (reciever instanceof Tile && item instanceof Virologist) {
                        Tile t1 = (Tile) reciever;
                        Virologist v1 = (Virologist) item;
                        t1.AddVirologist(v1);
                        v1.SetTile(t1);
                    } else if (reciever instanceof GameMap && item instanceof Virologist) {
                        GameMap gameMap = (GameMap) reciever;
                        Virologist v1 = (Virologist) item;
                        gameMap.AddVirologist(v1);
                        v1.SetMap(gameMap);
                    } else if (reciever instanceof Virologist && item instanceof GameMap) {
                        GameMap gameMap = (GameMap) item;
                        Virologist v1 = (Virologist) reciever;
                        gameMap.AddVirologist(v1);
                        v1.SetMap(gameMap);
                    }
                    else if (reciever instanceof GameMap && item instanceof Tile) {
                        GameMap gameMap = (GameMap) reciever;
                        Tile t1 = (Tile) item;
                        gameMap.AddTile(t1);
                    } else if (reciever instanceof Laboratory && item instanceof Agent) {
                        Laboratory laboratory = (Laboratory) reciever;
                        Agent agent = (Agent) item;
                        if (agent instanceof DanceVirus || agent instanceof ForgetVirus || agent instanceof StunVirus) {
                            laboratory.AddVirus(agent);
                        }
                        if (agent instanceof DanceVaccine || agent instanceof ForgetVaccine || agent instanceof StunVaccine) {
                            laboratory.AddVaccine(agent);
                        }
                    } else if (reciever instanceof Bunker && item instanceof Equipment) {
                        Bunker bunker = (Bunker) reciever;
                        Equipment equipment = (Equipment) item;
                        bunker.AddEquipment(equipment);
                    } else {
                        this.println("Error: You can't add these items together!");
                        return;
                    }
                } catch (KeyException e) {
                    this.println("Error: The given key was not found in the scene!");
                    return;
                }
                break;
            case "changeMoveType":
                if (!(words.length == 3)) {
                    this.println("Error: " + "Watch out for parameters!");
                    return;
                }
                try {
                    Object reciever = scene.getObject(words[1]);
                    Object item = scene.getObject(words[2]);
                    if (reciever instanceof Virologist && item instanceof IMove) {
                        Virologist v1 = (Virologist) reciever;
                        IMove move = (IMove) item;
                        v1.SetMoveType(move);
                    } else {
                        this.println("Error: You can't use this function on these keys!");
                    }
                    this.println("Virologist " + words[1] + " has received the following moveType: " + words[2]);
                } catch (KeyException e) {
                    this.println("Error: " + "The given key was not found in the scene!");
                    return;
                }
                break;
            // Loadscene függvény hivása esetén az előző scene elvész
            case "loadScene":
                if (!(words.length == 2)) {
                    this.println("Error: Watch out for parameters!");
                    return;
                }
                try {
                    List<String> lines = ReadFile("Projlab/Tester/BaseScenes/"+words[1] + ".txt");

                    Iterator<String> itr = lines.iterator();
                    Tester temp = new Tester();
                    while (itr.hasNext())
                        temp.RunCommand(itr.next());
                    this.scene = temp.scene;
                    this.println("Tester successfully loaded: " + words[1]);
                }
                catch (IOException e) {
                    this.println("Error: This file does not exist!");
                    return;
                }
                break;
            case "state":
                if (!(words.length == 2)) {
                    this.println("Error: Watch out for parameters!");
                    return;
                }
                String objectName = words[1];
                try {
                    Object object = scene.getObject(objectName);
                    if (object instanceof ToString) {
                        this.println(((ToString) object).ToOutput(this.scene));
                    }
                    else {
                        this.println("Error: The ToOutput function was not implemented for this class!");
                    }
                } catch (KeyException e) {
                    this.println("Error: The given key was not found in the scene!");
                    return;
                }
                break;
            case "runTest":
                if (!(words.length==2)) {
                    this.println("Error: Watch out for parameters!");
                    return;
                }
                try {
                    List<String> lines = ReadFile("Projlab/Tester/Tests/"+words[1] + ".txt");
                    this.testName = words[1];
                    for (String next : lines) {
                        System.out.println(">>> " + next);
                        this.RunCommand(next);
                    }
                }
                catch (IOException e) {
                    this.println("Error: This file does not exist!");
                    this.testName = null;
                    return;
                }
                break;
            case "help":
                if (!(words.length == 1)) {
                    this.println("Error: " + "This command takes no parameters!");
                    return;
                }
                System.out.println("-------------------------------------------------------------");
                System.out.println("| The commands you can use are the following:               |");
                System.out.println("| create <Type> <Name>                                      |");
                System.out.println("| move <VirologistName> <TileName>                          |");
                System.out.println("| setChance <ItemName> <Percent (0-100)>                    |");
                System.out.println("| attack <AttackerVirologist> <Weapon> <AttackedVirologist> |");
                System.out.println("| steal <StealerName> <ItemName> <StaelFrom>                |");
                System.out.println("| craft <CrafterName> <AgentType> <AgentName>               |");
                System.out.println("| add <NameToAddItemTo> <NameOfItemToAdd>                   |");
                System.out.println("| loadScene <SceneName>                                     |");
                System.out.println("| changeMoveType <Virologist> <MoveType>                    |");
                System.out.println("| state <Name>                                              |");
                System.out.println("| runTest <TestName>                                        |");
                System.out.println("-------------------------------------------------------------");

                break;
            case "exit":
                System.out.println("Exiting the tester...");
                this.saveTestResults();
                System.exit(0);
            default:
                this.println("Error: Command not found, to see the list of commands type \"help\"!");
        }
    }

    private void println(String toPrint) {
        System.out.println(toPrint);
        this._output += toPrint + "\n";
    }

    private void saveTestResults() {
        if (this.testName == null)
            this.testName = "UnnamedTest";
        FileWriter writer;
        try {
            writer = new FileWriter("Projlab/Tester/Outputs/" + this.testName + ".txt");
            writer.write(_output);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
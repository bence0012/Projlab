package Moves;

import Interfaces.IMove;
import Players.Virologist;
import Tester.Scene;
import Tiles.Tile;

public class DeadMove implements IMove{
    public boolean Move(Virologist virologist, Tile btile){
            return false;
    }

    @Override
    public String toString() {
        return "DeadMove";
    }

    @Override
    public String ToOutput(Scene scene) {
        return "DeadMove " + scene.keyFromObject(this) + "{\n" +
                "\tThis class has no parameters!\n" +
                "}";
    }
}


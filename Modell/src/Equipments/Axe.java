package Equipments;

import Agents.Agent;
import Moves.DeadMove;
import Players.Virologist;
import Tester.Scene;

import java.util.ArrayList;

/**
 * Olyan Védőfelszerelés, mellyel egy medvevírussal fertőződött Virológust lehet hatástalanítani
 */
public class Axe extends Equipment {
     // Eltárolja, hogy éppen melyik Virológusnál van
    protected Virologist virologist;

    // Eltárolja, hogy ki van e csorbulva
    private Boolean usable;


    public Axe() {
        this.virologist = null;
        usable=true;
    }

    /**
     *Ha a Viselő Virológust támadás éri, akkor ez a Védőfelszerelés nem véd semmilyen Vírus ellen
     *
     *
     * @param other - melyik virológus támadta meg a felszerelést viselő virológust
     * @param a - melyik ágens ellen kell védekezni
     * @return boolean - véletlenszerűen sorsolva
     */
    @Override
    public boolean Defend(Virologist other, Agent a){
        return false;
    }

    /**
     * Amint a felszerelés valakinél van, felszereléstől függően,
     * lehet annak állandó hatása, amit kifejt, jelen esetben semmit
     *
     * @param v akire a hatás kerül
     */

    public void ApplyEffect(Virologist v){
    }
    /**
     * Ha a Virológus elveszti az adott felszerelést akkor
     * lekerül róla a felszerelés nyújotta állandó hatás, ennél a felszerelésnél nincsen
     */
    public void RemoveEffect(){
    }
    /**
     * Beállítja, hogy kinél van a felszerelés
     *
     * @param v akinél a Védőfelszerelés lesz
     */
    public void SetVirologist(Virologist v){
        virologist=v;

    }
    /**
     * Megtámad egy paraméterben megadott virológust, ha sikeres volt a támadás, akkor
     * a másik Virológus meghal,
     *
     * @param other akit megtámad
     */
    @Override
    public void Attack(Virologist player, Virologist other) {
        if(usable){
            usable=false;
            other.SetMoveType(new DeadMove());
            ArrayList<Agent> toRemove=new ArrayList<>();


            //  other.RemoveAffectedBy(agent);
            toRemove.addAll(other.getAffectedBy());
            other.getAffectedBy().removeAll(toRemove);
            //kitörli a virológust a pályáról
            other.GetTile().RemoveVirologist(other);
        }
    }
    @Override
    public String ToOutput(Scene scene) {
        // Eloszor meg kell keresni a valtozok neveit a sceneben:
        String objectName = scene.keyFromObject(this);
        // Meg kell keresni a neveket az osszes attributumhoz is, szepen vegig kell menni minden attributumon amit fent
        // deklaraltunk:
        String virologistName = scene.keyFromObject(this.virologist);
        // Ha egy attributum nem osztály, hanem int, bool stb, akkor csak darabszamot, true / false-ot kell kiirni
        String usable = String.valueOf(this.usable);
        // Majd végül össze kell rakni a kimenetet:
        return "Axe " + objectName + " {\n" +
                "\tvirologistName: " + virologistName + "\n" +
                "\tusable: " + usable + "\n" +
                "}";
    }

}


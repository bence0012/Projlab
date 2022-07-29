package Agents;

import Interfaces.ToString;
import Players.Virologist;

public class BearVaccine extends Agent implements ToString {
    @Override
    public boolean Defend(Agent agent){
        return agent instanceof BearVirus;
    }
    /**
     * Lemásolja az ágenst
     *
     * @return a lemásolt ágens
     */
    public Agent Clone() {
        return new BearVaccine();
    }
    /**
     * Ez a függvény felelős azért, hogy a vírus kifejtse hatását a megkent virológuson.
     *
     * @param virologist a Virológus aki a kenést kezdeményezte
     * @param other  a Virológus akire kenni szeretnénk
     */
    public void Effect(Virologist virologist, Virologist other) {
        // Aktiv�lja a BearVaccine
        this.ResetTimer();
        other.AddAffectedBy(this);
        virologist.RemoveFromAgentStorage(this);
    }
    /**
     * Megállítja az Ágens Hatását
     */
    public void RemoveEffect() {
        this.virologist.RemoveAffectedBy(this);
        this.virologist.RemoveFromAgentStorage(this);
    }
}

package Agents;

import Interfaces.ToString;
import Players.Virologist;

public class DanceVaccine extends Agent implements ToString {
    @Override
    public boolean Defend(Agent agent) {
        return agent.getClass() == DanceVirus.class;
    }

    /**
     * Lemásolja az ágenst
     *
     * @return a lemásolt ágens
     */
    @Override
    public Agent Clone() {
        return new DanceVaccine();
    }

    /**
     * Ez a függvény felelős azért, hogy a vírus kifejtse hatását a megkent virológuson.
     *
     * @param player a Virológus aki a kenést kezdeményezte
     * @param other  a Virológus akire kenni szeretnénk
     */
    @Override
    public void Effect(Virologist player, Virologist other) {
        this.ResetTimer();
        other.AddAffectedBy(this);
        player.RemoveFromAgentStorage(this);
    }

    /**
     * Megállítja az Ágens Hatását
     */
    public void RemoveEffect() {
        // Deaktiv�lja a DanceVakcin�t
        this.virologist.RemoveAffectedBy(this);
        this.virologist.RemoveFromAgentStorage(this);
    }
}
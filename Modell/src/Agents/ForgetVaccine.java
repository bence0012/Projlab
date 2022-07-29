package Agents;

import Interfaces.ToString;
import Players.Virologist;

public class ForgetVaccine extends Agent implements ToString {
    /**
     * Védekezik az ágens ellen
     *
     * @param agent az ágens ami ellen védekezik
     * @return ha sikeres true
     */
    public boolean Defend(Agent agent) {
        return agent.getClass() == ForgetVirus.class;
    }

    /**
     * Lemásolja az ágenst
     *
     * @return a lemásolt ágens
     */
    @Override
    public Agent Clone() {
        return new ForgetVaccine();
    }

    /**
     * Ez a függvény felelős azért, hogy a vírus kifejtse hatását a megkent virológuson.
     *
     * @param virologist a Virológus aki a kenést kezdeményezte
     * @param other      a Virológus akire kenni szeretnénk
     */
    public void Effect(Virologist virologist, Virologist other) {
        this.ResetTimer();
        other.AddAffectedBy(this);
        virologist.RemoveFromAgentStorage(this);
    }

    /**
     * Megállítja az Ágens Hatását
     */
    public void RemoveEffect() {
        this.virologist.RemoveFromAgentStorage(this);
    }
}


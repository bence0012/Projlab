package Agents;

import Interfaces.ToString;
import Players.Virologist;

/**
 * Egy különleges Agent, megakadájozza a StunVirus hatását
 */
public class StunVaccine extends Agent implements ToString {
    /**
     * Az Ágens kifelyti a hátását
     *
     * @param player a Virológus aki a kenést kezdeményezte
     * @param other  a Virológus akire kenni szeretnénk
     */
    public void Effect(Virologist player, Virologist other) {
        boolean defended = other.Defend(player, this);
        if (!defended) {
            this.ResetTimer();
            other.AddAffectedBy(this);
            this.virologist = other;
            player.RemoveFromAgentStorage(this);
        }
    }

    public void RemoveEffect() {

        try {
            this.virologist.RemoveAffectedBy(this);

        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Védekezik az ágens ellen
     *
     * @param agent az ágens ami ellen védekezik
     * @return sikerült-e megvédeni a virológust
     */
    public boolean Defend(Agent agent) {

        return agent.getClass() == StunVirus.class;
    }

    /**
     * Lemásolja az ágenst
     *
     * @return a lemásolt ágens
     */
    public Agent Clone() {
        return new StunVaccine();
    }
}


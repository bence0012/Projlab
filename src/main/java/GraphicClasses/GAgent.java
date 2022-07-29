package GraphicClasses;

/**
 * Az agent-ok megjelenitesehez hasznalt grafikus osztaly
 */
public class GAgent extends Drawable{
    /**
     * @param x A kep kezdo x koordinataja
     * @param y A kep kezdo y koordinataja
     */
    public GAgent(int x, int y) {
        super(x, y, 0.05, "Agent.png");
    }
}

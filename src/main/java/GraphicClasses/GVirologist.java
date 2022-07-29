package GraphicClasses;

import Players.Virologist;

/**
 * Ez az osztaly segit abban, hogy ki tudjuk rajzolni a virologust
 */
public class GVirologist extends Drawable {
    private final Virologist virologist;

    public Virologist getVirologist() {
        return virologist;
    }

    /**
     * @param x a virologus kepenek kezdo x koordinataja
     * @param y a virologus kepenek kezdo y koordinataja
     * @param virologist a virologus amit a rajzolo osztaly a modellbol magaba zar
     */
    public GVirologist(int x, int y, Virologist virologist) {
        super(x, y, 0.05, virologist.GetName()+".jpg");
        this.virologist = virologist;
    }
}

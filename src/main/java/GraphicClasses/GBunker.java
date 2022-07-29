package GraphicClasses;

import Tiles.Bunker;

/**
 * Ez az osztaly segit a bunker modellbeli osztaly megjeleniteseben
 */
public class GBunker extends GTile{
    private final GEquipment gEquipment;

    /**
     * @param x a kezdo x koordinata
     * @param y a kezdo y koordinata
     * @param bunker a bunker objektum amit tarol majd az osztaly
     */
    public GBunker(int x, int y, Bunker bunker) {
        super(x, y, bunker, "Bunker.jpg");
        this.tile = bunker;
        this.gEquipment = new GEquipment(this.x, this.y);
   }

    /**
     * Ez a fuggveny rajzolja ki az osztalyt
     */
    public void draw() {
        super.draw();
        if (((Bunker)this.tile).HasEquipment()) {
            gEquipment.Move(this.x, this.y);
            gEquipment.draw();
            this.group.getChildren().add(this.gEquipment.imageView);
        }
    }
}

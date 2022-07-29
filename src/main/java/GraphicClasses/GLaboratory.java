package GraphicClasses;

import Tiles.Laboratory;

/**
 * Ez az osztaly segit a modellbeli laboratoriumok megjeleniteseben
 */
public class GLaboratory extends GTile{
    private final GAgent gAgent;

    /**
     * @param x a kezdo x koordinata
     * @param y a kezdo y koordinata
     * @param tile a tile amit az osztaly magabafoglal
     */
    public GLaboratory(int x, int y, Laboratory tile) {
        super(x, y, tile, "Laboratory.jpg");
        this.tile = tile;
        this.gAgent = new GAgent(this.x, this.y);
    }

    public void draw() {
        super.draw();
        gAgent.Move(this.x, this.y);
        gAgent.draw();
        this.group.getChildren().add(this.gAgent.imageView);
    }
}


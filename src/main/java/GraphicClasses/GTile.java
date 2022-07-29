package GraphicClasses;

import Tiles.Tile;

/**
 * Ez az osztaly segit a modellbeli sima tile-okk megjeleniteseben
 */
public class GTile extends Drawable {
    protected Tile tile;

    public Tile getTile() {
        return tile;
    }

    /**
     * Ez a konstruktor azert kulonleges, mert ennek a segitsegevel lehet elkesziteni a
     * modellben lathato tile-tobbi tile tipus kozti oroklodest
     * @param x a kep kezdo x koordinataja
     * @param y a kep kezdo y koordinataja
     * @param tile a tile, amit az objektum tarol majd
     * @param imageName a betoltendo kep neve
     */
    public GTile(int x, int y, Tile tile, String imageName) {
        super(x, y, 0.2, imageName);
        this.tile = tile;
    }
}

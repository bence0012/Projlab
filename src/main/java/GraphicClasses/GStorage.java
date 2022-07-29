package GraphicClasses;

import Tiles.Storage;

/**
 * Ez az osztaly segit a modellbeli storage mezok megjeleniteseben
 */
public class GStorage extends GTile {
    private final GMatter gMatter;

    /**
     * @param x a kezdo x koorinata
     * @param y a kezdo y koordinata
     * @param storage a storage amit az osztaly magabazar
     */
    public GStorage(int x, int y, Storage storage) {
        super(x, y, storage, "Storage.jpg");
        this.tile = storage;
        this.gMatter = new GMatter(this.x, this.y);
    }

    public void draw() {
        super.draw();
        if (((Storage)this.tile).HasItem()) {
            gMatter.Move(this.x, this.y);
            gMatter.draw();
            this.group.getChildren().add(this.gMatter.imageView);
        }
    }
}

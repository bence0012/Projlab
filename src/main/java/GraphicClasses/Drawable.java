package GraphicClasses;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Objects;


/**
 * Az ososztaly ami interfacekent mukodik a modell osztalyai es a javafx kozt
 */
public abstract class Drawable {
    protected int x;
    protected int y;
    protected int midX;
    protected int midY;
    protected double sizeMultiplier;
    protected static int staticScreenWidth;
    protected static int staticScreenHeight;
    protected String fileName;
    protected ImageView imageView;
    protected Group group;

    /**
     * @param screenWidth a kepernyo szelessege
     * @param screenHeight a kepernyo magassaga
     */
    public static void SetScreenDimensions(int screenWidth, int screenHeight) {
        staticScreenWidth = screenWidth;
        staticScreenHeight = screenHeight;
    }

    /**
     * @return Visszaadja a group-ot
     */
    public Group GetGroup() {
        return group;
    }

    /**
     * @return visszaadja a kepet tartalmazo osztalyt
     */
    public ImageView GetImageView() {
        return imageView;
    }

    /**
     * @return Visszaadja az osztaly altal feljegyzett kepernyoszelesseget
     */
    public int GetStaticScreenWidth() {
        return staticScreenWidth;
    }

    /**
     * @return Visszaadja az osztaly alatal tarolt kepernyo magassagot
     */
    public int GetStaticScreenHeight() {
        return staticScreenHeight;
    }

    /**
     * Elmozgatja a kepet az adott x,y koordinata
     * @param x az uj x koordinata
     * @param y az uj y koordinata
     */
    public void Move(int x, int y) {
        this.x = x;
        this.y = y;
        this.midX = (int) (x + imageView.getFitWidth()/2)-4;
        this.midY = (int) (y + ((300*imageView.getFitWidth())/550)/2)-4;
    }

    /**
     * @param x a kezdo x koordinata
     * @param y a kezdo y koordinata
     * @param sizeMultiplier Ennyivel kell megszorozni a kepet hogy jo legyen a merete
     * @param fileName A file ahol a kepet megtalaljuk amit az osztalynak be kell tolteni
     */
    public Drawable(int x, int y, double sizeMultiplier, String fileName) {
        this.x = x;
        this.y = y;
        this.fileName = fileName;
        this.sizeMultiplier = sizeMultiplier;
        group = new Group();
        javafx.scene.image.Image picture = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream(fileName)));
        this.imageView = new ImageView(picture);
        group.getChildren().add(this.imageView);
        this.midX = (int) (x + imageView.getFitWidth()/2)-4;
        this.midY = (int) (y + ((300*imageView.getFitWidth())/550)/2)-4;
    }

    /**
     * Ez a fuggveny vegzi el a vegleges rajzolast es az ImageView elkesziteset
     */
    public void draw() {
        group.getChildren().clear();
        this.imageView.setX(this.x);
        this.imageView.setY(this.y);
        this.imageView.setSmooth(true);
        this.imageView.setPreserveRatio(true);
        this.imageView.setFitWidth(staticScreenWidth*this.sizeMultiplier);
        group.getChildren().add(this.imageView);
    }
}

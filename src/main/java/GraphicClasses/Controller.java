package GraphicClasses;

import Agents.BearVirus;
import Agents.DanceVirus;
import Agents.StunVirus;
import Classes.GraphicsMain;
import GameControl.GameController;
import GameControl.GameMap;
import Moves.NormalMove;
import Players.Virologist;
import Tiles.Bunker;
import Tiles.Laboratory;
import Tiles.Storage;
import Tiles.Tile;
import javafx.scene.Group;
import javafx.scene.shape.Line;

import java.util.ArrayList;

/**
 * Ez az osztaly felelos a graikus osztalyok vezerleseert, illetve
 * itt kell minden olyan fuggvenyre interface-t irni, ami a modellben benne van
 * es a grafikus dolgok is hasznalni akarjak
 */
public class Controller {
    private static final GameController gameController = new GameController();
    private static final ArrayList<Drawable> drawables = new ArrayList<>();

    private static Virologist virologist;

    /**
     * Felepiti a grafikus modellt a modell elemeibol
     * Beallitja az osszes grafikus osztaly koordinatajat 0,0 ra
     */
    public static void Init() {
        GameMap map = gameController.getGameMap();
        for (Tile tile : map.getTiles()) {
            if (tile instanceof Laboratory) {
                drawables.add(new GLaboratory(0, 0, (Laboratory) tile));
            } else if (tile instanceof Bunker) {
                drawables.add(new GBunker(0, 0, (Bunker) tile));
            } else if (tile instanceof Storage) {
                drawables.add(new GStorage(0, 0, (Storage) tile));
            } else {
                drawables.add(new GTile(0, 0, tile, "Tile.jpg"));
            }
        }
        for (Virologist virologist : map.getVirologists()) {
            drawables.add(new GVirologist(0, 0, virologist));
        }
        virologist=gameController.GetCurrentPlayer();
    }

    /**
     * Ez a fuggveny azert kell, hogy ha ujrakezdik a jatekot ujra tudjuk inicializalni
     * a szukseges fuggvenyeket.
     * A jatek vegenel ujra meg kell hivni ezt a fuggvenyt.
     */
    public static void NewDrawables(){
        GameMap map = gameController.getGameMap();
        for (Tile tile : map.getTiles()) {
            if (tile instanceof Laboratory) {
                drawables.add(new GLaboratory(0, 0, (Laboratory) tile));
            } else if (tile instanceof Bunker) {
                drawables.add(new GBunker(0, 0, (Bunker) tile));
            } else if (tile instanceof Storage) {
                drawables.add(new GStorage(0, 0, (Storage) tile));
            } else {
                drawables.add(new GTile(0, 0, tile, "Tile.jpg"));
            }
        }
        for (Virologist virologist : map.getVirologists()) {
            drawables.add(new GVirologist(0, 0, virologist));
        }
    }

    /**
     * Ezt a fuggvenyt kell meghivni, hogy ha ujra szeretnenk rajzolni a palyat illetve
     * a rajta levo virologusokat.
     * @param width a kepernyo szelessege
     * @param height a kepernyo magassaga
     */
    public static void ReDraw(int width, int height) {
        Drawable.SetScreenDimensions(width, height);
        if (width == 0 || height == 0) {
            width = 600;
            height = 400;
        }
        int xCounter = 0;
        int yCounter = 0;
        int xOffset = width/5;
        int yOffset = (int) (height/3+height*0.08);
        for (Drawable drawable : drawables) {
            if (!(drawable instanceof GVirologist)) {
                if (xCounter + xOffset > width) {
                    yCounter += yOffset;
                    xCounter = 0;
                }
                drawable.Move(xCounter, yCounter);
                xCounter += xOffset + width*0.06;

                GTile gTile=(GTile) drawable;
                int virologistsOnTile = gTile.getTile().getVirologists().size();
                for (int i=0;i<virologistsOnTile;i++){
                    GVirologist virologist=FindVirologist(gTile.getTile().getVirologists().get(i));
                    virologist.Move((int) (i * (virologist.GetImageView().getFitWidth()+2) +gTile.x + 4),
                            (int) (gTile.y + height*0.01));
                    virologist.draw();
                }
                drawable.draw();
            }
        }

        DrawLines();
    }

    /**
     * Kirajzolja a mezok kozti vonalakat
     * @return a mezook kozti vonalak
     */
    private static ArrayList<Line> DrawLines() {
        ArrayList<Line> lines = new ArrayList<>();

        for (Drawable drawable : drawables) {
            if (drawable instanceof GTile) {
                ArrayList<Tile> tiles = ((GTile) drawable).getTile().GetNeighbours();
                for (Tile tile : tiles) {
                    Drawable otherTile = FindTile(tile);
                    Line line = new Line();
                    line.setStartX(drawable.midX);
                    line.setStartY(drawable.midY);
                    line.setEndX(otherTile.midX);
                    line.setEndY(otherTile.midY);
                    line.setStrokeWidth(8);
                    lines.add(line);
                }
            }
        }
        return lines;
    }

    /**
     * Ezt a fuggvenyt kell hasznalni ha a grafikus gombokkal a virologust mozgatni szeretnenk.
     * Mivel egy adott kor a jatekos leptetesevel er veget, ezert automatikusan ujrarajzolja a palyat
     * es lepteti a soron kovetkezo jatekosra valt (GameController.currentPlayer)
     * A fuggveny automatikusan a soron kovetkezo virologust lepteti
     *
     * @param tileToMove       a mezo ahova mozogni fog
     */
    public static void GVirologistMove(Tile tileToMove) {
        GVirologist gVirologist = FindVirologist(gameController.GetCurrentPlayer());
        if (gameController.GetCurrentPlayer().GetMoveType() instanceof NormalMove) {
            GTile gTile = FindTile(tileToMove);
            if (gTile == null) {
                System.err.println("Nem talalhato a jatekban egy mezo amire lepni probaltak");
                System.exit(1);
            }


            if (gVirologist == null) {
                System.err.println("Nem talalhato a jatekban egy virologus amit leptetni probaltak");
                System.exit(1);
            }

            //int virologistsOnTile = gTile.getTile().getVirologists().size();
            // gVirologist.x = virologistsOnTile * 150 + 10 + gTile.x;
            //gVirologist.y = gTile.y + 100;
            gVirologist.getVirologist().Move(gTile.getTile());
            if ((gameController.GetCurrentPlayer().getLearnedAgents().size() / 2) == 3) {
                Controller.GameOver();
                GameController.EndGame();
                drawables.clear();
                NewDrawables();
            }
        }
        ReDraw(gVirologist.GetStaticScreenWidth(), gVirologist.GetStaticScreenHeight());

        gameController.NextPlayer();
    }


    /**
     * Megkeresi a grafikus osztalyokban, hogy melyik osztalyban van benne az adott virologus
     * @param virologist a virologus akinek a grafikus osztalyat meg szeretnenk talalni
     * @return a virologus grafikus osztalya
     */
    private static GVirologist FindVirologist(Virologist virologist) {
        for (Drawable drawable : drawables) {
            if (drawable instanceof GVirologist) {
                GVirologist gVirologist = (GVirologist) drawable;
                if (gVirologist.getVirologist() == virologist) {
                    return gVirologist;
                }
            }
        }
        return null;
    }

    /**
     * Egy tile objektumrol megmondja, hogy melyik GTile tartozik hozza
     * @param tile amelyiket meg szeretnenk talalni
     * @return a tile-t tartalmazao GTile objektum
     */
    private static GTile FindTile(Tile tile) {
        for (Drawable drawable : drawables) {
            if (drawable instanceof GStorage ) {
                GStorage gTile = (GStorage) drawable;
                if (gTile.getTile() == tile) {
                    return gTile;
                }
            }
            if (drawable instanceof GBunker ) {
                GBunker gTile = (GBunker) drawable;
                if (gTile.getTile() == tile) {
                    return gTile;
                }
            }
            if (drawable instanceof GLaboratory ) {
                GLaboratory gTile = (GLaboratory) drawable;
                if (gTile.getTile() == tile) {
                    return gTile;
                }
            }
            if (drawable instanceof GTile ) {
                GTile gTile = (GTile) drawable;
                if (gTile.getTile() == tile) {
                    return gTile;
                }
            }

        }
        return null;
    }


    /**
     * Ujrarajzolja az elemeket es beleteszi oket egy group ba
     * @return visszaad egy group-ot amit oda kell majd adni a kirajzolast vegzo csoportnak
     */
    public static Group RedrawComponenets() {
        Group group = new Group();
        ArrayList<Line> lines = DrawLines();
        for (Line line : lines) {
            group.getChildren().add(line);
        }
        for (Drawable drawable : drawables) {
            group.getChildren().add(drawable.GetGroup());
        }
        return group;
    }



    //Getter a gameControl attributumra
    public static GameController getGameController() {
        return gameController;
    }
    public static void setVirologist(Virologist v){
        virologist=v;
    }
    public static  Virologist getVirologist(){
        return virologist;
    }
    public static void GameOver(){
        GraphicsMain.SetPane(3);
    }
}

package Tiles;

import Players.Virologist;
import Tester.Scene;

/**
 * Egy olyan Tile, amelyik az első rálépő Virológusnak egy anyagot ad.
 */

public class Storage extends Tile {
    private int amino = 0;
    private int nukleo = 0;

    /**
     * Storage ctor, leginkább csak message értékű a Logger számára
     */
    public Storage() {
    }

    public boolean HasItem() {
        return nukleo > 0 || amino > 0;
    }

    /**
     * Az első Virológus, aki erre a mezőre lép, véletlenszerűen
     * kap vagy egy aminosavat, vagy egy nuklteotidot.
     *
     * @param virologist -  a rálépő Virológust kapja meg paraméterként
     */
    public void GiveItemTo(Virologist virologist) {
        if (amino == 1) {
            if (virologist.GiveMatter("amino")){
                hasItem = false;
            	amino--;
	    }
        } else if (nukleo == 1) {
            if (virologist.GiveMatter("nukleo")){
                hasItem = false;
            	nukleo--;
	    }
        }
    }

    public void AddAmino(int number) {
        this.amino += number;
        hasItem = true;
    }

    public void AddNukleo(int number) {
        this.nukleo += number;
        hasItem = true;
    }

    public void SetAmino(int amino) {
        this.amino = amino;
        if (amino > 0 || nukleo > 0) hasItem = true;
    }

    public void SetNukleo(int nukleo) {
        this.nukleo = nukleo;
        if (amino > 0 || nukleo > 0) hasItem = true;
    }

	@Override
	public String ToOutput(Scene scene){
		StringBuffer begin = new StringBuffer(super.ToOutput(scene));
		begin.deleteCharAt(begin.length()-1);
		// Meg kell keresni a neveket az osszes attributumhoz is, szepen vegig kell menni minden attributumon amit fent
		// deklaraltunk:
		String amino = String.valueOf(this.amino);
		String nukleo = String.valueOf(this.nukleo);
		// Majd végül össze kell rakni a kimenetet:
		return begin +
				"\tamino: " + amino + "\n" +
				"\tnukleo: " + nukleo + "\n" +
				"}";
	}

}

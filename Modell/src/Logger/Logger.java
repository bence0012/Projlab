package Logger;

import java.util.Scanner;

/**
 * A Logger osztály, amit arr használunk, hogy a függvényhvásokat lehessen követni.
 */
public class Logger {
    /**
     * Egy belső osztály, hogy a Logger tudja, hogy éppen milyen sor volt az utolsó, amit leírt
     */
    private enum State {
        functionStart,
        functionEnd,
        loggerStart
    }

    private static int indentCounter = 0;
    private static State currentState = State.loggerStart;


    /**
     * @return A megfelelő indentáció a sor elejére
     */
    private static String MakeIndentation() {
        StringBuilder indentString = new StringBuilder();
        return indentString.append("\t".repeat(Math.max(0, indentCounter))).toString();
    }

    /**
     * Elkészíti a sor elején látható indenjtációt, és hozzáadja a megfelelő hívás szerinti nyíl irányát.
     * @return egy string a használandó indentációval
     */
    private static String MakeIndentationWithArrow() {
        switch (currentState) {
            case functionStart:
                return MakeIndentation() + "->";
            case functionEnd:
                return MakeIndentation() + "<-";
        };
        return "";
    }

    /**
     * Ezt a függvényt kell minden függvényhívás elejére tenni, hogy a logger ki tudja írni, hogy meghívták az adott
     * függvényt.
     * @param functionName A függvénye neve, amiben meghvjuk zárójelekkel és paraméterekkel együtt
     */
    public static void FunctionStart(String functionName) {
        if (functionName.isBlank()) {
            System.err.println("You have to specify a name in the function call.");
            System.exit(1);
        }
        if (currentState == State.functionStart)
            indentCounter++;
        currentState = State.functionStart;
        System.out.println(MakeIndentationWithArrow() + functionName);
    }

    /**
     * Ezt a függvényt kell meghívni, amikor egy függvényt befejezünk.
     * @param returnType A függvény visszatérési értéke egy beszédes változónévben, ha nincs akkor üresen kell hagyni
     */
    public static void FunctionEnd(String returnType) {
        if (indentCounter != 0) {
            if (currentState == State.functionEnd)
                indentCounter--;
        }
        currentState = State.functionEnd;
        System.out.println(MakeIndentationWithArrow() + returnType);
    }

    /**
     * A felhasználóknak feltett kérdésekre vonatkozó függvény.
     * @param text Ezt a szöveget fogja kiírni a függvény kérdezéskor
     * @return A felhasználó igen / nem válasza a kérdésre
     */
    public static boolean DecisionCall(String text) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(MakeIndentation() + text);
        System.out.print(" y/n: ");
        String input = scanner.next();
        while (!input.equals("y") && !input.equals("n")) {
            System.out.print(MakeIndentation() + "Csak y/n-el válaszolhatsz a kérdésre: ");
            input = scanner.next();
        }
        return input.equals("y");
    }
}

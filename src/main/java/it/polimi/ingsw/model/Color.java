package it.polimi.ingsw.model;
/**
 * @author Giacomo Gumiero
 * Enum to define colors and column position
 */
public enum Color {
    GREEN(0),
    BLUE(1),
    YELLOW(2),
    PURPLE(3);

    private int column;

    Color(int column) {
        this.column = column;
    }

    public int getColumn() {
        return column;
    }
}
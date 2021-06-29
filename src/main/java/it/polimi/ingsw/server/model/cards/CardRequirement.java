package it.polimi.ingsw.server.model.cards;

import it.polimi.ingsw.server.model.Color;

public class CardRequirement {
        private int level;
        private Color color;
        private int quantity;

        public int getLevel() {
                return level;
        }

        public Color getColor() {
                return color;
        }

        public int getQuantity() {
                return quantity;
        }
}

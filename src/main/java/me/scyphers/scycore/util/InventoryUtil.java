package me.scyphers.scycore.util;

public class InventoryUtil {

    public static int getIndexOfItemWithBorder(int inventoryIndex, int borderRows, int borderColumns) {

        int invRows = 6 - borderRows;
        int invColumns = 9 - borderColumns;

        int invRow = inventoryIndex / 9;
        int invColumn = inventoryIndex % 9;

        if (invRow < borderRows / 2 || invRow > invRows + ((borderRows / 2) - 1)) return -1;
        if (invColumn < borderColumns / 2 || invColumn > invColumns + ((borderColumns / 2) - 1)) return -1;

        return ((invRow - (borderRows / 2)) * invColumns) + (invColumn - (borderColumns / 2));

    }

}

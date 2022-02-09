package me.scyphers.scycore.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InventoryUtilTests {

    @Test
    public void getIndexOfItemWithBorderTests() {

        // Testing a 4x7 inventory
        // #########
        // #0000000#
        // #0000000#
        // #0000000#
        // #0000000#
        // #########

        assertEquals(-1, InventoryUtil.getIndexOfItemWithBorder(0, 2, 2));
        assertEquals(-1, InventoryUtil.getIndexOfItemWithBorder(1, 2, 2));
        assertEquals(-1, InventoryUtil.getIndexOfItemWithBorder(2, 2, 2));
        assertEquals(-1, InventoryUtil.getIndexOfItemWithBorder(3, 2, 2));
        assertEquals(-1, InventoryUtil.getIndexOfItemWithBorder(4, 2, 2));
        assertEquals(-1, InventoryUtil.getIndexOfItemWithBorder(5, 2, 2));
        assertEquals(-1, InventoryUtil.getIndexOfItemWithBorder(6, 2, 2));
        assertEquals(-1, InventoryUtil.getIndexOfItemWithBorder(7, 2, 2));
        assertEquals(-1, InventoryUtil.getIndexOfItemWithBorder(8, 2, 2));
        assertEquals(-1, InventoryUtil.getIndexOfItemWithBorder(9, 2, 2));
        assertEquals(0, InventoryUtil.getIndexOfItemWithBorder(10, 2, 2));
        assertEquals(1, InventoryUtil.getIndexOfItemWithBorder(11, 2, 2));
        assertEquals(2, InventoryUtil.getIndexOfItemWithBorder(12, 2, 2));
        assertEquals(3, InventoryUtil.getIndexOfItemWithBorder(13, 2, 2));
        assertEquals(4, InventoryUtil.getIndexOfItemWithBorder(14, 2, 2));
        assertEquals(5, InventoryUtil.getIndexOfItemWithBorder(15, 2, 2));
        assertEquals(6, InventoryUtil.getIndexOfItemWithBorder(16, 2, 2));
        assertEquals(7, InventoryUtil.getIndexOfItemWithBorder(19, 2, 2));
        assertEquals(8, InventoryUtil.getIndexOfItemWithBorder(20, 2, 2));
        assertEquals(9, InventoryUtil.getIndexOfItemWithBorder(21, 2, 2));
        assertEquals(10, InventoryUtil.getIndexOfItemWithBorder(22, 2, 2));
        assertEquals(11, InventoryUtil.getIndexOfItemWithBorder(23, 2, 2));
        assertEquals(12, InventoryUtil.getIndexOfItemWithBorder(24, 2, 2));
        assertEquals(13, InventoryUtil.getIndexOfItemWithBorder(25, 2, 2));
        assertEquals(14, InventoryUtil.getIndexOfItemWithBorder(28, 2, 2));
        assertEquals(15, InventoryUtil.getIndexOfItemWithBorder(29, 2, 2));
        assertEquals(16, InventoryUtil.getIndexOfItemWithBorder(30, 2, 2));
        assertEquals(17, InventoryUtil.getIndexOfItemWithBorder(31, 2, 2));
        assertEquals(18, InventoryUtil.getIndexOfItemWithBorder(32, 2, 2));
        assertEquals(19, InventoryUtil.getIndexOfItemWithBorder(33, 2, 2));
        assertEquals(20, InventoryUtil.getIndexOfItemWithBorder(34, 2, 2));
        assertEquals(21, InventoryUtil.getIndexOfItemWithBorder(37, 2, 2));
        assertEquals(22, InventoryUtil.getIndexOfItemWithBorder(38, 2, 2));
        assertEquals(23, InventoryUtil.getIndexOfItemWithBorder(39, 2, 2));
        assertEquals(24, InventoryUtil.getIndexOfItemWithBorder(40, 2, 2));
        assertEquals(25, InventoryUtil.getIndexOfItemWithBorder(41, 2, 2));
        assertEquals(26, InventoryUtil.getIndexOfItemWithBorder(42, 2, 2));
        assertEquals(27, InventoryUtil.getIndexOfItemWithBorder(43, 2, 2));
        assertEquals(-1, InventoryUtil.getIndexOfItemWithBorder(44, 2, 2));
        assertEquals(-1, InventoryUtil.getIndexOfItemWithBorder(45, 2, 2));
        assertEquals(-1, InventoryUtil.getIndexOfItemWithBorder(46, 2, 2));
        assertEquals(-1, InventoryUtil.getIndexOfItemWithBorder(47, 2, 2));
        assertEquals(-1, InventoryUtil.getIndexOfItemWithBorder(48, 2, 2));
        assertEquals(-1, InventoryUtil.getIndexOfItemWithBorder(49, 2, 2));
        assertEquals(-1, InventoryUtil.getIndexOfItemWithBorder(50, 2, 2));
        assertEquals(-1, InventoryUtil.getIndexOfItemWithBorder(51, 2, 2));
        assertEquals(-1, InventoryUtil.getIndexOfItemWithBorder(52, 2, 2));
        assertEquals(-1, InventoryUtil.getIndexOfItemWithBorder(53, 2, 2));

        // Testing a 2x7 inventory
        // #########
        // #########
        // #0000000#
        // #0000000#
        // #########
        // #########

        assertEquals(0, InventoryUtil.getIndexOfItemWithBorder(19, 4, 2));
        assertEquals(1, InventoryUtil.getIndexOfItemWithBorder(20, 4, 2));
        assertEquals(2, InventoryUtil.getIndexOfItemWithBorder(21, 4, 2));
        assertEquals(3, InventoryUtil.getIndexOfItemWithBorder(22, 4, 2));
        assertEquals(4, InventoryUtil.getIndexOfItemWithBorder(23, 4, 2));
        assertEquals(5, InventoryUtil.getIndexOfItemWithBorder(24, 4, 2));
        assertEquals(6, InventoryUtil.getIndexOfItemWithBorder(25, 4, 2));
        assertEquals(7, InventoryUtil.getIndexOfItemWithBorder(28, 4, 2));
        assertEquals(8, InventoryUtil.getIndexOfItemWithBorder(29, 4, 2));
        assertEquals(9, InventoryUtil.getIndexOfItemWithBorder(30, 4, 2));
        assertEquals(10, InventoryUtil.getIndexOfItemWithBorder(31, 4, 2));
        assertEquals(11, InventoryUtil.getIndexOfItemWithBorder(32, 4, 2));
        assertEquals(12, InventoryUtil.getIndexOfItemWithBorder(33, 4, 2));
        assertEquals(13, InventoryUtil.getIndexOfItemWithBorder(34, 4, 2));

    }

}

package ru.sbt.gameoflife.simple;

/**
 * Created by SBT-Ovchinnikov-DK on 13.12.2017.
 *
 * Types of cell on current step
 * Типы текущего состояния клетки. Необходима для реализации в одном потоке с одной матрицей.
 */
public enum CellType {
    DEAD((byte)0),
    LIVE((byte)1),
    NOW_LIVE_WAS_LIVE((byte)2),
    NOW_LIVE_WAS_DEAD((byte)3),
    NOW_DEAD_WAS_LIVE((byte)4);

    private byte value;

    CellType(byte val) {
        this.value = val;
    }

    public byte getValue() {
        return value;
    }
}

package com.gabid.ezaciancraft.api.common.items;

public enum EzacianToolMode {
    SINGLE(),
    AREA(),
    COLUMN();

    @Override
    public String toString() {
        return values().toString();
    }
}
package com.runescape.client.world;

import com.runescape.client.world.data.ItemDef;

public final class Item extends Animable {

    public int ID;
    public int x;
    public int y;
    public int anInt1559;

    public Item() {
    }

    public final Model getRotatedModel() {
        ItemDef itemDef = ItemDef.forID(ID);
        return itemDef.getRotatedModel(anInt1559);
    }
}

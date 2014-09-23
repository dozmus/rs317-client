package com.runescape.client;

public final class OnDemandData extends NodeSub {

    int dataType;
    byte buffer[];
    int ID;
    boolean incomplete;
    int loopCycle;

    public OnDemandData() {
        incomplete = true;
    }
}

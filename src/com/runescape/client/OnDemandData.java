package com.runescape.client;

import com.runescape.client.util.node.NodeSub;

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

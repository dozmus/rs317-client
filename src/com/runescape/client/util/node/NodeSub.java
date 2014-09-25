package com.runescape.client.util.node;

public class NodeSub extends Node {

    public static int anInt1305;

    public NodeSub prevNodeSub;
    NodeSub nextNodeSub;

    public NodeSub() {
    }

    public final void unlinkSub() {
        if (nextNodeSub != null) {
            nextNodeSub.prevNodeSub = prevNodeSub;
            prevNodeSub.nextNodeSub = nextNodeSub;
            prevNodeSub = null;
            nextNodeSub = null;
        }
    }
}

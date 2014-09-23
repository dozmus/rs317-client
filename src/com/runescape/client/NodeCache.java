package com.runescape.client;

import com.runescape.client.signlink.Signlink;

final class NodeCache {

    private final int size;
    private final Node[] cache;

    public NodeCache() {
        int i = 1024;//was parameter
        size = i;
        cache = new Node[i];
        
        for (int k = 0; k < i; k++) {
            Node node = cache[k] = new Node();
            node.prev = node;
            node.next = node;
        }
    }

    public Node findNodeByID(long l) {
        Node node = cache[(int) (l & (long) (size - 1))];
        
        for (Node tmp = node.prev; tmp != node; tmp = tmp.prev) {
            if (tmp.id == l) {
                return tmp;
            }
        }
        return null;
    }

    public void removeFromCache(Node node, long l) {
        try {
            if (node.next != null) {
                node.unlink();
            }
            Node tmp = cache[(int) (l & (long) (size - 1))];
            node.next = tmp.next;
            node.prev = tmp;
            node.next.prev = node;
            node.prev.next = node;
            node.id = l;
            return;
        } catch (RuntimeException ex) {
            Signlink.printError("91499, " + node + ", " + l + ", " + (byte) 7 + ", " + ex.toString());
        }
        throw new RuntimeException();
    }
}

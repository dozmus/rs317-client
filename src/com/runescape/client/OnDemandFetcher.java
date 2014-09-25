package com.runescape.client;

import com.runescape.client.util.node.NodeList;
import com.runescape.client.util.node.NodeSubList;
import com.runescape.client.io.Stream;
import com.runescape.client.io.StreamLoader;
import java.io.*;
import java.net.Socket;
import java.util.zip.CRC32;
import java.util.zip.GZIPInputStream;
import com.runescape.client.signlink.Signlink;

public final class OnDemandFetcher extends OnDemandFetcherParent
        implements Runnable {

    private int totalFiles;
    private final NodeList requested;
    private int anInt1332;
    public String statusString;
    private int writeLoopCycle;
    private long openSocketTime;
    private int[] mapIndices3;
    private final CRC32 crc32;
    private final byte[] ioBuffer;
    public int onDemandCycle;
    private final byte[][] fileStatus;
    private Client clientInstance;
    private final NodeList onDemandDataNodeList;
    private int completedSize;
    private int expectedSize;
    private int[] anIntArray1348;
    public int anInt1349;
    private int[] mapIndices2;
    private int filesLoaded;
    private boolean running;
    private OutputStream outputStream;
    private int[] mapIndices4;
    private boolean waiting;
    private final NodeList completeOnDemandDataNodeList;
    private final byte[] gzipInputBuffer;
    private int[] anIntArray1360;
    private final NodeSubList nodeSubList;
    private InputStream inputStream;
    private Socket socket;
    private final int[][] versions;
    private final int[][] crcs;
    private int uncompletedCount;
    private int completedCount;
    private final NodeList queuedOnDemandDataNodeList;
    private OnDemandData current;
    private final NodeList nodeList;
    private int[] mapIndices1;
    private byte[] modelIndices;
    private int loopCycle;

    public OnDemandFetcher() {
        requested = new NodeList();
        statusString = "";
        crc32 = new CRC32();
        ioBuffer = new byte[500];
        fileStatus = new byte[4][];
        onDemandDataNodeList = new NodeList();
        running = true;
        waiting = false;
        completeOnDemandDataNodeList = new NodeList();
        gzipInputBuffer = new byte[65000];
        nodeSubList = new NodeSubList();
        versions = new int[4][];
        crcs = new int[4][];
        queuedOnDemandDataNodeList = new NodeList();
        nodeList = new NodeList();
    }

    private boolean crcMatches(int i, int j, byte buf[]) {
        if (buf == null || buf.length < 2) {
            return false;
        }
        int length = buf.length - 2;
        int l = ((buf[length] & 0xff) << 8) + (buf[length + 1] & 0xff);
        crc32.reset();
        crc32.update(buf, 0, length);
        int i1 = (int) crc32.getValue();
        return l == i && i1 == j;
    }

    private void readData() {
        try {
            int j = inputStream.available();
            if (expectedSize == 0 && j >= 6) {
                waiting = true;
                for (int k = 0; k < 6; k += inputStream.read(ioBuffer, k, 6 - k));
                int l = ioBuffer[0] & 0xff;
                int j1 = ((ioBuffer[1] & 0xff) << 8) + (ioBuffer[2] & 0xff);
                int l1 = ((ioBuffer[3] & 0xff) << 8) + (ioBuffer[4] & 0xff);
                int i2 = ioBuffer[5] & 0xff;
                current = null;
                for (OnDemandData onDemandData = (OnDemandData) requested.reverseGetFirst(); onDemandData != null; onDemandData = (OnDemandData) requested.reverseGetNext()) {
                    if (onDemandData.dataType == l && onDemandData.ID == j1) {
                        current = onDemandData;
                    }
                    if (current != null) {
                        onDemandData.loopCycle = 0;
                    }
                }

                if (current != null) {
                    loopCycle = 0;
                    if (l1 == 0) {
                        Signlink.printError("Rej: " + l + "," + j1);
                        current.buffer = null;
                        if (current.incomplete) {
                            synchronized (completeOnDemandDataNodeList) {
                                completeOnDemandDataNodeList.insertHead(current);
                            }
                        } else {
                            current.unlink();
                        }
                        current = null;
                    } else {
                        if (current.buffer == null && i2 == 0) {
                            current.buffer = new byte[l1];
                        }
                        if (current.buffer == null && i2 != 0) {
                            throw new IOException("missing start of file");
                        }
                    }
                }
                completedSize = i2 * 500;
                expectedSize = 500;
                if (expectedSize > l1 - i2 * 500) {
                    expectedSize = l1 - i2 * 500;
                }
            }
            if (expectedSize > 0 && j >= expectedSize) {
                waiting = true;
                byte abyte0[] = ioBuffer;
                int i1 = 0;
                if (current != null) {
                    abyte0 = current.buffer;
                    i1 = completedSize;
                }
                for (int k1 = 0; k1 < expectedSize; k1 += inputStream.read(abyte0, k1 + i1, expectedSize - k1));
                if (expectedSize + completedSize >= abyte0.length && current != null) {
                    if (clientInstance.decompressors[0] != null) {
                        clientInstance.decompressors[current.dataType + 1].method234(abyte0.length, abyte0, current.ID);
                    }
                    if (!current.incomplete && current.dataType == 3) {
                        current.incomplete = true;
                        current.dataType = 93;
                    }
                    if (current.incomplete) {
                        synchronized (completeOnDemandDataNodeList) {
                            completeOnDemandDataNodeList.insertHead(current);
                        }
                    } else {
                        current.unlink();
                    }
                }
                expectedSize = 0;
            }
        } catch (IOException ioexception) {
            try {
                socket.close();
            } catch (Exception _ex) {
            }
            socket = null;
            inputStream = null;
            outputStream = null;
            expectedSize = 0;
        }
    }

    public void start(StreamLoader streamLoader, Client instance) {
        String as[] = {
            "model_version", "anim_version", "midi_version", "map_version"
        };

        for (int dataType = 0; dataType < 4; dataType++) {
            byte abyte0[] = streamLoader.getDataForName(as[dataType]);
            int idCount = abyte0.length / 2;
            Stream stream = new Stream(abyte0);
            versions[dataType] = new int[idCount];
            fileStatus[dataType] = new byte[idCount];

            for (int id = 0; id < idCount; id++) {
                versions[dataType][id] = stream.readUShort();
            }
        }
        String as1[] = {
            "model_crc", "anim_crc", "midi_crc", "map_crc"
        };

        for (int k = 0; k < 4; k++) {
            byte abyte1[] = streamLoader.getDataForName(as1[k]);
            int i1 = abyte1.length / 4;
            Stream stream_1 = new Stream(abyte1);
            crcs[k] = new int[i1];

            for (int l1 = 0; l1 < i1; l1++) {
                crcs[k][l1] = stream_1.readUInt();
            }
        }
        byte buf[] = streamLoader.getDataForName("model_index");
        int j1 = versions[0].length;
        modelIndices = new byte[j1];

        for (int k1 = 0; k1 < j1; k1++) {
            if (k1 < buf.length) {
                modelIndices[k1] = buf[k1];
            } else {
                modelIndices[k1] = 0;
            }
        }
        buf = streamLoader.getDataForName("map_index");
        Stream stream2 = new Stream(buf);
        j1 = buf.length / 7;
        mapIndices1 = new int[j1];
        mapIndices2 = new int[j1];
        mapIndices3 = new int[j1];
        mapIndices4 = new int[j1];

        for (int i2 = 0; i2 < j1; i2++) {
            mapIndices1[i2] = stream2.readUShort();
            mapIndices2[i2] = stream2.readUShort();
            mapIndices3[i2] = stream2.readUShort();
            mapIndices4[i2] = stream2.readUByte();
        }
        buf = streamLoader.getDataForName("anim_index");
        stream2 = new Stream(buf);
        j1 = buf.length / 2;
        anIntArray1360 = new int[j1];

        for (int j2 = 0; j2 < j1; j2++) {
            anIntArray1360[j2] = stream2.readUShort();
        }
        buf = streamLoader.getDataForName("midi_index");
        stream2 = new Stream(buf);
        j1 = buf.length;
        anIntArray1348 = new int[j1];

        for (int k2 = 0; k2 < j1; k2++) {
            anIntArray1348[k2] = stream2.readUByte();
        }
        clientInstance = instance;
        running = true;
        clientInstance.startRunnable(this, 2);
    }

    public int getNodeCount() {
        synchronized (nodeSubList) {
            return nodeSubList.getNodeCount();
        }
    }

    public void disable() {
        running = false;
    }

    public void method554(boolean flag) {
        int j = mapIndices1.length;

        for (int k = 0; k < j; k++) {
            if (flag || mapIndices4[k] != 0) {
                method563((byte) 2, 3, mapIndices3[k]);
                method563((byte) 2, 3, mapIndices2[k]);
            }
        }
    }

    public int getVersionCount(int dataType) {
        return versions[dataType].length;
    }

    private void closeRequest(OnDemandData onDemandData) {
        try {
            if (socket == null) {
                long l = System.currentTimeMillis();

                if (l - openSocketTime < 4000L) {
                    return;
                }
                openSocketTime = l;
                socket = clientInstance.openSocket(Client.port);
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
                outputStream.write(15);

                for (int j = 0; j < 8; j++) {
                    inputStream.read();
                }
                loopCycle = 0;
            }
            ioBuffer[0] = (byte) onDemandData.dataType;
            ioBuffer[1] = (byte) (onDemandData.ID >> 8);
            ioBuffer[2] = (byte) onDemandData.ID;

            if (onDemandData.incomplete) {
                ioBuffer[3] = 2;
            } else if (!clientInstance.loggedIn) {
                ioBuffer[3] = 1;
            } else {
                ioBuffer[3] = 0;
            }
            outputStream.write(ioBuffer, 0, 4);
            writeLoopCycle = 0;
            anInt1349 = -10000;
            return;
        } catch (IOException ioexception) {
        }

        try {
            socket.close();
        } catch (IOException _ex) {
        }
        socket = null;
        inputStream = null;
        outputStream = null;
        expectedSize = 0;
        anInt1349++;
    }

    public int getAnimCount() {
        return anIntArray1360.length;
    }

    public void fetchItem(int dataType, int id) {
        if (dataType < 0 || dataType > versions.length || id < 0 || id > versions[dataType].length) {
            return;
        }

        if (versions[dataType][id] == 0) {
            return;
        }

        synchronized (nodeSubList) {
            for (OnDemandData onDemandData = (OnDemandData) nodeSubList.reverseGetFirst();
                    onDemandData != null;
                    onDemandData = (OnDemandData) nodeSubList.reverseGetNext()) {
                if (onDemandData.dataType == dataType && onDemandData.ID == id) {
                    return;
                }
            }
            OnDemandData data = new OnDemandData();
            data.dataType = dataType;
            data.ID = id;
            data.incomplete = true;

            synchronized (nodeList) {
                nodeList.insertHead(data);
            }
            nodeSubList.insertHead(data);
        }
    }

    public int getModelIndex(int i) {
        return modelIndices[i] & 0xff;
    }

    public void run() {
        try {
            while (running) {
                onDemandCycle++;
                int i = 20;

                if (anInt1332 == 0 && clientInstance.decompressors[0] != null) {
                    i = 50;
                }

                try {
                    Thread.sleep(i);
                } catch (Exception _ex) {
                }
                waiting = true;

                for (int j = 0; j < 100; j++) {
                    if (!waiting) {
                        break;
                    }
                    waiting = false;
                    checkReceived();
                    handleFailed();

                    if (uncompletedCount == 0 && j >= 5) {
                        break;
                    }
                    method568();

                    if (inputStream != null) {
                        readData();
                    }
                }
                boolean flag = false;

                for (OnDemandData data = (OnDemandData) requested.reverseGetFirst();
                        data != null; data = (OnDemandData) requested.reverseGetNext()) {
                    if (data.incomplete) {
                        flag = true;
                        data.loopCycle++;

                        if (data.loopCycle > 50) {
                            data.loopCycle = 0;
                            closeRequest(data);
                        }
                    }
                }

                if (!flag) {
                    for (OnDemandData data = (OnDemandData) requested.reverseGetFirst();
                            data != null; data = (OnDemandData) requested.reverseGetNext()) {
                        flag = true;
                        data.loopCycle++;

                        if (data.loopCycle > 50) {
                            data.loopCycle = 0;
                            closeRequest(data);
                        }
                    }
                }

                if (flag) {
                    loopCycle++;
                    if (loopCycle > 750) {
                        try {
                            socket.close();
                        } catch (Exception _ex) {
                        }
                        socket = null;
                        inputStream = null;
                        outputStream = null;
                        expectedSize = 0;
                    }
                } else {
                    loopCycle = 0;
                    statusString = "";
                }
                if (clientInstance.loggedIn && socket != null && outputStream != null
                        && (anInt1332 > 0 || clientInstance.decompressors[0] == null)) {
                    writeLoopCycle++;

                    if (writeLoopCycle > 500) {
                        writeLoopCycle = 0;
                        ioBuffer[0] = 0;
                        ioBuffer[1] = 0;
                        ioBuffer[2] = 0;
                        ioBuffer[3] = 10;

                        try {
                            outputStream.write(ioBuffer, 0, 4);
                        } catch (IOException _ex) {
                            loopCycle = 5000;
                        }
                    }
                }
            }
        } catch (Exception exception) {
            Signlink.printError("od_ex " + exception.getMessage());
        }
    }

    public void method560(int id, int dataType) {
        if (clientInstance.decompressors[0] == null) {
            return;
        }

        if (versions[dataType][id] == 0) {
            return;
        }

        if (fileStatus[dataType][id] == 0) {
            return;
        }

        if (anInt1332 == 0) {
            return;
        }
        OnDemandData onDemandData = new OnDemandData();
        onDemandData.dataType = dataType;
        onDemandData.ID = id;
        onDemandData.incomplete = false;

        synchronized (onDemandDataNodeList) {
            onDemandDataNodeList.insertHead(onDemandData);
        }
    }

    public OnDemandData getNextNode() {
        OnDemandData onDemandData;

        synchronized (completeOnDemandDataNodeList) {
            onDemandData = (OnDemandData) completeOnDemandDataNodeList.popHead();
        }

        if (onDemandData == null) {
            return null;
        }

        synchronized (nodeSubList) {
            onDemandData.unlinkSub();
        }

        if (onDemandData.buffer == null) {
            return onDemandData;
        }
        int length = 0;

        try {
            GZIPInputStream gzipinputstream = new GZIPInputStream(new ByteArrayInputStream(onDemandData.buffer));

            do {
                if (length == gzipInputBuffer.length) {
                    throw new RuntimeException("buffer overflow!");
                }
                int k = gzipinputstream.read(gzipInputBuffer, length, gzipInputBuffer.length - length);

                if (k == -1) {
                    break;
                }
                length += k;
            } while (true);
        } catch (IOException _ex) {
            throw new RuntimeException("error unzipping");
        }
        onDemandData.buffer = new byte[length];
        System.arraycopy(gzipInputBuffer, 0, onDemandData.buffer, 0, length);
        return onDemandData;
    }

    public int method562(int i, int k, int l) {
        int i1 = (l << 8) + k;

        for (int j1 = 0; j1 < mapIndices1.length; j1++) {
            if (mapIndices1[j1] == i1) {
                if (i == 0) {
                    return mapIndices2[j1];
                } else {
                    return mapIndices3[j1];
                }
            }
        }
        return -1;
    }

    public void method548(int id) {
        fetchItem(0, id);
    }

    public void method563(byte byte0, int dataType, int id) {
        if (clientInstance.decompressors[0] == null) {
            return;
        }
        if (versions[dataType][id] == 0) {
            return;
        }
        byte data[] = clientInstance.decompressors[dataType + 1].decompress(id);

        if (crcMatches(versions[dataType][id], crcs[dataType][id], data)) {
            return;
        }
        fileStatus[dataType][id] = byte0;

        if (byte0 > anInt1332) {
            anInt1332 = byte0;
        }
        totalFiles++;
    }

    public boolean method564(int i) {
        for (int k = 0; k < mapIndices1.length; k++) {
            if (mapIndices3[k] == i) {
                return true;
            }
        }
        return false;
    }

    private void handleFailed() {
        uncompletedCount = 0;
        completedCount = 0;

        for (OnDemandData data = (OnDemandData) requested.reverseGetFirst();
                data != null;
                data = (OnDemandData) requested.reverseGetNext()) {
            if (data.incomplete) {
                uncompletedCount++;
            } else {
                completedCount++;
            }
        }

        while (uncompletedCount < 10) {
            OnDemandData data = (OnDemandData) queuedOnDemandDataNodeList.popHead();

            if (data == null) {
                break;
            }

            if (fileStatus[data.dataType][data.ID] != 0) {
                filesLoaded++;
            }
            fileStatus[data.dataType][data.ID] = 0;
            requested.insertHead(data);
            uncompletedCount++;
            closeRequest(data);
            waiting = true;
        }
    }

    public void clearOnDemandDataNodeList() {
        synchronized (onDemandDataNodeList) {
            onDemandDataNodeList.removeAll();
        }
    }

    private void checkReceived() {
        OnDemandData onDemandData;

        synchronized (nodeList) {
            onDemandData = (OnDemandData) nodeList.popHead();
        }

        while (onDemandData != null) {
            waiting = true;
            byte buf[] = null;

            if (clientInstance.decompressors[0] != null) {
                buf = clientInstance.decompressors[onDemandData.dataType + 1].decompress(onDemandData.ID);
            }

            if (!crcMatches(versions[onDemandData.dataType][onDemandData.ID], crcs[onDemandData.dataType][onDemandData.ID], buf)) {
                buf = null;
            }

            synchronized (nodeList) {
                if (buf == null) {
                    queuedOnDemandDataNodeList.insertHead(onDemandData);
                } else {
                    onDemandData.buffer = buf;

                    synchronized (completeOnDemandDataNodeList) {
                        completeOnDemandDataNodeList.insertHead(onDemandData);
                    }
                }
                onDemandData = (OnDemandData) nodeList.popHead();
            }
        }
    }

    private void method568() {
        while (uncompletedCount == 0 && completedCount < 10) {
            if (anInt1332 == 0) {
                break;
            }
            OnDemandData onDemandData;

            synchronized (onDemandDataNodeList) {
                onDemandData = (OnDemandData) onDemandDataNodeList.popHead();
            }

            while (onDemandData != null) {
                if (fileStatus[onDemandData.dataType][onDemandData.ID] != 0) {
                    fileStatus[onDemandData.dataType][onDemandData.ID] = 0;
                    requested.insertHead(onDemandData);
                    closeRequest(onDemandData);
                    waiting = true;

                    if (filesLoaded < totalFiles) {
                        filesLoaded++;
                    }
                    statusString = "Loading extra files - " + (filesLoaded * 100) / totalFiles + "%";
                    completedCount++;

                    if (completedCount == 10) {
                        return;
                    }
                }
                synchronized (onDemandDataNodeList) {
                    onDemandData = (OnDemandData) onDemandDataNodeList.popHead();
                }
            }

            for (int dataType = 0; dataType < 4; dataType++) {
                byte buf[] = fileStatus[dataType];
                int idCount = buf.length;

                for (int id = 0; id < idCount; id++) {
                    if (buf[id] == anInt1332) {
                        buf[id] = 0;
                        OnDemandData data = new OnDemandData();
                        data.dataType = dataType;
                        data.ID = id;
                        data.incomplete = false;
                        requested.insertHead(data);
                        closeRequest(data);
                        waiting = true;

                        if (filesLoaded < totalFiles) {
                            filesLoaded++;
                        }
                        statusString = "Loading extra files - " + (filesLoaded * 100) / totalFiles + "%";
                        completedCount++;

                        if (completedCount == 10) {
                            return;
                        }
                    }
                }
            }
            anInt1332--;
        }
    }

    public boolean method569(int i) {
        return anIntArray1348[i] == 1;
    }
}

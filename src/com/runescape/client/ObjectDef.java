package com.runescape.client;

import com.runescape.client.io.Stream;
import com.runescape.client.io.StreamLoader;

public final class ObjectDef {

    private static final Model[] aModelArray741s = new Model[4];
    public static boolean lowMem;
    private static Stream stream;
    private static int[] streamIndices;
    public static Client instance;
    private static int cacheIndex;
    public static MRUNodes mruNodes2 = new MRUNodes(30);
    private static ObjectDef[] cache;
    public static MRUNodes mruNodes1 = new MRUNodes(500);

    public static ObjectDef forID(int id) {
        for (int j = 0; j < 20; j++) {
            if (cache[j].type == id) {
                return cache[j];
            }
        }
        cacheIndex = (cacheIndex + 1) % 20;
        ObjectDef def = cache[cacheIndex];
        stream.currentOffset = streamIndices[id];
        def.type = id;
        def.setDefaults();
        def.readValues(stream);
        return def;
    }

    public static void nullLoader() {
        mruNodes1 = null;
        mruNodes2 = null;
        streamIndices = null;
        cache = null;
        stream = null;
    }

    public static void unpackConfig(StreamLoader streamLoader) {
        stream = new Stream(streamLoader.getDataForName("loc.dat"));
        Stream stream = new Stream(streamLoader.getDataForName("loc.idx"));
        int totalObjects = stream.readUShort();
        streamIndices = new int[totalObjects];
        int i = 2;
        
        for (int j = 0; j < totalObjects; j++) {
            streamIndices[j] = i;
            i += stream.readUShort();
        }
        cache = new ObjectDef[20];
        
        for (int k = 0; k < 20; k++) {
            cache[k] = new ObjectDef();
        }
    }

    public boolean aBoolean736;
    private byte aByte737;
    private int anInt738;
    public String name;
    private int anInt740;
    private byte aByte742;
    public int anInt744;
    private int anInt745;
    public int anInt746;
    private int[] originalModelColors;
    private int anInt748;
    public int clientSettingId;
    private boolean aBoolean751;
    public int type;
    public boolean aBoolean757;
    public int anInt758;
    public int childrenIDs[];
    private int anInt760;
    public int anInt761;
    public boolean aBoolean762;
    public boolean aBoolean764;
    private boolean aBoolean766;
    public boolean aBoolean767;
    public int anInt768;
    private boolean aBoolean769;
    private int anInt772;
    private int[] anIntArray773;
    public int varBitId;
    public int anInt775;
    private int[] anIntArray776;
    public byte description[];
    public boolean hasActions;
    public boolean aBoolean779;
    public int anInt781;
    private int anInt783;
    private int[] modifiedModelColors;
    public String actions[];

    private ObjectDef() {
        type = -1;
    }

    private void setDefaults() {
        anIntArray773 = null;
        anIntArray776 = null;
        name = null;
        description = null;
        modifiedModelColors = null;
        originalModelColors = null;
        anInt744 = 1;
        anInt761 = 1;
        aBoolean767 = true;
        aBoolean757 = true;
        hasActions = false;
        aBoolean762 = false;
        aBoolean769 = false;
        aBoolean764 = false;
        anInt781 = -1;
        anInt775 = 16;
        aByte737 = 0;
        aByte742 = 0;
        actions = null;
        anInt746 = -1;
        anInt758 = -1;
        aBoolean751 = false;
        aBoolean779 = true;
        anInt748 = 128;
        anInt772 = 128;
        anInt740 = 128;
        anInt768 = 0;
        anInt738 = 0;
        anInt745 = 0;
        anInt783 = 0;
        aBoolean736 = false;
        aBoolean766 = false;
        anInt760 = -1;
        varBitId = -1;
        clientSettingId = -1;
        childrenIDs = null;
    }

    public void method574(OnDemandFetcher ondemandFetcher) {
        if (anIntArray773 == null) {
            return;
        }
        
        for (int j = 0; j < anIntArray773.length; j++) {
            ondemandFetcher.method560(anIntArray773[j] & 0xffff, 0);
        }
    }

    public boolean method577(int i) {
        if (anIntArray776 == null) {
            if (anIntArray773 == null) {
                return true;
            }
            
            if (i != 10) {
                return true;
            }
            boolean flag1 = true;
            
            for (int k = 0; k < anIntArray773.length; k++) {
                flag1 &= Model.method463(anIntArray773[k] & 0xffff);
            }
            return flag1;
        }
        
        for (int j = 0; j < anIntArray776.length; j++) {
            if (anIntArray776[j] == i) {
                return Model.method463(anIntArray773[j] & 0xffff);
            }
        }
        return true;
    }

    public Model method578(int i, int j, int k, int l, int i1, int j1, int k1) {
        Model model = method581(i, k1, j);
        
        if (model == null) {
            return null;
        }
        
        if (aBoolean762 || aBoolean769) {
            model = new Model(aBoolean762, aBoolean769, model);
        }
        
        if (aBoolean762) {
            int l1 = (k + l + i1 + j1) / 4;
            
            for (int i2 = 0; i2 < model.anInt1626; i2++) {
                int j2 = model.anIntArray1627[i2];
                int k2 = model.anIntArray1629[i2];
                int l2 = k + ((l - k) * (j2 + 64)) / 128;
                int i3 = j1 + ((i1 - j1) * (j2 + 64)) / 128;
                int j3 = l2 + ((i3 - l2) * (k2 + 64)) / 128;
                model.anIntArray1628[i2] += j3 - l1;
            }
            model.method467();
        }
        return model;
    }

    public boolean method579() {
        if (anIntArray773 == null) {
            return true;
        }
        boolean flag1 = true;
        
        for (int i = 0; i < anIntArray773.length; i++) {
            flag1 &= Model.method463(anIntArray773[i] & 0xffff);
        }
        return flag1;
    }

    public ObjectDef method580() {
        int i = -1;
        
        if (varBitId != -1) {
            VarBit varBit = VarBit.cache[varBitId];
            int j = varBit.anInt648;
            int k = varBit.anInt649;
            int l = varBit.anInt650;
            int i1 = Client.anIntArray1232[l - k];
            i = instance.currentUserSetting[j] >> k & i1;
        } else if (clientSettingId != -1) {
            i = instance.currentUserSetting[clientSettingId];
        }
        
        if (i < 0 || i >= childrenIDs.length || childrenIDs[i] == -1) {
            return null;
        } else {
            return forID(childrenIDs[i]);
        }
    }

    private Model method581(int j, int k, int l) {
        Model m = null;
        long l1;
        
        if (anIntArray776 == null) {
            if (j != 10) {
                return null;
            }
            l1 = (long) ((type << 6) + l) + ((long) (k + 1) << 32);
            Model m2 = (Model) mruNodes2.insertFromCache(l1);
            
            if (m2 != null) {
                return m2;
            }
            
            if (anIntArray773 == null) {
                return null;
            }
            boolean flag1 = aBoolean751 ^ (l > 3);
            int k1 = anIntArray773.length;
            
            for (int i2 = 0; i2 < k1; i2++) {
                int l2 = anIntArray773[i2];
                
                if (flag1) {
                    l2 += 0x10000;
                }
                m = (Model) mruNodes1.insertFromCache(l2);
                
                if (m == null) {
                    m = Model.method462(l2 & 0xffff);
                    
                    if (m == null) {
                        return null;
                    }
                    
                    if (flag1) {
                        m.method477();
                    }
                    mruNodes1.removeFromCache(m, l2);
                }
                
                if (k1 > 1) {
                    aModelArray741s[i2] = m;
                }
            }

            if (k1 > 1) {
                m = new Model(k1, aModelArray741s);
            }
        } else {
            int i1 = -1;
            
            for (int j1 = 0; j1 < anIntArray776.length; j1++) {
                if (anIntArray776[j1] != j) {
                    continue;
                }
                i1 = j1;
                break;
            }

            if (i1 == -1) {
                return null;
            }
            l1 = (long) ((type << 6) + (i1 << 3) + l) + ((long) (k + 1) << 32);
            Model model_2 = (Model) mruNodes2.insertFromCache(l1);
            
            if (model_2 != null) {
                return model_2;
            }
            int j2 = anIntArray773[i1];
            boolean flag3 = aBoolean751 ^ (l > 3);
            
            if (flag3) {
                j2 += 0x10000;
            }
            m = (Model) mruNodes1.insertFromCache(j2);
            
            if (m == null) {
                m = Model.method462(j2 & 0xffff);
                
                if (m == null) {
                    return null;
                }
                
                if (flag3) {
                    m.method477();
                }
                mruNodes1.removeFromCache(m, j2);
            }
        }
        boolean flag;
        flag = anInt748 != 128 || anInt772 != 128 || anInt740 != 128;
        boolean flag2;
        flag2 = anInt738 != 0 || anInt745 != 0 || anInt783 != 0;
        Model model_3 = new Model(modifiedModelColors == null, Class36.method532(k), l == 0 && k == -1 && !flag && !flag2, m);
        
        if (k != -1) {
            model_3.method469();
            model_3.method470(k);
            model_3.anIntArrayArray1658 = null;
            model_3.anIntArrayArray1657 = null;
        }
        while (l-- > 0) {
            model_3.method473();
        }
        if (modifiedModelColors != null) {
            for (int k2 = 0; k2 < modifiedModelColors.length; k2++) {
                model_3.method476(modifiedModelColors[k2], originalModelColors[k2]);
            }

        }
        if (flag) {
            model_3.method478(anInt748, anInt740, anInt772);
        }
        if (flag2) {
            model_3.method475(anInt738, anInt745, anInt783);
        }
        model_3.method479(64 + aByte737, 768 + aByte742 * 5, -50, -10, -50, !aBoolean769);
        if (anInt760 == 1) {
            model_3.anInt1654 = model_3.modelHeight;
        }
        mruNodes2.removeFromCache(model_3, l1);
        return model_3;
    }

    private void readValues(Stream stream) {
        int i = -1;
        
        label0:
        do {
            int opcode;
            
            do {
                opcode = stream.readUByte();
                
                if (opcode == 0) {
                    break label0;
                }
                
                if (opcode == 1) {
                    int k = stream.readUByte();
                    
                    if (k > 0) {
                        if (anIntArray773 == null || lowMem) {
                            anIntArray776 = new int[k];
                            anIntArray773 = new int[k];
                            
                            for (int k1 = 0; k1 < k; k1++) {
                                anIntArray773[k1] = stream.readUShort();
                                anIntArray776[k1] = stream.readUByte();
                            }
                        } else {
                            stream.currentOffset += k * 3;
                        }
                    }
                } else if (opcode == 2) {
                    name = stream.readString();
                } else if (opcode == 3) {
                    description = stream.readBytes();
                } else if (opcode == 5) {
                    int l = stream.readUByte();
                    
                    if (l > 0) {
                        if (anIntArray773 == null || lowMem) {
                            anIntArray776 = null;
                            anIntArray773 = new int[l];
                            
                            for (int l1 = 0; l1 < l; l1++) {
                                anIntArray773[l1] = stream.readUShort();
                            }
                        } else {
                            stream.currentOffset += l * 2;
                        }
                    }
                } else if (opcode == 14) {
                    anInt744 = stream.readUByte();
                } else if (opcode == 15) {
                    anInt761 = stream.readUByte();
                } else if (opcode == 17) {
                    aBoolean767 = false;
                } else if (opcode == 18) {
                    aBoolean757 = false;
                } else if (opcode == 19) {
                    i = stream.readUByte();
                    
                    if (i == 1) {
                        hasActions = true;
                    }
                } else if (opcode == 21) {
                    aBoolean762 = true;
                } else if (opcode == 22) {
                    aBoolean769 = true;
                } else if (opcode == 23) {
                    aBoolean764 = true;
                } else if (opcode == 24) {
                    anInt781 = stream.readUShort();
                    
                    if (anInt781 == 65535) {
                        anInt781 = -1;
                    }
                } else if (opcode == 28) {
                    anInt775 = stream.readUByte();
                } else if (opcode == 29) {
                    aByte737 = stream.readByte();
                } else if (opcode == 39) {
                    aByte742 = stream.readByte();
                } else if (opcode >= 30 && opcode < 39) {
                    if (actions == null) {
                        actions = new String[5];
                    }
                    actions[opcode - 30] = stream.readString();
                    
                    if (actions[opcode - 30].equalsIgnoreCase("hidden")) {
                        actions[opcode - 30] = null;
                    }
                } else if (opcode == 40) {
                    int len = stream.readUByte();
                    modifiedModelColors = new int[len];
                    originalModelColors = new int[len];
                    
                    for (int colourIndex = 0; colourIndex < len; colourIndex++) {
                        modifiedModelColors[colourIndex] = stream.readUShort();
                        originalModelColors[colourIndex] = stream.readUShort();
                    }
                } else if (opcode == 60) {
                    anInt746 = stream.readUShort();
                } else if (opcode == 62) {
                    aBoolean751 = true;
                } else if (opcode == 64) {
                    aBoolean779 = false;
                } else if (opcode == 65) {
                    anInt748 = stream.readUShort();
                } else if (opcode == 66) {
                    anInt772 = stream.readUShort();
                } else if (opcode == 67) {
                    anInt740 = stream.readUShort();
                } else if (opcode == 68) {
                    anInt758 = stream.readUShort();
                } else if (opcode == 69) {
                    anInt768 = stream.readUByte();
                } else if (opcode == 70) {
                    anInt738 = stream.readShort();
                } else if (opcode == 71) {
                    anInt745 = stream.readShort();
                } else if (opcode == 72) {
                    anInt783 = stream.readShort();
                } else if (opcode == 73) {
                    aBoolean736 = true;
                } else if (opcode == 74) {
                    aBoolean766 = true;
                } else {
                    if (opcode != 75) {
                        continue;
                    }
                    anInt760 = stream.readUByte();
                }
                continue label0;
            } while (opcode != 77);
            varBitId = stream.readUShort();
            
            if (varBitId == 65535) {
                varBitId = -1;
            }
            clientSettingId = stream.readUShort();
            
            if (clientSettingId == 65535) {
                clientSettingId = -1;
            }
            int j1 = stream.readUByte();
            childrenIDs = new int[j1 + 1];
            
            for (int j2 = 0; j2 <= j1; j2++) {
                childrenIDs[j2] = stream.readUShort();
                
                if (childrenIDs[j2] == 65535) {
                    childrenIDs[j2] = -1;
                }
            }
        } while (true);
        
        if (i == -1) {
            hasActions = anIntArray773 != null && (anIntArray776 == null || anIntArray776[0] == 10);
            
            if (actions != null) {
                hasActions = true;
            }
        }
        
        if (aBoolean766) {
            aBoolean767 = false;
            aBoolean757 = false;
        }
        
        if (anInt760 == -1) {
            anInt760 = aBoolean767 ? 1 : 0;
        }
    }
}
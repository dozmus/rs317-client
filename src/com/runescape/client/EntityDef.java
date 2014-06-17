package com.runescape.client;

import com.runescape.client.io.Stream;
import com.runescape.client.io.StreamLoader;

public final class EntityDef {

    private static int entityId;
    private static Stream stream;
    private static int[] streamIndices;
    private static EntityDef[] cache;
    public static Client clientInstance;
    public static MRUNodes mruNodes = new MRUNodes(30);

    public static EntityDef forID(int id) {
        for (int i = 0; i < 20; i++) {
            if (cache[i].type == (long) id) {
                return cache[i];
            }
        }
        entityId = (entityId + 1) % 20;
        EntityDef entityDef = cache[entityId] = new EntityDef();
        stream.currentOffset = streamIndices[id];
        entityDef.type = id;
        entityDef.readValues(stream);
        return entityDef;
    }

    public static void unpackConfig(StreamLoader streamLoader) {
        stream = new Stream(streamLoader.getDataForName("npc.dat"));
        Stream stream2 = new Stream(streamLoader.getDataForName("npc.idx"));
        int totalNPCs = stream2.readUShort();
        streamIndices = new int[totalNPCs];
        int i = 2;
        
        for (int j = 0; j < totalNPCs; j++) {
            streamIndices[j] = i;
            i += stream2.readUShort();
        }
        cache = new EntityDef[20];
        
        for (int k = 0; k < 20; k++) {
            cache[k] = new EntityDef();
        }
    }

    public static void nullLoader() {
        mruNodes = null;
        streamIndices = null;
        cache = null;
        stream = null;
    }

    public int anInt55;
    private int anInt57;
    public int anInt58;
    private int anInt59;
    public int combatLevel;
    private final int anInt64;
    public String name;
    public String actions[];
    public int anInt67;
    public byte aByte68;
    private int[] anIntArray70;
    private int[] anIntArray73;
    public int anInt75;
    private int[] anIntArray76;
    public int anInt77;
    public long type;
    public int anInt79;
    public int anInt83;
    public boolean aBoolean84;
    private int anInt85;
    private int anInt86;
    public boolean aBoolean87;
    public int childrenIDs[];
    public byte description[];
    private int anInt91;
    private int anInt92;
    public boolean aBoolean93;
    private int[] anIntArray94;

    private EntityDef() {
        anInt55 = -1;
        anInt57 = -1;
        anInt58 = -1;
        anInt59 = -1;
        combatLevel = -1;
        anInt64 = 1834;
        anInt67 = -1;
        aByte68 = 1;
        anInt75 = -1;
        anInt77 = -1;
        type = -1L;
        anInt79 = 32;
        anInt83 = -1;
        aBoolean84 = true;
        anInt86 = 128;
        aBoolean87 = true;
        anInt91 = 128;
        aBoolean93 = false;
    }

    public Model method160() {
        if (childrenIDs != null) {
            EntityDef entityDef = method161();
            
            if (entityDef == null) {
                return null;
            } else {
                return entityDef.method160();
            }
        }
        if (anIntArray73 == null) {
            return null;
        }
        boolean flag1 = false;
        
        for (int i = 0; i < anIntArray73.length; i++) {
            if (!Model.method463(anIntArray73[i])) {
                flag1 = true;
            }
        }

        if (flag1) {
            return null;
        }
        Model aclass30_sub2_sub4_sub6s[] = new Model[anIntArray73.length];
        
        for (int j = 0; j < anIntArray73.length; j++) {
            aclass30_sub2_sub4_sub6s[j] = Model.method462(anIntArray73[j]);
        }
        Model model;
        
        if (aclass30_sub2_sub4_sub6s.length == 1) {
            model = aclass30_sub2_sub4_sub6s[0];
        } else {
            model = new Model(aclass30_sub2_sub4_sub6s.length, aclass30_sub2_sub4_sub6s);
        }
        
        if (anIntArray76 != null) {
            for (int k = 0; k < anIntArray76.length; k++) {
                model.method476(anIntArray76[k], anIntArray70[k]);
            }
        }
        return model;
    }

    public EntityDef method161() {
        int j = -1;
        
        if (anInt57 != -1) {
            VarBit varBit = VarBit.cache[anInt57];
            int k = varBit.anInt648;
            int l = varBit.anInt649;
            int i1 = varBit.anInt650;
            int j1 = Client.anIntArray1232[i1 - l];
            j = clientInstance.currentButtonState[k] >> l & j1;
        } else if (anInt59 != -1) {
            j = clientInstance.currentButtonState[anInt59];
        }
        
        if (j < 0 || j >= childrenIDs.length || childrenIDs[j] == -1) {
            return null;
        } else {
            return forID(childrenIDs[j]);
        }
    }

    public Model method164(int j, int k, int ai[]) {
        if (childrenIDs != null) {
            EntityDef entityDef = method161();
            
            if (entityDef == null) {
                return null;
            } else {
                return entityDef.method164(j, k, ai);
            }
        }
        Model model = (Model) mruNodes.insertFromCache(type);
        
        if (model == null) {
            boolean flag = false;
            
            for (int i1 = 0; i1 < anIntArray94.length; i1++) {
                if (!Model.method463(anIntArray94[i1])) {
                    flag = true;
                }
            }

            if (flag) {
                return null;
            }
            Model aclass30_sub2_sub4_sub6s[] = new Model[anIntArray94.length];
            
            for (int j1 = 0; j1 < anIntArray94.length; j1++) {
                aclass30_sub2_sub4_sub6s[j1] = Model.method462(anIntArray94[j1]);
            }

            if (aclass30_sub2_sub4_sub6s.length == 1) {
                model = aclass30_sub2_sub4_sub6s[0];
            } else {
                model = new Model(aclass30_sub2_sub4_sub6s.length, aclass30_sub2_sub4_sub6s);
            }
            
            if (anIntArray76 != null) {
                for (int k1 = 0; k1 < anIntArray76.length; k1++) {
                    model.method476(anIntArray76[k1], anIntArray70[k1]);
                }
            }
            model.method469();
            model.method479(64 + anInt85, 850 + anInt92, -30, -50, -30, true);
            mruNodes.removeFromCache(model, type);
        }
        Model model_1 = Model.aModel_1621;
        model_1.method464(model, Class36.method532(k) & Class36.method532(j));
        
        if (k != -1 && j != -1) {
            model_1.method471(ai, j, k);
        } else if (k != -1) {
            model_1.method470(k);
        }
        
        if (anInt91 != 128 || anInt86 != 128) {
            model_1.method478(anInt91, anInt91, anInt86);
        }
        model_1.method466();
        model_1.anIntArrayArray1658 = null;
        model_1.anIntArrayArray1657 = null;
        
        if (aByte68 == 1) {
            model_1.aBoolean1659 = true;
        }
        return model_1;
    }

    private void readValues(Stream stream) {
        do {
            int i = stream.readUByte();
            
            if (i == 0) {
                return;
            }
            
            if (i == 1) {
                int j = stream.readUByte();
                anIntArray94 = new int[j];
                
                for (int j1 = 0; j1 < j; j1++) {
                    anIntArray94[j1] = stream.readUShort();
                }
            } else if (i == 2) {
                name = stream.readString();
            } else if (i == 3) {
                description = stream.readBytes();
            } else if (i == 12) {
                aByte68 = stream.readByte();
            } else if (i == 13) {
                anInt77 = stream.readUShort();
            } else if (i == 14) {
                anInt67 = stream.readUShort();
            } else if (i == 17) {
                anInt67 = stream.readUShort();
                anInt58 = stream.readUShort();
                anInt83 = stream.readUShort();
                anInt55 = stream.readUShort();
            } else if (i >= 30 && i < 40) {
                if (actions == null) {
                    actions = new String[5];
                }
                actions[i - 30] = stream.readString();
                
                if (actions[i - 30].equalsIgnoreCase("hidden")) {
                    actions[i - 30] = null;
                }
            } else if (i == 40) {
                int k = stream.readUByte();
                anIntArray76 = new int[k];
                anIntArray70 = new int[k];
                
                for (int k1 = 0; k1 < k; k1++) {
                    anIntArray76[k1] = stream.readUShort();
                    anIntArray70[k1] = stream.readUShort();
                }

            } else if (i == 60) {
                int l = stream.readUByte();
                anIntArray73 = new int[l];
                
                for (int l1 = 0; l1 < l; l1++) {
                    anIntArray73[l1] = stream.readUShort();
                }
            } else if (i == 90) {
                stream.readUShort();
            } else if (i == 91) {
                stream.readUShort();
            } else if (i == 92) {
                stream.readUShort();
            } else if (i == 93) {
                aBoolean87 = false;
            } else if (i == 95) {
                combatLevel = stream.readUShort();
            } else if (i == 97) {
                anInt91 = stream.readUShort();
            } else if (i == 98) {
                anInt86 = stream.readUShort();
            } else if (i == 99) {
                aBoolean93 = true;
            } else if (i == 100) {
                anInt85 = stream.readByte();
            } else if (i == 101) {
                anInt92 = stream.readByte() * 5;
            } else if (i == 102) {
                anInt75 = stream.readUShort();
            } else if (i == 103) {
                anInt79 = stream.readUShort();
            } else if (i == 106) {
                anInt57 = stream.readUShort();
                
                if (anInt57 == 65535) {
                    anInt57 = -1;
                }
                anInt59 = stream.readUShort();
                
                if (anInt59 == 65535) {
                    anInt59 = -1;
                }
                int i1 = stream.readUByte();
                childrenIDs = new int[i1 + 1];
                
                for (int i2 = 0; i2 <= i1; i2++) {
                    childrenIDs[i2] = stream.readUShort();
                    
                    if (childrenIDs[i2] == 65535) {
                        childrenIDs[i2] = -1;
                    }
                }
            } else if (i == 107) {
                aBoolean84 = false;
            }
        } while (true);
    }

}

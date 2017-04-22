package com.runescape.client;

import com.runescape.client.io.Stream;
import com.runescape.client.io.StreamLoader;

public final class ItemDef {

    static MRUNodes mruNodes1 = new MRUNodes(100);
    public static MRUNodes mruNodes2 = new MRUNodes(50);
    private static ItemDef[] cache;
    private static int cacheIndex;
    public static boolean isMembers = true;
    private static Stream stream;
    private static int[] streamIndices;
    public static int totalItems;

    public static void nullLoader() {
        mruNodes2 = null;
        mruNodes1 = null;
        streamIndices = null;
        cache = null;
        stream = null;
    }

    public static void unpackConfig(StreamLoader streamLoader) {
        stream = new Stream(streamLoader.getDataForName("obj.dat"));
        Stream stream = new Stream(streamLoader.getDataForName("obj.idx"));
        totalItems = stream.readUShort();
        streamIndices = new int[totalItems];
        int i = 2;
        
        for (int j = 0; j < totalItems; j++) {
            streamIndices[j] = i;
            i += stream.readUShort();
        }

        cache = new ItemDef[10];
        
        for (int k = 0; k < 10; k++) {
            cache[k] = new ItemDef();
        }
    }

    public static ItemDef forID(int i) {
        for (int j = 0; j < 10; j++) {
            if (cache[j].id == i) {
                return cache[j];
            }
        }
        cacheIndex = (cacheIndex + 1) % 10;
        ItemDef itemDef = cache[cacheIndex];
        stream.currentOffset = streamIndices[i];
        itemDef.id = i;
        itemDef.setDefaults();
        itemDef.readValues(stream);
        
        if (itemDef.certTemplateID != -1) {
            itemDef.toNote();
        }
        
        if (!isMembers && itemDef.membersObject) {
            itemDef.name = "Members Object";
            itemDef.description = "Login to a members' server to use this object.".getBytes();
            itemDef.groundActions = null;
            itemDef.actions = null;
            itemDef.team = 0;
        }
        return itemDef;
    }

    public static Sprite getSprite(int id, int j, int k) {
        if (k == 0) {
            Sprite sprite = (Sprite) mruNodes1.insertFromCache(id);
            
            if (sprite != null && sprite.anInt1445 != j && sprite.anInt1445 != -1) {
                sprite.unlink();
                sprite = null;
            }
            
            if (sprite != null) {
                return sprite;
            }
        }
        ItemDef itemDef = forID(id);
        
        if (itemDef.stackIDs == null) {
            j = -1;
        }
        
        if (j > 1) {
            int tmpId = -1;
            
            for (int slotId = 0; slotId < 10; slotId++) {
                if (j >= itemDef.stackAmounts[slotId] && itemDef.stackAmounts[slotId] != 0) {
                    tmpId = itemDef.stackIDs[slotId];
                }
            }

            if (tmpId != -1) {
                itemDef = forID(tmpId);
            }
        }
        Model model = itemDef.getRotatedModel(1);
        
        if (model == null) {
            return null;
        }
        Sprite sprite = null;
        
        if (itemDef.certTemplateID != -1) {
            sprite = getSprite(itemDef.certID, 10, -1);
            
            if (sprite == null) {
                return null;
            }
        }
        Sprite sprite2 = new Sprite(32, 32);
        int k1 = Texture.textureInt1;
        int l1 = Texture.textureInt2;
        int ai[] = Texture.anIntArray1472;
        int dAreaPixels[] = DrawingArea.pixels;
        int dAreaWidth = DrawingArea.width;
        int dAreaHeight = DrawingArea.height;
        int dAreatopX = DrawingArea.topX;
        int dAreaBottomX = DrawingArea.bottomX;
        int dAreaTopY = DrawingArea.topY;
        int dAreaBottomY = DrawingArea.bottomY;
        Texture.aBoolean1464 = false;
        DrawingArea.initDrawingArea(32, 32, sprite2.spritePixels);
        DrawingArea.method336(32, 0, 0, 0, 32);
        Texture.method364();
        int modelZoom = itemDef.modelZoom;
        
        if (k == -1) {
            modelZoom = (int) ((double) modelZoom * 1.5D);
        }
        
        if (k > 0) {
            modelZoom = (int) ((double) modelZoom * 1.04D);
        }
        int l3 = Texture.anIntArray1470[itemDef.modelRotation1] * modelZoom >> 16;
        int i4 = Texture.anIntArray1471[itemDef.modelRotation1] * modelZoom >> 16;
        model.method482(itemDef.modelRotation2, itemDef.anInt204, itemDef.modelRotation1, itemDef.modelOffset1, l3 + model.modelHeight / 2 + itemDef.modelOffset2, i4 + itemDef.modelOffset2);
        
        for (int i5 = 31; i5 >= 0; i5--) {
            for (int j4 = 31; j4 >= 0; j4--) {
                if (sprite2.spritePixels[i5 + j4 * 32] == 0) {
                    if (i5 > 0 && sprite2.spritePixels[(i5 - 1) + j4 * 32] > 1) {
                        sprite2.spritePixels[i5 + j4 * 32] = 1;
                    } else if (j4 > 0 && sprite2.spritePixels[i5 + (j4 - 1) * 32] > 1) {
                        sprite2.spritePixels[i5 + j4 * 32] = 1;
                    } else if (i5 < 31 && sprite2.spritePixels[i5 + 1 + j4 * 32] > 1) {
                        sprite2.spritePixels[i5 + j4 * 32] = 1;
                    } else if (j4 < 31 && sprite2.spritePixels[i5 + (j4 + 1) * 32] > 1) {
                        sprite2.spritePixels[i5 + j4 * 32] = 1;
                    }
                }
            }
        }

        if (k > 0) {
            for (int j5 = 31; j5 >= 0; j5--) {
                for (int k4 = 31; k4 >= 0; k4--) {
                    if (sprite2.spritePixels[j5 + k4 * 32] == 0) {
                        if (j5 > 0 && sprite2.spritePixels[(j5 - 1) + k4 * 32] == 1) {
                            sprite2.spritePixels[j5 + k4 * 32] = k;
                        } else if (k4 > 0 && sprite2.spritePixels[j5 + (k4 - 1) * 32] == 1) {
                            sprite2.spritePixels[j5 + k4 * 32] = k;
                        } else if (j5 < 31 && sprite2.spritePixels[j5 + 1 + k4 * 32] == 1) {
                            sprite2.spritePixels[j5 + k4 * 32] = k;
                        } else if (k4 < 31 && sprite2.spritePixels[j5 + (k4 + 1) * 32] == 1) {
                            sprite2.spritePixels[j5 + k4 * 32] = k;
                        }
                    }
                }
            }
        } else if (k == 0) {
            for (int k5 = 31; k5 >= 0; k5--) {
                for (int l4 = 31; l4 >= 0; l4--) {
                    if (sprite2.spritePixels[k5 + l4 * 32] == 0 && k5 > 0 && l4 > 0 && sprite2.spritePixels[(k5 - 1) + (l4 - 1) * 32] > 0) {
                        sprite2.spritePixels[k5 + l4 * 32] = 0x302020;
                    }
                }
            }
        }
        if (itemDef.certTemplateID != -1) {
            int l5 = sprite.anInt1444;
            int j6 = sprite.anInt1445;
            sprite.anInt1444 = 32;
            sprite.anInt1445 = 32;
            sprite.drawSprite(0, 0);
            sprite.anInt1444 = l5;
            sprite.anInt1445 = j6;
        }
        
        if (k == 0) {
            mruNodes1.removeFromCache(sprite2, id);
        }
        DrawingArea.initDrawingArea(dAreaHeight, dAreaWidth, dAreaPixels);
        DrawingArea.setDrawingArea(dAreaBottomY, dAreatopX, dAreaBottomX, dAreaTopY);
        Texture.textureInt1 = k1;
        Texture.textureInt2 = l1;
        Texture.anIntArray1472 = ai;
        Texture.aBoolean1464 = true;
        
        if (itemDef.stackable) {
            sprite2.anInt1444 = 33;
        } else {
            sprite2.anInt1444 = 32;
        }
        sprite2.anInt1445 = j;
        return sprite2;
    }

    private byte aByte154;
    public int value;
    private int[] modifiedModelColors;
    public int id;
    private int[] originalModelColors;
    public boolean membersObject;
    private int anInt162;
    private int certTemplateID;
    private int anInt164;
    private int anInt165;
    private int anInt166;
    private int anInt167;
    public String groundActions[];
    private int modelOffset1;
    public String name;
    private int anInt173;
    private int modelID;
    private int anInt175;
    public boolean stackable;
    public byte description[];
    private int certID;
    public int modelZoom;
    private int anInt184;
    private int anInt185;
    private int anInt188;
    public String actions[];
    public int modelRotation1;
    private int anInt191;
    private int anInt192;
    private int[] stackIDs;
    private int modelOffset2;
    private int anInt196;
    private int anInt197;
    public int modelRotation2;
    private int anInt200;
    private int[] stackAmounts;
    public int team;
    private int anInt204;
    private byte aByte205;

    private ItemDef() {
        id = -1;
    }

    public boolean method192(int j) {
        int k = anInt175;
        int l = anInt166;
        
        if (j == 1) {
            k = anInt197;
            l = anInt173;
        }
        
        if (k == -1) {
            return true;
        }
        boolean flag = true;
        
        if (!Model.method463(k)) {
            flag = false;
        }
        
        if (l != -1 && !Model.method463(l)) {
            flag = false;
        }
        return flag;
    }

    public Model method194(int j) {
        int k = anInt175;
        int l = anInt166;
        
        if (j == 1) {
            k = anInt197;
            l = anInt173;
        }
        
        if (k == -1) {
            return null;
        }
        Model model = Model.method462(k);
        
        if (l != -1) {
            Model model_1 = Model.method462(l);
            Model models[] = {
                model, model_1
            };
            model = new Model(2, models);
        }
        
        if (modifiedModelColors != null) {
            for (int i1 = 0; i1 < modifiedModelColors.length; i1++) {
                model.method476(modifiedModelColors[i1], originalModelColors[i1]);
            }
        }
        return model;
    }

    public boolean method195(int j) {
        int k = anInt165;
        int l = anInt188;
        int i1 = anInt185;
        
        if (j == 1) {
            k = anInt200;
            l = anInt164;
            i1 = anInt162;
        }
        
        if (k == -1) {
            return true;
        }
        boolean flag = true;
        
        if (!Model.method463(k)) {
            flag = false;
        }
        
        if (l != -1 && !Model.method463(l)) {
            flag = false;
        }
        
        if (i1 != -1 && !Model.method463(i1)) {
            flag = false;
        }
        return flag;
    }

    public Model method196(int i) {
        int j = anInt165;
        int k = anInt188;
        int l = anInt185;
        
        if (i == 1) {
            j = anInt200;
            k = anInt164;
            l = anInt162;
        }
        
        if (j == -1) {
            return null;
        }
        Model model = Model.method462(j);
        
        if (k != -1) {
            if (l != -1) {
                Model model_1 = Model.method462(k);
                Model model_3 = Model.method462(l);
                Model aclass30_sub2_sub4_sub6_1s[] = {
                    model, model_1, model_3
                };
                model = new Model(3, aclass30_sub2_sub4_sub6_1s);
            } else {
                Model model_2 = Model.method462(k);
                Model aclass30_sub2_sub4_sub6s[] = {
                    model, model_2
                };
                model = new Model(2, aclass30_sub2_sub4_sub6s);
            }
        }
        
        if (i == 0 && aByte205 != 0) {
            model.method475(0, aByte205, 0);
        }
        
        if (i == 1 && aByte154 != 0) {
            model.method475(0, aByte154, 0);
        }
        
        if (modifiedModelColors != null) {
            for (int i1 = 0; i1 < modifiedModelColors.length; i1++) {
                model.method476(modifiedModelColors[i1], originalModelColors[i1]);
            }
        }
        return model;
    }

    private void setDefaults() {
        modelID = 0;
        name = null;
        description = null;
        modifiedModelColors = null;
        originalModelColors = null;
        modelZoom = 2000;
        modelRotation1 = 0;
        modelRotation2 = 0;
        anInt204 = 0;
        modelOffset1 = 0;
        modelOffset2 = 0;
        stackable = false;
        value = 1;
        membersObject = false;
        groundActions = null;
        actions = null;
        anInt165 = -1;
        anInt188 = -1;
        aByte205 = 0;
        anInt200 = -1;
        anInt164 = -1;
        aByte154 = 0;
        anInt185 = -1;
        anInt162 = -1;
        anInt175 = -1;
        anInt166 = -1;
        anInt197 = -1;
        anInt173 = -1;
        stackIDs = null;
        stackAmounts = null;
        certID = -1;
        certTemplateID = -1;
        anInt167 = 128;
        anInt192 = 128;
        anInt191 = 128;
        anInt196 = 0;
        anInt184 = 0;
        team = 0;
    }

    private void toNote() {
        ItemDef regDef = forID(certTemplateID);
        modelID = regDef.modelID;
        modelZoom = regDef.modelZoom;
        modelRotation1 = regDef.modelRotation1;
        modelRotation2 = regDef.modelRotation2;
        anInt204 = regDef.anInt204;
        modelOffset1 = regDef.modelOffset1;
        modelOffset2 = regDef.modelOffset2;
        modifiedModelColors = regDef.modifiedModelColors;
        originalModelColors = regDef.originalModelColors;
        ItemDef notedDef = forID(certID);
        name = notedDef.name;
        membersObject = notedDef.membersObject;
        value = notedDef.value;
        String s = "a";
        char c = notedDef.name.charAt(0);
        
        if (c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') {
            s = "an";
        }
        description = ("Swap this note at any bank for " + s + " " + notedDef.name + ".").getBytes();
        stackable = true;
    }

    public Model getRotatedModel(int i) {
        if (stackIDs != null && i > 1) {
            int itemId = -1;
            
            for (int slotId = 0; slotId < 10; slotId++) {
                if (i >= stackAmounts[slotId] && stackAmounts[slotId] != 0) {
                    itemId = stackIDs[slotId];
                }
            }
            if (itemId != -1) {
                return forID(itemId).getRotatedModel(1);
            }
        }
        Model model = (Model) mruNodes2.insertFromCache(id);
        
        if (model != null) {
            return model;
        }
        model = Model.method462(modelID);
        
        if (model == null) {
            return null;
        }
        
        if (anInt167 != 128 || anInt192 != 128 || anInt191 != 128) {
            model.method478(anInt167, anInt191, anInt192);
        }
        
        if (modifiedModelColors != null) {
            for (int l = 0; l < modifiedModelColors.length; l++) {
                model.method476(modifiedModelColors[l], originalModelColors[l]);
            }
        }
        model.method479(64 + anInt196, 768 + anInt184, -50, -10, -50, true);
        model.aBoolean1659 = true;
        mruNodes2.removeFromCache(model, id);
        return model;
    }

    public Model method202(int i) {
        if (stackIDs != null && i > 1) {
            int j = -1;
            
            for (int k = 0; k < 10; k++) {
                if (i >= stackAmounts[k] && stackAmounts[k] != 0) {
                    j = stackIDs[k];
                }
            }

            if (j != -1) {
                return forID(j).method202(1);
            }
        }
        Model model = Model.method462(modelID);
        
        if (model == null) {
            return null;
        }
        
        if (modifiedModelColors != null) {
            for (int l = 0; l < modifiedModelColors.length; l++) {
                model.method476(modifiedModelColors[l], originalModelColors[l]);
            }
        }
        return model;
    }

    private void readValues(Stream stream) {
        do {
            int fieldId = stream.readUByte();
            
            if (fieldId == 0) {
                return;
            }
            
            if (fieldId == 1) {
                modelID = stream.readUShort();
            } else if (fieldId == 2) {
                name = stream.readString();
            } else if (fieldId == 3) {
                description = stream.readBytes();
            } else if (fieldId == 4) {
                modelZoom = stream.readUShort();
            } else if (fieldId == 5) {
                modelRotation1 = stream.readUShort();
            } else if (fieldId == 6) {
                modelRotation2 = stream.readUShort();
            } else if (fieldId == 7) {
                modelOffset1 = stream.readUShort();
                
                if (modelOffset1 > 32767) {
                    modelOffset1 -= 0x10000;
                }
            } else if (fieldId == 8) {
                modelOffset2 = stream.readUShort();
                
                if (modelOffset2 > 32767) {
                    modelOffset2 -= 0x10000;
                }
            } else if (fieldId == 10) {
                stream.readUShort();
            } else if (fieldId == 11) {
                stackable = true;
            } else if (fieldId == 12) {
                value = stream.readUInt();
            } else if (fieldId == 16) {
                membersObject = true;
            } else if (fieldId == 23) {
                anInt165 = stream.readUShort();
                aByte205 = stream.readByte();
            } else if (fieldId == 24) {
                anInt188 = stream.readUShort();
            } else if (fieldId == 25) {
                anInt200 = stream.readUShort();
                aByte154 = stream.readByte();
            } else if (fieldId == 26) {
                anInt164 = stream.readUShort();
            } else if (fieldId >= 30 && fieldId < 35) {
                if (groundActions == null) {
                    groundActions = new String[5];
                }
                groundActions[fieldId - 30] = stream.readString();
                
                if (groundActions[fieldId - 30].equalsIgnoreCase("hidden")) {
                    groundActions[fieldId - 30] = null;
                }
            } else if (fieldId >= 35 && fieldId < 40) {
                if (actions == null) {
                    actions = new String[5];
                }
                actions[fieldId - 35] = stream.readString();
            } else if (fieldId == 40) {
                int j = stream.readUByte();
                modifiedModelColors = new int[j];
                originalModelColors = new int[j];
                
                for (int k = 0; k < j; k++) {
                    modifiedModelColors[k] = stream.readUShort();
                    originalModelColors[k] = stream.readUShort();
                }
            } else if (fieldId == 78) {
                anInt185 = stream.readUShort();
            } else if (fieldId == 79) {
                anInt162 = stream.readUShort();
            } else if (fieldId == 90) {
                anInt175 = stream.readUShort();
            } else if (fieldId == 91) {
                anInt197 = stream.readUShort();
            } else if (fieldId == 92) {
                anInt166 = stream.readUShort();
            } else if (fieldId == 93) {
                anInt173 = stream.readUShort();
            } else if (fieldId == 95) {
                anInt204 = stream.readUShort();
            } else if (fieldId == 97) {
                certID = stream.readUShort();
            } else if (fieldId == 98) {
                certTemplateID = stream.readUShort();
            } else if (fieldId >= 100 && fieldId < 110) {
                if (stackIDs == null) {
                    stackIDs = new int[10];
                    stackAmounts = new int[10];
                }
                stackIDs[fieldId - 100] = stream.readUShort();
                stackAmounts[fieldId - 100] = stream.readUShort();
            } else if (fieldId == 110) {
                anInt167 = stream.readUShort();
            } else if (fieldId == 111) {
                anInt192 = stream.readUShort();
            } else if (fieldId == 112) {
                anInt191 = stream.readUShort();
            } else if (fieldId == 113) {
                anInt196 = stream.readByte();
            } else if (fieldId == 114) {
                anInt184 = stream.readByte() * 5;
            } else if (fieldId == 115) {
                team = stream.readUByte();
            }
        } while (true);
    }
}
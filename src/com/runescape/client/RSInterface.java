package com.runescape.client;

import com.runescape.client.io.Stream;
import com.runescape.client.io.StreamLoader;

public final class RSInterface {

    public static RSInterface[] interfaceCache;
    private static MRUNodes aMRUNodes_238;
    private static final MRUNodes aMRUNodes_264 = new MRUNodes(30);

    public static void unpack(StreamLoader streamLoader, TextDrawingArea areas[], StreamLoader streamLoader2) {
        aMRUNodes_238 = new MRUNodes(50000);
        Stream stream = new Stream(streamLoader.getDataForName("data"));
        int parentId = -1;
        int j = stream.readUShort();
        interfaceCache = new RSInterface[j];

        while (stream.currentOffset < stream.buffer.length) {
            int id = stream.readUShort();

            if (id == 65535) {
                parentId = stream.readUShort();
                id = stream.readUShort();
            }
            RSInterface rsInterface = interfaceCache[id] = new RSInterface();
            rsInterface.id = id;
            rsInterface.parentID = parentId;
            rsInterface.type = stream.readUByte();
            rsInterface.atActionType = stream.readUByte();
            rsInterface.anInt214 = stream.readUShort();
            rsInterface.width = stream.readUShort();
            rsInterface.height = stream.readUShort();
            rsInterface.aByte254 = (byte) stream.readUByte();
            rsInterface.anInt230 = stream.readUByte();

            if (rsInterface.anInt230 != 0) {
                rsInterface.anInt230 = (rsInterface.anInt230 - 1 << 8) + stream.readUByte();
            } else {
                rsInterface.anInt230 = -1;
            }
            int i1 = stream.readUByte();

            if (i1 > 0) {
                rsInterface.anIntArray245 = new int[i1];
                rsInterface.anIntArray212 = new int[i1];

                for (int j1 = 0; j1 < i1; j1++) {
                    rsInterface.anIntArray245[j1] = stream.readUByte();
                    rsInterface.anIntArray212[j1] = stream.readUShort();
                }
            }
            int k1 = stream.readUByte();

            if (k1 > 0) {
                rsInterface.valueIndexArray = new int[k1][];
                for (int l1 = 0; l1 < k1; l1++) {
                    int i3 = stream.readUShort();
                    rsInterface.valueIndexArray[l1] = new int[i3];

                    for (int l4 = 0; l4 < i3; l4++) {
                        rsInterface.valueIndexArray[l1][l4] = stream.readUShort();
                    }
                }
            }

            if (rsInterface.type == 0) {
                rsInterface.maximumScrollPosition = stream.readUShort();
                rsInterface.aBoolean266 = stream.readUByte() == 1;
                int i2 = stream.readUShort();
                rsInterface.children = new int[i2];
                rsInterface.childX = new int[i2];
                rsInterface.childY = new int[i2];

                for (int j3 = 0; j3 < i2; j3++) {
                    rsInterface.children[j3] = stream.readUShort();
                    rsInterface.childX[j3] = stream.readShort();
                    rsInterface.childY[j3] = stream.readShort();
                }
            }

            if (rsInterface.type == 1) {
                stream.readUShort();
                stream.readUByte();
            }

            if (rsInterface.type == 2) {
                rsInterface.inv = new int[rsInterface.width * rsInterface.height];
                rsInterface.invStackSizes = new int[rsInterface.width * rsInterface.height];
                rsInterface.aBoolean259 = stream.readUByte() == 1;
                rsInterface.isInventoryInterface = stream.readUByte() == 1;
                rsInterface.usableItemInterface = stream.readUByte() == 1;
                rsInterface.aBoolean235 = stream.readUByte() == 1;
                rsInterface.invSpritePadX = stream.readUByte();
                rsInterface.invSpritePadY = stream.readUByte();
                rsInterface.spritesX = new int[20];
                rsInterface.spritesY = new int[20];
                rsInterface.sprites = new Sprite[20];

                for (int j2 = 0; j2 < 20; j2++) {
                    int k3 = stream.readUByte();

                    if (k3 == 1) {
                        rsInterface.spritesX[j2] = stream.readShort();
                        rsInterface.spritesY[j2] = stream.readShort();
                        String s1 = stream.readString();

                        if (streamLoader2 != null && s1.length() > 0) {
                            int i5 = s1.lastIndexOf(",");
                            rsInterface.sprites[j2] = method207(Integer.parseInt(s1.substring(i5 + 1)), streamLoader2, s1.substring(0, i5));
                        }
                    }
                }
                rsInterface.actions = new String[5];

                for (int l3 = 0; l3 < 5; l3++) {
                    rsInterface.actions[l3] = stream.readString();

                    if (rsInterface.actions[l3].length() == 0) {
                        rsInterface.actions[l3] = null;
                    }
                }
            }

            if (rsInterface.type == 3) {
                rsInterface.aBoolean227 = stream.readUByte() == 1;
            }

            if (rsInterface.type == 4 || rsInterface.type == 1) {
                rsInterface.aBoolean223 = stream.readUByte() == 1;
                int k2 = stream.readUByte();

                if (areas != null) {
                    rsInterface.textDrawingAreas = areas[k2];
                }
                rsInterface.aBoolean268 = stream.readUByte() == 1;
            }

            if (rsInterface.type == 4) {
                rsInterface.message = stream.readString();
                rsInterface.aString228 = stream.readString();
            }

            if (rsInterface.type == 1 || rsInterface.type == 3 || rsInterface.type == 4) {
                rsInterface.textColor = stream.readUInt();
            }

            if (rsInterface.type == 3 || rsInterface.type == 4) {
                rsInterface.anInt219 = stream.readUInt();
                rsInterface.anInt216 = stream.readUInt();
                rsInterface.anInt239 = stream.readUInt();
            }

            if (rsInterface.type == 5) {
                String s = stream.readString();

                if (streamLoader2 != null && s.length() > 0) {
                    int i4 = s.lastIndexOf(",");
                    rsInterface.sprite1 = method207(Integer.parseInt(s.substring(i4 + 1)), streamLoader2, s.substring(0, i4));
                }
                s = stream.readString();

                if (streamLoader2 != null && s.length() > 0) {
                    int j4 = s.lastIndexOf(",");
                    rsInterface.sprite2 = method207(Integer.parseInt(s.substring(j4 + 1)), streamLoader2, s.substring(0, j4));
                }
            }

            if (rsInterface.type == 6) {
                int l = stream.readUByte();

                if (l != 0) {
                    rsInterface.anInt233 = 1;
                    rsInterface.mediaId = (l - 1 << 8) + stream.readUByte();
                }
                l = stream.readUByte();

                if (l != 0) {
                    rsInterface.anInt255 = 1;
                    rsInterface.anInt256 = (l - 1 << 8) + stream.readUByte();
                }
                l = stream.readUByte();

                if (l != 0) {
                    rsInterface.animationId = (l - 1 << 8) + stream.readUByte();
                } else {
                    rsInterface.animationId = -1;
                }
                l = stream.readUByte();

                if (l != 0) {
                    rsInterface.anInt258 = (l - 1 << 8) + stream.readUByte();
                } else {
                    rsInterface.anInt258 = -1;
                }
                rsInterface.zoomFactor = stream.readUShort();
                rsInterface.modelRotation1 = stream.readUShort();
                rsInterface.anInt271 = stream.readUShort();
            }

            if (rsInterface.type == 7) {
                rsInterface.inv = new int[rsInterface.width * rsInterface.height];
                rsInterface.invStackSizes = new int[rsInterface.width * rsInterface.height];
                rsInterface.aBoolean223 = stream.readUByte() == 1;
                int l2 = stream.readUByte();

                if (areas != null) {
                    rsInterface.textDrawingAreas = areas[l2];
                }
                rsInterface.aBoolean268 = stream.readUByte() == 1;
                rsInterface.textColor = stream.readUInt();
                rsInterface.invSpritePadX = stream.readShort();
                rsInterface.invSpritePadY = stream.readShort();
                rsInterface.isInventoryInterface = stream.readUByte() == 1;
                rsInterface.actions = new String[5];

                for (int k4 = 0; k4 < 5; k4++) {
                    rsInterface.actions[k4] = stream.readString();

                    if (rsInterface.actions[k4].length() == 0) {
                        rsInterface.actions[k4] = null;
                    }
                }
            }

            if (rsInterface.atActionType == 2 || rsInterface.type == 2) {
                rsInterface.selectedActionName = stream.readString();
                rsInterface.spellName = stream.readString();
                rsInterface.spellUsableOn = stream.readUShort();
            }

            if (rsInterface.type == 8) {
                rsInterface.message = stream.readString();
            }

            if (rsInterface.atActionType == 1 || rsInterface.atActionType == 4 || rsInterface.atActionType == 5 || rsInterface.atActionType == 6) {
                rsInterface.tooltip = stream.readString();

                if (rsInterface.tooltip.length() == 0) {
                    if (rsInterface.atActionType == 1) {
                        rsInterface.tooltip = "Ok";
                    }

                    if (rsInterface.atActionType == 4) {
                        rsInterface.tooltip = "Select";
                    }

                    if (rsInterface.atActionType == 5) {
                        rsInterface.tooltip = "Select";
                    }

                    if (rsInterface.atActionType == 6) {
                        rsInterface.tooltip = "Continue";
                    }
                }
            }
            //aryan	Bot.notifyInterface(rsInterface);
        }
        aMRUNodes_238 = null;
    }

    private static Sprite method207(int i, StreamLoader streamLoader, String s) {
        long l = (StringHelper.method585(s) << 8) + (long) i;
        Sprite sprite = (Sprite) aMRUNodes_238.insertFromCache(l);

        if (sprite != null) {
            return sprite;
        }

        try {
            sprite = new Sprite(streamLoader, s, i);
            aMRUNodes_238.removeFromCache(sprite, l);
        } catch (Exception _ex) {
            return null;
        }
        return sprite;
    }

    public static void method208(boolean flag, Model model) {
        int i = 0;//was parameter
        int j = 5;//was parameter

        if (flag) {
            return;
        }
        aMRUNodes_264.unlinkAll();

        if (model != null && j != 4) {
            aMRUNodes_264.removeFromCache(model, (j << 16) + i);
        }
    }

    public Sprite sprite1;
    public int anInt208;
    public Sprite sprites[];
    public int anIntArray212[];
    public int anInt214;
    public int spritesX[];
    public int anInt216;
    public int atActionType;
    public String spellName;
    public int anInt219;
    public int width;
    public String tooltip;
    public String selectedActionName;
    public boolean aBoolean223;
    public int scrollPosition;
    public String actions[];
    public int valueIndexArray[][];
    public boolean aBoolean227;
    public String aString228;
    public int anInt230;
    public int invSpritePadX;
    public int textColor;
    public int anInt233;
    public int mediaId;
    public boolean aBoolean235;
    public int parentID;
    public int spellUsableOn;
    public int anInt239;
    public int children[];
    public int childX[];
    public boolean usableItemInterface;
    public TextDrawingArea textDrawingAreas;
    public int invSpritePadY;
    public int anIntArray245[];
    public int anInt246;
    public int spritesY[];
    public String message;
    public boolean isInventoryInterface;
    public int id;
    public int invStackSizes[];
    public int inv[];
    public byte aByte254;
    private int anInt255;
    private int anInt256;
    public int animationId;
    public int anInt258;
    public boolean aBoolean259;
    public Sprite sprite2;
    public int maximumScrollPosition;
    public int type;
    public int offsetX;
    public int offsetY;
    public boolean aBoolean266;
    public int height;
    public boolean aBoolean268;
    public int zoomFactor;
    public int modelRotation1;
    public int anInt271;
    public int childY[];

    public RSInterface() {
    }

    public void swapInventoryItems(int slot1, int slot2) {
        int tmp = inv[slot1];
        inv[slot1] = inv[slot2];
        inv[slot2] = tmp;

        tmp = invStackSizes[slot1];
        invStackSizes[slot1] = invStackSizes[slot2];
        invStackSizes[slot2] = tmp;
    }

    private Model method206(int type, int id) {
        Model model = (Model) aMRUNodes_264.insertFromCache((type << 16) + id);

        if (model != null) {
            return model;
        }
        if (type == 1) {
            model = Model.method462(id);
        }
        if (type == 2) {
            model = EntityDef.forID(id).method160();
        }
        if (type == 3) {
            model = Client.myPlayer.method453();
        }
        if (type == 4) {
            model = ItemDef.forID(id).method202(50);
        }
        if (type == 5) {
            model = null;
        }
        if (model != null) {
            aMRUNodes_264.removeFromCache(model, (type << 16) + id);
        }
        return model;
    }

    public Model method209(int j, int k, boolean flag) {
        Model model;

        if (flag) {
            model = method206(anInt255, anInt256);
        } else {
            model = method206(anInt233, mediaId);
        }

        if (model == null) {
            return null;
        }

        if (k == -1 && j == -1 && model.colours == null) {
            return model;
        }
        Model model_1 = new Model(true, Class36.method532(k) & Class36.method532(j), false, model);

        if (k != -1 || j != -1) {
            model_1.method469();
        }

        if (k != -1) {
            model_1.method470(k);
        }

        if (j != -1) {
            model_1.method470(j);
        }
        model_1.method479(64, 768, -50, -10, -50, true);
        return model_1;
    }
}

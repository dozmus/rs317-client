package com.runescape.client;

public final class Animable_Sub5 extends Animable {

    public static Client clientInstance;

    private int anInt1599;
    private final int[] objectChildrenIds;
    private final int varBitId;
    private final int clientSettingId;
    private final int anInt1603;
    private final int anInt1604;
    private final int anInt1605;
    private final int anInt1606;
    private Animation anim;
    private int loopCycle;
    private final int objectId;
    private final int anInt1611;
    private final int anInt1612;

    public Animable_Sub5(int objectId, int j, int k, int l, int i1, int j1,
            int k1, int animId, boolean flag) {
        this.objectId = objectId;
        anInt1611 = k;
        anInt1612 = j;
        anInt1603 = j1;
        anInt1604 = l;
        anInt1605 = i1;
        anInt1606 = k1;
        
        if (animId != -1) {
            anim = Animation.anims[animId];
            anInt1599 = 0;
            loopCycle = Client.loopCycle;
            
            if (flag && anim.anInt356 != -1) {
                anInt1599 = (int) (Math.random() * (double) anim.anInt352);
                loopCycle -= (int) (Math.random() * (double) anim.method258(anInt1599));
            }
        }
        ObjectDef objDef = ObjectDef.forID(objectId);
        varBitId = objDef.varBitId;
        clientSettingId = objDef.anInt749;
        objectChildrenIds = objDef.childrenIDs;
    }

    public Model getRotatedModel() {
        int j = -1;
        
        if (anim != null) {
            int k = Client.loopCycle - loopCycle;
            
            if (k > 100 && anim.anInt356 > 0) {
                k = 100;
            }
            
            while (k > anim.method258(anInt1599)) {
                k -= anim.method258(anInt1599);
                anInt1599++;
                
                if (anInt1599 < anim.anInt352) {
                    continue;
                }
                anInt1599 -= anim.anInt356;
                
                if (anInt1599 >= 0 && anInt1599 < anim.anInt352) {
                    continue;
                }
                anim = null;
                break;
            }
            loopCycle = Client.loopCycle - k;
            
            if (anim != null) {
                j = anim.anIntArray353[anInt1599];
            }
        }
        ObjectDef objDef;
        
        if (objectChildrenIds != null) {
            objDef = constructDefFromObjChildren();
        } else {
            objDef = ObjectDef.forID(objectId);
        }
        
        if (objDef == null) {
            return null;
        } else {
            return objDef.method578(anInt1611, anInt1612, anInt1603, anInt1604, anInt1605, anInt1606, j);
        }
    }

    private ObjectDef constructDefFromObjChildren() {
        int i = -1;
        
        if (varBitId != -1) {
            VarBit varBit = VarBit.cache[varBitId];
            int settingId = varBit.anInt648;
            int l = varBit.anInt649;
            int i1 = varBit.anInt650;
            int j1 = Client.anIntArray1232[i1 - l];
            i = clientInstance.currentUserSetting[settingId] >> l & j1;
        } else if (clientSettingId != -1) {
            i = clientInstance.currentUserSetting[clientSettingId];
        }
        
        if (i < 0 || i >= objectChildrenIds.length || objectChildrenIds[i] == -1) {
            return null;
        } else {
            return ObjectDef.forID(objectChildrenIds[i]);
        }
    }
}

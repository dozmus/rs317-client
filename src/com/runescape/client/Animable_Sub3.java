package com.runescape.client;

public final class Animable_Sub3 extends Animable {

    public final int anInt1560;
    public final int anInt1561;
    public final int anInt1562;
    public final int anInt1563;
    public final int anInt1564;
    public boolean aBoolean1567;
    private final StaticAnimation staticAnimation;
    private int anInt1569;
    private int anInt1570;

    public Animable_Sub3(int plane, int loopCycle, int l, int spotAnimId, int j1, int k1,
            int l1) {
        aBoolean1567 = false;
        staticAnimation = StaticAnimation.cache[spotAnimId];
        anInt1560 = plane;
        anInt1561 = l1;
        anInt1562 = k1;
        anInt1563 = j1;
        anInt1564 = loopCycle + l;
        aBoolean1567 = false;
    }

    public Model getRotatedModel() {
        Model model = staticAnimation.getModel();
        
        if (model == null) {
            return null;
        }
        int j = staticAnimation.anim.anIntArray353[anInt1569];
        Model model_1 = new Model(true, Class36.method532(j), false, model);
        
        if (!aBoolean1567) {
            model_1.method469();
            model_1.method470(j);
            model_1.anIntArrayArray1658 = null;
            model_1.anIntArrayArray1657 = null;
        }
        
        if (staticAnimation.anInt410 != 128 || staticAnimation.anInt411 != 128) {
            model_1.method478(staticAnimation.anInt410, staticAnimation.anInt410, staticAnimation.anInt411);
        }
        
        if (staticAnimation.anInt412 != 0) {
            if (staticAnimation.anInt412 == 90) {
                model_1.method473();
            }
            
            if (staticAnimation.anInt412 == 180) {
                model_1.method473();
                model_1.method473();
            }
            
            if (staticAnimation.anInt412 == 270) {
                model_1.method473();
                model_1.method473();
                model_1.method473();
            }
        }
        model_1.method479(64 + staticAnimation.anInt413, 850 + staticAnimation.anInt414, -30, -50, -30, true);
        return model_1;
    }

    public void method454(int i) {
        for (anInt1570 += i; anInt1570 > staticAnimation.anim.method258(anInt1569);) {
            anInt1570 -= staticAnimation.anim.method258(anInt1569) + 1;
            anInt1569++;
            
            if (anInt1569 >= staticAnimation.anim.anInt352
                   && (anInt1569 < 0 || anInt1569 >= staticAnimation.anim.anInt352)) {
                anInt1569 = 0;
                aBoolean1567 = true;
            }
        }
    }
}

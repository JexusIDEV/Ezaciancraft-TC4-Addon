package com.gabid.ezaciancraft.registry;

import com.gabid.ezaciancraft.api.aspects.ChangeableColoredAspect;
import com.gabid.ezaciancraft.api.aspects.EzacianAspect;
import thaumcraft.api.aspects.Aspect;

import static com.gabid.ezaciancraft.CoreMod.MODID;

public class EzacianCraftAspects {
    public static final Aspect TEMPUS = new EzacianAspect("tempus", 12566463, new Aspect[]{Aspect.MOTION, Aspect.VOID});
    public static final Aspect SPAZIO = new EzacianAspect("spazio", 7566195, new Aspect[]{Aspect.VOID, Aspect.ORDER});
    public static final Aspect REPLICATIO = new EzacianAspect("replicatio", 13421772, new Aspect[]{Aspect.EXCHANGE, Aspect.MAGIC});

    //and interesting way for alchemy
    public static final Aspect MAGICUS = new EzacianAspect("magicus", 7995536, new Aspect[]{Aspect.MAGIC, Aspect.TAINT}, 1);

    public static final Aspect AERUM = new EzacianAspect("aerum", 16777120, new Aspect[]{Aspect.AIR, MAGICUS}, 1);
    public static final Aspect IGNUS = new EzacianAspect("ignus", 16742430, new Aspect[]{Aspect.FIRE, MAGICUS}, 1);
    public static final Aspect AQUEUS = new EzacianAspect("aqueus", 11200255, new Aspect[]{Aspect.WATER, MAGICUS}, 1);
    public static final Aspect TERRUM = new EzacianAspect("terrum", 7848006, new Aspect[]{Aspect.EARTH, MAGICUS}, 1);
    public static final Aspect ORDOS = new EzacianAspect("ordos", 15527148, new Aspect[]{Aspect.ORDER, MAGICUS}, 1);
    public static final Aspect PERDOTOS = new EzacianAspect("perdotos", 5328976, new Aspect[]{Aspect.ENTROPY, MAGICUS}, 1);

    //the true compound needs a complex machine to produce it
    //public static final Aspect PRIMORDIUM = new ChangeableColoredAspect("primordium", 0xff0000, null, 1, ChangeableColoredAspect.SOFT_RAINBOW);

    public static void initAspects() {
    }
}

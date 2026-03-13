package com.gabid.ezaciancraft.registry;

import net.minecraft.util.ResourceLocation;
import thaumcraft.api.aspects.Aspect;

import static com.gabid.ezaciancraft.CoreMod.MODID;

public class EzacianCraftAspects {

    public static class EzacianAspect extends Aspect {
        public EzacianAspect(String tag, int color, Aspect[] components, ResourceLocation image, int blend) {
            super(tag, color, components, image, blend);
        }

        public EzacianAspect(String tag, int color, Aspect[] components) {
            this(tag, color, components, new ResourceLocation(MODID, "textures/aspects/"+tag.toLowerCase()+".png"), 1);
        }

        public EzacianAspect(String tag, int color, Aspect[] components, int blend) {
            this(tag, color, components, new ResourceLocation(MODID, "textures/aspects/"+tag.toLowerCase()+".png"), blend);
        }

        public EzacianAspect(String tag, int color, String chatcolor, int blend) {
            this(tag, color, (Aspect[])null, blend);
            this.setChatcolor(chatcolor);
        }
    }

    public static final Aspect TEMPUS = new EzacianAspect("tempus", 12566463, new Aspect[]{Aspect.MOTION, Aspect.VOID});
    public static final Aspect SPAZIO = new EzacianAspect("spazio", 7566195, new Aspect[]{Aspect.VOID, Aspect.ORDER});

    public static void initAspects() {
    }
}

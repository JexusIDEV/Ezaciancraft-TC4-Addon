package com.gabid.ezaciancraft.api.registry;

import com.gabid.ezaciancraft.api.common.items.MetalResourceItem;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCompressed;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.MapColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import static com.gabid.ezaciancraft.CoreMod.MODID;

//a helper for registering various materials with ingot, nugget, block and ore
public class ResourceMaterialBuilder {
    protected final String resourceBaseName;

    protected final Block resourceOreBlock;
    protected final Block resourceBlock;

    protected final Item resourceMetal; //uses my own class "MetalResourceItem"
    
    private final float extraHardness;
    private final float extraResistance;
    private final MapColor metalAndOreColor;
    private final CreativeTabs tabToRegister;

    public ResourceMaterialBuilder(Builder builder) {
        this.resourceBaseName = builder.resourceBaseName;
        this.extraHardness = builder.extraHardness;
        this.extraResistance = builder.extraResistance;
        this.metalAndOreColor = builder.metalAndOreColor;
        this.tabToRegister = builder.tabToRegister;

        this.resourceOreBlock = builder.resourceOreBlock != null ? builder.resourceOreBlock : new BlockOre();
        this.resourceOreBlock.setBlockName(builder.resourceBaseName+"Ore");
        this.resourceOreBlock.setBlockTextureName(new ResourceLocation(MODID, builder.resourceBaseName+"Ore").toString());
        this.resourceOreBlock.setHardness(3f + builder.extraHardness);
        this.resourceOreBlock.setResistance(5f + builder.extraResistance);
        this.resourceOreBlock.setCreativeTab(builder.tabToRegister);
        GameRegistry.registerBlock(this.resourceOreBlock, this.resourceOreBlock.getUnlocalizedName());

        this.resourceBlock = builder.resourceBlock != null ? builder.resourceBlock : new BlockCompressed(builder.metalAndOreColor);
        this.resourceBlock.setBlockName(builder.resourceBaseName+"Block");
        this.resourceBlock.setBlockTextureName(new ResourceLocation(MODID, builder.resourceBaseName+"Block").toString());
        this.resourceBlock.setHardness(5f + builder.extraHardness);
        this.resourceBlock.setResistance(10f + builder.extraResistance);
        this.resourceBlock.setStepSound(Block.soundTypeMetal);
        this.resourceBlock.setCreativeTab(builder.tabToRegister);
        GameRegistry.registerBlock(this.resourceBlock, this.resourceBlock.getUnlocalizedName());

        this.resourceMetal = builder.resourceMetal != null ? builder.resourceMetal : new MetalResourceItem(builder.resourceBaseName, builder.tabToRegister);
        GameRegistry.registerItem(this.resourceMetal, this.resourceMetal.getUnlocalizedName());
    }

    public String getResourceBaseName() {
        return this.resourceBaseName;
    }

    public Block getResourceOreBlock() {
        return this.resourceOreBlock;
    }

    public Block getResourceBlock() {
        return this.resourceBlock;
    }

    public Item getResourceMetal() {
        return this.resourceMetal;
    }

    public float getExtraHardness() {
        return this.extraHardness;
    }

    public float getExtraResistance() {
        return this.extraResistance;
    }

    public MapColor getMetalAndOreColor() {
        return this.metalAndOreColor;
    }

    public CreativeTabs getTabToRegister() {
        return this.tabToRegister;
    }

    public static ResourceMaterialBuilder createAFullSet(String _resourceBaseName, float _extraHardness, float _extraResistance, MapColor _metalAndOreColor, CreativeTabs tabToRegister) {
        return new ResourceMaterialBuilder(new Builder(_resourceBaseName, _extraHardness, _extraResistance, _metalAndOreColor, tabToRegister));
    }

    public static class Builder {
        protected final String resourceBaseName;

        protected Block resourceOreBlock;
        protected Block resourceBlock;

        protected Item resourceMetal; //uses my own class "MetalResourceItem"

        private final float extraHardness;
        private final float extraResistance;
        private final MapColor metalAndOreColor;
        private final CreativeTabs tabToRegister;

        public Builder(String _resourceBaseName, float _extraHardness, float _extraResistance, MapColor _metalAndOreColor, CreativeTabs _tabToRegister) {
            this.resourceBaseName = _resourceBaseName;
            this.extraHardness = _extraHardness;
            this.extraResistance = _extraResistance;
            this.metalAndOreColor = _metalAndOreColor;
            this.tabToRegister = _tabToRegister;
        }

        public Builder createResourceOreBlock(Block newBlock) {
            this.resourceOreBlock = newBlock;
            return this;
        }

        public Builder createResourceBlock(Block newBlock) {
            this.resourceBlock = newBlock;
            return this;
        }

        public Builder createResourceItem(MetalResourceItem newItem) {
            this.resourceMetal = newItem;
            return this;
        }

        public float getExtraHardness() {
            return this.extraHardness;
        }

        public float getExtraResistance() {
            return this.extraResistance;
        }

        public MapColor getMetalAndOreColor() {
            return this.metalAndOreColor;
        }

        public CreativeTabs getTabToRegister() {
            return this.tabToRegister;
        }
    }
}

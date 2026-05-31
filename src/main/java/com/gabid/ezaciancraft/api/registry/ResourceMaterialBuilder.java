package com.gabid.ezaciancraft.api.registry;

import com.gabid.ezaciancraft.api.common.blocks.CustomBlockOre;
import com.gabid.ezaciancraft.api.common.items.MetalResourceItem;
import com.gabid.ezaciancraft.registry.EzacianCraftResources;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCompressed;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.MapColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.StringUtils;
import thaumcraft.api.ThaumcraftApi;

import static com.gabid.ezaciancraft.CoreMod.MODID;

//a helper for registering various materials with ingot, nugget, block and ore
public class ResourceMaterialBuilder {
    protected final String resourceBaseName;

    protected final Block resourceOreBlock;
    protected final Block resourceBlock;

    protected final Item resourceMetal; //uses my own class "MetalResourceItem"

    private final float extraHardness;
    private final float extraResistance;
    private final float resourceExperience;
    private final MapColor metalAndOreColor;
    private final CreativeTabs tabToRegister;

    public ResourceMaterialBuilder(Builder builder) {
        this.resourceBaseName = builder.resourceBaseName;
        this.extraHardness = builder.extraHardness;
        this.extraResistance = builder.extraResistance;
        this.resourceExperience = builder.resourceExperience;
        this.metalAndOreColor = builder.metalAndOreColor;
        this.tabToRegister = builder.tabToRegister;

        this.resourceOreBlock = builder.resourceOreBlock != null ? builder.resourceOreBlock : new CustomBlockOre(this.resourceExperience);
        this.resourceOreBlock.setBlockName(builder.resourceBaseName + "Ore");
        this.resourceOreBlock.setBlockTextureName(new ResourceLocation(MODID, builder.resourceBaseName + "Ore").toString());
        this.resourceOreBlock.setHardness(3f + builder.extraHardness);
        this.resourceOreBlock.setResistance(5f + builder.extraResistance);
        this.resourceOreBlock.setCreativeTab(builder.tabToRegister);
        GameRegistry.registerBlock(this.resourceOreBlock, this.resourceOreBlock.getUnlocalizedName());

        this.resourceBlock = builder.resourceBlock != null ? builder.resourceBlock : new BlockCompressed(builder.metalAndOreColor);
        this.resourceBlock.setBlockName(builder.resourceBaseName + "Block");
        this.resourceBlock.setBlockTextureName(new ResourceLocation(MODID, builder.resourceBaseName + "Block").toString());
        this.resourceBlock.setHardness(5f + builder.extraHardness);
        this.resourceBlock.setResistance(10f + builder.extraResistance);
        this.resourceBlock.setStepSound(Block.soundTypeMetal);
        this.resourceBlock.setCreativeTab(builder.tabToRegister);
        GameRegistry.registerBlock(this.resourceBlock, this.resourceBlock.getUnlocalizedName());

        this.resourceMetal = builder.resourceMetal != null ? builder.resourceMetal : new MetalResourceItem(builder.resourceBaseName, builder.tabToRegister);
        GameRegistry.registerItem(this.resourceMetal, this.resourceMetal.getUnlocalizedName());

        //tag registry
        OreDictionary.registerOre(this.resourceBaseName, new ItemStack(this.resourceMetal, 1, 0));
        OreDictionary.registerOre("nugget" + StringUtils.capitalize(this.resourceBaseName), new ItemStack(this.resourceMetal, 1, 1));
        OreDictionary.registerOre("cluster" + StringUtils.capitalize(this.resourceBaseName), new ItemStack(this.resourceMetal, 1, 2));
        OreDictionary.registerOre("ore" + StringUtils.capitalize(this.resourceBaseName), this.resourceOreBlock);
        OreDictionary.registerOre("block" + StringUtils.capitalize(this.resourceBaseName), this.resourceBlock);

        //*ingot to nugget
        GameRegistry.addShapelessRecipe(
                new ItemStack(this.resourceMetal, 9, 1),
                new ItemStack(this.resourceMetal, 1, 0));

        //*nuggets to ingot
        GameRegistry.addShapedRecipe(
                new ItemStack(this.resourceMetal, 1, 0),
                "###", "###", "###", '#', new ItemStack(this.resourceMetal, 1, 1));

        //*ingot to block
        GameRegistry.addShapedRecipe(
                new ItemStack(this.resourceBlock, 1),
                "###", "###", "###", '#', new ItemStack(this.resourceMetal, 1, 0));

        //*block to ingot
        GameRegistry.addShapelessRecipe(
                new ItemStack(this.resourceMetal, 9, 0),
                (new ItemStack(this.resourceBlock, 1, 0))
        );

        //normal ore smelt
        GameRegistry.addSmelting(
                new ItemStack(this.resourceOreBlock, 1),
                new ItemStack(this.resourceMetal, 1, 0),
                this.resourceExperience
        );

        //*cluster base smelt
        GameRegistry.addSmelting(
                new ItemStack(this.resourceMetal, 1, 2),
                new ItemStack(this.resourceMetal, 2, 0),
                this.resourceExperience*2
        );

        //add the nugget bonus to the cluster recipe on an infernal furnace
        ThaumcraftApi.addSmeltingBonus(new ItemStack(this.resourceMetal, 1, 2), new ItemStack(this.resourceMetal, 1, 1));
    }

    public static ResourceMaterialBuilder createAFullSet(String _resourceBaseName, float _extraHardness, float _extraResistance, float _experience,MapColor _metalAndOreColor, CreativeTabs tabToRegister) {
        return new ResourceMaterialBuilder(new Builder(_resourceBaseName, _extraHardness, _extraResistance, _experience, _metalAndOreColor, tabToRegister));
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

    public float getResourceExperience() {
        return this.resourceExperience;
    }

    public MapColor getMetalAndOreColor() {
        return this.metalAndOreColor;
    }

    public CreativeTabs getTabToRegister() {
        return this.tabToRegister;
    }

    public static class Builder {
        protected final String resourceBaseName;
        private final float extraHardness;
        private final float extraResistance;
        private final float resourceExperience;
        private final MapColor metalAndOreColor;
        private final CreativeTabs tabToRegister;
        protected Block resourceOreBlock;
        protected Block resourceBlock;
        protected Item resourceMetal; //uses my own class "MetalResourceItem"

        public Builder(String _resourceBaseName, float _extraHardness, float _experience,float _extraResistance, MapColor _metalAndOreColor, CreativeTabs _tabToRegister) {
            this.resourceBaseName = _resourceBaseName;
            this.extraHardness = _extraHardness;
            this.extraResistance = _extraResistance;
            this.resourceExperience = _experience;
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

        public float getResourceExperience() {
            return this.resourceExperience;
        }

        public MapColor getMetalAndOreColor() {
            return this.metalAndOreColor;
        }

        public CreativeTabs getTabToRegister() {
            return this.tabToRegister;
        }
    }
}

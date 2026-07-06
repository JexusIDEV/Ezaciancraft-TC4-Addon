package com.gabid.ezaciancraft.api.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import static com.gabid.ezaciancraft.CoreMod.MODID;

public class BaseGenericBlock extends Block {
    protected IIcon iconBlock;

    protected String unlocName;

    public BaseGenericBlock(Material material, String _unlocName, float blockHardness, float explosionResistance, SoundType sound, CreativeTabs tab) {
        super(material);
        this.setBlockName(_unlocName);
        this.unlocName = _unlocName;
        this.setHardness(blockHardness);
        this.setResistance(explosionResistance);
        this.setStepSound(sound);
        this.setCreativeTab(tab);
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        this.iconBlock = register.registerIcon(new ResourceLocation(MODID, this.unlocName).toString());
    }

    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return this.iconBlock;
    }
}

package com.gabid.ezaciancraft.api.common.blocks;

import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;

import java.util.List;
import java.util.Random;

import static com.gabid.ezaciancraft.CoreMod.MODID;

public class EzacianCustomBlockPlant extends BlockBush implements IGrowable {
    public final String[] plantNames;
    public final IIcon[] plantIcons;

    protected final String blockBaseName;
    protected final EnumPlantType plantType;

    public EzacianCustomBlockPlant(String _blockBaseName, EnumPlantType type, String[] plantNamesToRegister, CreativeTabs tabToRegister) {
        super(Material.plants);
        this.setStepSound(EzacianCustomBlockPlant.soundTypeGrass);
        this.blockBaseName = _blockBaseName;
        this.setBlockName(_blockBaseName);
        this.plantNames = plantNamesToRegister;
        this.plantIcons = new IIcon[plantNames.length];
        this.plantType = type;
        this.setTickRandomly(true);
        this.setCreativeTab(tabToRegister);
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        for (int i=0 ; i < this.plantNames.length; i++) {
            this.plantIcons[i] = register.registerIcon(new ResourceLocation(MODID, this.blockBaseName+"_"+this.plantNames[i] + "Plant").toString());
        }
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return this.plantIcons[meta];
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
        for(int i=0; i < this.plantNames.length; i++) {
            ItemStack plantToAdd = new ItemStack(this, 1, i);
            list.add(plantToAdd);
        }
    }

    @Override
    public int getDamageValue(World world, int x, int y, int z) {
        return world.getBlockMetadata(x,y,z);
    }

    @Override
    public int damageDropped(int meta) {
        return meta;
    }

    //canFertilize
    @Override
    public boolean func_149851_a(World world, int x, int y, int z, boolean flag) {
        return true;
    }

    //shouldFertilize
    @Override
    public boolean func_149852_a(World world, Random rand, int x, int y, int z) {
        return true;
    }

    //fertilize
    @Override
    public void func_149853_b(World world, Random random, int x, int y, int z) {

    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
        return this.plantType;
    }
}

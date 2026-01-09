package com.gabid.ezaciancraft.api.common.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import java.util.List;

import static com.gabid.ezaciancraft.CoreMod.MODID;

public class MetalResourceItem extends Item {

    public IIcon[] metalIcons = new IIcon[2];
    public String baseName;

    public MetalResourceItem(String name, CreativeTabs tabToRegister) {
        this.setUnlocalizedName(name);
        this.setMaxDamage(0);
        this.setMaxStackSize(64);
        this.setHasSubtypes(true);
        this.setCreativeTab(tabToRegister);
        this.baseName = name;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister register) {
        this.metalIcons[0] = register.registerIcon(new ResourceLocation(MODID, this.baseName).toString());
        this.metalIcons[1] = register.registerIcon(new ResourceLocation(MODID, this.baseName+"Nugget").toString());
    }

    @Override
    public IIcon getIconFromDamage(int meta) {
        return this.metalIcons[meta];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        list.add(new ItemStack(this, 1, 0));
        list.add(new ItemStack(this, 1, 1));
    }

    @Override
    public String getUnlocalizedNameInefficiently(ItemStack stack) {
        return super.getUnlocalizedNameInefficiently(stack) + "." + stack.getItemDamage();
    }
}

package com.gabid.ezaciancraft.common.items;

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
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_EZACIAN_PLATES_BASENAME;
import static com.gabid.ezaciancraft.registry.EzacianCraftCreativeTab.EZACIANCRAFT_RESOURCES_TAB;

public class ItemEzacianPlates extends Item {
    public String[] plateNames = {"magicAlloy"};
    public IIcon[] plateIcons;

    public ItemEzacianPlates() {
        this.setMaxDamage(0);
        this.setMaxStackSize(64);
        this.setHasSubtypes(true);
        this.setCreativeTab(EZACIANCRAFT_RESOURCES_TAB);
        this.setUnlocalizedName(UNLOCALE_EZACIAN_PLATES_BASENAME);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister register) {
        this.plateIcons = new IIcon[plateNames.length];
        for(int i=0; i < this.plateNames.length; i++) {
            plateIcons[i] = register.registerIcon(new ResourceLocation(MODID, this.plateNames[i]+"Plate").toString());
        }
    }

    @Override
    public IIcon getIconFromDamage(int meta) {
        return this.plateIcons[meta];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for(int i=0; i < this.plateNames.length; i++) {
            list.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public String getUnlocalizedNameInefficiently(ItemStack stack) {
        return super.getUnlocalizedNameInefficiently(stack) + "." + stack.getItemDamage();
    }
}

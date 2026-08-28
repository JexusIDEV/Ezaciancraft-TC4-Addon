package com.gabid.ezaciancraft.api.common.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import thaumcraft.api.IVisDiscountGear;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.items.armor.ItemFortressArmor;

import java.util.List;

public class ItemCustomFortressArmor extends ItemFortressArmor implements IVisDiscountGear {

    protected final String materialName;
    protected final String modid;

    public ItemCustomFortressArmor(ArmorMaterial enumarmormaterial, int armorType, int render, String _materialName, String _modid) {
        super(enumarmormaterial, armorType, render);
        this.materialName = _materialName;
        this.modid = _modid;
    }

    @Override
    public void registerIcons(IIconRegister ir) {
        this.iconHelm = ir.registerIcon(new ResourceLocation(this.modid, this.materialName+"FortressHelm").toString());
        this.iconChest = ir.registerIcon(new ResourceLocation(this.modid, this.materialName+"FortressChest").toString());
        this.iconLegs = ir.registerIcon(new ResourceLocation(this.modid,this.materialName+"FortressLeggings").toString());
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        return this.modid+":textures/models/"+this.materialName+"_fortress_armor.png";
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag) {
        if(this.getVisDiscount(stack, player, null) > 0) {
            list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("tc.visdiscount") + ": " + this.getVisDiscount(stack, player, null) + "%");
        }
        super.addInformation(stack, player, list, flag);
    }

    @Override
    public int getVisDiscount(ItemStack itemStack, EntityPlayer entityPlayer, Aspect aspect) {
        return 0;
    }
}

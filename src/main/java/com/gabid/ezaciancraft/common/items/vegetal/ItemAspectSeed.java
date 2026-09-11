package com.gabid.ezaciancraft.common.items.vegetal;

import com.gabid.ezaciancraft.common.blocks.vegetal.TileEntityAspectCrop;
import com.gabid.ezaciancraft.registry.EzacianCraftBlocks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IEssentiaContainerItem;
import thaumcraft.common.Thaumcraft;

import java.util.List;

import static com.gabid.ezaciancraft.CoreMod.MODID;
import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_ASPECT_SEED_BASENAME;
import static com.gabid.ezaciancraft.registry.EzacianCraftCreativeTab.EZACIANCRAFT_RESOURCES_TAB;

public class ItemAspectSeed extends ItemSeeds implements IEssentiaContainerItem {
    protected IIcon[] seedIcons = new IIcon[2];

    public ItemAspectSeed() {
        super(EzacianCraftBlocks.aspectCrop, Blocks.farmland);
        this.setUnlocalizedName(UNLOCALE_ASPECT_SEED_BASENAME);
        this.setCreativeTab(EZACIANCRAFT_RESOURCES_TAB);
        this.setMaxDamage(64);
        this.setMaxDamage(0);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float a, float b, float c) {
        if (side != 1) {
            return false;
        } else if (player.canPlayerEdit(x, y, z, side, stack) && player.canPlayerEdit(x, y + 1, z, side, stack)) {
            if (world.getBlock(x, y, z).canSustainPlant(world, x, y, z, ForgeDirection.UP, this) && world.isAirBlock(x, y + 1, z))
            {
                world.setBlock(x, y + 1, z, EzacianCraftBlocks.aspectCrop, 0, 3);
                TileEntity te = world.getTileEntity(x, y+1, z);

                if (te instanceof TileEntityAspectCrop) {
                    AspectList asp = new AspectList().add(this.getAspects(stack).getAspects()[0], 1);
                    ((TileEntityAspectCrop) te).setCropAspect(asp);
                }
                --stack.stackSize;
                return true;
            }
            else
            {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer plyr, List list, boolean flag) {
        super.addInformation(stack, plyr, list, flag);
        if(this.getAspects(stack) != null) {
            if (Thaumcraft.proxy.playerKnowledge.hasDiscoveredAspect(plyr.getDisplayName(), this.getAspects(stack).getAspects()[0])) {
                list.add(this.getAspects(stack).getAspects()[0].getName());
            } else {
                list.add(StatCollector.translateToLocal("tc.aspect.unknown"));
            }
        }
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        this.seedIcons[0] = iconRegister.registerIcon(new ResourceLocation(MODID, "aspect_seed_base").toString());
        this.seedIcons[1] = iconRegister.registerIcon(new ResourceLocation(MODID, "aspect_seed_overlay").toString());
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIconFromDamageForRenderPass(int meta, int pass) {
        return this.seedIcons[pass];
    }

    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public int getRenderPasses(int metadata) {
        return 2;
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int pass) {
        if (pass == 1) {
            return this.getAspectSeedColor(stack);
        }
        return 0xFFFFFF;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tabs, List list) {
        for (Aspect asp : Aspect.aspects.values()) {
            ItemStack dummyStack = new ItemStack(item);
            this.setAspects(dummyStack, new AspectList().add(asp, 2));
            list.add(dummyStack);
        }
    }

    public int getAspectSeedColor(ItemStack stackSeed) {
        if(stackSeed != null) {
            if(this.getAspects(stackSeed) != null) {
                return this.getAspects(stackSeed).getAspects()[0].getColor();
            }
        }
        return 0xffffff;
    }

    @Override
    public AspectList getAspects(ItemStack itemStack) {
        AspectList aspects = new AspectList();
        if (!itemStack.hasTagCompound()) {
            itemStack.setTagCompound(new NBTTagCompound());
        }
        aspects.readFromNBT(itemStack.getTagCompound());
        return aspects.size() > 0 ? aspects : null;
    }

    @Override
    public void setAspects(ItemStack itemStack, AspectList aspectList) {
        if(!itemStack.hasTagCompound()) {
            itemStack.setTagCompound(new NBTTagCompound());
        }
        aspectList.writeToNBT(itemStack.getTagCompound());
    }
}

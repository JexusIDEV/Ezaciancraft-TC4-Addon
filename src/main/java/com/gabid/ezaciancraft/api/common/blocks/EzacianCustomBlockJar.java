package com.gabid.ezaciancraft.api.common.blocks;

import com.gabid.ezaciancraft.api.common.blocks.tileentity.EzacianCustomJarFillableTE;
import com.gabid.ezaciancraft.api.common.blocks.tileentity.EzacianCustomJarFillableVoidTE;
import com.gabid.ezaciancraft.api.common.items.EzacianCustomItemJarFilled;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IEssentiaContainerItem;
import thaumcraft.common.blocks.BlockJar;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.ItemEssence;

import java.util.ArrayList;
import java.util.List;

public class EzacianCustomBlockJar extends BlockJar {
    protected int newEssentiaMaxAmount;
    private final String materialNameJar;
    private final String modidRoot;

    public EzacianCustomBlockJar(int _newEssentiaMaxAmount, String _materialNameJar, String _modidRoot, CreativeTabs tabToRegister) {
        this.newEssentiaMaxAmount = _newEssentiaMaxAmount;
        this.materialNameJar = _materialNameJar;
        this.modidRoot = _modidRoot;
        this.setCreativeTab(tabToRegister);
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        if(metadata == 0) {
            return new EzacianCustomJarFillableTE();
        } else {
            return new EzacianCustomJarFillableVoidTE();
        }
    }

    @Override
    public void registerBlockIcons(IIconRegister ir) {
        this.iconJarSide = ir.registerIcon(new ResourceLocation(this.modidRoot, this.materialNameJar+"_jar_side").toString());
        this.iconJarTop = ir.registerIcon(new ResourceLocation(this.modidRoot, this.materialNameJar+"_jar_top").toString());
        this.iconJarTopVoid = ir.registerIcon(new ResourceLocation(this.modidRoot, this.materialNameJar+"_jar_top_void").toString());
        this.iconJarSideVoid = ir.registerIcon(new ResourceLocation(this.modidRoot, this.materialNameJar+"_jar_side_void").toString());
        this.iconJarBottom = ir.registerIcon(new ResourceLocation(this.modidRoot, this.materialNameJar+"_jar_bottom").toString());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void getSubBlocks(Item item, CreativeTabs tabs, List list) {
        list.add(new ItemStack(item, 1, 0));
        list.add(new ItemStack(item, 1, 3));
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float a, float b, float c) {
        super.onBlockActivated(world, x, y, z, player, side, a, b, c);
        ItemStack heldItem = player.getHeldItem();
        TileEntity te = world.getTileEntity(x, y, z);

        if(te instanceof EzacianCustomJarFillableTE) {
            EzacianCustomJarFillableTE jarTE = (EzacianCustomJarFillableTE) te;

            if(player.isSneaking() && jarTE.aspectFilter != null && side == jarTE.facing) {
                jarTE.aspectFilter = null;
                if (world.isRemote) {
                    world.playSound(((float)x + 0.5F), ((float)y + 0.5F), ((float)z + 0.5F), "thaumcraft:page", 1.0F, 1.0F, false);
                } else {
                    ForgeDirection fd = ForgeDirection.getOrientation(side);
                    world.spawnEntityInWorld(new EntityItem(world, ((float)x + 0.5F + (float)fd.offsetX / 3.0F), ((float)y + 0.5F), ((float)z + 0.5F + (float)fd.offsetZ / 3.0F), new ItemStack(ConfigItems.itemResource, 1, 13)));
                }
            } else if (heldItem != null && jarTE.aspectFilter == null && heldItem.getItem() == ConfigItems.itemResource && heldItem.getItemDamage() == 13) {
                if (jarTE.amount == 0 && ((IEssentiaContainerItem)(heldItem.getItem())).getAspects(heldItem) == null) {
                    return false;
                }

                if (jarTE.amount == 0 && ((IEssentiaContainerItem)(heldItem.getItem())).getAspects(heldItem) != null) {
                    jarTE.aspect = ((IEssentiaContainerItem)(heldItem.getItem())).getAspects(heldItem).getAspects()[0];
                }

                --heldItem.stackSize;
                this.onBlockPlacedBy(world, x, y, z, player, null);
                jarTE.aspectFilter = jarTE.aspect;
                if (world.isRemote) {
                    world.playSound(((float)x + 0.5F), ((float)y + 0.5F), ((float)z + 0.5F), "thaumcraft:jar", 0.4F, 1.0F, false);
                }
            } else if (player.isSneaking() && heldItem == null) {
                if(jarTE.amount > 0) {
                    jarTE.amount = 0;
                    jarTE.setMaxAmount(this.getNewEssentiaMaxAmount());
                    if (jarTE.aspectFilter == null) {
                        jarTE.aspect = null;
                    }

                    if (world.isRemote) {
                        world.playSound(((float) x + 0.5F), ((float) y + 0.5F), ((float) z + 0.5F), "thaumcraft:jar", 0.4F, 1.0F, false);
                        world.playSound(((float) x + 0.5F), ((float) y + 0.5F), ((float) z + 0.5F), "game.neutral.swim", 0.5F, 1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.3F, false);
                    }
                } else {
                    return false;
                }
            } else if(heldItem != null && heldItem.getItem() == ConfigItems.itemEssence) {
                return this.dealWithPhial(world, player, x, y, z, jarTE);
            } else {
                return false;
            }
        }
        return true;
    }

    public ArrayList<ItemStack> setupDrops(EzacianCustomJarFillableTE jarTE, ArrayList<ItemStack> drops, Item filledJar) {
        ItemStack drop = null;
        if (jarTE.amount <= 0 && jarTE.aspectFilter == null) {
            drop = new ItemStack(this);
        } else if(jarTE.amount > 0) {
            drop = new ItemStack(filledJar);
            ((EzacianCustomItemJarFilled)drop.getItem()).setAspects(drop, (new AspectList()).add(jarTE.aspect, jarTE.amount));
        }

        if (jarTE.aspectFilter != null && drop != null) {
            if (!drop.hasTagCompound()) {
                drop.setTagCompound(new NBTTagCompound());
            }
            drop.stackTagCompound.setString("AspectFilter", jarTE.aspectFilter.getTag());
        }

        if((jarTE instanceof EzacianCustomJarFillableVoidTE || jarTE.getBlockMetadata() == 3) && drop != null) {
            drop.setItemDamage(3);
        }
        drops.add(drop);

        return drops;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> drops = new ArrayList<>();
        TileEntity te = world.getTileEntity(x, y, z);
        if(te instanceof EzacianCustomJarFillableTE) {
            EzacianCustomJarFillableTE jarTE = (EzacianCustomJarFillableTE) te;
            this.setupDrops(jarTE, drops, null);
        }
        return drops;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
        int l = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5) & 3;
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof EzacianCustomJarFillableTE) {
            EzacianCustomJarFillableTE jarTE = (EzacianCustomJarFillableTE)tile;
            if (l == 0) {
                jarTE.facing = 2;
            }

            if (l == 1) {
                jarTE.facing = 5;
            }

            if (l == 2) {
                jarTE.facing = 3;
            }

            if (l == 3) {
                jarTE.facing = 4;
            }
            jarTE.setMaxAmount(this.getNewEssentiaMaxAmount());
        }
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World world, int x, int y, int z, int rs) {
        TileEntity tile = world.getTileEntity(x, y, z);
        float r;
        if (tile instanceof EzacianCustomJarFillableTE) {
            EzacianCustomJarFillableTE jarTE = (EzacianCustomJarFillableTE)tile;
            r = (float) jarTE.amount / jarTE.maxAmount;
            return MathHelper.floor_float(r * 14.0F) + (jarTE.amount > 0 ? 1 : 0);
        } else {
            return super.getComparatorInputOverride(world, x, y, z, rs);
        }
    }

    private boolean dealWithPhial(World world, EntityPlayer player, int x, int y, int z, EzacianCustomJarFillableTE jarTE) {
        ItemStack heldItem = player.getHeldItem();
        // Check whether to fill or to drain the phial
        if(heldItem.getItemDamage() == 0) {
            if(jarTE.amount >= 8){
                if (world.isRemote) {
                    player.swingItem();
                    return false;
                }

                final Aspect jarAspect = Aspect.getAspect(jarTE.aspect.getTag());
                if(jarTE.takeFromContainer(jarAspect, 8)) {
                    // Take an empty phial from the player's inventory
                    heldItem.stackSize--;
                    // Fill a new phial
                    final ItemStack filledPhial = new ItemStack(ConfigItems.itemEssence, 1, 1);
                    final AspectList phialContent = new AspectList().add(jarAspect, 8);
                    ((ItemEssence) ConfigItems.itemEssence).setAspects(filledPhial, phialContent);
                    // Drop on ground if there's no inventory space
                    if (!player.inventory.addItemStackToInventory(filledPhial)) {
                        world.spawnEntityInWorld(new EntityItem(world, (float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F, filledPhial));
                    }

                    world.playSoundAtEntity(player, "game.neutral.swim", 0.25F, 1.0F);
                    player.inventoryContainer.detectAndSendChanges();
                    return true;
                }
            }
        } else {
            final AspectList phialContent = ((ItemEssence) ConfigItems.itemEssence).getAspects(heldItem);
            if(phialContent != null && phialContent.size() == 1) {
                final Aspect phialAspect = phialContent.getAspects()[0];
                if(jarTE.amount + 8 <= jarTE.maxAmount && jarTE.doesContainerAccept(phialAspect)) {
                    if (world.isRemote) {
                        player.swingItem();
                        return false;
                    }

                    if(jarTE.addToContainer(phialAspect, 8) == 0) {
                        world.markBlockForUpdate(x, y, z);
                        jarTE.markDirty();
                        heldItem.stackSize--;
                        // Drop on ground if there's no inventory space
                        if (!player.inventory.addItemStackToInventory(new ItemStack(ConfigItems.itemEssence, 1, 0))) {
                            world.spawnEntityInWorld(new EntityItem(world, (float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F, new ItemStack(ConfigItems.itemEssence, 1, 0)));
                        }

                        world.playSoundAtEntity(player, "game.neutral.swim", 0.25F, 1.0F);
                        player.inventoryContainer.detectAndSendChanges();
                        return true;
                    }
                }
            }
        }
        return true;
    }

    public int getNewEssentiaMaxAmount() {
        return this.newEssentiaMaxAmount;
    }

    public void setNewEssentiaMaxAmount(int max) {
        this.newEssentiaMaxAmount = max;
    }
}

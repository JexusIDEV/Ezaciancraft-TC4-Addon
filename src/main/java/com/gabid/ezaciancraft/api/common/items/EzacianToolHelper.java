package com.gabid.ezaciancraft.api.common.items;

import com.gabid.ezaciancraft.api.oredict.OreDictUtils;
import com.gabid.ezaciancraft.common.items.tools.VoidCorePickaxeItem;
import com.gabid.ezaciancraft.common.items.tools.VoidStaffOfPrimalReconstructorItem;
import com.gabid.ezaciancraft.lib.world.BlockFinder;
import com.gabid.ezaciancraft.lib.world.math.Coord4D;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.event.world.BlockEvent;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.items.equipment.ItemElementalPickaxe;
import thaumcraft.common.lib.utils.BlockUtils;
import thaumcraft.common.lib.utils.InventoryUtils;
import thaumcraft.common.lib.utils.Utils;

import java.util.List;
import java.util.Set;

import static com.gabid.ezaciancraft.common.items.tools.VoidEarthMoverShovelItem.getOrientation;

/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 * <p>
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * <p>
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4.
 * Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 * <p>
 * File Created @ [Dec 29, 2013, 6:01:31 PM (GMT)]
 */

//obtained from thaumic tinkerer: https://github.com/Thaumic-Tinkerer/ThaumicTinkerer/blob/1.7.10/src/main/java/thaumic/tinkerer/common/item/kami/tool/ToolHandler.java
//some stuff has been removed, because doesn't mix well with my ideas in my mod...
public class EzacianToolHelper {
    public static Material[] materialsPick = new Material[]{Material.rock, Material.iron, Material.ice, Material.glass, Material.piston, Material.anvil};
    public static Material[] materialsShovel = new Material[]{Material.grass, Material.ground, Material.sand, Material.snow, Material.craftedSnow, Material.clay};
    public static Material[] materialsAxe = new Material[]{Material.coral, Material.leaves, Material.plants, Material.wood};

    public static Material[] allMaterials = new Material[]{Material.grass, Material.ground, Material.sand, Material.snow, Material.craftedSnow, Material.clay, Material.rock, Material.iron, Material.ice, Material.glass, Material.piston, Material.anvil, Material.coral, Material.leaves, Material.plants, Material.wood};

    public static boolean isRightMaterial(Material material, Material[] materialsListing) {
        for (Material mat : materialsListing)
            if (material == mat)
                return false;
        return true;
    }

    public static boolean handleThaumcraftCluster(World world, EntityPlayer player, Block block, int meta, int x, int y, int z) {
        if (world.isRemote) return false;

        ItemStack tool = player.getHeldItem();

        ItemStack ore = new ItemStack(block, 1, meta);
        ItemStack cluster = Utils.findSpecialMiningResult(ore, .25f, world.rand);
        ItemStack clusterStaff = Utils.findSpecialMiningResult(ore, 4f, world.rand);

        if(tool.getItem() instanceof VoidStaffOfPrimalReconstructorItem) {
            if (clusterStaff != null) {
                EntityItem entityItem = new EntityItem(
                        world,
                        x + 0.5,
                        y + 0.5,
                        z + 0.5,
                        clusterStaff.copy()
                );

                world.spawnEntityInWorld(entityItem);
                return true;
            }
        } else {
            if (cluster != null) {
                EntityItem entityItem = new EntityItem(
                        world,
                        x + 0.5,
                        y + 0.5,
                        z + 0.5,
                        cluster.copy()
                );

                world.spawnEntityInWorld(entityItem);
                return true;
            }
        }
        return false;
    }

    protected static void breakExtraBlock(ItemStack stack, World world, int x, int y, int z, int totalSize, EntityPlayer player, float refStrength, boolean breakSound) {
        if (world.isAirBlock(x, y, z)) return;

        Block block = world.getBlock(x, y, z);
        if (block.getMaterial() instanceof MaterialLiquid || (block.getBlockHardness(world, x, y, x) == -1 && !player.capabilities.isCreativeMode))
            return;

        int meta = world.getBlockMetadata(x, y, z);
        ItemStack tool = player.getHeldItem();

        boolean effective = false;

        for (String s : stack.getItem().getToolClasses(stack)) {
            if (block.isToolEffective(s, meta) || stack.getItem().func_150893_a(stack, block) > 1F) effective = true;
        }

        if (!effective) return;

        float strength = ForgeHooks.blockStrength(block, player, world, x, y, z);

        if (!player.canHarvestBlock(block) || !ForgeHooks.canHarvestBlock(block, player, meta) || refStrength / strength > 10f && !player.capabilities.isCreativeMode)
            return;

        if (!world.isRemote) {
            BlockEvent.BreakEvent event = ForgeHooks.onBlockBreakEvent(world, world.getWorldInfo().getGameType(), (EntityPlayerMP) player, x, y, z);
            if (event.isCanceled()) {
                ((EntityPlayerMP) player).playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, world));
                return;
            }
        }

        if (player.capabilities.isCreativeMode) {

            block.onBlockHarvested(world, x, y, z, meta, player);
            if (block.removedByPlayer(world, player, x, y, z, false))
                block.onBlockDestroyedByPlayer(world, x, y, z, meta);

            if (!world.isRemote) {
                ((EntityPlayerMP) player).playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, world));
            }

            if (breakSound) world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block) + (meta << 12));
            return;
        }

        stack.damageItem(world.rand.nextInt(totalSize), player);

        if (!world.isRemote) {
            block.onBlockHarvested(world, x, y, z, meta, player);

            if (block.removedByPlayer(world, player, x, y, z, true)) {
                block.onBlockDestroyedByPlayer(world, x, y, z, meta);

                boolean hasCluster = handleThaumcraftCluster(world, player, block, meta, x, y, z);

                if (!hasCluster && (tool.getItem() instanceof VoidStaffOfPrimalReconstructorItem || tool.getItem() instanceof ItemElementalPickaxe)) {
                    block.harvestBlock(world, player, x, y, z, meta);
                }

                player.addExhaustion(-0.025F);

                int exp = block.getExpDrop(world, meta, EnchantmentHelper.getFortuneModifier(player));
                if (exp > 0) {
                    player.addExperience(exp);
                }
            }

            EntityPlayerMP mpPlayer = (EntityPlayerMP) player;
            mpPlayer.playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, world));
        } else {
            if (breakSound) world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block) + (meta << 12));
            if (block.removedByPlayer(world, player, x, y, z, true)) {
                block.onBlockDestroyedByPlayer(world, x, y, z, meta);
            }

            Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C07PacketPlayerDigging(2, x, y, z, Minecraft.getMinecraft().objectMouseOver.sideHit));
        }
    }

    /**
     * @author mDiyo
     */
    public static MovingObjectPosition raytraceFromEntity(World world, Entity player, boolean par3, double range) {
        float f = 1.0F;
        float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
        float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
        double d0 = player.prevPosX + (player.posX - player.prevPosX) * f;
        double d1 = player.prevPosY + (player.posY - player.prevPosY) * f;
        if (!world.isRemote && player instanceof EntityPlayer)
            d1 += 1.62D;
        double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * f;
        Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float) Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
        float f6 = MathHelper.sin(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = range;
        if (player instanceof EntityPlayerMP)
            d3 = ((EntityPlayerMP) player).theItemInWorldManager.getBlockReachDistance();
        Vec3 vec31 = vec3.addVector(f7 * d3, f6 * d3, f8 * d3);
        return world.rayTraceBlocks(vec3, vec31, par3);
    }

    public static void updateGhostBlocks(EntityPlayer player, World world) {
        if (!world.isRemote) {
            int xPos = (int) player.posX;
            int yPos = (int) player.posY;
            int zPos = (int) player.posZ;

            for (int x = xPos - 6; x < xPos + 6; ++x) {
                for (int y = yPos - 6; y < yPos + 6; ++y) {
                    for (int z = zPos - 6; z < zPos + 6; ++z) {
                        ((EntityPlayerMP) player).playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, world));
                    }
                }
            }

        }
    }

    public static boolean removeAOEBlocks(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int radius) {
        Block block = player.worldObj.getBlock(x, y, z);
        int meta = player.worldObj.getBlockMetadata(x, y, z);
        boolean effective = false;

        if (block != null) {
            for (String s : stack.getItem().getToolClasses(stack)) {
                if (block.isToolEffective(s, meta) || stack.getItem().func_150893_a(stack, block) > 1F)
                    effective = true;
            }
        }

        if (!effective) return true;

        MovingObjectPosition mop = raytraceFromEntity(player.worldObj, player, false, 4.5d);

        if (mop == null) {
            updateGhostBlocks(player, player.worldObj);
            return true;
        }

        float refStrength = ForgeHooks.blockStrength(block, player, player.worldObj, x, y, z);

        int sideHit = mop.sideHit;

        int xMax = radius;
        int xMin = radius;
        int yMax = radius;
        int yMin = radius;
        int zMax = radius;
        int zMin = radius;
        int yOffset = 0;

        switch (sideHit) {
            case 0:
                yMax = 0;
                yMin = 0;
                zMax = radius;
                break;
            case 1:
                yMin = 0;
                yMax = 0;
                zMax = radius;
                break;
            case 2:
                xMax = radius;
                zMin = 0;
                zMax = 0;
                yOffset = radius - 1;
                break;
            case 3:
                xMax = radius;
                zMax = 0;
                zMin = 0;
                yOffset = radius - 1;
                break;
            case 4:
                xMax = 0;
                xMin = 0;
                yOffset = radius - 1;
                break;
            case 5:
                xMin = 0;
                xMax = 0;
                zMax = radius;
                yOffset = radius - 1;
                break;
        }

        for (int xPos = x - xMin; xPos <= x + xMax; xPos++) {
            for (int yPos = y + yOffset - yMin; yPos <= y + yOffset + yMax; yPos++) {
                for (int zPos = z - zMin; zPos <= z + zMax; zPos++) {
                    breakExtraBlock(stack, world, xPos, yPos, zPos, radius, player, refStrength, Math.abs(x - xPos) <= 1 && Math.abs(y - yPos) <= 1 && Math.abs(z - zPos) <= 1);
                    player.worldObj.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(player.worldObj.getBlock(x, y, z)) + (player.worldObj.getBlockMetadata(x, y, z) << 12));
                }
            }
        }
        return true;
    }

    public static void removeBlocksInIteration(EntityPlayer player, World world, int x, int y, int z, int xs, int ys, int zs, int xe, int ye, int ze) {
        for (int x1 = xs; x1 < xe; ++x1) {
            for (int y1 = ys; y1 < ye; ++y1) {
                for (int z1 = zs; z1 < ze; ++z1) {
                    if (x != x1 + x || y != y1 + y || z != z1 + z) {
                        Block block = world.getBlock(x1 + x, y1 + y, z1 + z);
                        breakExtraBlock(player.getHeldItem(), world, x1 + x, y1 + y, z1 + z, 1, player, ForgeHooks.blockStrength(block, player, world, x1, y1, z1), Math.abs(x - x1) <= 1 && Math.abs(y - y1) <= 1 && Math.abs(z - z1) <= 1);
                    }
                }
            }
        }
    }

    public static void breakVeinWood(ItemStack stack, World world, EntityPlayer player, int x, int y, int z) {
        Block blck = world.getBlock(x, y, z);
        if (Utils.isWoodLog(world, x, y, z)) {
            while (blck != Blocks.air) {
                BlockUtils.breakFurthestBlock(world, x, y, z, blck, player);
                if ((stack.isItemStackDamageable() || stack.getItemDamage() > 0) && stack.getItem() instanceof ItemTool) {
                    stack.damageItem(1, player);
                }
                blck = world.getBlock(x, y, z);
            }

            List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(x - 5, y - 1, z - 5, x + 5, y + 64, z + 5));
            for (EntityItem item : items) {
                item.setPosition(x + 0.5, y + 0.5, z + 0.5);
                item.ticksExisted += 20;
            }
        }
    }

    //using mekanism algorithm method for the moment, maybe will use an own algorithm or something else future...
    public static boolean breakVein(ItemStack stack, World world, EntityPlayer player, int x, int y, int z) {
        Block blck = world.getBlock(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);
        ItemStack tool = player.getHeldItem();

        boolean isVeinableWood = false;
        boolean isVeinableOre = false;

        if (blck == Blocks.lit_redstone_ore) {
            blck = Blocks.redstone_ore;
        }

        ItemStack itemStackBlock = new ItemStack(blck, 1, meta);
        List<String> oreDicts = OreDictUtils.getOreDictName(itemStackBlock);

        for (String dicts : oreDicts) {
            if (dicts.equals("logWood")) {
                isVeinableWood = true;
                break;
            } else if (dicts.contains("ore")) {
                isVeinableOre = true;
            }
        }
        if (Utils.isWoodLog(world, x, y, z))
            isVeinableWood = true;

        Coord4D orig = new Coord4D(x, y, z, player.worldObj.provider.dimensionId);

        if (isVeinableWood && !player.capabilities.isCreativeMode) {
            while (blck != Blocks.air) {
                BlockUtils.breakFurthestBlock(world, x, y, z, blck, player);
                if ((stack.isItemStackDamageable() || stack.getItemDamage() > 0) && stack.getItem() instanceof ItemTool) {
                    stack.damageItem(1, player);
                }
                blck = world.getBlock(x, y, z);
            }

            List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(x - 5, y - 1, z - 5, x + 5, y + 64, z + 5));
            for (EntityItem item : items) {
                item.setPosition(x + 0.5, y + 0.5, z + 0.5);
                item.ticksExisted += 20;
            }
        }

        if (isVeinableOre && !player.capabilities.isCreativeMode) {

            Set<Coord4D> found = new BlockFinder(player.worldObj, itemStackBlock, new Coord4D(x, y, z, player.worldObj.provider.dimensionId)).calc();

            for (Coord4D coord : found) {
                if (coord.equals(orig) || stack.getItemDamage() > 3) {
                    continue;
                }

                Block block2 = coord.getBlock(player.worldObj);

                block2.onBlockDestroyedByPlayer(player.worldObj, coord.xCoord, coord.yCoord, coord.zCoord, meta);
                player.worldObj.playAuxSFXAtEntity(null, 2001, coord.xCoord, coord.yCoord, coord.zCoord, meta << 12);
                player.worldObj.setBlockToAir(coord.xCoord, coord.yCoord, coord.zCoord);
                block2.breakBlock(player.worldObj, coord.xCoord, coord.yCoord, coord.zCoord, blck, meta);
                boolean hasCluster = handleThaumcraftCluster(world, player, block2, meta, coord.xCoord, coord.yCoord, coord.zCoord);

                if (!hasCluster && (tool.getItem() instanceof ItemElementalPickaxe || tool.getItem() instanceof VoidStaffOfPrimalReconstructorItem)) {
                    block2.dropBlockAsItem(player.worldObj, coord.xCoord, coord.yCoord, coord.zCoord, meta, 0);
                }

                stack.damageItem(1, player);
            }
        }
        return false;
    }

    protected static void useHoe(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side) {
        if (player.canPlayerEdit(x, y, z, side, stack) && (!player.capabilities.isCreativeMode || stack.getItemDamage() >= 3)) {
            UseHoeEvent event = new UseHoeEvent(player, stack, world, x, y, z);

            if (MinecraftForge.EVENT_BUS.post(event)) {
                return;
            }

            if (event.getResult() == Event.Result.ALLOW) {
                stack.damageItem(world.rand.nextInt(3), player);
                return;
            }

            Block block1 = world.getBlock(x, y, z);
            boolean air = world.isAirBlock(x, y + 1, z);

            if (side != 0 && air && (block1 == Blocks.grass || block1 == Blocks.dirt)) {
                Block farm = Blocks.farmland;
                world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, farm.stepSound.getStepResourcePath(), (farm.stepSound.getVolume() + 1.0F) / 2.0F, farm.stepSound.getPitch() * 0.8F);

                if (!world.isRemote) {
                    world.setBlock(x, y, z, farm);
                    if (!player.capabilities.isCreativeMode) {
                        stack.damageItem(world.rand.nextInt(3), player);
                    }

                }
            }
        }
    }

    public static boolean hoeInArea(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, int radius) {
        for (int x1 = x - radius; x1 <= x + radius; x1++) {
            for (int z1 = z - radius; z1 <= z + radius; z1++) {
                useHoe(stack, player, world, x1, y, z1, side);
            }
        }
        return true;
    }

    public static boolean placerInArea(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, int radius) {
        Block block = player.worldObj.getBlock(x, y, z);
        int meta = player.worldObj.getBlockMetadata(x, y, z);

        int xm = ForgeDirection.getOrientation(side).offsetX;
        int ym = ForgeDirection.getOrientation(side).offsetY;
        int zm = ForgeDirection.getOrientation(side).offsetZ;

        for (int aa = -radius; aa <= radius; ++aa) {
            for (int bb = -radius; bb <= radius; ++bb) {
                placerBlocks(stack, block, meta, world, player, side, x, y, z, xm, ym, zm, aa, bb);
            }
        }
        return true;
    }

    public static void placerBlocks(ItemStack stack, Block bi, int md, World world, EntityPlayer player, int side, int x, int y, int z, int xm, int ym, int zm, int bb, int aa) {
        int xx = 0;
        int yy = 0;
        int zz = 0;
        byte o = getOrientation(stack);
        int l;
        if (o == 1) {
            yy = bb;
            if (side <= 1) {
                l = MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5) & 3;
                if (l != 0 && l != 2) {
                    zz = aa;
                } else {
                    xx = aa;
                }
            } else if (side <= 3) {
                zz = aa;
            } else {
                xx = aa;
            }
        } else if (o == 2) {
            if (side <= 1) {
                l = MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5) & 3;
                yy = bb;
                if (l != 0 && l != 2) {
                    zz = aa;
                } else {
                    xx = aa;
                }
            } else {
                zz = bb;
                xx = aa;
            }
        } else if (side <= 1) {
            xx = aa;
            zz = bb;
        } else if (side <= 3) {
            xx = aa;
            yy = bb;
        } else {
            zz = aa;
            yy = bb;
        }

        Block b2 = world.getBlock(x + xx + xm, y + yy + ym, z + zz + zm);
        if (world.isAirBlock(x + xx + xm, y + yy + ym, z + zz + zm) || b2 == Blocks.vine || b2 == Blocks.tallgrass || b2.getMaterial() == Material.water || b2 == Blocks.lava || b2.isReplaceable(world, x + xx + xm, y + yy + ym, z + zz + zm)) {
            if (!player.capabilities.isCreativeMode && !InventoryUtils.consumeInventoryItem(player, Item.getItemFromBlock(bi), md)) {
                if (bi == Blocks.grass && (player.capabilities.isCreativeMode || InventoryUtils.consumeInventoryItem(player, Item.getItemFromBlock(Blocks.dirt), 0))) {
                    world.playSound(x + xx + xm, y + yy + ym, z + zz + zm, bi.stepSound.func_150496_b(), 0.6F, 0.9F + world.rand.nextFloat() * 0.2F, false);
                    world.setBlock(x + xx + xm, y + yy + ym, z + zz + zm, Blocks.dirt, 0, 3);
                    stack.damageItem(1, player);
                    Thaumcraft.proxy.blockSparkle(world, x + xx + xm, y + yy + ym, z + zz + zm, 3, 4);
                    player.swingItem();
                }
            } else {
                world.playSound(x + xx + xm, y + yy + ym, z + zz + zm, bi.stepSound.func_150496_b(), 0.6F, 0.9F + world.rand.nextFloat() * 0.2F, false);
                world.setBlock(x + xx + xm, y + yy + ym, z + zz + zm, bi, md, 3);
                stack.damageItem(1, player);
                Thaumcraft.proxy.blockSparkle(world, x + xx + xm, y + yy + ym, z + zz + zm, 8401408, 4);
                player.swingItem();
            }
        }
    }
}

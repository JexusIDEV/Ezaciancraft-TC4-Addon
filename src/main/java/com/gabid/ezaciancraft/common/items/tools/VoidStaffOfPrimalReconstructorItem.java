package com.gabid.ezaciancraft.common.items.tools;

import com.gabid.ezaciancraft.api.EzacianToolMaterials;
import com.gabid.ezaciancraft.api.common.items.EzacianToolHelper;
import com.gabid.ezaciancraft.api.common.items.IEzacianPrimalTool;
import com.gabid.ezaciancraft.api.registry.EzacianCraftMiscRegistry;
import com.gabid.ezaciancraft.lib.nbt.NBTHelper;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.potion.Potion;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import thaumcraft.api.IRepairable;
import thaumcraft.api.IWarpingGear;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.blocks.BlockCustomPlant;
import thaumcraft.common.blocks.BlockJar;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.entities.EntityFollowingItem;
import thaumcraft.common.entities.golems.EntityGolemBase;
import thaumcraft.common.lib.utils.EntityUtils;
import thaumcraft.common.lib.utils.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_STAFF_OF_PRIMAL_RECONSTRUCTOR;
import static com.gabid.ezaciancraft.api.EzacianCraftNBTConstants.*;
import static com.gabid.ezaciancraft.api.EzacianCraftTranslations.*;
import static com.gabid.ezaciancraft.api.common.items.EzacianToolHelper.hoeInArea;
import static com.gabid.ezaciancraft.registry.EzacianCraftCreativeTab.EZACIANCRAFT_TAB;

public class VoidStaffOfPrimalReconstructorItem extends ItemTool implements IWarpingGear, IRepairable, IEzacianPrimalTool {

    private static final Set<Block> validMaterials = Sets.newHashSet(
            Blocks.cobblestone,
            Blocks.double_stone_slab,
            Blocks.stone_slab,
            Blocks.stone,
            Blocks.sandstone,
            Blocks.mossy_cobblestone,
            Blocks.iron_ore,
            Blocks.iron_block,
            Blocks.coal_ore,
            Blocks.gold_block,
            Blocks.gold_ore,
            Blocks.diamond_ore,
            Blocks.diamond_block,
            Blocks.ice,
            Blocks.netherrack,
            Blocks.lapis_ore,
            Blocks.lapis_block,
            Blocks.redstone_ore,
            Blocks.lit_redstone_ore,
            Blocks.rail,
            Blocks.detector_rail,
            Blocks.golden_rail,
            Blocks.activator_rail,
            Blocks.grass,
            Blocks.dirt,
            Blocks.sand,
            Blocks.gravel,
            Blocks.snow_layer,
            Blocks.snow,
            Blocks.clay,
            Blocks.farmland,
            Blocks.soul_sand,
            Blocks.mycelium,
            Blocks.planks,
            Blocks.bookshelf,
            Blocks.log,
            Blocks.log2,
            Blocks.chest,
            Blocks.pumpkin,
            Blocks.lit_pumpkin
    );
    protected final int generalMaxAOERadiusTool = 8;

    public VoidStaffOfPrimalReconstructorItem() {
        super(8f, EzacianToolMaterials.toolMatPrimalVoidElemental, validMaterials);
        this.setUnlocalizedName(UNLOCALE_STAFF_OF_PRIMAL_RECONSTRUCTOR);
        this.setCreativeTab(EZACIANCRAFT_TAB);
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack) {
        return ImmutableSet.of("sword", "pickaxe", "shovel", "axe", "hoe");
    }

    @Override
    public EnumRarity getRarity(ItemStack p_77613_1_) {
        return EzacianCraftMiscRegistry.beyondRarity;
    }

    @Override
    public void onUpdate(ItemStack stack, World level, Entity player, int a, boolean b) {
        if (stack.isItemDamaged() && player != null && player.ticksExisted % 5 == 0 && player instanceof EntityLivingBase) {
            stack.damageItem(-level.rand.nextInt(10), (EntityLivingBase) player);
        }
        super.onUpdate(stack, level, player, a, b);
    }

    @Override
    public int getWarp(ItemStack itemStack, EntityPlayer entityPlayer) {
        return 6;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List tooltips, boolean flag) {
        super.addInformation(stack, player, tooltips, flag);

        int toolBehaviourData = this.getBehaviour(stack);
        String infoBehaviour = "";
        switch (toolBehaviourData) {
            case 0:
                infoBehaviour = StatCollector.translateToLocal(ezacianPrimalToolBehaviourSeekerTranslation);
                break;
            case 1:
                infoBehaviour = StatCollector.translateToLocal(ezacianPrimalToolBehaviourMagnetTranslation);
                break;
            case 2:
                infoBehaviour = StatCollector.translateToLocal(ezacianPrimalToolBehaviourPlacerTranslation);
                break;
            case 3:
                infoBehaviour = StatCollector.translateToLocal(ezacianPrimalToolBehaviourGrowerTranslation);
                break;
            case 4:
                infoBehaviour = StatCollector.translateToLocal(ezacianPrimalToolBehaviourZephyrTranslation);
                break;
        }
        tooltips.add(StatCollector.translateToLocal(ezacianPrimalToolBehaviourTranslation) + infoBehaviour);

        int toolSubModeData = this.getSubMode(stack);
        String infoSubMode = "";
        switch (toolSubModeData) {
            case 0:
                infoSubMode = StatCollector.translateToLocal(ezacianPrimalToolSubModeAreaTranslation);
                break;
            case 1:
                infoSubMode = StatCollector.translateToLocal(ezacianPrimalToolSubModeVeinTranslation);
                break;
        }
        tooltips.add(StatCollector.translateToLocal(ezacianPrimalToolSubModeTranslation) + infoSubMode);

        if (stack.stackTagCompound.getInteger(PRIMAL_TOOL_SUB_MODE) == 0) {
            int currentAOE = 2 * stack.stackTagCompound.getInteger(PRIMAL_TOOL_AOE) + 1;
            tooltips.add(StatCollector.translateToLocal(ezacianPrimalToolAOETranslation) + currentAOE + "x" + currentAOE + "x" + 1);
        }
    }

    @Override
    public float func_150893_a(ItemStack p_150893_1_, Block p_150893_2_) {
        return this.efficiencyOnProperMaterial;
    }

    @Override
    public boolean func_150897_b(Block p_150897_1_) {
        return true;
    }

    @Override
    public float getDigSpeed(ItemStack stack, Block block, int meta) {
        if (block instanceof BlockJar && !ForgeHooks.isToolEffective(stack, block, meta)) {
            return this.efficiencyOnProperMaterial;
        }
        return super.getDigSpeed(stack, block, meta);
    }

    @Override
    public boolean canHarvestBlock(Block block, ItemStack stack) {
        return block != Blocks.bedrock;
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float offsetX, float offsetY, float offsetZ) {
        if (itemStack.hasTagCompound()) {
            int currentBehaviour = itemStack.stackTagCompound.getInteger(PRIMAL_TOOL_BEHAVIOUR);
            int currentSubMode = itemStack.stackTagCompound.getInteger(PRIMAL_TOOL_SUB_MODE);
            int currentAOE = itemStack.stackTagCompound.getInteger(PRIMAL_TOOL_AOE);
            //miner seeker
            if (currentBehaviour == 0) {
                return this.seekMinerals(itemStack, player, world, x, y, z, side, offsetX, offsetY, offsetZ);
            }
            //placer
            if (currentBehaviour == 2 && currentSubMode == 0) {
                return EzacianToolHelper.placerInArea(itemStack, player, world, x, y, z, side, currentAOE);
            }
            //grower
            if (currentBehaviour == 3) {
                return this.growPlantsOrHoe(itemStack, player, world, x, y, z, side, offsetX, offsetY, offsetZ);
            }
        }
        return super.onItemUse(itemStack, player, world, x, y, z, side, offsetX, offsetY, offsetZ);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, int x, int y, int z, EntityPlayer player) {
        if (itemstack.hasTagCompound()) {
            int currentSubMode = itemstack.stackTagCompound.getInteger(PRIMAL_TOOL_SUB_MODE);
            int currentAOERadius = itemstack.stackTagCompound.getInteger(PRIMAL_TOOL_AOE);

            World world = player.worldObj;
            Material mat = world.getBlock(x, y, z).getMaterial();
            if (EzacianToolHelper.isRightMaterial(mat, EzacianToolHelper.allMaterials))
                return false;

            MovingObjectPosition mopBlock = EzacianToolHelper.raytraceFromEntity(world, player, true, 4.5);
            if (mopBlock == null)
                return false;

            if (currentSubMode == 0) {
                if (currentAOERadius == 0) {
                    this.manageSingleClusterDrop(world, player, itemstack, x, y, z);
                } else {
                    if (player.isSneaking()) {
                        this.manageSingleClusterDrop(world, player, itemstack, x, y, z);
                    } else {
                        EzacianToolHelper.removeAOEBlocks(itemstack, player, world, x, y, z, currentAOERadius);
                    }
                }
            } else if (currentSubMode == 1) {
                return EzacianToolHelper.breakVein(itemstack, world, player, x, y, z);
            }
        }
        return super.onBlockStartBreak(itemstack, x, y, z, player);
    }

    private void manageSingleClusterDrop(World world, EntityPlayer player, ItemStack stack, int x, int y, int z) {
        if (!world.isRemote) {
            Block block = world.getBlock(x, y, z);
            int meta = world.getBlockMetadata(x, y, z);

            ItemStack ore = new ItemStack(block, 1, meta);

            ItemStack cluster = Utils.findSpecialMiningResult(ore, 4f, player.worldObj.rand);

            if (cluster != null) {
                world.setBlockToAir(x, y, z);

                EntityItem entityItem = new EntityItem(
                        world,
                        x + 0.5,
                        y + 0.5,
                        z + 0.5,
                        cluster.copy()
                );

                world.spawnEntityInWorld(entityItem);

                return;
            }
        }
        super.onBlockStartBreak(stack, x, y, z, player);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        if (stack.hasTagCompound()) {
            int currentBehaviour = stack.stackTagCompound.getInteger(PRIMAL_TOOL_BEHAVIOUR);
            if (currentBehaviour == 1 || currentBehaviour == 4) {
                return 160000;
            } else {
                return super.getMaxItemUseDuration(stack);
            }
        } else {
            return super.getMaxItemUseDuration(stack);
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (stack.hasTagCompound()) {
            int currentBehaviour = stack.stackTagCompound.getInteger(PRIMAL_TOOL_BEHAVIOUR);
            if (currentBehaviour == 1 || currentBehaviour == 4) {
                player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
            }
        }
        return stack;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        if (stack.hasTagCompound()) {
            int currentBehaviour = stack.stackTagCompound.getInteger(PRIMAL_TOOL_BEHAVIOUR);
            if (currentBehaviour == 1) {
                return EnumAction.bow;
            } else if (currentBehaviour == 4) {
                return EnumAction.block;
            } else {
                return super.getItemUseAction(stack);
            }
        } else {
            return super.getItemUseAction(stack);
        }
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        if (stack.hasTagCompound()) {
            int currentBehaviour = stack.stackTagCompound.getInteger(PRIMAL_TOOL_BEHAVIOUR);

            if (currentBehaviour == 1) {
                ArrayList<Entity> stuff = EntityUtils.getEntitiesInRange(player.worldObj, player.posX, player.posY, player.posZ, player, EntityItem.class, 20.0);
                if (!stuff.isEmpty()) {
                    Iterator<Entity> i$ = stuff.iterator();

                    while (true) {
                        Entity e;
                        do {
                            if (!i$.hasNext()) {
                                return;
                            }

                            e = i$.next();
                        } while (e instanceof EntityFollowingItem && ((EntityFollowingItem) e).target != null);

                        if (!e.isDead && e instanceof EntityItem) {
                            double d6 = e.posX - player.posX;
                            double d8 = e.posY - player.posY + (double) (player.height / 2.0F);
                            double d10 = e.posZ - player.posZ;
                            double d11 = MathHelper.sqrt_double(d6 * d6 + d8 * d8 + d10 * d10);
                            d6 /= d11;
                            d8 /= d11;
                            d10 /= d11;
                            double d13 = 0.3;
                            e.motionX -= d6 * d13;
                            e.motionY -= d8 * d13;
                            e.motionZ -= d10 * d13;
                            if (e.motionX > 0.35) {
                                e.motionX = 0.35;
                            }

                            if (e.motionX < -0.35) {
                                e.motionX = -0.35;
                            }

                            if (e.motionY > 0.35) {
                                e.motionY = 0.35;
                            }

                            if (e.motionY < -0.35) {
                                e.motionY = -0.35;
                            }

                            if (e.motionZ > 0.35) {
                                e.motionZ = 0.35;
                            }

                            if (e.motionZ < -0.35) {
                                e.motionZ = -0.35;
                            }

                            Thaumcraft.proxy.crucibleBubble(player.worldObj, (float) e.posX + (player.worldObj.rand.nextFloat() - player.worldObj.rand.nextFloat()) * 0.125F, (float) e.posY + (player.worldObj.rand.nextFloat() - player.worldObj.rand.nextFloat()) * 0.125F, (float) e.posZ + (player.worldObj.rand.nextFloat() - player.worldObj.rand.nextFloat()) * 0.125F, 0.33F, 0.33F, 1.0F);
                        }
                    }
                }
            } else if (currentBehaviour == 4) {
                super.onUsingTick(stack, player, count);
                player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
                int ticks = this.getMaxItemUseDuration(stack) - count;
                if (player.motionY < 0.0) {
                    player.motionY /= 1.2000000476837158;
                    player.fallDistance /= 1.2F;
                }

                player.motionY += 0.07999999821186066;
                if (player.motionY > 0.5) {
                    player.motionY = 0.20000000298023224;
                }

                if (player instanceof EntityPlayerMP) {
                    Utils.resetFloatCounter((EntityPlayerMP) player);
                }

                List targets = player.worldObj.getEntitiesWithinAABBExcludingEntity(player, player.boundingBox.offset(2.5, 2.5, 2.5));
                int miny;
                if (!targets.isEmpty()) {
                    for (miny = 0; miny < targets.size(); ++miny) {
                        Entity entity = (Entity) targets.get(miny);
                        if (!(entity instanceof EntityPlayer) && !entity.isDead && (player.ridingEntity == null || player.ridingEntity != entity)) {
                            Vec3 p = Vec3.createVectorHelper(player.posX, player.posY, player.posZ);
                            Vec3 t = Vec3.createVectorHelper(entity.posX, entity.posY, entity.posZ);
                            double distance = p.distanceTo(t) + 0.1;
                            Vec3 r = Vec3.createVectorHelper(t.xCoord - p.xCoord, t.yCoord - p.yCoord, t.zCoord - p.zCoord);
                            entity.motionX += r.xCoord / 2.5 / distance;
                            entity.motionY += r.yCoord / 2.5 / distance;
                            entity.motionZ += r.zCoord / 2.5 / distance;
                        }
                    }
                }

                if (player.worldObj.isRemote) {
                    miny = (int) (player.boundingBox.minY - 2.0);
                    if (player.onGround) {
                        miny = MathHelper.floor_double(player.boundingBox.minY);
                    }

                    for (int a = 0; a < 5; ++a) {
                        Thaumcraft.proxy.smokeSpiral(player.worldObj, player.posX, player.boundingBox.minY + (double) (player.height / 2.0F), player.posZ, 1.5F, player.worldObj.rand.nextInt(360), miny, 14540253);
                    }

                    if (player.onGround) {
                        float r1 = player.worldObj.rand.nextFloat() * 360.0F;
                        float mx = -MathHelper.sin(r1 / 180.0F * 3.1415927F) / 5.0F;
                        float mz = MathHelper.cos(r1 / 180.0F * 3.1415927F) / 5.0F;
                        player.worldObj.spawnParticle("smoke", player.posX, player.boundingBox.minY + 0.10000000149011612, player.posZ, mx, 0.0, mz);
                    }
                } else if (ticks % 20 == 0) {
                    player.worldObj.playSoundToNearExcept(player, "thaumcraft:wind", 0.5F, 0.9F + player.worldObj.rand.nextFloat() * 0.2F);
                }

                if (ticks % 20 == 0) {
                    stack.damageItem(1, player);
                }
            } else {
                super.onUsingTick(stack, player, count);
            }
        } else {
            super.onUsingTick(stack, player, count);
        }
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if (stack.hasTagCompound()) {
            int currentBehaviour = stack.stackTagCompound.getInteger(PRIMAL_TOOL_BEHAVIOUR);
            if (currentBehaviour == 4) {
                if (entity.isEntityAlive()) {
                    List targets = player.worldObj.getEntitiesWithinAABBExcludingEntity(player, entity.boundingBox.expand(1.2, 1.1, 1.2));
                    int count = 0;
                    if (targets.size() > 1) {
                        for (Object target : targets) {
                            Entity ent = (Entity) target;
                            if (!ent.isDead && (!(ent instanceof EntityGolemBase) || !((EntityGolemBase) ent).getOwnerName().equals(player.getCommandSenderName())) && (!(ent instanceof EntityTameable) || !((EntityTameable) ent).func_152113_b().equals(player.getCommandSenderName())) && ent instanceof EntityLiving && ent.getEntityId() != entity.getEntityId()) {
                                if (ent.isEntityAlive()) {
                                    this.attackTargetEntityWithCurrentItem(ent, player);
                                    ++count;
                                }
                            }
                        }

                        if (count > 0 && !player.worldObj.isRemote) {
                            player.worldObj.playSoundAtEntity(entity, "thaumcraft:swing", 1.0F, 0.9F + player.worldObj.rand.nextFloat() * 0.2F);
                        }
                    }
                }
            }
        }

        return super.onLeftClickEntity(stack, player, entity);
    }

    public void attackTargetEntityWithCurrentItem(Entity par1Entity, EntityPlayer player) {
        if (!MinecraftForge.EVENT_BUS.post(new AttackEntityEvent(player, par1Entity))) {
            if (par1Entity.canAttackWithItem() && !par1Entity.hitByEntity(player)) {
                float f = (float) player.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
                int i = 0;
                float f1 = 0.0F;
                if (par1Entity instanceof EntityLivingBase) {
                    f1 = EnchantmentHelper.getEnchantmentModifierLiving(player, (EntityLivingBase) par1Entity);
                    i += EnchantmentHelper.getKnockbackModifier(player, (EntityLivingBase) par1Entity);
                }

                if (player.isSprinting()) {
                    ++i;
                }

                if (f > 0.0F || f1 > 0.0F) {
                    boolean flag = player.fallDistance > 0.0F && !player.onGround && !player.isOnLadder() && !player.isInWater() && !player.isPotionActive(Potion.blindness) && player.ridingEntity == null && par1Entity instanceof EntityLivingBase;
                    if (flag && f > 0.0F) {
                        f *= 1.5F;
                    }

                    f += f1;
                    boolean flag1 = false;
                    int j = EnchantmentHelper.getFireAspectModifier(player);
                    if (par1Entity instanceof EntityLivingBase && j > 0 && !par1Entity.isBurning()) {
                        flag1 = true;
                        par1Entity.setFire(1);
                    }

                    boolean flag2 = par1Entity.attackEntityFrom(DamageSource.causeMobDamage(player), f);
                    if (flag2) {
                        if (i > 0) {
                            par1Entity.addVelocity(-MathHelper.sin(player.rotationYaw * 3.1415927F / 180.0F) * (float) i * 0.5F, 0.1, MathHelper.sin(player.rotationYaw * 3.1415927F / 180.0F) * (float) i * 0.5F);
                            player.motionX *= 0.6;
                            player.motionZ *= 0.6;
                            player.setSprinting(false);
                        }

                        if (flag) {
                            player.onCriticalHit(par1Entity);
                        }

                        if (f1 > 0.0F) {
                            player.onEnchantmentCritical(par1Entity);
                        }

                        if (f >= 18.0F) {
                            player.triggerAchievement(AchievementList.overkill);
                        }

                        player.setLastAttacker(par1Entity);
                        if (par1Entity instanceof EntityLivingBase) {
                            EnchantmentHelper.func_151384_a((EntityLivingBase) par1Entity, player);
                        }
                    }

                    ItemStack itemstack = player.getCurrentEquippedItem();
                    Object object = par1Entity;
                    if (par1Entity instanceof EntityDragonPart) {
                        IEntityMultiPart ientitymultipart = ((EntityDragonPart) par1Entity).entityDragonObj;
                        if (ientitymultipart instanceof EntityLivingBase) {
                            object = ientitymultipart;
                        }
                    }

                    if (itemstack != null && object instanceof EntityLivingBase) {
                        itemstack.hitEntity((EntityLivingBase) object, player);
                        if (itemstack.stackSize <= 0) {
                            player.destroyCurrentEquippedItem();
                        }
                    }

                    if (par1Entity instanceof EntityLivingBase) {
                        player.addStat(StatList.damageDealtStat, Math.round(f * 10.0F));
                        if (j > 0 && flag2) {
                            par1Entity.setFire(j * 4);
                        } else if (flag1) {
                            par1Entity.extinguish();
                        }
                    }

                    player.addExhaustion(0.3F);
                }
            }
        }
    }

    @Override
    public void setBehaviour(ItemStack stack, int behaviour) {
        if (!NBTHelper.containerNBTIsNotNull(stack)) {
            NBTHelper.setDefaultContainerNBT(stack, PRIMAL_TOOL_BEHAVIOUR, 0);
        }

        if (behaviour > -1 && behaviour < 5) {
            stack.getTagCompound().setInteger(PRIMAL_TOOL_BEHAVIOUR, behaviour);
        } else {
            stack.getTagCompound().setInteger(PRIMAL_TOOL_BEHAVIOUR, 0);
        }
    }

    @Override
    public void changeBehaviour(ItemStack stack) {
        if (!NBTHelper.containerNBTIsNotNull(stack)) {
            NBTHelper.setDefaultContainerNBT(stack, PRIMAL_TOOL_BEHAVIOUR, 0);
        }
        int current = stack.getTagCompound().getInteger(PRIMAL_TOOL_BEHAVIOUR);
        current++;
        this.setBehaviour(stack, current);
    }

    @Override
    public int getBehaviour(ItemStack stack) {
        if (!NBTHelper.containerNBTIsNotNull(stack)) {
            NBTHelper.setDefaultContainerNBT(stack, PRIMAL_TOOL_BEHAVIOUR, 0);
        }
        return stack.getTagCompound().getInteger(PRIMAL_TOOL_BEHAVIOUR);
    }

    @Override
    public void setSubMode(ItemStack stack, int subMode) {
        if (!NBTHelper.containerNBTIsNotNull(stack)) {
            NBTHelper.setDefaultContainerNBT(stack, PRIMAL_TOOL_SUB_MODE, 0);
        }

        if (subMode > -1 && subMode < 2) {
            stack.getTagCompound().setInteger(PRIMAL_TOOL_SUB_MODE, subMode);
        } else {
            stack.getTagCompound().setInteger(PRIMAL_TOOL_SUB_MODE, 0);
        }
    }

    @Override
    public void changeSubMode(ItemStack stack) {
        if (!NBTHelper.containerNBTIsNotNull(stack)) {
            NBTHelper.setDefaultContainerNBT(stack, PRIMAL_TOOL_SUB_MODE, 0);
        }
        int current = stack.getTagCompound().getInteger(PRIMAL_TOOL_SUB_MODE);
        current++;
        this.setSubMode(stack, current);
    }

    @Override
    public int getSubMode(ItemStack stack) {
        if (!NBTHelper.containerNBTIsNotNull(stack)) {
            NBTHelper.setDefaultContainerNBT(stack, PRIMAL_TOOL_SUB_MODE, 0);
        }
        return stack.getTagCompound().getInteger(PRIMAL_TOOL_SUB_MODE);
    }

    @Override
    public void setAOE(ItemStack stack, int aoe) {
        if (!NBTHelper.containerNBTIsNotNull(stack)) {
            NBTHelper.setDefaultContainerNBT(stack, PRIMAL_TOOL_AOE, 0);
        }

        if (aoe > -1 && aoe < this.generalMaxAOERadiusTool) {
            stack.getTagCompound().setInteger(PRIMAL_TOOL_AOE, aoe);
        } else {
            stack.getTagCompound().setInteger(PRIMAL_TOOL_AOE, 0);
        }
    }

    @Override
    public void changeAOE(ItemStack stack) {
        if (!NBTHelper.containerNBTIsNotNull(stack)) {
            NBTHelper.setDefaultContainerNBT(stack, PRIMAL_TOOL_AOE, 0);
        }
        int current = stack.getTagCompound().getInteger(PRIMAL_TOOL_AOE);
        current++;
        this.setAOE(stack, current);
    }

    @Override
    public int getAOE(ItemStack stack) {
        if (!NBTHelper.containerNBTIsNotNull(stack)) {
            NBTHelper.setDefaultContainerNBT(stack, PRIMAL_TOOL_AOE, 0);
        }
        return stack.getTagCompound().getInteger(PRIMAL_TOOL_AOE);
    }

    private boolean seekMinerals(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float offsetX, float offsetY, float offsetZ) {
        if (!world.isRemote) {
            world.playSoundEffect((double) x + 0.5, (double) y + 0.5, (double) z + 0.5, "thaumcraft:wandfail", 0.2F, 0.2F + world.rand.nextFloat() * 0.2F);
        } else {
            Thaumcraft.instance.renderEventHandler.startScan(player, x, y, z, System.currentTimeMillis() + 5000L, 8);
            player.swingItem();
            itemStack.damageItem(world.rand.nextInt(5), player);
        }
        return super.onItemUse(itemStack, player, world, x, y, z, side, offsetX, offsetY, offsetZ);
    }

    private boolean growPlantsOrHoe(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float offsetX, float offsetY, float offsetZ) {
        int currentAOE = itemStack.stackTagCompound.getInteger(PRIMAL_TOOL_AOE);
        int currentSubMode = itemStack.stackTagCompound.getInteger(PRIMAL_TOOL_SUB_MODE);
        if (player.isSneaking() && currentSubMode == 0) {
            return hoeInArea(itemStack, player, world, x, y, z, side, currentAOE);
        } else {
            boolean did = false;

            int md;
            for (int xx = -1; xx <= 1; ++xx) {
                for (md = -1; md <= 1; ++md) {
                    if (super.onItemUse(itemStack, player, world, x + xx, y, z + md, side, offsetX, offsetY, offsetZ)) {
                        Thaumcraft.proxy.blockSparkle(world, x + xx, y, z + md, 8401408, 2);
                        if (!did) {
                            did = true;
                        }
                    }
                }
            }

            if (!did) {
                did = Utils.useBonemealAtLoc(world, player, x, y, z);
                if (!did) {
                    Block bi = world.getBlock(x, y, z);
                    md = world.getBlockMetadata(x, y, z);
                    if (bi == ConfigBlocks.blockCustomPlant && md == 0 && itemStack.getItemDamage() + 20 <= itemStack.getMaxDamage()) {
                        ((BlockCustomPlant) bi).growGreatTree(world, x, y, z, world.rand);
                        itemStack.damageItem(world.rand.nextInt(5), player);
                        Thaumcraft.proxy.blockSparkle(world, x, y, z, 0, 2);
                        did = true;
                    } else if (bi == ConfigBlocks.blockCustomPlant && md == 1 && itemStack.getItemDamage() + 150 <= itemStack.getMaxDamage()) {
                        ((BlockCustomPlant) bi).growSilverTree(world, x, y, z, world.rand);
                        itemStack.damageItem(world.rand.nextInt(25), player);
                        Thaumcraft.proxy.blockSparkle(world, x, y, z, 0, 2);
                        did = true;
                    }
                } else {
                    itemStack.damageItem(1, player);
                    Thaumcraft.proxy.blockSparkle(world, x, y, z, 0, 3);
                }

                if (did) {
                    world.playSoundEffect((double) x + 0.5, (double) y + 0.5, (double) z + 0.5, "thaumcraft:wand", 0.75F, 0.9F + world.rand.nextFloat() * 0.2F);
                }
            }
            return did;
        }
    }

    @Override
    public boolean isFull3D() {
        return true;
    }
}

package com.gabid.ezaciancraft.common.blocks.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.StatCollector;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;
import thaumcraft.common.tiles.TileAlembic;
import thaumcraft.common.tiles.TileBellows;

import static com.gabid.ezaciancraft.api.EzacianCraftGeneralLang.UNLOCALE_SHADOW_ALCHEMY_FURNACE;
import static net.minecraft.tileentity.TileEntityFurnace.isItemFuel;
import static net.minecraftforge.common.util.ForgeDirection.UP;

public class TileEntityShadowAlchemyFurnace extends TileThaumcraft implements ISidedInventory {
    //sided container and itemstack interface
    private static final int[] inputSidesSlots = new int[]{0, 1};
    //essentia
    public AspectList visAspects = new AspectList();
    protected int alembicDistillationHeightLimit = 8; //how many alembics the furnace can handle and fill
    protected int essentiaMaxVis = 512;
    protected int essentiaAmountVis = 0;
    //item smelting and what
    protected boolean isBurning = false;
    protected boolean isProcessing = false;
    protected int furnaceBurnTime = 0;
    protected int maxFurnaceBurnTime;
    protected int furnaceCookTime = 0;
    protected int maxFurnaceCookTime;
    protected float speedBurnMultiplier = 1;
    //extra
    private int ticks = 0;
    private int bellowsAmount = 0;
    private String customName;
    private ItemStack[] furnaceItemStacks = new ItemStack[2];

    public TileEntityShadowAlchemyFurnace() {
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtCompound) {
        super.readFromNBT(nbtCompound);
        NBTTagList itemStackNBT = nbtCompound.getTagList("Items", 10);
        this.furnaceItemStacks = new ItemStack[2];

        for (int i = 0; i < itemStackNBT.tagCount(); i++) {
            NBTTagCompound t = itemStackNBT.getCompoundTagAt(i);
            int index = t.getByte("Index");
            if (index >= 0 && index < this.furnaceItemStacks.length) {
                this.furnaceItemStacks[index] = ItemStack.loadItemStackFromNBT(t);
            }
        }
        this.speedBurnMultiplier = nbtCompound.getFloat("SpeedBurnBoost");
        this.furnaceCookTime = nbtCompound.getInteger("CookTime");
        this.maxFurnaceCookTime = nbtCompound.getInteger("MaxCookTime");

        this.visAspects.readFromNBT(nbtCompound);
        this.customName = nbtCompound.getString("CustomName");
        this.essentiaAmountVis = this.visAspects.visSize();
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setFloat("SpeedBurnBoost", this.speedBurnMultiplier);
        nbttagcompound.setInteger("CookTime", this.furnaceCookTime);
        nbttagcompound.setInteger("MaxCookTime", this.maxFurnaceCookTime);
        NBTTagList itemStackNBT = new NBTTagList();

        for (int i = 0; i < this.furnaceItemStacks.length; i++) {
            ItemStack stack = this.furnaceItemStacks[i];
            if (stack != null) {
                NBTTagCompound t = new NBTTagCompound();
                stack.writeToNBT(t);
                t.setByte("Index", (byte) i);
                itemStackNBT.appendTag(t);
            }
        }
        nbttagcompound.setTag("Items", itemStackNBT);

        if (this.hasCustomInventoryName()) {
            nbttagcompound.setString("CustomName", this.customName);
        }

        this.visAspects.writeToNBT(nbttagcompound);
    }

    //nbt what thaumcraft registers after the base methods are called
    @Override
    public void readCustomNBT(NBTTagCompound nbttagcompound) {
        this.furnaceBurnTime = nbttagcompound.getInteger("BurnTime");
        this.maxFurnaceBurnTime = nbttagcompound.getInteger("MaxBurnTime");
        this.essentiaAmountVis = nbttagcompound.getInteger("Vis");
    }

    @Override
    public void writeCustomNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound.setInteger("BurnTime", this.furnaceBurnTime);
        nbttagcompound.setInteger("MaxBurnTime", this.maxFurnaceBurnTime);
        nbttagcompound.setInteger("Vis", this.essentiaAmountVis);
    }

    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        if (this.worldObj != null) {
            this.worldObj.updateLightByType(EnumSkyBlock.Block, this.xCoord, this.yCoord, this.zCoord);
        }
    }

    //inventory interface stuff
    @Override
    public int[] getAccessibleSlotsFromSide(int slot) {
        return inputSidesSlots;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        if (side == UP.ordinal()) {
            return false;
        } else {
            return this.isItemValidForSlot(slot, stack);
        }
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return side != 0 || slot != 0 || stack.getItem() == Items.bucket;
    }

    @Override
    public int getSizeInventory() {
        return this.furnaceItemStacks.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return this.furnaceItemStacks[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int decrement) {
        if (this.getStackInSlot(slot) != null) {
            ItemStack itemstack;

            if (this.getStackInSlot(slot).stackSize <= decrement) {
                itemstack = this.getStackInSlot(slot);
                this.setInventorySlotContents(slot, null);
            } else {
                itemstack = this.getStackInSlot(slot).splitStack(decrement);

                if (this.getStackInSlot(slot).stackSize == 0) {
                    this.setInventorySlotContents(slot, null);
                }

            }
            markDirty();
            return itemstack;
        } else {
            markDirty();
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        if (this.getStackInSlot(slot) != null) {
            ItemStack itemstack = this.getStackInSlot(slot);
            this.furnaceItemStacks[slot] = null;
            return itemstack;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        this.furnaceItemStacks[slot] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
            stack.stackSize = this.getInventoryStackLimit();
        }

        worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        markDirty();
    }

    @Override
    public String getInventoryName() {
        return this.hasCustomInventoryName() ? this.customName : StatCollector.translateToLocal("container." + UNLOCALE_SHADOW_ALCHEMY_FURNACE + ".title");
    }

    @Override
    public boolean hasCustomInventoryName() {
        return this.customName != null && !this.customName.isEmpty();
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        if (slot == 0) {
            AspectList al = ThaumcraftCraftingManager.getObjectTags(stack);
            al = ThaumcraftCraftingManager.getBonusTags(stack, al);
            if (al.size() > 0) {
                return true;
            }
        }
        return slot == 1 && isItemFuel(stack);
    }

    public Aspect takeRandomAspect(AspectList exlude) {
        if (this.visAspects.size() > 0) {
            AspectList temp = this.visAspects.copy();
            if (exlude.size() > 0) {
                Aspect[] arr$ = exlude.getAspects();
                for (Aspect a : arr$) {
                    temp.remove(a);
                }
            }

            if (temp.size() > 0) {
                Aspect tag = temp.getAspects()[this.worldObj.rand.nextInt(temp.getAspects().length)];
                this.visAspects.remove(tag, 1);
                --this.essentiaAmountVis;
                return tag;
            }
        }

        return null;
    }

    public boolean takeFromContainer(Aspect tag, int amount) {
        if (this.visAspects != null && this.visAspects.getAmount(tag) >= amount) {
            this.visAspects.remove(tag, amount);
            this.essentiaAmountVis -= amount;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void updateEntity() {
        this.ticks++;

        if (!this.worldObj.isRemote) {
            //reset timers
            if (this.bellowsAmount <= 0) {
                this.getBellows();
                this.speedBurnMultiplier = 1;
            } else {
                this.speedBurnMultiplier = (float) (this.bellowsAmount * 2.5);
            }

            this.processFuelAndResult();
            this.processEssentia();
        }
    }

    //manages the burn and the processing of the furnace
    private void processFuelAndResult() {
        ItemStack fuelItemStack = this.getStackInSlot(1);
        ItemStack itemStackToProcess = this.getStackInSlot(0);
        AspectList itemStackToProcessAspects = ThaumcraftCraftingManager.getObjectTags(itemStackToProcess);

        if (this.furnaceBurnTime <= 0 && itemStackToProcess != null && fuelItemStack != null && TileEntityFurnace.isItemFuel(fuelItemStack)) {
            this.setMaxFurnaceBurnTime(TileEntityFurnace.getItemBurnTime(fuelItemStack));
            this.setFurnaceBurnTime(this.getMaxFurnaceBurnTime());
            fuelItemStack.stackSize--;

            if (this.furnaceItemStacks[1].stackSize <= 0) {
                this.furnaceItemStacks[1] = this.furnaceItemStacks[1].getItem().getContainerItem(this.furnaceItemStacks[1]);
            }

            this.markDirty();
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        } else if (fuelItemStack == null) {
            if (this.furnaceBurnTime <= 0) {
                this.setFurnaceBurnTime(0);
                this.setMaxFurnaceBurnTime(0);
                this.markDirty();
                this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            }
        }
        if (this.furnaceBurnTime > 0 && this.maxFurnaceBurnTime > 0) {
            this.furnaceBurnTime--;

            if (itemStackToProcess != null) {
                if (itemStackToProcessAspects != null && this.furnaceBurnTime > 0) {
                    AspectList metaVis = ThaumcraftCraftingManager.getBonusTags(itemStackToProcess, itemStackToProcessAspects);
                    int visItemAmount = metaVis.visSize();
                    if (this.essentiaAmountVis + visItemAmount > this.essentiaMaxVis) {
                        return;
                    } else {
                        if (this.bellowsAmount > 1) {
                            this.maxFurnaceCookTime = (int) ((float) (visItemAmount * 10) * (1.0F - 0.125F)) / (int) this.speedBurnMultiplier;
                        } else {
                            this.maxFurnaceCookTime = (int) ((float) (visItemAmount * 10) * (1.0F - 0.125F));
                        }

                        this.furnaceCookTime++;
                        if (this.furnaceCookTime >= this.maxFurnaceCookTime) {
                            this.setFurnaceCookTime(0);
                            itemStackToProcess.stackSize--;

                            if (this.furnaceItemStacks[0].stackSize <= 0) {
                                this.furnaceItemStacks[0] = this.furnaceItemStacks[0].getItem().getContainerItem(this.furnaceItemStacks[0]);
                            }

                            for (Aspect aspect : metaVis.getAspects()) {
                                this.visAspects.add(aspect, metaVis.getAmount(aspect));
                            }

                            this.essentiaAmountVis = this.visAspects.visSize();
                        }
                    }
                }
            } else {
                this.setFurnaceCookTime(0);
                this.setMaxFurnaceCookTime(0);
                this.markDirty();
            }

            //EzacianNetworkHandler.sendToServer(new PacketUpdateShadowAlchemyFurnaceData(this.furnaceBurnTime, this.furnaceCookTime, this.essentiaAmountVis));
            this.markDirty();
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        }
    }

    //manages the transfer of essentia to the alembic or other stuff
    private void processEssentia() {
        int expulsionSpeed = 1;
        if (this.bellowsAmount > 0) {
            expulsionSpeed = expulsionSpeed + this.bellowsAmount;
        }
        if (this.ticks % (20 / expulsionSpeed) == 0 && this.visAspects.size() > 0) {
            AspectList aspectsAlembicDummy = new AspectList();

            TileEntity te;
            TileAlembic alembicTE;
            int alembicHeight = 0;
            while (alembicHeight < this.alembicDistillationHeightLimit) {
                alembicHeight++;
                te = this.worldObj.getTileEntity(this.xCoord, this.yCoord + alembicHeight, this.zCoord);
                if (!(te instanceof TileAlembic)) {
                    break;
                } else {
                    alembicTE = (TileAlembic) te;

                    if (alembicTE.aspect != null && alembicTE.amount < alembicTE.maxAmount && this.visAspects.getAmount(alembicTE.aspect) > 0) {
                        this.takeFromContainer(alembicTE.aspect, 1);
                        alembicTE.addToContainer(alembicTE.aspect, 1);
                        aspectsAlembicDummy.merge(alembicTE.aspect, 1);
                        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
                        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord + alembicHeight, this.zCoord);
                    }
                }
            }

            alembicHeight = 0;

            refill:
            while (true) {
                do {
                    if (alembicHeight >= this.alembicDistillationHeightLimit) {
                        break refill;
                    }

                    ++alembicHeight;

                    te = this.worldObj.getTileEntity(this.xCoord, this.yCoord + alembicHeight, this.zCoord);
                    if (!(te instanceof TileAlembic)) {
                        break refill;
                    }
                    alembicTE = (TileAlembic) te;

                } while (alembicTE.aspect != null && alembicTE.amount != 0);

                Aspect as = null;
                if (alembicTE.aspectFilter == null) {
                    as = this.takeRandomAspect(aspectsAlembicDummy);
                } else if (this.takeFromContainer(alembicTE.aspectFilter, 1)) {
                    as = alembicTE.aspectFilter;
                }

                if (as != null) {
                    alembicTE.addToContainer(as, 1);
                    this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
                    this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord + alembicHeight, this.zCoord);
                    break;
                }
            }
        }
    }

    public boolean isBurning() {
        return this.isBurning;
    }

    public void setBurning(boolean burning) {
        this.isBurning = burning;
    }

    public boolean isProcessing() {
        return this.isProcessing;
    }

    public void setProcessing(boolean processing) {
        this.isProcessing = processing;
    }

    public int getFurnaceBurnTime() {
        return this.furnaceBurnTime;
    }

    public void setFurnaceBurnTime(int furnaceBurnTime) {
        this.furnaceBurnTime = furnaceBurnTime;
    }

    public int getFurnaceCookTime() {
        return this.furnaceCookTime;
    }

    public void setFurnaceCookTime(int furnaceCookTime) {
        this.furnaceCookTime = furnaceCookTime;
    }

    public float getSpeedBurnMultiplier() {
        return this.speedBurnMultiplier;
    }

    public void setSpeedBurnMultiplier(float speedBurnMultiplier) {
        this.speedBurnMultiplier = speedBurnMultiplier;
    }

    public int getBellowsAmount() {
        return this.bellowsAmount;
    }

    public void setBellowsAmount(int bellowsAmount) {
        this.bellowsAmount = bellowsAmount;
    }

    public int getEssentiaAmountVis() {
        return this.essentiaAmountVis;
    }

    public void setEssentiaAmountVis(int essentiaAmountVis) {
        this.essentiaAmountVis = essentiaAmountVis;
    }

    public int getEssentiaMaxVis() {
        return this.essentiaMaxVis;
    }

    public void setEssentiaMaxVis(int essentiaMaxVis) {
        this.essentiaMaxVis = essentiaMaxVis;
    }

    public int getMaxFurnaceBurnTime() {
        return this.maxFurnaceBurnTime;
    }

    public void setMaxFurnaceBurnTime(int maxFurnaceBurnTime) {
        this.maxFurnaceBurnTime = maxFurnaceBurnTime;
    }

    public int getMaxFurnaceCookTime() {
        return this.maxFurnaceCookTime;
    }

    public void setMaxFurnaceCookTime(int maxFurnaceCookTime) {
        this.maxFurnaceCookTime = maxFurnaceCookTime;
    }

    public AspectList getVisAspects() {
        return this.visAspects;
    }

    public void getBellows() {
        this.bellowsAmount = TileBellows.getBellows(this.worldObj, this.xCoord, this.yCoord, this.zCoord, ForgeDirection.VALID_DIRECTIONS);
    }
}
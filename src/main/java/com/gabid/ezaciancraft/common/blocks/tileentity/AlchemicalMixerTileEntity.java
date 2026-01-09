package com.gabid.ezaciancraft.common.blocks.tileentity;

import com.gabid.ezaciancraft.api.aspects.AspectHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.common.blocks.BlockJar;

import static com.gabid.ezaciancraft.api.EzacianCraftNBTConstants.*;

public class AlchemicalMixerTileEntity extends TileThaumcraft implements IEssentiaTransport, IAspectContainer {

    public Aspect aspectInput1 = null;
    public Aspect aspectInput2 = null;
    public Aspect aspectOutput = null;

    public int metaFacing = 3;
    public ForgeDirection facing = null;

    private int ticks = 0;
    private int aspectProcessingTime = 0;
    public float rotationSpeed = 0f;
    public int whiskerRotation = 0;

    public boolean isFirstRun = true;

    public AlchemicalMixerTileEntity() {
    }

    @Override
    public void writeCustomNBT(NBTTagCompound nbttagcompound) {
        if(this.aspectInput1 != null)
            nbttagcompound.setString(FIRST_ASPECT_IN, this.aspectInput1.getTag());

        if(this.aspectInput2 != null)
            nbttagcompound.setString(SECOND_ASPECT_IN, this.aspectInput2.getTag());

        if(this.aspectOutput != null)
            nbttagcompound.setString(OUTPUT_ASPECT, this.aspectOutput.getTag());

        nbttagcompound.setInteger(TE_META_FACING, (byte) this.metaFacing);

        super.writeCustomNBT(nbttagcompound);
    }

    @Override
    public void readCustomNBT(NBTTagCompound nbttagcompound) {
        this.aspectInput1 = Aspect.getAspect(nbttagcompound.getString(FIRST_ASPECT_IN));
        this.aspectInput2 = Aspect.getAspect(nbttagcompound.getString(SECOND_ASPECT_IN));
        this.aspectOutput = Aspect.getAspect(nbttagcompound.getString(OUTPUT_ASPECT));
        this.metaFacing = nbttagcompound.getInteger(TE_META_FACING);
        this.facing = ForgeDirection.getOrientation(this.metaFacing);

        super.readCustomNBT(nbttagcompound);
    }

    //Pipe

    private boolean getConnectableInput(ForgeDirection forgeDirection) {
        //I hate my existence :(
        if(this.metaFacing == 2 || this.metaFacing == 3) {
            if (forgeDirection == ForgeDirection.EAST) {
                return true;
            } else if (forgeDirection == ForgeDirection.WEST) {
                return true;
            } else if (forgeDirection == ForgeDirection.UNKNOWN) {
                return false;
            }
        }

        if(this.metaFacing == 4 || this.metaFacing == 5) {
            if (forgeDirection == ForgeDirection.NORTH) {
                return true;
            } else if (forgeDirection == ForgeDirection.SOUTH) {
                return true;
            } else if (forgeDirection == ForgeDirection.UNKNOWN) {
                return false;
            }
        }

        return false;
    }

    private int getSuctionInInputPipes(ForgeDirection forgeDirection) {
        //I hate my existence :(
        if(!this.isGettingRedstonePower()) {
            if (this.metaFacing == 2 || this.metaFacing == 3) {
                if (forgeDirection == ForgeDirection.EAST) {
                    return 128;
                } else if (forgeDirection == ForgeDirection.WEST) {
                    return 128;
                } else if (forgeDirection == ForgeDirection.UNKNOWN) {
                    return 0;
                }
            }

            if (this.metaFacing == 4 || this.metaFacing == 5) {
                if (forgeDirection == ForgeDirection.NORTH) {
                    return 128;
                } else if (forgeDirection == ForgeDirection.SOUTH) {
                    return 128;
                } else if (forgeDirection == ForgeDirection.UNKNOWN) {
                    return 0;
                }
            }
        }

        return 0;
    }

    @Override
    public boolean isConnectable(ForgeDirection forgeDirection) {
        if(forgeDirection == ForgeDirection.DOWN) {
            return true;
        } else {
            return this.getConnectableInput(forgeDirection);
        }
    }

    @Override
    public boolean canInputFrom(ForgeDirection forgeDirection) {
        return this.getConnectableInput(forgeDirection);
    }

    @Override
    public boolean canOutputTo(ForgeDirection forgeDirection) {
        return forgeDirection == ForgeDirection.DOWN;
    }

    @Override
    public void setSuction(Aspect aspect, int i) {

    }

    @Override
    public Aspect getSuctionType(ForgeDirection forgeDirection) {
        return null;
    }

    @Override
    public int getSuctionAmount(ForgeDirection forgeDirection) {
        return this.getSuctionInInputPipes(forgeDirection);
    }

    @Override
    public int takeEssentia(Aspect aspect, int amount, ForgeDirection forgeDirection) {
        return this.canOutputTo(forgeDirection) && this.takeFromContainer(aspect, amount) ? amount : 0;
    }

    @Override
    public int addEssentia(Aspect aspect, int i, ForgeDirection forgeDirection) {
        if(this.aspectInput1 == null) {
            this.aspectInput1 = aspect;
            this.markDirty();
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            return 1;
        } else if(this.aspectInput2 == null) {
            this.aspectInput2 = aspect;
            this.markDirty();
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            return 1;
        }
        return 0;
    }

    @Override
    public Aspect getEssentiaType(ForgeDirection forgeDirection) {
        return this.aspectOutput;
    }

    @Override
    public int getEssentiaAmount(ForgeDirection forgeDirection) {
        return 0;
    }

    @Override
    public int getMinimumSuction() {
        return 0;
    }

    @Override
    public boolean renderExtendedTube() {
        return false;
    }

    //Container

    @Override
    public AspectList getAspects() {
        AspectList aspectList = new AspectList();
        if (this.aspectOutput != null) {
            aspectList.add(this.aspectOutput, 1);
        }
        if (this.aspectInput1 != null) {
            aspectList.add(this.aspectInput1, 1);
        }
        if (this.aspectInput2 != null) {
            aspectList.add(this.aspectInput2, 1);
        }
        return aspectList;
    }

    @Override
    public void setAspects(AspectList aspectList) {

    }

    @Override
    public boolean doesContainerAccept(Aspect aspect) {
        return true;
    }

    @Override
    public int addToContainer(Aspect aspect, int amount) {
        if (amount > 0 && this.aspectOutput == null) {
            this.aspectOutput = aspect;
            this.markDirty();
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            --amount;
        }

        return amount;
    }

    @Override
    public boolean takeFromContainer(Aspect aspect, int amount) {
        if (this.aspectOutput != null && aspect == this.aspectOutput) {
            this.aspectOutput = null;
            this.markDirty();
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean takeFromContainer(AspectList aspectList) {
        return false;
    }

    @Override
    public boolean doesContainerContainAmount(Aspect aspect, int amount) {
        return amount == 1 && aspect == this.aspectOutput;
    }

    @Override
    public boolean doesContainerContain(AspectList aspectList) {
        Aspect[] aspects = aspectList.getAspects();

        for (Aspect aspectToCompare : aspects) {
            if (aspectToCompare == this.aspectOutput) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int containerContains(Aspect aspect) {
        return aspect == this.aspectOutput ? 1 : 0;
    }

    @Override
    public void updateEntity() {
        if(!this.worldObj.isRemote) {
            if(!this.isGettingRedstonePower()) {
                if (++this.ticks % 5 == 0) {
                    if (this.aspectInput1 == null || this.aspectInput2 == null) {
                        this.drawEssentiaFromInputPipes();
                    }
                }
                if(++this.ticks % 20 == 0) {
                    if(this.aspectInput1 != null && this.aspectInput2 != null && this.aspectOutput == null && this.aspectProcessingTime == 0) {
                        this.aspectProcessingTime = 8;
                    } else if(this.aspectInput1 == null || this.aspectInput2 == null) {
                        this.aspectProcessingTime = 8;
                        return;
                    } else if(!AspectHelper.compoundExists(this.aspectInput1, this.aspectInput2)) {
                        this.aspectProcessingTime = 8;
                        return;
                    }

                    if(this.aspectProcessingTime > 0) {
                        this.aspectProcessingTime--;
                    }

                    if(this.aspectProcessingTime == 0 && this.aspectOutput == null) {
                        this.processEssentiaMixing();
                        this.aspectProcessingTime = 8;
                    } else {
                        return;
                    }
                }
            }
        } else {
            if (this.aspectInput1 != null && this.aspectInput2 != null && !this.isGettingRedstonePower() && this.rotationSpeed < 20.0F) {
                this.rotationSpeed += 2.0F;
            }

            if ((this.aspectInput1 == null || this.aspectInput2 == null || this.isGettingRedstonePower()) && this.rotationSpeed > 0.0F) {
                this.rotationSpeed -= 0.5F;
            }
        }

        super.updateEntity();
    }

    //functional methods - please,someone viewing this, please don kill me... :skull:
    private void drawEssentiaFromInputPipes() {
        TileEntity pipeInputA;
        TileEntity pipeInputB;

        if(this.metaFacing == 2) {
            pipeInputA = ThaumcraftApiHelper.getConnectableTile(this.worldObj, this.xCoord, this.yCoord, this.zCoord, ForgeDirection.EAST);
            pipeInputB = ThaumcraftApiHelper.getConnectableTile(this.worldObj, this.xCoord, this.yCoord, this.zCoord, ForgeDirection.WEST);

            if(pipeInputA != null) {
                IEssentiaTransport pipe = (IEssentiaTransport) pipeInputA;
                if(!pipe.canOutputTo(ForgeDirection.WEST)) return;

                Aspect pipeAspect = null;
                //primero comprueba si la escencia de la tuberia es mayor de cero, luego se revisa si la succión es menor a la del bloque fuente y al final si la fuente es mayor que la tuberia a succionar --sorry to much latino spanish :eyes:
                if(pipe.getEssentiaAmount(ForgeDirection.WEST) > 0 && pipe.getSuctionAmount(ForgeDirection.WEST) < this.getSuctionAmount(ForgeDirection.EAST) && this.getSuctionAmount(ForgeDirection.EAST) >= pipe.getMinimumSuction()) {
                    pipeAspect = pipe.getEssentiaType(ForgeDirection.WEST);
                }
                if(pipeAspect != null && this.aspectInput1 == null && pipe.getSuctionAmount(ForgeDirection.WEST) < this.getSuctionAmount(ForgeDirection.EAST) && pipe.takeEssentia(pipeAspect, 1, ForgeDirection.WEST) == 1) {
                    this.aspectInput1 = pipeAspect;
                    this.markDirty();
                    this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
                }
            }
            if(pipeInputB != null) {
                IEssentiaTransport pipe = (IEssentiaTransport) pipeInputB;
                if(!pipe.canOutputTo(ForgeDirection.EAST)) return;

                Aspect pipeAspect = null;
                if(pipe.getEssentiaAmount(ForgeDirection.EAST) > 0 && pipe.getSuctionAmount(ForgeDirection.EAST) < this.getSuctionAmount(ForgeDirection.WEST) && this.getSuctionAmount(ForgeDirection.WEST) >= pipe.getMinimumSuction()) {
                    pipeAspect = pipe.getEssentiaType(ForgeDirection.WEST);
                }
                if(pipeAspect != null && this.aspectInput2 == null && pipe.getSuctionAmount(ForgeDirection.EAST) < this.getSuctionAmount(ForgeDirection.WEST) && pipe.takeEssentia(pipeAspect, 1, ForgeDirection.EAST) == 1) {
                    this.aspectInput2 = pipeAspect;
                    this.markDirty();
                    this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
                }
            }

        } else if(this.metaFacing == 3) {
            pipeInputA = ThaumcraftApiHelper.getConnectableTile(this.worldObj, this.xCoord, this.yCoord, this.zCoord, ForgeDirection.WEST);
            pipeInputB = ThaumcraftApiHelper.getConnectableTile(this.worldObj, this.xCoord, this.yCoord, this.zCoord, ForgeDirection.EAST);

            if(pipeInputA != null) {
                IEssentiaTransport pipe = (IEssentiaTransport) pipeInputA;
                if(!pipe.canOutputTo(ForgeDirection.EAST)) return;

                Aspect pipeAspect = null;
                if(pipe.getEssentiaAmount(ForgeDirection.EAST) > 0 && pipe.getSuctionAmount(ForgeDirection.EAST) < this.getSuctionAmount(ForgeDirection.WEST) && this.getSuctionAmount(ForgeDirection.WEST) >= pipe.getMinimumSuction()) {
                    pipeAspect = pipe.getEssentiaType(ForgeDirection.WEST);
                }
                if(pipeAspect != null && this.aspectInput1 == null && pipe.getSuctionAmount(ForgeDirection.EAST) < this.getSuctionAmount(ForgeDirection.WEST) && pipe.takeEssentia(pipeAspect, 1, ForgeDirection.EAST) == 1) {
                    this.aspectInput1 = pipeAspect;
                    this.markDirty();
                    this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
                }
            }
            if(pipeInputB != null) {
                IEssentiaTransport pipe = (IEssentiaTransport) pipeInputB;
                if(!pipe.canOutputTo(ForgeDirection.WEST)) return;

                Aspect pipeAspect = null;
                if(pipe.getEssentiaAmount(ForgeDirection.WEST) > 0 && pipe.getSuctionAmount(ForgeDirection.WEST) < this.getSuctionAmount(ForgeDirection.EAST) && this.getSuctionAmount(ForgeDirection.EAST) >= pipe.getMinimumSuction()) {
                    pipeAspect = pipe.getEssentiaType(ForgeDirection.WEST);
                }
                if(pipeAspect != null && this.aspectInput2 == null && pipe.getSuctionAmount(ForgeDirection.WEST) < this.getSuctionAmount(ForgeDirection.EAST) && pipe.takeEssentia(pipeAspect, 1, ForgeDirection.WEST) == 1) {
                    this.aspectInput2 = pipeAspect;
                    this.markDirty();
                    this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
                }
            }
        } else if(this.metaFacing == 4) {
            pipeInputA = ThaumcraftApiHelper.getConnectableTile(this.worldObj, this.xCoord, this.yCoord, this.zCoord, ForgeDirection.NORTH);
            pipeInputB = ThaumcraftApiHelper.getConnectableTile(this.worldObj, this.xCoord, this.yCoord, this.zCoord, ForgeDirection.SOUTH);

            if(pipeInputA != null) {
                IEssentiaTransport pipe = (IEssentiaTransport) pipeInputA;
                if(!pipe.canOutputTo(ForgeDirection.SOUTH)) return;

                Aspect pipeAspect = null;
                if(pipe.getEssentiaAmount(ForgeDirection.SOUTH) > 0 && pipe.getSuctionAmount(ForgeDirection.SOUTH) < this.getSuctionAmount(ForgeDirection.NORTH) && this.getSuctionAmount(ForgeDirection.NORTH) >= pipe.getMinimumSuction()) {
                    pipeAspect = pipe.getEssentiaType(ForgeDirection.SOUTH);
                }
                if(pipeAspect != null && this.aspectInput1 == null && pipe.getSuctionAmount(ForgeDirection.SOUTH) < this.getSuctionAmount(ForgeDirection.NORTH) && pipe.takeEssentia(pipeAspect, 1, ForgeDirection.SOUTH) == 1) {
                    this.aspectInput1 = pipeAspect;
                    this.markDirty();
                    this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
                }
            }
            if(pipeInputB != null) {
                IEssentiaTransport pipe = (IEssentiaTransport) pipeInputB;
                if(!pipe.canOutputTo(ForgeDirection.NORTH)) return;

                Aspect pipeAspect = null;
                if(pipe.getEssentiaAmount(ForgeDirection.NORTH) > 0 && pipe.getSuctionAmount(ForgeDirection.NORTH) < this.getSuctionAmount(ForgeDirection.SOUTH) && this.getSuctionAmount(ForgeDirection.SOUTH) >= pipe.getMinimumSuction()) {
                    pipeAspect = pipe.getEssentiaType(ForgeDirection.SOUTH);
                }
                if(pipeAspect != null && this.aspectInput2 == null && pipe.getSuctionAmount(ForgeDirection.NORTH) < this.getSuctionAmount(ForgeDirection.SOUTH) && pipe.takeEssentia(pipeAspect, 1, ForgeDirection.NORTH) == 1) {
                    this.aspectInput2 = pipeAspect;
                    this.markDirty();
                    this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
                }
            }
        } else if(this.metaFacing == 5) {
            pipeInputA = ThaumcraftApiHelper.getConnectableTile(this.worldObj, this.xCoord, this.yCoord, this.zCoord, ForgeDirection.SOUTH);
            pipeInputB = ThaumcraftApiHelper.getConnectableTile(this.worldObj, this.xCoord, this.yCoord, this.zCoord, ForgeDirection.NORTH);

            if(pipeInputA != null) {
                IEssentiaTransport pipe = (IEssentiaTransport) pipeInputA;
                if(!pipe.canOutputTo(ForgeDirection.NORTH)) return;

                Aspect pipeAspect = null;
                if(pipe.getEssentiaAmount(ForgeDirection.NORTH) > 0 && pipe.getSuctionAmount(ForgeDirection.NORTH) < this.getSuctionAmount(ForgeDirection.SOUTH) && this.getSuctionAmount(ForgeDirection.SOUTH) >= pipe.getMinimumSuction()) {
                    pipeAspect = pipe.getEssentiaType(ForgeDirection.SOUTH);
                }
                if(pipeAspect != null && this.aspectInput1 == null && pipe.getSuctionAmount(ForgeDirection.NORTH) < this.getSuctionAmount(ForgeDirection.SOUTH) && pipe.takeEssentia(pipeAspect, 1, ForgeDirection.NORTH) == 1) {
                    this.aspectInput1 = pipeAspect;
                    this.markDirty();
                    this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
                }
            }
            if(pipeInputB != null) {
                IEssentiaTransport pipe = (IEssentiaTransport) pipeInputB;
                if(!pipe.canOutputTo(ForgeDirection.SOUTH)) return;

                Aspect pipeAspect = null;
                if(pipe.getEssentiaAmount(ForgeDirection.SOUTH) > 0 && pipe.getSuctionAmount(ForgeDirection.SOUTH) < this.getSuctionAmount(ForgeDirection.NORTH) && this.getSuctionAmount(ForgeDirection.NORTH) >= pipe.getMinimumSuction()) {
                    pipeAspect = pipe.getEssentiaType(ForgeDirection.SOUTH);
                }
                if(pipeAspect != null && this.aspectInput2 == null && pipe.getSuctionAmount(ForgeDirection.SOUTH) < this.getSuctionAmount(ForgeDirection.NORTH) && pipe.takeEssentia(pipeAspect, 1, ForgeDirection.SOUTH) == 1) {
                    this.aspectInput2 = pipeAspect;
                    this.markDirty();
                    this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
                }
            }
        }
    }

    private void processEssentiaMixing() {
        if(AspectHelper.compoundExists(this.aspectInput1, this.aspectInput2)) {
            this.aspectOutput = AspectHelper.getCompound(this.aspectInput1, this.aspectInput2);
            this.aspectInput1 = null;
            this.aspectInput2 = null;
        } else {
            this.aspectOutput = null;
        }
        this.markDirty();
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    }

    private boolean isGettingRedstonePower() {
        return this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord,this.yCoord,this.zCoord);
    }
}

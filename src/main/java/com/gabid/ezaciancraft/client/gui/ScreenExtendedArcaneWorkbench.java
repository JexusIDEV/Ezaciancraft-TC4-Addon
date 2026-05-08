package com.gabid.ezaciancraft.client.gui;

import com.gabid.ezaciancraft.common.blocks.container.ContainerExtendedArcaneWorkbench;
import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityExtendedArcaneWorkbench;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;

import java.util.ArrayList;

import static com.gabid.ezaciancraft.CoreMod.MODID;

@SideOnly(Side.CLIENT)
public class ScreenExtendedArcaneWorkbench extends GuiContainer {
    private static final String GUI_TEXTURE_PATH = "textures/gui/extendedArcaneWorkbench_gui.png";
    public final TileEntityExtendedArcaneWorkbench arcaneWorkbenchTE;
    private final InventoryPlayer inventoryPlayer;
    private final int[][] aspectLocs = new int[][]{{72, 21}, {24, 43}, {24, 102}, {72, 124}, {120, 102}, {120, 43}};
    ArrayList<Aspect> primals = Aspect.getPrimalAspects();

    public ScreenExtendedArcaneWorkbench(InventoryPlayer _inventoryPlayer, TileEntityExtendedArcaneWorkbench _arcaneWorkbenchTE) {
        super(new ContainerExtendedArcaneWorkbench(_inventoryPlayer, _arcaneWorkbenchTE));
        this.inventoryPlayer = _inventoryPlayer;
        this.arcaneWorkbenchTE = _arcaneWorkbenchTE;
        this.xSize = 192;
        this.ySize = 234;
    }

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float ticks, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        UtilsFX.bindTexture(MODID, GUI_TEXTURE_PATH);
        GL11.glEnable(GL11.GL_BLEND);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        GL11.glDisable(GL11.GL_BLEND);
        ItemWandCasting wand = null;
        if (this.arcaneWorkbenchTE.func_70301_a(10) != null && this.arcaneWorkbenchTE.func_70301_a(10).getItem() instanceof ItemWandCasting) {
            wand = (ItemWandCasting) this.arcaneWorkbenchTE.func_70301_a(10).getItem();
        }

        AspectList cost;
        if (ThaumcraftCraftingManager.findMatchingArcaneRecipe(this.arcaneWorkbenchTE, this.inventoryPlayer.player) != null) {
            cost = ThaumcraftCraftingManager.findMatchingArcaneRecipeAspects(this.arcaneWorkbenchTE, this.inventoryPlayer.player);
            int count = 0;

            for (Aspect primal : this.primals) {
                float amt = (float) cost.getAmount(primal);
                if (cost.getAmount(primal) > 0) {
                    float alpha = 0.5F + (MathHelper.sin((float) (this.inventoryPlayer.player.ticksExisted + count * 10) / 2.0F) * 0.2F - 0.2F);
                    if (wand != null) {
                        amt *= wand.getConsumptionModifier(this.arcaneWorkbenchTE.func_70301_a(10), this.inventoryPlayer.player, primal, true);
                        if (amt * 100.0F <= (float) wand.getVis(this.arcaneWorkbenchTE.func_70301_a(10), primal)) {
                            alpha = 1.0F;
                        }
                    }

                    UtilsFX.drawTag(this.guiLeft + this.aspectLocs[count][0] - 8, this.guiTop + this.aspectLocs[count][1] - 8, primal, amt, 0, this.zLevel, 771, alpha, false);
                }

                ++count;
                if (count > 5) {
                    break;
                }
            }

            if (wand != null && cost != null && !wand.consumeAllVisCrafting(this.arcaneWorkbenchTE.func_70301_a(10), this.inventoryPlayer.player, cost, false)) {
                GL11.glPushMatrix();
                float var40 = 0.33F;
                GL11.glColor4f(var40, var40, var40, 0.66F);
                itemRender.renderWithColor = false;
                GL11.glEnable(2896);
                GL11.glEnable(2884);
                GL11.glEnable(3042);
                itemRender.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, ThaumcraftCraftingManager.findMatchingArcaneRecipe(this.arcaneWorkbenchTE, this.inventoryPlayer.player), this.guiLeft + 160, this.guiTop + 64);
                itemRender.renderItemOverlayIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, ThaumcraftCraftingManager.findMatchingArcaneRecipe(this.arcaneWorkbenchTE, this.inventoryPlayer.player), this.guiLeft + 160, this.guiTop + 64);
                itemRender.renderWithColor = true;
                GL11.glDisable(3042);
                GL11.glDisable(2896);
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                GL11.glTranslatef((float) (this.guiLeft + 168), (float) (this.guiTop + 46), 0.0F);
                GL11.glScalef(0.5F, 0.5F, 0.0F);
                String text = "Insufficient vis";
                int ll = this.fontRendererObj.getStringWidth(text) / 2;
                this.fontRendererObj.drawStringWithShadow(text, -ll, 0, 15625838);
                GL11.glScalef(1.0F, 1.0F, 1.0F);
                GL11.glPopMatrix();
            }
        }
    }
}

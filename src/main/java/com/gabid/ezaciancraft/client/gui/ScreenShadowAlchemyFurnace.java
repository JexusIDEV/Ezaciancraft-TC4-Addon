package com.gabid.ezaciancraft.client.gui;

import com.gabid.ezaciancraft.api.EzacianCraftTranslations;
import com.gabid.ezaciancraft.common.blocks.container.ContainerShadowAlchemyFurnace;
import com.gabid.ezaciancraft.common.blocks.tileentity.TileEntityShadowAlchemyFurnace;
import com.gabid.ezaciancraft.lib.client.gui.EzacianGuiUtils;
import com.gabid.ezaciancraft.lib.math.EzacianGuiContainerMathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static com.gabid.ezaciancraft.CoreMod.MODID;
import static com.gabid.ezaciancraft.lib.math.EzacianGuiContainerMathHelper.getScaledValue;
import static java.lang.String.format;

@SideOnly(Side.CLIENT)
public class ScreenShadowAlchemyFurnace extends GuiContainer {
    private static final String GUI_TEXTURE_PATH = "textures/gui/shadowAlchemyFurnace_gui.png";
    public final TileEntityShadowAlchemyFurnace furnaceTE;
    private final HashMap<String, String> visTooltip = new HashMap<>();
    private final List<String> progressTooltip = new ArrayList<>(1);

    public ScreenShadowAlchemyFurnace(InventoryPlayer inventoryPlayer, TileEntityShadowAlchemyFurnace _furnaceTE) {
        super(new ContainerShadowAlchemyFurnace(inventoryPlayer, _furnaceTE));
        this.furnaceTE = _furnaceTE;
        this.ySize = 187;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.progressTooltip.add(format("Progress: %d%%", EzacianGuiContainerMathHelper.getPercentageValue(this.furnaceTE.getFurnaceCookTime(), this.furnaceTE.getMaxFurnaceCookTime()) * 100));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float ticks, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        UtilsFX.bindTexture(MODID, GUI_TEXTURE_PATH);
        GL11.glEnable(3042);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        this.renderVis();
        GL11.glDisable(3042);
        this.renderBurn();
        this.renderProgress();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        String titleName = furnaceTE.getInventoryName();
        this.drawCenteredString(this.fontRendererObj, titleName, 88, 1, 0xffffffff);
    }

    @Override
    public void drawScreen(int x, int y, float ticks) {
        super.drawScreen(x, y, ticks);
        //vis storage
        if (EzacianGuiUtils.isInRect(63, 20, 8, 69, x - this.guiLeft, y - this.guiTop)) {
            this.visTooltip.put("AmountVis", StatCollector.translateToLocal(EzacianCraftTranslations.shadowAlchemyFurnaceVisCapacityAmount) + this.furnaceTE.getEssentiaAmountVis() + " / " + this.furnaceTE.getEssentiaMaxVis() + " " + StatCollector.translateToLocal(EzacianCraftTranslations.visTranslation));
            this.drawHoveringText(Collections.singletonList(this.visTooltip.get("AmountVis")), x, y, this.fontRendererObj);
        } else {
            this.visTooltip.remove("AmountVis");
        }

        //burn time
        int burn;
        if (this.furnaceTE.getMaxFurnaceBurnTime() > 0) {
            burn = (this.furnaceTE.getFurnaceBurnTime() * 100) / this.furnaceTE.getMaxFurnaceBurnTime();
        } else {
            burn = 0;
        }
        if (EzacianGuiUtils.isInRect(82, 43, 20, 26, x - this.guiLeft, y - this.guiTop)) {
            this.drawCreativeTabHoveringText(StatCollector.translateToLocal("gui.shadowAlchemyFurnace.burnProgress") + burn + "%", x, y);
        }

        //progress cook bar
        int progress;
        if (this.furnaceTE.getMaxFurnaceCookTime() > 0) {
            progress = (this.furnaceTE.getFurnaceCookTime() * 100) / this.furnaceTE.getMaxFurnaceCookTime();
        } else {
            progress = 0;
        }
        if (EzacianGuiUtils.isInRect(106, 30, 6, 49, x - this.guiLeft, y - this.guiTop)) {
            this.drawCreativeTabHoveringText(StatCollector.translateToLocal("gui.shadowAlchemyFurnace.cookProgress") + progress + "%", x, y);
        }
    }

    private void renderVis() {
        int scaledVis = getScaledValue(this.furnaceTE.getEssentiaAmountVis(), this.furnaceTE.getEssentiaMaxVis(), 69);
        this.drawTexturedModalRect(this.guiLeft + 63, this.guiTop + 20 + (69 - scaledVis), 205, 69 - scaledVis, 8, scaledVis);
        this.drawTexturedModalRect(this.guiLeft + 62, this.guiTop + 15, 194, 0, 10, 79);
    }

    private void renderBurn() {
        if (this.furnaceTE.getMaxFurnaceBurnTime() > 0) {
            int scaledBurn = getScaledValue(this.furnaceTE.getFurnaceBurnTime(), this.furnaceTE.getMaxFurnaceBurnTime(), 20);
            this.drawTexturedModalRect(this.guiLeft + 84, this.guiTop + 46 + (20 - scaledBurn), 177, 20 - scaledBurn, 16, scaledBurn);
        }
    }

    private void renderProgress() {
        int scaledProgress = getScaledValue(this.furnaceTE.getFurnaceCookTime(), this.furnaceTE.getMaxFurnaceCookTime(), 49);
        this.drawTexturedModalRect(this.guiLeft + 106, this.guiTop + 30 + (49 - scaledProgress), 214, 49 - scaledProgress, 6, scaledProgress);

    }
}

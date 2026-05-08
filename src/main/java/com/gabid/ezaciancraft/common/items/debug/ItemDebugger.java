package com.gabid.ezaciancraft.common.items.debug;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchCategoryList;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.playerdata.PacketSyncResearch;
import thaumcraft.common.lib.research.ResearchManager;

import java.util.Collection;
import java.util.List;

import static com.gabid.ezaciancraft.CoreMod.MODID;
import static com.gabid.ezaciancraft.registry.EzacianCraftCreativeTab.EZACIANCRAFT_TAB;

public class ItemDebugger extends Item {

    public ItemDebugger() {
        this.setUnlocalizedName("debugItem");
        this.setCreativeTab(EZACIANCRAFT_TAB);
        this.setMaxStackSize(1);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setTextureName(new ResourceLocation(MODID, "ezacianSymbol").toString());
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tabs, List list) {
        list.add(new ItemStack(item, 1, 0));
        list.add(new ItemStack(item, 1, 1));
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List tooltips, boolean flag) {
        super.addInformation(stack, player, tooltips, flag);
        switch (stack.getItemDamage()) {
            case 0:
                tooltips.add("Debug Item - Used for unlocking all current researches / including other mods-addons");
                break;
            case 1:
                tooltips.add("Debug Item - Used to drain all warp to the player");
                break;
        }

    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            switch (stack.getItemDamage()) {
                case 0:
                    Collection<ResearchCategoryList> rc = ResearchCategories.researchCategories.values();

                    for (ResearchCategoryList _cat : rc) {
                        Collection<ResearchItem> rl = _cat.research.values();
                        for (ResearchItem ri : rl) {
                            if (!ResearchManager.isResearchComplete(player.getDisplayName(), ri.key)) {
                                Thaumcraft.proxy.getResearchManager().completeResearch(player, ri.key);
                            }
                        }
                    }

                    player.addChatMessage(new ChatComponentText("All Researches added"));
                    PacketHandler.INSTANCE.sendTo(new PacketSyncResearch(player), (EntityPlayerMP) player);
                    break;
                case 1:
                    Thaumcraft.proxy.playerKnowledge.setWarpPerm(player.getDisplayName(), 0);
                    Thaumcraft.proxy.playerKnowledge.setWarpSticky(player.getDisplayName(), 0);
                    Thaumcraft.proxy.playerKnowledge.setWarpTemp(player.getDisplayName(), 0);
                    player.addChatMessage(new ChatComponentText("All Warp purged"));
                    break;
            }
        }
        stack.stackSize--;
        return stack;
    }
}

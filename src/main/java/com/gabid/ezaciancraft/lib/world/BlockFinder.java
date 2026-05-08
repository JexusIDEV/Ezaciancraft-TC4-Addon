package com.gabid.ezaciancraft.lib.world;


import com.gabid.ezaciancraft.api.oredict.OreDictUtils;
import com.gabid.ezaciancraft.lib.world.math.Coord4D;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.*;

//weird thingies from meka meka
// https://github.com/mekanism/Mekanism/blob/1.7.10/src/main/java/mekanism/common/item/ItemAtomicDisassembler.java
public class BlockFinder {
    public static Map<Block, List<Block>> ignoreBlocks = new HashMap<Block, List<Block>>();

    static {
        ignoreBlocks.put(Blocks.redstone_ore, Arrays.asList(Blocks.redstone_ore, Blocks.lit_redstone_ore));
        ignoreBlocks.put(Blocks.lit_redstone_ore, Arrays.asList(Blocks.redstone_ore, Blocks.lit_redstone_ore));
    }

    public World world;
    public ItemStack stack;
    public Coord4D location;
    public Set<Coord4D> found = new HashSet<>();

    public BlockFinder(World w, ItemStack s, Coord4D loc) {
        world = w;
        stack = s;
        location = loc;
    }

    public void loop(Coord4D pointer) {
        if (found.contains(pointer) || found.size() > 128) {
            return;
        }

        found.add(pointer);

        for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
            Coord4D coord = pointer.getFromSide(side);

            if (coord.exists(world) && checkID(coord.getBlock(world)) && (coord.getMetadata(world) == stack.getItemDamage() || (OreDictUtils.getOreDictName(stack).contains("logWood") && coord.getMetadata(world) % 4 == stack.getItemDamage() % 4))) {
                loop(coord);
            }
        }
    }

    public Set<Coord4D> calc() {
        loop(location);

        return found;
    }

    public boolean checkID(Block b) {
        Block origBlock = location.getBlock(world);
        return (ignoreBlocks.get(origBlock) == null && b == origBlock) || (ignoreBlocks.get(origBlock) != null && ignoreBlocks.get(origBlock).contains(b));
    }
}

package net.homak.homakmod.block;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.homak.homakmod.HomakMod;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final SoulDominanceBlock SOUL_DOMINANCE = (SoulDominanceBlock) registerBlock("soul_dominance",
            new SoulDominanceBlock(AbstractBlock.Settings.create().strength(0.6f, 0.6f).sounds(BlockSoundGroup.DEEPSLATE).requiresTool()));


    ;private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(HomakMod.MOD_ID, name), block);
    }

   private static void registerBlockItem(String name, Block block) {
       Registry.register(Registries.ITEM, Identifier.of(HomakMod.MOD_ID, name),
               new BlockItem(block, new Item.Settings()));
   }

    public static void registerModBlocks() {

        HomakMod.LOGGER.info("Registering Mod Blocks for " + HomakMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> {
            entries.add(ModBlocks.SOUL_DOMINANCE);
        });
    }


}

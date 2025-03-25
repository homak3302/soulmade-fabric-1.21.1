package net.homak.homakmod.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.homak.homakmod.HomakMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    // Items
    public static final Item SOUL_FORCE_FIELD = registerItem("soul_force_field",
            new SoulForceFieldItem(new Item.Settings().maxCount(1)));

    // Swords
    public static final Item SOUL_CURVESWORD = registerItem("soul_curvesword",
            new SoulCurvesword(ToolMaterials.NETHERITE, new Item.Settings()
                    .attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.NETHERITE, 3, -2.4f))));

    public static final Item SOUL_CLEAVER = registerItem("soul_cleaver",
            new SoulCleaverItem(ToolMaterials.NETHERITE, new Item.Settings()
                    .attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.NETHERITE, 5, -3.0f))));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(HomakMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        HomakMod.LOGGER.info("Registering Mod Items for " + HomakMod.MOD_ID);

        // Item Groups
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.add(SOUL_FORCE_FIELD);
            entries.add(SOUL_CURVESWORD);
            entries.add(SOUL_CLEAVER);
        } );

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
        } );
        }
    }




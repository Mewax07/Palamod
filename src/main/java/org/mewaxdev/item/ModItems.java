package org.mewaxdev.item;

import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.mewaxdev.Palamod;
import org.mewaxdev.item.custom.ChiselItem;
import org.mewaxdev.item.custom.HammerItem;
import org.mewaxdev.item.custom.ModArmorItem;

public class ModItems {
    public static final Item PALADIUM_INGOT = registerItem("paladium_ingot",
			new Item(new Item.Settings()));
	public static final Item RAW_PALADIUM = registerItem("raw_paladium",
			new Item(new Item.Settings()));

	public static final Item PALADIUM_CHISEL = registerItem("paladium_chisel",
			new ChiselItem(new Item.Settings().maxDamage(60)));

	public static final Item PALADIUM_SWORD = registerItem("paladium_sword",
			new SwordItem(ModToolMaterials.PALADIUM, new Item.Settings()
					.attributeModifiers(SwordItem.createAttributeModifiers(ModToolMaterials.PALADIUM, 3, -2.4f))
			));
	public static final Item PALADIUM_PICKAXE = registerItem("paladium_pickaxe",
			new PickaxeItem(ModToolMaterials.PALADIUM, new Item.Settings()
					.attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterials.PALADIUM, 1, -2.8f))
			));
	public static final Item PALADIUM_AXE = registerItem("paladium_axe",
			new AxeItem(ModToolMaterials.PALADIUM, new Item.Settings()
					.attributeModifiers(AxeItem.createAttributeModifiers(ModToolMaterials.PALADIUM, 2, -2f))
			));
	public static final Item PALADIUM_SHOVEL = registerItem("paladium_shovel",
			new ShovelItem(ModToolMaterials.PALADIUM, new Item.Settings()
					.attributeModifiers(ShovelItem.createAttributeModifiers(ModToolMaterials.PALADIUM, 0, -3.0f))
			));
	public static final Item PALADIUM_HOE = registerItem("paladium_hoe",
			new HoeItem(ModToolMaterials.PALADIUM, new Item.Settings()
					.attributeModifiers(HoeItem.createAttributeModifiers(ModToolMaterials.PALADIUM, 0, -3.0f))
			));

	public static final Item PALADIUM_HAMMER = registerItem("paladium_hammer",
			new HammerItem(ModToolMaterials.PALADIUM, new Item.Settings()
					.attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterials.PALADIUM, 5, -3.4f))
			));

	public static final Item PALADIUM_HELMET = registerItem("paladium_helmet",
			new ModArmorItem(ModArmorMaterials.PALADIUM_ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Settings()
					.maxDamage(2860)
			));
	public static final Item PALADIUM_CHESTPLATE = registerItem("paladium_chestplate",
			new ModArmorItem(ModArmorMaterials.PALADIUM_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Settings()
					.maxDamage(4160)
			));
	public static final Item PALADIUM_LEGGINGS = registerItem("paladium_leggings",
			new ModArmorItem(ModArmorMaterials.PALADIUM_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Settings()
					.maxDamage(3900)
			));
	public static final Item PALADIUM_BOOTS = registerItem("paladium_boots",
			new ModArmorItem(ModArmorMaterials.PALADIUM_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, new Item.Settings()
					.maxDamage(3380)
			));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Palamod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        Palamod.LOGGER.info("Registering Palamod Items.");
    }
}

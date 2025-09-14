package org.mewaxdev.item;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.mewaxdev.Palamod;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ModArmorMaterials {
	public static final RegistryEntry<ArmorMaterial> AMETHYST_ARMOR_MATERIAL = registerArmorMaterial("amethyst_armor",
			() -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
				map.put(ArmorItem.Type.HELMET, 5);
				map.put(ArmorItem.Type.CHESTPLATE, 5);
				map.put(ArmorItem.Type.LEGGINGS, 5);
				map.put(ArmorItem.Type.BOOTS, 4);
				map.put(ArmorItem.Type.BODY, 5);
			}), 20, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND,
					() -> Ingredient.ofItems(ModItems.AMETHYST.INGOT),
					List.of(new ArmorMaterial.Layer(Identifier.of(Palamod.MOD_ID, "amethyst_armor"))),
					0, 0
			));

	public static final RegistryEntry<ArmorMaterial> TITANE_ARMOR_MATERIAL = registerArmorMaterial("titane_armor",
			() -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
				map.put(ArmorItem.Type.HELMET, 5);
				map.put(ArmorItem.Type.CHESTPLATE, 6);
				map.put(ArmorItem.Type.LEGGINGS, 5);
				map.put(ArmorItem.Type.BOOTS, 4);
				map.put(ArmorItem.Type.BODY, 6);
			}), 25, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
					() -> Ingredient.ofItems(ModItems.TITANE.INGOT),
					List.of(new ArmorMaterial.Layer(Identifier.of(Palamod.MOD_ID, "titane_armor"))),
					0, 0
			));

	public static final RegistryEntry<ArmorMaterial> PALADIUM_ARMOR_MATERIAL = registerArmorMaterial("paladium_armor",
			() -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
				map.put(ArmorItem.Type.HELMET, 5);
				map.put(ArmorItem.Type.CHESTPLATE, 7);
				map.put(ArmorItem.Type.LEGGINGS, 6);
				map.put(ArmorItem.Type.BOOTS, 5);
				map.put(ArmorItem.Type.BODY, 7);
			}), 30, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
					() -> Ingredient.ofItems(ModItems.PALADIUM.INGOT),
					List.of(new ArmorMaterial.Layer(Identifier.of(Palamod.MOD_ID, "paladium_armor"))),
					0, 0
			));

	public static final RegistryEntry<ArmorMaterial> GREEN_PALADIUM_ARMOR_MATERIAL = registerArmorMaterial("green_paladium_armor",
			() -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
				map.put(ArmorItem.Type.HELMET, 5);
				map.put(ArmorItem.Type.CHESTPLATE, 7);
				map.put(ArmorItem.Type.LEGGINGS, 6);
				map.put(ArmorItem.Type.BOOTS, 5);
				map.put(ArmorItem.Type.BODY, 7);
			}), 30, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
					() -> Ingredient.ofItems(ModItems.PALADIUM.INGOT),
					List.of(new ArmorMaterial.Layer(Identifier.of(Palamod.MOD_ID, "green_paladium_armor"))),
					0, 0
			));

	public static final RegistryEntry<ArmorMaterial> MIXED_ARMOR_MATERIAL = registerArmorMaterial("mixed_endium_armor",
			() -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
				map.put(ArmorItem.Type.HELMET, 5);
				map.put(ArmorItem.Type.CHESTPLATE, 7);
				map.put(ArmorItem.Type.LEGGINGS, 6);
				map.put(ArmorItem.Type.BOOTS, 5);
				map.put(ArmorItem.Type.BODY, 7);
			}), 30, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
					() -> Ingredient.ofItems(ModItems.PALADIUM.INGOT),
					List.of(new ArmorMaterial.Layer(Identifier.of(Palamod.MOD_ID, "mixed_endium_armor"))),
					0, 0
			));

	public static final RegistryEntry<ArmorMaterial> ENDIUM_ARMOR_MATERIAL = registerArmorMaterial("endium_armor",
			() -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
				map.put(ArmorItem.Type.HELMET, 6);
				map.put(ArmorItem.Type.CHESTPLATE, 7);
				map.put(ArmorItem.Type.LEGGINGS, 6);
				map.put(ArmorItem.Type.BOOTS, 5);
				map.put(ArmorItem.Type.BODY, 7);
			}), 35, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE,
					() -> Ingredient.ofItems(ModItems.ENDIUM.INGOT),
					List.of(new ArmorMaterial.Layer(Identifier.of(Palamod.MOD_ID, "endium_armor"))),
					3, 0
			));

	public static RegistryEntry<ArmorMaterial> registerArmorMaterial(String name, Supplier<ArmorMaterial> material) {
		return Registry.registerReference(Registries.ARMOR_MATERIAL, Identifier.of(Palamod.MOD_ID, name), material.get());
	}
}

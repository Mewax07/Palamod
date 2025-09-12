package org.mewaxdev.item;

import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.mewaxdev.Palamod;
import org.mewaxdev.block.ModBlocks;
import org.mewaxdev.item.custom.ChiselItem;
import org.mewaxdev.item.custom.HammerItem;
import org.mewaxdev.item.custom.ModArmorItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModItems {
	public static final List<Item> GENERATED_ITEMS = new ArrayList<>();

	public record MaterialDefinition(
			String name,
			ModToolMaterials toolMaterial,
			ArmorMaterial armorMaterial
	) {
	}

	public static final MaterialItems PALADIUM = new MaterialItems(
			"paladium", ModToolMaterials.PALADIUM, ModArmorMaterials.PALADIUM_ARMOR_MATERIAL, new MaterialItems.Options()
	);

	public static final MaterialItems ENDIUM = new MaterialItems(
			"endium", ModToolMaterials.ENDIUM, ModArmorMaterials.ENDIUM_ARMOR_MATERIAL,
			new MaterialItems.Options().raw(false).hammer(false).shovel(false)
	);

	public static final Item PALADIUM_CHISEL = registerItem("paladium_chisel",
			new ChiselItem(new Item.Settings().maxDamage(60)
			));

	public static final Item PALADIUM_SEEDS = registerItem("paladium_seeds",
			new AliasedBlockItem(ModBlocks.PALADIUM_CROP, new Item.Settings()
			));

	public static final Item PALA_FLOWER = registerItem("pala_flower",
			new Item(new Item.Settings()
			));

	private static Item registerItem(String name, Item item) {
		return Registry.register(Registries.ITEM, Identifier.of(Palamod.MOD_ID, name), item);
	}

	public static void registerModItems() {
		Palamod.LOGGER.info("Registering Palamod Items.");
	}

	public static List<MaterialItems> getAllMaterials() {
		return Arrays.asList(PALADIUM, ENDIUM);
	}

	public static class MaterialItems {
		private final String name;

		public final Item INGOT;
		public final Item RAW;
		public final Item SWORD;
		public final Item PICKAXE;
		public final Item AXE;
		public final Item SHOVEL;
		public final Item HOE;
		public final Item HAMMER;
		public final Item HELMET;
		public final Item CHESTPLATE;
		public final Item LEGGINGS;
		public final Item BOOTS;

		public static class Options {
			private boolean includeRaw = true;
			private boolean includeHammer = true;
			private boolean includeArmor = true;
			private boolean includeSword = true;
			private boolean includePickaxe = true;
			private boolean includeAxe = true;
			private boolean includeShovel = true;
			private boolean includeHoe = true;

			public Options raw(boolean value) { includeRaw = value; return this; }
			public Options hammer(boolean value) { includeHammer = value; return this; }
			public Options armor(boolean value) { includeArmor = value; return this; }
			public Options sword(boolean value) { includeSword = value; return this; }
			public Options pickaxe(boolean value) { includePickaxe = value; return this; }
			public Options axe(boolean value) { includeAxe = value; return this; }
			public Options shovel(boolean value) { includeShovel = value; return this; }
			public Options hoe(boolean value) { includeHoe = value; return this; }
		}

		public MaterialItems(String name, ToolMaterial toolMaterial, RegistryEntry<ArmorMaterial> armorMaterial, Options options) {
			this.name = name;

			this.INGOT = registerItem(name + "_ingot", new Item(new Item.Settings()));

			this.RAW = options.includeRaw ? registerItem("raw_" + name, new Item(new Item.Settings())) : null;

			this.SWORD = options.includeSword ? registerItem(name + "_sword", new SwordItem(toolMaterial,
					new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(toolMaterial, 3, -2.4f)))) : null;
			this.PICKAXE = options.includePickaxe ? registerItem(name + "_pickaxe", new PickaxeItem(toolMaterial,
					new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(toolMaterial, 1, -2.8f)))) : null;
			this.AXE = options.includeAxe ? registerItem(name + "_axe", new AxeItem(toolMaterial,
					new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(toolMaterial, 2, -2f)))) : null;
			this.SHOVEL = options.includeShovel ? registerItem(name + "_shovel", new ShovelItem(toolMaterial,
					new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(toolMaterial, 0, -3.0f)))) : null;
			this.HOE = options.includeHoe ? registerItem(name + "_hoe", new HoeItem(toolMaterial,
					new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(toolMaterial, 0, -3.0f)))) : null;

			this.HAMMER = options.includeHammer ? registerItem(name + "_hammer", new HammerItem(toolMaterial,
					new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(toolMaterial, 5, -3.4f)))) : null;

			if (options.includeArmor) {
				this.HELMET = registerItem(name + "_helmet", new ModArmorItem(armorMaterial, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(2860)));
				this.CHESTPLATE = registerItem(name + "_chestplate", new ModArmorItem(armorMaterial, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(4160)));
				this.LEGGINGS = registerItem(name + "_leggings", new ModArmorItem(armorMaterial, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(3900)));
				this.BOOTS = registerItem(name + "_boots", new ModArmorItem(armorMaterial, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(3380)));
			} else {
				this.HELMET = this.CHESTPLATE = this.LEGGINGS = this.BOOTS = null;
			}
		}

		public String name() { return name; }
	}
}

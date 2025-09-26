package org.mewaxdev.item;

import net.minecraft.block.Blocks;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.mewaxdev.Palamod;
import org.mewaxdev.block.ModBlocks;
import org.mewaxdev.item.custom.*;
import org.mewaxdev.item.custom.tool.PalamodHoeItem;
import org.mewaxdev.item.custom.tool.SeedplanterItem;

import java.util.*;

public class ModItems {
	public static final Map<String, Item> NON_CLASS_REGISTERED_ITEM = new HashMap<>();

	public record MaterialDefinition(
			String name,
			ModToolMaterials toolMaterial,
			ArmorMaterial armorMaterial
	) {
	}

	public static final MaterialItems AMETHYST = new MaterialItems(
			"amethyst", ModToolMaterials.AMETHYST, ModArmorMaterials.AMETHYST_ARMOR_MATERIAL, 2,
			new MaterialItems.Options()
	);

	public static final MaterialItems TITANE = new MaterialItems(
			"titane", ModToolMaterials.TITANE, ModArmorMaterials.TITANE_ARMOR_MATERIAL, 4,
			new MaterialItems.Options()
	);

	public static final MaterialItems PALADIUM = new MaterialItems(
			"paladium", ModToolMaterials.PALADIUM, ModArmorMaterials.PALADIUM_ARMOR_MATERIAL, 6,
			new MaterialItems.Options()
	);

	public static final MaterialItems GREEN_PALADIUM = new MaterialItems(
			"green_paladium", ModToolMaterials.GREEN_PALADIUM, ModArmorMaterials.GREEN_PALADIUM_ARMOR_MATERIAL, 8,
			new MaterialItems.Options()
					.hammer(false)
					.customSword(false)
	);

	public static final MaterialItems MIXED_ENDIUM = new MaterialItems(
			"mixed_endium", ModToolMaterials.ENDIUM, ModArmorMaterials.MIXED_ARMOR_MATERIAL, 0,
			new MaterialItems.Options()
					.raw(false)
					.hammer(false)
					.sword(false)
					.customSword(false)
					.pickaxe(false)
					.axe(false)
					.shovel(false)
					.hoe(false)
					.armor(true)
	);

	public static final MaterialItems ENDIUM = new MaterialItems(
			"endium", ModToolMaterials.ENDIUM, ModArmorMaterials.ENDIUM_ARMOR_MATERIAL, 12,
			new MaterialItems.Options()
					.raw(false)
					.hammer(false)
					.customSword(false)
					.custom("fragment", new Item.Settings())
	);

	public static final Item PALADIUM_CHISEL = registerNonClassItem("paladium_chisel",
			new ChiselItem(new Item.Settings().maxDamage(60)
			));

	public static final Item KIWANO = registerNonClassItem("kiwano",
			new Item(new Item.Settings()
			));

	public static final Item KIWANO_SEEDS = registerItem("kiwano_seeds",
			new AliasedBlockItem(ModBlocks.KIWANO_CROP, new Item.Settings()
			));

	public static final Item ORANGEBLUE = registerNonClassItem("orangeblue",
			new Item(new Item.Settings()
			));

	public static final Item ORANGEBLUE_SEEDS = registerItem("orangeblue_seeds",
			new AliasedBlockItem(ModBlocks.ORANGEBLUE_CROP, new Item.Settings()
			));

	public static final Item GOD_PICKAXE = registerItem("god_pickaxe",
			new PickaxeOfGod(ModToolMaterials.GOD_PICKAXE, new Item.Settings()
			));

	public static final Item AMETHYST_SEEDPLANTER = registerNonClassItem("amethyst_seed_planter",
			new SeedplanterItem(
					new Item.Settings().maxCount(1),
					3,
					10,
					List.of(Items.WHEAT_SEEDS, Items.CARROT, Items.POTATO)
			));

	public static final Item TITANIUM_SEEDPLANTER = registerNonClassItem("titane_seed_planter",
			new SeedplanterItem(
					new Item.Settings().maxCount(1),
					5,
					30,
					List.of(Items.WHEAT_SEEDS, Items.CARROT, Items.PUMPKIN_SEEDS, Items.MELON_SEEDS)
			));

	public static final Item HANG_GLIDER = registerNonClassItem("hangglider",
			new Hangglider(
					new Item.Settings()
			));

	private static Item registerItem(String name, Item item) {
		return Registry.register(Registries.ITEM, Identifier.of(Palamod.MOD_ID, name), item);
	}

	private static Item registerNonClassItem(String name, Item item) {
		Item registered = Registry.register(Registries.ITEM, Identifier.of(Palamod.MOD_ID, name), item);
		NON_CLASS_REGISTERED_ITEM.put(name, registered);
		return registered;
	}

	public static void registerModItems() {
		Palamod.LOGGER.info("Registering Palamod Items.");
	}

	public static List<MaterialItems> getAllMaterials() {
		return Arrays.asList(AMETHYST, TITANE, PALADIUM, GREEN_PALADIUM, MIXED_ENDIUM, ENDIUM);
	}

	public static class MaterialItems {
		private final String name;

		public final Item INGOT;
		public final Item RAW;
		public final Item SWORD;
		public final Item BROAD_SWORD;
		public final Item FAST_SWORD;
		public final Item PICKAXE;
		public final Item AXE;
		public final Item SHOVEL;
		public final Item HOE;
		public final Item HAMMER;
		public final Item HELMET;
		public final Item CHESTPLATE;
		public final Item LEGGINGS;
		public final Item BOOTS;

		public final Map<String, Item> CUSTOM_ITEMS = new HashMap<>();

		public static class Options {
			private boolean includeRaw = true;
			private boolean includeHammer = true;
			private boolean includeArmor = true;
			private boolean includeSword = true;
			private boolean includeCustomSword = true;
			private boolean includePickaxe = true;
			private boolean includeAxe = true;
			private boolean includeShovel = true;
			private boolean includeHoe = true;

			private final Map<String, Item> customItems = new HashMap<>();

			public Options raw(boolean value) {
				includeRaw = value;
				return this;
			}

			public Options hammer(boolean value) {
				includeHammer = value;
				return this;
			}

			public Options armor(boolean value) {
				includeArmor = value;
				return this;
			}

			public Options sword(boolean value) {
				includeSword = value;
				return this;
			}

			public Options customSword(boolean value) {
				includeCustomSword = value;
				return this;
			}

			public Options pickaxe(boolean value) {
				includePickaxe = value;
				return this;
			}

			public Options axe(boolean value) {
				includeAxe = value;
				return this;
			}

			public Options shovel(boolean value) {
				includeShovel = value;
				return this;
			}

			public Options hoe(boolean value) {
				includeHoe = value;
				return this;
			}

			public Options custom(String suffix, Item.Settings settings) {
				customItems.put(suffix, new Item(settings));
				return this;
			}
		}

		public MaterialItems(String name, ToolMaterial toolMaterial, RegistryEntry<ArmorMaterial> armorMaterial, int hoeRange, Options options) {
			this.name = name;

			this.INGOT = registerItem(name + "_ingot", new Item(new Item.Settings()));

			this.RAW = options.includeRaw ? registerItem("raw_" + name, new Item(new Item.Settings())) : null;

			this.BROAD_SWORD = options.includeCustomSword ? registerItem(name + "_broad_sword", new SwordItem(toolMaterial,
					new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(toolMaterial, 4, -2.8f)))) : null;
			this.FAST_SWORD = options.includeCustomSword ? registerItem(name + "_fast_sword", new SwordItem(toolMaterial,
					new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(toolMaterial, 2, -0.4f)))) : null;

			this.SWORD = options.includeSword ? registerItem(name + "_sword", new SwordItem(toolMaterial,
					new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(toolMaterial, 3, -2.4f)))) : null;
			this.PICKAXE = options.includePickaxe ? registerItem(name + "_pickaxe", new PickaxeItem(toolMaterial,
					new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(toolMaterial, 1, -2.8f)))) : null;
			this.AXE = options.includeAxe ? registerItem(name + "_axe", new AxeItem(toolMaterial,
					new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(toolMaterial, 2, -3.2f)))) : null;
			this.SHOVEL = options.includeShovel ? registerItem(name + "_shovel", new ShovelItem(toolMaterial,
					new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(toolMaterial, 0, -2.0f)))) : null;
			this.HOE = options.includeHoe ? registerItem(name + "_hoe", new PalamodHoeItem(toolMaterial, hoeRange,
					new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(toolMaterial, 0, -2.0f)))) : null;

			this.HAMMER = options.includeHammer ? registerItem(name + "_hammer", new HammerItem(toolMaterial,
					new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(toolMaterial, 5, -3.0f)))) : null;

			if (options.includeArmor) {
				ArmorItems armorItems = new ArmorItems(name, armorMaterial);
				this.HELMET = armorItems.HELMET;
				this.CHESTPLATE = armorItems.CHESTPLATE;
				this.LEGGINGS = armorItems.LEGGINGS;
				this.BOOTS = armorItems.BOOTS;
			} else {
				this.HELMET = this.CHESTPLATE = this.LEGGINGS = this.BOOTS = null;
			}

			for (Map.Entry<String, Item> entry : options.customItems.entrySet()) {
				String suffix = entry.getKey();
				Item item = registerItem(name + "_" + suffix, entry.getValue());
				CUSTOM_ITEMS.put(suffix, item);
			}
		}

		public String name() {
			return name;
		}
	}

	public static class ArmorItems {
		public final Item HELMET;
		public final Item CHESTPLATE;
		public final Item LEGGINGS;
		public final Item BOOTS;

		public ArmorItems(String name, RegistryEntry<ArmorMaterial> armorMaterial) {
			int helmetDur, chestDur, legDur, bootsDur;

			switch (name) {
				case "amethyst" -> {
					helmetDur = 1870;
					chestDur = 2720;
					legDur = 2550;
					bootsDur = 2210;
				}
				case "titane" -> {
					helmetDur = 2200;
					chestDur = 3200;
					legDur = 3000;
					bootsDur = 2600;
				}
				case "paladium" -> {
					helmetDur = 2860;
					chestDur = 4160;
					legDur = 3900;
					bootsDur = 3380;
				}
				case "green_paladium" -> {
					helmetDur = 3300;
					chestDur = 4800;
					legDur = 4500;
					bootsDur = 3900;
				}
				case "mixed_endium" -> {
					helmetDur = 3850;
					chestDur = 5600;
					legDur = 5250;
					bootsDur = 4550;
				}
				case "endium" -> {
					helmetDur = 4400;
					chestDur = 6400;
					legDur = 6000;
					bootsDur = 5200;
				}
				default -> {
					helmetDur = chestDur = legDur = bootsDur = 1000;
				}
			}

			this.HELMET = registerItem(name + "_helmet",
					new ModArmorItem(armorMaterial, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(helmetDur)));
			this.CHESTPLATE = registerItem(name + "_chestplate",
					new ModArmorItem(armorMaterial, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(chestDur)));
			this.LEGGINGS = registerItem(name + "_leggings",
					new ModArmorItem(armorMaterial, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(legDur)));
			this.BOOTS = registerItem(name + "_boots",
					new ModArmorItem(armorMaterial, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(bootsDur)));
		}
	}
}

package org.mewaxdev.block;

import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import org.mewaxdev.Palamod;
import org.mewaxdev.block.custom.ModCaveBlock;
import org.mewaxdev.block.custom.ModCropBlock;
import org.mewaxdev.block.custom.ModElevatorBlock;
import org.mewaxdev.item.custom.FertilizedDirt;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModBlocks {
	private static final Map<String, Block> REGISTERED_BLOCKS = new HashMap<>();
	public static final Map<String, Block> NON_CLASS_REGISTERED_BLOCKS = new HashMap<>();

	public static final MaterialBlocks AMETHYST = new MaterialBlocks(
			"amethyst",
			new MaterialBlocks.Options()
					.deep_ore(false)
					.ore_gen(new MaterialBlocks.OreGenOptions(
							6,
							4,
							-80,
							25
					))
	);

	public static final MaterialBlocks TITANE = new MaterialBlocks(
			"titane",
			new MaterialBlocks.Options()
					.deep_ore(false)
					.ore_gen(new MaterialBlocks.OreGenOptions(
							6,
							3,
							-80,
							25
					))
	);

	public static final MaterialBlocks PALADIUM = new MaterialBlocks(
			"paladium",
			new MaterialBlocks.Options()
					.ore_gen(new MaterialBlocks.OreGenOptions(
							4,
							1,
							-80,
							16
					))
	);

	public static final MaterialBlocks GREEN_PALADIUM = new MaterialBlocks(
			"green_paladium",
			new MaterialBlocks.Options()
					.ore_gen(new MaterialBlocks.OreGenOptions(
							2,
							1,
							-40,
							10
					))
	);

	public static final MaterialBlocks ENDIUM = new MaterialBlocks(
			"endium",
			new MaterialBlocks.Options()
					.raw(false)
					.ore_gen(new MaterialBlocks.OreGenOptions(
							1,
							1,
							-60,
							18
					))
	);

	public static final Block WARNING = registerNonClassBlock("warning",
			new Block(AbstractBlock.Settings.create()
					.strength(3f)
					.requiresTool()
					.sounds(BlockSoundGroup.STONE)
			));

	public static final Block KIWANO_CROP = registerBlock("kiwano",
			new ModCropBlock(AbstractBlock.Settings.create().noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP).pistonBehavior(PistonBehavior.DESTROY).mapColor(MapColor.DARK_GREEN)
			));

	public static final Block ORANGEBLUE_CROP = registerBlock("orangeblue",
			new ModCropBlock(AbstractBlock.Settings.create().noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP).pistonBehavior(PistonBehavior.DESTROY).mapColor(MapColor.DARK_GREEN)
			));

	public static final Block FERTILIZED_DIRT = registerNonClassBlock("fertilized_dirt",
			new FertilizedDirt(AbstractBlock.Settings.create()
					.strength(5f)
					.requiresTool()
					.sounds(BlockSoundGroup.GRASS)
			));

	public static final Block CAVE_BLOCK = registerNonClassBlock("cave_block",
			new ModCaveBlock(AbstractBlock.Settings.create()
					.sounds(BlockSoundGroup.GLASS)
					.strength(1.5f, 4f)
					.requiresTool()
			));

	public static final Block ELEVATOR_BLOCK = registerNonClassBlock("elevator_block",
			new ModElevatorBlock()
			);

	public static class MaterialBlocks {
		private final String name;
		public Options options;

		public final Block BLOCK;
		public final Block STAIRS;
		public final Block SLAB;
		public final Block RAW_BLOCK;
		public final Block ORE;
		public final Block DEEPSLATE_ORE;

		public static class Options {
			private boolean includeBlock = true;
			private boolean includeStairs = true;
			private boolean includeSlab = true;
			private boolean includeRaw = true;
			private boolean includeOre = true;
			private boolean includeDeepOre = true;
			private OreGenOptions oreGenOptions = null;

			public Options block(boolean value) {
				includeBlock = value;
				return this;
			}

			public Options stairs(boolean value) {
				includeStairs = value;
				return this;
			}

			public Options slab(boolean value) {
				includeSlab = value;
				return this;
			}

			public Options raw(boolean value) {
				includeRaw = value;
				return this;
			}

			public Options ore(boolean value) {
				includeOre = value;
				return this;
			}

			public Options deep_ore(boolean value) {
				includeDeepOre = value;
				return this;
			}

			public Options ore_gen(OreGenOptions value) {
				oreGenOptions = value;
				return this;
			}

			public OreGenOptions getOreGenOptions() {
				return oreGenOptions;
			}
		}

		public record OreGenOptions(int veinSize, int veinsPerChunk, int minY, int maxY) {
		}

		public MaterialBlocks(String name, Options options) {
			this.name = name;
			this.options = options;

			this.BLOCK = options.includeBlock ? registerBlock(name + "_block",
					new Block(AbstractBlock.Settings.create()
							.strength(4f)
							.requiresTool()
							.sounds(BlockSoundGroup.METAL))) : null;

			this.STAIRS = options.includeStairs ? registerBlock(name + "_block_stairs",
					new StairsBlock(BLOCK != null ? BLOCK.getDefaultState() : Blocks.IRON_BLOCK.getDefaultState(),
							AbstractBlock.Settings.create()
									.strength(2f)
									.requiresTool())) : null;

			this.SLAB = options.includeSlab ? registerBlock(name + "_block_slab",
					new SlabBlock(AbstractBlock.Settings.create()
							.strength(2f)
							.requiresTool())) : null;

			this.RAW_BLOCK = options.includeRaw ? registerBlock("raw_" + name + "_block",
					new Block(AbstractBlock.Settings.create()
							.strength(2.8f)
							.requiresTool()
							.sounds(BlockSoundGroup.STONE))) : null;

			if (options.includeOre) {
				this.ORE = registerBlock(name + "_ore",
						new ExperienceDroppingBlock(UniformIntProvider.create(2, 5),
								AbstractBlock.Settings.create()
										.strength(3f)
										.requiresTool()
										.sounds(BlockSoundGroup.STONE)));
			} else {
				this.ORE = null;
			}
			if (options.includeDeepOre) {
				this.DEEPSLATE_ORE = registerBlock("deepslate_" + name + "_ore",
						new ExperienceDroppingBlock(UniformIntProvider.create(3, 6),
								AbstractBlock.Settings.create()
										.strength(4f)
										.requiresTool()
										.sounds(BlockSoundGroup.DEEPSLATE)));
			} else {
				this.DEEPSLATE_ORE = null;
			}
		}

		public String name() {
			return name;
		}
	}

	private static Block registerBlock(String name, Block block) {
		registerBlockItem(name, block);
		Block registered = Registry.register(Registries.BLOCK, Identifier.of(Palamod.MOD_ID, name), block);
		REGISTERED_BLOCKS.put(name, registered);
		return registered;
	}

	private static Block registerNonClassBlock(String name, Block block) {
		registerBlockItem(name, block);
		Block registered = Registry.register(Registries.BLOCK, Identifier.of(Palamod.MOD_ID, name), block);
		NON_CLASS_REGISTERED_BLOCKS.put(name, registered);
		return registered;
	}

	private static void registerBlockItem(String name, Block block) {
		Registry.register(Registries.ITEM, Identifier.of(Palamod.MOD_ID, name), new BlockItem(block, new Item.Settings()));
	}

	public static void registerModBlocks() {
		Palamod.LOGGER.info("Registering Palamod Blocks.");
	}

	public static boolean hasBlock(String name) {
		return REGISTERED_BLOCKS.containsKey(name) || NON_CLASS_REGISTERED_BLOCKS.containsKey(name);
	}

	public static Block getBlock(String name) {
		Block block = REGISTERED_BLOCKS.get(name);
		if (block != null) {
			return block;
		}
		return NON_CLASS_REGISTERED_BLOCKS.get(name);
	}

	public static List<ModBlocks.MaterialBlocks> getAllMaterials() {
		return Arrays.asList(AMETHYST, TITANE, PALADIUM, GREEN_PALADIUM, ENDIUM);
	}
}

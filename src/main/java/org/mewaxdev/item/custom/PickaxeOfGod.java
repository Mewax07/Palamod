package org.mewaxdev.item.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.mewaxdev.component.ModDataComponentTypes;

import java.util.*;

public class PickaxeOfGod extends MiningToolItem {
	private static final int[] LEVEL_XP = new int[101];
	private static final int XP_PER_BLOCK = 3;
	private static final boolean CHEAT_MODE = false;

	static {
		LEVEL_XP[1] = 52 * XP_PER_BLOCK;
		LEVEL_XP[2] = 208 * XP_PER_BLOCK;
		LEVEL_XP[3] = 404 * XP_PER_BLOCK;
		LEVEL_XP[4] = 625 * XP_PER_BLOCK;
		LEVEL_XP[5] = 866 * XP_PER_BLOCK;
		LEVEL_XP[6] = 1125 * XP_PER_BLOCK;
		LEVEL_XP[7] = 1398 * XP_PER_BLOCK;
		LEVEL_XP[8] = 1683 * XP_PER_BLOCK;
		LEVEL_XP[9] = 1981 * XP_PER_BLOCK;
		LEVEL_XP[10] = 2289 * XP_PER_BLOCK;
		LEVEL_XP[11] = 2607 * XP_PER_BLOCK;
		LEVEL_XP[12] = 2935 * XP_PER_BLOCK;
		LEVEL_XP[13] = 3270 * XP_PER_BLOCK;
		LEVEL_XP[14] = 3615 * XP_PER_BLOCK;
		LEVEL_XP[15] = 3967 * XP_PER_BLOCK;
		LEVEL_XP[16] = 4326 * XP_PER_BLOCK;
		LEVEL_XP[17] = 4692 * XP_PER_BLOCK;
		LEVEL_XP[18] = 5065 * XP_PER_BLOCK;
		LEVEL_XP[19] = 5445 * XP_PER_BLOCK;
		LEVEL_XP[20] = 5831 * XP_PER_BLOCK;
		LEVEL_XP[21] = 6222 * XP_PER_BLOCK;
		LEVEL_XP[22] = 6620 * XP_PER_BLOCK;
		LEVEL_XP[23] = 7023 * XP_PER_BLOCK;
		LEVEL_XP[24] = 7431 * XP_PER_BLOCK;
		LEVEL_XP[25] = 7845 * XP_PER_BLOCK;
		LEVEL_XP[26] = 8264 * XP_PER_BLOCK;
		LEVEL_XP[27] = 8688 * XP_PER_BLOCK;
		LEVEL_XP[28] = 9116 * XP_PER_BLOCK;
		LEVEL_XP[29] = 9550 * XP_PER_BLOCK;
		LEVEL_XP[30] = 9988 * XP_PER_BLOCK;
		LEVEL_XP[31] = 10430 * XP_PER_BLOCK;
		LEVEL_XP[32] = 10876 * XP_PER_BLOCK;
		LEVEL_XP[33] = 11328 * XP_PER_BLOCK;
		LEVEL_XP[34] = 11783 * XP_PER_BLOCK;
		LEVEL_XP[35] = 12242 * XP_PER_BLOCK;
		LEVEL_XP[36] = 12706 * XP_PER_BLOCK;
		LEVEL_XP[37] = 13173 * XP_PER_BLOCK;
		LEVEL_XP[38] = 13644 * XP_PER_BLOCK;
		LEVEL_XP[39] = 14119 * XP_PER_BLOCK;
		LEVEL_XP[40] = 14597 * XP_PER_BLOCK;
		LEVEL_XP[41] = 15080 * XP_PER_BLOCK;
		LEVEL_XP[42] = 15565 * XP_PER_BLOCK;
		LEVEL_XP[43] = 16055 * XP_PER_BLOCK;
		LEVEL_XP[44] = 16548 * XP_PER_BLOCK;
		LEVEL_XP[45] = 17044 * XP_PER_BLOCK;
		LEVEL_XP[46] = 17544 * XP_PER_BLOCK;
		LEVEL_XP[47] = 18046 * XP_PER_BLOCK;
		LEVEL_XP[48] = 18553 * XP_PER_BLOCK;
		LEVEL_XP[49] = 19062 * XP_PER_BLOCK;
		LEVEL_XP[50] = 19575 * XP_PER_BLOCK;
		LEVEL_XP[51] = 20090 * XP_PER_BLOCK;
		LEVEL_XP[52] = 20609 * XP_PER_BLOCK;
		LEVEL_XP[53] = 21131 * XP_PER_BLOCK;
		LEVEL_XP[54] = 21655 * XP_PER_BLOCK;
		LEVEL_XP[55] = 22184 * XP_PER_BLOCK;
		LEVEL_XP[56] = 22714 * XP_PER_BLOCK;
		LEVEL_XP[57] = 23247 * XP_PER_BLOCK;
		LEVEL_XP[58] = 23784 * XP_PER_BLOCK;
		LEVEL_XP[59] = 24322 * XP_PER_BLOCK;
		LEVEL_XP[60] = 24865 * XP_PER_BLOCK;
		LEVEL_XP[61] = 25049 * XP_PER_BLOCK;
		LEVEL_XP[62] = 25957 * XP_PER_BLOCK;
		LEVEL_XP[63] = 26507 * XP_PER_BLOCK;
		LEVEL_XP[64] = 27059 * XP_PER_BLOCK;
		LEVEL_XP[65] = 27615 * XP_PER_BLOCK;
		LEVEL_XP[66] = 28172 * XP_PER_BLOCK;
		LEVEL_XP[67] = 28733 * XP_PER_BLOCK;
		LEVEL_XP[68] = 29296 * XP_PER_BLOCK;
		LEVEL_XP[69] = 29862 * XP_PER_BLOCK;
		LEVEL_XP[70] = 30429 * XP_PER_BLOCK;
		LEVEL_XP[71] = 31000 * XP_PER_BLOCK;
		LEVEL_XP[72] = 31573 * XP_PER_BLOCK;
		LEVEL_XP[73] = 32148 * XP_PER_BLOCK;
		LEVEL_XP[74] = 32726 * XP_PER_BLOCK;
		LEVEL_XP[75] = 33305 * XP_PER_BLOCK;
		LEVEL_XP[76] = 33888 * XP_PER_BLOCK;
		LEVEL_XP[77] = 34473 * XP_PER_BLOCK;
		LEVEL_XP[78] = 35060 * XP_PER_BLOCK;
		LEVEL_XP[79] = 35648 * XP_PER_BLOCK;
		LEVEL_XP[80] = 36241 * XP_PER_BLOCK;
		LEVEL_XP[81] = 36834 * XP_PER_BLOCK;
		LEVEL_XP[82] = 37430 * XP_PER_BLOCK;
		LEVEL_XP[83] = 38028 * XP_PER_BLOCK;
		LEVEL_XP[84] = 38629 * XP_PER_BLOCK;
		LEVEL_XP[85] = 39231 * XP_PER_BLOCK;
		LEVEL_XP[86] = 39836 * XP_PER_BLOCK;
		LEVEL_XP[87] = 40442 * XP_PER_BLOCK;
		LEVEL_XP[88] = 41052 * XP_PER_BLOCK;
		LEVEL_XP[89] = 41662 * XP_PER_BLOCK;
		LEVEL_XP[90] = 42275 * XP_PER_BLOCK;
		LEVEL_XP[91] = 42891 * XP_PER_BLOCK;
		LEVEL_XP[92] = 43507 * XP_PER_BLOCK;
		LEVEL_XP[93] = 44127 * XP_PER_BLOCK;
		LEVEL_XP[94] = 44748 * XP_PER_BLOCK;
		LEVEL_XP[95] = 45371 * XP_PER_BLOCK;
		LEVEL_XP[96] = 45996 * XP_PER_BLOCK;
		LEVEL_XP[97] = 46624 * XP_PER_BLOCK;
		LEVEL_XP[98] = 47252 * XP_PER_BLOCK;
		LEVEL_XP[99] = 47883 * XP_PER_BLOCK;
	}

	public PickaxeOfGod(ToolMaterial material, Item.Settings settings) {
		super(material, BlockTags.PICKAXE_MINEABLE, settings.maxDamage(999));
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		int level = getLevel(stack);
		int xp = getXp(stack);
		int xpForNextLevel = level < 100 ? LEVEL_XP[level + 1] : 0;
		int totalXpBefore = 0;
		for (int i = 1; i <= level; i++) {
			totalXpBefore += LEVEL_XP[i];
		}
		int xpIntoLevel = xp - totalXpBefore;

		tooltip.add(Text.translatable("tooltip.palamod.pickaxe_of_god.level", level).formatted(Formatting.YELLOW));
		tooltip.add(Text.translatable("tooltip.palamod.pickaxe_of_god.xp", xpIntoLevel, xpForNextLevel).formatted(Formatting.GRAY));
		tooltip.add(Text.translatable("tooltip.palamod.pickaxe_of_god.total_xp", xp).formatted(Formatting.GREEN));

		if (level >= 100) {
			tooltip.add(Text.translatable("tooltip.palamod.pickaxe_of_god.max_level").formatted(Formatting.GOLD, Formatting.BOLD));
		}
	}

	private static int computeLevel(int totalXp) {
		int lvl = 0;
		for (int i = 1; i <= 100; i++) {
			if (totalXp >= LEVEL_XP[i]) {
				totalXp -= LEVEL_XP[i];
				lvl = i;
			} else {
				break;
			}
		}
		return lvl;
	}

	@Override
	public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
		if (!world.isClient && isXpBlock(state.getBlock())) {
			Integer currentXp = stack.get(ModDataComponentTypes.XP_LEVEL);
			if (currentXp == null) currentXp = 0;

			int oldLevel = getLevel(stack);
			currentXp += CHEAT_MODE ? 3000 : XP_PER_BLOCK;
			stack.set(ModDataComponentTypes.XP_LEVEL, currentXp);

			int newLevel = computeLevel(currentXp);
			stack.set(ModDataComponentTypes.LEVEL, newLevel);

			stack.set(DataComponentTypes.CUSTOM_NAME,
					Text.translatable("item.palamod.pickaxe_of_god.name", newLevel)
							.formatted(Formatting.GOLD)
							.styled(style -> style.withItalic(false)));
			if (oldLevel != newLevel) {
				applyLevelBonuses(stack, newLevel, world);
			}
		}

		return true;
	}

	private void applyLevelBonuses(ItemStack stack, int level, World world) {
		ItemEnchantmentsComponent.Builder enchantments = new ItemEnchantmentsComponent.Builder(ItemEnchantmentsComponent.DEFAULT);

		RegistryEntry<Enchantment> efficiencyEntry = world.getRegistryManager()
				.get(net.minecraft.registry.RegistryKeys.ENCHANTMENT)
				.getEntry(Enchantments.EFFICIENCY)
				.orElse(null);

		RegistryEntry<Enchantment> fortuneEntry = world.getRegistryManager()
				.get(net.minecraft.registry.RegistryKeys.ENCHANTMENT)
				.getEntry(Enchantments.FORTUNE)
				.orElse(null);

		if (level >= 41 && efficiencyEntry != null) {
			enchantments.add(efficiencyEntry, 1);
		}
		if (level >= 46 && efficiencyEntry != null) {
			enchantments.set(efficiencyEntry, 2);
		}
		if (level >= 51 && efficiencyEntry != null) {
			enchantments.set(efficiencyEntry, 3);
		}
		if (level >= 56 && efficiencyEntry != null) {
			enchantments.set(efficiencyEntry, 4);
		}
		if (level >= 61 && efficiencyEntry != null) {
			enchantments.set(efficiencyEntry, 5);
		}
		if (level >= 66 && efficiencyEntry != null && fortuneEntry != null) {
			enchantments.set(efficiencyEntry, 5);
			enchantments.add(fortuneEntry, 1);
		}
		if (level >= 71 && efficiencyEntry != null && fortuneEntry != null) {
			enchantments.set(efficiencyEntry, 5);
			enchantments.set(fortuneEntry, 2);
		}
		if (level >= 76 && efficiencyEntry != null && fortuneEntry != null) {
			enchantments.set(efficiencyEntry, 5);
			enchantments.set(fortuneEntry, 3);
		}

		stack.set(net.minecraft.component.DataComponentTypes.ENCHANTMENTS, enchantments.build());
	}

	public static int getLevel(ItemStack stack) {
		Integer level = stack.get(ModDataComponentTypes.LEVEL);
		return level != null ? level : 0;
	}

	public static int getXp(ItemStack stack) {
		Integer xp = stack.get(ModDataComponentTypes.XP_LEVEL);
		return xp != null ? xp : 0;
	}

	public static float getXpProgress(ItemStack stack) {
		int xp = getXp(stack);
		int level = getLevel(stack);

		if (level >= 100) return 1.0f;

		int xpForLevel = LEVEL_XP[level + 1];
		int totalXpBefore = 0;
		for (int i = 1; i <= level; i++) totalXpBefore += LEVEL_XP[i];

		int xpIntoLevel = xp - totalXpBefore;
		return (float) xpIntoLevel / (float) xpForLevel;
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		int lvl = getLevel(stack);
		target.damage(attacker.getWorld().getDamageSources().mobAttack(attacker), 2.0f + lvl * 0.2f);
		return true;
	}

	@Override
	public float getMiningSpeed(ItemStack stack, BlockState state) {
		int lvl = getLevel(stack);
		Block block = state.getBlock();

		if (getInstantMineBlocks(lvl).contains(block)) {
			return 9999f;
		}

		return super.getMiningSpeed(stack, state) + lvl * 0.5f;
	}

	private static Set<Block> getInstantMineBlocks(int level) {
		Set<Block> blocks = new HashSet<>();

		if (level >= 11) {
			blocks.add(Blocks.SNOW_BLOCK);
			blocks.add(Blocks.OAK_LEAVES);
		}
		if (level >= 21) {
			blocks.add(Blocks.NETHERRACK);
			blocks.add(Blocks.CACTUS);
		}
		if (level >= 26) {
			blocks.add(Blocks.DIRT);
			blocks.add(Blocks.SAND);
			blocks.add(Blocks.GRAVEL);
			blocks.add(Blocks.RED_SAND);
			blocks.add(Blocks.SOUL_SAND);
			blocks.add(Blocks.ICE);
		}
		if (level >= 31) {
			blocks.add(Blocks.WHITE_WOOL);
		}
		if (level >= 41) {
			blocks.add(Blocks.SANDSTONE);
		}
		if (level >= 51) {
			blocks.add(Blocks.TERRACOTTA);
		}
		if (level >= 56) {
			blocks.add(Blocks.STONE);
		}

		return blocks;
	}

	@Override
	public boolean isItemBarVisible(ItemStack stack) {
		return true;
	}

	@Override
	public int getItemBarStep(ItemStack stack) {
		return Math.round(getXpProgress(stack) * 13);
	}

	@Override
	public int getItemBarColor(ItemStack stack) {
		float progress = getXpProgress(stack);
		if (progress < 0.5f) {
			float t = progress / 0.5f;
			return interpolateColor(0xFF0000, 0xFFFF00, t);
		} else {
			float t = (progress - 0.5f) / 0.5f;
			return interpolateColor(0xFFFF00, 0x00FF00, t);
		}
	}

	private int interpolateColor(int startColor, int endColor, float t) {
		int sr = (startColor >> 16) & 0xFF;
		int sg = (startColor >> 8) & 0xFF;
		int sb = startColor & 0xFF;
		int er = (endColor >> 16) & 0xFF;
		int eg = (endColor >> 8) & 0xFF;
		int eb = endColor & 0xFF;
		int r = (int) (sr + (er - sr) * t);
		int g = (int) (sg + (eg - sg) * t);
		int b = (int) (sb + (eb - sb) * t);
		return (r << 16) | (g << 8) | b;
	}

	public static boolean isXpBlock(Block block) {
		return block == Blocks.STONE ||
				block == Blocks.DIORITE ||
				block == Blocks.GRANITE ||
				block == Blocks.ANDESITE ||
				block == Blocks.DEEPSLATE ||
				block == Blocks.DIRT ||
				block == Blocks.GRASS_BLOCK ||
				block == Blocks.SHORT_GRASS ||
				block == Blocks.TALL_GRASS ||
				block == Blocks.SAND ||
				block == Blocks.SNOW_BLOCK ||
				block == Blocks.GRAVEL ||
				block == Blocks.OAK_LOG ||
				block == Blocks.OAK_LEAVES ||
				block.getDefaultState().isIn(BlockTags.COPPER_ORES) ||
				block.getDefaultState().isIn(BlockTags.COAL_ORES) ||
				block.getDefaultState().isIn(BlockTags.IRON_ORES) ||
				block.getDefaultState().isIn(BlockTags.GOLD_ORES) ||
				block.getDefaultState().isIn(BlockTags.LAPIS_ORES) ||
				block.getDefaultState().isIn(BlockTags.REDSTONE_ORES) ||
				block.getDefaultState().isIn(BlockTags.EMERALD_ORES) ||
				block.getDefaultState().isIn(BlockTags.DIAMOND_ORES)
				;
	}
}
package org.mewaxdev.item.custom.tool;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.mewaxdev.block.ModBlocks;
import org.mewaxdev.component.ModDataComponentTypes;

import java.util.List;

public class SeedplanterItem extends Item {

	private final int range;
	private final int requiredLevel;
	private final List<Item> seeds;

	public SeedplanterItem(Settings settings, int range, int requiredLevel, List<Item> seeds) {
		super(settings);
		this.range = range;
		this.requiredLevel = requiredLevel;
		this.seeds = seeds;
	}

	private int getSelectedSeedIndex(ItemStack stack) {
		int index = stack.getOrDefault(ModDataComponentTypes.SELECTED_SEED, 0);
		if (index < 0 || index >= seeds.size()) return 0;
		return index;
	}

	private void setSelectedSeedIndex(ItemStack stack, int index) {
		stack.set(ModDataComponentTypes.SELECTED_SEED, index);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getStackInHand(hand);

		if (!player.isSneaking()) {
			return TypedActionResult.pass(stack);
		}

		int index = getSelectedSeedIndex(stack);
		index = (index + 1) % seeds.size();
		setSelectedSeedIndex(stack, index);

		if (world.isClient()) {
			Item seed = seeds.get(index);
			player.sendMessage(
					Text.literal("§aGraine sélectionnée : §e" + seed.getName().getString()),
					true
			);
		}

		return TypedActionResult.success(stack);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext ctx) {
		World world = ctx.getWorld();
		PlayerEntity player = ctx.getPlayer();
		ItemStack stack = ctx.getStack();
		BlockPos pos = ctx.getBlockPos();

		if (player == null) return ActionResult.FAIL;

		int index = getSelectedSeedIndex(stack);
		Item selectedSeed = seeds.get(index);

		int half = range / 2;

		for (int x = -half; x <= half; x++) {
			for (int z = -half; z <= half; z++) {

				BlockPos plantPos = pos.add(x, 1, z);
				BlockPos soilPos = plantPos.down();

				Block soil = world.getBlockState(soilPos).getBlock();

				if (soil != Blocks.FARMLAND && soil != ModBlocks.FERTILIZED_DIRT) continue;
				if (!world.getBlockState(plantPos).isAir()) continue;

				ItemStack seedStack = findSeedInInventory(player, selectedSeed);
				if (seedStack == null || seedStack.isEmpty()) continue;

				if (selectedSeed instanceof BlockItem blockItem) {

					BlockHitResult hitResult = new BlockHitResult(
							ctx.getHitPos(),
							Direction.UP,
							plantPos,
							false
					);

					ActionResult result = blockItem.place(
							new ItemPlacementContext(player, ctx.getHand(), seedStack, hitResult)
					);

					if (result.isAccepted()) {
						if (!player.isCreative()) {
							seedStack.decrement(1);
						}
					}
				}
			}
		}
		return ActionResult.SUCCESS;
	}

	private ItemStack findSeedInInventory(PlayerEntity player, Item seedItem) {
		for (int i = 0; i < player.getInventory().size(); i++) {
			ItemStack slot = player.getInventory().getStack(i);
			if (slot.isOf(seedItem)) return slot;
		}
		return null;
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}

	@Override
	public boolean hasGlint(ItemStack stack) {
		return false;
	}
}

package org.mewaxdev;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import org.mewaxdev.block.ModBlocks;
import org.mewaxdev.util.ModModelPredicates;

public class PalamodClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.KIWANO_CROP, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ORANGEBLUE_CROP, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CAVE_BLOCK, RenderLayer.getCutoutMipped());

		ModModelPredicates.registerModelPredicates();
	}
}

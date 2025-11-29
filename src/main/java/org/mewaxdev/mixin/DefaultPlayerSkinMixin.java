package org.mewaxdev.mixin;

import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.util.Identifier;
import org.mewaxdev.Palamod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(DefaultSkinHelper.class)
public final class DefaultPlayerSkinMixin {
	@Unique
	private static final SkinTextures[] DEV_SKINS = new SkinTextures[]{
			createSkinTextures("textures/entity/player/wide/zeldown.png", SkinTextures.Model.WIDE),
			createSkinTextures("textures/entity/player/wide/fuzeiii.png", SkinTextures.Model.WIDE),
	};

	@Inject(method = "getSkinTextures(Ljava/util/UUID;)Lnet/minecraft/client/util/SkinTextures;", at = @At("HEAD"), cancellable = true)
	private static void getSkinTextures(UUID uuid, CallbackInfoReturnable<SkinTextures> cir) {
		cir.setReturnValue(DEV_SKINS[Math.floorMod(uuid.hashCode(), DEV_SKINS.length)]);
	}

	@Unique
	private static SkinTextures createSkinTextures(String texture, SkinTextures.Model model) {
		return new SkinTextures(Identifier.of(Palamod.MOD_ID, texture), null, null, null, model, true);
	}
}

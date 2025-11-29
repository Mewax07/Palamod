package org.mewaxdev.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;

public final class DrawShapeModern {
	private static DrawShapeModern instance;

	public DrawShapeModern() {
		if (instance != null) throw new RuntimeException("Duplicate DrawShapeModern");
		instance = this;
	}

	public static DrawShapeModern getInstance() {
		if (instance == null) instance = new DrawShapeModern();
		return instance;
	}

	public void drawRect(MatrixStack ms, double x, double y, double width, double height, int color) {
		fill(ms, x, y, x + width, y + height, color);
	}

	public void drawBorder(MatrixStack ms, double x, double y, double x2, double y2, int color, double thickness) {
		fill(ms, x, y - thickness, x2, y, color);
		fill(ms, x - thickness, y, x, y2, color);
		fill(ms, x, y2, x2, y2 + thickness, color);
		fill(ms, x2, y, x2 + thickness, y2, color);
	}

	private void fill(MatrixStack ms, double x1, double y1, double x2, double y2, int color) {
		float a = (color >> 24 & 0xFF) / 255f;
		float r = (color >> 16 & 0xFF) / 255f;
		float g = (color >> 8 & 0xFF) / 255f;
		float b = (color & 0xFF) / 255f;

		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.setShader(GameRenderer::getPositionColorProgram);

		Matrix4f mat = ms.peek().getPositionMatrix();
		Tessellator tess = Tessellator.getInstance();
		BufferBuilder buffer = tess.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);

		buffer.vertex(mat, (float) x1, (float) y2, 0f).color(r, g, b, a);
		buffer.vertex(mat, (float) x2, (float) y2, 0f).color(r, g, b, a);
		buffer.vertex(mat, (float) x2, (float) y1, 0f).color(r, g, b, a);
		buffer.vertex(mat, (float) x1, (float) y1, 0f).color(r, g, b, a);

		BufferRenderer.drawWithGlobalProgram(buffer.end());

		RenderSystem.disableBlend();
	}
}

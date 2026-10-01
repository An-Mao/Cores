package dev.anye.mc.cores.screen.widget;

import net.minecraft.resources.Identifier;

public class DT_ImageInfo {
	public final Identifier image;
	public final int imageWidth;
	public final int imageHeight;
	public final int elementWidth;
	public final int elementHeight;
	public final int u;
	public final int v;

	public DT_ImageInfo(Identifier image) {
		this(image, 96, 32, 32, 32);
	}

	public DT_ImageInfo(String image) {
		this(image, 96, 32, 32, 32);
	}

	public DT_ImageInfo(String image, int imageWidth, int imageHeight, int elementWidth, int elementHeight) {
		this(image, imageWidth, imageHeight, elementWidth, elementHeight, 0, 0);
	}

	public DT_ImageInfo(Identifier image, int imageWidth, int imageHeight, int elementWidth, int elementHeight) {
		this(image, imageWidth, imageHeight, elementWidth, elementHeight, 0, 0);
	}

	public DT_ImageInfo(String image, int imageWidth, int imageHeight, int elementWidth, int elementHeight, int u, int v) {
		this(Identifier.tryParse(image), imageWidth, imageHeight, elementWidth, elementHeight, u, v);
	}

	public DT_ImageInfo(Identifier image, int imageWidth, int imageHeight, int elementWidth, int elementHeight, int u, int v) {
		this.image = image;
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
		this.elementWidth = elementWidth;
		this.elementHeight = elementHeight;
		this.u = u;
		this.v = v;
	}
}

package filter;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import enums.FilterTypes;

public class BlurFilter implements IFilter {

	private static final long serialVersionUID = -6110985366940552472L;

	public BufferedImage processImage(BufferedImage image) {
		float[] blurMatrix = { 1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f,
				1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f,
				1.0f / 9.0f, 1.0f / 9.0f };
		BufferedImageOp blurFilter = new ConvolveOp(
				new Kernel(3, 3, blurMatrix), ConvolveOp.EDGE_NO_OP, null);
		return blurFilter.filter(image, null);
	}

	@Override
	public FilterTypes getFilter() {

		return FilterTypes.BlurFilter;
	}

}

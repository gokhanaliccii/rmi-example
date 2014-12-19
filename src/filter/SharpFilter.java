package filter;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import enums.FilterTypes;

public class SharpFilter implements IFilter {

	private static final long serialVersionUID = 3403134694600189485L;

	public BufferedImage processImage(BufferedImage image) {
		float[] sharpenMatrix = { 0.0f, -1.0f, 0.0f, -1.0f, 5.0f, -1.0f, 0.0f,
				-1.0f, 0.0f };
		BufferedImageOp sharpenFilter = new ConvolveOp(new Kernel(3, 3,
				sharpenMatrix), ConvolveOp.EDGE_NO_OP, null);
		return sharpenFilter.filter(image, null);
	}

	@Override
	public FilterTypes getFilter() {

		return FilterTypes.SharpenFilter;
	}
}
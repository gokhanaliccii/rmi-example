package filter;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ByteLookupTable;
import java.awt.image.LookupOp;

import enums.FilterTypes;

public class InvertFilter implements IFilter {

	private static final long serialVersionUID = -3097486925799917990L;

	public BufferedImage processImage(BufferedImage image) {
		byte[] invertArray = new byte[256];

		for (int counter = 0; counter < 256; counter++)
			invertArray[counter] = (byte) (255 - counter);

		BufferedImageOp invertFilter = new LookupOp(new ByteLookupTable(0,
				invertArray), null);
		return invertFilter.filter(image, null);

	}

	@Override
	public FilterTypes getFilter() {

		return FilterTypes.InvertFilter;
	}
	
}

package filter;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.rmi.Remote;

import enums.FilterTypes;

public interface IFilter extends Serializable, Remote {
	BufferedImage processImage(BufferedImage image);
	public FilterTypes getFilter();
}

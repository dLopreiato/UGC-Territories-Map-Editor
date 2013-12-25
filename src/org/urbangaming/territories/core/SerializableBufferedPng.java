package org.urbangaming.territories.core;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.Serializable;
import javax.imageio.ImageIO;

/**
 * A wrapper class used to serialize buffered images.
 * @author Andrew Lopreiato
 * @version 1.0.1 12/24/2013
 */
public class SerializableBufferedPng implements Serializable {

	// DATA MEMBERS
	public BufferedImage BufferedImage;
	private static final long serialVersionUID = 2L;
	// END DATA MEMBERS
	
	/**
	 * Constructs a SerializableBufferedPng with a null BufferedImage.
	 */
	public SerializableBufferedPng() {
		BufferedImage = null;
	}
	
	/**
	 * Constructs a SerializableBufferedPng with a give BufferedImage.
	 * @param bufferedImage	BufferedImage.
	 */
	public SerializableBufferedPng(BufferedImage bufferedImage) {
		BufferedImage = bufferedImage;
	}
	
	/**
	 * Returns a new copy of the instance.
	 * @return	New copy.
	 */
	public SerializableBufferedPng Copy() {
		ColorModel cm = BufferedImage.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = BufferedImage.copyData(null);
		BufferedImage bi = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
		return new SerializableBufferedPng(bi);
	}
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		ImageIO.write(BufferedImage, "png", ImageIO.createImageOutputStream(out));
	}
	
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException{
		BufferedImage = ImageIO.read(ImageIO.createImageInputStream(in));
	}
	
	/**
	 * Returns if the two serializable buffered pngs are of equal values.
	 * @param other	The other serializable buffered png.
	 * @return		Boolean representation of comparison.
	 */
	@Override
	public boolean equals(Object object) {
		if (object.getClass() != this.getClass())
			return false;
		SerializableBufferedPng other = (SerializableBufferedPng)object;
		return (other.BufferedImage.equals(this.BufferedImage));
	}
}

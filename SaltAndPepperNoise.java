package code;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class SaltAndPepperNoise {
	/**
	 * Method to calculate thresholded image
	 * 
	 * @param img
	 * @param requiredThresholdValue
	 * @return
	 */

	public static BufferedImage saltnpepper(BufferedImage img, int saltPercent, int pepperPercent) {
		int height = img.getHeight();
		int width = img.getWidth();
		BufferedImage saltnpepperedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		int salt = (height * width * saltPercent) / 100;
		int pepper = (height * width * pepperPercent) / 100;

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Color c = new Color(img.getRGB(x, y));
				int red = c.getRed();
				int green = c.getGreen();
				int blue = c.getBlue();
				Color newColor = new Color(red, green, blue);
				saltnpepperedImage.setRGB(x, y, newColor.getRGB());
			}
		}

		for (int i = 1; i <= salt; i++) {
			int x = (int) (Math.random() * width);
			int y = (int) (Math.random() * height);
			Color newColor = new Color(255, 255, 255);
			saltnpepperedImage.setRGB(x, y, newColor.getRGB());

		}
		for (int i = 1; i <= pepper; i++) {
			int x = (int) (Math.random() * width);
			int y = (int) (Math.random() * height);
			Color newColor = new Color(0, 0, 0);
			//System.out.println("White:"+newColor.getRGB());
			saltnpepperedImage.setRGB(x, y, newColor.getRGB());
			System.out.println(saltnpepperedImage.getRGB(x, y));
		}

		return saltnpepperedImage;
	}

	public static void main(String args[]) {
		BufferedImage inputImage = null, outputImage = null;
		int salt = 10, pepper = 10;
		try {

			File input = new File("sample.jpg");
			inputImage = ImageIO.read(input);
			outputImage = saltnpepper(inputImage, salt, pepper);
			File ouptut = new File("saltedandpeppered.jpg");
			ImageIO.write(outputImage, "jpg", ouptut);

		} catch (Exception e) {
			System.out.println("Exception occured: " + e.getMessage());
		}
	}

}

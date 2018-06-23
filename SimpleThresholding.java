package code;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class SimpleThresholding {
	/**
	 * Method to calculate thresholded image
	 * 
	 * @param img
	 * @param requiredThresholdValue
	 * @return
	 */

	public static BufferedImage findThreshold(BufferedImage img, int requiredThresholdValue) {
		int height = img.getHeight();
		int width = img.getWidth();
		BufferedImage finalThresholdImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		int red = 0;
		int green = 0;
		int blue = 0;

		for (int x = 0; x < height; x++) {
			try {

				for (int y = 0; y < width; y++) {
					Color c = new Color(img.getRGB(y, x));

					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();

					Color newColor = null;

					if ((red + green + blue) / 3 < (int) (requiredThresholdValue)) {
						newColor = new Color(0, 0, 0);
						finalThresholdImage.setRGB(y, x, newColor.getRGB());
					} else {
						newColor = new Color(255, 255, 255);
						finalThresholdImage.setRGB(y, x, newColor.getRGB());
					}

				}
			} catch (Exception e) {
				System.out.println("Exception occured in method findThreshold(): " + e.getMessage());
			}
		}

		return finalThresholdImage;
	}

	public static void main(String args[]) {
		BufferedImage inputImage = null, outputImage = null;
		int T = 125;
		try {

			File input = new File("sample.jpg");
			inputImage = ImageIO.read(input);
			outputImage = findThreshold(inputImage, T);
			File ouptut = new File("thresholdedImage.jpg");
			ImageIO.write(outputImage, "jpg", ouptut);

		} catch (Exception e) {
			System.out.println("Exception occured: " + e.getMessage());
		}
	}

}

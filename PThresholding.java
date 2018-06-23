package code;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class PThresholding {
	/**
	 * Method to calculate thresholded image
	 * 
	 * @param img
	 * @param requiredThresholdValue
	 * @return
	 */

	public static long noOfPixels = 0;

	public static BufferedImage findPThreshold(BufferedImage img, int percentile) {
		int height = img.getHeight();
		int width = img.getWidth();
		noOfPixels = height * width;
		int intArray[] = new int[(int) noOfPixels];
		BufferedImage finalThresholdImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		int red = 0;
		int green = 0;
		int blue = 0;
		int index = 0;

		try {
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					Color c = new Color(img.getRGB(x, y));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					int intensityValue = (red + green + blue) / 3;
					intArray[index++] = intensityValue;
				}
			}

			Arrays.sort(intArray);
			long topPixels = (long) (noOfPixels * percentile / 100);
			int pthresholdValue = intArray[(int) (noOfPixels - topPixels)];
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					Color c = new Color(img.getRGB(x, y));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					Color newColor = null;

					if ((red + green + blue) / 3 < (int) (pthresholdValue)) {
						newColor = new Color(0, 0, 0);
						finalThresholdImage.setRGB(x, y, newColor.getRGB());
					} else {
						newColor = new Color(255, 255, 255);
						finalThresholdImage.setRGB(x, y, newColor.getRGB());
					}
				}
			}

		} catch (Exception e) {
			System.out.println("Exception occured in method findThreshold(): " + e.getMessage());
		}

		return finalThresholdImage;
	}

	public static void main(String args[]) {
		BufferedImage inputImage = null, outputImage = null;
		int percentile = 100;
		try {

			File input = new File("sample.jpg");
			inputImage = ImageIO.read(input);
			outputImage = findPThreshold(inputImage, percentile);
			File ouptut = new File("PercentileThresholdedImage.jpg");
			ImageIO.write(outputImage, "jpg", ouptut);

		} catch (Exception e) {
			System.out.println("Exception occured: " + e.getMessage());
		}
	}

}

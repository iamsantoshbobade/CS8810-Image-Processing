package code;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class IterativeThresholding {
	/**
	 * Method to calculate thresholded image
	 * 
	 * @param img
	 * @param threshold
	 * @return
	 */

	public static BufferedImage findIterativeThreshold(BufferedImage img, int threshold) {
		int height = img.getHeight();
		int width = img.getWidth();
		float eps = 1.0f, u = 0.0f, u1 = 0.0f, u2 = 0.0f;
		float newThreshold = 0.0f, oldThreshold = (float) threshold;
		BufferedImage finalThresholdImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		int red = 0;
		int green = 0;
		int blue = 0;
		int noOfR1Pixels = 0, noOfR2Pixels = 0;
		int sumOfR1Intensities = 0, sumOfR2Intensities = 0;

		int count = 0;

		do {

			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					Color c = new Color(img.getRGB(x, y));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					Color newColor = null;
					int intensityValue = (red + green + blue) / 3;
					if (intensityValue < oldThreshold) {
						sumOfR1Intensities += intensityValue;
						noOfR1Pixels++;
						newColor = new Color(0, 0, 0);
						finalThresholdImage.setRGB(x, y, newColor.getRGB());
					} else {
						sumOfR2Intensities += intensityValue;
						noOfR2Pixels++;
						newColor = new Color(255, 255, 255);
						finalThresholdImage.setRGB(x, y, newColor.getRGB());
					}
				}
			}

			u1 = ((float) sumOfR1Intensities) / noOfR1Pixels;
			u2 = ((float) sumOfR2Intensities) / noOfR2Pixels;
			newThreshold = (u1 + u2) / 2.0f;

			count++;
			System.out.println("count: " + count + "\t abs value: " + Math.abs(oldThreshold - newThreshold));
			if (Math.abs(oldThreshold - newThreshold) < eps)
				break;
			oldThreshold = newThreshold;

		} while (true);

		System.out.println("do....while count: " + count);
		return finalThresholdImage;
	}

	public static void main(String args[]) {
		BufferedImage inputImage = null, outputImage = null;
		int T = 254;
		try {

			File input = new File("sample.jpg");
			inputImage = ImageIO.read(input);
			outputImage = findIterativeThreshold(inputImage, T);
			File ouptut = new File("IterativelyThresholdedImage.jpg");
			ImageIO.write(outputImage, "jpg", ouptut);

		} catch (Exception e) {
			System.out.println("Exception occured: " + e.getMessage());
		}
	}

}

package code;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ContrastEnhancement {

	/**
	 * Method to calculate thresholded image
	 * 
	 * @param img
	 * @param requiredThresholdValue
	 * @return
	 */

	public static long noOfPixels = 0;

	public static BufferedImage findPThreshold(BufferedImage img) {
		int height = img.getHeight();
		int width = img.getWidth();
		noOfPixels = height * width;
		int intensityFrequency[] = new int[256];
		int cumulativeFrequency[] = new int[256];
		double cumulativeProbability[] = new double[256];
		int intArray[] = new int[(int) noOfPixels];
		BufferedImage finalThresholdImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		int red = 0;
		int green = 0;
		int blue = 0;
		int index = 0;
		int blow = 0, bhigh = 0;

		try {
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					Color c = new Color(img.getRGB(x, y));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					int intensityValue = (red + green + blue) / 3;
					intensityFrequency[intensityValue] += 1;
				}
			}
			cumulativeFrequency[0] = intensityFrequency[0];
			cumulativeProbability[0] = ((double) cumulativeFrequency[0]) / noOfPixels;

			for (int i = 1; i < intensityFrequency.length; i++) {
				cumulativeFrequency[i] = intensityFrequency[i] + cumulativeFrequency[i - 1];
				cumulativeProbability[i] = ((double) cumulativeFrequency[i]) / noOfPixels;
			}
			for (int i = 0; i < -1; i++) {
				System.out.print(" value of i " + i + " normnal freq " + intensityFrequency[i]);
				System.out.println(" cumu " + cumulativeFrequency[i] + " cumu prob : " + cumulativeProbability[i]);
			}
			
			while(intensityFrequency[index]< 200)
				index++;
			blow = index;
			index = 255;
			while(intensityFrequency[index]>200)
				index--;
			bhigh = index;

			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					Color c = new Color(img.getRGB(x, y));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					int intensityValue = (red + green + blue) / 3;
					Color newColor = null;
					if (intensityValue < blow) {
						newColor = new Color(0, 0, 0);
						finalThresholdImage.setRGB(x, y, newColor.getRGB());
					} else if (intensityValue > bhigh) {
						newColor = new Color(255, 255, 255);
						finalThresholdImage.setRGB(x, y, newColor.getRGB());
					} else {
						double temp = ((double)(intensityValue - blow))/(bhigh -  blow);
						temp = temp * (intensityFrequency.length - 1);
						intensityValue = (int)Math.floor(temp);
						newColor = new Color(intensityValue,intensityValue,intensityValue);
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
		try {

			File input = new File("lcimage.jpg");
			inputImage = ImageIO.read(input);
			outputImage = findPThreshold(inputImage);
			File ouptut = new File("contrastEnhancedImage4.jpg");
			ImageIO.write(outputImage, "jpg", ouptut);

		} catch (Exception e) {
			System.out.println("Exception occured: " + e.getMessage());
		}
	}

}

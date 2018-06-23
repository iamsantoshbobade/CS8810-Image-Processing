package code;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class AveragingSmoothing {
	/**
	 * Method to calculate thresholded image
	 * 
	 * @param img
	 * @param requiredThresholdValue
	 * @return
	 */

	public static BufferedImage averagingSmoothing(BufferedImage img, int NS) {
		int height = img.getHeight();
		int width = img.getWidth();
		int arr[] = new int[NS];
		int redValues[] = new int[NS];
		int blueValues[] = new int[NS];
		int greenValues[] = new int[NS];
		BufferedImage finalSmoothedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		int red = 0;
		int green = 0;
		int blue = 0;
		int index = 0;

		try {
			for (int x = 1; x < width - 1; x++) {
				for (int y = 1; y < height - 1; y++) {

					index = 0;

					Color c = new Color(img.getRGB(x - 1, y));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					redValues[index] = red;
					greenValues[index] = green;
					blueValues[index++] = blue;

					c = new Color(img.getRGB(x + 1, y));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					redValues[index] = red;
					greenValues[index] = green;
					blueValues[index++] = blue;

					c = new Color(img.getRGB(x, y - 1));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();

					redValues[index] = red;
					greenValues[index] = green;
					blueValues[index++] = blue;

					c = new Color(img.getRGB(x, y + 1));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();

					redValues[index] = red;
					greenValues[index] = green;
					blueValues[index++] = blue;

					switch (NS) {
					case 5:
						c = new Color(img.getRGB(x, y));
						red = c.getRed();
						green = c.getGreen();
						blue = c.getBlue();
						redValues[index] = red;
						greenValues[index] = green;
						blueValues[index++] = blue;
						break;
					case 9:
						c = new Color(img.getRGB(x, y));
						red = c.getRed();
						green = c.getGreen();
						blue = c.getBlue();
						redValues[index] = red;
						greenValues[index] = green;
						blueValues[index++] = blue;
					case 8:
						c = new Color(img.getRGB(x - 1, y - 1));
						red = c.getRed();
						green = c.getGreen();
						blue = c.getBlue();
						redValues[index] = red;
						greenValues[index] = green;
						blueValues[index++] = blue;

						c = new Color(img.getRGB(x + 1, y - 1));
						red = c.getRed();
						green = c.getGreen();
						blue = c.getBlue();
						redValues[index] = red;
						greenValues[index] = green;
						blueValues[index++] = blue;

						c = new Color(img.getRGB(x + 1, y + 1));
						red = c.getRed();
						green = c.getGreen();
						blue = c.getBlue();
						redValues[index] = red;
						greenValues[index] = green;
						blueValues[index++] = blue;

						c = new Color(img.getRGB(x - 1, y + 1));
						red = c.getRed();
						green = c.getGreen();
						blue = c.getBlue();
						redValues[index] = red;
						greenValues[index] = green;
						blueValues[index++] = blue;

						break;

					}

					finalSmoothedImage.setRGB(x, y, findAverage(redValues, greenValues, blueValues).getRGB());

				}
			}

		} catch (Exception e) {
			System.out.println("Exception occured in method findThreshold(): " + e.getMessage());
		}

		return finalSmoothedImage;
	}

	private static Color findAverage(int redValues[], int greenValues[], int blueValues[]) {
		int red = 0, green = 0, blue = 0;
		for (int i = 0; i < redValues.length; i++) {
			red += redValues[i];
			green += greenValues[i];
			blue += blueValues[i];
		}

		red = red / redValues.length;
		green = green / greenValues.length;
		blue = blue / blueValues.length;

		return new Color(red, green, blue);
	}

	public static void main(String args[]) {
		BufferedImage inputImage = null, outputImage = null;
		int NS = 4;
		try {

			File input = new File("sample.jpg");
			inputImage = ImageIO.read(input);
			outputImage = averagingSmoothing(inputImage, NS);
			File ouptut = new File("averaginSmoothingImage_"+NS+"_NS.jpg");
			ImageIO.write(outputImage, "jpg", ouptut);

		} catch (Exception e) {
			System.out.println("Exception occured: " + e.getMessage());
		}
	}

}

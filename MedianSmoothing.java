package code;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class MedianSmoothing {
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
		int intensityValue = 0;
		int index = 0;

		try {
			for (int x = 1; x < width - 1; x++) {
				for (int y = 1; y < height - 1; y++) {

					index = 0;

					Color c = new Color(img.getRGB(x - 1, y));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					intensityValue = (red + green + blue) / 3;
					arr[index] = intensityValue;
					redValues[index] = red;
					greenValues[index] = green;
					blueValues[index++] = blue;

					c = new Color(img.getRGB(x + 1, y));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					intensityValue = (red + green + blue) / 3;
					arr[index] = intensityValue;
					redValues[index] = red;
					greenValues[index] = green;
					blueValues[index++] = blue;

					c = new Color(img.getRGB(x, y - 1));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					intensityValue = (red + green + blue) / 3;
					arr[index] = intensityValue;
					redValues[index] = red;
					greenValues[index] = green;
					blueValues[index++] = blue;

					c = new Color(img.getRGB(x, y + 1));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					intensityValue = (red + green + blue) / 3;
					arr[index] = intensityValue;
					redValues[index] = red;
					greenValues[index] = green;
					blueValues[index++] = blue;

					switch (NS) {
					case 5:
						c = new Color(img.getRGB(x, y));
						red = c.getRed();
						green = c.getGreen();
						blue = c.getBlue();
						intensityValue = (red + green + blue) / 3;
						arr[index] = intensityValue;
						redValues[index] = red;
						greenValues[index] = green;
						blueValues[index++] = blue;
						break;
					case 9:
						c = new Color(img.getRGB(x, y));
						red = c.getRed();
						green = c.getGreen();
						blue = c.getBlue();
						intensityValue = (red + green + blue) / 3;
						arr[index] = intensityValue;
						redValues[index] = red;
						greenValues[index] = green;
						blueValues[index++] = blue;
					case 8:
						c = new Color(img.getRGB(x - 1, y - 1));
						red = c.getRed();
						green = c.getGreen();
						blue = c.getBlue();
						intensityValue = (red + green + blue) / 3;
						arr[index] = intensityValue;
						redValues[index] = red;
						greenValues[index] = green;
						blueValues[index++] = blue;

						c = new Color(img.getRGB(x + 1, y - 1));
						red = c.getRed();
						green = c.getGreen();
						blue = c.getBlue();
						intensityValue = (red + green + blue) / 3;
						arr[index] = intensityValue;
						redValues[index] = red;
						greenValues[index] = green;
						blueValues[index++] = blue;

						c = new Color(img.getRGB(x + 1, y + 1));
						red = c.getRed();
						green = c.getGreen();
						blue = c.getBlue();
						intensityValue = (red + green + blue) / 3;
						arr[index] = intensityValue;
						redValues[index] = red;
						greenValues[index] = green;
						blueValues[index++] = blue;

						c = new Color(img.getRGB(x - 1, y + 1));
						red = c.getRed();
						green = c.getGreen();
						blue = c.getBlue();
						intensityValue = (red + green + blue) / 3;
						arr[index] = intensityValue;
						redValues[index] = red;
						greenValues[index] = green;
						blueValues[index++] = blue;

						break;

					}

					finalSmoothedImage.setRGB(x, y, findMedian(arr, redValues, greenValues, blueValues).getRGB());

				}
			}

		} catch (Exception e) {
			System.out.println("Exception occured in method findThreshold(): " + e.getMessage());
		}

		return finalSmoothedImage;
	}

	private static Color findMedian(int arr[], int redValues[], int greenValues[], int blueValues[]) {
		int medianIntensity = 0;
		int arrayLength = arr.length;
		int auxArray[] = Arrays.copyOf(arr, arrayLength);
		int foundIndex = 0;

		Arrays.sort(arr);

		if (arrayLength % 2 == 0) {
			medianIntensity = arr[arrayLength / 2] + arr[(arrayLength / 2) - 1];
			medianIntensity /= 2;
			foundIndex = findIndex(auxArray, arr[arrayLength / 2]);

		} else {
			medianIntensity = arr[arrayLength / 2];
			foundIndex = findIndex(auxArray, medianIntensity);
		}

		return new Color(redValues[foundIndex], greenValues[foundIndex], blueValues[foundIndex]);
	}

	private static int findIndex(int auxArray[], int medianIntensity) {
		int index = -1;
		int i = 0;
		while (index == -1 && i < auxArray.length) {
			if (auxArray[i] == medianIntensity) {
				index = i;
				break;
			}
			i++;
		}

		return index;
	}

	public static void main(String args[]) {
		BufferedImage inputImage = null, outputImage = null;
		int NS = 4;
		try {

			File input = new File("shanghai.jpg");
			inputImage = ImageIO.read(input);
			int k = input.getName().trim().indexOf('.');
			outputImage = averagingSmoothing(inputImage, NS);
			File ouptut = new File(input.getName().substring(0, k)+"_medianSmoothingImage_" + NS + "_NS.jpg");
			ImageIO.write(outputImage, "jpg", ouptut);

		} catch (Exception e) {
			System.out.println("Exception occured: " + e.getMessage());
		}
	}

}

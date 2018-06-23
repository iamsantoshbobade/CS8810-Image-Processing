package code;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LastMinuteWindow extends JFrame {

	private static int WIDTH = 1000;
	private static int HEIGHT = 1000;

	public static long noOfPixels = 0;

	private JMenuBar mainMenuBar;
	private JMenu fileMenu;
	private JMenu utilitiesMenu;

	private JMenuItem openImage;
	private JMenuItem exit;

	private JMenuItem greyScale;
	private JMenu noise;
	private JMenuItem saltNpepper;
	private JMenuItem someOtherNoise;

	private JMenu smoothing;
	private JMenuItem averaging;
	private JMenuItem median;

	private JMenu threshold;
	private JMenuItem simpleThreshold;
	private JMenuItem ptileThreshold;
	private JMenuItem iterativeThreshold;

	private JMenuItem contrastEnhancement;

	private JPanel buttonPanel;
	private JButton convert;

	public static JTextField redGreyScaleTextField;
	public static JTextField greenGreyScaleTextField;
	public static JTextField blueGreyScaleTextField;

	private JLabel inputImageLabel;
	private JLabel outputImageLabel;

	public static File input = null;
	public static File output = null;
	public static String filePath = System.getProperty("user.dir");
	public static String fileName = "";
	public static LastMinuteWindow staticWindow = null;
	public static boolean fileSelected = false;
	public static boolean goClicked = false;
	public static String filepath = "";
	public static String operation = "";
	public static boolean processingCompleted = false;

	public LastMinuteWindow(String title) {
		super("Image Project");
		mainMenuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		utilitiesMenu = new JMenu("Utilities");
		openImage = new JMenuItem("Open");
		exit = new JMenuItem("Exit");
		greyScale = new JMenuItem("Grey Scale");
		noise = new JMenu("Add Noise");
		saltNpepper = new JMenuItem("Salt N Pepper");
		someOtherNoise = new JMenuItem("Some Other Noise");
		smoothing = new JMenu("Smoothing");
		averaging = new JMenuItem("Averaging");
		median = new JMenuItem("Median");
		threshold = new JMenu("Threshold");
		simpleThreshold = new JMenuItem("Simple");
		ptileThreshold = new JMenuItem("P-Tile");
		iterativeThreshold = new JMenuItem("Iterative");

		contrastEnhancement = new JMenuItem("Contrast Enhancement");

		inputImageLabel = new JLabel("");
		outputImageLabel = new JLabel("");

		add(inputImageLabel);
		add(outputImageLabel);

		fileMenu.setMnemonic('F');
		openImage.setMnemonic('O');
		exit.setMnemonic('X');
		greyScale.setMnemonic('G');
		utilitiesMenu.setMnemonic('U');
		noise.setMnemonic('N');
		smoothing.setMnemonic('S');
		threshold.setMnemonic('T');
		simpleThreshold.setMnemonic('S');
		ptileThreshold.setMnemonic('P');
		iterativeThreshold.setMnemonic('I');
		contrastEnhancement.setMnemonic('C');

		setJMenuBar(mainMenuBar);

		mainMenuBar.add(fileMenu);
		mainMenuBar.add(utilitiesMenu);

		fileMenu.add(openImage);
		fileMenu.add(exit);

		utilitiesMenu.add(greyScale);
		noise.add(saltNpepper);
		noise.add(someOtherNoise);
		utilitiesMenu.add(noise);

		smoothing.add(averaging);
		smoothing.add(median);
		utilitiesMenu.add(smoothing);

		threshold.add(simpleThreshold);
		threshold.add(ptileThreshold);
		threshold.add(iterativeThreshold);
		utilitiesMenu.add(threshold);

		utilitiesMenu.add(contrastEnhancement);

		openImage.addActionListener(new OpenImageAction());
		exit.addActionListener(new ExitProjectAction());

		greyScale.addActionListener(new GreyscaleAction());

		saltNpepper.addActionListener(new SaltAndPepperAction());
		someOtherNoise.addActionListener(new SaltAndPepperAction());// STILL
																	// THINKING

		averaging.addActionListener(new AveragingSmoothingAction());
		median.addActionListener(new MedianSmoothingAction());

		simpleThreshold.addActionListener(new SimpleThresholdAction());
		ptileThreshold.addActionListener(new PercentileThresholdAction());
		iterativeThreshold.addActionListener(new IterativedThresholdAction());

		contrastEnhancement.addActionListener(new ContrastEnhancemntAction());

		buttonPanel = new JPanel();
		buttonPanel.setBounds(550, 550, 200, 200);

		redGreyScaleTextField = new JTextField("", 25);
		greenGreyScaleTextField = new JTextField("", 25);
		blueGreyScaleTextField = new JTextField("", 25);

		convert = new JButton("Go!");
		convert.setBounds(5, 5, 20, 20);
		// convert.setSize(150, 50);
		convert.setEnabled(true);
		convert.setToolTipText("Press to start processing the image.");
		convert.addActionListener(new GoButtonAction());
		// buttonPanel.add(BorderLayout.CENTER,convert);

		redGreyScaleTextField.setBounds(50, 50, 30, 30);
		greenGreyScaleTextField.setBounds(90, 90, 30, 30);
		blueGreyScaleTextField.setBounds(140, 140, 30, 30);
		add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.add(convert);
		buttonPanel.add(redGreyScaleTextField);
		buttonPanel.add(greenGreyScaleTextField);
		buttonPanel.add(blueGreyScaleTextField);

		redGreyScaleTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				redGreyScaleTextField.setText("");
			}
		});

		greenGreyScaleTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				greenGreyScaleTextField.setText("");
			}
		});

		blueGreyScaleTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				blueGreyScaleTextField.setText("");
			}
		});

		setSize(WIDTH, HEIGHT);
		setVisible(true);
		setLocationRelativeTo(null);
		setEnabled(true);
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		staticWindow = this;

		redGreyScaleTextField.setVisible(false);
		greenGreyScaleTextField.setVisible(false);
		blueGreyScaleTextField.setVisible(false);
	}

	public static BufferedImage greyScale(BufferedImage image, double R, double G, double B) {

		int height = image.getHeight();
		int width = image.getWidth();
		BufferedImage finalGrescaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		try {
			for (int i = 0; i < width; i++) {

				for (int j = 0; j < height; j++) {

					Color c = new Color(image.getRGB(i, j));
					int red = (int) (c.getRed() * R);
					int green = (int) (c.getGreen() * G);
					int blue = (int) (c.getBlue() * B);
					int intensityValue = red + green + blue;
					Color newColor = new Color(intensityValue, intensityValue, intensityValue);

					finalGrescaledImage.setRGB(i, j, newColor.getRGB());
				}
			}

		} catch (Exception e) {
			System.out.println("Exception occured in method greyScale(): " + e.getMessage());
		}

		return finalGrescaledImage;

	}

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
			saltnpepperedImage.setRGB(x, y, newColor.getRGB());
		}

		return saltnpepperedImage;
	}

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
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {

					Color c;

					if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
						c = new Color(img.getRGB(x, y));
						red = c.getRed();
						green = c.getGreen();
						blue = c.getBlue();
						finalSmoothedImage.setRGB(x, y, c.getRGB());
						continue;
					}

					index = 0;

					c = new Color(img.getRGB(x - 1, y));
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

	public static BufferedImage medianSmoothing(BufferedImage img, int NS) {
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
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {

					Color c;

					if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
						c = new Color(img.getRGB(x, y));
						red = c.getRed();
						green = c.getGreen();
						blue = c.getBlue();
						finalSmoothedImage.setRGB(x, y, c.getRGB());
						continue;
					}

					index = 0;

					c = new Color(img.getRGB(x - 1, y));
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

	public static BufferedImage simpleThresholding(BufferedImage img, int requiredThresholdValue) {
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

	public static BufferedImage percentileThresholding(BufferedImage img, int percentile) {
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

	public static BufferedImage iterativeThresholind(BufferedImage img, int threshold) {
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

			if (Math.abs(oldThreshold - newThreshold) < eps)
				break;
			oldThreshold = newThreshold;

		} while (true);

		return finalThresholdImage;
	}

	public static BufferedImage enhanceContrast(BufferedImage img) {
		int height = img.getHeight();
		int width = img.getWidth();
		noOfPixels = height * width;
		int intensityFrequency[] = new int[256];
		// int cumulativeFrequency[] = new int[256];
		// double cumulativeProbability[] = new double[256];
		int intArray[] = new int[(int) noOfPixels];
		BufferedImage contrastEnhancedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

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

			while (intensityFrequency[index] < 200)
				index++;
			blow = index;
			index = 255;
			while (intensityFrequency[index] > 200)
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
						contrastEnhancedImage.setRGB(x, y, newColor.getRGB());
					} else if (intensityValue > bhigh) {
						newColor = new Color(255, 255, 255);
						contrastEnhancedImage.setRGB(x, y, newColor.getRGB());
					} else {
						double temp = ((double) (intensityValue - blow)) / (bhigh - blow);
						temp = temp * (intensityFrequency.length - 1);
						intensityValue = (int) Math.floor(temp);
						newColor = new Color(intensityValue, intensityValue, intensityValue);
						contrastEnhancedImage.setRGB(x, y, newColor.getRGB());
					}

				}
			}

		} catch (Exception e) {
			System.out.println("Exception occured in method findThreshold(): " + e.getMessage());
		}

		return contrastEnhancedImage;
	}

	public static void main(String[] args) throws IOException {
		LastMinuteWindow mainWindow = new LastMinuteWindow("Image Project");

		while (true) {
			System.out.print("");

			ImageIcon icon = new ImageIcon();

			if (fileSelected) {
				String fileName = LastMinuteWindow.filepath;
				icon = new ImageIcon(fileName);

				mainWindow.inputImageLabel.setIcon(icon);
				mainWindow.inputImageLabel.setBounds(10, 0, 600, 600);

				if (goClicked) {
					String command = LastMinuteWindow.operation;
					// System.out.println(command);
					switch (command) {
					case "greyscale":
						boolean redEntered = (LastMinuteWindow.redGreyScaleTextField.getText().trim().length() != 0);
						boolean greenEntered = (LastMinuteWindow.greenGreyScaleTextField.getText().trim()
								.length() != 0);
						boolean blueEntered = (LastMinuteWindow.blueGreyScaleTextField.getText().trim().length() != 0);
						double red = 0.0, green = 0.0, blue = 0.0;
						if (redEntered)
							red = Double.parseDouble(LastMinuteWindow.redGreyScaleTextField.getText());
						if (greenEntered)
							green = Double.parseDouble(LastMinuteWindow.greenGreyScaleTextField.getText());
						if (blueEntered)
							blue = Double.parseDouble(LastMinuteWindow.blueGreyScaleTextField.getText());

						BufferedImage grescaledImage = greyScale(ImageIO.read(mainWindow.input), red, green, blue);
						mainWindow.outputImageLabel.setIcon(new ImageIcon(grescaledImage));
						mainWindow.outputImageLabel.setBounds(650, 0, 600, 600);
						File grescaledFile = new File(System.getProperty("user.dir") + File.separator + "output"
								+ File.separator + "grescaled.jpg");
						ImageIO.write(grescaledImage, "jpg", grescaledFile);
						goClicked = false;

						break;
					case "saltandpepper":
						boolean saltEntered = (LastMinuteWindow.redGreyScaleTextField.getText().trim().length() != 0);
						boolean pepperEntered = (LastMinuteWindow.greenGreyScaleTextField.getText().trim()
								.length() != 0);
						int salt = 0, pepper = 0;
						if (saltEntered)
							salt = Integer.parseInt(LastMinuteWindow.redGreyScaleTextField.getText());
						if (pepperEntered)
							pepper = Integer.parseInt(LastMinuteWindow.greenGreyScaleTextField.getText());
						BufferedImage saltandpepperImage = saltnpepper(ImageIO.read(mainWindow.input), salt, pepper);
						mainWindow.outputImageLabel.setIcon(new ImageIcon(saltandpepperImage));
						mainWindow.outputImageLabel.setBounds(icon.getIconWidth() + 20, 0, 600, 600);
						File saltandpepperFile = new File(System.getProperty("user.dir") + File.separator + "output"
								+ File.separator + "saltnpeppered_"+salt+"_"+pepper+".jpg");
						ImageIO.write(saltandpepperImage, "jpg", saltandpepperFile);
						mainWindow.convert.doClick();
						goClicked = false;

						break;
					case "averagingsmoothing":
						boolean noOfNeighboursEntered = (LastMinuteWindow.redGreyScaleTextField.getText().trim()
								.length() != 0);
						int NS = 4;
						if (noOfNeighboursEntered)
							NS = Integer.parseInt(LastMinuteWindow.redGreyScaleTextField.getText());
						BufferedImage averagingsmoothedImage = averagingSmoothing(ImageIO.read(mainWindow.input), NS);
						mainWindow.outputImageLabel.setIcon(new ImageIcon(averagingsmoothedImage));
						mainWindow.outputImageLabel.setBounds(icon.getIconWidth() + 20, 0, 600, 600);
						File averagedsmoothedFile = new File(System.getProperty("user.dir") + File.separator + "output"
								+ File.separator + "averagingsmoothed_NS" + NS + ".jpg");
						ImageIO.write(averagingsmoothedImage, "jpg", averagedsmoothedFile);
						mainWindow.convert.doClick();
						goClicked = false;
						break;
					case "mediansmoothing":

						noOfNeighboursEntered = (LastMinuteWindow.redGreyScaleTextField.getText().trim().length() != 0);
						NS = 4;
						if (noOfNeighboursEntered)
							NS = Integer.parseInt(LastMinuteWindow.redGreyScaleTextField.getText());
						BufferedImage mediansmoothedImage = medianSmoothing(ImageIO.read(mainWindow.input), NS);
						mainWindow.outputImageLabel.setIcon(new ImageIcon(mediansmoothedImage));
						mainWindow.outputImageLabel.setBounds(icon.getIconWidth() + 20, 0, 600, 600);
						File mediansmoothedFile = new File(System.getProperty("user.dir") + File.separator + "output"
								+ File.separator + "mediansmoothed_NS" + NS + ".jpg");
						ImageIO.write(mediansmoothedImage, "jpg", mediansmoothedFile);
						mainWindow.convert.doClick();
						goClicked = false;
						break;
					case "simplethreshold":
						boolean thresholdEntered = (LastMinuteWindow.redGreyScaleTextField.getText().trim()
								.length() != 0);
						int threshold = 125;
						if (thresholdEntered)
							threshold = Integer.parseInt(LastMinuteWindow.redGreyScaleTextField.getText());
						BufferedImage simpleThresholdedImage = simpleThresholding(ImageIO.read(mainWindow.input),
								threshold);
						mainWindow.outputImageLabel.setIcon(new ImageIcon(simpleThresholdedImage));
						mainWindow.outputImageLabel.setBounds(icon.getIconWidth() + 20, 0, 600, 600);
						File simpleThresholdedFile = new File(System.getProperty("user.dir") + File.separator + "output"
								+ File.separator + "simpleThresholded_threshold" + threshold + ".jpg");
						ImageIO.write(simpleThresholdedImage, "jpg", simpleThresholdedFile);
						mainWindow.convert.doClick();
						goClicked = false;
						break;
					case "percentilethreshold":
						thresholdEntered = (LastMinuteWindow.redGreyScaleTextField.getText().trim().length() != 0);
						threshold = 50;
						if (thresholdEntered)
							threshold = Integer.parseInt(LastMinuteWindow.redGreyScaleTextField.getText());
						BufferedImage percentileThresholdedImage = percentileThresholding(ImageIO.read(mainWindow.input),
								threshold);
						mainWindow.outputImageLabel.setIcon(new ImageIcon(percentileThresholdedImage));
						mainWindow.outputImageLabel.setBounds(icon.getIconWidth() + 20, 0, 600, 600);
						File percentileThresholdedFile = new File(System.getProperty("user.dir") + File.separator
								+ "output" + File.separator + "percentileThresholded_threshold" + threshold + ".jpg");
						ImageIO.write(percentileThresholdedImage, "jpg", percentileThresholdedFile);
						mainWindow.convert.doClick();
						goClicked = false;
						break;
					case "iterativethreshold":
						thresholdEntered = (LastMinuteWindow.redGreyScaleTextField.getText().trim().length() != 0);
						threshold = 125;
						if (thresholdEntered)
							threshold = Integer.parseInt(LastMinuteWindow.redGreyScaleTextField.getText());
						BufferedImage iterativeThresholdedImage = iterativeThresholind(ImageIO.read(mainWindow.input),
								threshold);
						mainWindow.outputImageLabel.setIcon(new ImageIcon(iterativeThresholdedImage));
						mainWindow.outputImageLabel.setBounds(icon.getIconWidth() + 20, 0, 600, 600);
						File iterativeThresholdedFile = new File(System.getProperty("user.dir") + File.separator
								+ "output" + File.separator + "iterativeThresholded_threshold" + threshold + ".jpg");
						ImageIO.write(iterativeThresholdedImage, "jpg", iterativeThresholdedFile);
						mainWindow.convert.doClick();
						goClicked = false;
						break;
					case "contrastenhancement":
						BufferedImage contrastEnhancedImage = enhanceContrast(ImageIO.read(mainWindow.input));
						mainWindow.outputImageLabel.setIcon(new ImageIcon(contrastEnhancedImage));
						mainWindow.outputImageLabel.setBounds(icon.getIconWidth() + 20, 0, 600, 600);
						File contrastEnhancedFile = new File(System.getProperty("user.dir") + File.separator + "output"
								+ File.separator + "contrastEnhanced.jpg");
						ImageIO.write(contrastEnhancedImage, "jpg", contrastEnhancedFile);
						mainWindow.convert.doClick();
						goClicked = false;
						break;
					default:

					}
				}
			}

		}
	}
}

class ExitProjectAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}

}

class OpenImageAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
		int result = fileChooser.showOpenDialog(LastMinuteWindow.staticWindow);
		if (result == JFileChooser.APPROVE_OPTION) {
			LastMinuteWindow.input = fileChooser.getSelectedFile();
			LastMinuteWindow.fileSelected = true;
			LastMinuteWindow.goClicked = false;
			LastMinuteWindow.filepath = fileChooser.getCurrentDirectory().getAbsolutePath() + File.separator
					+ fileChooser.getSelectedFile().getName();
		}

	}

}

class GoButtonAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		LastMinuteWindow.goClicked = true;

	}

}

class GreyscaleAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		LastMinuteWindow.operation = "greyscale";
		LastMinuteWindow.redGreyScaleTextField.setVisible(true);
		LastMinuteWindow.greenGreyScaleTextField.setVisible(true);
		LastMinuteWindow.blueGreyScaleTextField.setVisible(true);

		LastMinuteWindow.redGreyScaleTextField.setToolTipText("Enter Red here..");
		LastMinuteWindow.greenGreyScaleTextField.setToolTipText("Enter Green here..");
		LastMinuteWindow.blueGreyScaleTextField.setToolTipText("Enter Blue here..");
	}

}

class SaltAndPepperAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		LastMinuteWindow.operation = "saltandpepper";
		LastMinuteWindow.redGreyScaleTextField.setVisible(true);
		LastMinuteWindow.redGreyScaleTextField.setToolTipText("Enter Salt here..");
		LastMinuteWindow.greenGreyScaleTextField.setVisible(true);
		LastMinuteWindow.greenGreyScaleTextField.setToolTipText("Enter Pepper here..");
		LastMinuteWindow.blueGreyScaleTextField.setVisible(false);
	}

}

class AveragingSmoothingAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		LastMinuteWindow.operation = "averagingsmoothing";
		LastMinuteWindow.redGreyScaleTextField.setToolTipText("Enter value for NS here..");
		LastMinuteWindow.redGreyScaleTextField.setVisible(true);
		LastMinuteWindow.greenGreyScaleTextField.setVisible(false);
		LastMinuteWindow.blueGreyScaleTextField.setVisible(false);
	}

}

class MedianSmoothingAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		LastMinuteWindow.operation = "mediansmoothing";
		LastMinuteWindow.redGreyScaleTextField.setVisible(true);
		LastMinuteWindow.redGreyScaleTextField.setToolTipText("Enter value for NS here..");
		LastMinuteWindow.greenGreyScaleTextField.setVisible(false);
		LastMinuteWindow.blueGreyScaleTextField.setVisible(false);
	}

}

class SimpleThresholdAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		LastMinuteWindow.operation = "simplethreshold";
		LastMinuteWindow.redGreyScaleTextField.setVisible(true);
		LastMinuteWindow.redGreyScaleTextField.setToolTipText("Enter Threshold here..");
		LastMinuteWindow.greenGreyScaleTextField.setVisible(false);
		LastMinuteWindow.blueGreyScaleTextField.setVisible(false);
	}

}

class PercentileThresholdAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		LastMinuteWindow.operation = "percentilethreshold";
		LastMinuteWindow.redGreyScaleTextField.setVisible(true);
		LastMinuteWindow.redGreyScaleTextField.setText("Enter Threshold  here..");
		LastMinuteWindow.greenGreyScaleTextField.setVisible(false);
		LastMinuteWindow.blueGreyScaleTextField.setVisible(false);
	}

}

class IterativedThresholdAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		LastMinuteWindow.operation = "iterativethreshold";
		LastMinuteWindow.redGreyScaleTextField.setVisible(true);
		LastMinuteWindow.redGreyScaleTextField.setToolTipText("Enter Threshold here..");
		LastMinuteWindow.greenGreyScaleTextField.setVisible(false);
		LastMinuteWindow.blueGreyScaleTextField.setVisible(false);
	}

}

class ContrastEnhancemntAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		LastMinuteWindow.operation = "contrastenhancement";
		LastMinuteWindow.redGreyScaleTextField.setVisible(false);
		LastMinuteWindow.greenGreyScaleTextField.setVisible(false);
		LastMinuteWindow.blueGreyScaleTextField.setVisible(false);
	}

}
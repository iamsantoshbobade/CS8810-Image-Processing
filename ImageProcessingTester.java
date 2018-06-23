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

public class ImageProcessingTester extends JFrame {

	private static int WIDTH = 1200;
	private static int HEIGHT = 1200;

	public static int NUMBER = 0;

	public static int start = 0;

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

	private JMenu edgedetection;
	private JMenuItem robertsoperator;
	private JMenuItem sobeloperator;
	private JMenuItem kirshoperator;
	private JMenuItem laplacianoperator;
	private JMenuItem sobelOperator;

	private JMenuItem constructPyramid;

	private JMenu pyramidExpansion;
	private JMenuItem zeroOrderExpansion;
	private JMenuItem firstOrderExapnsion;

	private JMenuItem componentLabel;
	// private JMenuItem horizontalPrewittOperator;
	// private JMenuItem verticalPrewittOperator;

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
	public static ImageProcessingTester staticWindow = null;
	public static boolean fileSelected = false;
	public static boolean goClicked = false;
	public static String filepath = "";
	public static String operation = "";
	public static boolean processingCompleted = false;

	public ImageProcessingTester(String title) {
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

		edgedetection = new JMenu("Edge Detection");
		robertsoperator = new JMenuItem("Robert's Operator");
		sobeloperator = new JMenuItem("Sobel Operator");
		kirshoperator = new JMenuItem("Kirsh Operator");
		laplacianoperator = new JMenuItem("Laplacian Operator");

		componentLabel = new JMenuItem("Component Labellibg");
		constructPyramid = new JMenuItem("Construct Pyramid");

		pyramidExpansion = new JMenu("Pyramid Expansion");
		zeroOrderExpansion = new JMenuItem("Zero Order");
		firstOrderExapnsion = new JMenuItem("First Order");

		// sobelOperator = new JMenuItem("Sobel Operator");
		// positivePrewittOperator = new JMenuItem("Positive Prewitt Operator");
		// nega

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
		edgedetection.setMnemonic('D');
		robertsoperator.setMnemonic('R');
		sobeloperator.setMnemonic('B');
		kirshoperator.setMnemonic('K');
		laplacianoperator.setMnemonic('L');

		setJMenuBar(mainMenuBar);

		mainMenuBar.add(fileMenu);
		mainMenuBar.add(utilitiesMenu);

		fileMenu.add(openImage);
		fileMenu.add(exit);

		utilitiesMenu.add(greyScale);
		noise.add(saltNpepper);
		// noise.add(someOtherNoise);
		utilitiesMenu.add(noise);

		smoothing.add(averaging);
		smoothing.add(median);
		utilitiesMenu.add(smoothing);

		threshold.add(simpleThreshold);
		threshold.add(ptileThreshold);
		threshold.add(iterativeThreshold);
		utilitiesMenu.add(threshold);

		utilitiesMenu.add(contrastEnhancement);

		edgedetection.add(robertsoperator);
		edgedetection.add(sobeloperator);
		edgedetection.add(kirshoperator);
		edgedetection.add(laplacianoperator);
		utilitiesMenu.add(edgedetection);

		utilitiesMenu.add(componentLabel);
		utilitiesMenu.add(constructPyramid);

		pyramidExpansion.add(zeroOrderExpansion);
		pyramidExpansion.add(firstOrderExapnsion);
		utilitiesMenu.add(pyramidExpansion);

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

		robertsoperator.addActionListener(new RobertsOperatorAction());
		sobeloperator.addActionListener(new SobelOperatorAction());
		kirshoperator.addActionListener(new KirshOperatorAction());
		laplacianoperator.addActionListener(new LaplacianActionListener());

		componentLabel.addActionListener(new ComponentLabelActionListener());

		constructPyramid.addActionListener(new ConstructPyramidActionListener());

		zeroOrderExpansion.addActionListener(new ZeroOrderExpansionActionListener());
		firstOrderExapnsion.addActionListener(new FirstOrderExpansionActionListener());

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
					int intensityValue = (red + green + blue) / 3;
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
		start = 0;
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

		/*
		 * System.out.println("Total pixels: " + (img.getHeight() *
		 * img.getWidth())); int white = 0, black = 0;
		 * 
		 * for (int x = 0; x < height; x++) { for (int y = 0; y < width; y++) {
		 * Color c = new Color(finalThresholdImage.getRGB(y, x)); if
		 * (c.equals(Color.BLACK)) black++; else white++; } }
		 * System.out.println("BLACK " + black + " WHITE " + white);
		 */

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

	public static BufferedImage iterativeThresholding(BufferedImage img, int threshold) {
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
		int cumulativeFrequency[] = new int[256];
		double cumulativeProbability[] = new double[256];
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

			cumulativeFrequency[0] = intensityFrequency[0];
			cumulativeProbability[0] = ((double) cumulativeFrequency[0]) / noOfPixels;

			for (int i = 1; i < intensityFrequency.length; i++) {
				cumulativeFrequency[i] = intensityFrequency[i] + cumulativeFrequency[i - 1];
				cumulativeProbability[i] = ((double) cumulativeFrequency[i]) / noOfPixels;
			}
			for (int i = 0; i < 256; i++) {
				System.out.print(" value of i " + i + " normnal freq " + intensityFrequency[i]);
				System.out.println(" cumu " + cumulativeFrequency[i] + " cumu prob : " + cumulativeProbability[i]);
			}

			while (intensityFrequency[index] < 200)
				index++;
			blow = index;
			index = 255;
			while (intensityFrequency[index] < 200)
				index--;
			bhigh = index;

			// blow=0;
			// bhigh=20;

			System.out.println("blow" + blow);
			System.out.println("bhigh" + bhigh);
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

	private static BufferedImage robertsOperator(BufferedImage img) {
		int height = img.getHeight();
		int width = img.getWidth();
		BufferedImage robertsOperatorImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int red = 0;
		int green = 0;
		int blue = 0;
		int ans = 0;
		try {

			for (int x = 0; x < width - 1; x++) {
				for (int y = 0; y < height - 1; y++) {
					Color c = new Color(img.getRGB(x, y));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					int mod1 = (red + green + blue) / 3;

					c = new Color(img.getRGB(x + 1, y + 1));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();

					mod1 = Math.abs(mod1 - ((red + green + blue) / 3));

					c = new Color(img.getRGB(x + 1, y));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();

					int mod2 = (red + green + blue) / 3;

					c = new Color(img.getRGB(x, y + 1));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					mod2 = Math.abs(mod2 - ((red + green + blue) / 3));

					ans = mod1 + mod2;
					if (ans > 255)
						ans = 255;

					c = new Color(ans, ans, ans);
					robertsOperatorImage.setRGB(x, y, c.getRGB());

				}
			}

		} catch (Exception e) {
			System.out.println("Exception occured in robertsOperator(): " + e.getMessage() + " " + ans);
		}

		return robertsOperatorImage;
	}

	private static BufferedImage constructLevel1(BufferedImage img) {
		int width = img.getWidth();
		int height = img.getHeight();
		BufferedImage outputImage = new BufferedImage(width / 2, height / 2, BufferedImage.TYPE_INT_RGB);

		try {

			int red = 0;
			int green = 0;
			int blue = 0;
			int redValues[] = new int[4];
			int blueValues[] = new int[4];
			int greenValues[] = new int[4];
			for (int x = 0, newx = 0; x < width - 1; x += 2, newx++) {
				for (int y = 0, newy = 0; y < height - 1; y += 2, newy++) {
					int index = 0;

					Color c = new Color(img.getRGB(x, y));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();

					redValues[index] = red;
					greenValues[index] = green;
					blueValues[index++] = blue;

					// int intensityValue = (red + green + blue) / 3;

					c = new Color(img.getRGB(x + 1, y));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					// intensityValue += (red + green + blue) / 3;
					redValues[index] = red;
					greenValues[index] = green;
					blueValues[index++] = blue;

					c = new Color(img.getRGB(x, y + 1));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					// intensityValue += (red + green + blue) / 3;
					redValues[index] = red;
					greenValues[index] = green;
					blueValues[index++] = blue;

					c = new Color(img.getRGB(x + 1, y + 1));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					// intensityValue = (red + green + blue) / 3;
					redValues[index] = red;
					greenValues[index] = green;
					greenValues[index++] = blue;

					red = blue = green = 0;
					for (int k = 0; k < 4; k++) {
						red += redValues[k];
						green += greenValues[k];
						blue += greenValues[k];
					}

					red /= 4;
					green /= 4;
					blue /= 4;

					outputImage.setRGB(newx, newy, new Color(red, green, blue).getRGB());

				}
			}

			File op = new File(
					System.getProperty("user.dir") + File.separator + "output" + File.separator + "level1.jpg");
			ImageIO.write(outputImage, "jpg", op);

		} catch (Exception e) {
			System.out.println("Exception occured in level 1 construction method.");
			e.printStackTrace();
		}

		return outputImage;
	}

	private static BufferedImage constructLevel2(BufferedImage img) {
		int width = img.getWidth();
		int height = img.getHeight();
		BufferedImage outputImage = new BufferedImage(width / 2, height / 2, BufferedImage.TYPE_INT_RGB);

		try {

			int red = 0;
			int green = 0;
			int blue = 0;
			int redValues[] = new int[4];
			int blueValues[] = new int[4];
			int greenValues[] = new int[4];
			for (int x = 0, newx = 0; x < width - 1; x += 2, newx++) {
				for (int y = 0, newy = 0; y < height - 1; y += 2, newy++) {
					int index = 0;

					Color c = new Color(img.getRGB(x, y));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();

					redValues[index] = red;
					greenValues[index] = green;
					blueValues[index++] = blue;

					// int intensityValue = (red + green + blue) / 3;

					c = new Color(img.getRGB(x + 1, y));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					// intensityValue += (red + green + blue) / 3;
					redValues[index] = red;
					greenValues[index] = green;
					blueValues[index++] = blue;

					c = new Color(img.getRGB(x, y + 1));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					// intensityValue += (red + green + blue) / 3;
					redValues[index] = red;
					greenValues[index] = green;
					blueValues[index++] = blue;

					c = new Color(img.getRGB(x + 1, y + 1));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					// intensityValue = (red + green + blue) / 3;
					redValues[index] = red;
					greenValues[index] = green;
					greenValues[index++] = blue;

					red = blue = green = 0;
					for (int k = 0; k < 4; k++) {
						red += redValues[k];
						green += greenValues[k];
						blue += greenValues[k];
					}

					red /= 4;
					green /= 4;
					blue /= 4;

					outputImage.setRGB(newx, newy, new Color(red, green, blue).getRGB());

				}
			}

			File op = new File(
					System.getProperty("user.dir") + File.separator + "output" + File.separator + "level2.jpg");
			ImageIO.write(outputImage, "jpg", op);

		} catch (Exception e) {
			System.out.println("Exception occured in level 2 construction method.");
			e.printStackTrace();
		}

		return outputImage;
	}

	private static BufferedImage constructLevel3(BufferedImage img) {
		int width = img.getWidth();
		int height = img.getHeight();
		BufferedImage outputImage = new BufferedImage(width / 2, height / 2, BufferedImage.TYPE_INT_RGB);

		try {

			int red = 0;
			int green = 0;
			int blue = 0;
			int redValues[] = new int[4];
			int blueValues[] = new int[4];
			int greenValues[] = new int[4];
			for (int x = 0, newx = 0; x < width - 1; x += 2, newx++) {
				for (int y = 0, newy = 0; y < height - 1; y += 2, newy++) {
					int index = 0;

					Color c = new Color(img.getRGB(x, y));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();

					redValues[index] = red;
					greenValues[index] = green;
					blueValues[index++] = blue;

					// int intensityValue = (red + green + blue) / 3;

					c = new Color(img.getRGB(x + 1, y));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					// intensityValue += (red + green + blue) / 3;
					redValues[index] = red;
					greenValues[index] = green;
					blueValues[index++] = blue;

					c = new Color(img.getRGB(x, y + 1));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					// intensityValue += (red + green + blue) / 3;
					redValues[index] = red;
					greenValues[index] = green;
					blueValues[index++] = blue;

					c = new Color(img.getRGB(x + 1, y + 1));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					// intensityValue = (red + green + blue) / 3;
					redValues[index] = red;
					greenValues[index] = green;
					greenValues[index++] = blue;

					red = blue = green = 0;
					for (int k = 0; k < 4; k++) {
						red += redValues[k];
						green += greenValues[k];
						blue += greenValues[k];
					}

					red /= 4;
					green /= 4;
					blue /= 4;

					outputImage.setRGB(newx, newy, new Color(red, green, blue).getRGB());

				}
			}

			File op = new File(
					System.getProperty("user.dir") + File.separator + "output" + File.separator + "level3.jpg");
			ImageIO.write(outputImage, "jpg", op);

		} catch (Exception e) {
			System.out.println("Exception occured in level 3 construction method.");
			e.printStackTrace();
		}

		return outputImage;
	}

	private static BufferedImage constructLevel4(BufferedImage img) {
		int width = img.getWidth();
		int height = img.getHeight();
		BufferedImage outputImage = new BufferedImage(width / 2, height / 2, BufferedImage.TYPE_INT_RGB);

		try {

			int red = 0;
			int green = 0;
			int blue = 0;
			int redValues[] = new int[4];
			int blueValues[] = new int[4];
			int greenValues[] = new int[4];
			for (int x = 0, newx = 0; x < width - 1; x += 2, newx++) {
				for (int y = 0, newy = 0; y < height - 1; y += 2, newy++) {
					int index = 0;

					Color c = new Color(img.getRGB(x, y));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();

					redValues[index] = red;
					greenValues[index] = green;
					blueValues[index++] = blue;

					// int intensityValue = (red + green + blue) / 3;

					c = new Color(img.getRGB(x + 1, y));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					// intensityValue += (red + green + blue) / 3;
					redValues[index] = red;
					greenValues[index] = green;
					blueValues[index++] = blue;

					c = new Color(img.getRGB(x, y + 1));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					// intensityValue += (red + green + blue) / 3;
					redValues[index] = red;
					greenValues[index] = green;
					blueValues[index++] = blue;

					c = new Color(img.getRGB(x + 1, y + 1));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					// intensityValue = (red + green + blue) / 3;
					redValues[index] = red;
					greenValues[index] = green;
					greenValues[index++] = blue;

					red = blue = green = 0;
					for (int k = 0; k < 4; k++) {
						red += redValues[k];
						green += greenValues[k];
						blue += greenValues[k];
					}

					red /= 4;
					green /= 4;
					blue /= 4;

					outputImage.setRGB(newx, newy, new Color(red, green, blue).getRGB());

				}
			}

			File op = new File(
					System.getProperty("user.dir") + File.separator + "output" + File.separator + "level4.jpg");
			ImageIO.write(outputImage, "jpg", op);

		} catch (Exception e) {
			System.out.println("Exception occured in level 4 construction method.");
			e.printStackTrace();
		}

		return outputImage;
	}

	private static BufferedImage constructLevel5(BufferedImage img) {
		int width = img.getWidth();
		int height = img.getHeight();
		BufferedImage outputImage = new BufferedImage(width / 2, height / 2, BufferedImage.TYPE_INT_RGB);

		try {

			int red = 0;
			int green = 0;
			int blue = 0;
			int redValues[] = new int[4];
			int blueValues[] = new int[4];
			int greenValues[] = new int[4];
			for (int x = 0, newx = 0; x < width - 1; x += 2, newx++) {
				for (int y = 0, newy = 0; y < height - 1; y += 2, newy++) {
					int index = 0;

					Color c = new Color(img.getRGB(x, y));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();

					redValues[index] = red;
					greenValues[index] = green;
					blueValues[index++] = blue;

					// int intensityValue = (red + green + blue) / 3;

					c = new Color(img.getRGB(x + 1, y));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					// intensityValue += (red + green + blue) / 3;
					redValues[index] = red;
					greenValues[index] = green;
					blueValues[index++] = blue;

					c = new Color(img.getRGB(x, y + 1));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					// intensityValue += (red + green + blue) / 3;
					redValues[index] = red;
					greenValues[index] = green;
					blueValues[index++] = blue;

					c = new Color(img.getRGB(x + 1, y + 1));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					// intensityValue = (red + green + blue) / 3;
					redValues[index] = red;
					greenValues[index] = green;
					greenValues[index++] = blue;

					red = blue = green = 0;
					for (int k = 0; k < 4; k++) {
						red += redValues[k];
						green += greenValues[k];
						blue += greenValues[k];
					}

					red /= 4;
					green /= 4;
					blue /= 4;

					outputImage.setRGB(newx, newy, new Color(red, green, blue).getRGB());

				}
			}

			File op = new File(
					System.getProperty("user.dir") + File.separator + "output" + File.separator + "level5.jpg");
			ImageIO.write(outputImage, "jpg", op);

		} catch (Exception e) {
			System.out.println("Exception occured in level 5 construction method.");
			e.printStackTrace();
		}

		return outputImage;
	}

	private static BufferedImage constructLevel6(BufferedImage img) {
		int width = img.getWidth();
		int height = img.getHeight();
		BufferedImage outputImage = new BufferedImage(width / 2, height / 2, BufferedImage.TYPE_INT_RGB);

		try {

			int red = 0;
			int green = 0;
			int blue = 0;
			int redValues[] = new int[4];
			int blueValues[] = new int[4];
			int greenValues[] = new int[4];
			for (int x = 0, newx = 0; x < width - 1; x += 2, newx++) {
				for (int y = 0, newy = 0; y < height - 1; y += 2, newy++) {
					int index = 0;

					Color c = new Color(img.getRGB(x, y));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();

					redValues[index] = red;
					greenValues[index] = green;
					blueValues[index++] = blue;

					// int intensityValue = (red + green + blue) / 3;

					c = new Color(img.getRGB(x + 1, y));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					// intensityValue += (red + green + blue) / 3;
					redValues[index] = red;
					greenValues[index] = green;
					blueValues[index++] = blue;

					c = new Color(img.getRGB(x, y + 1));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					// intensityValue += (red + green + blue) / 3;
					redValues[index] = red;
					greenValues[index] = green;
					blueValues[index++] = blue;

					c = new Color(img.getRGB(x + 1, y + 1));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					// intensityValue = (red + green + blue) / 3;
					redValues[index] = red;
					greenValues[index] = green;
					greenValues[index++] = blue;

					red = blue = green = 0;
					for (int k = 0; k < 4; k++) {
						red += redValues[k];
						green += greenValues[k];
						blue += greenValues[k];
					}

					red /= 4;
					green /= 4;
					blue /= 4;

					outputImage.setRGB(newx, newy, new Color(red, green, blue).getRGB());

				}
			}

			File op = new File(
					System.getProperty("user.dir") + File.separator + "output" + File.separator + "level6.jpg");
			ImageIO.write(outputImage, "jpg", op);

		} catch (Exception e) {
			System.out.println("Exception occured in level 6 construction method.");
			e.printStackTrace();
		}

		return outputImage;
	}

	private static void constructPyramid(BufferedImage img, int noofleveles) {
		try {
			if (noofleveles <= 0) {
				File outputImage = new File(
						System.getProperty("user.dir") + File.separator + "output" + File.separator + "level0.jpg");
				ImageIO.write(img, "jpg", outputImage);

				return;

			}

			/*
			 * File outputImage = new File( System.getProperty("user.dir") +
			 * File.separator + "output" + File.separator + "level0.jpg");
			 * ImageIO.write(img, "jpg", outputImage);
			 */

			int width = img.getWidth();
			int height = img.getHeight();

			BufferedImage outputImage = null;

			height /= 2;
			width /= 2;
			outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

			for (int i = 1; i <= noofleveles; i++) {
				switch (i) {
				case 1:
					outputImage = constructLevel1(img);
					break;
				case 2:
					height /= 2;
					width /= 2;
					BufferedImage outputImageLevel2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
					outputImage = ImageIO.read(new File(System.getProperty("user.dir") + File.separator + "output"
							+ File.separator + "level1.jpg"));
					outputImageLevel2 = constructLevel2(outputImage);
					break;
				case 3:
					height /= 2;
					width /= 2;
					BufferedImage outputImageLevel3 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
					outputImageLevel2 = ImageIO.read(new File(System.getProperty("user.dir") + File.separator + "output"
							+ File.separator + "level2.jpg"));
					outputImageLevel3 = constructLevel3(outputImageLevel2);
					break;
				case 4:
					height /= 2;
					width /= 2;
					BufferedImage outputImageLevel4 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
					outputImageLevel3 = ImageIO.read(new File(System.getProperty("user.dir") + File.separator + "output"
							+ File.separator + "level3.jpg"));
					outputImageLevel4 = constructLevel4(outputImageLevel3);
					break;
				case 5:
					height /= 2;
					width /= 2;
					BufferedImage outputImageLevel5 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
					outputImageLevel4 = ImageIO.read(new File(System.getProperty("user.dir") + File.separator + "output"
							+ File.separator + "level4.jpg"));
					outputImageLevel5 = constructLevel5(outputImageLevel4);
					break;
				case 6:
					height /= 2;
					width /= 2;
					BufferedImage outputImageLevel6 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
					outputImageLevel5 = ImageIO.read(new File(System.getProperty("user.dir") + File.separator + "output"
							+ File.separator + "level5.jpg"));
					outputImageLevel6 = constructLevel6(outputImageLevel5);
					break;

				}

			}

			System.out.println("Exited constructPyramid()..");
		} catch (Exception e) {
			System.out.println("Exception in constructPyramid() : " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static BufferedImage zeroOrderExpansion(BufferedImage img) {
		int height = img.getHeight();
		int width = img.getWidth();
		BufferedImage zeroOrderExpansionImage = new BufferedImage(width * 2, height * 2, BufferedImage.TYPE_INT_RGB);

		try {

			// Padding zeroes
			for (int x = 0, newx = 1; x < width; x++, newx += 2) {
				for (int y = 0, newy = 1; y < height; y++, newy += 2) {
					Color c = new Color(img.getRGB(x, y));
					zeroOrderExpansionImage.setRGB(newx, newy, c.getRGB());
				}
			}

			// Moving over unit mask
			width *= 2;
			height *= 2;
			for (int x = 0; x < width - 1; x += 2)
				for (int y = 0; y < height - 1; y += 2) {
					Color c = new Color(zeroOrderExpansionImage.getRGB(x + 1, y + 1));
					zeroOrderExpansionImage.setRGB(x, y, c.getRGB());
					zeroOrderExpansionImage.setRGB(x + 1, y, c.getRGB());
					zeroOrderExpansionImage.setRGB(x, y + 1, c.getRGB());

				}

		} catch (Exception e) {
			System.out.println("Exception occured in zeroOrderExpansion() " + e.getMessage());
		}

		return zeroOrderExpansionImage;

	}

	private static BufferedImage firstOrderExpansion(BufferedImage img) {
		int height = img.getHeight();
		int width = img.getWidth();
		BufferedImage firstOrderExpansionImage = new BufferedImage(width * 2, height * 2, BufferedImage.TYPE_INT_RGB);
		BufferedImage finalOutputImage = new BufferedImage(width * 2, height * 2, BufferedImage.TYPE_INT_RGB);

		try {

			// Padding zeroes
			for (int x = 0, newx = 1; x < width; x++, newx += 2) {
				for (int y = 0, newy = 1; y < height; y++, newy += 2) {
					Color c = new Color(img.getRGB(x, y));
					firstOrderExpansionImage.setRGB(newx, newy, c.getRGB());
				}
			}

			width *= 2;
			height *= 2;

			// Creating aux
			for (int x = 0; x < width; x++)
				for (int y = 0; y < height; y++) {
					Color c = new Color(firstOrderExpansionImage.getRGB(x, y));
					finalOutputImage.setRGB(x, y, c.getRGB());
				}

			// Moving over mask

			int red;
			int green;
			int blue;

			int sumOfRed = 0;
			int sumOfGreen = 0;
			int sumOfBlue = 0;
			final float onebyfour = 0.25f, onebytwo = 0.5f;

			int g = 0;

			for (int x = 1; x < width - 1; x++) {
				for (int y = 1; y < height - 1; y++) {
					Color c = new Color(firstOrderExpansionImage.getRGB(x, y));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					sumOfRed += red;
					sumOfGreen += green;
					sumOfBlue += blue;

					c = new Color(firstOrderExpansionImage.getRGB(x - 1, y - 1));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					sumOfRed += (int) (red * onebyfour);
					sumOfGreen += (int) (green * onebyfour);
					sumOfBlue += (int) (blue * onebyfour);

					c = new Color(firstOrderExpansionImage.getRGB(x, y - 1));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					sumOfRed += (int) (red * onebytwo);
					sumOfGreen += (int) (green * onebytwo);
					sumOfBlue += (int) (blue * onebytwo);

					c = new Color(firstOrderExpansionImage.getRGB(x + 1, y - 1));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					sumOfRed += (int) (red * onebyfour);
					sumOfGreen += (int) (green * onebyfour);
					sumOfBlue += (int) (blue * onebyfour);

					c = new Color(firstOrderExpansionImage.getRGB(x - 1, y));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					sumOfRed += (int) (red * onebytwo);
					sumOfGreen += (int) (green * onebytwo);
					sumOfBlue += (int) (blue * onebytwo);

					c = new Color(firstOrderExpansionImage.getRGB(x + 1, y));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					sumOfRed += (int) (red * onebytwo);
					sumOfGreen += (int) (green * onebytwo);
					sumOfBlue += (int) (blue * onebytwo);

					c = new Color(firstOrderExpansionImage.getRGB(x - 1, y + 1));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					sumOfRed += (int) (red * onebyfour);
					sumOfGreen += (int) (green * onebyfour);
					sumOfBlue += (int) (blue * onebyfour);

					c = new Color(firstOrderExpansionImage.getRGB(x, y + 1));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					sumOfRed += (int) (red * onebytwo);
					sumOfGreen += (int) (green * onebytwo);
					sumOfBlue += (int) (blue * onebytwo);

					c = new Color(firstOrderExpansionImage.getRGB(x + 1, y + 1));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					sumOfRed += (int) (red * onebyfour);
					sumOfGreen += (int) (green * onebyfour);
					sumOfBlue += (int) (blue * onebyfour);

					
					 sumOfRed /= 9; sumOfGreen /= 9; sumOfBlue /= 9;
					 

					/*
					 * sumOfRed = (sumOfRed > 255) ? 255 : sumOfRed; sumOfGreen
					 * = (sumOfGreen > 255) ? 255 : sumOfGreen; sumOfBlue =
					 * (sumOfBlue > 255) ? 255 : sumOfBlue;
					 */

					g++;

					finalOutputImage.setRGB(x, y, new Color(sumOfRed, sumOfGreen, sumOfBlue).getRGB());

				}
			}
			
			//System.out.println("After: "+g);

		} catch (Exception e) {
			System.out.println("Exception occured in firstOrderExpansion() " + e.getMessage() + " G: ");
		}

		return finalOutputImage;

	}

	private static BufferedImage sobelOperator(BufferedImage img) {
		int height = img.getHeight();
		int width = img.getWidth();
		BufferedImage sobelOperatorImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int red = 0;
		int green = 0;
		int blue = 0;
		int intensity = 0;
		final int one = 1;
		final int two = 2;
		int gx = 0;
		int gy = 0;
		try {

			for (int x = 1; x < width - 1; x++) {
				for (int y = 1; y < height - 1; y++) {
					Color c = new Color(img.getRGB(x - 1, y - 1));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					intensity = (red + green + blue) / 3;

					gx = intensity * (-1);
					gy = gx;

					c = new Color(img.getRGB(x, y - 1));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();

					intensity = (red + green + blue) / 3;

					intensity = intensity * (-2);
					gx = gx + intensity;

					c = new Color(img.getRGB(x + 1, y - 1));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();

					intensity = (red + green + blue) / 3;
					gy = gy + intensity;
					intensity = intensity * (-1);
					gx = gx + intensity;

					c = new Color(img.getRGB(x - 1, y));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					intensity = (red + green + blue) / 3;
					intensity = intensity * (-2);
					gy = gy + intensity;

					c = new Color(img.getRGB(x + 1, y));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					intensity = (red + green + blue) / 3;
					intensity = intensity * 2;
					gy = gy + intensity;

					c = new Color(img.getRGB(x - 1, y + 1));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					intensity = (red + green + blue) / 3;
					gx = gx + intensity;
					intensity = intensity * (-1);
					gy = gy + intensity;

					c = new Color(img.getRGB(x, y + 1));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					intensity = (red + green + blue) / 3;
					intensity = intensity * 2;
					gx = gx + intensity;

					c = new Color(img.getRGB(x + 1, y + 1));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					intensity = (red + green + blue) / 3;
					gx = gx + intensity;
					gy = gy + intensity;

					gx = gx * gx;
					gy = gy * gy;
					intensity = (int) Math.sqrt(gx + gy);

					if (intensity > 255)
						intensity = 255;
					if (intensity < 0)
						intensity = 0;
					c = new Color(intensity, intensity, intensity);
					sobelOperatorImage.setRGB(x, y, c.getRGB());

				}
			}

		} catch (Exception e) {
			System.out.println("Exception occured in robertsOperator(): " + e.getMessage() + " " + intensity);
		}

		return sobelOperatorImage;
	}

	private static BufferedImage laplacianOperator(BufferedImage img) {
		int height = img.getHeight();
		int width = img.getWidth();
		BufferedImage laplacianOperatorImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int red = 0;
		int green = 0;
		int blue = 0;
		int sum = 0;
		try {

			for (int x = 1; x < width - 1; x++) {
				for (int y = 1; y < height - 1; y++) {
					Color c = new Color(img.getRGB(x, y));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					sum = 0;
					sum = (red + green + blue) / 3;

					sum *= (-4);

					c = new Color(img.getRGB(x - 1, y));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();

					sum += ((red + green + blue) / 3);

					c = new Color(img.getRGB(x + 1, y));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();

					sum += ((red + green + blue) / 3);

					c = new Color(img.getRGB(x, y - 1));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					sum += ((red + green + blue) / 3);

					c = new Color(img.getRGB(x, y + 1));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					sum += ((red + green + blue) / 3);

					if (sum > 255)
						sum = 255;
					if (sum < 0)
						sum = 0;

					c = new Color(sum, sum, sum);
					laplacianOperatorImage.setRGB(x, y, c.getRGB());

				}
			}

		} catch (Exception e) {
			System.out.println("Exception occured in laplacianOperator(): " + e.getMessage() + " " + sum);
		}

		return laplacianOperatorImage;
	}

	private static BufferedImage componentlabel(BufferedImage img) {
		int height = img.getHeight();
		int width = img.getWidth();
		BufferedImage componentlabelImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// int red = 0;
		// int green = 0;
		// int blue = 0;
		int intensityLeftPixel = 0;
		int intensityTopPixel = 0;
		int intensityCurrentPixel = 0;

		noOfPixels = height * width;
		char label[][] = new char[width][height];

		String equivalence[] = new String[10];
		int i = 0;
		try {
			int count = 0;
			for (int x = 0; x < width; x++) {
				boolean done = false;
				for (int y = 0; y < height /* && !done */; y++) {
					// if (y == 0)
					componentlabelImage.setRGB(x, y, Color.BLACK.getRGB());
					// else {
					// componentlabelImage.setRGB(x, y, Color.BLACK.getRGB());
					// done = true;
					// }
					label[x][y] = '?';
				}
			}

			// TO DEBUG
			for (int x = 0; x < height; x++) {
				for (int y = 0; y < width; y++) {
					Color c = new Color(img.getRGB(y, x));
					if (c.equals(Color.BLACK))
						System.out.print("0");
					else
						System.out.print("1");
				}
				System.out.println();
			}

			// END DEBUG HERE

			count = 66;

			boolean firstForegroundFound = false;

			int a = 0;

			for (int x = 1; x < width; x++) {
				for (int y = 1; y < height; y++) {
					Color p = new Color(img.getRGB(x, y));
					// red = p.getRed();
					// green = p.getGreen();
					// blue = p.getBlue();
					// intensityCurrentPixel = (red + green + blue) / 3;
					if (p.equals(Color.BLACK) && !firstForegroundFound) {
						a++;
						continue;
					} else if (p.equals(Color.WHITE) && !firstForegroundFound) {
						a++;
						firstForegroundFound = true;
					}

					// System.out.print(x+","+y);

					Color leftColor = new Color(img.getRGB(x - 1, y));
					// red = leftColor.getRed();
					// green = leftColor.getGreen();
					// blue = leftColor.getBlue();
					// intensityLeftPixel = (red + green + blue) / 3;

					Color topColor = new Color(img.getRGB(x, y - 1));
					/*
					 * red = topColor.getRed(); green = topColor.getGreen();
					 * blue = topColor.getBlue(); intensityTopPixel = (red +
					 * green + blue) / 3;
					 */
					// System.out.print(":" + count + "inj loop");

					if (leftColor.equals(Color.BLACK) && topColor.equals(Color.BLACK)) {
						// Assign new label to P
						label[x][y] = (char) count++;
						a++;
						// System.out.println(" Value a "+a);
						// count++;
						// componentlabelImage.setRGB(x, y,
						// Color.WHITE.getRGB());
					} else if (!leftColor.equals(Color.BLACK) && topColor.equals(Color.BLACK)) {
						// Assign Left to P
						label[x][y] = (char) (count - 1);
						// componentlabelImage.setRGB(x, y,new
						// Color(intensityLeftPixel, intensityLeftPixel,
						// intensityLeftPixel).getRGB());
					} else if (leftColor.equals(Color.BLACK) && !topColor.equals(Color.BLACK)) {
						// Assign Top to P
						label[x][y] = (char) (count - 1);
						// componentlabelImage.setRGB(x, y,new
						// Color(intensityTopPixel, intensityTopPixel,
						// intensityTopPixel).getRGB());
					} else {
						// Assign Left to P
						// IF left and top are different then they are
						// equivalent
						label[x][y] = (char) (count - 1);
						// componentlabelImage.setRGB(x, y,new
						// Color(intensityLeftPixel, intensityLeftPixel,
						// intensityLeftPixel).getRGB());
						// equivalence[i++]=
					}

				}
			}
			// System.out.println();
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					// if(Character.toString(label[x][y]).trim().length()>0)
					// System.out.print(Character.toString(label[x][y]).trim()+":");
					if (Character.toString(label[x][y]).trim().equals("A")) {
						// paint BLUE
						componentlabelImage.setRGB(x, y, Color.BLUE.getRGB());
					} else if (Character.toString(label[x][y]).trim().equals("B")) {
						// paint GREEN
						componentlabelImage.setRGB(x, y, Color.GREEN.getRGB());
					} else {
						// Color temp = new Color(img.getRGB(x, y));
						/*
						 * red = temp.getRed(); green = temp.getGreen(); blue =
						 * temp.getBlue(); int tempIntensity = (red + green +
						 * blue) / 3; if (tempIntensity > 10)
						 */
						componentlabelImage.setRGB(x, y, new Color(238, 64, 0).getRGB());
					}

				}
				// System.out.println();
			}

			// System.out.println("\nCount :" + count);

		} catch (Exception e) {
			System.out.println("Exception occured in componentlabel(): " + e.getMessage());
		}

		return componentlabelImage;

	}

	private static BufferedImage kirshOperator(BufferedImage img) {
		int height = img.getHeight();
		int width = img.getWidth();
		BufferedImage kirshOperatorImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int red = 0;
		int green = 0;
		int blue = 0;
		int intensity = 0;
		final int one = 1;
		final int two = 2;
		int gx = 0;
		int gy = 0;
		int dx = 0;
		int dy = 0;
		try {

			for (int x = 1; x < width - 1; x++) {
				for (int y = 1; y < height - 1; y++) {
					Color c = new Color(img.getRGB(x - 1, y - 1));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					intensity = (red + green + blue) / 3;

					dx = 0;
					dy = intensity;
					gx = intensity * (-1);
					gy = gx;

					c = new Color(img.getRGB(x, y - 1));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();

					intensity = (red + green + blue) / 3;
					dx = dx + intensity;
					dy = dy + intensity;

					intensity = intensity * (-1);
					gx = gx + intensity;

					c = new Color(img.getRGB(x + 1, y - 1));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();

					intensity = (red + green + blue) / 3;
					gy = gy + intensity;
					dx = dx + intensity;
					intensity = intensity * (-1);
					gx = gx + intensity;

					c = new Color(img.getRGB(x - 1, y));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					intensity = (red + green + blue) / 3;
					dy = dy + intensity;
					intensity = intensity * (-1);
					gy = gy + intensity;
					dx = dx + intensity;

					c = new Color(img.getRGB(x + 1, y));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					intensity = (red + green + blue) / 3;
					gy = gy + intensity;
					dx = dx + intensity;
					intensity = intensity * (-1);
					dy = dy + intensity;

					c = new Color(img.getRGB(x - 1, y + 1));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					intensity = (red + green + blue) / 3;
					gx = gx + intensity;
					intensity = intensity * (-1);
					gy = gy + intensity;
					dx = dx + intensity;

					c = new Color(img.getRGB(x, y + 1));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					intensity = (red + green + blue) / 3;

					gx = gx + intensity;
					intensity = intensity * (-1);
					dx = dx + intensity;
					dy = dy + intensity;

					c = new Color(img.getRGB(x + 1, y + 1));
					red = c.getRed();
					green = c.getGreen();
					blue = c.getBlue();
					intensity = (red + green + blue) / 3;
					gx = gx + intensity;
					gy = gy + intensity;
					intensity = intensity * (-1);
					dy = dy + intensity;

					gx = gx * gx;
					gy = gy * gy;
					dx = dx * dx;
					dy = dy * dy;
					intensity = (int) Math.sqrt(gx + gy + dx + dy);

					// intensity = findMax(gx,gy,dx,dy);

					// System.out.println(intensity);

					if (intensity > 255)
						intensity = 255;
					if (intensity < 0)
						intensity = 0;
					c = new Color(intensity, intensity, intensity);
					kirshOperatorImage.setRGB(x, y, c.getRGB());

				}
			}

		} catch (Exception e) {
			System.out.println("Exception occured in kirschOperator(): " + e.getMessage() + " " + intensity);
		}

		return kirshOperatorImage;
	}

	private static int findMax(int a, int b, int c, int d) {
		return Math.max(a, Math.max(b, Math.max(c, d)));
	}

	private static BufferedImage findComponents(BufferedImage img) {
		BufferedImage finalImage = null;

		// img = simpleThresholding(img, 100);
		int startLabel = 0, labelcount = 60, start = 60;
		int height = img.getHeight();
		int width = img.getWidth();
		System.out.println("H" + height + ":W" + width);

		BufferedImage componentlabelImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int y_cord = 0, x_cord = 0;
		int labels[][] = new int[width][height];
		int count = 0;
		int otherCount = 0;
		try {
			for (y_cord = 1; y_cord < height; y_cord++) {
				for (x_cord = 1; x_cord < width; x_cord++) {
					Color p = new Color(img.getRGB(x_cord, y_cord));
					if (p.equals(Color.WHITE)) {
						Color leftPixelColor = new Color(img.getRGB(x_cord - 1, y_cord));
						Color topPixelColor = new Color(img.getRGB(x_cord, y_cord - 1));
						int leftintensity = leftPixelColor.getRed();
						leftintensity = leftintensity + leftPixelColor.getBlue();
						leftintensity = leftintensity + leftPixelColor.getGreen();
						leftintensity = leftintensity / 3;
						// System.out.println("intensoty : "+leftintensity);

						if (!leftPixelColor.equals(Color.WHITE) && !topPixelColor.equals(Color.WHITE)) {
							startLabel++;
							// System.out.println("Exception here:
							// "+x_cord+":"+y_cord);
							labels[x_cord][y_cord] = startLabel;// System.out.println();
							// System.out.println("xcord
							// ycord"+x_cord+":"+y_cord);
							count++;
						} else if (!leftPixelColor.equals(Color.WHITE) && topPixelColor.equals(Color.WHITE)) {

							labels[x_cord][y_cord] = labels[x_cord][y_cord - 1];
							count++;
						} else if (leftPixelColor.equals(Color.WHITE) && !topPixelColor.equals(Color.WHITE)) {

							labels[x_cord][y_cord] = labels[x_cord - 1][y_cord];
							count++;
						} else if (leftPixelColor.equals(Color.WHITE) && topPixelColor.equals(Color.WHITE)) {
							labels[x_cord][y_cord] = labels[x_cord - 1][y_cord];
							count++;
							int oldValue = labels[x_cord][y_cord - 1];
							int newValue = labels[x_cord - 1][y_cord];
							if (oldValue != newValue) {
								for (int y = 1; y <= y_cord; y++) {
									for (int x = 1; x <= x_cord; x++) {
										if (labels[x][y] == oldValue) {
											otherCount++;
											labels[x][y] = newValue;
										}

									}
								}
							}
							/*
							 * for (int y = 1; y <= y_cord; y++) { for (int x =
							 * 1; x <= x_cord; x++) { if (labels[x][y] != 0 &&
							 * (labels[x_cord][y_cord - 1] == labels[x_cord -
							 * 1][y_cord])) { if (labels[x_cord][y_cord - 1] !=
							 * 0 && labels[x_cord - 1][y_cord] != 0) {
							 * labels[x][y] = labels[x_cord - 1][y_cord]; }
							 * 
							 * }
							 * 
							 * } }
							 */
						}

					}

				} // End of x_cord for loop
			} // End of x_cord for loop
				// System.out.println("bigger loops count
				// :"+otherCount+":"+startLabel);
			/*
			 * for (int temp_y = 1; temp_y < height; temp_y++) { for (int temp_x
			 * = 1; temp_x < width; temp_x++) { if (labels[temp_x][temp_y] != 0)
			 * System.out.print(labels[temp_x][temp_y] + " "); else
			 * System.out.print(" "); } System.out.println(); }
			 */

			int var = 0;
			int labelCount[] = new int[10000];
			for (int temp_y = 0; temp_y < height; temp_y++) {
				for (int temp_x = 0; temp_x < width; temp_x++) {
					// if (labels[temp_x][temp_y] != 0)
					// labelCount[labels[temp_x][temp_y]]++;
					// System.out.print(labels[temp_x][temp_y]);
					labelCount[labels[temp_x][temp_y]] = labelCount[labels[temp_x][temp_y]] + 1;
				}
			}

			for (int i = 1; i < 5000; i++) {
				if (labelCount[i] > start) {
					// System.out.print("\nLabel Value: " + i + " Area: " +
					// labelCount[i]);
					System.out.println(" Area: " + labelCount[i]);
					var++;
				}

			}
			System.out.println("\nTotal Components: " + var);

			/*
			 * for (int temp_y = 0; temp_y < width; temp_y++) { for (int temp_x
			 * = 0; temp_x < height; temp_x++) {
			 * System.out.print(labels[temp_y][temp_x]+":"); }
			 * System.out.println(); }
			 */

		} catch (Exception e)

		{
			System.out.println("SSAAAKAPAAKPAKPAKAPKAPAKAPK" + e.getMessage());
			System.out.println();
			e.printStackTrace();
		}

		return componentlabelImage;

	}

	private static void equivalence(int labels[][], int oldValue, int newValue, int x, int y) {
		for (int i = 1; i <= x; i++)
			for (int j = 1; j <= y; j++)
				if (labels[j][i] == oldValue)
					labels[j][i] = newValue;

	}

	public static void main(String[] args) throws IOException {
		ImageProcessingTester mainWindow = new ImageProcessingTester("Image Project");
		// boolean processed = false;

		while (true) {
			System.out.print("");

			ImageIcon icon = new ImageIcon();

			if (fileSelected) {
				String fileName = ImageProcessingTester.filepath;
				icon = new ImageIcon(fileName);

				mainWindow.inputImageLabel.setIcon(icon);
				mainWindow.inputImageLabel.setBounds(10, 0, 600, 600);

				if (goClicked) {
					String command = ImageProcessingTester.operation;
					// System.out.println(command);
					switch (command) {
					case "greyscale":
						boolean redEntered = (ImageProcessingTester.redGreyScaleTextField.getText().trim()
								.length() != 0);
						boolean greenEntered = (ImageProcessingTester.greenGreyScaleTextField.getText().trim()
								.length() != 0);
						boolean blueEntered = (ImageProcessingTester.blueGreyScaleTextField.getText().trim()
								.length() != 0);
						double red = 0.2, green = 0.4, blue = 0.4;
						if (redEntered)
							red = Double.parseDouble(ImageProcessingTester.redGreyScaleTextField.getText());
						if (greenEntered)
							green = Double.parseDouble(ImageProcessingTester.greenGreyScaleTextField.getText());
						if (blueEntered)
							blue = Double.parseDouble(ImageProcessingTester.blueGreyScaleTextField.getText());

						BufferedImage grescaledImage = greyScale(ImageIO.read(mainWindow.input), red, green, blue);
						mainWindow.outputImageLabel.setIcon(new ImageIcon(grescaledImage));
						mainWindow.outputImageLabel.setBounds(650, 0, 600, 600);
						File grescaledFile = new File(System.getProperty("user.dir") + File.separator + "output"
								+ File.separator + "grescaled.jpg");
						ImageIO.write(grescaledImage, "jpg", grescaledFile);
						goClicked = false;

						break;
					case "saltandpepper":
						boolean saltEntered = (ImageProcessingTester.redGreyScaleTextField.getText().trim()
								.length() != 0);
						boolean pepperEntered = (ImageProcessingTester.greenGreyScaleTextField.getText().trim()
								.length() != 0);
						int salt = 0, pepper = 0;
						if (saltEntered)
							salt = Integer.parseInt(ImageProcessingTester.redGreyScaleTextField.getText());
						if (pepperEntered)
							pepper = Integer.parseInt(ImageProcessingTester.greenGreyScaleTextField.getText());
						BufferedImage saltandpepperImage = saltnpepper(ImageIO.read(mainWindow.input), salt, pepper);
						mainWindow.outputImageLabel.setIcon(new ImageIcon(saltandpepperImage));
						mainWindow.outputImageLabel.setBounds(icon.getIconWidth() + 20, 0, 600, 600);
						File saltandpepperFile = new File(System.getProperty("user.dir") + File.separator + "output"
								+ File.separator + "saltnpeppered_" + salt + "_" + pepper);
						ImageIO.write(saltandpepperImage, "jpg", saltandpepperFile);
						// mainWindow.convert.doClick();
						goClicked = false;

						break;
					case "averagingsmoothing":
						boolean noOfNeighboursEntered = (ImageProcessingTester.redGreyScaleTextField.getText().trim()
								.length() != 0);
						int NS = 4;
						if (noOfNeighboursEntered)
							NS = Integer.parseInt(ImageProcessingTester.redGreyScaleTextField.getText());
						BufferedImage averagingsmoothedImage = averagingSmoothing(ImageIO.read(mainWindow.input), NS);
						mainWindow.outputImageLabel.setIcon(new ImageIcon(averagingsmoothedImage));
						mainWindow.outputImageLabel.setBounds(icon.getIconWidth() + 20, 0, 600, 600);
						File averagedsmoothedFile = new File(System.getProperty("user.dir") + File.separator + "output"
								+ File.separator + "aveintragingsmoothed_NS" + NS + ".jpg");
						ImageIO.write(averagingsmoothedImage, "jpg", averagedsmoothedFile);
						// mainWindow.convert.doClick();
						goClicked = false;
						// processed = false;
						break;
					case "mediansmoothing":

						noOfNeighboursEntered = (ImageProcessingTester.redGreyScaleTextField.getText().trim()
								.length() != 0);
						NS = 4;
						if (noOfNeighboursEntered)
							NS = Integer.parseInt(ImageProcessingTester.redGreyScaleTextField.getText());
						BufferedImage mediansmoothedImage = medianSmoothing(ImageIO.read(mainWindow.input), NS);
						mainWindow.outputImageLabel.setIcon(new ImageIcon(mediansmoothedImage));
						mainWindow.outputImageLabel.setBounds(icon.getIconWidth() + 20, 0, 600, 600);
						File mediansmoothedFile = new File(System.getProperty("user.dir") + File.separator + "output"
								+ File.separator + "mediansmoothed_NS" + NS + ".jpg");
						ImageIO.write(mediansmoothedImage, "jpg", mediansmoothedFile);
						// mainWindow.convert.doClick();
						goClicked = false;
						break;
					case "simplethreshold":
						boolean thresholdEntered = (ImageProcessingTester.redGreyScaleTextField.getText().trim()
								.length() != 0);
						int threshold = 125;
						if (thresholdEntered)
							threshold = Integer.parseInt(ImageProcessingTester.redGreyScaleTextField.getText());
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
						thresholdEntered = (ImageProcessingTester.redGreyScaleTextField.getText().trim().length() != 0);
						threshold = 50;
						if (thresholdEntered)
							threshold = Integer.parseInt(ImageProcessingTester.redGreyScaleTextField.getText());
						BufferedImage percentileThresholdedImage = percentileThresholding(
								ImageIO.read(mainWindow.input), threshold);
						mainWindow.outputImageLabel.setIcon(new ImageIcon(percentileThresholdedImage));
						mainWindow.outputImageLabel.setBounds(icon.getIconWidth() + 20, 0, 600, 600);
						File percentileThresholdedFile = new File(System.getProperty("user.dir") + File.separator
								+ "output" + File.separator + "percentileThresholded_threshold" + threshold + ".jpg");
						ImageIO.write(percentileThresholdedImage, "jpg", percentileThresholdedFile);
						// mainWindow.convert.doClick();
						goClicked = false;
						break;
					case "iterativethreshold":
						thresholdEntered = (ImageProcessingTester.redGreyScaleTextField.getText().trim().length() != 0);
						threshold = 125;
						if (thresholdEntered)
							threshold = Integer.parseInt(ImageProcessingTester.redGreyScaleTextField.getText());
						BufferedImage iterativeThresholdedImage = iterativeThresholding(ImageIO.read(mainWindow.input),
								threshold);
						mainWindow.outputImageLabel.setIcon(new ImageIcon(iterativeThresholdedImage));
						mainWindow.outputImageLabel.setBounds(icon.getIconWidth() + 20, 0, 600, 600);
						File iterativeThresholdedFile = new File(System.getProperty("user.dir") + File.separator
								+ "output" + File.separator + "iterativeThresholded_threshold" + threshold + ".jpg");
						ImageIO.write(iterativeThresholdedImage, "jpg", iterativeThresholdedFile);
						// mainWindow.convert.doClick();
						goClicked = false;
						break;
					case "contrastenhancement":
						BufferedImage contrastEnhancedImage = enhanceContrast(ImageIO.read(mainWindow.input));
						mainWindow.outputImageLabel.setIcon(new ImageIcon(contrastEnhancedImage));
						mainWindow.outputImageLabel.setBounds(icon.getIconWidth() + 20, 0, 600, 600);
						File contrastEnhancedFile = new File(System.getProperty("user.dir") + File.separator + "output"
								+ File.separator + "contrastEnhanced.jpg");
						ImageIO.write(contrastEnhancedImage, "jpg", contrastEnhancedFile);
						// mainWindow.convert.doClick();
						goClicked = false;
						break;

					case "robertsoperator":
						BufferedImage robertsOperatorImage = robertsOperator(ImageIO.read(mainWindow.input));
						mainWindow.outputImageLabel.setIcon(new ImageIcon(robertsOperatorImage));
						mainWindow.outputImageLabel.setBounds(icon.getIconWidth() + 20, 0, 600, 600);
						File robertsOperatorFile = new File(System.getProperty("user.dir") + File.separator + "output"
								+ File.separator + "robertsOperator.jpg");
						ImageIO.write(robertsOperatorImage, "jpg", robertsOperatorFile);
						goClicked = false;
						break;
					case "sobeloperator":
						BufferedImage sobelOperatorImage = sobelOperator(ImageIO.read(mainWindow.input));
						mainWindow.outputImageLabel.setIcon(new ImageIcon(sobelOperatorImage));
						mainWindow.outputImageLabel.setBounds(icon.getIconWidth() + 20, 0, 600, 600);
						File sobelOperatorFile = new File(System.getProperty("user.dir") + File.separator + "output"
								+ File.separator + "sobelOperator.jpg");
						ImageIO.write(sobelOperatorImage, "jpg", sobelOperatorFile);
						goClicked = false;
						break;
					case "kirshoperator":
						BufferedImage kirschOperatorImage = kirshOperator(ImageIO.read(mainWindow.input));
						mainWindow.outputImageLabel.setIcon(new ImageIcon(kirschOperatorImage));
						mainWindow.outputImageLabel.setBounds(icon.getIconWidth() + 20, 0, 600, 600);
						File kirschOperatorFile = new File(System.getProperty("user.dir") + File.separator + "output"
								+ File.separator + "kirschOperator.jpg");
						ImageIO.write(kirschOperatorImage, "jpg", kirschOperatorFile);
						// System.out.println("robertsOperatorImage finished");
						goClicked = false;
						break;
					case "laplacianoperator":
						BufferedImage laplacianOperatorImage = laplacianOperator(ImageIO.read(mainWindow.input));
						mainWindow.outputImageLabel.setIcon(new ImageIcon(laplacianOperatorImage));
						mainWindow.outputImageLabel.setBounds(icon.getIconWidth() + 20, 0, 600, 600);
						File laplacianOperatorFile = new File(System.getProperty("user.dir") + File.separator + "output"
								+ File.separator + "laplacianOperator.jpg");
						ImageIO.write(laplacianOperatorImage, "jpg", laplacianOperatorFile);
						// System.out.println("robertsOperatorImage finished");
						goClicked = false;
						break;

					case "componentlabel":
						BufferedImage componentlabelImage = findComponents(ImageIO.read(mainWindow.input));

						String bg = System.getProperty("user.dir") + File.separator + "bg.jpg";
						ImageIcon t = new ImageIcon(bg);
						mainWindow.outputImageLabel.setIcon(t);
						mainWindow.outputImageLabel.setBounds(icon.getIconWidth() + 20, 0, 600, 600);
						File componentlabelFile = new File(System.getProperty("user.dir") + File.separator + "output"
								+ File.separator + "componentlabel.jpg");
						ImageIO.write(componentlabelImage, "jpg", componentlabelFile);
						// System.out.println("robertsOperatorImage finished");
						goClicked = false;
						break;

					case "constructpyramid":

						int noofleveles = 6;
						boolean nooflevelsentered = (ImageProcessingTester.redGreyScaleTextField.getText().trim()
								.length() != 0);
						if (nooflevelsentered)
							noofleveles = Integer.parseInt(ImageProcessingTester.redGreyScaleTextField.getText());
						/*
						 * BufferedImage simpleThresholdedImage =
						 * simpleThresholding(ImageIO.read(mainWindow.input),
						 * threshold);
						 */

						constructPyramid(ImageIO.read(mainWindow.input), 6);
						String bg1 = System.getProperty("user.dir") + File.separator + "bg.jpg";
						ImageIcon t1 = new ImageIcon(bg1);
						mainWindow.outputImageLabel.setIcon(t1);
						mainWindow.outputImageLabel.setBounds(icon.getIconWidth() + 20, 0, 600, 600);
						/*
						 * File simpleThresholdedFile = new
						 * File(System.getProperty("user.dir") + File.separator
						 * + "output" + File.separator +
						 * "simpleThresholded_threshold" + threshold + ".jpg");
						 * ImageIO.write(simpleThresholdedImage, "jpg",
						 * simpleThresholdedFile);
						 */
						goClicked = false;

						break;

					case "zeroorderexpansion":
						BufferedImage zeroOrderExpansionImage = zeroOrderExpansion(ImageIO.read(mainWindow.input));
						mainWindow.outputImageLabel.setIcon(new ImageIcon(zeroOrderExpansionImage));
						mainWindow.outputImageLabel.setBounds(icon.getIconWidth() + 20, 0, 600, 600);
						File zeroOrderExpansionFile = new File(System.getProperty("user.dir") + File.separator
								+ "output" + File.separator + "zeroOrderExpansion_" + NUMBER + ".jpg");
						ImageIO.write(zeroOrderExpansionImage, "jpg", zeroOrderExpansionFile);
						goClicked = false;
						NUMBER++;
						break;
					case "firstorderexpansion":
						BufferedImage firstOrderExpansionImage = firstOrderExpansion(ImageIO.read(mainWindow.input));
						mainWindow.outputImageLabel.setIcon(new ImageIcon(firstOrderExpansionImage));
						mainWindow.outputImageLabel.setBounds(icon.getIconWidth() + 20, 0, 600, 600);
						File firstOrderExpansionFile = new File(System.getProperty("user.dir") + File.separator
								+ "output" + File.separator + "firstOrderExpansion_" + NUMBER + ".jpg");
						ImageIO.write(firstOrderExpansionImage, "jpg", firstOrderExpansionFile);
						goClicked = false;
						NUMBER++;
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
		int result = fileChooser.showOpenDialog(ImageProcessingTester.staticWindow);
		if (result == JFileChooser.APPROVE_OPTION) {
			ImageProcessingTester.input = fileChooser.getSelectedFile();
			ImageProcessingTester.fileSelected = true;
			ImageProcessingTester.goClicked = false;
			ImageProcessingTester.filepath = fileChooser.getCurrentDirectory().getAbsolutePath() + File.separator
					+ fileChooser.getSelectedFile().getName();
			ImageProcessingTester.redGreyScaleTextField.setText("");
			ImageProcessingTester.greenGreyScaleTextField.setText("");
			ImageProcessingTester.blueGreyScaleTextField.setText("");
			ImageProcessingTester.redGreyScaleTextField.setVisible(false);
			ImageProcessingTester.greenGreyScaleTextField.setVisible(false);
			ImageProcessingTester.blueGreyScaleTextField.setVisible(false);

		}

	}

}

class GoButtonAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		ImageProcessingTester.goClicked = true;

	}

}

class GreyscaleAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		ImageProcessingTester.operation = "greyscale";
		ImageProcessingTester.redGreyScaleTextField.setVisible(true);
		ImageProcessingTester.greenGreyScaleTextField.setVisible(true);
		ImageProcessingTester.blueGreyScaleTextField.setVisible(true);

		ImageProcessingTester.redGreyScaleTextField.setToolTipText("Enter Red here..");
		ImageProcessingTester.greenGreyScaleTextField.setToolTipText("Enter Green here..");
		ImageProcessingTester.blueGreyScaleTextField.setToolTipText("Enter Blue here..");
	}

}

class SaltAndPepperAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		ImageProcessingTester.operation = "saltandpepper";
		ImageProcessingTester.redGreyScaleTextField.setVisible(true);
		ImageProcessingTester.redGreyScaleTextField.setToolTipText("Enter Salt here..");
		ImageProcessingTester.greenGreyScaleTextField.setVisible(true);
		ImageProcessingTester.greenGreyScaleTextField.setToolTipText("Enter Pepper here..");
		ImageProcessingTester.blueGreyScaleTextField.setVisible(false);
	}

}

class AveragingSmoothingAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		ImageProcessingTester.operation = "averagingsmoothing";
		ImageProcessingTester.redGreyScaleTextField.setToolTipText("Enter value for NS here..");
		ImageProcessingTester.redGreyScaleTextField.setVisible(true);
		ImageProcessingTester.greenGreyScaleTextField.setVisible(false);
		ImageProcessingTester.blueGreyScaleTextField.setVisible(false);
	}

}

class MedianSmoothingAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		ImageProcessingTester.operation = "mediansmoothing";
		ImageProcessingTester.redGreyScaleTextField.setVisible(true);
		ImageProcessingTester.redGreyScaleTextField.setToolTipText("Enter value for NS here..");
		ImageProcessingTester.greenGreyScaleTextField.setVisible(false);
		ImageProcessingTester.blueGreyScaleTextField.setVisible(false);
	}

}

class SimpleThresholdAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		ImageProcessingTester.operation = "simplethreshold";
		ImageProcessingTester.redGreyScaleTextField.setVisible(true);
		ImageProcessingTester.redGreyScaleTextField.setToolTipText("Enter Threshold here..");
		ImageProcessingTester.greenGreyScaleTextField.setVisible(false);
		ImageProcessingTester.blueGreyScaleTextField.setVisible(false);
	}

}

class PercentileThresholdAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		ImageProcessingTester.operation = "percentilethreshold";
		ImageProcessingTester.redGreyScaleTextField.setVisible(true);
		ImageProcessingTester.redGreyScaleTextField.setText("Enter Threshold  here..");
		ImageProcessingTester.greenGreyScaleTextField.setVisible(false);
		ImageProcessingTester.blueGreyScaleTextField.setVisible(false);
	}

}

class IterativedThresholdAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		ImageProcessingTester.operation = "iterativethreshold";
		ImageProcessingTester.redGreyScaleTextField.setVisible(true);
		ImageProcessingTester.redGreyScaleTextField.setToolTipText("Enter Threshold here..");
		ImageProcessingTester.greenGreyScaleTextField.setVisible(false);
		ImageProcessingTester.blueGreyScaleTextField.setVisible(false);
	}

}

class ContrastEnhancemntAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		ImageProcessingTester.operation = "contrastenhancement";
		ImageProcessingTester.redGreyScaleTextField.setVisible(false);
		ImageProcessingTester.greenGreyScaleTextField.setVisible(false);
		ImageProcessingTester.blueGreyScaleTextField.setVisible(false);
	}

}

class RobertsOperatorAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		ImageProcessingTester.operation = "robertsoperator";
		ImageProcessingTester.redGreyScaleTextField.setVisible(false);
		ImageProcessingTester.greenGreyScaleTextField.setVisible(false);
		ImageProcessingTester.blueGreyScaleTextField.setVisible(false);
	}
}

class SobelOperatorAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		ImageProcessingTester.operation = "sobeloperator";
		ImageProcessingTester.redGreyScaleTextField.setVisible(false);
		ImageProcessingTester.greenGreyScaleTextField.setVisible(false);
		ImageProcessingTester.blueGreyScaleTextField.setVisible(false);
	}
}

class KirshOperatorAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		ImageProcessingTester.operation = "kirshoperator";
		ImageProcessingTester.redGreyScaleTextField.setVisible(false);
		ImageProcessingTester.greenGreyScaleTextField.setVisible(false);
		ImageProcessingTester.blueGreyScaleTextField.setVisible(false);
	}
}

class LaplacianActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		ImageProcessingTester.operation = "laplacianoperator";
		ImageProcessingTester.redGreyScaleTextField.setVisible(false);
		ImageProcessingTester.greenGreyScaleTextField.setVisible(false);
		ImageProcessingTester.blueGreyScaleTextField.setVisible(false);
	}
}

class ComponentLabelActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		ImageProcessingTester.operation = "componentlabel";
		ImageProcessingTester.redGreyScaleTextField.setVisible(false);
		ImageProcessingTester.greenGreyScaleTextField.setVisible(false);
		ImageProcessingTester.blueGreyScaleTextField.setVisible(false);
	}
}

class ConstructPyramidActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		ImageProcessingTester.operation = "constructpyramid";
		ImageProcessingTester.redGreyScaleTextField.setVisible(false);
		// ImageProcessingTester.redGreyScaleTextField.setText("Enter No. of
		// levels desired here..");
		ImageProcessingTester.greenGreyScaleTextField.setVisible(false);
		ImageProcessingTester.blueGreyScaleTextField.setVisible(false);
	}

}

class ZeroOrderExpansionActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		ImageProcessingTester.operation = "zeroorderexpansion";
		ImageProcessingTester.redGreyScaleTextField.setVisible(false);
		ImageProcessingTester.greenGreyScaleTextField.setVisible(false);
		ImageProcessingTester.blueGreyScaleTextField.setVisible(false);
	}
}

class FirstOrderExpansionActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		ImageProcessingTester.operation = "firstorderexpansion";
		ImageProcessingTester.redGreyScaleTextField.setVisible(false);
		ImageProcessingTester.greenGreyScaleTextField.setVisible(false);
		ImageProcessingTester.blueGreyScaleTextField.setVisible(false);
	}
}

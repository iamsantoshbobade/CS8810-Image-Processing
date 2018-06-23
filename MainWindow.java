package code;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MainWindow extends JFrame {

	private static int WIDTH = 1200;
	private static int HEIGHT = 1200;
	
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

	private JPanel buttonPanel;
	private JButton convert;

	// private JPanel imagePanel;
	private ImageIcon inputImage;
	// private ImageIcon outputImage;
	// private static Image img;

	private JLabel inputImageLabel;
	private JLabel outputImageLabel;

	public static File input = null;
	public static File output = null;
	public static String filePath = System.getProperty("user.dir");
	public static String fileName = "";
	public static MainWindow staticWindow = null;
	public static boolean fileSelected = false;
	public static boolean goClicked = false;
	public static String operation = "";

	private static int count = 0;

	public MainWindow(String title) {
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

		// imagePanel = new JPanel();
		inputImageLabel = new JLabel("");
		outputImageLabel = new JLabel("");
		// inputImageLabel.setBounds(0, 100, 150, 150);
		// inputImageLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		// inputImageLabel.setBackground(Color.RED);

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
		;
		ptileThreshold.setMnemonic('P');
		iterativeThreshold.setMnemonic('I');

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

		// mainMenuBar.add(utilitiesMenu);

		openImage.addActionListener(new OpenImageAction());
		exit.addActionListener(new ExitProjectAction());

		saltNpepper.addActionListener(new SaltAndPepperAction());
		someOtherNoise.addActionListener(new SaltAndPepperAction());// STILL
																	// THINKING

		averaging.addActionListener(new AveragingSmoothingAction());
		median.addActionListener(new MedianSmoothingAction());

		simpleThreshold.addActionListener(new SimpleThresholdAction());
		ptileThreshold.addActionListener(new PercentileThresholdAction());
		iterativeThreshold.addActionListener(new IterativedThresholdAction());

		buttonPanel = new JPanel();
		buttonPanel.setBounds(550, 550, 200, 200);

		convert = new JButton("Go!");
		convert.setBounds(600, 250, 20, 20);
		// convert.setSize(150, 50);
		convert.setEnabled(true);
		convert.setToolTipText("Press to start processing the image.");
		convert.addActionListener(new GoButtonAction());
		// buttonPanel.add(BorderLayout.CENTER,convert);
		add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.add(BorderLayout.CENTER, convert);

		setSize(WIDTH, HEIGHT);
		setVisible(true);
		setLocationRelativeTo(null);
		setEnabled(true);
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		staticWindow = this;
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
			//System.out.println("White:"+newColor.getRGB());
			saltnpepperedImage.setRGB(x, y, newColor.getRGB());
			System.out.println(saltnpepperedImage.getRGB(x, y));
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
			//System.out.println("count: " + count + "\t abs value: " + Math.abs(oldThreshold - newThreshold));
			if (Math.abs(oldThreshold - newThreshold) < eps)
				break;
			oldThreshold = newThreshold;

		} while (true);

		//System.out.println("do....while count: " + count);
		return finalThresholdImage;
	}

	



	public static void main(String[] args) throws IOException {
		MainWindow mainWindow = new MainWindow("Image Project");

		while (true) {
			System.out.print("");
			
			ImageIcon icon = new ImageIcon();

			if (fileSelected) {
				
				//icon = 

				mainWindow.inputImageLabel.setIcon(new ImageIcon(mainWindow.input.getName()));
				mainWindow.inputImageLabel.setBounds(0, 0, 600, 600);

				if (goClicked) {
					String command = MainWindow.operation;
					switch (command) {
					case "greyscale":
						break;
					case "saltandpepper":
						break;
					case "averagingsmoothing":
						break;
					case "mediansmoothing":
						break;
					case "simplethreshold":
						break;
					case "percentilethreshold":
						break;
					case "iterativethreshold":
						break;
					default:

					}
				}

				mainWindow.outputImageLabel.setIcon(new ImageIcon("sample.jpg"));
				mainWindow.outputImageLabel.setBounds(500, 0, 600, 600);

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
		int result = fileChooser.showOpenDialog(MainWindow.staticWindow);
		if (result == JFileChooser.APPROVE_OPTION) {
			MainWindow.input = fileChooser.getSelectedFile();
			MainWindow.fileSelected = true;
		}

	}

}

class GoButtonAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		MainWindow.goClicked = true;

	}

}

class GreyscaleAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		MainWindow.operation = "greyscale";
	}

}

class SaltAndPepperAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		MainWindow.operation = "saltandpepper";
	}

}

class AveragingSmoothingAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		MainWindow.operation = "averagingsmoothing";
	}

}

class MedianSmoothingAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		MainWindow.operation = "mediansmoothing";
	}

}

class SimpleThresholdAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		MainWindow.operation = "simplethreshold";
	}

}

class PercentileThresholdAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		MainWindow.operation = "percentilethreshold";
	}

}

class IterativedThresholdAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		MainWindow.operation = "iterativethreshold";
	}

}
package code;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Steganography extends JFrame {

	private static int WIDTH = 2000;
	private static int HEIGHT = 1800;

	static HashMap<String, int[][]> colorMap = new HashMap<String, int[][]>();

	private JMenuBar mainMenuBar;
	private JMenu fileMenu;

	private JMenuItem encode;
	private JMenuItem decode;

	private JPanel buttonPanel;
	private JButton go;

	public static File input = null;
	public static File output = null;

	public static String filePath = System.getProperty("user.dir");
	public static String fileName = "";
	public static boolean fileSelected = false;
	public static boolean goClicked = false;
	public static Steganography staticWindow = null;
	public static String inputImagePath = "";
	public static String inputTextPath = "";
	public static String operation = "";
	public static String temptext = "";
	public static boolean encrypt = false;

	public Steganography() {
		super("Steg Project");
		mainMenuBar = new JMenuBar();
		fileMenu = new JMenu("Options");
		encode = new JMenuItem("Encode");
		decode = new JMenuItem("Decode");

		setJMenuBar(mainMenuBar);
		mainMenuBar.add(fileMenu);
		fileMenu.add(encode);
		fileMenu.add(decode);

		encode.addActionListener(new EncodeAction());
		decode.addActionListener(new DecodeAction());

		buttonPanel = new JPanel();
		buttonPanel.setBounds(550, 550, 200, 200);

		go = new JButton("Go!");
		go.setBounds(5, 5, 20, 20);
		go.setEnabled(true);
		go.setToolTipText("Press to start encoding/decoding the image.");
		go.addActionListener(new GoButtonAction());

		add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.add(go);

		setSize(WIDTH, HEIGHT);
		setVisible(true);
		setLocationRelativeTo(null);
		setEnabled(true);
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);

		staticWindow = this;
	}

	public static void encode(String data) throws IOException {
		System.out.println("Ecndoe starteds");

		String binary = "";
		String temp_bin = "";
		for (int i = 0; i < data.length(); i++) {
			int x = data.charAt(i);
			String temp = dec2binary(x);
			binary = binary + temp;
			temp_bin = binary;
			
			if(encrypt && i%5==0){
				//insert junk char here
				x = 97 + (int)(Math.random() * 122);
				temp = dec2binary(x);
				binary = binary + temp;
				temp_bin = binary;
			}
		}

		// retrieve individual color pixels from hashmap
		int[][] red = colorMap.get("Red");
		int[][] blue = colorMap.get("Blue");
		int[][] green = colorMap.get("Green");
		int flag = 0;
		for (int i = 0; i < red.length && temp_bin.length() > 0; i++) {
			for (int j = 0; j < red[0].length && temp_bin.length() > 0; j++) {
				int r = red[i][j];
				int g = green[i][j];
				int b = blue[i][j];
				// convert individual pixels to binary
				String bin_r = dec2binary(r);
				String bin_g = dec2binary(g);
				String bin_b = dec2binary(b);
				// we now have the three in binary String
				bin_r = bin_r.substring(0, 6) + temp_bin.substring(0, 2);
				if (temp_bin.length() != 2) {
					temp_bin = temp_bin.substring(2);
					bin_g = bin_g.substring(0, 6) + temp_bin.substring(0, 2);
					if (temp_bin.length() != 2) {
						temp_bin = temp_bin.substring(2);
						bin_b = bin_g.substring(0, 6) + temp_bin.substring(0, 2);
						if (temp_bin.length() != 2) {
							temp_bin = temp_bin.substring(2);
						}
					}
				}

				r = Integer.parseInt(bin_r, 2);
				g = Integer.parseInt(bin_g, 2);
				b = Integer.parseInt(bin_b, 2);

				red[i][j] = r;
				blue[i][j] = b;
				green[i][j] = g;

			}

		}
		colorMap.remove("Red");
		colorMap.remove("Green");
		colorMap.remove("Blue");
		colorMap.put("Red", red);
		colorMap.put("Green", green);
		colorMap.put("Blue", blue);
	}

	public static String decode(int len)	{
		
		System.out.println("decode started");

		String decoded = "";
		String bin_decoded = "";
		int[][] red = colorMap.get("Red");
		int[][] blue = colorMap.get("Blue");
		int[][] green = colorMap.get("Green");
		int flag = 0;
		for (int i = 0; i < red.length; i++)
		{
			for (int j = 0; j < red[0].length; j++)
			{
				int r = red[i][j];
				int g = green[i][j];
				int b = blue[i][j];
				String bin_r = dec2binary(r);
				String bin_g = dec2binary(g);
				String bin_b = dec2binary(b);
				String x = bin_r.substring(bin_r.length() - 2);
				String y = bin_g.substring(bin_g.length() - 2);
				String z = bin_b.substring(bin_b.length() - 2);
				bin_decoded = bin_decoded + (x + y + z);
				if ((bin_decoded.length()) >= len * 8)
				{
					flag = 1;
					break;
				}
			}
			if (flag == 1)
				break;
		}

		for (int i = 0; i >= 0; i++){
			String temp = bin_decoded.substring(0, 8);
			int val = Integer.parseInt(temp, 2);
			decoded = decoded + Character.toString((char) (val));
			if (decoded.length() != len)
				bin_decoded = bin_decoded.substring(8);
			else
				break;
		}

		System.out.println("Before decode returns "+decoded);
		return decoded;
	}

	public static void getpixels(BufferedImage img) {

		int width = img.getWidth();
		int height = img.getHeight();
		int[][] blue_pixels = new int[height][width];
		int[][] red_pixels = new int[height][width];
		int[][] green_pixels = new int[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				
				Color colr = new Color(img.getRGB(j, i));
				int b = (colr.getBlue());
				int g = (int) (colr.getGreen());
				int r = (int) (colr.getRed());
				blue_pixels[i][j] = b;
				red_pixels[i][j] = r;
				green_pixels[i][j] = g;
			}

		}

		colorMap.put("Red", red_pixels);
		colorMap.put("Green", green_pixels);
		colorMap.put("Blue", blue_pixels);
	}

	public static void display(BufferedImage image) throws IOException {

		BufferedImage pix_image = new BufferedImage(image.getWidth(), image.getHeight(),BufferedImage.TYPE_INT_RGB);
		int width = image.getWidth();
		int height = image.getHeight();
		int[][] blue_pixels = colorMap.get("Blue");
		int[][] red_pixels = colorMap.get("Red");
		int[][] green_pixels = colorMap.get("Green");
		for (int i = 0; i < blue_pixels.length; i++){
			for (int j = 0; j < blue_pixels[0].length; j++){
				Color newcolr = new Color(red_pixels[i][j], green_pixels[i][j], blue_pixels[i][j]);
				pix_image.setRGB(j, i, newcolr.getRGB());
			}
		}

		File output = new File(System.getProperty("user.dir")+File.separator+"output"+File.separator+input.getName() + "_" + new Random().nextInt(100));
		ImageIO.write(pix_image, "jpg", output);

	}

	public static String dec2binary(int num) {

		String bin = "";
		for (int i = 0; num != 0; i++) {
			int temp = num % 2;
			bin = String.valueOf(temp) + bin;
			num = num / 2;
		}

		if (bin.length() < 8) { // Pad the leading zeros
			int n = 8 - bin.length();
			for (int j = 0; j < n; j++)
				bin = "0" + bin;
		}

		return bin;
	}

	public static void main(String[] args) {
		Steganography ui = new Steganography();
		BufferedReader reader = null;
		
		BufferedImage im = null;
		try {
			while (true) {
				System.out.print("");
				if (fileSelected) {

					if (goClicked) {

						String imageFilename = Steganography.inputImagePath;

						switch (operation) {
						case "encode":
							File f = new File(inputImagePath);
							im = ImageIO.read(input);
							getpixels(im);

							String textFilename = Steganography.inputTextPath;
							String secretMessage = "";
							Path paths = Paths.get(textFilename);
							reader = Files.newBufferedReader(paths, StandardCharsets.UTF_8);
							String line = null;
							while ((line = reader.readLine()) != null) {
								secretMessage += line;
							}
							//System.out.println("Encoding this:" + secretMessage);
							encode(secretMessage);
							temptext = secretMessage;
							fileSelected = false;
							goClicked = false;
							display(im);
							break;
						case "decode":
							System.out.println("decode case");
							String result = decode(temptext.length());
							File file = new File(System.getProperty("user.dir") + File.separator + "output"
									+ File.separator + "extractedText.txt");
							FileWriter fileWriter = new FileWriter(file);
							
							fileWriter.write(result);
							fileWriter.flush();
							fileWriter.close();
							fileSelected = false;
							goClicked = false;
							break;
						default:
							System.out.println("This doesnt make any sense!!" + operation);
						}

					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

class DecodeAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
		fileChooser.setDialogTitle("Select Stego Image");
		int result = fileChooser.showOpenDialog(Steganography.staticWindow);
		if (result == JFileChooser.APPROVE_OPTION) {
			Steganography.input = fileChooser.getSelectedFile();
			Steganography.inputImagePath = fileChooser.getCurrentDirectory().getAbsolutePath() + File.separator
					+ fileChooser.getSelectedFile().getName();

			Steganography.fileSelected = true;
			Steganography.operation = "decode";

		}

	}

}

class EncodeAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
		fileChooser.setDialogTitle("Select Cover Image");
		int result = fileChooser.showOpenDialog(Steganography.staticWindow);
		if (result == JFileChooser.APPROVE_OPTION) {
			Steganography.input = fileChooser.getSelectedFile();
			Steganography.inputImagePath = fileChooser.getCurrentDirectory().getAbsolutePath() + File.separator
					+ fileChooser.getSelectedFile().getName();
			Steganography.goClicked = false;
		}
		fileChooser = new JFileChooser(System.getProperty("user.dir"));
		fileChooser.setDialogTitle("Select Secret Text File");
		result = fileChooser.showOpenDialog(Steganography.staticWindow);
		if (result == JFileChooser.APPROVE_OPTION) {
			Steganography.fileSelected = true;
			Steganography.operation = "encode";
			Steganography.inputTextPath = fileChooser.getCurrentDirectory().getAbsolutePath() + File.separator
					+ fileChooser.getSelectedFile().getName();

		}
	}

}

class GoButtonAction implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Steganography.goClicked = true;
	}

}
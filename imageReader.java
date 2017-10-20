/*
 * CS576 - Multimedia System Design:  HW 1
 * This is program for Resampling and Filtering : Upsampling and down sampling a image
 *
 * � In the down sample case,
 * Image is downsampled from 4000 x 3000 to (1920 x 1080), (1280 x 720) and (640 x 480) using 2 algorithms:
 * 1. Specific sampling where you choose a specific pixel
 * 2. Gaussian smoothing where you choose the average of a set of samples
 *
 * � In the up sample case,
 * Image is upsampled from 400 x 300 to (1920 x 1080), (1280 x 720) and (640 x 480) using 2 algorithms:
 * 1. Nearest neighbor to choose your up sampled pixel
 * 2. Bilinear interpolation
 *
 * input Parameters: input file name,input image height, input image width, Resampling method, and output size of the image
 * 			Ex : java imageReader <input_image_name> <input_image_width> <input_image_height> <resampling_method> <output_size_of_the_image>
 * 				 resampling_method options are 1 /2
 * 				 output_size_of_the_image options are O1 / O2/ O3
 *
 * output: Program will produce 2 outputs
 * 			a. original image
 * 			b. scaled image
 *
 * to compile : javac imageReader.java
 * to run 	  : java imageReader hw_1_high_res 4000 3000 1 O1
 *
 * Author: Monika Devanga Ravi
 * Date: 09/08/2017
 *
 */

import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.math.*;
import java.util.*;

// Read images in RGB interleaved format

public class imageReader {

	public static void main(String[] args) {

		String fileName = args[0];
		int width = Integer.parseInt(args[1]);
		int height = Integer.parseInt(args[2]);
		int resampling_method = Integer.parseInt(args[3]);
		String output_format = args[4];
		// height and width of the image according to the given output_format.
		int O1_width = 1920;
		int O1_height = 1080;
		int O2_width = 1280;
		int O2_height = 720;
		int O3_width = 640;
		int O3_height = 480;

		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		BufferedImage img1 = null;

		File file = null;
		byte[] bytes = null;

		try {
			file = new File(args[0]);
			InputStream is = new FileInputStream(file);

			long len = file.length();
			bytes = new byte[(int) (len)];

			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}

			int ind = 0;
			for (int y = 0; y < height; y++) {

				for (int x = 0; x < width; x++) {

					byte a = 0;
					byte r = bytes[ind];
					byte g = bytes[ind + height * width];
					byte b = bytes[ind + height * width * 2];

					int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
					// int pix = ((a << 24) + (r << 16) + (g << 8) + b);
					img.setRGB(x, y, pix);
					ind++;
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// if width of the image is equal to 4000 use down sample algorithms based on resampling_method
		if (width == 4000) {
			if (resampling_method == 1) { // resampling method 1 implies Specific sampling
				System.out.println("Downsampling a image using specific sampling algorithm.");
				if (output_format.equals("O1")||output_format.equals("o1")) {
					img1 = specific(width, height, img, O1_width, O1_height, bytes,fileName);
				}
				if (output_format.equals("O2")||output_format.equals("o2")) {
					img1 = specific(width, height, img, O2_width, O2_height, bytes,fileName);
				}
				if (output_format.equals("O3")||output_format.equals("o3")) {
					img1 = specific(width, height, img, O3_width, O3_height, bytes,fileName);
				}
			}

			if (resampling_method == 2) { // resampling method 2 implies Gaussian smoothing
				System.out.println("Down sampling a image using Gaussian smoothing by averaging a set of samples");
				if (output_format.equals("O1")||output_format.equals("o1")) {
					img1 = gaussian(width, height, img, O1_width, O1_height, bytes,fileName);
				}
				if (output_format.equals("O2")||output_format.equals("o2")) {
					img1 = gaussian(width, height, img, O2_width, O2_height, bytes,fileName);
				}
				if (output_format.equals("O3")||output_format.equals("o3")) {
					img1 = gaussian(width, height, img, O3_width, O3_height, bytes,fileName);
				}
			}
		}

		// if the width of the image is 400 use upsample algorithms based on resampling_method
		if (width == 400) {
			if (resampling_method == 1) { // Nearest neighbour algorithm
				System.out.println("Upsampling a image using Nearest neighbour algorithm.");

				if (output_format.equals("O1")||output_format.equals("o1")) {
					img1 = nearest(width, height, img, O1_width, O1_height, bytes,fileName);
				}
				if (output_format.equals("O2")||output_format.equals("o2")) {
					img1 = nearest(width, height, img, O2_width, O2_height, bytes,fileName);
				}
				if (output_format.equals("O3")||output_format.equals("o3")) {
					img1 = nearest(width, height, img, O3_width, O3_height, bytes,fileName);
				}
			}

			if (resampling_method == 2) { // Bilinear interpolation algorithm
				System.out.println("Upsampling a image using Bilinear interpolation algorithm.");

				if (output_format.equals("O1")||output_format.equals("o1")) {
					img1 = bilinear(width, height, img, O1_width, O1_height,fileName);
				}
				if (output_format.equals("O2")||output_format.equals("o2")) {
					img1 = bilinear(width, height, img, O2_width, O2_height,fileName);
				}
				if (output_format.equals("O3")||output_format.equals("o3")) {
					img1 = bilinear(width, height, img, O3_width, O3_height,fileName);
				}
			}
		}
		// Use a panel and label to display images
		// display original image
		JPanel panel = new JPanel();
		panel.add(new JLabel(new ImageIcon(img)));

		JFrame frame = new JFrame("Original images");

		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// display the scaled image
		JPanel panel1 = new JPanel();
		panel1.add(new JLabel(new ImageIcon(img1)));

		JFrame frame1 = new JFrame("Scaled images");

		frame1.getContentPane().add(panel1);
		frame1.pack();
		frame1.setVisible(true);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	// bilinear function where interpolate a value between (x,y), (x,y+1),
	// (x+1,y),(x+1,y+1)

	public static BufferedImage bilinear(int width, int height, BufferedImage img, int new_width, int new_height,String f) {
		BufferedImage img1 = new BufferedImage(new_width, new_height, BufferedImage.TYPE_INT_RGB);

		// get the ratio of x axis by which every pixel should jump
		double S1 = (width-1) / (double) new_width;
		double S2 = (height-1) / (double) new_height;
		float x_diff, y_diff;
		int a, b, c, d, x, y, index;

		// loop to get the r,g and b values
		for (int i = 0; i < new_height - 1; i++) {
			for (int j = 0; j < new_width - 1; j++) {
				x = (int) (S1 * j);
				y = (int) (S2 * i);
				x_diff = (float) ((S1 * j) - x);
				y_diff = (float) ((S2 * i) - y);

				// get RGB value
				a = img.getRGB(x, y);
				b = img.getRGB(x, y + 1);
				c = img.getRGB(x + 1, y);
				d = img.getRGB(x + 1, y + 1);

				// blue element
				// Yb = Ab(1-w)(1-h) + Bb(w)(1-h) + Cb(h)(1-w) + Db(wh)
				float blue = (a & 0xff) * (1 - x_diff) * (1 - y_diff) + (c & 0xff) * (x_diff) * (1 - y_diff)
						+ (b & 0xff) * (y_diff) * (1 - x_diff) + (d & 0xff) * (x_diff * y_diff);

				// green element
				// Yg = Ag(1-w)(1-h) + Bg(w)(1-h) + Cg(h)(1-w) + Dg(wh)
				float green = ((a >> 8) & 0xff) * (1 - x_diff) * (1 - y_diff)
						+ ((c >> 8) & 0xff) * (x_diff) * (1 - y_diff) + ((b >> 8) & 0xff) * (y_diff) * (1 - x_diff)
						+ ((d >> 8) & 0xff) * (x_diff * y_diff);

				// red element
				// Yr = Ar(1-w)(1-h) + Br(w)(1-h) + Cr(h)(1-w) + Dr(wh)
				float red = ((a >> 16) & 0xff) * (1 - x_diff) * (1 - y_diff)
						+ ((c >> 16) & 0xff) * (x_diff) * (1 - y_diff) + ((b >> 16) & 0xff) * (y_diff) * (1 - x_diff)
						+ ((d >> 16) & 0xff) * (x_diff * y_diff);

				int pix = 0xff000000 | ((((int) red) << 16) & 0xff0000) | ((((int) green) << 8) & 0xff00)
						| ((int) blue);

				// set RGB value back to output image.
				img1.setRGB(j, i, pix);
			}
		}
		//save image
		try{
			File file = new File("bilinear_"+f+"_"+width+"_"+new_width+".png");
			ImageIO.write(img1, "PNG", file);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		// return output image
		return img1;
	}

	public static BufferedImage nearest(int width, int height, BufferedImage img, int new_width, int new_height,
			byte[] bytes, String f) {
		// nearest neighbour by approximating the value of a function for a
		// non-given point in some space when given the value of that function
		// in points around (neighboring) that point
		BufferedImage img1 = new BufferedImage(new_width, new_height, BufferedImage.TYPE_INT_RGB);

		// ratio of old to new image
		double S1 = width / (double) new_width;
		double S2 = height / (double) new_height;

		// loop to get r,g,b value for the pixel
		for (int y = 0; y < new_height; y++) {

			for (int x = 0; x < new_width; x++) {
				double px = Math.floor(x * S1);
				double py = Math.floor(y * S2);
				int xy = (int) (py * width + px);

				byte a = 0;
				byte r = bytes[xy];
				byte g = bytes[xy + height * width];
				byte b = bytes[xy + height * width * 2];

				int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);

				img1.setRGB(x, y, pix);
			}
		}
		try{
			File file = new File("nearest_"+f+"_"+width+"_"+new_width+".png");
			ImageIO.write(img1, "PNG", file);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		return img1;
	}

	// Gaussian smoothing by averaging of pixels
	public static BufferedImage gaussian(int width, int height, BufferedImage img, int new_width, int new_height,
			byte[] bytes, String f) {

		BufferedImage img1 = new BufferedImage(new_width, new_height, BufferedImage.TYPE_INT_RGB);

		// ratio of old and new image to get the index of a image to which needs
		// to be picked for the scaled image
		double col = (double) width / new_width;
		double row = (double) height / new_height;

		int ind = 0;
		int x = 0, y = 0;

		for (y = 0; y < new_height; y++) {

			for (x = 0; x < new_width; x++) {
				double px = Math.floor(x * col);
				double py = Math.floor(y * row);
				ind = (int) (py * width + px); // indentify the index which
												// needs to be picked

				if (ind < 4000) {
					double a = 0;
					double r = bytes[ind];
					double g = bytes[ind + height * width];
					double b = bytes[ind + height * width * 2];
					//
					int pix = 0xff000000 | (((byte) r & 0xff) << 16) | (((byte) g & 0xff) << 8) | ((byte) b & 0xff);

					img1.setRGB(x, y, pix);
				}
				if (ind > 4000) {
					double a = 0;// get the average of (neighboring 8 pixels +
									// index pixel).
					double r = (double) (((bytes[ind] & 0xff) + (bytes[ind - width] & 0xff)
							+ (bytes[ind - width - 1] & 0xff) + (bytes[ind - width - 2] & 0xff)
							+ (bytes[ind - 1] & 0xff) + (bytes[ind + 1] & 0xff) + (bytes[ind + width] & 0xff)
							+ (bytes[ind + width + 1] & 0xff) + (bytes[ind + width + 2] & 0xff)) / (double) 9);

					double g = (double) (((bytes[ind + height * width] & 0xff)
							+ (bytes[(ind - width) + height * width] & 0xff)
							+ (bytes[(ind - width - 1) + height * width] & 0xff)
							+ (bytes[(ind - width - 2) + height * width] & 0xff)
							+ (bytes[(ind - 1) + height * width] & 0xff) + (bytes[(ind + 1) + height * width] & 0xff)
							+ (bytes[(ind + width) + height * width] & 0xff)
							+ (bytes[(ind + width + 1) + height * width] & 0xff)
							+ (bytes[(ind + width + 2) + height * width] & 0xff)) / (double) 9);

					double b = (double) (((bytes[ind + height * width * 2] & 0xff)
							+ (bytes[(ind - width) + height * width * 2] & 0xff)
							+ (bytes[(ind - width - 1) + height * width * 2] & 0xff)
							+ (bytes[(ind - width - 2) + height * width * 2] & 0xff)
							+ (bytes[(ind - 1) + height * width * 2] & 0xff)
							+ (bytes[(ind + 1) + height * width * 2] & 0xff)
							+ (bytes[(ind + width) + height * width * 2] & 0xff)
							+ (bytes[(ind + width + 1) + height * width * 2] & 0xff)
							+ (bytes[(ind + width + 2) + height * width * 2] & 0xff)) / (double) 9);

					int pix = 0xff000000 | ((byte) r & 0xff) << 16 | ((byte) g & 0xff) << 8 | (byte) b & 0xff;

					img1.setRGB(x, y, pix);
				}
			}
		}
		try{
			File file = new File("gaussian_"+f+"_"+width+"_"+new_width+".png");
			ImageIO.write(img1, "PNG", file);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		return img1;
	}

	// Specific sampling - picking pixel based on the ratio of old and new
	// image.
	// example - if 4000* 3000 is scaled to 1920 * 1080 then every 2nd pixel is
	// picked
	// if 4000*3000 image is scaled to 1280*740 then every 3rd pixel is picked
	// if 4000*3000 image is scaled to 640*480 then every 6th pixel is picked.

	public static BufferedImage specific(int width, int height, BufferedImage img, int new_width, int new_height,
			byte[] bytes, String f) {

		BufferedImage img1 = new BufferedImage(new_width, new_height, BufferedImage.TYPE_INT_RGB);

		double col = (double) width / new_width;

		double row = (double) height / new_height;

		int col_fl = (int) Math.floor(col);

		int row_fl = (int) Math.floor(row);

		int ind = col_fl;
		int x = 0, y = 0;

		for (y = 0; y < new_height; y++) {
			ind = width * y * row_fl;

			for (x = 0; x < new_width; x++) {
				byte a = 0;
				byte r = bytes[ind];
				byte g = bytes[ind + height * width];
				byte b = bytes[ind + height * width * 2];

				int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
				// int pix = ((a << 24) + (r << 16) + (g << 8) + b);
				ind = ind + col_fl;

				img1.setRGB(x, y, pix);
			}
		}
		try{
		File file = new File("specific_"+f+"_"+width+"_"+new_width+".png");
		ImageIO.write(img1, "PNG", file);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return img1;
	}
}

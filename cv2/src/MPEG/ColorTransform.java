package MPEG;

import ij.ImagePlus;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.util.Arrays;

import Jama.Matrix;

public class ColorTransform {

	private int[][] red;

	public int[][] getRed() {
		return red;
	}

	public void setRed(int[][] red) {
		this.red = red;
	}

	public int[][] getGreen() {
		return green;
	}

	public void setGreen(int[][] green) {
		this.green = green;
	}

	public int[][] getBlue() {
		return blue;
	}

	public void setBlue(int[][] blue) {
		this.blue = blue;
	}
	

	public Matrix getY() {
		return y;
	}

	public void setY(Matrix y) {
		this.y = y;
	}

	public Matrix getcB() {
		return cB;
	}

	public void setcB(Matrix cB) {
		this.cB = cB;
	}

	public Matrix getcR() {
		return cR;
	}

	public void setcR(Matrix cR) {
		this.cR = cR;
	}


	public int getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}


	private int[][] green;
	private int[][] blue;

	private int imageHeight;
	private int imageWidth;

	private Matrix y;
	private Matrix cB;
	private Matrix cR;

	private BufferedImage bImage;
	private ColorModel colorModel;
	
	public static final double[][] quantizationMatrix8Y = {
			{ 16, 11, 10, 16, 24, 40, 51, 61 },
			{ 12, 12, 14, 19, 26, 58, 60, 55 },
			{ 14, 13, 16, 24, 40, 57, 69, 56},
			{ 14, 17, 22, 29, 51, 87, 80, 62 },
			{ 18, 22, 37, 56, 68, 109, 103, 77 },
			{ 24, 35, 55, 64, 81, 104, 113, 92 },
			{ 49, 64, 78, 87, 103 , 121, 120, 101 },
			{ 72, 92, 95, 98, 112, 100, 103, 99 }
	};
	
	public Matrix getQuantizationMatrix8Y() {
		return new Matrix(Arrays.stream(quantizationMatrix8Y).toArray(double[][]::new));
	}
	public static final double[][] quantizationMatrix8C = {
			{ 17, 18, 24, 47, 99, 99, 99, 99 },
			{ 18, 21, 26, 66, 99, 99, 99, 99 },
			{ 24, 26, 56, 99, 99, 99, 99, 99 },
			{ 47, 66, 99, 99, 99, 99, 99, 99 },
			{ 99, 99, 99, 99, 99, 99, 99, 99 },
			{ 99, 99, 99, 99, 99, 99, 99, 99 },
			{ 99, 99, 99, 99, 99, 99, 99, 99 },
			{ 99, 99, 99, 99, 99, 99, 99, 99 }
	};
	public Matrix getQuantizationMatrix8C() {
		return new Matrix(Arrays.stream(quantizationMatrix8C).toArray(double[][]::new));
	} 

	public ColorTransform(BufferedImage bImage) {
		this.bImage = bImage;
		this.colorModel = bImage.getColorModel();
		this.imageHeight = bImage.getHeight();
		this.imageWidth = bImage.getWidth();

		this.red = new int[this.imageHeight][this.imageWidth];
		this.green = new int[this.imageHeight][this.imageWidth];
		this.blue = new int[this.imageHeight][this.imageWidth];

		this.y = new Matrix(this.imageHeight, this.imageWidth);
		this.cB = new Matrix(this.imageHeight, this.imageWidth);
		this.cR = new Matrix(this.imageHeight, this.imageWidth);
	}
	
	public ColorTransform(Matrix Y, Matrix cB, Matrix cR) {
		this.imageHeight = Y.getRowDimension();
		this.imageWidth = Y.getColumnDimension();

		this.y = Y;
		this.cB = cB;
		this.cR = cR;
		
		this.red = new int[this.imageHeight][this.imageWidth];
		this.green = new int[this.imageHeight][this.imageWidth];
		this.blue = new int[this.imageHeight][this.imageWidth];
		convertYcbcrToRgb();
		this.bImage = new BufferedImage(this.imageWidth, this.imageHeight, BufferedImage.TYPE_INT_RGB);
		int[][] rgb = new int[this.imageHeight][this.imageWidth];
		for (int i = 0; i < this.imageHeight; i++) {
			for (int j = 0; j < this.imageWidth; j++) {
				rgb[i][j] = new Color(red[i][j], green[i][j], blue[i][j])
						.getRGB();
				this.bImage.setRGB(j, i, rgb[i][j]);
			}
		}
		this.colorModel = bImage.getColorModel();

	}

	public void getRGB() {
		for (int i = 0; i < this.imageHeight; i++) {
			for (int j = 0; j < this.imageWidth; j++) {
				this.red[i][j] = colorModel.getRed(this.bImage.getRGB(j, i));
				this.green[i][j] = colorModel
						.getGreen(this.bImage.getRGB(j, i));
				this.blue[i][j] = colorModel.getBlue(this.bImage.getRGB(j, i));
			}
		}
	}

	public ImagePlus setImageFromRGB(int width, int height, int[][] red,
			int[][] green, int[][] blue) {
		BufferedImage bImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		int[][] rgb = new int[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				rgb[i][j] = new Color(red[i][j], green[i][j], blue[i][j])
						.getRGB();
				bImage.setRGB(j, i, rgb[i][j]);
			}
		}
		return (new ImagePlus("RGB", bImage));
	}
	
	public ImagePlus setImageFromRGB(String component) {
		this.bImage = new BufferedImage(this.imageWidth, this.imageHeight, BufferedImage.TYPE_INT_RGB);
		int[][] rgb = new int[this.imageHeight][this.imageWidth];
		for (int i = 0; i < this.imageHeight; i++) {
			for (int j = 0; j < this.imageWidth; j++) {
				rgb[i][j] = new Color(red[i][j], green[i][j], blue[i][j])
						.getRGB();
				this.bImage.setRGB(j, i, rgb[i][j]);
			}
		}
		return (new ImagePlus(component, bImage));
	}
	
	public ImagePlus setImageFromRGB(int width, int height, int[][] red,
			int[][] green, int[][] blue, String component) {
		BufferedImage bImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		int[][] rgb = new int[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				rgb[i][j] = new Color(red[i][j], green[i][j], blue[i][j])
						.getRGB();
				bImage.setRGB(j, i, rgb[i][j]);
			}
		}
		return (new ImagePlus(component, bImage));
	}

	public ImagePlus setImageFromRGB(int width, int height, int[][] x, String component) {
		BufferedImage bImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int[][] rgb = new int[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				rgb[i][j] = new Color(x[i][j], x[i][j], x[i][j]).getRGB();
				bImage.setRGB(j, i, rgb[i][j]);
			}
		}
		return (new ImagePlus(component, bImage));
	}
	
	public ImagePlus setImageFromRGB(int width, int height, Matrix x, String component) {
		BufferedImage bImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int[][] rgb = new int[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				rgb[i][j] = new Color((int)x.get(i, j), (int)x.get(i, j), (int)x.get(i, j)).getRGB();
				bImage.setRGB(j, i, rgb[i][j]);
			}
		}
		return (new ImagePlus(component, bImage));
	}
	
	public void convertRgbToYcbcr() {
		for (int i = 0; i < this.imageHeight; i++) {
			for (int j = 0; j < this.imageWidth; j++) {
				y.set(i, j, 0.257 * red[i][j] + 0.504 * green[i][j] + 0.098
						* blue[i][j] + 16);
				cB.set(i, j, -0.148 * red[i][j] - 0.291 * green[i][j] + 0.439
						* blue[i][j] + 128);
				cR.set(i, j, 0.439 * red[i][j] - 0.368 * green[i][j] - 0.071
						* blue[i][j] + 128);
			}
		}
	}

	public void convertYcbcrToRgb() {
		for (int i = 0; i < this.imageHeight; i++) {
			for (int j = 0; j < this.imageWidth; j++) {
				int r, g, b;
				r = (int) Math.round(1.164 * (y.get(i, j) - 16) + 1.596 * (cR.get(i, j) - 128));
				if (r > 255)
					r = 255;
				if (r < 0)
					r = 0;
				
				red[i][j] = r;
				
				g = (int) Math.round(1.164 * (y.get(i, j) - 16) - 0.813 * (cR.get(i, j) - 128) - 0.391 * (cB.get(i, j) - 128));
				if (g > 255)
					g = 255;
				if (g < 0)
					g = 0;
				green[i][j] = g;
				
				b = (int) Math.round(1.164 * (y.get(i, j) - 16) + 2.018 * (cB.get(i, j) - 128));
				if (b > 255)
					b = 255;
				if (b < 0)
					b = 0;
				blue[i][j] = b;
			}
		}
	}
	
	public Matrix downSample(Matrix mat)
	{
		int [] columns = new int [mat.getColumnDimension()/2];
		
		for (int i = 0; i < mat.getColumnDimension()/2; i++) 
		{
			columns[i] = i+i;
			mat.getMatrix(0, mat.getRowDimension()-1, columns);
		}
		return mat.getMatrix(0, mat.getRowDimension()-1, columns);
	}
	
	
	/*public Matrix downSample (Matrix mat, int ratio) {
		ratio=4/ratio;
		int y=mat.getRowDimension();	//øádky
		int x=mat.getColumnDimension()/ratio;	//sloupce
		Matrix m = new Matrix(y, x);
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				m.set(i, j, mat.get(i, j*ratio));
			}
		}
		return m;
	}
	
	public Matrix downSample0 (Matrix mat, int ratio) {
		ratio=4/ratio;
		int y=mat.getRowDimension()/ratio;	//øádky
		int x=mat.getColumnDimension()/ratio;	//sloupce
		Matrix m = new Matrix(y, x);
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				m.set(i, j, mat.get(i*ratio, j*ratio));
			}
		}
		return m;
	}

	public Matrix overSample(Matrix mat) {
		int y=mat.getRowDimension()*2;	//øádky
		int x=mat.getColumnDimension()*2;	//sloupce
		Matrix mat2 = new Matrix((mat.getRowDimension()*2), (mat.getColumnDimension()*2));
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				m.set(i, j, mat.get(i*ratio, j*ratio));
			}
		}
		return mat;
	}*/
	
	public Matrix overSample(Matrix mat)
	{
		int [] column= new int[1];
		Matrix mat2 = new Matrix((mat.getRowDimension()), (mat.getColumnDimension()*2));
		for (int i = 0; i < mat.getColumnDimension(); i++) 
		{
			column[0] = i;
			mat2.setMatrix(0, mat.getRowDimension()-1, i*2, i*2, mat.getMatrix(0, mat.getRowDimension()-1, column));
			mat2.setMatrix(0, mat.getRowDimension()-1, i*2+1, i*2+1, mat.getMatrix(0, mat.getRowDimension()-1, column));
		}
		return mat2;
	}
	
	public Matrix transform (int size, Matrix transformMatrix, Matrix inputMatrix) {
		Matrix y = transformMatrix.times(inputMatrix);
		y = y.times(transformMatrix.transpose());
		
		return y;
	}
	
	public Matrix inverseTransform (int size, Matrix transformMatrix, Matrix inputMatrix) {
		Matrix y = transformMatrix.transpose();
		y = y.times(inputMatrix);
		y = y.times(transformMatrix);
		return y;
	}
	
	public Matrix[][] MatrixToBlocks(Matrix mat, int size) {
		Matrix[][] matOut = new Matrix[mat.getRowDimension()/size][mat.getColumnDimension()/size];
		Matrix temp = new Matrix(size, size);
		for (int i = 0; i < mat.getRowDimension()/size; i++) {
			for (int j = 0; j < mat.getColumnDimension()/size; j++) {
				temp = mat.getMatrix(i*size, i*size+size-1, j*size, j*size+size-1);
				matOut[i][j]=temp;
			}
			
		}
		
		return matOut;
	}
	
	public Matrix MatrixRounding(Matrix mat) {
		double pom = 0;
		for (int i = 0; i < mat.getRowDimension(); i++) {
			for (int j = 0; j < mat.getColumnDimension(); j++) {
				pom = mat.get(i, j);
				if (pom > 0)
					mat.set(i, j, Math.floor(pom));
				else
					mat.set(i, j, Math.ceil(pom));
			}
		}
		return mat;
	}
	
	public Matrix BlocksToMatrix(Matrix mat[][], int size) {
		Matrix temp = new Matrix(mat.length*size, mat[0].length*size);
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) {
				temp.setMatrix(i*size, i*size+size-1, j*size, j*size+size-1, mat[i][j]);						
			}
		}
		return temp;
	}
	

	public Matrix[][] QuantizationY(Matrix[][] input, double quality){
		Matrix[][] output = new Matrix[input.length][input[0].length];
		output = input;
		double alfa = 0.0;
		Matrix qantMat = null;
		if (quality < 50) {
			alfa = 50 / quality;
			qantMat = new Matrix(getQuantizationMatrix8Y().getArrayCopy());
		} else if (quality >= 50 && quality < 99) {

			alfa = 2 - 2 * quality / 100.0;
			qantMat = new Matrix(getQuantizationMatrix8Y().getArrayCopy());
		} else if (quality == 100) {
			alfa=1;
			qantMat = new Matrix(8, 8, 1);
		}
		//System.out.println("Alfa: " + alfa);
		
		if (input[0][0].getRowDimension()>8) {
			qantMat = overSample(qantMat);
			qantMat = qantMat.transpose();
			qantMat = overSample(qantMat);
			qantMat = qantMat.transpose();
		}
		
		/*qantMat = qantMat.timesEquals(alfa);
		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < input[0].length; j++) {
				output[i][j] = input[i][j].arrayRightDivideEquals(qantMat);
				output[i][j] = MatrixRounding(output[i][j]);
			}
		}*/
		/*System.out.println("Vypisuji qantovací matici po:");
		qantMat.print(2, 0);
		System.out.println("");*/
		
		
		double pom;
		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < input[0].length; j++) {
				for (int m = 0; m < input[i][j].getRowDimension(); m++) {
					for (int n = 0; n < input[i][j].getColumnDimension(); n++) {
						pom = (input[i][j].get(m, n) / (alfa *qantMat.get(m, n)));
						
						/*if (pom > 0)
							output[i][j].set(m, n, Math.floor(pom));
						else
							output[i][j].set(m, n, Math.ceil(pom));*/
						//pom = Math.round(pom);
						output[i][j].set(m, n, Math.round(pom));
						//output[i][j].set(m, n, pom);
					}
					
				}
			}
		}
		
		return output;
	}
	
	public Matrix[][] QuantizationC(Matrix[][] input, double quality){
		Matrix[][] output = new Matrix[input.length][input[0].length];
		output = input;
		double alfa = 0.0;
		Matrix qantMat = null;
		if (quality < 50) {
			alfa = 50 / quality;
			qantMat = new Matrix(getQuantizationMatrix8C().getArrayCopy());
		} else if (quality >= 50 && quality < 99) {

			alfa = 2 - 2 * quality / 100.0;
			qantMat = new Matrix(getQuantizationMatrix8C().getArrayCopy());
		} else if (quality == 100) {
			alfa=1;
			qantMat = new Matrix(8, 8, 1);
		}
		
		if (input[0][0].getRowDimension()>8) {
			qantMat = overSample(qantMat);
			qantMat = qantMat.transpose();
			qantMat = overSample(qantMat);
			qantMat = qantMat.transpose();
		}
		/*qantMat = qantMat.timesEquals(alfa);
		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < input[0].length; j++) {
				output[i][j] = input[i][j].arrayRightDivideEquals(qantMat);
				output[i][j] = MatrixRounding(output[i][j]);
			}
		}*/
		double pom;
		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < input[0].length; j++) {
				for (int m = 0; m < input[i][j].getRowDimension(); m++) {
					for (int n = 0; n < input[i][j].getColumnDimension(); n++) {
						pom = (input[i][j].get(m, n) / (alfa * qantMat.get(m, n)));
						
						/*if (pom > 0)
							output[i][j].set(m, n, Math.floor(pom));
						else
							output[i][j].set(m, n, Math.ceil(pom));*/
						//pom = Math.round(pom);
						output[i][j].set(m, n, Math.round(pom));
						//output[i][j].set(m, n, pom);
					}
				}
			}
		}
		
		return output;
	}
	
	public Matrix[][] InvQuantizationY(Matrix[][] input, double quality){
		Matrix[][] output = new Matrix[input.length][input[0].length];
		output = input;
		double alfa = 0.0;
		Matrix qantMat = null;
		if (quality < 50) {
			alfa = 50 / quality;
			qantMat = new Matrix(getQuantizationMatrix8Y().getArrayCopy());
		} else if (quality >= 50 && quality < 99) {

			alfa = 2 - 2 * quality / 100.0;
			qantMat = new Matrix(getQuantizationMatrix8Y().getArrayCopy());
		} else if (quality == 100) {
			alfa=1;
			qantMat = new Matrix(8, 8, 1);
		}
		
		if (input[0][0].getRowDimension()>8) {
			qantMat = overSample(qantMat);
			qantMat = qantMat.transpose();
			qantMat = overSample(qantMat);
			qantMat = qantMat.transpose();
		}
		/*qantMat = qantMat.timesEquals(alfa);
		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < input[0].length; j++) {
				output[i][j] = input[i][j].arrayTimesEquals(qantMat);
			}
		}*/
		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < input[0].length; j++) {
				
				for (int m = 0; m < input[i][j].getRowDimension(); m++) {
					for (int n = 0; n < input[i][j].getColumnDimension(); n++) {
						output[i][j].set(m, n, (input[i][j].get(m, n) * alfa * qantMat.get(m, n)));
					}
					
				}
			}
		}
		
		return output;
	}
	
	public Matrix[][] InvQuantizationC(Matrix[][] input, double quality){
		Matrix[][] output = new Matrix[input.length][input[0].length];
		output = input;
		double alfa = 0.0;
		Matrix qantMat = null;
		if (quality < 50) {
			alfa = 50 / quality;
			qantMat = new Matrix(getQuantizationMatrix8C().getArrayCopy());
		} else if (quality >= 50 && quality < 99) {

			alfa = 2 - 2 * quality / 100.0;
			qantMat = new Matrix(getQuantizationMatrix8C().getArrayCopy());
		} else if (quality == 100) {
			alfa=1;
			qantMat = new Matrix(8, 8, 1);
		}
		 
		if (input[0][0].getRowDimension()>8) {
			qantMat = overSample(qantMat);
			qantMat = qantMat.transpose();
			qantMat = overSample(qantMat);
			qantMat = qantMat.transpose();
		}
		
		/*qantMat = qantMat.timesEquals(alfa);
		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < input[0].length; j++) {
				output[i][j] = input[i][j].arrayTimesEquals(qantMat);
			}
		}*/
		
		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < input[0].length; j++) {
				for (int m = 0; m < input[i][j].getRowDimension(); m++) {
					for (int n = 0; n < input[i][j].getColumnDimension(); n++) {
						output[i][j].set(m, n, (input[i][j].get(m, n) * alfa * qantMat.get(m, n)));
					}
				}
				//output[i][j] = input[i][j].times(qantMat);
			}
		}
		
		return output;
	}
}
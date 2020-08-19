package MPEG;
import Jama.Matrix;


public class Quality {
	
	public double getMse(int width, int height, int[][] original, int[][] edited) {
		double MSE = 0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				MSE += (original[i][j] - edited[i][j]) * (original[i][j] - edited[i][j]);
			}
		}
		MSE = MSE/(height*width);
		
		return MSE;
	}
	
	public double getMse(Matrix original, Matrix edited) {
		double MSE = 0;
		for (int i = 0; i < original.getRowDimension(); i++) {
			for (int j = 0; j < original.getColumnDimension(); j++) {
				MSE += (original.get(i, j) - edited.get(i, j)) * (original.get(i, j) - edited.get(i, j));
			}
		}
		MSE = MSE/(original.getRowDimension()*original.getColumnDimension());
		
		return MSE;
	}
	
	public double getSad(Matrix original, Matrix edited) {
		double SAD = 0;
		for (int i = 0; i < original.getRowDimension(); i++) {
			for (int j = 0; j < original.getColumnDimension(); j++) {
				SAD += Math.abs(original.get(i, j) - edited.get(i, j));
			}
		}
		//SAD = SAD /(original.getRowDimension()*original.getColumnDimension());
		return SAD;
	}
	
	public double getPsnr(int width, int height, int[][] original, int[][] edited) {
		
		double MSE = this.getMse(width, height, original, edited);
		//return (10*Math.log10(((2^8 - 1)^2)/MSE));
		return 10*Math.log10(((Math.pow( (Math.pow(2, 8)-1), 2)/MSE)));
	}
	
	public double getPsnr(double MSE) {
		
		return 10*Math.log10(( ( Math.pow( (Math.pow(2, 8)-1), 2 ) / MSE ) ) );
		//return (10*Math.log10(((2^8 - 1)^2)/MSE));
	}
}

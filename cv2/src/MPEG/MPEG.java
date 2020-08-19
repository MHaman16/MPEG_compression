package MPEG;
//import java.net.URL;
//import java.util.ResourceBundle;

import Jama.Matrix;
//import ij.ImagePlus;


public class MPEG {
	private Quality quality = new Quality();
	private Matrix x, y;
	
	private Matrix minus(Matrix A, Matrix B) {
		double d;
		Matrix C = new Matrix(A.getRowDimension(), A.getColumnDimension());
		for (int i = 0; i < A.getRowDimension(); i++) {
			for (int j = 0; j < A.getColumnDimension(); j++) {
					d = A.get(i, j)+255-B.get(i, j);
					d=d/2;
					//if(d<0) d=0;
					C.set(i, j, d);					
				}
			}
		return C;
	}
	
	
	ColorTransform DPCM(ColorTransform A, ColorTransform B) {
		ColorTransform C = new ColorTransform(minus(A.getY(), B.getY()), minus(A.getcB(), B.getcB()), minus(A.getcR(), B.getcR()));
		return C;
	}

	
	Matrix ExpandMatrix(Matrix input, int size) {	//size is size of block
		
		Matrix output = new Matrix((input.getRowDimension()+size), (input.getColumnDimension()+size));
		int or = output.getRowDimension()-1;
		int oc = output.getColumnDimension()-1;
		int ir = input.getRowDimension()-1;
		int ic = input.getColumnDimension()-1;
		int s = size/2;	
		
		output.setMatrix(s, s+ir, s, s+ic, input); //prostøedek
		
		output.setMatrix(0, (s-1), 0, (s-1), new Matrix(s, s, input.get(0, 0)));	//roh L horni		
		output.setMatrix((or-s+1), or, 0, (s-1), new Matrix(s, s, input.get(ir, 0)));	//roh L spodni		
		output.setMatrix(0, (s-1), (oc-s+1), oc, new Matrix(s, s, input.get(0, ic)));	//roh P horni
		output.setMatrix((or-s+1), or, (oc-s+1), oc, new Matrix(s, s, input.get(ir, ic)));	//roh P spodni
		
		for (int i = 0; i < s; i++) {
			output.setMatrix(s, (or - s), i, i, input.getMatrix(0, ir, 0, 0));	//L bok
			output.setMatrix(s, (or-s), (oc-s+1+i), (oc-s+1+i), input.getMatrix(0, ir, ic, ic));	//P bok
			output.setMatrix(i, i, s, (oc-s), input.getMatrix(0, 0, 0, ic));	//horní bok
			output.setMatrix((or-s+1+i), (or-s+1+i), s, (oc-s), input.getMatrix(ir, ir, 0, ic));	//spodní bok
		}
		
		return output;
	}
	
	public int[] Search(Matrix A, Matrix B) {	//row offset, column offset
		if(A.getColumnDimension() != A.getRowDimension() || B.getColumnDimension() != B.getRowDimension() || A.getColumnDimension() != 2*(B.getColumnDimension()))	return null;
		double minSAD = quality.getSad(A.getMatrix(0, (0+B.getRowDimension()-1), 0, (0+B.getColumnDimension()-1)), B);
		double sad;
		int vector[] = {-B.getRowDimension()/2, -B.getColumnDimension()/2};
		for (int i = 0; i <= B.getRowDimension(); i++) {
			for (int j = 0; j <= B.getColumnDimension(); j++) {
				sad = quality.getSad(A.getMatrix(i, (i+B.getRowDimension()-1), j, (j+B.getColumnDimension()-1)), B);
				//System.out.println("Øádek: " + i + "    Sloupec: " + j + "     SAD: " + sad);
				if (sad<minSAD) {
					minSAD = sad;
					vector[0] = -(B.getRowDimension()/2 - i);
					vector[1] = -(B.getColumnDimension()/2 - j);
					
				}
			}
		}
		return vector;
	}


	public void fullSearch(Matrix A, Matrix B, int size) {
		Matrix a = ExpandMatrix(A, size);
		x = null;
		y = null;
		x = new Matrix((B.getRowDimension()/size), (B.getColumnDimension()/size));
		y = new Matrix((B.getRowDimension()/size), (B.getColumnDimension()/size));
		for (int i = 0; i < (B.getRowDimension()/size); i++) {
			for (int j = 0; j < (B.getColumnDimension()/size); j++) {
				int vectors[] = Search(a.getMatrix((i*size), ((i+2)*size-1), (j*size), ((j+2)*size-1)), B.getMatrix((i*size), ((i+1)*size-1), (j*size), ((j+1)*size-1)));
				x.set(i, j, vectors[0]);
				y.set(i, j, vectors[1]);
			}
		}
		//y.print(2, 0);
	}
	
	public ColorTransform motionCompensation(ColorTransform A, int size) {
		
		Matrix Ycop = new Matrix(A.getY().getArrayCopy());
		Matrix Cbcop = new Matrix(A.getcB().getArrayCopy());
		Matrix Crcop = new Matrix(A.getcR().getArrayCopy());
		int a = (int)(A.getImageHeight() + size/2 - 1);
		int b = (int)(A.getImageWidth() + size/2 - 1);
		Matrix Y = new Matrix((A.getImageHeight()+size), (A.getImageWidth()+size));
		Matrix Cb = new Matrix((A.getImageHeight()+size), (A.getImageWidth()+size)); 
		Matrix Cr = new Matrix((A.getImageHeight()+size), (A.getImageWidth()+size));
		Y.setMatrix((int)(size/2), a, (int)(size/2), b, Ycop);
		Cb.setMatrix((int)(size/2), a, (int)(size/2), b, Cbcop);
		Cr.setMatrix((int)(size/2), a, (int)(size/2), b, Crcop);
		for (int i = 0; i < (A.getImageHeight()/size); i++) {
			for (int j = 0; j < (A.getImageWidth()/size); j++) {
				Y.setMatrix((int)(size/2 + i*size + x.get(i, j)), (int)(size/2 + (i+1)*size - 1 + x.get(i, j)), (int)(size/2 + j*size + y.get(i, j)), (int)(size/2 + (j+1)*size -1 + y.get(i, j)), A.getY().getMatrix((i*size), ((i+1)*size-1), (j*size), ((j+1)*size-1)));
				Cb.setMatrix((int)(size/2 + i*size + x.get(i, j)), (int)(size/2 + (i+1)*size - 1 + x.get(i, j)), (int)(size/2 + j*size + y.get(i, j)), (int)(size/2 + (j+1)*size -1 + y.get(i, j)), A.getcB().getMatrix((i*size), ((i+1)*size-1), (j*size), ((j+1)*size-1)));
				Cr.setMatrix((int)(size/2 + i*size + x.get(i, j)), (int)(size/2 + (i+1)*size - 1 + x.get(i, j)), (int)(size/2 + j*size + y.get(i, j)), (int)(size/2 + (j+1)*size -1 + y.get(i, j)), A.getcR().getMatrix((i*size), ((i+1)*size-1), (j*size), ((j+1)*size-1)));
			}
		}
		
		Matrix y = Y.getMatrix((size/2), (A.getImageHeight()+size/2-1), (size/2), (A.getImageWidth()+size/2)-1);
		Matrix cb = Cb.getMatrix((size/2), (A.getImageHeight()+size/2-1), (size/2), (A.getImageWidth()+size/2-1));
		Matrix cr = Cr.getMatrix((size/2), (A.getImageHeight()+size/2-1), (size/2), (A.getImageWidth()+size/2-1));
		return new ColorTransform(y, cb, cr);
	}

}
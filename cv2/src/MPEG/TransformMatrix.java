package MPEG;

import Jama.Matrix;

public class TransformMatrix {
	
	public Matrix getDctMatrix (int size) {
		Matrix dct = new Matrix(size, size);
		double c;
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (i == 0) {
					c = Math.sqrt(1.0/size)*Math.cos((double)(((2*j+1)*i*Math.PI)/(2*size)));
							
				} else {
					c = Math.sqrt(2.0/size)*Math.cos((double)(((2*j+1)*i*Math.PI)/(2*size)));
				}
				dct.set(i, j, c);
			}
		}
		//System.out.println("Vypisuji DCT matici:");
		//dct.print(2, 2);
		//System.out.println("");
		return dct;
		
	}
	
	public Matrix getWhtMatrix(int N) {

		Matrix newMat = new Matrix(N, N);
		newMat.set(0, 0, 1);

		for (int k = 1; k < N; k += k) {
			for (int i = 0; i < k; i++) {
				for (int j = 0; j < k; j++) {
					newMat.set(i + k, j, newMat.get(i, j));
					newMat.set(i, j + k, newMat.get(i, j));
					newMat.set(i + k, j + k, (-1) * newMat.get(i, j));
				}
			}
		}
		//System.out.println("Vypisuji WHT matici:");
		//newMat.print(2, 2);
		//System.out.println("");
		newMat = newMat.times(1/Math.sqrt(N));
		return newMat;
		}

}

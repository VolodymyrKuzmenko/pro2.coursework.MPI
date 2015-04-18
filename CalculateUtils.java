

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                                                                           *
 *                 Laboratory work #6. Java. Monitors                        *
 *                                                                           *
 * Task: MA = (B*C)*MO + α*(MT*MR)                                           *
 *                                                                           *
 * @file CalculateUtils.java 						     *
 * @author Kuzmenko Volodymyr					             *
 * @group IO-21								     *
 * @date 17.04.2015                                                          *
 *                                                                           *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
public class CalculateUtils {

	
 	public static void inputVector(int [] vector,int value) {
  		for(int i = 0; i < vector.length; i++) {
 			vector[i] = value;
 		}
 	}
 	
 	
 	public static void inputMatrix(int [][] matrix,int value) {
 		for(int i = 0; i < matrix.length; i++) {
 			for(int j = 0; j < matrix[i].length; j++) {
 				matrix[i][j] = value;	
 			}
 		}
 		
 	}
 	
 	
 	public static void outputVector(int [] vector) {
 		if(Executor.N <= 12) {
 			for (int i = 0; i < vector.length; i++) {
				System.out.print(vector[i]+" ");
			}
 		}
 	}
 	
 	
 	public static void outputMatrix(int [][] matrix) {
 		if(Executor.H <= 12) {
 			for (int i = 0; i < matrix.length; i++) {
				outputVector(matrix[i]);
				System.out.println();
			}
 		}
 	}
 	
 /*	
 	private static Matrix add(final Matrix left, final Matrix right,
 			final int id) {
 		int l = (id - 1) * Executor.H;
 		int r = id * Executor.H;
 		Matrix result = new Matrix(left.size());
 		for(int i = l; i < r; i++) {
 			for (int j = 0; j < left.size(); j++) {
 				result.set(i,j, left.get(i,j) + right.get(i,j));
			}
 			
 		}
 		return result;
 	}
 	
 	
 	private static Matrix mult(final int left, final Matrix right,
 			final int id) {
 		int l = (id - 1) * Executor.H;
 		int r = id * Executor.H;
 		Matrix result = new Matrix(right.size());
 		for (int i = l; i < r; i++) {
 			for (int j = 0; j < right.size(); j++) {
 				result.set(i,j, left * right.get(i, j));
 			}
 		}
 		
 		return result;
 	}
 	
 	private static Matrix mult(final Matrix left, final Matrix right,
 			final int id) {
 		int l = (id - 1) * Executor.H;
 		int r = id * Executor.H;
 		Matrix result = new Matrix(left.size());
 		for (int i = l; i < r; i++) {
 			for (int j = 0; j < left.size(); j++) {
 				result.set(i, j, 0);
 				for (int y = 0; y < left.size(); y++) {
 					result.set(i, j, result.get(i, j) + left.get(i, y)
 							* right.get(y, j));
 				}
 			}
 		}
 		
 		
 		return result;
 	}
 	private static int mult(final Vector left, final Vector right, final int id){
 		
 		int l = (id - 1) * Executor.H;
 		int r = id * Executor.H;
 		int result =  0;
 		for (int i = l; i < r; i++) {
			result += left.get(i)*right.get(i);
		}

 		return result;
 	}
 	
 	public static void operation3(final int alfai, final int vi, final Matrix MRi, int tid ){
 		Matrix MAi = CalculateUtils.add(mult(vi, Executor.MO, tid), mult(alfai, mult(MRi, Executor.MT, tid), tid), tid);
 		int l = (tid - 1) * Executor.H;
 		int r = tid * Executor.H;
 		
 		for (int i = l; i < r; i++) {
			for (int j = 0; j < Executor.N; j++) {
				Executor.MA.set(i, j, MAi.get(i, j));
			}
		}
 	}
 	public static int operation1(int tid){
 		return CalculateUtils.mult(Executor.B, Executor.C, tid);
 	}
 	*/
 	
}

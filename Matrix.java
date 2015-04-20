import java.io.Serializable;


/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                                                                           *
 *                 Laboratory work #6. Java. Monitors                        *
 *                                                                           *
 * Task: MA = (B*C)*MO + α*(MT*MR)                                           *
 *                                                                           *
 * @file Matrix.java 							     *
 * @author Kuzmenko Volodymyr					             *
 * @group IO-21								     *
 * @date 17.04.2015                                                          *
 *                                                                           *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
public class Matrix implements Serializable {
	
	private Vector[] array;

	public Matrix(int n) {
		array = new Vector[n];
		for (int i = 0; i < array.length; i++){
			array[i] = new Vector(n);
		}
	}

	public void set(int n, int m, int val) {
		array[n].set(m, val);
	}
	
	public int get(int n, int m) {
		return array[n].get(m);
	}

	public Vector get(int index) {
		return array[index];
	}
	
	public int size() {
		return array.length;
	}
	
	public String toString() {
		String res = "";
		for (int i = 0; i < array.length; i++){
			res += array[i].toString();
		}
		return res;
	}
	
	public Matrix copy(int start,int size){
		Matrix result = new Matrix(size);
		for (int i = 0; i < result.size(); i++) {
			result.array[i] = array[i+start].copy(0, Executor.N);
		}
		return result;
	}
}

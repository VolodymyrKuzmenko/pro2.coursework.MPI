import java.io.Serializable;


/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                                                                           *
 *                 Laboratory work #6. Java. Monitors                        *
 *                                                                           *
 * Task: MA = (B*C)*MO + α*(MT*MR)                                           *
 *                                                                           *
 * @file Vector.java 						             *
 * @author Kuzmenko Volodymyr					             *
 * @group IO-21								     *
 * @date 17.04.2015                                                          *
 *                                                                           *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
public class Vector implements Serializable {
	private int[] array;
	

	public Vector(int n) {
		array = new int[n];
	}

	public void set(int index, int value) {
		array[index] = value;
	}
	
	public int get(int index) {
		return array[index];
	}
	
	public int size() {
		return array.length;
	}
	
	public String toString() {
		String res = "";
		for (int i = 0; i < array.length; i++){
			res += "   " + array[i];
		}
		res += "\n";
		return res;
	}
	
	public Vector copy(int start,int size){
		Vector result = new Vector(size);
		for (int i = 0; i < result.size(); i++) {
			result.array[i] = this.array[i+start];
		}
		return result;
	}

}

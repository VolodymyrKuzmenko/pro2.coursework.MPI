import java.io.Serializable;
import java.util.ArrayList;


public class MessageBox implements Serializable {
	private ArrayList<Matrix> matrixs = new ArrayList<Matrix>(3);
	private ArrayList<Vector> vectors = new ArrayList<Vector>(3);
	private ArrayList<Integer> values = new ArrayList<>(2);
	
	public void addMatrix(Matrix matrix){
		matrixs.add(matrix);
		
	}
	
	public Matrix setMatrix(int key, Matrix matrix){
		return matrixs.set(key, matrix);
	}
	
	public void AddVector(Vector vector){
		vectors.add(vector);
	}
	
	public Vector setVector(int key, Vector vector){
		return vectors.set(key, vector);
	}
	
	public void AddValue(int value){
		values.add(value);
	}
	
	public int setValue(int key, int value){
		return values.set(key, value);
	}
	
	public Matrix getMatrix(int key){
		return matrixs.get(key);
	}
	public Vector getVector(int key){
		return vectors.get(key);
	}
	
	public int getValue(int key){
		return values.get(key);
	}
}

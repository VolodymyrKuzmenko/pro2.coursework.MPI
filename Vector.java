import java.io.Serializable;

/**
*****************************************************************
*                                                               *
*            Програмування паралельний комп'ютерних систем      *     
*             Курсова робота. ПРГ2. Бібліотека MPI              *
*                                                               *
* Завдання: A = B(MO*MK)*a + min(Z)*E*MR                        *
*                                                               *  
* Файл: Vector.java                                             *            
* Автор: Кузьменко Володимир                                    *
* Група: ІО-21                                                  *
* Дата: 23.04.15                                                *
*                                                               *
*****************************************************************
*/
public class Vector implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
		for (int i = 0; i < array.length; i++) {
			res += "   " + array[i];
		}
		res += "\n";
		return res;
	}

	public Vector copy(int start, int size) {
		Vector result = new Vector(size);
		for (int i = 0; i < result.size(); i++) {
			result.array[i] = this.array[i + start];
		}
		return result;
	}

	public void merge(Vector vector) {
		int[] buf = array;
		array = new int[buf.length + vector.size()];
		System.arraycopy(buf, 0, array, 0, buf.length);
		System.arraycopy(vector.array, 0, this.array, buf.length,
				vector.array.length);

	}

	public void reverse() {
		for (int i = 0; i < array.length / 2; i++) {
			int buf = array[i];
			array[i] = array[i + array.length / 2];
			array[array.length / 2 + i] = buf;

		}
	}

}

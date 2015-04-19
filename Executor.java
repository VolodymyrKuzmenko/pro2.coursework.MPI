import mpi.*;

public class Executor {
	public static int N = 12;
	public static int P;
	public static int H;
	public static int k;

	public static void main(String[] args) throws Exception {
		P = Integer.parseInt(args[1]);
		H = N / P;
		k = P / 2;
		MPI.Init(args);
		TaskPool pool = new TaskPool();
		pool.leftTaskGroup();
		
		pool.middleTaskGroup();
		pool.rightTaskGroup();
		MPI.Finalize();
	}

	public static void task1() {
		int[][] E0 = new int[N][N];
		//CalculateUtils.inputMatrix(E0, 1);
		MPI.COMM_WORLD.Send(E0, 0, N, MPI.OBJECT, 1, 99);
	}

	public static void task2() {
		int[][] E1 = new int[N][N];
		MPI.COMM_WORLD.Recv(E1, 0, N, MPI.OBJECT, 0, 99);
		for (int i = 0; i < E1.length; i++) {
			for (int j = 0; j < E1.length; j++) {
				System.out.print(E1[i][j]);
			}
			System.out.println();
		}
	}

}

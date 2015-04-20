import mpi.*;

public class Executor {
	public static int N = 3000;
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

	

}

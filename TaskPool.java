import mpi.MPI;

public class TaskPool {
	private int N = Executor.N;
	private int P = MPI.COMM_WORLD.Size();
	private int H = Executor.H;
	private int k = P / 2;
	private int rank;

	public TaskPool() {
		rank = MPI.COMM_WORLD.Rank();
	}

	public void leftTaskGroup() {

		if (rank == 0) {
			int[] B = new int[N];
			int[][] MK = new int[N][N];
			int[][] MR = new int[N][N];
			int[] alfa = new int[1];
			alfa[0] = 1;
			CalculateUtils.inputVector(B, 1);
			CalculateUtils.inputMatrix(MK, 1);
			CalculateUtils.inputMatrix(MR, 1);
			
			MPI.COMM_WORLD.Send(B, 0, N, MPI.INT, k, 101);
		//	MPI.COMM_WORLD.Send(alfa, 0, 1, MPI.INT, k, 102);
		//	MPI.COMM_WORLD.Send(copy(MK, k * H), 0, k * H, MPI.OBJECT, k, 103);
		//	MPI.COMM_WORLD.Send(copy(MR, k * H), 0, k * H, MPI.OBJECT, k, 104);
			
			
		//	MPI.COMM_WORLD.Send(copy(MR, k * H - H), 0, k * H - H, MPI.OBJECT,
		//			rank + 1, 105);
		//	MPI.COMM_WORLD.Send(copy(MK, k * H - H), 0, k * H - H, MPI.OBJECT,
		//		rank + 1, 106);
			System.out.println("Sent B from " + rank);
			MPI.COMM_WORLD.Send(B, 0, N, MPI.INT, rank + 1, 107);
		//	MPI.COMM_WORLD.Send(alfa, 0, 1, MPI.INT, rank + 1, 108);

		}
		if (rank == k) {
			
			int sizeResieve = k*H;
			
			int[] B = new int[N];
			int[][] MK = new int[sizeResieve][N];
			int[][] MR = new int[sizeResieve][N];
			int[] alfa = new int[1];
			alfa[0] = 1;
			System.out.println("Reciv B from " + rank);
			MPI.COMM_WORLD.Recv(B, 0, N, MPI.INT, 0, 101);
			//MPI.COMM_WORLD.Recv(alfa, 0, 1, MPI.INT, 0, 102);
		//	MPI.COMM_WORLD.Recv(MK, 0, sizeResieve, MPI.OBJECT, 0, 103);
		//	MPI.COMM_WORLD.Recv(MR, 0, sizeResieve, MPI.OBJECT, 0, 104);

			//MPI.COMM_WORLD.Send(copy(MR, k * H - H), 0, k * H - H, MPI.OBJECT,
			//		rank + 1, 105);
		//	MPI.COMM_WORLD.Send(copy(MK, k * H - H), 0, k * H - H, MPI.OBJECT,
		//			rank + 1, 106);
			System.out.println("Sent B from " + rank);
			MPI.COMM_WORLD.Send(B, 0, N, MPI.INT, rank + 1, 107);
		//	MPI.COMM_WORLD.Send(alfa, 0, 1, MPI.INT, rank + 1, 108);
		}

	}

	public void middleTaskGroup() {
		if (rank != 0 && rank != k && rank != k - 1 && rank != P - 1) {
			
			int sizeRecv;
			int sizeSend;
			int leftRank = rank - 1;
			int rightRank = rank + 1;
			if (rank < k) {
				sizeRecv = (k - rank) * H;
				sizeSend = (k - rank - 1) * H;
			} else {
				// rank > k
				sizeRecv = (rank - k) * H;
				sizeSend = (rank - k - 1) * H;
			}

			int[] B = new int[N];
			int[][] MK = new int[sizeRecv][N];
			int[][] MR = new int[sizeRecv][N];
			int[] alfa = new int[1];
			alfa[0] = 1;

			//MPI.COMM_WORLD.Recv(MR, 0, sizeRecv, MPI.OBJECT, leftRank, 105);
			//MPI.COMM_WORLD.Recv(MK, 0, sizeRecv, MPI.OBJECT, leftRank, 106);
			System.out.println("Recive B in" + rank);
			MPI.COMM_WORLD.Recv(B, 0, N, MPI.INT, leftRank, 107);
			//MPI.COMM_WORLD.Recv(alfa, 0, 1, MPI.INT, leftRank, 108);

			//MPI.COMM_WORLD.Send(copy(MR, sizeSend), 0, sizeSend, MPI.OBJECT,
			//		rightRank, 105);
		//	MPI.COMM_WORLD.Send(copy(MK, sizeSend), 0, sizeSend, MPI.OBJECT,
		//			rightRank, 106);
			System.out.println("Sent B from " + rank);
			MPI.COMM_WORLD.Send(B, 0, N, MPI.INT, rightRank, 107);
		//	MPI.COMM_WORLD.Send(alfa, 0, 1, MPI.INT, rightRank, 108);
		}
	}

	public void rightTaskGroup() {
		int sizeRecv = H;
		int leftRank = rank - 1;
		
		
		if(rank == k-1){
			int[] B = new int[N];
			int[][] MK = new int[sizeRecv][N];
			int[][] MR = new int[sizeRecv][N];
			int[] alfa = new int[1];
			alfa[0] = 1;
			
			
			//MPI.COMM_WORLD.Recv(MR, 0, sizeRecv, MPI.OBJECT, leftRank, 105);
			//MPI.COMM_WORLD.Recv(MK, 0, sizeRecv, MPI.OBJECT, leftRank, 106);
			System.out.println("Recive B in" + rank);
			MPI.COMM_WORLD.Recv(B, 0, N, MPI.INT, leftRank, 107);
			//MPI.COMM_WORLD.Recv(alfa, 0, 1, MPI.INT, leftRank, 108);

		}
		
		if (rank == P-1){
			int[] B = new int[N];
			int[][] MK = new int[sizeRecv][N];
			int[][] MR = new int[sizeRecv][N];
			int[] alfa = new int[1];
			alfa[0] = 1;
			//MPI.COMM_WORLD.Recv(MR, 0, sizeRecv, MPI.OBJECT, leftRank, 105);
		//	MPI.COMM_WORLD.Recv(MK, 0, sizeRecv, MPI.OBJECT, leftRank, 106);
			System.out.println("Recive B in" + rank);
			MPI.COMM_WORLD.Recv(B, 0, N, MPI.INT, leftRank, 107);
		//	MPI.COMM_WORLD.Recv(alfa, 0, 1, MPI.INT, leftRank, 108);
		}
	}

	public int[][] copy(final int[][] matrixFrom, int size) {
		int l = matrixFrom.length - size;
		int[][] result = new int[size][N];
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[i].length; j++) {

				result[i][j] = matrixFrom[i + l][j];
			}
		}
		return result;
	}

}

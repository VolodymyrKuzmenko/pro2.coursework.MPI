import mpi.MPI;

public class TaskPool {
	private int N = Executor.N;
	private int P = MPI.COMM_WORLD.Size();
	private int H = Executor.H;
	private int k = P / 2;
	private int rank;
	int leftRank;
	int rightRank;
	
	public TaskPool() {
		rank = MPI.COMM_WORLD.Rank();
		rightRank = rank+1;
		leftRank = rank -1;
	}

	public void leftTaskGroup() {

		if (rank == 0) {
			int[] B = new int[N];
			int[][] MK = new int[N][N];
			int[][] MR = new int[N][N];
			int[] alfa = new int[1];
			alfa[0] = 1;
			
			int [][] MO = new int [N][N];
			int [] E = new int [N];
			
			CalculateUtils.inputVector(B, 1);
			CalculateUtils.inputMatrix(MK, 1);
			CalculateUtils.inputMatrix(MR, 1);

			
			MPI.COMM_WORLD.Irsend(alfa, 0, 1, MPI.INT, k, 102);
			MPI.COMM_WORLD.Irsend(alfa, 0, 1, MPI.INT, rightRank, 108);
			
			MPI.COMM_WORLD.Irsend(B, 0, N, MPI.INT, k, 101);
			MPI.COMM_WORLD.Irsend(B, 0, N, MPI.INT, rightRank, 107);
		
			MPI.COMM_WORLD.Send(copy(MR, k * H), 0, k * H, MPI.OBJECT, k, 104);		
			MPI.COMM_WORLD.Send(copy(MR, k * H - H), 0, k * H - H, MPI.OBJECT,
					rightRank, 105);
			
			MPI.COMM_WORLD.Send(copy(MK, k * H), 0, k * H, MPI.OBJECT, k, 103);		
			MPI.COMM_WORLD.Send(copy(MK, k * H - H), 0, k * H - H, MPI.OBJECT,
					rightRank, 106);
			MPI.COMM_WORLD.Barrier();
			MPI.COMM_WORLD.Recv(MO, 0, N, MPI.OBJECT, rightRank, 115);
		//	MPI.COMM_WORLD.Recv(E, 0, N, MPI.INT, rightRank, 116);

		}
		if (rank == k) {
			
			int sizeResieve = k*H;
			
			int[] B = new int[N];
			int[][] MK = new int[sizeResieve][N];
			int[][] MR = new int[sizeResieve][N];
			int[] alfa = new int[1];
			alfa[0] = 1;
		
			int [][] MO = new int [N][N];
			int [] E = new int [N];
			
			MPI.COMM_WORLD.Irecv(alfa, 0, 1, MPI.INT, 0, 102);
			MPI.COMM_WORLD.Irsend(alfa, 0, 1, MPI.INT, rightRank, 108);

			MPI.COMM_WORLD.Irecv(B, 0, N, MPI.INT, 0, 101);
			MPI.COMM_WORLD.Irsend(B, 0, N, MPI.INT, rightRank, 107);
		
			MPI.COMM_WORLD.Recv(MR, 0, sizeResieve, MPI.OBJECT, 0, 104);
			MPI.COMM_WORLD.Send(copy(MR, k * H - H), 0, k * H - H, MPI.OBJECT,
					rightRank, 105);
			
			MPI.COMM_WORLD.Recv(MK, 0, sizeResieve, MPI.OBJECT, 0, 103);
			MPI.COMM_WORLD.Send(copy(MK, k * H - H), 0, k * H - H, MPI.OBJECT,
					rightRank, 106);
			MPI.COMM_WORLD.Barrier();
			MPI.COMM_WORLD.Recv(MO, 0, N, MPI.OBJECT, rightRank, 115);
			//MPI.COMM_WORLD.Recv(E, 0, N, MPI.INT, rightRank, 115);
			
		}

	}

	public void middleTaskGroup() {
		if (rank != 0 && rank != k && rank != k - 1 && rank != P - 1) {
			
			int sizeRecv;
			int sizeSend;

			if (rank < k) {
				sizeRecv = (k - rank) * H;
				sizeSend = (k - rank - 1) * H;
			} else {
				// rank > k
				sizeRecv = (rank - k+1) * H;
				sizeSend = (rank - k - 1) * H;
			}

			int[] B = new int[N];
			int[][] MK = new int[sizeRecv][N];
			int[][] MR = new int[sizeRecv][N];
			int[] alfa = new int[1];
			alfa[0] = 1;

			int [][]MO = new int [N][N];
			int [] E = new int [N];
			
			MPI.COMM_WORLD.Irecv(alfa, 0, 1, MPI.INT, leftRank, 108);
			MPI.COMM_WORLD.Irsend(alfa, 0, 1, MPI.INT, rightRank, 108);
			
			MPI.COMM_WORLD.Irecv(B, 0, N, MPI.INT, leftRank, 107);
			MPI.COMM_WORLD.Irsend(B, 0, N, MPI.INT, rightRank, 107);
			
			MPI.COMM_WORLD.Recv(MR, 0, sizeRecv, MPI.OBJECT, leftRank, 105);
			MPI.COMM_WORLD.Send(copy(MR, sizeSend), 0, sizeSend, MPI.OBJECT,
					rightRank, 105);
	
			MPI.COMM_WORLD.Recv(MK, 0, sizeRecv, MPI.OBJECT, leftRank, 106);
			MPI.COMM_WORLD.Send(copy(MK, sizeSend), 0, sizeSend, MPI.OBJECT,
					rightRank, 106);
			MPI.COMM_WORLD.Barrier();
			MPI.COMM_WORLD.Recv(MO, 0, N, MPI.OBJECT, rightRank, 115);
			//MPI.COMM_WORLD.Recv(E, 0, N, MPI.INT, rightRank, 116);
			
			MPI.COMM_WORLD.Send(MO, 0, N, MPI.OBJECT, leftRank, 115);
			//MPI.COMM_WORLD.Send(E, 0, N, MPI.INT, leftRank, 116);
			
			
			
			
			
		
			
		}
	}

	public void rightTaskGroup() {
		int sizeRecv = H;
		if(rank == k-1){
			int[] B = new int[N];
			int[][] MK = new int[sizeRecv][N];
			int[][] MR = new int[sizeRecv][N];
			int[] alfa = new int[1];
			alfa[0] = 1;
			
			int [] Z = new int [N];
			int [] E = new int [N];
			int [][] MO = new int [N][N];
			
			
			CalculateUtils.inputVector(E, 1);
			CalculateUtils.inputVector(Z, 1);
			CalculateUtils.inputMatrix(MO, 1);
			
			MPI.COMM_WORLD.Irsend(alfa, 0, 1, MPI.INT, leftRank, 108);
			MPI.COMM_WORLD.Irecv(B, 0, N, MPI.INT, leftRank, 107);
			MPI.COMM_WORLD.Recv(MR, 0, sizeRecv, MPI.OBJECT, leftRank, 105);
			MPI.COMM_WORLD.Recv(MK, 0, sizeRecv, MPI.OBJECT, leftRank, 106);
			
			MPI.COMM_WORLD.Barrier();
			
			MPI.COMM_WORLD.Send(MO, 0, N, MPI.OBJECT, P-1, 110);
			MPI.COMM_WORLD.Send(MO, 0, N, MPI.OBJECT, leftRank, 115);
			MPI.COMM_WORLD.Send(E, 0, N, MPI.INT, P-1, 111);
			//MPI.COMM_WORLD.Send(E, 0, N, MPI.INT, leftRank, 116);
		}
		
		if (rank == P-1){
			int[] B = new int[N];
			int[][] MK = new int[sizeRecv][N];
			int[][] MR = new int[sizeRecv][N];
			int[] alfa = new int[1];
			alfa[0] = 1;
		
			int [][] MO = new int [N][N];
			int [] E = new int [N];
			
			
			MPI.COMM_WORLD.Irsend(alfa, 0, 1, MPI.INT, leftRank, 108);
			MPI.COMM_WORLD.Irecv(B, 0, N, MPI.INT, leftRank, 107);
			MPI.COMM_WORLD.Recv(MR, 0, sizeRecv, MPI.OBJECT, leftRank, 105);
			MPI.COMM_WORLD.Recv(MK, 0, sizeRecv, MPI.OBJECT, leftRank, 106);
			
			MPI.COMM_WORLD.Barrier();
			MPI.COMM_WORLD.Recv(MO, 0, N, MPI.OBJECT, k-1, 110);
			MPI.COMM_WORLD.Recv(E, 0, N, MPI.INT, k-1, 111);
			
			MPI.COMM_WORLD.Send(MO, 0, N, MPI.OBJECT, leftRank, 115);
		//	MPI.COMM_WORLD.Send(E, 0, N, MPI.INT, leftRank, 116);
		
		
			
			
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

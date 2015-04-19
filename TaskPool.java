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

			Vector B, E, Z;
			Matrix MK, MR, MO;
			int alfa=1;
		
			
			B = CalculateUtils.inputVector(1);
			MR = CalculateUtils.inputMatrix(1);
			MK = CalculateUtils.inputMatrix(1);
			
			MessageBox[] box = new MessageBox [1];
			
			MessageBox message = new MessageBox();
			message.AddValue(alfa);
			message.AddVector(B);
			message.addMatrix(MK.copy(MK.size()/2, MK.size()/2));
			message.addMatrix(MR.copy(MR.size()/2, MR.size()/2));
			
			box[0] = message;
			MPI.COMM_WORLD.Send(box, 0, 1, MPI.OBJECT, k, 101);
			
			message.setMatrix(0,MK.copy(H,MR.size()/2-H));
			message.setMatrix(1,MR.copy(H,MR.size()/2-H));
			
			MPI.COMM_WORLD.Send(box, 0, 1, MPI.OBJECT, rightRank, 105);
			
			/*
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
		 * 
		 */

		}
		if (rank == k) {
			
			int sizeResieve = k*H;
			
			Vector B, E, Z;
			Matrix MK, MR, MO;
			int alfa;
			
			MessageBox [] box = new MessageBox [1];
			
			MPI.COMM_WORLD.Recv(box, 0, 1, MPI.OBJECT, 0, 101);
			B = box[0].getVector(0);
			MK = box[0].getMatrix(0);
			MR = box[0].getMatrix(1);
			alfa = box[0].getValue(0);
			
			
			box[0].setMatrix(0,MK.copy(H, MK.size()-H-1));
			box[0].setMatrix(1,MR.copy(H, MR.size()-H-1));
			
			
			MPI.COMM_WORLD.Send(box, 0, 1, MPI.OBJECT, rightRank, 105);
			
			/*
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
			 * 
			 */
			
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
			Vector B, E, Z;
			Matrix MK, MR, MO;
			int alfa;
			
			
			MessageBox [] box = new MessageBox [1];
			
			MPI.COMM_WORLD.Recv(box, 0, 1, MPI.OBJECT, leftRank, 105);
			
			B = box[0].getVector(0);
			MK = box[0].getMatrix(0);
			MR = box[0].getMatrix(1);
			alfa = box[0].getValue(0);
			
		
			box[0].setMatrix(0,MK.copy(H, MK.size()-H-1));
			box[0].setMatrix(1,MR.copy(H, MR.size()-H-1));
	
			
			
			MPI.COMM_WORLD.Send(box, 0, 1, MPI.OBJECT, rightRank, 105);
			
			
			/*
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
			 * 
			 */
			
			
			
			
			
		
			
		}
	}

	public void rightTaskGroup() {
		int sizeRecv = H;
		if(rank == k-1){
			Vector B, E, Z;
			Matrix MK, MR, MO;
			int alfa;
			
			E = CalculateUtils.inputVector(1);
			Z = CalculateUtils.inputVector(1);
			MO = CalculateUtils.inputMatrix(1);
			
			MessageBox [] box = new MessageBox [1];
			MessageBox message = new MessageBox();
			message.addMatrix(MO);
			message.AddVector(E);
			message.AddVector(Z.copy(k*H, Z.size()/2));
			box[0]=message;
			MPI.COMM_WORLD.Send(box, 0, 1, MPI.OBJECT, P-1, 102);
			
			
			
			box[0] = null;
			MPI.COMM_WORLD.Recv(box, 0, 1, MPI.OBJECT, leftRank, 105);
			
			B = box[0].getVector(0);
			MK = box[0].getMatrix(0);
			MR = box[0].getMatrix(1);
			alfa = box[0].getValue(0);
			
			/*
			MPI.COMM_WORLD.Irsend(alfa, 0, 1, MPI.INT, leftRank, 108);
			MPI.COMM_WORLD.Irecv(B, 0, N, MPI.INT, leftRank, 107);
			MPI.COMM_WORLD.Recv(MR, 0, sizeRecv, MPI.OBJECT, leftRank, 105);
			MPI.COMM_WORLD.Recv(MK, 0, sizeRecv, MPI.OBJECT, leftRank, 106);
			
			MPI.COMM_WORLD.Barrier();
			
			MPI.COMM_WORLD.Send(MO, 0, N, MPI.OBJECT, P-1, 110);
			MPI.COMM_WORLD.Send(MO, 0, N, MPI.OBJECT, leftRank, 115);
			MPI.COMM_WORLD.Send(E, 0, N, MPI.INT, P-1, 111);
			//MPI.COMM_WORLD.Send(E, 0, N, MPI.INT, leftRank, 116);
			 */
		}
		
		if (rank == P-1){
			Vector B, E, Z;
			Matrix MK, MR, MO;
			int alfa;
			
			
			MessageBox [] box = new MessageBox [1];
			
			MPI.COMM_WORLD.Recv(box, 0, 1, MPI.OBJECT, k-1, 102);
			MO = box[0].getMatrix(0);
			E = box[0].getVector(0);
			Z = box[0].getVector(1);
			box[0] = null;
			MPI.COMM_WORLD.Recv(box, 0, 1, MPI.OBJECT, leftRank, 105);
			
			B = box[0].getVector(0);
			MK = box[0].getMatrix(0);
			MR = box[0].getMatrix(1);
			alfa = box[0].getValue(0);
			
			/*
			MPI.COMM_WORLD.Irsend(alfa, 0, 1, MPI.INT, leftRank, 108);
			MPI.COMM_WORLD.Irecv(B, 0, N, MPI.INT, leftRank, 107);
			MPI.COMM_WORLD.Recv(MR, 0, sizeRecv, MPI.OBJECT, leftRank, 105);
			MPI.COMM_WORLD.Recv(MK, 0, sizeRecv, MPI.OBJECT, leftRank, 106);
			
			MPI.COMM_WORLD.Barrier();
			MPI.COMM_WORLD.Recv(MO, 0, N, MPI.OBJECT, k-1, 110);
			MPI.COMM_WORLD.Recv(E, 0, N, MPI.INT, k-1, 111);
			
			MPI.COMM_WORLD.Send(MO, 0, N, MPI.OBJECT, leftRank, 115);
		//	MPI.COMM_WORLD.Send(E, 0, N, MPI.INT, leftRank, 116);
		 * 
		 */
		
		
			
			
		}
	}


}

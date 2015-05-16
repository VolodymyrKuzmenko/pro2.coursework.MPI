import mpi.*;
/**
*****************************************************************
*                                                               *
*            Програмування паралельний комп'ютерних систем      *     
*             Курсова робота. ПРГ2. Бібліотека MPI              *
*                                                               *
* Завдання: A = B(MO*MK)*a + min(Z)*E*MR                        *
*                                                               *  
* Файл: Executor.java                                           *            
* Автор: Кузьменко Володимир                                    *
* Група: ІО-21                                                  *
* Дата: 23.04.15                                                *
*                                                               *
*****************************************************************
*/


public class Executor {
	public static int N;
	public static int P;
	public static int H;
	public static int k;

	public static void main(String[] args) throws Exception {
		MPI.Init(args);
		System.out.println("Task "+MPI.COMM_WORLD.Rank()+" started");
		P = Integer.parseInt(args[1]);
		N = Integer.parseInt(args[3]);
		H = N / P;
		k = P / 2;
		long  start;
		long []timework = new long[1];
		long [] buf = new long[1];
		TaskPool pool = new TaskPool();
		start = System.currentTimeMillis();
		if (MPI.COMM_WORLD.Size()==1){
			pool.singleThreadTask();
		}else{
			pool.leftTaskGroup();
			pool.middleTaskGroup();
			pool.rightTaskGroup();			
		}
		buf[0] = System.currentTimeMillis() - start; 
		//System.out.print("Task "+MPI.COMM_WORLD.Rank()+" finished, time: ");
		//System.out.println(buf[0]);
		//System.out.println();
		MPI.COMM_WORLD.Reduce(buf,0,timework,0,1,MPI.LONG,MPI.MAX,0);
		System.out.println("Task "+MPI.COMM_WORLD.Rank()+" finished");
		if(MPI.COMM_WORLD.Rank()==0){
			System.out.println("Time work: "+timework[0]);
		}
		
		MPI.Finalize();
		
		
	}

	

}

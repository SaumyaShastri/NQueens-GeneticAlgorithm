package geneticAlgorithm;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class NQueens {
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n = 8;
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter n");
		n = scan.nextInt();
		GeneticAlgorithm genetic = new GeneticAlgorithm(n);
		GeneticAlgorithm2 genetic2 = new GeneticAlgorithm2(n);

		Thread newThread = new Thread(genetic);
		newThread.start();

		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future future = executor.submit(genetic);

		try {
			System.out.println("Started..");
			future.get(100, TimeUnit.SECONDS);
			System.out.println("Finished!");
		} catch (TimeoutException e) {
			future.cancel(true);
			ArrayList<States> myStates = genetic.stateList;
			int max = 0;
			int position = 0;
			for (int i = 0; i < myStates.size(); i++) {
				States myState = myStates.get(i);
				if (myState.getFitnessFunction() > max) {
					max = myState.getFitnessFunction();
					position = i;
				}}
					ArrayList<States> myStates2 = genetic2.stateList;
					int max2 = 0;
					int position2 = 0;
					for (int j = 0; j < myStates2.size(); j++) {
						States myState2 = myStates2.get(j);
						if (myState2.getFitnessFunction() > max2) {
							max2 = myState2.getFitnessFunction();
							position2 = j;
				}}
			
			System.out.println("After Timeout the best possible configuration is "
					+ Arrays.toString(myStates.get(position).getConfiguration()));

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		executor.shutdownNow();
	}

	private static Scanner Scanner(InputStream in) {
		// TODO Auto-generated method stub
		return null;
	}

}

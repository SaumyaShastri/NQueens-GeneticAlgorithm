package geneticAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GeneticAlgorithm implements Runnable {

	private int sizeOfChessboard;
	Random random;
	ArrayList<States> stateList = new ArrayList<>();
	int kRandomStates;
	int[] solutionConfiguration;
	int epoch;

	public GeneticAlgorithm(int size) {
		this.sizeOfChessboard = size;
		random = new Random();
	}

	@Override
	public void run() {
		InitialGenerateKRandomStates();
		while(!calculateFitnessFunctionAndProbability() && !selectionAndCrossOver()){
			mutation();
			epoch++;
		}
		System.out.println(Arrays.toString(solutionConfiguration));
		System.out.println("fit =" +epoch);
		
	}

	public void InitialGenerateKRandomStates() {
		kRandomStates = random.nextInt(10)+100;
		for (int i = 0; i < kRandomStates; i++) {
			States myState = new States(sizeOfChessboard);
			int[] configuration = new int[sizeOfChessboard];
			for (int j = 0; j < sizeOfChessboard; j++) {
				configuration[j] = random.nextInt(sizeOfChessboard) + 1;
			}
			myState.setState(configuration);
			stateList.add(myState);
		}
		//System.out.println("Successfully Generated Random States " + kRandomStates);
	
	}
	
	public boolean calculateFitnessFunctionAndProbability(){
		int count = 0;
		for(States myState : stateList){
			count = count + myState.calculateFitnessFunction();
			if(myState.getFitnessFunction()==((sizeOfChessboard*(sizeOfChessboard-1))/2)){
				solutionConfiguration = myState.getConfiguration();
				return true;
			}
		}
		
		for(States myState : stateList){
			int percent = (myState.getFitnessFunction()*100/count);
			//System.out.print("Percent is " + percent + " ");
			myState.setPercentOfFitness(percent);
		}
		//System.out.println("");
		//System.out.println("Successfully evaluated fitness and Probability " + count  + " and size is " + stateList.size());
		return false;
		
	}
	
	public boolean selectionAndCrossOver(){
		int randomThreshold = random.nextInt(1);
		ArrayList<States> selectionThreshold = new ArrayList<>();
		for(States myState : stateList){
			if(myState.getPercentOfFitness()>=randomThreshold){
				selectionThreshold.add(myState);
			}
		}
		
		//System.out.println("The size of the List for selection is " + selectionThreshold.size());
		if(selectionThreshold.size()==0){
			int max = 0;
			int position = 0;
			for (int i = 0; i < stateList.size(); i++) {
				States myState = stateList.get(i);
				if (myState.getFitnessFunction() > max) {
					max = myState.getFitnessFunction();
					position = i;
				}
			}
			solutionConfiguration = stateList.get(position).getConfiguration();
			//System.out.println("The best possible configuration is ");
			return true;
		}
		
		stateList.clear();
		stateList.addAll(selectionThreshold);
		selectionThreshold.clear();
		
		for(int i=0;i<kRandomStates;i++){
			int index1 = random.nextInt(stateList.size());
			int index2 = random.nextInt(stateList.size());
			int crossOver = random.nextInt(sizeOfChessboard);
			
			int[] newConfiguration = new int[sizeOfChessboard];
			int[] parentMale = stateList.get(index1).getConfiguration();
			int[] parentFemale = stateList.get(index2).getConfiguration();
			for(int j=0;j<crossOver;j++){
				newConfiguration[j] = parentMale[j];
			}
			for(int j=crossOver;j<sizeOfChessboard;j++){
				newConfiguration[j] = parentFemale[j];
			}
			States newState = new States(sizeOfChessboard);
			newState.setState(newConfiguration);
			if(newState.calculateFitnessFunction()==((sizeOfChessboard*(sizeOfChessboard-1))/2)){
				solutionConfiguration = newState.getConfiguration();
				return true;
			}
			selectionThreshold.add(newState);
		}
		
		stateList.clear();
		stateList.addAll(selectionThreshold);
		return false;
	}
	
	public void mutation(){
		int randomProbabilityThreshold = 25;
		for(States myState : stateList){
			int generatedProbability = random.nextInt(100);
			if(generatedProbability>randomProbabilityThreshold){
				int index = random.nextInt(sizeOfChessboard);
				int value = random.nextInt(sizeOfChessboard)+1;
				int[] configuration = myState.getConfiguration();
				configuration[index] = value;
				myState.setState(configuration);
			}
		}
	}
	
	

}

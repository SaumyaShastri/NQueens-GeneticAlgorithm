package geneticAlgorithm;

public class States {

	private int[] queens;
	private int fitnessFunction;
	private int size;
	private int percentOfFitness;
	
	public States(int size){
		this.queens = new int[size];
		this.size = size;
	}
	
	public void setState(int[] randomlyGeneratedState){
		this.queens = randomlyGeneratedState;
	}
	
	public int[] getConfiguration(){
		return this.queens;
	}
	public int getPercentOfFitness(){
		return percentOfFitness;
	}
	
	public void setPercentOfFitness(int percent) {
		this.percentOfFitness = percent;
	}
	
	public int calculateFitnessFunction(){
		fitnessFunction = 0;
		for(int i=0;i<size;i++){
			for(int j=i+1;j<size;j++){
				if((queens[i]==queens[j])||isSlope45(i, queens[i], j, queens[j])){
					
				}
				else{
					fitnessFunction++;
				}
			}
		}
		return fitnessFunction;
	}
	
	public int getFitnessFunction(){
		return this.fitnessFunction;
	}
	
	public boolean isSlope45(int x1,int y1, int x2, int y2){
		if(x2-x1==0){
			return false;
		}
		else{
			if((y2-y1)==(x2-x1) || (y2-y1)==(x1-x2)){
				return true;
			}
			return false;
		}
	}
	
}

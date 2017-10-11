public class ManhattanHeuristic implements NodeHeuristic {


	private static ManhattanHeuristic instance = null;
	
	private ManhattanHeuristic() {
		
	}


	public static NodeHeuristic getInstance() {

		if (instance == null){
			instance = new ManhattanHeuristic();
		}
		
		return instance;
	}


	@Override
	public int getHeuristicCost(int S, int L, int Ma, int Mb) {

		return  S + Ma + Mb + L;
	}

}

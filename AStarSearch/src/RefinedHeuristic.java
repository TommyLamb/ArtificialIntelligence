public class RefinedHeuristic implements NodeHeuristic {


	private static RefinedHeuristic instance = null;
	
	private RefinedHeuristic() {
		
	}

	
	public static RefinedHeuristic getInstance(){
		
		if (instance == null){
			instance = new RefinedHeuristic();
		}
		
		return instance;
	}

	@Override
	public int getHeuristicCost(int S, int L, int Ma, int Mb) {

		int x = (2*(Math.min(Ma, S)))+(2*(Math.min(Mb, L)));
		int a = Math.max(Ma - S, S - Ma);
		int b = Math.max(Mb-L, L-Mb);
		
		if (((Ma - S)>0 && (L-Mb)>0) || ((Mb-L)>0 && (S-Ma)>0)){
			return x + 2* (a + b) - Math.min(a, b) -1;
			
		} else {
			return x+ (2 * (a+b)) -1;
		}
	}

}

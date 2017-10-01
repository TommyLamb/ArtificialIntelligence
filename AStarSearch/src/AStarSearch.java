


public class AStarSearch {

	
	public static void main(int arg, String[] args){
		AStarSearchAlgorithm search = new AStarSearchAlgorithm(new Node(1, 0, 0, 0, RobotLocation.TRUCK, 0, null));
		search.startSearch();
	}
}

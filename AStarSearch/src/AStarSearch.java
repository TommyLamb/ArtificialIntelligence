public class AStarSearch {
	public static void main(String[] args){
		AStarSearchAlgorithm search = new AStarSearchAlgorithm(new Node(1, 1, 1, 1, RobotLocation.TRUCK, 0, null));
		search.startSearch();
	}
}

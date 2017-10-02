public class AStarSearch {


	public static void main(String[] args) {

		AStarSearchAlgorithm search = new AStarSearchAlgorithm(new Node(0, 1, 0, 1, RobotLocation.TRUCK, 0, null));
		search.startSearch();

	}
}

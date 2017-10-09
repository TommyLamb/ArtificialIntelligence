public class AStarSearch {

	
	public static void main(String[] args){
		
		Node root = new Node(5, 0, 0, 0, RobotLocation.TRUCK, 0, null);
		
		System.out.println("Hue: " + root.calculateHeuristic());
		
		AStarSearchAlgorithm search = new AStarSearchAlgorithm(root);
		search.startSearch();

	}
}

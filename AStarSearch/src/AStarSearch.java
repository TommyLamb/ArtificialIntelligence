import java.util.Stack;

public class AStarSearch {


	public static void main(String[] args) {

		NodeHeuristic hue = RefinedHeuristic.getInstance();
		Node initialState = new Node(10, 10, 100, 20, RobotLocation.WAREHOUSEa, 0, null, hue);

		
		executeSearch(initialState);

	}
	
	private static void executeSearch(Node initialState){
		
		final long startTime = System.currentTimeMillis();

		AStarSearchAlgorithm search = new AStarSearchAlgorithm(initialState);
		Stack<Node> path = new Stack<Node>();
		

		try {
			path = search.search();
		} catch (Exception e) {
			System.err.println("Goal State unreachable!");
			System.exit(0);
		}


		final long duration = System.currentTimeMillis() - startTime;
		
		printPath(path, duration);
	}
	
	
	private static void printPath(Stack<Node> stack, long duration){
		
		StatePrettyPrinter prettyPrinter = new StatePrettyPrinter();
		
		
		Node currNode = stack.pop(); //RootNode
		int predictedCost = currNode.getHeuristicCost();
		
		prettyPrinter.printState(currNode);
		
		while(!stack.isEmpty()){
			prettyPrinter.printSeparator();
			prettyPrinter.printStateTransition(currNode, stack.peek());
			currNode = stack.pop();
		}
		
		//CurrNode is goal state

		System.out.println("Execution time: " + duration + "ms");
		System.out.println("Error in heuristic (Heuristic - Path cost): " + (predictedCost - currNode.getPathCost()));
	}
}

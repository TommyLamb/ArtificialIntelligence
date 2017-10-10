import java.util.Stack;

public class AStarSearch {


	public static void main(String[] args) {

		
		Node initialState = new Node(5, 0, 5, 5, RobotLocation.WAREHOUSEa, 0, null);
		NodeHeuristic hue = NodeHeuristic.REFINED;
		
		
		executeSearch(initialState, hue);

	}
	
	private static void executeSearch(Node initialState, NodeHeuristic hue){
		
		final long startTime = System.currentTimeMillis();

		AStarSearchAlgorithm search = new AStarSearchAlgorithm(initialState, hue);
		Stack<Node> path = new Stack<Node>();
		

		try {
			path = search.search();
		} catch (Exception e) {
			System.err.println("Goal State unreachable!");
			System.exit(0);
		}


		final long duration = System.currentTimeMillis() - startTime;
		
		printPath(path, duration, hue);
	}
	
	
	private static void printPath(Stack<Node> stack, long duration, NodeHeuristic hue){
		
		StatePrettyPrinter prettyPrinter = new StatePrettyPrinter();
		
		
		Node currNode = stack.pop(); //RootNode
		int predictedCost = currNode.getHeuristicCost(hue);
		
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

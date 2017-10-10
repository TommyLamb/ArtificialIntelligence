import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;


public class AStarSearchAlgorithm {

	private PriorityQueue<Node> priorityQueue;

	/**
	 * Initialises a new A* Search from the given initial state using the specified heuristic
	 * 
	 * @param initialState A <code>Node</code> representing the initial state
	 * @param hue The heuristic to use in cost estimations. One of the <code>NodeHeuristic</code> enums
	 */
	public AStarSearchAlgorithm(Node initialState, NodeHeuristic hue) {
		
		priorityQueue = new PriorityQueue<>(new Comparator<Node>(){

			@Override
			public int compare(Node arg0, Node arg1) {

				return arg0.getHeuristicCost(hue) - arg1.getHeuristicCost(hue);
			}
			
		});
		
		priorityQueue.add(initialState);
	}

	/**
	 * Determines if the given node represents the goal state.
	 * 
	 * @param goalCandidate The <code>Node</code> potentially representing the goal state.
	 * @return	<strong>boolean</strong> Whether or not the node is the goal state.
	 */
	private boolean areWeThereYet(Node goalCandidate) {

		return goalCandidate.getSumOfState() == 0;
	}

	/**
	 * Returns a stack of nodes representing the path from the root node (initial state) to the given node.
	 * 
	 * @param goalNode The <code>Node</code> to construct the path to. Typically the goal node.
	 * @return The path as a <strong>Stack&lt;Node&gt;</strong>
	 */
	private Stack<Node> IWantToGoHome(Node goalNode) {

		Node currNode = goalNode;
		Stack<Node> stack = new Stack<>();

		
		do {
			stack.push(currNode);
			currNode = currNode.getParentNode();
		} while (currNode != null);
			
		return stack;
	}


	/**
	 * Searches for a path from the initial state specified in the constructor to the goal state.
	 * 
	 * @return The path as a <strong>Stack&lt;Node&gt;</strong>
	 * @throws Exception If no path exists
	 */
	public Stack<Node> search() throws Exception {

		Node currNode;

		while (!priorityQueue.isEmpty()) {
			currNode = priorityQueue.remove();
			if (areWeThereYet(currNode)) {
				return IWantToGoHome(currNode);
			} else
				priorityQueue.addAll(currNode.generateChildren());
		}
		
		throw new Exception();

	}

}

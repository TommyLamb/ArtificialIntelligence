import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;


public class AStarSearchAlgorithm {

	private HashMap<Integer, Node> visitedNodes = new HashMap<>();
	private PriorityQueue<Node> priorityQueue = new PriorityQueue<>(new Comparator<Node>(){

		@Override
		public int compare(Node arg0, Node arg1) {

			return arg0.getTotalCost() - arg1.getTotalCost();
		}
		
	});

	/**
	 * Initialises a new A* Search from the given initial state
	 * 
	 * @param initialState A <code>Node</code> representing the initial state
	 */
	public AStarSearchAlgorithm(Node initialState) {
		
		ArrayList<Node> list = new ArrayList<>();
		
		list.add(initialState);
		
		addToQueue(list);
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
	 * This method adds the list of nodes to the priority queue so long as they represent an unevaluated state. More specifically, 
	 * it will not add those nodes whose state matches one in the <code>visitedNodes</code> hash map.
	 * 
	 * @param nodes The <strong>ArrayList&lt;Node&gt;</strong> to add
	 */
	private void addToQueue(ArrayList<Node> nodes){
		
		for (Node x : nodes)
			if (!visitedNodes.containsKey(x.hashCode()))
				priorityQueue.add(x);

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
			if (areWeThereYet(currNode)) 
				return IWantToGoHome(currNode);
			else if (!visitedNodes.containsKey(currNode.hashCode())){ 	//If current state never before visited
				visitedNodes.put(currNode.hashCode(), currNode);	//List it as visited
				addToQueue(currNode.generateChildren());			//Add appropriate children to queue
			}
				
		}
		
		throw new Exception();

	}

}

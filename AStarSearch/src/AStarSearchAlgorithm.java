import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;


public class AStarSearchAlgorithm {


	private class NodeComparator implements Comparator<Node> {


		@Override
		public int compare(Node arg0, Node arg1) {

			return arg0.getTotalCost() - arg1.getTotalCost();
		}

	}

	private PriorityQueue<Node> priorityQueue = new PriorityQueue<>(new NodeComparator());
	private StatePrettyPrinter prettyPrinter = new StatePrettyPrinter();

	public AStarSearchAlgorithm(Node rootNode) {
		priorityQueue.add(rootNode);
	}


	private boolean areWeThereYet(Node goalCandidate) {

		return goalCandidate.getSumOfState() == 0;
	}


	/**
	 * 
	 * @param goalNode
	 * @return goal state path cost
	 */
	private int IWantToGoHome(Node goalNode) {
//
//		Node currNode = goalNode;
//		Stack<Node> stack = new Stack<>();
		
		return goalNode.getPathCost();
		
//		do {
//			stack.push(currNode);
//			currNode = currNode.getParentNode();
//		} while (currNode != null);
//		
//		
//		currNode = stack.pop(); //RootNode
//		prettyPrinter.printState(currNode);
//		
//		while(!stack.isEmpty()){
//			prettyPrinter.printSeparator();
//			prettyPrinter.printStateTransition(currNode, stack.peek());
//			currNode = stack.pop();
//		}
	}


	/**
	 * 
	 * @return The found path cost to goal state
	 * @throws Exception 
	 */
	public int startSearch() throws Exception {

		Node currNode;

		while (!priorityQueue.isEmpty()) {
			currNode = priorityQueue.remove();
			if (areWeThereYet(currNode)) {
				return IWantToGoHome(currNode);
				//break;
			} else
				priorityQueue.addAll(currNode.generateChildren());
		}
		
		throw new Exception();

	}

}

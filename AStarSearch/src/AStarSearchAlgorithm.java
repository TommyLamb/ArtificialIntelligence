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


	private void IWantToGoHome(Node goalNode) {

		Node currNode = goalNode;
		Stack<Node> stack = new Stack<>();
		
		System.out.println("Path: " + goalNode.getPathCost());
		
		do {
			stack.push(currNode);
			currNode = currNode.getParentNode();
		} while (currNode != null);
		
		
		currNode = stack.pop(); //RootNode
		prettyPrinter.printState(currNode);
		
		while(!stack.isEmpty()){
			prettyPrinter.printSeparator();
			prettyPrinter.printStateTransition(currNode, stack.peek());
			currNode = stack.pop();
		}
	}


	public void startSearch() {

		Node currNode;

		while (!priorityQueue.isEmpty()) {
			currNode = priorityQueue.remove();
			if (areWeThereYet(currNode)) {
				IWantToGoHome(currNode);
				break;
			} else
				priorityQueue.addAll(currNode.generateChildren());
		}

	}

}

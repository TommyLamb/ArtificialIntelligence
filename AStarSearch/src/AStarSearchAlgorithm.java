import java.util.Comparator;
import java.util.PriorityQueue;


public class AStarSearchAlgorithm {


	private class NodeComparator implements Comparator<Node> {


		@Override
		public int compare(Node arg0, Node arg1) {

			return arg0.getTotalCost() - arg1.getTotalCost();
		}

	}

	private PriorityQueue<Node> priorityQueue = new PriorityQueue<>(new NodeComparator());


	public AStarSearchAlgorithm(Node rootNode) {
		priorityQueue.add(rootNode);
	}


	private boolean areWeThereYet(Node goalCandidate) {

		return goalCandidate.getSumOfState() == 0;
	}


	private void IWantToGoHome(Node goalNode) {

		// TODO
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

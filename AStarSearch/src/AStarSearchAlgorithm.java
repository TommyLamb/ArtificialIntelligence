import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;


class AStarSearchAlgorithm {


	private class NodeComparator implements Comparator<Node> {


		@Override
		public int compare(Node arg0, Node arg1) {

			return arg0.getTotalCost() - arg1.getTotalCost();
		}

	}

	private PriorityQueue<Node> priorityQueue = new PriorityQueue<>(new NodeComparator());


	AStarSearchAlgorithm(Node rootNode) {
		priorityQueue.add(rootNode);
	}


	private boolean areWeThereYet(Node goalCandidate) {

		return goalCandidate.getSumOfState() == 0;
	}


	private void IWantToGoHome(Node goalNode) {

		Node currNode = goalNode;
		Stack<Node> stack = new Stack<>();
		Scanner wait = new Scanner(System.in);
		do {
			stack.push(currNode);
			currNode = currNode.getParentNode();
		} while (currNode != null);
		while(!stack.isEmpty()){
			currNode = stack.pop();
			System.out.format(
					"+-------------+\n" +
					"| Warehouse A |\n" +
					"+-------------+\n" +
					"| Med: %6d |\n" +
					"+-------------+----------+\n" +
					"|             |          |\n" +
					"|             |          |\n" +
					"|             |   +-------------+\n" +
					"+------+------+   |    Truck    |\n" +
					"       |          +-------------+\n" +
					"       |          | Sma: %6d |\n" +
					"       |          | Lrg: %6d |\n" +
					"       |          +-------------+\n" +
					"       |          |             |\n" +
					"       |          |             |\n" +
					"+------+------+   |             |\n" +
					"| Warehouse B |   +-------------+\n" +
					"+-------------+          |\n" +
					"| Med: %6d |          |\n" +
					"+-------------+--------- +\n" +
					"|             |\n" +
					"|             |\n" +
					"|             |\n" +
					"+-------------+", currNode.getMa(), currNode.getS(), currNode.getL(),currNode.getMb());
			wait.nextLine();
		}

	}


	void startSearch() {

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

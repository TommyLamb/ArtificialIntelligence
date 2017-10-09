public class AStarSearch {


	public static void main(String[] args) {

		final long startTime = System.currentTimeMillis();
		
		int maxUncertainty = 0;
		int sumUncertainty = 0;
		
		for (int S = 0; S < 5; S++) {
			for (int L = 0; L < 5; L++) {
				for (int ma = 0; ma < 5; ma++) {
					for (int mb = 0; mb < 5; mb++) {
						Node root = new Node(S, L, ma, mb, RobotLocation.WAREHOUSEa, 0, null);
						AStarSearchAlgorithm search = new AStarSearchAlgorithm(root);

						int hue = root.calculateHeuristic();
						int path = 0;

						try {
							path = search.startSearch();
						} catch (Exception e) {
							System.err.println("Goal State unreachable!");
							System.exit(0);
						}

						if (path < hue) {
							System.out.println("Hue overestimated:");
							System.exit(0);
						} else {
							sumUncertainty += hue-path;
							if ((hue - path)<maxUncertainty){
								maxUncertainty = hue-path;
							}
						}
					}
				}
			}
		}
		
		final long duration = System.currentTimeMillis() - startTime;
		
		
		System.out.println("Execution time: " + duration+"ms");
		System.out.println("MaxUncertainty " + maxUncertainty);
		System.out.println("Sum Uncertainty "+sumUncertainty);
	}
}

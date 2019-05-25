package astar;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.apache.commons.lang3.ArrayUtils;

/*
 * Class for the A* Search on the distribution of requests
 */
public class AStarSearch {
	
	public AStarSearch() {
		
	}
	
	
	// Creates the first node for the search algorithm
	static SearchNode createFirstNode(int[] allElevators, int[] allRequests) {
		SearchNode firstNode = new SearchNode(allElevators, allRequests);
		firstNode.gCost = 0;
		firstNode.finalCost = firstNode.hCost;
		
		return firstNode;
	}
	
	
	// Open and closed list for the A* algorithm
	// open: Priority queue with all requests sorted after f(request)=g(request)+h(request)
	// closed: List of evaluated nodes
	static PriorityQueue<SearchNode> open = new PriorityQueue<SearchNode>();
	static List<SearchNode> closed = new ArrayList<SearchNode>();
	static int countOpen = 0;
	
	
	public SearchNode Search(int[] allElevators, int[] allRequests) {
		
		// Creates first node and adds it to open list
		SearchNode firstNode = createFirstNode(allElevators, allRequests);
		open.add(firstNode);
		
		SearchNode currentNode;
		
		while(true) {
			currentNode = open.poll();
			if (ArrayUtils.isEmpty(currentNode.allRequests)) break;
			
			closed.add(currentNode);
			SearchNode temp;
			
			for (int i = 0; i <= currentNode.allRequests.length-1; i++) {
				for (int j = 0; j <= currentNode.allElevators.length-1; j++) {
						temp = new SearchNode(currentNode, j, i);
						if (!open.contains(temp)) {
							open.add(temp);
							countOpen += 1;
						}
				}
			}
		}
		
		return currentNode;
	}
	
		
	public List<SearchNode> makePath(SearchNode lastNode) {
		List<SearchNode> path = new ArrayList<SearchNode>();
		
		while (lastNode.parent != null) {
			//System.out.println(lastNode);
			path.add(lastNode);
			lastNode = lastNode.parent;
		}
		return path;
	}
	
	
}

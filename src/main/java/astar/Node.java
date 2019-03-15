package astar;

import astar.SendingOrder;

/*
 * Node class used for the A* algorithm containing information about
 * hCost: the heuristic cost
 * gCost: cost in terms of waiting time till pickup
 * finalCost: overall cost of the path up until that node
 * leftRequests: List with all requests that are left to be processed
 * order: associated SendCar order
 * parent: previous node of the path
 */
public class Node implements Comparable<Node>{

	int hCost;
	// keeping track of the accumulated g-costs for all elevators so far
	int[] accGCost;
	int finalCost;
	SendingOrder order;
	Node parent;
	
	public Node() {}
	
	public Node(Node parent, Request request, Elevator current, int hCost) {
		this.hCost = hCost;
		this.accGCost = parent.accGCost;
		this.accGCost[current.number] += Math.abs(current.floor - request.floor);
		this.finalCost = this.accGCost[current.number] + this.hCost;
		this.order = new SendingOrder(current.number, current.floor, request.floor);
		this.parent = parent;
	}
	
	@Override
	public String toString() {
		return this.order.toString();
	}
	
	@Override
	public int compareTo(Node node) {
		if (this.finalCost > node.finalCost) {
			return 1; 
		} else if (this.finalCost < node.finalCost) {
			return -1; 
		} else {
			return 0;
		}
	}
	
}

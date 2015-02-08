
public class DoubleChain {
	
	private DNode head;
	
	public DoubleChain(double val) {
		head = new DNode(val);
	}

	public DNode getFront() {
		DNode temp = head;
		while (temp.next != null) {
			temp = temp.next;
		}
		return temp;
	}

	/** Returns the last item in the DoubleChain. */		
	public DNode getBack() {
		DNode temp = head;
		while (temp.prev != null) {
			temp = temp.prev;
		}
		return temp;
	}
	
	/** Adds D to the front of the DoubleChain. */	
	public void insertFront(double d) {
		DNode temp = head;
		while (temp.next != null) {
			temp = temp.next;
		}
		temp.next = new DNode(temp, d, null);
	}
	
	/** Adds D to the back of the DoubleChain. */	
	public void insertBack(double d) {

		DNode temp = head;
		while (temp.prev != null) {
			temp = temp.prev;
		}
		temp.prev = new DNode(null, d, temp);
	}
	
	/** Removes the last item in the DoubleChain and returns it. 
	  * This is an extra challenge problem. */
	public DNode deleteBack() {
		if (head != null) {
			DNode rv = this.getBack();
			DNode temp = this.getBack().next;
			if (temp != null) {
				temp.prev = null;
			}
			head = temp;
			return rv;
		}
		return null;
	}
	
	/** Returns a string representation of the DoubleChain. 
	  * This is an extra challenge problem. */
	public String toString() {
		String returnObj = "";
		DNode temp = this.getBack();
		while (temp.next != null)	{
			returnObj = returnObj + temp.val + ", ";
			temp = temp.next;
		}

		returnObj = returnObj + this.getFront().val;
		return returnObj;
	}

	public static class DNode {
		public DNode prev;
		public DNode next;
		public double val;
		
		private DNode(double val) {
			this(null, val, null);
		}
		
		private DNode(DNode prev, double val, DNode next) {
			this.prev = prev;
			this.val = val;
			this.next =next;
		}
	}
	
}

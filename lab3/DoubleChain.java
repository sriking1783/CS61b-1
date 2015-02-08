
public class DoubleChain {
	
	private DNode head;
	
	public DoubleChain(double val) {
		head = new DNode(val);
	}

	public DNode getFront() {
		return head;
	}

	/** Returns the last item in the DoubleChain. */		
	public DNode getBack() {
		DNode temp = head;
		while (temp.next != null) {
			temp = temp.next;
		}
		return temp;
	}
	
	/** Adds D to the front of the DoubleChain. */	
	public void insertFront(double d) {
		DNode temp = head;
		temp.prev = new DNode(null, d, temp);
		head = temp.prev;
	}
	
	/** Adds D to the back of the DoubleChain. */	
	public void insertBack(double d) {
		DNode temp = head;
		while (temp.next != null) {
			temp = temp.next;
		}
		temp.next = new DNode(temp, d, null);
	}
	
	/** Removes the last item in the DoubleChain and returns it. 
	  * This is an extra challenge problem. */
	public DNode deleteBack() {
		if (head != null) {
			DNode rv = this.getBack();
			DNode temp = this.getBack().prev;
			if (temp != null) {
				temp.next = null;
			}
			head = temp;
			return rv;
		}
		return null;
	}
	
	/** Returns a string representation of the DoubleChain. 
	  * This is an extra challenge problem. */
	public String toString() {
		DNode temp = this.getBack();
		return this.toString(temp);
	}

	private String toString(DNode dChain) {
		if (dChain.prev == null) {
			return String.valueOf(dChain.val);
		}
		return dChain.val + ", " + this.toString(dChain.prev);
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


public class DoubleChain {
	
	private DNode head;
	
	public DoubleChain(double val) {
<<<<<<< HEAD
		head = new DNode(val);
	}

	public DNode getFront() {
		DNode temp = head;
		while (temp.next != null) {
			temp = temp.next;
		}
		return temp;
=======
		/* your code here. */
		head = null; 
	}

	public DNode getFront() {
		return head;
>>>>>>> 090c93dc4065bbe7fed1839581959c1e0f32df5f
	}

	/** Returns the last item in the DoubleChain. */		
	public DNode getBack() {
<<<<<<< HEAD
		DNode temp = head;
		while (temp.prev != null) {
			temp = temp.prev;
		}
		return temp;
=======
		/* your code here */
		return null;
>>>>>>> 090c93dc4065bbe7fed1839581959c1e0f32df5f
	}
	
	/** Adds D to the front of the DoubleChain. */	
	public void insertFront(double d) {
<<<<<<< HEAD
		DNode temp = head;
		while (temp.next != null) {
			temp = temp.next;
		}
		temp.next = new DNode(temp, d, null);
=======
		/* your code here */
>>>>>>> 090c93dc4065bbe7fed1839581959c1e0f32df5f
	}
	
	/** Adds D to the back of the DoubleChain. */	
	public void insertBack(double d) {
<<<<<<< HEAD
		DNode temp = head;
		while (temp.prev != null) {
			temp = temp.prev;
		}
		temp.prev = new DNode(null, d, temp);
=======
		/* your code here */
>>>>>>> 090c93dc4065bbe7fed1839581959c1e0f32df5f
	}
	
	/** Removes the last item in the DoubleChain and returns it. 
	  * This is an extra challenge problem. */
	public DNode deleteBack() {
<<<<<<< HEAD
		if (head != null) {
			DNode rv = this.getBack();
			DNode temp = this.getBack().next;
			if (temp != null) {
				temp.prev = null;
			}
			head = temp;
			return rv;
		}
=======
		/* your code here */
>>>>>>> 090c93dc4065bbe7fed1839581959c1e0f32df5f
		return null;
	}
	
	/** Returns a string representation of the DoubleChain. 
	  * This is an extra challenge problem. */
	public String toString() {
<<<<<<< HEAD
		String returnObj = "";
		DNode temp = this.getBack();
		while (temp.next != null)	{
			returnObj = returnObj + temp.val + ", ";
			temp = temp.next;
		}

		returnObj = returnObj + this.getFront().val;
		return returnObj;
=======
		/* your code here */		
		return null;
>>>>>>> 090c93dc4065bbe7fed1839581959c1e0f32df5f
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

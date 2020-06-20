/*
 * This is combined this and StackTest.java and to run, run it from StackTest.java.
 * by Shin Ikeda on 4 June 2020
 */

public class LinkedStack{
	Node head = null;

	public void showList() {
		showList(head);
	}

	public void showList(Node node) {
		if(node == null) {
			System.out.println();	
		} else {
			System.out.print(node.getElement() + " ");
			showList(node.getNext());
		}
	}

	public void push(String e) {

		head = new Node(e,head);

	}

	public String pop() {

		if (head == null) {
			return null;
		} else {
			// if() cannot use.
			String pop = head.getElement();
			head = head.getNext();
			return pop;
		}
	}
}

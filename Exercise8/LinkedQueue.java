public class LinkedQueue {
  Node head = null;
  Node tail = null;
  int size = 0;

  // to display a singly linked list
  public void showList() {
    showList(head);
  }

  public void showList(Node node) {
    if (node == null) {
      System.out.println("");
    } else {
      System.out.print(node.getElement() + " ");
      showList(node.getNext());
    }
  }

  public String dequeue() {
    if (size == 0) {
      return null;
    } else {
      String que = head.getElement();
      head = head.getNext();
      size -= 1;
      return que;
    }
  }

  public void enqueue(String e) {
    Node latest = new Node(e, null);
    if (size == 0) {
      head = latest;
    } else {
      tail.setNext(latest);
    }
    tail = latest;
    size += 1;
  }
}
public class Node {
  private String element;
  private Node next;

  // constructor of Node
  // e: element, n: next node (null if no next node)
  public Node(String e, Node n) {
  element = e;
  next = n;
  }

  // return element
  public String getElement() {
  return element;
  }

  // return next node
  Node getNext(){
  return next;
  }

  // set node to next
  public void setNext(Node n) {
    next = n;
  }
}
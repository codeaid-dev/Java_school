public class Exception3 {
  public Exception3() {
    try {
      methodA();
    } catch(NullPointerException e) {
      System.out.println("NullPointer error ! " + e.getMessage());
    } catch(ArithmeticException e) {
      System.out.println("Arithmetic error ! " + e.getMessage());
    }
  }

  void methodA() throws ArithmeticException {
    int x = 10 / 0;
  }

  public static void main(String[] args) {
    new Exception3();
  }
}
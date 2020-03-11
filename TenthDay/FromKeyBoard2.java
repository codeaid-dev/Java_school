import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FromKeyBoard2 {
  BufferedReader br;

  public FromKeyBoard2 () {
    int data;

    InputStreamReader isr = new InputStreamReader(System.in);
    br = new BufferedReader(isr);

    do {
      data = getData();
      if (data > 0) {
        System.out.println(data);
      } else {
        System.out.println("not integer or negative number");
      }
    } while (data >= 0);
    System.out.println("The system was terminated");
  }

  public int getData() {
    String kb;
    int data;

    try {
      System.out.print("Input: ");
      kb = br.readLine();
      data = Integer.parseInt(kb);
      return data;
    } catch (Exception e) {
      System.out.println(e);
      return 0;
    }
  }

  public static void main(String[] args) {
    new FromKeyBoard2();
  }
}
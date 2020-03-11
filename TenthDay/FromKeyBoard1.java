import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FromKeyBoard1 {
  String kb;

  public FromKeyBoard1() {
    try {
      InputStreamReader isr = new InputStreamReader(System.in);
      BufferedReader br = new BufferedReader(isr);

      System.out.print("Input: ");
      kb = br.readLine();
      while (!kb.equals("")) {
        System.out.println(kb);
        System.out.print("Input: ");
        kb = br.readLine();
      }
      System.out.println("end of input");
    } catch (IOException e) {
      System.out.println("error ! " + e.getMessage());
    }
  }

  public static void main(String[] args) {
    new FromKeyBoard1();
  }
}

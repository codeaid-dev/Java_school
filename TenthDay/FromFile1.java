import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FromFile1 {
  public FromFile1() {
    String str;

    try {
      FileReader isr = new FileReader("random.txt");
      BufferedReader br = new BufferedReader(isr);

      str = br.readLine();
      while (str != null) {
        System.out.println(str);
        str = br.readLine();
      }
      System.out.println("end of file loading");
      br.close();
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  public static void main(String[] args) {
    new FromFile1();
  }
}
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ToFile {
  public ToFile() {
    int num;

    try {
      FileWriter fw = new FileWriter("random.txt");
      BufferedWriter bw = new BufferedWriter(fw);
      for (int i = 0; i < 40; i++) {
        num = (int)(10.0 * Math.random());
        bw.write(Integer.toString(num));
        bw.newLine();
      }
      bw.close();
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  public static void main(String[] args) {
    new ToFile();
  }
}
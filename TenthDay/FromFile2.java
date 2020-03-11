import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFrame;

public class FromFile2 extends JFrame {
  int record[] = new int[10];

  public FromFile2() {
    setTitle("FromFile2");
    setSize(500, 500);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    countData();
    setVisible(true);
  }

  public void paint(java.awt.Graphics g) {
    Font font1 = new Font("Dialoge", Font.PLAIN, 20);
    g.setFont(font1);
    for (int i = 0; i < 10; i++) {
      g.setColor(Color.red);
      g.drawString(Integer.toString(i), 20, 70 + 40 * i);
      g.setColor(Color.blue);
      g.drawString(Integer.toString(record[i]), 50, 70 + 40 * i);
      g.fillRect(100, 50 + 40 * i, 50 * record[i], 30);
    }
  }

  public void countData() {
    String kb;

    try {
      FileReader isr = new FileReader("random.txt");
      BufferedReader br = new BufferedReader(isr);

      kb = br.readLine();
      while (kb != null) {
        record[Integer.parseInt(kb)]++;
        kb = br.readLine();
      }
      System.out.println("end of file loading");
      br.close();
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  public static void main(String[] args) {
    new FromFile2();
  }
}
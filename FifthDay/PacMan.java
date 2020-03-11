import java.awt.Color;
import java.awt.Graphics;

public class PacMan {
  private int size;
  private int xCenter;
  private int yCenter;
  private int angle;

  public PacMan() {

  }

  public PacMan(int size_, int x_, int y_, int angle_) {
    setSize(size_);
    setXCenter(x_);
    setYCenter(y_);
    setAngle(angle_);
  }

  int getSize() {
    return(size);
  }

  void setSize(int num) {
    this.size = num;
  }

  int getXCenter() {
    return(xCenter);
  }

  void setXCenter(int num) {
    this.xCenter = num;
  }

  int getYCenter() {
    return(yCenter);
  }

  void setYCenter(int num) {
    this.yCenter = num;
  }

  int getAngle() {
    return(angle);
  }

  void setAngle(int num) {
    this.angle = num;
  }

  public void make(Graphics g) {
    g.setColor(Color.yellow);
    g.fillArc(xCenter - size / 2, yCenter - size /2,
              size, size, angle / 2, 360-angle);
  }
}
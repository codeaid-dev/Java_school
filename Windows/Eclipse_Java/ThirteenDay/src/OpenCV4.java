import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class OpenCV4 extends JFrame {
	BufferedImage bimage1, bimage2, bimage3;
	Mat src, part, mosaic;
	String filename = "kannon.jpg";
	int[][] rect = {{0, 0}, {0, 0}};

	public OpenCV4() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		setTitle("OpenCV2");
		setSize(700, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		MyListener myListener = new MyListener();
		addMouseListener(myListener);
		addMouseMotionListener(myListener);
		setVisible(true);
		takeImage();
	}

	public void paint(Graphics g) {
		if (bimage1 != null) {
			g.drawImage(bimage1, 10, 30, this);
		}
		if (bimage2 != null) {
			g.drawImage(bimage2, 240, 30, this);
		}
		if (bimage3 != null) {
			g.drawImage(bimage3, 470, 30, this);
		}
		g.setColor(Color.red);
		g.drawRect(rect[0][0], rect[0][1], rect[1][0] - rect[0][0], rect[1][1] - rect[0][1]);
	}

	public void takeImage() {
		src = new Mat();
		src = Imgcodecs.imread(filename);
		bimage1 = matToBufferedImage(src);
		repaint();
	}

	public void partImage() {
		Rect roi = new Rect(rect[0][0] - 10, rect[0][1] - 30, rect[1][0] - rect[0][0], rect[1][1] - rect[0][1]);
		part = new Mat(src, roi);
		bimage2 = matToBufferedImage(part);
		mosaic = new Mat(src, roi);
		Imgproc.resize(mosaic,  mosaic,  new Size(), 0.1, 0.1, Imgproc.INTER_NEAREST);
		Imgproc.resize(mosaic,  mosaic,  new Size(), 10.0, 10.0, Imgproc.INTER_NEAREST);
		bimage3 = matToBufferedImage(mosaic);
		repaint();
	}

	public static void main(String[] args) {
		new OpenCV4();
	}

	public static BufferedImage matToBufferedImage(Mat matrix) {
		int cols = matrix.cols();
		int rows = matrix.rows();
		int elemSize = (int)matrix.elemSize();
		byte[] data = new byte[cols * rows * elemSize];
		int type;

		matrix.get(0, 0, data);

		switch (matrix.channels()) {
		case 1:
			type = BufferedImage.TYPE_BYTE_GRAY;
			break;
		case 3:
			type = BufferedImage.TYPE_3BYTE_BGR;
			byte b;
			for (int i = 0; i < data.length; i = i + 3) {
				b = data[i];
				data[i] = data[i+2];
				data[i+2] = b;
			}
			break;
		default:
			return null;

		}
		BufferedImage image2 = new BufferedImage(cols, rows, type);
		image2.getRaster().setDataElements(0,  0,  cols, rows, data);;
		return image2;
	}

	class MyListener extends MouseAdapter {

		public void mousePressed(MouseEvent e) {
			rect[0][0] = e.getX();
			rect[0][1] = e.getY();
			repaint();
		}

		public void mouseReleased(MouseEvent e) {
			partImage();
			repaint();
		}

		public void mouseDragged(MouseEvent e) {
			rect[1][0] = e.getX();
			rect[1][1] = e.getY();
			repaint();
		}
	}
}

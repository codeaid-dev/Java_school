import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class OpenCV2 extends JFrame {
	BufferedImage bimage1, bimage2, bimage3;
	Mat src, gray, binary;
	String filename = "kannon.jpg";

	public OpenCV2() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		setTitle("OpenCV2");
		setSize(700, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
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
	}

	public void takeImage() {
		src = new Mat();
		gray = new Mat();
		binary = new Mat();
		src = Imgcodecs.imread(filename);
		Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
		Imgproc.threshold(gray,  binary,  80,  255,  Imgproc.THRESH_BINARY);
		bimage1 = matToBufferedImage(src);
		bimage2 = matToBufferedImage(gray);
		bimage3 = matToBufferedImage(binary);
		repaint();
	}

	public static void main(String[] args) {
		new OpenCV2();
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

}

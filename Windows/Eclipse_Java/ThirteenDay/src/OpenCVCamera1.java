import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

public class OpenCVCamera1 extends JFrame{
	BufferedImage bimage1;
	Mat imageArray;
	int width, height;
	boolean flag = true;

	public OpenCVCamera1() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		setTitle("OpenCVCamera1");
		setSize(660, 430);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		cameraImage();
	}

	public void paint(Graphics g) {
		if (bimage1 != null) {
			g.drawImage(bimage1, 10, 60, width, height, this);
		}
	}

	public void cameraImage() {
		imageArray = new Mat();
		VideoCapture videoDevice = new VideoCapture(0);
		if (videoDevice.isOpened()) {
			videoDevice.read(imageArray);
			bimage1 = matToBufferedImage(imageArray);
			System.out.println("width = " + bimage1.getWidth() + " height = " + bimage1.getHeight());
			width = bimage1.getWidth();
			height = bimage1.getHeight();
			//width = bimage1.getWidth() / 2;
			//height = bimage1.getHeight() / 2;
			while (flag) {
				videoDevice.read(imageArray);
				bimage1 = matToBufferedImage(imageArray);
				repaint();
			}
			videoDevice.release();
		} else {
			System.out.println("Error.");
		}
	}

	public static void main(String[] args) {
		new OpenCVCamera1();
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

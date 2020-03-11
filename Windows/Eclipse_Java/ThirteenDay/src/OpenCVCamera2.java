import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

public class OpenCVCamera2 extends JFrame implements ActionListener {
	BufferedImage bimage1, bimage2;
	Mat imageArray, gray;
	int width, height;
	JButton button;
	boolean flag = true;

	public OpenCVCamera2() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		setTitle("OpenCVCamera2");
		setSize(660, 630);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		button = new JButton("stop");
		button.setActionCommand("stop");
		button.addActionListener(this);
		JPanel panel = new JPanel();
		panel.add(button);
		getContentPane().add(panel, BorderLayout.NORTH);
		setVisible(true);
		cameraImage();
	}

	public void paint(Graphics g) {
		if (bimage1 != null) {
			g.drawImage(bimage1, 10, 60, width, height, this);
		}
		if (bimage2 != null) {
			g.drawImage(bimage2, 10, 430, width / 2, height / 2, this);
		}
	}

	public void cameraImage() {
		imageArray = new Mat();
		VideoCapture videoDevice = new VideoCapture(0);
		if (videoDevice.isOpened()) {
			videoDevice.read(imageArray);
			bimage1 = matToBufferedImage(imageArray);
			System.out.println("width = " + bimage1.getWidth() + " height = " + bimage1.getHeight());
			//width = bimage1.getWidth();
			//height = bimage1.getHeight();
			width = bimage1.getWidth() / 2;
			height = bimage1.getHeight() / 2;
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

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("stop")) {
			flag = false;
			gray = new Mat();
			Imgproc.cvtColor(imageArray, gray, Imgproc.COLOR_BGR2GRAY);
			bimage2 = matToBufferedImage(gray);
		}
	}

	public static void main(String[] args) {
		new OpenCVCamera2();
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

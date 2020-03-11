import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class OpenCV1 {
	Mat src;
	String filename = "kannon.jpg";
	String destname = "new.jpg";

	public OpenCV1() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		src = new Mat();
		src = Imgcodecs.imread(filename);
		System.out.println("cols = " + src.cols() + " width = " + src.width());
		System.out.println("rows = " + src.rows() + " height = " + src.height());
		Imgcodecs.imwrite(destname, src);
	}

	public static void main(String[] args) {
		new OpenCV1();
	}

}

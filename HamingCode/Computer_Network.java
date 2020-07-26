
public class Computer_Network {
	
	public static void main(String[] args) {
		String data = "1011001";
		int m = data.length();
		int s = calcRedundnatBits(m);
		posRedundantBits(data, s);
		
	}
	
	public static int calcRedundnatBits(int m) {
		
		for(int i = 0; i < m; i++) {
			if((int)Math.pow(2,i) >= m + i + 1) {
				return i;
			}
			
		}
		return 0;
		
	}
	
	public void posRedundantBits(String data, int s) {
		int a = 0;
		int b = 1;
		int c = data.length();
		String d = "";
		for (int i=1; i<=c+s; i++) {
			if (i == Math.pow(a,2)) {
				d += "0";
				a += 1;
			} else {
				d = d + data.string[-1*b];
			}

		}
	}

}

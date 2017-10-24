import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

class CalcThread extends Thread {
   private Thread t;
   private String threadName;
   private PixelWriter pw;

   private double scale = 200;
	private int resX = 800;
	private int resY = 800;
	private int iterations = 200;
	private static double limit = 2;
	
	private double x = 0;
	private double y = 0;
	private int nr;
   
   CalcThread(String name) {
      threadName = name;
      System.out.println("Creating " +  threadName );
   }
   
   public void run() {
      System.out.println("Running " +  threadName );
      
      
      System.out.println("Thread " +  threadName + " exiting.");
   }
   
   
   
   public int iterator(Complex c) {
		//Julia Set
		//Complex z = c;
		//Complex r = new Complex(0.3, -0.49);
		//====================
		
		//Madelbrot Set
		Complex z = new Complex(0, 0);
		//====================
		
		Complex buffer;
		int i;
		for (i = 0; i < iterations; i++) {
			
			//Julia Set
			//buffer = function(z, r);
			//====================
			
			//Madelbrot Set
			buffer = function(z, c);
			//====================
			
			
			if (buffer.add(z.neg()).abs() == 0) break;
			z = buffer;
			if (z.abs() > limit) break;
			//System.out.println(z);
		}
		
		if (z.abs() < limit) {
			return 0;
		}
		return i;
	}
	
	public Complex function(Complex z, Complex c) {
		return z.mul(z).add(c);
	}
	
	public void setPixel(int x, int y, Color c) {
		pw.setColor(x, y, c);
	}
   
	public void start (int nr, int resX, int resY, double x, double y, double scale, PixelWriter pw) {
		System.out.println("Starting " +  threadName );
		this.resX = resX;
		this.resY = resY;
		this.scale = scale;
		this.x = x;
		this.y = y;
		this.pw = pw;
		this.nr = nr;
		if (t == null) {
			t = new Thread (this, threadName);
			t.start ();
		}
   }
}
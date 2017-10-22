import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application{
	private Canvas canvas;
	private GraphicsContext gc;
	private PixelWriter pw;
	private Color colBackground = Color.ALICEBLUE;
	private long lastNanoTime;
	private double timer = 0;
	
	private double scale = 200;
	private int iterations = 1000;
	private static double limit = 2;
	Complex julia = new Complex(0, 0.4);
		
	private double x = 0;
	private double y = 0;
	
	private int resX = 800;
	private int resY = 800;
	

	public static void main(String[] args) {
		launch (args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("CSets");
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 900, 900);
		primaryStage.setScene(scene);
		
		canvas = new Canvas(resX, resY);
		gc = canvas.getGraphicsContext2D();
		pw = canvas.getGraphicsContext2D().getPixelWriter();
		root.setCenter(canvas);
		canvas.setOnMouseClicked(e -> onClickScreen(e));

		primaryStage.show();
		
		clearScreen();
		
		final long startNanoTime = System.nanoTime();
	    lastNanoTime = startNanoTime;
		
		new AnimationTimer()
	      {
	          public void handle(long currentNanoTime)
	          {
	              double dt = (currentNanoTime - lastNanoTime) / 1000000000.0;
	              lastNanoTime = currentNanoTime;
	              
	        	  refreshScreen(dt);
	          }
	      }.start();
	      
	      calcSet();
	}
	
	private void onClickScreen(MouseEvent e) {
		x = x + (e.getX() - resX/2)/scale;
		y = y - (e.getY() - resY/2)/scale;
		scale *= 1.5;
		System.out.println(x + " " + y);
		
		calcSet();
	}
	
	public void calcSet() {
		for (double i = 0; i < resY; i++) {
	  		for (double j = 0; j < resX; j++) {
	  			
				double wx = x + (j - resX/2)/scale;
				double wy = y - (i - resY/2)/scale;
				
				
				int value = iterator(new Complex(wx, wy));
				if (value == 0) setPixel((int) j, (int) i, Color.BLACK);
				else {
					double colSteps = 10;
					setPixel((int) j, (int) i, Color.color(
							1- Math.pow(Math.sin(Math.sqrt(value)/colSteps),2),
							1- Math.pow(Math.sin(Math.sqrt(value)/colSteps + Math.PI * 0.66666667),2),
							1- Math.pow(Math.sin(Math.sqrt(value)/colSteps + Math.PI * 1.33333333),2)
							));
				}
			}
		}
	    
		//CalcThread T2 = new CalcThread( "Thread-2");
	    //T2.start(1, resX, resY, x, y, scale, pw1);
	    
		//CalcThread T3 = new CalcThread( "Thread-3");
	    //T3.start(2, resX, resY, x, y, scale, pw2);
		
	}
	
	public void refreshScreen(double dt) {
		timer += dt;
		if (timer > 0) {
			timer -= 0.2;
			julia.y += 0.001;
			calcSet();
		}
	}
	
	 public int iterator(Complex c) {
			//Julia Set
			//Complex z = c;
			//====================
			
			//Madelbrot Set
			Complex z = new Complex(0, 0);
			//====================
			
			Complex buffer;
			int i;
			for (i = 0; i < iterations; i++) {
				
				//Julia Set
				//buffer = function(z, julia);
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
	
	public void clearScreen() {
		gc.setFill(colBackground);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}
	
}

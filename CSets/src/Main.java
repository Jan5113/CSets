import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application{
	private Canvas canvas;
	private GraphicsContext gc;
	private PixelWriter pw;
	private Color colBackground = Color.ALICEBLUE;
	private int iterations = 0;
	private static double limit = 2;
	private long lastNanoTime;
	private double timer = 0;
	

	public static void main(String[] args) {
		launch (args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("CSets");
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 900, 900);
		primaryStage.setScene(scene);
		
		canvas = new Canvas(800, 800);
		gc = canvas.getGraphicsContext2D();
		pw = canvas.getGraphicsContext2D().getPixelWriter();
		root.setCenter(canvas);

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
	}
	
	public void setPixel(int x, int y, Color c) {
		pw.setColor(x, y, c);
	}
	
	public void refreshScreen(double dt) {
		timer += dt;
		if (timer > 0) {
			iterations++;
			timer -= 0.5;
			for (double i = 0; i < 800; i++) {
				for (double j = 0; j < 800; j++) {
					//int value = iterator(new Complex(j/600000 +0.14, 0.5875 -(i/600000)));
					int value = iterator(new Complex(j/200 -2, 2 -(i/200)));
					if (value == 0) setPixel((int) j, (int) i, Color.BLACK);
					else {
						setPixel((int) j, (int) i, Color.color(1, 1- (double) value/iterations, 1 - (double) value/iterations));
					}
				}
			}
		}
	}
	
	public void clearScreen() {
		gc.setFill(colBackground);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
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

}

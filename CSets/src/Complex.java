
public class Complex {
	public double x;
	public double y;
	
	public Complex (double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Complex add(Complex c) {
		return new Complex(x + c.x, y + c.y);
	}
	
	public Complex addLocal(Complex c) {
		this.x += c.x;
		this.y += c.y;
		return this;
	}
	
	public Complex mul(Complex c) {
		return new Complex(this.x * c.x - this.y * c.y,	this.x * c.y + this.y * c.x);
	}
	
	public Complex mulLocal(Complex c) {
		return thisIs(mul(c));
	}
	
	public Complex mul(double x) {
		return new Complex(this.x * x, this.y * x);
	}
	
	public Complex mulLocal(double x) {
		return thisIs(mul(x));
	}
	
	public Complex div(Complex c) {
		return new Complex(
				(this.x * c.x + this.y * c.y)/(c.x * c.x + c.y * c.y),
				(this.y * c.x - this.x * c.y)/(c.x * c.x + c.y * c.y));
	}
	
	public Complex divLocal(Complex c) {
		return thisIs(this.div(c));
	}
	
	public double abs() {
		return (Math.sqrt(x*x+y*y));
	}
	
	public double arg() {
		double atan;
		if (x == 0) {
			if (y >= 0) atan =  Math.PI*0.5; //90°
			else atan =  Math.PI*1.5; //270°
		}
		else if (y == 0) {
			if (x >= 0) atan =  0; //0°
			else atan =  Math.PI; //180°
		}
		else {
			atan = (Math.atan(y/x));
			if (x > 0 && y < 0) atan += 2*Math.PI; //Q4
			else if (x < 0) atan += Math.PI; //Q2 & Q3
		}		
		return atan;
	}
	
	public Complex neg() {
		return new Complex(-this.x, -this.y);
	}
	
	public Complex negLocal() {
		return thisIs(conjugate());
	}
	
	public Complex conjugate() {
		return new Complex(this.x, -this.y);
	}
	
	public Complex conjugateLocal() {
		return thisIs(conjugate());
	}
	
	public String toString() {
		return ("x: " + x + " | y: " + y);
	}
	
	private Complex thisIs(Complex c) {
		this.x = c.x;
		this.y = c.y;
		return this;
	}
}

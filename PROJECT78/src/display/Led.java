package display;
/**
 * deze classe maakt het mogelijk om de ledjes als een object op te slaan deze worden gebruikt in de classe CubeObject om meerdere ledjes te gelijk te kunnen besturen
 * 
 * @author Jimmy
 * 
 */
public class Led {
	private int x,y,z,green,red;

	public Led() {
		x = 0;
		y = 0;
		z = 0;
		green = 0;
		red = 0;
	}

	public Led(int x,int y,int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		green = 0;
		red = 0;
	}

	public Led(int green,int red) {
		x = 0;
		y = 0;
		z = 0;
		this.green = green;
		this.red = red;
	}

	public Led(int x,int y,int z,int green,int red) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.green = green;
		this.red = red;
	}

	public Led(Led a) {
		this.x = a.getX();
		this.y = a.getY();
		this.z = a.getZ();
		this.green = a.getGreen();
		this.red = a.getRed();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public int getGreen() {
		return green;
	}

	public int getRed() {
		return red;
	}

	public void setX(int x) throws Exception {
		if (x < 0 || x > 15) {
			throw new Exception("invalid value");
		}
		this.x = x;
	}

	public void setY(int y) throws Exception {
		if (y < 0 || y > 15) {
			throw new Exception("invalid value");
		}
		this.y = y;
	}

	public void setZ(int z) throws Exception {
		if (z < 0 || z > 15) {
			throw new Exception("invalid value");
		}
		this.z = z;
	}

	public void setGreen(int green) throws Exception {
		if (green < 0 || green > 255) {
			throw new Exception("invalid value");
		}
		this.green = green;
	}

	public void setRed(int red) throws Exception {
		if (red < 0 || red > 255) {
			throw new Exception("invalid value");
		}
		this.red = red;
	}
}
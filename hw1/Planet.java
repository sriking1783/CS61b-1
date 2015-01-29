public class Planet {
	public double x;
	public double y;
	public double xVelocity;
	public double yVelocity;
	public double xAccel;
	public double yAccel;
	public double xNetForce;
	public double yNetForce;
	public double mass;
	public String img;

	public Planet(double xPos, double yPos, double horizVelocity, double vertiVelocity, double objMass, String image) {
		x = xPos;
		y = yPos;
		xVelocity = horizVelocity;
		yVelocity = vertiVelocity;
		mass = objMass;
		img = image;
	}

	public double calcDistance (Planet p2) {
		double disX = p2.x - this.x;
		double disY = p2.y - this.y;
		double sum = (disX * disX) + (disY * disY);
		return Math.sqrt(sum);
	}

	public double calcPairwiseForce (Planet a) {
		double g = 6.67 * Math.pow(10, -11);
		double massProduct = this.mass * a.mass;
		double squareDist = this.calcDistance(a) * this.calcDistance(a);
		return (g * massProduct) / squareDist;
	}

	public double calcPairwiseForceX (Planet pX) {
		double dx = pX.x - this.x;
		return (this.calcPairwiseForce(pX) * dx) / (this.calcDistance(pX));
	}

	public double calcPairwiseForceY (Planet pY) {
		double dy = pY.y - this.y;
		return (this.calcPairwiseForce(pY) * dy) / (this.calcDistance(pY));
	}

	public void setNetForce (Planet[] pList) {
		xNetForce = 0;
		yNetForce = 0;
		for (Planet p : pList) {
			if (this.equals(p)) {
				continue;
			}
			this.xNetForce += this.calcPairwiseForceX(p);
			this.yNetForce += this.calcPairwiseForceY(p);
		}
	}

	public void draw () {
		StdDraw.picture(this.x, this.y, "images/" + this.img);
	}

	public void update (double dt) {
		this.xAccel = this.xNetForce / this.mass;
		this.yAccel = this.yNetForce / this.mass;
		this.xVelocity = xVelocity + (dt * xAccel); 
		this.yVelocity = yVelocity + (dt * yAccel);
		this.x = x + (dt * xVelocity);
		this.y = y + (dt * yVelocity);
	}
}
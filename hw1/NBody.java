public class NBody {

	public static void main (String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		In info = new In(filename);
		int N = info.readInt();
		double R = info.readDouble();

		Planet[] plst = new Planet[N];
		int x = 0;
		while (x < plst.length) {
			plst[x] = NBody.getPlanet(info);
			x += 1;
		}
		StdDraw.setScale((R * (-1)), R);
		StdDraw.picture(0, 0, "images/starfield.jpg");
		for (Planet p : plst) {
			p.draw();
		}

		double time = 0;
		while (time < T) {
			for (Planet p : plst) {
				p.setNetForce(plst);
			}
			for (Planet p : plst) {
				p.update(dt);
			}
			StdDraw.picture(0, 0, "images/starfield.jpg");
			for (Planet p : plst) {
				p.draw();
			}
			StdDraw.show(10);
			time += dt;
		}

		StdOut.printf("%d\n", N);
		StdOut.printf("%.2e\n", R);
		for (int i = 0; i < N; i++) {
		    StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
		                   plst[i].x, plst[i].y, plst[i].xVelocity, plst[i].yVelocity, plst[i].mass, plst[i].img);
		}
	}

	public static Planet getPlanet (In obj) {
		double x = obj.readDouble();
		double y = obj.readDouble();
		double xVel = obj.readDouble();
		double yVel = obj.readDouble();
		double mass = obj.readDouble();
		String img = obj.readString();
		return new Planet(x, y, xVel, yVel, mass, img);
	}
}
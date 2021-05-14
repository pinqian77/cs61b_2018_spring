public class NBody {
    private static String backgroundImage = "./images/starfield.jpg"; // background image
    private static final String backgroundMusic = "./audio/2001.mid"; // background music
    /**
     * read radius
     * @param file
     * @return
     */
    public static double readRadius(String file) {
        In in = new In(file);
        int num = in.readInt(); //number of planets
        double radius = in.readDouble(); //universe radius
        return radius;
    }

    /**
     * read planets
     * @param file
     * @return
     */
    public static Planet[] readPlanets(String file) {
        In in = new In(file);
        int num = in.readInt(); //number of planets
        Planet[] Planets = new Planet[num];
        double rd = in.readDouble(); //universe radius
    
        int i;
        for (i=0;i<num ;i++ ) {
          double xPos = in.readDouble();
          double yPos = in.readDouble();
          double xVel = in.readDouble();
          double yVel = in.readDouble();
          double m = in.readDouble();
          String img = in.readString();
          Planets[i] = new Planet(xPos, yPos, xVel, yVel, m, img);
        }
    
        return Planets;
    }

    public static void main(String[] args){
        double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double radius = readRadius(filename);
		Planet[] planets = readPlanets(filename);
		StdDraw.enableDoubleBuffering();
		StdDraw.setScale(-(radius+0.5*Math.pow(10,11)), radius+0.5*Math.pow(10,11));
		StdDraw.picture(0, 0, "./images/starfield.jpg");
		StdDraw.show();
		for(int i=0;i<planets.length;i++){
			planets[i].draw();
		}
		double[] xForces = new double[planets.length];
		double[] yForces = new double[planets.length];
		for (double time=0; time <= T; time = time + dt){
			for(int i=0; i<planets.length; i++){
				xForces[i] = planets[i].calcNetForceExertedByX(planets);
				yForces[i] = planets[i].calcNetForceExertedByY(planets);
			}
			for(int j=0; j<planets.length; j++){
				planets[j].update(dt,xForces[j],yForces[j]);
			}
			StdDraw.setScale(-(radius+0.5*Math.pow(10,11)), radius+0.5*Math.pow(10,11));
			StdDraw.picture(0, 0, "./images/starfield.jpg");
			for(int i=0; i<planets.length;i++){
				planets[i].draw();
			}
			StdDraw.show();
			StdDraw.pause(10);
		}
		StdOut.printf("%d\n", planets.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < planets.length; i++) {
    	StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                  planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }   
    };
    

}

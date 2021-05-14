public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    private static double G = 6.67*Math.pow(10,-11);


    public Planet(double xP, double yP, double xV,
                double yV, double m, String img){
                    this.xxPos = xP;
                    this.yyPos = yP;
                    this.xxVel = xV;
                    this.yyVel = yV;
                    this.mass = m;
                    this.imgFileName = img;
    }

    public Planet(Planet p){
        this.xxPos = p.xxPos;
        this.yyPos = p.yyPos;
        this.xxVel = p.xxVel;
        this.yyVel = p.yyVel;
        this.mass = p.mass;
        this.imgFileName = p.imgFileName;
    }

    /**
     * calculate distance
     * @param p
     * @return
     */
    public double calcDistance(Planet p){
        double dx = this.xxPos - p.xxPos;
        double dy = this.yyPos - p.yyPos;
        double r = Math.sqrt(dx * dx + dy * dy);
        return r;
    }

    /**
     * calculate force
     * @param p
     * @return
     */
    public double calcForceExertedBy(Planet p){
        double r = calcDistance(p);
        double F = G * this.mass * p.mass / (r * r);
        return F;
    }

    /**
     * calculate x direction force
     * @param p
     * @return
     */
    public double calcForceExertedByX (Planet p) {
        double r = calcDistance(p);
        double F = calcForceExertedBy(p);
        double dx = p.xxPos - this.xxPos; //x direction distance
        double Fx = dx * F / r;
        return Fx;
    }

    /**
     * calculate y direction force
     * @param p
     * @return
     */
    public double calcForceExertedByY (Planet p) {
        double r = calcDistance(p);
        double F = calcForceExertedBy(p);
        double dy = p.yyPos - this.yyPos; //y direction distance
        double Fy = dy * F / r;
        return Fy;
    }

    public double calcNetForceExertedByX (Planet[] p) {
        int num = p.length; //number of bodies
        double sum = 0;
        for (int i=0;i<num ;i++ ) {
            if (this.equals(p[i])) continue; 
            sum += calcForceExertedByX(p[i]);
        }
        return sum;
      }
    
      /** calculate y direction net force*/
    public double calcNetForceExertedByY (Planet[] p) {
        int num = p.length; //number of bodies
        double sum = 0;
        for (int i=0;i<num ;i++ ) {
            if (this.equals(p[i])) continue; 
            sum += calcForceExertedByY(p[i]);
        }
        return sum;
    }

    /**
     * update velocity and position
     * @param dt
     * @param fx
     * @param fy
     */
    public void update (double dt, double fx, double fy) {
        double a_net_x = fx / this.mass;
        double a_net_y = fy / this.mass;
    
        double v_new_x = this.xxVel + dt * a_net_x;
        double v_new_y = this.yyVel + dt * a_net_y;
        
        double p_new_x = this.xxPos + dt * v_new_x;
        double p_new_y = this.yyPos + dt * v_new_y;
    
        /** update velocity*/
        this.xxVel = v_new_x;
        this.yyVel = v_new_y;
    
        /** update position*/
        this.xxPos = p_new_x;
        this.yyPos = p_new_y;
    }

    public void draw(){
		StdDraw.picture(xxPos, yyPos, "./images/"+imgFileName);
	}



}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drawing;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
/**
 * @author matra4214
 */
public class Point{
        
        private double x, y, dx, dy, mass;
        private boolean clicked;
        int identifier;
        protected int Sd = 10;
        Color c;
        
        Boolean coll = false;
        final private double G = 20;
         
        public Point(double x, double y, int index){
            this.x = x;
            this.y = y;
            this.identifier = index;
            this.mass = 1;
            c = Color.white;
        }
        
        public void reset(){
            this.coll = false;
        }
        
        public double getX(){
            return x;
        }
        public double getY(){
            return y;
        }
        public double getDx(){
            return dx;
        }
        public double getDy(){
            return dy;
        }
        public int getIdentifier(){
            return identifier;
        }
        
        public void setIdentifier(int identifier){
            this.identifier = identifier;
        }
        public void setCoord(double x, double y){
            this.x = x;
            this.y = y;
        }
        public void setDx(double dx){
            this.dx = dx;
        }
        public void setDy(double dy){
            this.dy = dy;
        }
        public void update(ArrayList<Point> point, double delta){
            setD(point, delta);
            this.x += dx*delta;
            this.y += dy*delta;
        }
        public void setMass(double mass){
            this.mass = mass;
        }
        public double getMass(){
            return mass;
        }
        ArrayList<Point> cubes = new ArrayList<>();
        
        public double distance(double x1, double x2, double y1, double y2){
            return Math.sqrt(Math.pow(x2-x1, 2)+Math.pow(y2-y1, 2));
        }
        
        public double getVelocity(){
            return Math.sqrt(Math.pow(this.dx, 2)+Math.pow(this.dy,2));
        }
        public double velocityAngle(){
            return Math.atan2(this.dy, this.dx);
        }
        
        /*public void collision(Point point){
            if (this.distance(this.x, point.getX(), this.y, point.getY()) < this.Sd){
                collide.Coll(this, point);
                System.out.println(this.identifier);
            }
        }*/
        
        public boolean colliding(Point p){
            if (this.distance(this.x, p.getX(), this.y, p.getY()) < this.Sd){
                return true;
            }else{
                return false;
            }
        }
        
        private double gravitate(double distance, double mass1){
            return G*mass1/(Math.pow(distance, 2) + 1);
        }
        
        private void setD(ArrayList<Point> point, double delta){
            for (int i = 0; i < point.size(); i++){
                double diffX = this.x - point.get(i).getX();
                double diffY = this.y - point.get(i).getY();
                if (point.get(i).getIdentifier() != this.identifier){
                    double dist = distance (point.get(i).getX(), x, point.get(i).getY(), y);
                    
                    if (this.distance(this.x, point.get(i).getX(), this.y, point.get(i).getY()) >= this.Sd+5){
                        double g = -gravitate(dist, point.get(i).mass);
                        dx += (g*diffX)/dist;
                        dy += (g*diffY)/dist;
                    }
                }
            }
        }
        
         
        public void draw(Graphics g){
            g.setColor(c);
            String space = Integer.toString(this.identifier);
            //g.drawRect((int)x, (int)y, Sd, Sd);
            g.drawOval((int)this.x, (int)this.y, Sd, Sd);
            //g.drawChars(space.toCharArray(), 0, space.toCharArray().length, (int)x+2, (int)y-3);
            //g.drawLine((int)x, (int)y, (int)x, (int)y);
            //c = Color.white;
        }
        
    }

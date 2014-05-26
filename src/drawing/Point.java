/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drawing;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
/**
 * @author matra4214
 */
public class Point{
        
        private double x, y, dx, dy, mass;
        private boolean clicked;
        int identifier;
        
        Color c;
        Random r = new Random();
        private double radius;
        
        Boolean coll = false;
        final private double G = 20;
         
        public Point(double x, double y, double mass, int index, boolean clicked){
            this.x = x;
            this.y = y;
            this.identifier = index;
            this.clicked = clicked;
            this.mass = mass;
            this.radius = r.nextDouble()*r.nextInt((int)this.mass)+2;
            c = Color.white;
        }
        
        /**
         * Whether or not was clicked into creation
         * @return
         */
        public boolean isClickCreated(){
            return clicked;
        }
        
        /**
         * Set the state of whether clicked into creation or not
         * Use if wanted to change the mass in sim.
         * @param clicked
         */
        public void setState(boolean clicked){
            this.clicked = clicked;
        }
        
        /**
         * Current X position
         * @return X coordinate (double)
         */
        public double getX(){
            return x;
        }
        
        /**
         * Current Y position
         * @return Y coordinate (double)
         */
        public double getY(){
            return y;
        }
        
        /**
         * Velocity in the X direction
         * @return X Velocity (double)
         */
        public double getDx(){
            return dx;
        }
        
        /**
         * Velocity in the Y direction
         * @return Y Velocity  (double)
         */
        public double getDy(){
            return dy;
        }
        
        /**
         * Unique identifier for this point
         * @return Identifier (int)
         */
        public int getIdentifier(){
            return identifier;
        }
        
        /**
         * Set the identifier
         * @param identifier (int)
         */
        public void setIdentifier(int identifier){
            this.identifier = identifier;
        }
        
        /**
         * Set the X position
         * @param x (double)
         */
        public void setX(double x){
        	this.x = x;
        }
        
        /**
         * Set the Y position
         * @param y (double)
         */
        public void setY(double y){
        	this.y = y;
        }
        
        /**
         * Set the velocity in the X direction
         * @param dx (double)
         */
        public void setDx(double dx){
            this.dx = dx;
        }
        
        /**
         * Set the velocity in the Y direction
         * @param dy (double)
         */
        public void setDy(double dy){
            this.dy = dy;
        }
        
        /**
         * Update the velocities according to delta
         * @param point (ArrayList)
         * @param delta (double)
         */
        public void regUpdate(ArrayList<Point> point, double delta){
            setD(point);
            this.x += dx*delta;
            this.y += dy*delta;
        }
        
        /**
         * Increment the mass (preferably small values)
         * @param massIncrement (double)
         */
        public void incrementMass(double massIncrement){
            this.mass += massIncrement;
            this.radius += massIncrement;
        }
        
        /**
         * Radius of this point
         * @return radius (double)
         */
        public double getRadius(){
        	return this.radius;
        }
        
        /**
         * Set the mass of this point
         * Avoid use
         * @param mass (double)
         */
        public void setMass(double mass){
            this.mass = mass;
        }
        
        /**
         * Mass of this point
         * @return mass (double)
         */
        public double getMass(){
            return mass;
        }
        
        public double distance(double x1, double x2, double y1, double y2){
            return Math.sqrt(Math.pow(x2-x1, 2)+Math.pow(y2-y1, 2));
        }
        
        public double getVelocity(){
            return Math.sqrt(Math.pow(this.dx, 2)+Math.pow(this.dy,2));
        }
        
        public double velocityAngle(){
            return Math.atan2(this.dy, this.dx);
        }
        
        public boolean colliding(Point p){
            if (this.distance(this.x, p.x, this.y, p.y) <= (this.radius+p.radius)){
                return true;
            }else{
                return false;
            }
        }
        
        private double gravitate(double distance, double mass1){
            return G*mass1/(Math.pow(distance, 2) + 1);
        }
        
        private void setD(ArrayList<Point> p){
            for (int i = 0; i < p.size(); i++){
                double diffX = this.x - p.get(i).getX();
                double diffY = this.y - p.get(i).getY();
                if (p.get(i).getIdentifier() != this.identifier){
                    double dist = distance (p.get(i).getX(), x, p.get(i).getY(), y);
                    
                    if (this.distance(this.x, p.get(i).getX(), this.y, p.get(i).getY()) >= this.radius+5){
                        double g = -gravitate(dist, p.get(i).mass);
                        //dx += (g*diffX)/dist;
                        //dy += (g*diffY)/dist;
                    }
                }
            }
        }
        
         
        public void draw(Graphics g){
        	
            g.setColor(c);
            
            String space = Double.toString(this.mass);
            
            g.drawOval((int)this.x, (int)this.y, (int)radius, (int)radius);
            
            if (clicked){
                g.drawChars(space.toCharArray(), 0, space.toCharArray().length, (int)x-2, (int)y-3);
            }
        }
    }

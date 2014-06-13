/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drawing;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import math.VectorMath;
/**
 * @author matra4214
 */
public class Point{
        
        private double x, y, dx, dy, mass;
        private boolean clicked;
        int identifier;
        VectorMath math = new VectorMath();
        Color c;
        Random r = new Random();
        double diameter;
        
        Boolean coll = false;
        final private double G = 1;
         
        public Point(double x, double y, double mass, int index, boolean clicked){
            this.x = x;
            this.y = y;
            this.identifier = index;
            this.clicked = clicked;
            this.mass = mass;
            this.diameter = r.nextDouble()*r.nextInt((int)this.mass)+2;
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
            this.diameter += massIncrement;
        }
        
        public void randomizeMassandRadius(int max){
            double rMass = r.nextDouble()*r.nextInt(max)+1;
            this.mass = rMass;
            this.diameter = r.nextDouble()*r.nextInt((int)this.mass)+2;
        }
        
        /**
         * Radius of this point
         * @return radius (double)
         */
        public double getRadius(){
            return this.diameter/2;
        }
        
        public void setDiameter(double diameter){
            this.diameter = diameter;
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
        
        /**
         * Set the colour of the circle
         * @param c (Color)
         */
        public void setColour(Color c){
            this.c = c;
        }
        
        public double getVelocity(){
            return Math.sqrt(Math.pow(this.dx, 2)+Math.pow(this.dy,2));
        }
        
        public double velocityAngle(){
            return Math.atan2(this.dy, this.dx);
        }
        
        
        
        private double gravitate(double distance, double mass1){
            return G*mass1/(Math.pow(distance, 2) + 1);
        }
        
        private void setD(ArrayList<Point> p){
            for (int i = 0; i < p.size(); i++){
                double diffX = this.x-p.get(i).getX();
                double diffY = this.y-p.get(i).getY();
                if (p.get(i).getIdentifier() != this.identifier){
                    double dist = math.distance (p.get(i).getX(), x, p.get(i).getY(), y);
                    
                    if (dist >= this.diameter+5){
                        double g = -gravitate(dist, p.get(i).mass);
                        dx += (g*diffX)/dist;
                        dy += (g*diffY)/dist;
                    }
                }
            }
        }
        
         
        public void draw(Graphics g){
        	
            g.setColor(c);
            
            String space = Double.toString(this.mass);
            
            g.drawOval((int)(this.x-this.getRadius()), (int)(this.y-this.getRadius()), (int)diameter, (int)diameter);
            
            //g.drawOval((int)this.x, (int)this.y, 1, 1);
            if (clicked){
                g.drawChars(space.toCharArray(), 0, space.toCharArray().length, (int)x-2, (int)y-3);
            }
        }
    }

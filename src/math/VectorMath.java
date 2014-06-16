package math;

import drawing.Point;
import java.util.ArrayList;

public class VectorMath {
	
	public double dotProduct(double x1, double x2, double y1, double y2){
            return (x1*x2)+(y1*y2);
	}
        
        public double distance(double x1, double x2, double y1, double y2){
            return Math.sqrt(Math.pow(x2-x1, 2)+Math.pow(y2-y1, 2));
        }
        public int isWithin(ArrayList<Point> point, double x, double y){
            for (int i = 0; i < point.size(); i++){
                if (distance(x, point.get(i).getX(), y, point.get(i).getY()) <= point.get(i).getRadius()){
                    return i;
                }
            }
            return -1;
        }
}

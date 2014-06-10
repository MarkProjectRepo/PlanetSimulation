package math;

public class VectorMath {
	
	public double dotProduct(double x1, double x2, double y1, double y2){
            return (x1*x2)+(y1*y2);
	}
        
        public double distance(double x1, double x2, double y1, double y2){
            return Math.sqrt(Math.pow(x2-x1, 2)+Math.pow(y2-y1, 2));
        }
}

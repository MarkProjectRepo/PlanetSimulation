package drawing;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

public class Drawing{
    public double delta;
    public final int entities = 50;
    public boolean isRunning = true;
    public boolean clicked = false;
    public boolean wasRunning = false;
    double mx = Double.MAX_VALUE;
    double my = Double.MAX_VALUE;
    Collision collide = new Collision();
    Window window = new Window();
    
    int Height = window.Height, Width = window.Width;
    
    ArrayList<Point> point = new ArrayList<>();
    Random r = new Random();
    
    public Drawing() {
        window.init();
        window.mainCanvas.addKeyListener(new KeyListen(){
            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_SPACE){
                    isRunning = !isRunning;
                }
            }
        });
        
        window.mainCanvas.addMouseListener(new MouseClick(){;
            @Override
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2 && !me.isConsumed()){
                    me.consume();
                    point.add(new Point(me.getX(),me.getY(),point.size()));
                }
            }
            @Override
            public void mousePressed(MouseEvent me) {
                if (isRunning){
                    isRunning = false;
                    wasRunning = true;
                }else{
                    wasRunning = false;
                }
                clicked = true;
                mx = Double.MAX_VALUE;
                my = Double.MAX_VALUE;
            }
            @Override
            public void mouseReleased(MouseEvent me) {
                if (wasRunning){
                    isRunning = true;
                }else{
                    wasRunning = true;
                }
                clicked = false;
                
            }
        });
        
        window.mainCanvas.addMouseMotionListener(new MouseMotion(){
            
            double diffX;
            double diffY;
            @Override
            public void mouseDragged(MouseEvent me) {
                if (clicked){
                    if (mx == Double.MAX_VALUE) {
                        mx = me.getX();
                        my = me.getY();
                    }

                    diffX = me.getX()-mx;
                    diffY = me.getY()-my;
                    for (int i = 0; i < point.size(); i++){
                        point.get(i).setCoord(point.get(i).getX()+diffX, point.get(i).getY()+diffY);
                    }
                    mx = me.getX();
                    my = me.getY();
                }
            }
            
        });
        for (int u = 0; u < entities; u++){
             point.add(new Point(r.nextInt(Height), r.nextInt(Width), u));
             point.get(point.size()-1).setMass(r.nextDouble()*r.nextInt(50)+1);
        }
        /*point.add(new Point(200,259,1));
        point.get(point.size()-1).setMass(500);
        point.get(point.size()-1).setDx(0);
        point.add(new Point(160,250,2));
        point.get(point.size()-1).setMass(1);
        point.get(point.size()-1).setDx(10);
        point.get(point.size()-1).setDy(0);
        point.add(new Point(120,200,3));
        point.get(point.size()-1).setMass(1);
        point.get(point.size()-1).setDx(10);
        point.get(point.size()-1).setDy(3);*/
    }
    public void loop(){
        
        Graphics g = (Graphics)window.bufferStrategy.getDrawGraphics();
        
        long time1 = System.nanoTime();
        long time2;
        
        long optimalFPS = 60;
        long sToNs = 1000000000;
        long interval = sToNs / optimalFPS;
        while (true){
            time2 = System.nanoTime();
            
            if (time2 - time1 >= interval && isRunning) {
                delta = (time2 - time1) / (double) sToNs;
                //System.out.println((int)(1.0 / delta));
                update(point);
                time1 = time2;
            }else if (!isRunning){
                time1 = time2;
            }
            g.clearRect(0, 0, Width, Height);
            paints(g);
            
            window.bufferStrategy.show();
        }
    }
    
    public void paints(Graphics g){
        for (int i =0; i < point.size(); i++){
            if (point.get(i).getX() > -10 && point.get(i).getX() < Width ||
                point.get(i).getY() > -10 && point.get(i).getY() < Height){
                point.get(i).draw(g);
            }
        }
    }
    
    public void update(ArrayList<Point> point){
        for (int i = 0; i < point.size(); i++){
            point.get(i).update(point, delta);
            for (int c = 0; c < point.size(); c++){
                if (point.get(i).colliding(point.get(c)) && point.get(i).getIdentifier() != point.get(c).getIdentifier()){
                    collide.Coll(point.get(i), point.get(c));
                }
            }
        }
    }
    
    public static void main(String[] args) {
        Drawing drawing = new Drawing();
        drawing.loop();
    }
}
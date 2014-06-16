package drawing;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import java.util.ArrayList;
import java.util.Random;
import listeners.*;
import math.VectorMath;

public class Drawing{
    public double delta;
    
    public final int entities = 50;
    public boolean isRunning = true;
    public boolean clicked = false;
    public boolean wasRunning = false;
    public boolean run = true;
    public boolean focusToggle = false;
    public int gameState = 0;
    int focused = -1;
    double mx = Double.MAX_VALUE;
    double my = Double.MAX_VALUE;
    double wheelIncrease = 0;
    
    int max = 0;
    int identify = 1111111111;
    
    VectorMath math = new VectorMath();
    Collision collide = new Collision();
    Window window = new Window();
    Menu menu = new Menu();
    
    Tuple<Boolean, Integer> tuple = new Tuple<>();
    
    
    ArrayList<Point> point = new ArrayList<>();
    Random r = new Random();
    
    public Drawing() {
        
        JButton cameraToggle = new JButton("Toggle Select");
        JButton cameraFree = new JButton("Free camera");
        
        window.init();
        
        int size = 115;
        cameraToggle.setSize(size, 20);
        cameraToggle.setLocation(0,0);
        
        cameraFree.setSize(110, 20);
        cameraFree.setLocation(size, 0);
        
        size += 110;
        
        window.addToMenuPanel(cameraFree);
        window.addToMenuPanel(cameraToggle);
        
        window.mainCanvas.addKeyListener(new KeyListen(){
            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_SPACE){
                    isRunning = !isRunning;
                    gameState = 1;
                    window.menuPanelVisible(!isRunning);
                }
                if (ke.getKeyCode() == KeyEvent.VK_R && run){
                    double rMass = r.nextDouble()*r.nextInt(50)+1;
                    point.add(new Point(r.nextInt(window.Height), r.nextInt(window.Width), rMass,point.size(),false));
                }else if (ke.getKeyCode() == KeyEvent.VK_R && !run){
                    point.get(identify).randomizeMassandRadius(max);
                }
            }
        });
        
        window.mainCanvas.addMouseListener(new MouseClick(){;
            @Override
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2 && !me.isConsumed()){
                    me.consume();
                    point.add(new Point(me.getX(),me.getY(), 1,point.size(),true));
                    isRunning = true;
                }else if(!run){
                    for (Point p1 : point){
                        p1.setState(false);
                    }
                    run = true;
                    //gameState = 2;
                }
                if (focusToggle && focused >= 0){
                    for (Point p : point){
                        p.setFocus(false);
                        p.setColour(Color.white);
                    }
                    point.get(focused).setFocus(true);
                    point.get(focused).setColour(Color.red);
                    focusToggle = false;
                }
            }
            @Override
            public void mousePressed(MouseEvent me) {
                if (isRunning){
                    isRunning = false;
                    wasRunning = true;
                    //gameState = 2;
                }else{
                    wasRunning = false;
                    //gameState = 3;
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
                    
                    for (Point p1 : point){
                        p1.setX(p1.getX()+diffX);
                        p1.setY(p1.getY()+diffY);
                    }
                    mx = me.getX();
                    my = me.getY();
                }
            }
            
            
            @Override
            public void mouseMoved(MouseEvent me) {
                
                if (focusToggle){
                    if (focused >= 0){
                        point.get(focused).setColour(Color.white);
                    }
                    focused = math.isWithin(point, me.getX(), me.getY());
                    if (focused >= 0){
                        point.get(focused).setColour(Color.red);
                    }
                }
                
            }
        });
        
        window.mainCanvas.addMouseWheelListener(new MouseWheelListener(){
            
            @Override
            public void mouseWheelMoved(MouseWheelEvent mwe) {
                if (!run){
                    wheelIncrease = -mwe.getWheelRotation();
                }
            }
        });
        
        cameraToggle.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                focusToggle = true; 
           }
            
        });
        cameraFree.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                for (Point p : point){
                    p.setFocus(false);
                    p.setColour(Color.white);
                }
                focused = -1;
            }
            
        });
        
        for (int u = 0; u < entities; u++){
            double rMass = r.nextDouble()*r.nextInt(50)+1;
            point.add(new Point(r.nextInt(window.Height), r.nextInt(window.Width), rMass,u,false));
        }
        /*point.add(new Point(200,250,1000000,501,false));
        point.get(point.size()-1).setDx(0);
        point.get(point.size()-1).setDiameter(10);
        /*point.add(new Point(160,250,10,1,false));
        point.get(point.size()-1).setDx(10);
        point.get(point.size()-1).setDy(0);
        /*point.add(new Point(120,200,3));
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
            for (int i = 0; i < point.size(); i++){
                if (point.get(i).isClickCreated()){
                    run = false;
                    identify = i;
                }
            }
            if (time2 - time1 >= interval && isRunning && run) {
                delta = (time2 - time1) / (double) sToNs;
                //System.out.println((int)(1.0 / delta));
                if (focused < 0){
                    regUpdate(point);
                }else{
                    focusUpdate(point);
                }
                time1 = time2;
            }else if (!isRunning){
                time1 = time2;
            }else if (!run){
                time1 = time2;
                isRunning = true;
                if (point.get(identify).getMass() > 1 && wheelIncrease != 0){
                    point.get(identify).incrementMass(wheelIncrease);
                }else if (point.get(identify).getMass() == 1 && wheelIncrease > 0){
                    point.get(identify).incrementMass(wheelIncrease);
                }
                max += wheelIncrease;
                wheelIncrease = 0;
            }
            g.clearRect(0, 0, window.Width, window.Height);
            paints(g);
            window.bufferStrategy.show();
        }
    }
    
    public void paints(Graphics g){
        for (Point p1: point){
            if (p1.getX() > -10 && p1.getX() < window.Width ||
                p1.getY() > -10 && p1.getY() < window.Height){
                p1.draw(g);
            }
        }
    }
    
    public void focusUpdate(ArrayList<Point> point){
        double diffX, diffY;
        double px = Double.MAX_VALUE, py = Double.MAX_VALUE;
        for (int i = 0; i < point.size(); i++){
                
            point.get(i).focusUpdate(point, point.get(focused), delta);

            for (int c = 0; c < point.size(); c++){

                if (c != i){
                    if (point.get(i).getIdentifier() != point.get(c).getIdentifier()){

                        if (collide.colliding(point.get(i), point.get(c))){

                            collide.Coll(point.get(i), point.get(c));
                        }
                    }
                }
            }
            
            
            /*if (px == Double.MAX_VALUE) {
                px = point.get(focused).getX();
                py = point.get(focused).getY();
            }

            diffX = point.get(focused).getX()-px;
            diffY = point.get(focused).getY()-py;

            for (Point p1 : point){
                if (p1 != point.get(focused)){
                    p1.setX(p1.getX()+diffX);
                    p1.setY(p1.getY()+diffY);
                }
            }
            px = point.get(focused).getX();
            py = point.get(focused).getY();
            */
        }
    }
    
    public void regUpdate(ArrayList<Point> point){
        
        for (int i = 0; i < point.size(); i++){

            point.get(i).regUpdate(point, delta);

            for (int c = 0; c < point.size(); c++){

                if (c != i){
                    if (point.get(i).getIdentifier() != point.get(c).getIdentifier()){

                        if (collide.colliding(point.get(i), point.get(c))){

                            collide.Coll(point.get(i), point.get(c));
                        }
                    }
                }
            }

        }
    }
    

    public static void main(String[] args) {
        Drawing drawing = new Drawing();
        drawing.loop();
    }
}

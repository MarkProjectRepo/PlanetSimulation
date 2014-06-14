/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drawing;

import java.awt.Canvas;
import java.awt.*;
import java.awt.Color;
import java.awt.image.BufferStrategy;
import javax.swing.*;


public class Window {
    public final int Width = 500, Height = 500;
    public boolean clicked = false;
    Canvas mainCanvas = new Canvas();
    JFrame frame  = new JFrame("Colliding Spheres in Space");
    
    Panel menuPanel = new Panel();
    
    Button cameraToggle = new Button("Toggle Select");
    Button cameraFree = new Button("Free camera");
    
    BufferStrategy bufferStrategy; 
    
    JPanel panel;
    
    public void init(){
        panel = (JPanel) frame.getContentPane();
        panel.setSize(Height - 20, Width - 20);
        
        menuPanel.setSize(Width,Height/4);
        
        
        mainCanvas.setSize(Width, Height);
        
        menuPanel.setVisible(false);
        
        cameraToggle.setSize(80, 20);
        cameraToggle.setLocation(0,0);
        
        cameraFree.setSize(80, 20);
        cameraFree.setLocation(80, 0);
        
        menuPanel.setBackground(Color.white);
        
        panel.add(menuPanel);
        
        panel.add(mainCanvas);
        panel.setBackground(Color.black);
        
        frame.getContentPane().add(mainCanvas);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        
        mainCanvas.createBufferStrategy(2);
        
        mainCanvas.requestFocus();
        
        menuPanel.add(cameraToggle);
        menuPanel.add(cameraFree);
        
        bufferStrategy = mainCanvas.getBufferStrategy();
    }
    
    public void addToMenuPanel(Component c){
        this.menuPanel.add(c);
    }
    
    public void addToMainPanel(Component c){
        this.panel.add(c);
    }
    
    public void menuPanelVisible(Boolean visible){
        this.menuPanel.setVisible(visible);
    }
}

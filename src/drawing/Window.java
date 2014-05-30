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
    
    JPanel menuPanel = new JPanel();
    
    JButton cameraToggle = new JButton("Toggle Select");
    
    BufferStrategy bufferStrategy; 
    
    JPanel panel;
    
    public void init(){
        panel = (JPanel) frame.getContentPane();
        panel.setSize(Height - 20, Width - 20);
        
        mainCanvas.setSize(Width, Height);
        
        menuPanel.setSize(Width,Height/4);
        menuPanel.setVisible(false);
        
        cameraToggle.setSize(70, 20);
        cameraToggle.setLocation(100,100);
        
        menuPanel.add(cameraToggle);
        frame.add(menuPanel);
        
        panel.add(mainCanvas);
        panel.setBackground(Color.black);
        frame.getContentPane().add(mainCanvas);
        
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        
        
        
        mainCanvas.createBufferStrategy(2);
        mainCanvas.requestFocus();
        
        bufferStrategy = mainCanvas.getBufferStrategy();
    }
    
    public void menuPanelVisible(Boolean visible){
        this.menuPanel.setVisible(visible);
    }
}

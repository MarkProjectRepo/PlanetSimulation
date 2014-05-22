/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drawing;
import java.awt.*;
/**
 *
 * @author matra4214
 */
public class EditMenu extends Menu{
    
    int x, y;
    int Height, Width;
    boolean visible;
    Drawing drawing = new Drawing();
    
    public EditMenu(){
        
        this.x = 0;
        this.y = 0;
        //this.Height = drawing.Height/4;
        //this.Width = drawing.Width;
    }
    public void setBounds(int Height, int Width){
        this.Height = Height;
        this.Width = Width;
    }
    public void setCoords(int x, int y){
        this.y = y;
        this.x = x;
    }
    
    public void draw(Graphics g){
        g.setColor(new Color(1.0f, 1.0f, 0f, .5f));
        g.drawRect(x, y, Height, Width);
    }
}

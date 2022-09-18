/****************************************************
 * Clase que controla lo que esta en el escenario   *
 * del juego                                        *
 *               (C)2006 RomaComputer               *
 *                César Himura Elric                *
 ****************************************************/

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Actor 
{
    protected int x, y;
    protected int width, height;
    protected int frameSpeed;
    protected int t;
    protected int currentFrame;
    
    protected String[] spriteNames;
    
    protected Stage stage;
    
    protected SpriteCache spriteCache;
    
    protected boolean markedForRemoval;
    
    public Actor(Stage stage)
    {
        this.stage = stage;
        spriteCache = stage.getSpriteCache();
        currentFrame = 0;
        t = 0;
        frameSpeed = 1;
    }
    
    public void collision(Actor actor)
    {
        
    }
    
    public Rectangle getBounds()
    {
        return new Rectangle(x, y, width, height);
    }
    
    public void remove()
    {
        markedForRemoval = true;
    }
    
    public boolean isMarkedForRemoval()
    {
        return markedForRemoval;
    }
    
    public void paint(Graphics2D g)
    {
        g.drawImage(spriteCache.getSprite(spriteNames[currentFrame]), x, y, stage);
    }
    
    public int getX()
    {
        return x;
    }
    
    public void setX(int i)
    {
        x = i;
    }
    
    public int getY()
    {
        return y;
    }
    
    public void setY(int i)
    {
        y = i;
    }
    
    public void setSpriteNames(String[] names)
    {
        spriteNames = names;
        
        width = 0;
        height = 0;
        
        for(int i = 0; i < names.length; i++){
            BufferedImage image = spriteCache.getSprite(spriteNames[i]);
            
            width = Math.max(width, image.getWidth());
            height = Math.max(height, image.getHeight());
        }
    }
    
    public int getWidth()
    {
        return width;
    }
    
    public void setWidth(int i)
    {
        width = i;
    }
    
    public int getHeight()
    {
        return height;
    }
    
    public void setHeight(int i)
    {
        height = i;
    }
    
    public int getFrameSpeed()
    {
        return frameSpeed;
    }
    
    public void setFrameSpeed(int i)
    {
        frameSpeed = i;
    }
    
    public void act()
    {
        t++;
        if(t % frameSpeed == 0){
            t = 0;
            currentFrame = (currentFrame + 1) % spriteNames.length;
        }
    }
}
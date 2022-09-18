/****************************************************************
 * Videojuego GalaticWars con gràficos en 2D                    *
 * (C)2006 RomaComputer                                         *
 * Cèsar Himura Elric                                           *
 ****************************************************************/

import javax.swing.JFrame;
import javax.swing.JPanel;
        
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;

import java.net.URL;

import javax.imageio.ImageIO;

import java.io.IOException;

import java.util.HashMap;

public class GalacticWars extends Canvas
{
    private static final int MAX_WIDTH = 800;
    private static final int MAX_HEIGHT = 600;
    private static final int SPEED = 10;
    
    public HashMap sprites;
    
    public int PosX, PosY, vX;
    
    public BufferStrategy strategy;
    
    public long usedTime;
    
    public GalacticWars()
    {
        sprites = new HashMap();
        
        PosX = MAX_WIDTH/2;
        PosY = MAX_HEIGHT/2;
        
        vX = 2;
        
        JFrame frame = new JFrame("GalaticWars");
        
        JPanel panel = (JPanel)frame.getContentPane();
        
        setBounds(0, 0, MAX_WIDTH, MAX_HEIGHT);
        
        panel.setPreferredSize(new Dimension(MAX_HEIGHT, MAX_WIDTH));
        panel.setLayout(null);
        panel.add(this);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(MAX_WIDTH, MAX_HEIGHT);
        //frame.setLocation(screenWidth/4, screenHeight/4);
        frame.setIgnoreRepaint(true);
        frame.setResizable(false);
        frame.show();
        
        createBufferStrategy(2);
        strategy = getBufferStrategy();
        
        requestFocus();
    }
    
    public BufferedImage loadImage(String nameImage)
    {
        URL url = null;
        try
        {
            url = getClass().getClassLoader().getResource(nameImage);
            
            return ImageIO.read(url);
        }
        catch(IOException ex)
        {
            System.out.println("ERROR Al Cargar el Recurso:: "+nameImage+" :: " + ex);
            System.exit(0);
            return null;
        }
    }
    
    public BufferedImage getSprite(String nameImage)
    {
        BufferedImage img = (BufferedImage)sprites.get(nameImage);
        
        if(img == null)
        {
            img = loadImage("RomaComputer/graficos/"+nameImage);
            sprites.put(nameImage, img);
        }
        
        return img;
    }
    
    public void updateWorld()
    {
        PosX += vX;
        
        if(PosX < 0 || PosX > MAX_WIDTH)
            vX = -vX;
    }
    
    public void paintWorld()
    {
        Graphics g = strategy.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(),  getHeight());
        g.drawImage(getSprite("NAVE0010.gif"), PosX, PosY, this);
        
        g.setColor(Color.white);
        
        if(usedTime > 0)
            g.drawString(String.valueOf(1000/usedTime)+"fps", 0, MAX_HEIGHT-50);
        else
            g.drawString("--- fps", 0, MAX_HEIGHT-50);
        
        strategy.show();
    }
    
    public void game()
    {
        usedTime = 1000;
        
        while(isVisible())
        {
            long startTime = System.currentTimeMillis();
            updateWorld();
            paintWorld();
            
            usedTime = System.currentTimeMillis()-startTime;
            
            try
            {
                Thread.sleep(SPEED);
            }
            catch(InterruptedException ex)
            {
                System.out.println("ERROR:: " + ex);
            }
        } 
    }
    
   /* public void paint(Graphics g)
    {  /*
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(),  getHeight());
        g.drawImage(getSprite("NAVE0010.gif"), PosX, PosY, this);
        //g.drawImage(buffer, 0, 0, this);
    }
    
    public void update(Graphics g)
    {
        
    }*/
    
    public static void main(String[] args)
    {
        GalacticWars wars = new GalacticWars();
        
        wars.game();
    }
}
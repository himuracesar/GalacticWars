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

import java.net.URL;

import javax.imageio.ImageIO;

import java.io.IOException;

import java.util.HashMap;

public class GalacticWars extends Canvas
{
    private static final int MAX_WIDTH = 800;
    private static final int MAX_HEIGHT = 600;
    
    public HashMap sprites;
    
    public int PosX, PosY;
    
    public GalacticWars()
    {
        sprites = new HashMap();
        
        PosX = MAX_WIDTH/2;
        PosY = MAX_HEIGHT/2;
        
        JFrame frame = new JFrame("GalaticWars");
        
        JPanel panel = (JPanel)frame.getContentPane();
        
        setBounds(0, 0, MAX_WIDTH, MAX_HEIGHT);
        
        panel.setPreferredSize(new Dimension(MAX_HEIGHT, MAX_WIDTH));
        panel.setLayout(null);
        panel.add(this);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(MAX_WIDTH, MAX_HEIGHT);
        //frame.setLocation(screenWidth/4, screenHeight/4);
        frame.setResizable(false);
        frame.show();
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
        PosX = (int)(Math.random() * MAX_WIDTH);
        PosY = (int)(Math.random() * MAX_HEIGHT);
    }
    
    public void game()
    {
        while(isVisible())
        {
            updateWorld();
            paint(getGraphics());
        }
    }
    
    public void paint(Graphics g)
    {   
        g.drawImage(getSprite("NAVE0010.gif"), PosX, PosY, this);
        /*g.setColor(Color.RED);
        g.fillOval(MAX_WIDTH/2-10, MAX_HEIGHT/2-10, 20, 20);*/
    }
    
    public static void main(String[] args)
    {
        GalacticWars wars = new GalacticWars();
        
        wars.game();
    }
}
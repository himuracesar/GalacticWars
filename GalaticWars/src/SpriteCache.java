/*****************************************************************
 * Clase que gestiona el cache del Videojuego con gràficos en 2D *
 *                   (C)2006 RomaComputer                        *
 *                    César Himura Elric                         *
 *****************************************************************/

import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;

import java.net.URL;

import javax.imageio.ImageIO;

import java.io.IOException;

import java.util.HashMap;

public class SpriteCache extends ResourceCache implements ImageObserver
{
    //private HashMap sprites;
    
    public SpriteCache()
    {
        //sprites = new HashMap(); 
    }
    
    protected Object loadResource(URL url)
    {
        //URL url = null;
        try
        {
            //url = getClass().getClassLoader().getResource(nameImage);
            return ImageIO.read(url);
        }
        catch(IOException ex)
        {
            System.out.println("ERROR Al Cargar el Recurso:: "+url+" :: " + ex);
            System.exit(0);
            return null;
        }
    }
    
    public BufferedImage getSprite(String nameImage)
    {
        /*BufferedImage img = (BufferedImage)sprites.get(nameImage);
        
        if(img == null)
        {
            img = loadImage("RomaComputer/graficos/"+nameImage);
            sprites.put(nameImage, img);
        }
        
        return img;*/
        
        BufferedImage loaded = (BufferedImage)getResource(nameImage);
        BufferedImage compatible = createCompatible(loaded.getWidth(), loaded.getHeight(), Transparency.BITMASK);
        
        Graphics g = compatible.getGraphics();
        g.drawImage(loaded, 0, 0, this);
        
        return compatible;
    }
    
    public BufferedImage createCompatible(int width, int height, int transparency)
    {
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        
        BufferedImage compatible = gc.createCompatibleImage(width, height, transparency);
        
        return compatible;
    }
    
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int w, int h)
    {
        return (infoflags & (ALLBITS|ABORT)) == 0;
    }
}
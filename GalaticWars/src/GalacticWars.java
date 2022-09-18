/****************************************************************
 * Videojuego GalaticWars con gràficos en 2D                    *
 * (C)2006 RomaComputer                                         *
 * Cèsar Himura Elric                                           *
 ****************************************************************/

import javax.swing.JFrame;
import javax.swing.JPanel;
        
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.Point;
import java.awt.Cursor;
import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.ArrayList;

public class GalacticWars extends Canvas implements Stage, KeyListener
{
    private BufferStrategy strategy;
    
    private long usedTime;
    
    private SpriteCache spriteCache;
    
    private ArrayList actors;
    
    private Player player;
    
    private Explosion explosion;
    
    private boolean gameEnded = false;
    private boolean gameBegin = false;
    private boolean presents = true;
    private static boolean teclazo = false;
    
    private static final int max_cometas = 80;
    private static final int max_enemigos = 10;
    
    private SoundCache sound;
    
    public GalacticWars()
    {
        spriteCache = new SpriteCache();
        
        sound = new SoundCache();
        
        JFrame frame = new JFrame("GalaticWars Ver 1 - RomaComputer");
        
        JPanel panel = (JPanel)frame.getContentPane();
        
        setBounds(0, 0, Stage.MAX_WIDTH, Stage.MAX_HEIGHT);
        
        panel.setPreferredSize(new Dimension(Stage.MAX_HEIGHT, Stage.MAX_WIDTH));
        panel.setLayout(null);
        panel.add(this);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Stage.MAX_WIDTH, Stage.MAX_HEIGHT);
        //frame.setLocation(screenWidth/4, screenHeight/4);
        frame.setIgnoreRepaint(true);
        frame.setResizable(false);
        frame.setVisible(true);
        
        createBufferStrategy(2);
        strategy = getBufferStrategy();
        
        requestFocus();
        
        addKeyListener(this);
        
        setIgnoreRepaint(true);
        
        BufferedImage cursor = spriteCache.createCompatible(10, 10, Transparency.BITMASK);
        
        Toolkit t = Toolkit.getDefaultToolkit();
        
        Cursor c = t.createCustomCursor(cursor, new Point(5, 5), "null");
        setCursor(c);
    }
    
    public void checkCollisions()
    {
        Rectangle playerBounds = player.getBounds();
        
        for(int i = 0; i < actors.size(); i++)
        {
            Actor actor01 = (Actor)actors.get(i);
            Rectangle rect01 = actor01.getBounds();
            
            if(rect01.intersects(playerBounds))
            {
                player.collision(actor01);
                actor01.collision(player);
            }
            
            for(int j = i+1; j < actors.size(); j++)
            {
                Actor actor02 = (Actor)actors.get(j);
                Rectangle rect02 = actor02.getBounds();
            
                if(rect01.intersects(rect02))
                {
                    actor01.collision(actor02);
                    actor02.collision(actor01);
                }
            }
        }
    }
    
    public void gameOver()
    {
        gameEnded = true;
    }
    
    public void keyPressed(KeyEvent evt)
    {
        /*
        if(evt.getKeyCode() == KeyEvent.VK_ENTER && !gameBegin)
            gameBegin = true;
        
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE)
            System.exit(0);
        
        if(evt.getKeyCode() == KeyEvent.VK_ENTER && gameEnded)
        {
            gameEnded = false;
            teclazo = true;
        }*/

        if(player != null)
            player.keyPressed(evt);
    }
    
    public void keyReleased(KeyEvent evt)
    {
        if(evt.getKeyCode() == KeyEvent.VK_ENTER && !gameBegin)
            gameBegin = true;

        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE)
            System.exit(0);

        if(evt.getKeyCode() == KeyEvent.VK_ENTER && gameEnded)
        {
            gameEnded = false;
            teclazo = true;
        }

        if(player != null)
            player.keyReleased(evt);
    }
    
    public void keyTyped(KeyEvent evt)
    {
        
    }
    
    public void addActor(Actor actor)
    {
        actors.add(actor);
    }
    
    public void initWorld()
    {
        actors = new ArrayList();
        
        for(int i = 0; i < max_enemigos; i++)
        {
            Enemigo_01 ene01 = new Enemigo_01(this);
            ene01.setX((int)(Math.random() * (Stage.MAX_WIDTH-50)));
            ene01.setY(-5);

            int vy = 0;

            do{
                vy = (int)(Math.random() * 10);

                ene01.setVy(vy);
            }while(vy < 5);

            actors.add(ene01);
        }//for
        
        player = new Player(this);
        player.setX(Stage.MAX_WIDTH/2);
        player.setY(Stage.MAX_HEIGHT - 2 * player.getHeight());
        
        for(int i = 0; i <= max_cometas; i++)
        {
            Cometa cometa = new Cometa(this);
            cometa.setX((int)(Math.random() * Stage.MAX_WIDTH));
            cometa.setY((int)(Math.random() * Stage.MAX_HEIGHT));
            cometa.setVy((int)(Math.random() * 10));
            actors.add(cometa);
        }
        
        for(int i = 0; i < 2; i++)
        {
            Enemigo_02 enemigo = new Enemigo_02(this);
            enemigo.setX(0);
            enemigo.setY((enemigo.getHeight() * i * -1) - enemigo.getHeight());
            
            actors.add(enemigo);
            
            Enemigo_02 enemigoII = new Enemigo_02(this);
            enemigoII.setX(Stage.MAX_WIDTH - enemigoII.getWidth());
            enemigoII.setY((enemigoII.getHeight() * i * -1) - enemigoII.getHeight());
            
            actors.add(enemigoII);
        }
        
        //sound.loopSound("sonidos/musica.wav");
    }
    
    public void updateWorld()
    {
        int i = 0;
        while(i < actors.size())
        {
            Actor actor = (Actor)actors.get(i);
            if(actor.isMarkedForRemoval())
            {
                actors.remove(i);
            }
            else
            {
                actor.act();
                i++;
            }
        }
        player.act();
    }
    
    public void paintWorld()
    {
        Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        for(int i = 0; i < actors.size(); i++)
        {
            Actor enemigo = (Actor)actors.get(i);
            enemigo.paint(g);
        }
        
        player.paint(g);
        
        paintStatus(g);

        strategy.show();
    }
    
    public void paintGameOver()
    {
        Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("GAME OVER", 300, Stage.MAX_HEIGHT/2);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString("Presiona ENTER para iniciar una nueva partida o ESCAPE para salir", 200, Stage.MAX_HEIGHT/2 + 50);
        strategy.show();
        
        try
        {
            while(!teclazo)
                Thread.sleep(10);
        }
        catch(InterruptedException ex)
        {
            System.out.println("ERROR:: " + ex);
        }
    }
    
    public void paintShields(Graphics2D g)
    {
        g.setPaint(Color.red);
        g.fillRect(280, Stage.PLAY_HEIGHT+30, player.MAX_SHIELDS, 20);
        g.setPaint(Color.blue);
        g.fillRect(280 + Player.MAX_SHIELDS - player.getShields(), Stage.PLAY_HEIGHT+30, player.getShields(), 20);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setPaint(Color.green);
        g.drawString("Shield", 170, Stage.PLAY_HEIGHT+50);
    }
    
    public void paintScore(Graphics2D g)
    {
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setPaint(Color.green);
        g.drawString("Score", 20, Stage.PLAY_HEIGHT + 50);
        g.setPaint(Color.red);
        g.drawString(player.getScore() + "", 100, Stage.PLAY_HEIGHT+50);
    }
    
    public void paintPresent()
    {
        try
        {
            Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(),  getHeight());
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.setPaint(Color.white);
            g.drawString("RomaComputer Enterteinment", 200, Stage.MAX_HEIGHT/2);
            g.setFont(new Font("Arial", Font.BOLD, 25));
            g.drawString("presents", 350, Stage.MAX_HEIGHT/2 + 30);
            strategy.show();
            System.out.println("PROCESO DORMIDO...");
            Thread.sleep(5000);
            
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(),  getHeight());
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.setPaint(Color.white);
            g.drawString("a Cesar Himura game", 295, Stage.MAX_HEIGHT/2);
            strategy.show();
            System.out.println("PROCESO DORMIDO...");
            Thread.sleep(5000);
            
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(),  getHeight());
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.setPaint(Color.YELLOW);
            g.drawString("GALACTIC WARS", 200, Stage.MAX_HEIGHT/2);
            g.setPaint(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("presione Enter para iniciar el Juego", 250, Stage.MAX_HEIGHT/2 + 50);
            
            g.setPaint(Color.GREEN);
            g.setFont(new Font("Arial", Font.BOLD, 15));
            g.drawString("©2006 RomaComputer", 350, Stage.MAX_HEIGHT - 90);
            g.drawString("Galactic Wars® es propiedad de Cesar Himura Elric", 250, Stage.MAX_HEIGHT - 70);
            g.drawString("Se prohibe su copia total o parcial", 300, Stage.MAX_HEIGHT - 50);
            
            strategy.show();
            System.out.println("PROCESO DORMIDO...");
            while(!gameBegin)
                Thread.sleep(20);
        }
        catch(InterruptedException ex)
        {
            System.out.println("ERROR:: " + ex);
        }
    }
    
    public void paintFps(Graphics2D g)
    {
        g.setFont(new Font("Arial", Font.BOLD, 12));
        g.setColor(Color.white);
        
        if(usedTime > 0)
            g.drawString(String.valueOf(1000/usedTime)+"fps", Stage.MAX_WIDTH-50, Stage.PLAY_HEIGHT + 50);
        else
            g.drawString("--- fps", Stage.MAX_WIDTH - 50, Stage.PLAY_HEIGHT + 50);
    }
    
    public void paintStatus(Graphics2D g)
    {
        paintScore(g);
        paintShields(g);
        paintFps(g);
    }
    
    public void game(boolean presenta)
    {
        usedTime = 1000;
        
        if(presenta)
            paintPresent();
        
        initWorld();
        
        while(isVisible() && !gameEnded)
        {
            long startTime = System.currentTimeMillis();
            updateWorld();
            checkCollisions();
            paintWorld();
            
            usedTime = System.currentTimeMillis() - startTime;
            
            do
            {
                Thread.yield();
            }while(System.currentTimeMillis() - startTime < 17);
            /*try
            {
                Thread.sleep(SPEED);
            }
            catch(InterruptedException ex)
            {
                System.out.println("ERROR:: " + ex);
            }*/
        }
        paintGameOver();
    }
    
    public SoundCache getSoundCache()
    {
        return sound;
    }
    
    public SpriteCache getSpriteCache()
    {
        return spriteCache;
    }
    
    public Player getPlayer()
    {
        return player;
    }
    
    public int getWidthPlayer()
    {
        return player.getWidth();
    }
    
    public int getHeightPlayer()
    {
        return player.getHeight();
    }

    public static void main(String[] args)
    {
         GalacticWars wars = new GalacticWars(); 
        
         boolean reinicio = true;
         
         while(reinicio)
         {
            wars.game(wars.presents);
            
            if(!teclazo)
                reinicio = false;
            else
                wars.presents = false;
         }
         System.out.println("TERMINA...");
    }
}
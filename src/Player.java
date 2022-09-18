/**************************************************
 * Clase que controla al Jugador en el videojuego *
 *                (C)2006 RomaComputer            *
 *                 César Himura Elric             *
 **************************************************/

import java.awt.event.KeyEvent;

public class Player extends Actor
{
    protected int vx;
    protected int vy;
    private int score;
    private int shields;
    
    protected static final int PLAYER_SPEED = 4;
    protected static final int MAX_SHIELDS = 200;
    
    private boolean up, down, right, left;
    
    public Player(Stage stage)
    {
        super(stage);
        setSpriteNames(new String[] {"graficos/NAVE0010.gif"});
        shields = MAX_SHIELDS;
        score = 0;
    }
    
    public void act()
    {
        super.act();
        
        x += vx;
        y += vy;
        
        if(x < 0)
        {
            x = 0;
        }
        if(x > Stage.MAX_WIDTH - getWidth())
        {
            x = Stage.MAX_WIDTH - getWidth();
        }
        if(y > Stage.MAX_HEIGHT - getHeight())
        {
            y = Stage.MAX_HEIGHT - getHeight();
        }
    }
    
    public void collision(Actor actor)
    {
        if(actor instanceof Enemigo_01)
        {
            actor.remove();
            addScore(20);
            addShields(-40);
            
            Explosion explosion = new Explosion(stage);
            explosion.setX(x - 20 + getWidth()/2);
            explosion.setY(y + getHeight()/2);
            explosion.setVx(getVx());
            explosion.setVy(getVy());
            
            stage.addActor(explosion);
            
            Explosion explosion01 = new Explosion(stage);
            explosion01.setX(actor.getX() - 10 + actor.getWidth()/2);
            explosion01.setY(actor.getY() + actor.getHeight()/2);
            //explosion01.setVx(actor.getVx());
            //explosion01.setVy(getVy());
            
            stage.addActor(explosion01);
        }
        if(actor instanceof ShootEnemy)
        {
            actor.remove();
            addShields(-10);
            
            Explosion explosion = new Explosion(stage);
            explosion.setX(x - 20 + getWidth()/2);
            explosion.setY(y + getHeight()/2);
            explosion.setVx(getVx());
            explosion.setVy(getVy());
            
            stage.addActor(explosion);
        }
        if(actor instanceof Enemigo_02)
        {
            actor.remove();
            addShields(-25);
            
            Explosion explosion = new Explosion(stage);
            explosion.setX(x - 20 + getWidth()/2);
            explosion.setY(y + getHeight()/2);
            explosion.setVx(getVx());
            explosion.setVy(getVy());
            
            stage.addActor(explosion);
            
            stage.getSoundCache().playSound("sonidos/explosion.wav");
            
            Explosion explosion01 = new Explosion(stage);
            explosion01.setX(actor.getX() - 10 + actor.getWidth()/2);
            explosion01.setY(actor.getY() + actor.getHeight()/2);
            
            stage.addActor(explosion01);
            
            stage.getSoundCache().playSound("sonidos/explosion.wav");
        }
        if(getShields() < 0)
            stage.gameOver();
    }
    
    protected void updateSpeed()
    {
        vx = 0;
        vy = 0;
        
        if(down)
        {
            vy = PLAYER_SPEED;
        }
        if(up)
        {
            vy = -PLAYER_SPEED;
        }
        if(left)
        {
            vx = -PLAYER_SPEED;
        }
        if(right)
        {
            vx = PLAYER_SPEED;
        }
    }
    
    public void keyReleased(KeyEvent evt)
    {
        switch(evt.getKeyCode())
        {
            case KeyEvent.VK_DOWN: down = false;
                                   break;
            case KeyEvent.VK_UP: up = false;
                                 break;
            case KeyEvent.VK_LEFT: left = false;
                                   break;
            case KeyEvent.VK_RIGHT: right = false;
                                    break;
            case KeyEvent.VK_SPACE: fire();
                                    break;
        }
        updateSpeed();
    }
    
    public void keyPressed(KeyEvent evt)
    {
        switch(evt.getKeyCode())
        {
            case KeyEvent.VK_DOWN: down = true;
                                   break;
            case KeyEvent.VK_UP: up = true;
                                 break;
            case KeyEvent.VK_LEFT: left = true;
                                   break;
            case KeyEvent.VK_RIGHT: right = true;
                                    break;
            /*case KeyEvent.VK_SPACE: fire();
                                    break;*/
        }
        updateSpeed();
    }
    
    public void fire()
    {
        Bullet disparoIzq = new Bullet(stage);
        Bullet disparoDer = new Bullet(stage);
        
        disparoIzq.setX(x + 8);
        disparoIzq.setY(y + disparoIzq.getHeight());
        stage.addActor(disparoIzq);
        
        disparoDer.setX(x + 80);
        disparoDer.setY(y + disparoDer.getHeight());
        stage.addActor(disparoDer);
        
        stage.getSoundCache().playSound("sonidos/missile.wav");
    }
    
    public int getVx()
    {
        return vx;
    }
    
    public void setVx(int i)
    {
        vx = i;
    }
    
    public int getVy()
    {
        return vy;
    }
    
    public void setVy(int i)
    {
        vy = i;
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public int getScore()
    {
        return score;
    }
    
    public void addScore(int i)
    {
        score += i;
    }
    
    public int getShields()
    {
        return shields;
    }
    
    public void setShields(int i)
    {
        shields = i;
    }
    
    public void addShields(int i)
    {
        shields += i; 
    }
}
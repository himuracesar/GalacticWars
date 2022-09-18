/********************************************************
 * Interface que compone el escenario del juego         *
 *               (C)2006 RomaComputer                   *
 *                César Himura Elric                    *
 ********************************************************/

import java.awt.image.ImageObserver;

public interface Stage extends ImageObserver
{
    public static final int MAX_WIDTH = 800;
    public static final int MAX_HEIGHT = 600;
    public static final int SPEED = 10;
    public static final int PLAY_HEIGHT = 500;
    
    public SpriteCache getSpriteCache();
    
    public void addActor(Actor actor);
    
    public Player getPlayer();
    
    public void gameOver();
    
    public SoundCache getSoundCache();
}
/**********************************************************
 * Clase que gestiona los disparos de la Nave del jugador *
 *                   (C)2006 RomaComputer                 *
 *                    César Himura Elric                  *
 **********************************************************/

public class Bullet extends Actor
{
    protected static final int BULLET_SPEED = 10;
    
    public Bullet(Stage stage)
    {
        super(stage);
        setSpriteNames(new String[]{"graficos/BALA_NAVE.gif"});
    }
    
    public void act()
    {
        super.act();
        
        y -= BULLET_SPEED;
        
        if(y < 0)
            remove();
    }
    
    public void collision(Actor actor)
    {
        if(actor instanceof Enemigo_01 || actor instanceof Enemigo_02)
            remove();
    }
}
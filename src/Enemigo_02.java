/*************************************************
 * Clase que gestiona a los Enemigos del tipo 2  *
 *              (C)2006 RomaComputer             *
 *               César Himura Elric              *
 *************************************************/

public class Enemigo_02 extends Actor
{
    private static final int espera = 1000;
    
    private int vY = 2;
    private int vX = 0;
    private int espera_aux = 0;
    
    private Player player = stage.getPlayer();
    
    private boolean ataque = false;
    
    public Enemigo_02(Stage stage)
    {
        super(stage);
        setSpriteNames(new String[]{"graficos/ENE2_1.gif"});
    }
    
    public void act()
    {
       if(espera_aux > espera)
       {
            super.act();
        
            y += vY;
            
            if(player.getY() - 30 <= getY())
            {
                if(getX() > Stage.MAX_WIDTH/2 && !ataque)
                {
                    vX = -5;
                    ataque = true;
                }
                if(getX() < Stage.MAX_WIDTH && !ataque)
                {
                    vX = 5;
                    ataque = true;
                }
            }
            
            x += vX;
            
            if(y > Stage.MAX_HEIGHT || x > Stage.MAX_WIDTH || x < 0)
            {
                remove();
                spawn();
            }
        }
        else
            espera_aux++;
    }
    
    public void spawn()
    {
        Enemigo_02 enemigo = new Enemigo_02(stage);
        enemigo.setX(0);
        enemigo.setY((enemigo.getHeight() * -1) - enemigo.getHeight());
        
        stage.addActor(enemigo);
        
        Enemigo_02 enemigoII = new Enemigo_02(stage);
        enemigoII.setX(Stage.MAX_WIDTH - enemigoII.getWidth());
        enemigoII.setY((enemigoII.getHeight() * -1) - enemigoII.getHeight());
        
        stage.addActor(enemigoII);
    }
    
    public void collision(Actor actor)
    {
        if(actor instanceof Bullet)
        {
            remove();
            stage.getPlayer().addScore(25);
            
            Explosion explosion = new Explosion(stage);
            explosion.setX(x - 10 + getWidth()/2);
            explosion.setY(y + getHeight()/2);
            explosion.setVy(getVy());
            explosion.setVx(getVx());
            
            stage.addActor(explosion);
            
            stage.getSoundCache().playSound("sonidos/explosion.wav");
            
            spawn();
        }
    }
    
    public void setVy(int i)
    {
        vY = i;
    }
    
    public int getVy()
    {
        return vY;
    }
    
    public void setVx(int i)
    {
        vX = i;
    }
    
    public int getVx()
    {
        return vX;
    }
}
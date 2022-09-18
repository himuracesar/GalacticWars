/***************************************************
 * Clase que gestiona el primer tipo de enemigo    *
 * dentro del juego                                *
 *            (C)2006 RomaComputer                 *
 *             César Himura Elric                  *
 ***************************************************/

public class Enemigo_01 extends Actor
{
    protected int vY = 2;
    protected int vX = 0;
    
    protected static final double FIRING_FRECUENCY = 0.01;
    
    private Player player = null;

    private Stage st = null;
    
    public Enemigo_01(Stage stage)
    {
        super(stage);

        st = stage;

        setSpriteNames(new String[] {"graficos/ENE1_1.gif"});
    }
    
    public void collision(Actor actor)
    {
        if(actor instanceof Bullet)
        {
            remove();
            stage.getPlayer().addScore(20);
            
            Explosion explosion = new Explosion(stage);
            explosion.setX(x - 10 + getWidth()/2);
            explosion.setY(y + getHeight()/2);
            explosion.setVy(getVy());
            //explosion.setVy(getVy());
            
            stage.addActor(explosion);
            
            spawn();
        }
    }
    
    public void spawn()
    {
        Enemigo_01 enemigo = new Enemigo_01(st);
        enemigo.setX((int)(Math.random() * Stage.MAX_WIDTH));
        enemigo.setY(-5);

        int vy = 0;

        do{
            vy = (int)(Math.random() * 10);

            enemigo.setVy(vy);
        }while(vy < 5);

        stage.addActor(enemigo);
        
        stage.getSoundCache().playSound("sonidos/explosion.wav");
    }
    
    public void act()
    {
        super.act();
        
        y += vY;
                
        if(y > stage.MAX_WIDTH)
        {
            remove();
            spawn();
        }
        if(vY == 0)
            vY = 2;
        
       /* if(x > Stage.MAX_WIDTH || x < 0)
        {
            vX = -vX;
        }*/
        
        if(Math.random() < FIRING_FRECUENCY)
            fire();
    }
    
    public void fire()
    {
        ShootEnemy shoot = new ShootEnemy(st);
        shoot.setX(x-5 + getWidth()/2);
        shoot.setY(y-5 + getHeight());
        
        player = stage.getPlayer();
        shoot.setTarget(shoot.getX(), shoot.getY(), player.getX() + player.getWidth()/2, player.getY() + player.getHeight()/2, vY);
        
        stage.addActor(shoot);
        
        stage.getSoundCache().playSound("sonidos/photon.wav");
    }
    
    public int getVx()
    {
        return vX;
    }
    
    public void setVx(int i)
    {
        vX = i;
    }
    
    public int getVy()
    {
        return vY;
    }
    
    public void setVy(int i)
    {
        vY = i;
    }
}
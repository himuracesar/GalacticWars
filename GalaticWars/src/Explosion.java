/********************************************************
 * Clase que gestiona las explosiones en el videojuego  *
 *                (C)2006 RomaComputer                  *
 *                 César Himura Elric                   *
 ********************************************************/

public class Explosion extends Actor
{
    protected static final int duracion = 50;
    
    private int duracion_aux = 0;
    private int vX;
    private int vY;
    
    public Explosion(Stage stage)
    {
        super(stage);
        setSpriteNames(new String[]{"graficos/EXPLO1.gif","graficos/EXPLO2.gif","graficos/EXPLO3.gif","graficos/EXPLO4.gif","graficos/EXPLO5.gif","graficos/EXPLO6.gif",
                                    "graficos/EXPLO7.gif","graficos/EXPLO8.gif","graficos/EXPLO9.gif","graficos/EXPLO10.gif","graficos/EXPLO11.gif",});
    }
    
    public void act()
    {
        super.act();
        
        if(duracion_aux < duracion)
        {
            duracion_aux++;
        }
        else
        {
            remove();
        }
        
        y += vY;
                
        if(y > stage.MAX_HEIGHT)
        {
            remove();
        }
    }
    
    public void setVx(int i)
    {
        vX = i;
    }
    
    public int getVx()
    {
        return vX;
    }
    
    public void setVy(int i)
    {
        vY = i;
    }
    
    public int getVy()
    {
        return vY;
    }
}
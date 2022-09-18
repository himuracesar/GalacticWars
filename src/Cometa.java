/******************************************************
 * Clase que gestiona los Cometa para el Juego de     *
 * GalacticWars                                       *
 *                 (C)2006 RomaComputer               *
 *                  César Himura Elric                *
 ******************************************************/

public class Cometa extends Actor
{
    private String[] cometas = {"graficos/cometa1.gif", "graficos/cometa2.gif", "graficos/cometa3.gif", "graficos/cometa4.gif"};
    
    private int vY = 3;
    
    public Cometa(Stage stage)
    {
        super(stage);
        
        int index = (int)(Math.random() * 3);
        setSpriteNames(new String[]{cometas[index]});
    }
    
    public void act()
    {
        super.act();
        
        y += vY;
        
        if(y > Stage.MAX_HEIGHT)
        {
            remove();
            spawn();
        }
        if(vY == 0)
            vY = 3;
    }
    
    public void spawn()
    {
        Cometa cometin = new Cometa(stage);
        cometin.setX((int)(Math.random() * Stage.MAX_WIDTH));
        cometin.setY(-5);
        cometin.setVy((int)(Math.random() * 10));
        
        stage.addActor(cometin);
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
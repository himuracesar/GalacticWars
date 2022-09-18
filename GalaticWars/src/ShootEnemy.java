/*******************************************************
 * Clase que controla el disparo (gráfico) del enemigo *
 *                 (C)2006 RomaComputer                *
 *                  César Himura Elric                 *
 *******************************************************/

 public class ShootEnemy extends Actor
 {
    private static int BULLET_SPEED_Y = 3;
    private static int BULLET_SPEED_X = 0;
    
    private int dx = 0;
    private int dy = 0;
    private int dx_abs = 0;
    private int dy_abs = 0;
    
    public ShootEnemy(Stage stage)
    {
        super(stage);
        setSpriteNames(new String[]{"graficos/BALA_ENEMY_1.gif", "graficos/BALA_ENEMY_2.gif", "graficos/BALA_ENEMY_3.gif", "graficos/BALA_ENEMY_4.gif", "graficos/BALA_ENEMY_5.gif"});
        setFrameSpeed(10);
    }
    
    public void act()
    {
        super.act();
        
        y += BULLET_SPEED_Y;
        x += BULLET_SPEED_X;
        
        if(y > Stage.PLAY_HEIGHT || x > Stage.MAX_WIDTH || x < 0)
            remove();
    }
    
    public void setTarget(int x1, int y1, int x2, int y2, int velocidad)
    {
        /*System.out.println("ORIGEN:: "+x1+", "+y1);
        System.out.println("TARGET:: "+x2+", "+y2);*/

        BULLET_SPEED_Y = velocidad * 2;

        /*double angle = 0;
        double dx = x2 - x1;
        double dy = y2 - y1;
        double d = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));

        if (dx > 0) {
            angle = Math.atan(dy / dx);
        } else if (dx < 0) {
            angle = Math.atan(dy / dx) + Math.PI;
        } else if (dy < 0) {
            angle = -Math.PI / 2;
        } else {
            angle = Math.PI / 2;
        }

        angle = angle % (Math.PI * 2d);
        if (angle >= Math.PI) {
            angle = angle - 2d * Math.PI;
        }

        double radAngle = Math.toRadians(angle);

        BULLET_SPEED_X = (int)(d * Math.cos(radAngle)/100);
        BULLET_SPEED_Y = (int)(d * Math.sin(radAngle));*/
        //System.out.println("*** BULLET_SPEED_X:: " + BULLET_SPEED_X);
        //System.out.println("*** BULLET_SPEED_Y:: " + BULLET_SPEED_Y);

        /*dx = x2 - x1;
        dy = y2 - y1;
        
        dx_abs = Math.abs(dx) + 1;
        dy_abs = Math.abs(dy) + 1;
        //System.out.println("DISTANCIAS:: "+dx+", "+dy);
        if(dx_abs > dy_abs)
        {
            if(dx > 0)
            {
                BULLET_SPEED_X = velocidad;
            }
            else
            {
                BULLET_SPEED_X = -velocidad;
                BULLET_SPEED_Y = (dy * velocidad)/dx;
            }
        }
        else
        {
            if(dy > 0)
            {
                BULLET_SPEED_Y = velocidad;
            }
            else
            {
                BULLET_SPEED_Y = -velocidad;
                BULLET_SPEED_X = (dx * velocidad)/dy;
            }
        }*/
    }
 }
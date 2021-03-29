import java.awt.Color;
import java.awt.Graphics2D;

public class Bullet {
    private float x;
    private float y;
    private Sprite sprite;
    private int speed;
    private int r=5;

    Bullet(float x,float y,int speed){
        this.x=x+23;
        this.y=y-20;
        this.speed=speed;
    }

    public float getY(){return y;}

    public float getX(){return x;}

    public int getR(){return r;}

    public void update(){
        y-=speed;
    }

    public void render(Graphics2D g){
        g.setColor(Color.BLUE);
        g.fillOval((int)x,(int)y,r,r);
    }
}

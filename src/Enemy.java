import java.awt.Graphics2D;
import java.util.Random;

public class Enemy {

    private float x;
    private float y;
    private float speed;
    private Random rand;
    private int r=13;
    private int health=1;
    private Sprite sprite;

    public Enemy(float speed,TextureAtlas atlas_comet){
        rand=new Random();
        x=rand.nextInt(500);
        y=0;
        this.speed=speed;
        sprite=new Sprite(atlas_comet.cut(0,0,265,308),0.1f);


    }

    public float getY(){return y;}

    public float getX(){return x;}

    public int getR(){return r;}

    public void hit(){health--;};

    public boolean remove(){
        if(health==0)
            return true;
        return false;
    }

    public void update(){
        y+=speed;
    }

    public void render(Graphics2D g){
        sprite.render(g,x,y);
    }
}

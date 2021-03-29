
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class Player {

    public static final int SPRITE_SCALE=20;
    private float scale;
    private float speed;
    private float x;
    private float y;
    private Sprite sprite;
    private int health=100;
    private int Scale=0;

    public Player(float x, float y,float scale,float speed,TextureAtlas atlas){
        this.x=x;
        this.y=y;
        this.scale=scale;
        this.speed=speed;
        sprite=new Sprite(atlas.cut(0,0,108,93),0.5f);
    }

    public void hit(){
        if(health>0)
            health-=25;
    }

    public float GetX(){
        return x;
    }

    public float GetY(){
        return y;
    }

    public int getHealth(){return health;}

    public void showHealth(Graphics2D g){
        g.setColor(Color.BLUE);
        g.drawString("Health:"+String.valueOf(health), 630, 460);
    }

    public void killEnemy(){
        Scale++;
    }

    public void showScale(Graphics2D g){
        g.setColor(Color.BLUE);
        g.drawString("Scale:"+String.valueOf(Scale), 630, 480);
    }
    public void update(Input input){

        float newX=x;
        float newY=y;

        if(input.getKey(KeyEvent.VK_UP)){
            newY-=speed;
        }
        if(input.getKey(KeyEvent.VK_RIGHT)){
            newX+=speed;
        }
        if(input.getKey(KeyEvent.VK_LEFT)){
            newX-=speed;
        }

        if(input.getKey(KeyEvent.VK_DOWN)){
            newY+=speed;
        }


        if(newX<0){
            newX=0;
        }else if(newX>=Game.WIDTH - (SPRITE_SCALE+5)*scale){
            newX=Game.WIDTH - (SPRITE_SCALE+5)*scale;
        }

        if(newY<0){
            newY=0;
        }else if(newY>=Game.HEIGHT - SPRITE_SCALE*scale){
            newY=Game.HEIGHT - SPRITE_SCALE*scale;
        }

        x=newX;
        y=newY;

    }

    public void render(Graphics2D g){
        sprite.render(g, x, y);
    }
}

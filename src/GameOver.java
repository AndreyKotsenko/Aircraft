
import java.awt.Graphics2D;

public class GameOver {
    private float x;
    private float y;
    Sprite sprite;

    public GameOver(TextureAtlas atlas, float x,float y){
        sprite=new Sprite(atlas.cut(36*8, 23*8, 4*8, 2*8),2.0f);
        this.x=x;
        this.y=y;
    }


    public void render(Graphics2D g){
        sprite.render(g, x, y);
    }

}

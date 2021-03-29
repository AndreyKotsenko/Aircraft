
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Sprite {
    private float scale;
    BufferedImage image;

    public Sprite(BufferedImage image,float scale){
        this.scale=scale;
        this.image=image;
        for(int i=0;i<image.getHeight();i++)
            for(int j=0;j<image.getWidth();j++)
            {
                int pixel =image.getRGB(j,i);
                if ((pixel & 0x00FFFFFF) < 10) // пиксель на прозрачный
                    image.setRGB(j, i, (pixel & 0x00FFFFFF));
            }
    }

    public void render(Graphics2D g,float x,float y){
        g.drawImage(image,(int)(x),(int)(y),(int)(image.getWidth()*scale),(int)(image.getHeight()*scale),null);
    }
}

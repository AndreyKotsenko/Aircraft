

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Game implements Runnable {

    public static final int WIDTH=700;
    public static final int HEIGHT=500;
    public static final String TITLE="Aircraft";
    public static final int CLEAR_COLOR=0xff000000;
    public static final int NUM_BUFFERS=3;

    public static final float UPDATE_RATE=60.0f;// сколько раз в секунду хотим испл UPDATE
    public static final float UPDATE_INTERVAL=Time.SECOND/UPDATE_RATE;// время между апдейтами
    public static final long IDLE_TIME=1;// время на отдих процесу

    public static final String ATLAS_FILE_NAME ="shooting-bullets-tutorial.png";
    public static final String COMET ="C__fakepath_kometa-148317583536466.png";

    private boolean running;
    private Thread gameThread;
    private Graphics2D graphics;
    private Input input;
    private static TextureAtlas atlas;
    private static TextureAtlas atlas_comet;

    public static Player player;
    public static ArrayList<Bullet> bullets;
    public static ArrayList<Enemy> enemy;

    private static int Count;
    private static int Count2=0;
    private double dist;
    private BufferedImage gameoverImage;
    private GameOver gameover;
    private static boolean game;

    public Game(){
        running=false;
        Display.create(WIDTH, HEIGHT, TITLE, CLEAR_COLOR,NUM_BUFFERS);
        graphics=Display.getGraphics();
        input=new Input();
        Display.addInputListener(input);
        atlas=new TextureAtlas(ATLAS_FILE_NAME);
        atlas_comet=new TextureAtlas(COMET);
        player=new Player(300,300,2,3,atlas);
        bullets=new ArrayList<Bullet>();
        enemy=new ArrayList<Enemy>();
        gameover=new GameOver(new TextureAtlas("texture_atlas.png"),325,250);
        game=true;
    }

    public  synchronized void start(){
        if(running)
            return;

        running=true;
        gameThread=new Thread(this);
        gameThread.start();// запускает метод ран
    }

    public synchronized void stop(){
        if(!running)
            return;

        running=false;
        try{
            gameThread.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        cleanUp();
    }

    private void update(){
        player.update(input);
        for(int i=0;i<bullets.size();i++)
        {
            bullets.get(i).update();
        }

        for(int i=0;i<enemy.size();i++)
        {
            enemy.get(i).update();
        }

        Count2++;
        if(Count2==40)
        {enemy.add(new Enemy(2,atlas_comet));
            Count2=0;
        }
        if(!input.getKey(KeyEvent.VK_F))
            Count=0;

        if(input.getKey(KeyEvent.VK_F))
        {
            if(Count==0||Count==30)
            {bullets.add(new Bullet(player.GetX(),player.GetY(),5));
                Count=0;}

            Count++;
        }


        for(int i=0;i<enemy.size();i++){
            for(int j=0;j<bullets.size();j++)
            {
                double dx=enemy.get(i).getX()+10 - bullets.get(j).getX();
                double dy=enemy.get(i).getY()+10 - bullets.get(j).getY();
                dist= Math.sqrt(dx*dx+dy*dy);
                if(dist<=enemy.get(i).getR()+bullets.get(j).getR())
                {
                    enemy.get(i).hit();
                    bullets.remove(j);
                    j--;
                    boolean remove=enemy.get(i).remove();
                    if(remove)
                    {
                        enemy.remove(i);
                        i--;
                        player.killEnemy();
                        break;
                    }
                }
            }
        }

        ////////////

        for(int i=0; i<enemy.size();i++)
        {double dx=enemy.get(i).getX()+25 - (player.GetX()+35);
            double dy=enemy.get(i).getY()+25 - (player.GetY()+35);
            dist= Math.sqrt(dx*dx+dy*dy);
            if(dist<=enemy.get(i).getR()+20){
                enemy.get(i).hit();
                player.hit();
                boolean remove=enemy.get(i).remove();
                if(remove)
                {
                    enemy.remove(i);
                    i--;
                }
                break;
            }
        }
    }

    private void render(){
        Display.clear();
        player.render(graphics);


        for(int i=0;i<enemy.size();i++)
        {
            enemy.get(i).render(graphics);
            if(enemy.get(i).getY()>700)
            {enemy.remove(i);}

        }




        for(int i=0;i<bullets.size();i++)
        {
            bullets.get(i).render(graphics);
            if(bullets.get(i).getY()<0)
            {bullets.remove(i);}

        }
        player.showScale(graphics);
        player.showHealth(graphics);
        if(player.getHealth()==0)
        {
            gameover.render(graphics);
        }

        Display.swapBuffers();
        if(player.getHealth()==0 || game==false)
        {
            stop();
        }
    }


    public void run(){

        int fps=0;
        int upd=0;

        long count=0;

        float delta=0;

        long lastTime=Time.get();// получение настоящего времени
        while(running){
            long now=Time.get();
            long elapsedTime=now-lastTime;// время прохода одной итерации в цикле
            lastTime=now;

            count+=elapsedTime;// время которое проходит во время игры

            boolean render=false;
            delta+=(elapsedTime/UPDATE_INTERVAL);// время инетарации деленое на время к-во времени которое должно пройти до того как мы будем делать апдейт

            while(delta>1){
                update();
                upd++;
                delta--;
                if(!render){
                    render =true;}
            }
            if(render){
                render();
                fps++;
            }else{
                try {
                    Thread.sleep(IDLE_TIME);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            if(count>= Time.SECOND){
                Display.setTitle(TITLE + " || Fps:"+fps+" | Upd: "+upd);
                upd=0;
                fps=0;
                count =0;
            }

        }
    }

    private void cleanUp(){
        Display.destroy();
    }

    public static boolean getGame(){
        return game;
    }
    public static void GameOver(){
        game=false;
    }
}

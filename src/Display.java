import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;
import javax.swing.JFrame;

public abstract class Display {

    private static boolean created=false;
    private static JFrame window;
    private static Canvas content;

    private static BufferedImage buffer;
    private static int[] bufferData;
    private static Graphics bufferGraphics;
    private static int clearColor;

    private static BufferStrategy bufferStrategy;


    public static void create(int width,int height,String title,int _clearColor,int numBuffer){

        if(created)
            return;

        window=new JFrame(title);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MenuBar menuBar = new MenuBar();
        Menu gameMenu = new Menu("Game");
        MenuItem newGameMenu = new MenuItem("New");
        newGameMenu.addActionListener((event)->{// лямбда выражение, анонимная функция

            if(Game.getGame()==true){
                Game.GameOver();}

            Game aircraft=new Game();
            aircraft.start();
        });
        window.setMenuBar(menuBar);
        menuBar.add(gameMenu);
        gameMenu.add(newGameMenu);

        content=new Canvas();

        Dimension size=new Dimension(width,height);
        content.setPreferredSize(size);

        window.setResizable(false);
        window.getContentPane().add(content);
        window.pack();
        window.setLocationRelativeTo(null);// middle
        window.setVisible(true);

        buffer=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);// информацию будем хранить в виде массива
        bufferData=((DataBufferInt)buffer.getRaster().getDataBuffer()).getData();// получение пиксельного массива
        bufferGraphics=buffer.getGraphics();
        clearColor=_clearColor;

        content.createBufferStrategy(numBuffer);
        bufferStrategy=content.getBufferStrategy();

        created=true;
    }

    public static void clear(){
        Arrays.fill(bufferData,clearColor);// заполняет массив данным значением
    }

    public static void swapBuffers(){
        Graphics g=bufferStrategy.getDrawGraphics();
        g.drawImage(buffer,0,0,null);
        bufferStrategy.show();
    }

    public static Graphics2D getGraphics(){
        return (Graphics2D)bufferGraphics;
    }

    public static void destroy(){
        if(!created)
            return;

        window.dispose();//заявка для операционной системы, на освобождение ресурсов окна
    }

    public static void setTitle(String title){
        window.setTitle(title);
    }

    public static void addInputListener(Input inputListener){
        window.add(inputListener);
    }
}

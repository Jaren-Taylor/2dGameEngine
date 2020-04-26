package newgame;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static newgame.Main.battling;
import static newgame.Main.numOfSteps;

public class KeyBoardListener implements KeyListener {
    public boolean[] keys = new boolean[120];
    public Main main;
    
    public KeyBoardListener(Main main) {
        this.main = main;
    }
    public void keyPressed(KeyEvent event){
        //System.out.println(event.getKeyCode());
        int keyCode = event.getKeyCode();
//        if (keyCode < keys.length){
            keys[keyCode] = true;
            System.out.println(keyCode);
            numOfSteps++;
            System.out.print("\n numOfSteps: " + numOfSteps);
            Main.startBattle();
            System.out.println(battling);
        //}

    }

    public void keyReleased(KeyEvent event){
       int keyCode = event.getKeyCode();
//        if (keyCode < keys.length){
            keys[keyCode] = false;
        if(keyCode == KeyEvent.VK_ENTER) {
            if(main.getWidth() == 1006) {
                main.setSize(1200, 800);
            } else {
                main.setSize(1006, 800);
            }
        }

    }

    public void keyTyped(KeyEvent event){
        for (int i =0; i< keys.length; i++){
            keys[i] = false;
        }

    }
    public boolean up(){
        return keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP];

    }
    public boolean down(){
        return keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN];

    }
    public boolean left(){
        return keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT];
    }
    public boolean right(){
        return keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT];
    }


}

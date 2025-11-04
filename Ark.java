/*
Simple Game Arkanoid
Talha Hanif
This program is a playable game called "Arkanoid". The objective of this game is to destroy all bricks
on the field with the balls on the field. The ball will start on the Paddle, the ball is able to bounce
off the paddle and the boundries surrounding the field. If the ball falls past the paddle,
user will lose a life, if all lives are lost, the game will end, and user can try again.
When the ball hits any brick,it will destroy the brick and will change direction,
the score will increase every time a brick is destroyed.
If the score exceeds the highscore set in the game's file, it will replace that highscore.
The bricks will randomly drop powerUps. The powerUps include:
Disruption(splits every ball on the field)
Enlarge(increases the width of  the paddle)
Player(gives an extra life)
Slow(Slows all balls on the field)
Catch(removes all balls on the field execpt for 1, and catches the only ball)
Laser(shoots two lasers)
There are two levels, if all bricks are destroyed on each level, the user has completed the game,
and can restart if he wishes.



 */


import javax.swing.*;
import java.awt.*;


public class Ark extends JFrame {
    ArkPanel game =new ArkPanel();
    public Ark(){
        super("Arkanoid");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(game);
        ImageIcon icon=new ImageIcon("ark.png");
        Image image=icon.getImage();


        setIconImage(image);

        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        Ark frame=new Ark();
    }
}
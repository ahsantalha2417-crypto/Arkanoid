import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ArkPanel extends JPanel implements KeyListener, ActionListener, MouseListener {

    private boolean[] keys;

    public ArrayList<Brick> bricks=new ArrayList<Brick>();

    public ArrayList<Image> brickPics=new ArrayList<Image>();

    public ArrayList<Ball> balls=new ArrayList<Ball>();

    public ArrayList<PowerUp> pUps=new ArrayList<PowerUp>();

    public ArrayList<Image> pUpPics=new ArrayList<Image>();

    public ArrayList<Laser> lasers=new ArrayList<Laser>();

    public ArrayList<Image> lifePics=new ArrayList<Image>();




    private static boolean bmove;
    Timer timer;

    Timer pUptimer;

    boolean pUpActive;
    Ball ball;
    Paddle p1;

    Brick brick;




    int score=0;
    int highscore;


    public static final int INTRO=0,CONTROLS=1,GAME=2,CLEARED=3,GAME2=4,GAMEOVER=5,GAMECOMPLETE=6;
    int screen=INTRO;
    Image intro, back, win,gameOver,gameComplete,paddle,paddle2, life,  blue,green,red,orange,yellow,lightblue,     disruption, enlarge, player, slow, cach,laserpUp,    laser;

    public ArkPanel(){
        String a="a";
        keys=new boolean[KeyEvent.KEY_LAST+1];
        setPreferredSize(new Dimension(555,600));
        intro=new ImageIcon(a+"rk.png").getImage();
        back=new ImageIcon("back.png").getImage();
        win=new ImageIcon("win.png").getImage();
        gameOver=new ImageIcon("gameover.png").getImage();
        gameComplete=new ImageIcon("gamecomplete.png").getImage();
        paddle=new ImageIcon("paddle.png").getImage();
        paddle2=new ImageIcon("paddle2.png").getImage();
        life=new ImageIcon("lifeimg.png").getImage();
        for(int i=0;i<5;i++){//will add 5 lives
            lifePics.add(life);
        }


        blue=new ImageIcon("blue.png").getImage();
        brickPics.add(blue);
        green=new ImageIcon("green.png").getImage();
        brickPics.add(green);
        red=new ImageIcon("red.png").getImage();
        brickPics.add(red);
        orange=new ImageIcon("orange.png").getImage();
        brickPics.add(orange);
        yellow=new ImageIcon("yellow.png").getImage();
        brickPics.add(yellow);
        lightblue=new ImageIcon("lightblue.png").getImage();
        brickPics.add(lightblue);

        disruption=new ImageIcon("disruption.png").getImage();
        pUpPics.add(disruption);
        enlarge=new ImageIcon("enlarge.png").getImage();
        pUpPics.add(enlarge);
        player=new ImageIcon("player.png").getImage();
        pUpPics.add(player);
        slow=new ImageIcon("slow.png").getImage();
        pUpPics.add(slow);
        cach=new ImageIcon("catch.png").getImage();
        pUpPics.add(cach);
        laserpUp=new ImageIcon("laserpUp.png").getImage();
        pUpPics.add(laserpUp);


        laser=new ImageIcon("laser.png").getImage();

        bmove=false;//the variable that will allow the ball to be launched and start moving

        p1=new Paddle(KeyEvent.VK_A,KeyEvent.VK_D);//creates the paddle to start the game
        ball=new Ball(p1.getX()+(p1.getWidth()/2),p1.getY()-10,0,0);//the ball will start on the paddle
        balls.add(ball);//adds ball to ball lsit


        setFocusable(true);
        requestFocus();
        addKeyListener(this);
        addMouseListener(this);
        timer=new Timer(20,this);
        //initilizes the powerUp timer
        pUptimer = new Timer(2000, new ActionListener() {//the timer for the slow Powerup(determines how long the balls will be slow for)
            public void actionPerformed(ActionEvent e) {
                endPowerUp();//end of the slow powerup (balls will return back to original speed after timer is finished)
                for(Ball ball:balls){
                    if(ball.getVx()==5) {
                        ball.setVx(3);
                    }
                    else if(ball.getVx()==-5) {
                        ball.setVx(-3);
                    }

                    if(ball.getVy()==3) {
                        ball.setVy(5);
                    }
                    else if(ball.getVy()==-3) {
                        ball.setVy(-5);
                    }
                }//sets the orginal speed of each ball back accordingly
            }
        });
        pUpActive=false;
    }

    public static boolean getBmove(){
        return bmove;
    }

    public void setHighscore(){
        try {
            Scanner highScore=new Scanner(new BufferedReader(new FileReader("highscore.txt")));
            if(highScore.hasNextInt()) {
                if(highscore>=score) {
                    highscore = highScore.nextInt();//the hihgscore will remian the same if the score does not exceed it
                }
                else{//if the score is greater than the highscore
                    highscore=score;
                    PrintWriter newHighScore= null;
                    try {
                        newHighScore = new PrintWriter(new BufferedWriter(new FileWriter("highscore.txt")));
                        newHighScore.println(highscore);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    newHighScore.println(""+highscore);//new highscore is made
                    newHighScore.close();
                }
            }
            highScore.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void createBricks(int screen){
        Random rand=new Random();

        if (screen==CONTROLS) {//will create the bricks for the first level
            for (int i = 0; i < 12; i++) {
                for (int j = 0; j < 4; j++) {
                    int randVal = rand.nextInt(30, 101);//will randomly decide whether brick contains a powerUp or not
                    int val = 0;//normal value, does not contain powerUp
                    if (randVal >= 88 && randVal < 90) {
                        val = 1;//multypliyng balls
                    } else if (randVal >= 90 && randVal < 92) {
                        val = 2;//lengthen paddle
                    } else if (randVal >= 92 && randVal < 94) {
                        val = 3;//add extra life
                    } else if (randVal >= 94 && randVal < 96) {
                        val = 4;//slows all balls
                    } else if (randVal >= 96 && randVal < 98) {
                        val = 5;//catches ball
                    } else if (randVal >= 98 && randVal < 100) {
                        val = 6;//shoot lasers
                    }
                    brick = new Brick(15 + (43 * i), 150+(22 * j), 1, val, 1,100,j);
                    bricks.add(brick);
                    //creates a brick and adds it to the brick list

                }
            }
        }
        else if(screen==CLEARED){//will create the bricks for the 2nd level
            for(int i=11;i>-1;i--){
                for (int j=i;j<11;j++){
                    int randVal = rand.nextInt(30, 101);//will randomly decide whether brick contains a powerUp or not
                    int val = 0;//normal value, does not contain powerUp
                    if (randVal >= 88 && randVal < 90) {
                        val = 1;//multypliyng balls
                    } else if (randVal >= 90 && randVal < 92) {
                        val = 2;//lengthen paddle
                    } else if (randVal >= 92 && randVal < 94) {
                        val = 3;//add extra life
                    } else if (randVal >= 94 && randVal < 96) {
                        val = 4;//slows all balls
                    } else if (randVal >= 96 && randVal < 98) {
                        val = 5;//catches balls
                    } else if (randVal >= 98 && randVal < 100) {
                        val = 6;//shoot lasers
                    }
                    brick = new Brick( 16+(43 * i), 45+(22 * j), 1, val, 1,100,i);
                    bricks.add(brick);
                    //creates a brick and adds it to the brick list

                }
            }

        }
    }

    public void pUp(){
        for (PowerUp p:pUps) {
            if (p.isVis()) {//if the powerUp has not been collected yet (is still visible)
                if (p1.getRect().intersects(p.getRect())) {//powerUp hits paddle
                    if (p.getMode() == 1) {//mode 1 is "Disruption"
                        p.balls(balls);
                        for(PowerUp p2:pUps){
                            if(p2.getMode()==5){//if "catch"  is active as the same time as balls powerup is active
                               p2.setpUpActive(false);//stop the PowerUp from running (this will stop the paddle from catching the ball)

                            }
                        }
                    } else if (p.getMode() == 2) {//mode 2 is lengthen
                        p.lengthen(p1);
                        for(PowerUp p2:pUps){
                            if(p2.getMode()==5){//if "catch"  is active as the same time as lengthen powerup is active
                                p2.setpUpActive(false);//stop the PowerUp from running (this will stop the paddle from catching the ball)

                            }
                        }

                    } else if (p.getMode() == 3) {//mode 3 extra life
                        p.extraLife(lifePics,life);
                    } else if (p.getMode() == 4) {//mode 4 slow
                        startPowerUp();//starts the slow powerUp
                        p.slow(balls);//slows all the balls on the field
                    } else if (p.getMode() == 5) {//mode 5 catch
                        p.catchBalls(p1, balls, bmove);
                        p1.setWidth(60);//will revert the paddle back to its original length if the Paddle has been lengthened, otherwise it will do nothing to the paddle
                    }
                    else if(p.getMode()==6){//mode 6 lasers
                        p.lasers(p1,lasers,bricks);
                        p1.setWidth(60);//will revert the paddle back to its original length if the Paddle has been lengthened, otherwise it will do nothing to the paddle

                    }
                    //pUps.remove(p);
                    p.setVis(false);//the powerUp will dissapear (it will not be removed because it would still be needed to determine
                                    //whether each powerUp will be active or not (ex: Lasers will stop when Disruption powerUp is collected

                }
            }
        }
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    public void move(){//allows all movement

        p1.move(keys,balls);
        if (p1.start()) {//if the paddle has moved first

            bmove=true;//allow the ball to move





        }


        if(bmove){//if the ball has been launched
            for(Ball ball:balls) {

                ball.move(p1, brick);//every ball will move
            }
        }

        for(Ball ball:balls) {

            if (ball.getY() > 600) {
                balls.remove(ball);//removes the ball that wen out of bounds

                break;//needed to exit the for loop when the element of the for loop (the ball) is removed
            }

        }


        if (balls.isEmpty()) {//will only lose a life and reset the ball if there are no other balls on the screen
            lifePics.remove(life);//will remove a life picture
            for(PowerUp p:pUps){
                p.setpUpActive(false);//will cancel all powerUps once a life is lost
            }
            bmove = false;//the ball will sit still on the paddle until launched
            ball = new Ball(p1.getX()+(p1.getWidth()/2), p1.getY() - 10, 0, 0);//whenever the ball goes out of bounds it will restart back on the vaus
            balls.add(ball);//adds the new ball created on the paddle

        }
        for (Brick b : bricks) {//for every brick in the bricks list
            for(Ball ball:balls) {
                if (ball.getRect().intersects(b.getRect()) && b.getVis() == 1) {//if the ball hits one of the bricks
                    b.setHits(b.getHits() - 1);//1 less hit needed in order to break the brick


                    if (b.getHits() <= 0) {//if the amount of hits needed for the bricks to break reduces to zero
                        b.setVis(0);//sets to not visible
                        score+=b.getP();//adds the coressponding points on the brick to the score
                        if(b.getVal()>=1){
                            PowerUp p=new PowerUp(b.getX(),b.getY(),b.getVal(),true,true);//puts a new powerup at the position of the brick
                            //the powerUp will be based on the random value generated for the brick
                            pUps.add(p);//add the powerup to the powerups list
                            p.setDrop(true);//allows the powerUp to drop
                        }

                    }
                    ball.setVy(ball.getVy() * -1);//change the direction
                }
            }
            for (int i=0;i<lasers.size();i++){
                Laser l=lasers.get(i);
                if (l.getRect().intersects(b.getRect()) && b.getVis() == 1) {//if one of the lasers hits one of the bricks
                    b.setHits(b.getHits() - 1);//1 less hit needed in order to break the brick
                    lasers.remove(l);//removes the laser aftyer it hits teh brick


                    if (b.getHits() <= 0) {//if the amount of hits needed for the bricks to break reduces to zero
                        b.setVis(0);//sets to not visible
                        score+=b.getP();//adds the coressponding points on the brick to the score
                        if(b.getVal()>=1){//if the brick's value is greater than 1: powerUp will drop
                            PowerUp p=new PowerUp(b.getX(),b.getY(),b.getVal(),true,true);//puts a new powerup at the position of the brick
                            //the powerUp will be based on the random value generated for the brick
                            pUps.add(p);//add the powerup to the powerups list
                            p.setDrop(true);//allows the powerUp to drop
                        }
                    }
                }

            }

        }

    }//end move()
    public void setLvlClear(){//determines whether first level is cleared or not
        if(screen==GAME) {
            int count = 0;
            for (Brick b : bricks) {

                if (b.getVis() == 0) {//if the brick is gone(not visble)
                    count++;//add to counter
                }
            }
            if (count == bricks.size()) {//if all bricks are gone (all non visible bricks are counted to be the same as bricks.size()  )
                screen=CLEARED;



            }
        }
    }

    public void setLvl2(){
        if(screen==CLEARED) {
            screen = GAME2;
            bricks.clear();//clears all the bricks so new bricks can be generated
            balls.clear();//clears all balls on the field for the new level (this will remove the effects of disruption,slow, and catch)
            pUps.clear();//removes all the dropping powerUps on the field
            lasers.clear();//will remove the lasers if they are still on the field
            createBricks(CLEARED);
            p1.setX(275);//sets paddle back to original position
            p1.setWidth(60);//will set paddle back to original length if the width was increased last level due to a powerUp
            ball = new Ball(p1.getX() + (p1.getWidth() / 2), p1.getY() - 10, 0, 0);//the ball will start on the vaus
            balls.add(ball);
        }

    }
    public void setGameover(){
        if (lifePics.size()<=0){//if there are no more lives
            screen=GAMEOVER;
            for(int i=0;i<5;i++){//resets the lives
                lifePics.add(life);
            }
            //sets Gameover after everything has been removed so that if player wanys to try again after losing, everything will be reset
        }


    }

    public void setGameComplete() {
        if (screen==GAME2) {
            int count = 0;
            for (Brick b : bricks) {

                if (b.getVis() == 0) {
                    count++;
                }
            }
            if (count == bricks.size()) {//if all bricks are gone (set to non visible)
                screen = GAMECOMPLETE;//game is complete
                count=0;
                for(int i=0;i<5;i++){//resets the lives incase user wishes to play again
                    lifePics.add(life);
                }

            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        for(PowerUp p:pUps){//checks which powerUps are allowed to drop (when brick breaks)
            if (p.isDrop()){
                p.drop();//drops the PowerUp
            }
        }
        for(Laser l:lasers){
            l.shoot();//shoots every laser in  the laser list (two lasers at a time)
        }
        pUp();//powerUp
        if(screen==GAME||screen==GAME2) {
            move();//prevents movement from any screen besides the levels
        }
        setHighscore();//allows for highscore to be changed
        setLvlClear();//checks if level is cleared
        setGameover();//checks if game is over
        setGameComplete();//checks if game has been completed
        repaint();
    }

    private void startPowerUp() {//used for the slow powerUp
        pUpActive = true;
        pUptimer.start(); // Start slow power-up timer
    }

    private void endPowerUp() {
        pUpActive = false;
        pUptimer.stop(); // Stop slow power-up timer
    }


    @Override
    public void keyPressed(KeyEvent k){
        int key=k.getKeyCode();
        if(key==KeyEvent.VK_SPACE){
            timer.start();
            if(screen==INTRO){//if mouse is clicked on intro screen
                score=0;//resets the score
                p1.setX(275);
                p1.setWidth(60);
                bricks.clear();
                balls.clear();//clears all balls on the field for the new level (this will remove the effects of disruption,slow, and catch)
                pUps.clear();//removes all the dropping powerUps on the field
                lasers.clear();//will remove the lasers if they are still on the field
                //resets everything before each game starts
                screen=CONTROLS;//controrls screen will open
            }
        }
        if(key==KeyEvent.VK_ENTER){
            if(screen==CONTROLS) {//the if statement prevents the game from resetting back to the orginal every time you click
                screen = GAME;
                createBricks(CONTROLS);//will create the bricks only once when the game has started
        }
        }
        if(key==KeyEvent.VK_B){
            PowerUp p=new PowerUp(5,5,1,true,true);
            p.balls(balls);
        }


        keys[key]=true;
    }

    @Override
    public void keyReleased(KeyEvent k){
        int key=k.getKeyCode();
        keys[key]=false;
    }
    @Override
    public void keyTyped(KeyEvent k){
    }

    @Override
    public void mouseClicked(MouseEvent e){
        if(screen==CLEARED){
            setLvl2();
        }
        if(screen==GAMEOVER||screen==GAMECOMPLETE){
            screen=INTRO;
        }


    }

    @Override
    public void mousePressed(MouseEvent e){
    }
    @Override
    public void mouseReleased(MouseEvent e){
    }
    @Override
    public void mouseExited(MouseEvent e){
    }
    @Override
    public void mouseEntered(MouseEvent e){
    }

    @Override
    public void paint(Graphics g){
        if (screen==INTRO){
            g.drawImage(intro,0,0,null);
            g.setFont(new Font("Arial",Font.BOLD,40));
            g.setColor(Color.white);
            g.drawString("Press Space to Start",50,230);
        }

        else if(screen==CONTROLS){
            g.setColor(Color.BLACK);
            g.fillRect(0,0,555,600);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial",Font.BOLD,50));
            g.drawString("Controls",169,69);
            g.setFont(new Font("Arial",Font.BOLD,40));
            g.drawString("<------- A     D------->",80,160);
            g.drawString("SPACE: Launch Ball",80,250);
            g.setColor(Color.RED);
            g.drawString("Press Enter to Start",80,500);

        }
        else if (screen==GAME){
            g.setColor(Color.BLACK);
            g.fillRect(0,0,555,600);
            //
            g.drawImage(back,0,0,null);
            for (Ball ball:balls) {//draws every ball in the balls list
                ball.draw(g);
            }

            //p1.draw(g);
            if(p1.getWidth()==60) {
                g.drawImage(paddle, p1.getX(), p1.getY(), null);
            }
            else if(p1.getWidth()==70){
                g.drawImage(paddle2,p1.getX(), p1.getY(), null);
            }

            for (PowerUp p:pUps){
                if(p.isVis()) {
                    g.drawImage(pUpPics.get(p.getMode()-1), p.getX(), p.getY(), null);
                }


            }
            for (Brick brick:bricks) {
                if(brick.getVis()==1) {
                    g.drawImage((brickPics.get(brick.getPicValue())), brick.getX(), brick.getY(), null);
                    //will draw each brick based off the picture value its given
                }
            }

            for (Laser l:lasers){
                g.drawImage(laser,l.getX(),l.getY(),null);
            }


            g.setFont(new Font("Arial",Font.BOLD,20));
            for(int i=0;i<lifePics.size();i++){
                life=lifePics.get(i);
                g.drawImage(life,25+(i*12),570,null);
                //will draw the small life symbols on the bottom left
            }
            g.setColor(Color.white);
            g.drawString("Score: "+score,40,35);
            g.drawString("HighScore: "+highscore,350,35);



        }
        else if (screen==CLEARED){
            g.setColor(Color.BLACK);
            g.fillRect(0,0,555,600);
            g.drawImage(win,25,50,null);
            g.fillRect(0,50,555,100);
        }
        else if (screen==GAME2){
            g.setColor(Color.BLACK);
            g.fillRect(0,0,555,600);
            g.drawImage(back,0,0,null);
            for (Ball ball:balls) {//draws every ball in the balls list
                ball.draw(g);
            }

           // p1.draw(g);
            if(p1.getWidth()==60) {
                g.drawImage(paddle, p1.getX(), p1.getY(), null);
            }//will draw nornal paddle if lengthen powerup isnt active
            else if(p1.getWidth()==70){
                g.drawImage(paddle2,p1.getX(), p1.getY(), null);
            }//will draw lengthed paddle if lengthen powerup is active

            for (PowerUp p:pUps){
                //p.draw(g);
                if(p.isVis()) {
                    g.drawImage(pUpPics.get(p.getMode()-1), p.getX(), p.getY(), null);
                    //draws the powerup falling
                }


            }
            for (Brick brick:bricks) {
                //brick.draw(g);
                if(brick.getVis()==1) {
                    if(brick.getPicValue()>5) {//the length of the brickPics is 6, so this prevents form going out of bounds
                        g.drawImage((brickPics.get(brick.getPicValue()-5)), brick.getX(), brick.getY(), null);
                    }
                    else{
                        g.drawImage((brickPics.get(brick.getPicValue())), brick.getX(), brick.getY(), null);
                    }
                }
            }

            for (Laser l:lasers){
                g.drawImage(laser,l.getX(),l.getY(),null);
            }

            //p2.draw(g);

            g.setFont(new Font("Arial",Font.BOLD,20));
            for(int i=0;i<lifePics.size();i++){
                life=lifePics.get(i);
                g.drawImage(life,20+(i*12),570,null);
            }
            g.setColor(Color.white);
            g.drawString("Score: "+score,40,35);
            g.drawString("HighScore: "+highscore,350,35);



        }
        else if (screen==GAMEOVER){
            g.setColor(Color.BLACK);
            g.fillRect(0,0,555,600);
            g.drawImage(gameOver,25,50,null);
            g.fillRect(0,50,555,100);

        }
        else if(screen==GAMECOMPLETE){
            g.setColor(Color.BLACK);
            g.fillRect(0,0,555,600);
            g.drawImage(gameComplete,25,50,null);
            g.fillRect(0,400,555,200);
            g.setColor(Color.red);
            g.setFont(new Font("Arial",Font.BOLD,40));
            g.drawString("Click Anywhere to Restart",30,500);
        }
    }


}
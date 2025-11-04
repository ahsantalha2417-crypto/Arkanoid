import java.awt.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.Random;

public class PowerUp {
    Random rand=new Random();
    private int x,y, mode;
    //mode-determines which powerUp is in action


    private boolean vis,drop,pUpActive;

    public PowerUp(int x, int y,int mode,boolean vis,boolean active){
        this.x=x;
        this.y=y;
        this.mode=mode;
        this.vis=vis;
        this.pUpActive=active;
        this.drop=false;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean isVis() {
        return this.vis;
    }

    public void setVis(boolean vis) {
        this.vis = vis;
    }

    public int getMode() {
        return this.mode;
    }

    public boolean isDrop() {
        return this.drop;
    }

    public void setDrop(boolean drop) {
        this.drop = drop;
    }

    public Rectangle getRect() {
        return new Rectangle(this.x, this.y, 35, 19);
    }

    public void drop(){
        this.y+=3;
    }

    public void setpUpActive(boolean pUpActive) {
        this.pUpActive = pUpActive;
    }


    public void balls(ArrayList<Ball> balls){//splits all balls on the field into 3
        ArrayList<Ball> balls2= new ArrayList<Ball>();
        balls2=(ArrayList<Ball>)(balls.clone());//clones the original balls list
        //this prvents this new list's values being changed hwen the original's values are changed
        //(will not add ball if the original doesnt add the ball
        //int d=balls.size()*2;
        for(Ball b:balls2) {//for every ball on the screen
            for(int i=0;i<2;i++) {//adds two extra balls for every ball on the screen
                if(i==0){
                    Ball ball = new Ball(b.getX(), b.getY(), -5, 5);//shoots left
                    if(balls.size()<50) {
                        balls.add(ball);
                    }
                }
                else {
                    Ball ball = new Ball(b.getX(), b.getY(), 5, 5);//shoots right
                    if(balls.size()<50) {
                        balls.add(ball);
                    }
                }


            }

        }

    }

    public void lengthen(Paddle p1){//lengthens the paddle
        if(pUpActive) {
            p1.setWidth(70);
        }

    }
    public void extraLife(ArrayList<Image> lifePics,Image life) {//the powerUp to give an extra life
        int l=lifePics.size();
        if (pUpActive) {
            if(l<5) {//will only add a extra life if player has less than 5 lives

                lifePics.add(life);//adds a life picture
            }
        }

    }


    public void slow(ArrayList<Ball> balls) {//powerUP to slow down all balls on the field
        if (pUpActive) {
            for (Ball ball : balls) {
                if(ball.getVx()==5) {
                    ball.setVx(3);
                }
                else if(ball.getVx()==-5) {
                    ball.setVx(-3);
                }
                if(ball.getVy()==5) {
                    ball.setVy(3);
                }
                else if(ball.getVy()==-5){
                    ball.setVy(-3);
                }
            }//will set the speed according to which direction ball is going so ball does not change direction when the slow powerUp is collected
        }
    }

    public void catchBalls(Paddle p1,ArrayList<Ball> balls,boolean bmove) {
        if (pUpActive) {
            for (int i = balls.size() - 1; i > 0; i--) {//removes all balls ob the field execpt for  1 ball to catch
                Ball ball = balls.get(i);
                balls.remove(ball);
            }
            for (Ball ball : balls) {

                ball.setCatchB(true);//the paddle will catch the one ball

            }
        }
        else{//if the powerUp is turned off
            for (Ball ball : balls) {

                ball.setCatchB(false);//the paddle will no longer catch teh ball

            }
        }
    }

    public void lasers(Paddle p1,ArrayList<Laser> lasers,ArrayList<Brick> bricks) {//shoots two lasers form the paddle
        if (pUpActive) {
            Laser l=new Laser(p1.getX(),p1.getY());
            Laser l2=new Laser(p1.getX()+p1.getWidth(),p1.getY());
            lasers.add(l);
            lasers.add(l2);
        }
    }



//    public void draw(Graphics g){
//        //g.setColor(Color.DARK_GRAY);
//        g.fillRect(this.x,this.y,44,19);
//    }

}

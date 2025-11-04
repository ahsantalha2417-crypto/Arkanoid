import java.awt.*;
import java.util.ArrayList;

public class Paddle {
    private int x,y,width,left,right;

    private boolean move=false;//will allow the ball to move once the vaus is moved

    public boolean start(){
        if (move){

            return true;
        }
        else{
            return false;
        }
    }

    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public boolean getMove(){
        return move;
    }

    public void setMove(boolean move) {
        this.move = move;
    }

    public Paddle(int left, int right){
        this.x=275;
        this.y=545;
        this.left=left;//will move left based on the parameter key
        this.right=right;//will move left based on the key called in ArkPanel
        this.width=60;
        this.move=false;

    }

    public Rectangle getRect() {
        return new Rectangle(this.x, this.y, this.width, 1);
    }



    public void move(boolean[] keys, ArrayList<Ball> balls) {
        //for(Ball ball:balls) {
        int c=0;
        int d=0;
        //these two variable prevents paddle from moving depedning on all the balls in the balls list
        for (Ball ball : balls) {

            if (keys[32]) {//is the space bar is pressed
                if (ball.getVx() == 0) {
                    move = true;//the ball will be launched
                    if (Math.random()>0.5){//randomly chooses which direction the ball will shoot towards
                        ball.setVx(5);
                    }
                    else{
                        ball.setVx(-5);
                    }
                    ball.setVy(-5);
                }

            } else if (keys[left]) {
                c++;
                if (c==1&&this.x>15) {
                    this.x -= 10;
                }
                if (ball.getVx() == 0&&this.x>15) {//when the ball is not moving and staying still on the paddle, and the paddle does not exceed the border
                    ball.setX(this.x+(this.width/2));//the ball will move with the paddle until launched
                }
                //move=true;//allows ball to move once vaus had been mvoed
            } else if (keys[right]) {
                d++;
                if (d==1&&this.x+this.width<536) {
                    this.x += 10;
                }
                if (ball.getVx() == 0 &&this.x+this.width<536) {//when the ball is not moving and staying still on the vaus
                    ball.setX(this.x+(this.width/2));//the ball will move with the vaus until launched
                }
            }

        }
    }

}
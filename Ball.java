import java.awt.*;

public class Ball {
    private int x,y,vx,vy;
    private  boolean catchB;

    public Ball(int x,int y,int vx,int vy){
        this.x=x;
        this.y=y;
        this.vx=vx;
        this.vy=vy;
        this.catchB=false;



    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }

    public int getVx() {
        return this.vx;
    }

    public int getVy() {
        return this.vy;
    }



    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public void setCatchB(boolean catchB) {//detetrmines whether ball will stick to the paddle or not
        this.catchB = catchB;
    }

    public void move(Paddle p1, Brick b){//allows ball to move
        this.x+=this.vx;
        this.y+=this.vy;
        //         ball                paddle
        if (this.getRect().intersects(p1.getRect())){//if the ball hits the vaus

            if(!catchB) {//if the catch powerUp is not active
                vy *= -1;//change the direction of the ball
            }
            else{//if the catch powerUp is active
                this.vx=0;
                this.vy=0;
            }
        }


        if(this.x>525|| this.x<20){//if ball hits the side boundries
            vx*=-1;//the direction will change
        }


        if (this.y<15){//if ball hits the top boundary
            vy*=-1;//the direction will change
        }
    }


    public Rectangle getRect(){
        return new Rectangle(this.x,this.y+2,8,8);
    }

    public void draw(Graphics g){
        g.setColor(Color.YELLOW);
        g.fillOval(x,y,8,8);
    }
}

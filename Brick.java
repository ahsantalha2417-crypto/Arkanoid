import java.awt.*;
import java.util.ArrayList;

public class Brick {
        public ArrayList<Rectangle> bricks=new ArrayList<Rectangle>();
        public ArrayList<Integer> brickHits=new ArrayList<Integer>();
        Rectangle r;
        private int x,y,width,height,hits,val,vis,p,picValue;

        public Brick(int x,int y,int hits, int val,int vis,int p,int picInd){
            this.x=x;
            this.y=y;
            this.width=43;
            this.height=22;
            this.hits=hits;
            this.val=val;
            this.vis=vis;
            this.p=p;//points added to the score
            this.picValue=picInd;//will take the index so program can recognise which color brick to display
        }

    public Rectangle getRect(){
        return new Rectangle(this.x,this.y,43,22);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getVal() {
        return this.val;
    }


    public int getVis() {
        return this.vis;
    }

    public void setVis(int vis) {
        this.vis = vis;
    }

    public int getPicValue() {
        return this.picValue;
    }

    public int getHits() {
        return this.hits;
    }

    public void setHits(int hits) {//allows for the amount of hits needed for the brick to break to change
        this.hits = hits;
    }

    public int getP() {//gets the points for the brick in order to add it to the score
        return this.p;
    }


}



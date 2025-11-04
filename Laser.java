import java.awt.*;
import java.util.ArrayList;

public class Laser {
    private int x,y;

    public Laser(int x,int y){
        this.x=x;
        this.y=y;

    }

    public void shoot(){
        this.y-=8;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Rectangle getRect() {
        return new Rectangle(this.x, this.y, 6, 40);
    }

}

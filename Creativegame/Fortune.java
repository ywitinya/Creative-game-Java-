public class Fortune extends Entity implements Scrollable {
    
//Location of image file to be drawn for an Avoid
private static final String EXPLODE_IMAGE_FILE = "myassets/explosion-sprite.png";
//default explosion size    
private static final int EXPLODE_SIZE = 50;
private static final int GET_SCROLL_SPEED = 10;



public Fortune(int x, int y) {
    this(x, y, EXPLODE_SIZE);
    
}
public Fortune(int x, int y, int explodeSize) {
    this(x, y, EXPLODE_IMAGE_FILE, explodeSize);
}

public Fortune(int x, int y, String imageFile, int explosionSize) {
    super(x, y, explosionSize, explosionSize, imageFile); 

}

public int getLifeValue(){
    return 1;
    
}
public int getScrollSpeed() {
    return GET_SCROLL_SPEED;
}
@Override
public void scroll() {
    setX(getX() - getScrollSpeed());
    
}
@Override
public void scroll(int windowHeight, int windowWidth) {
    scroll();
}


}


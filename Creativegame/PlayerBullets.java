public class PlayerBullets extends Entity implements Scrollable{
    //Location of image file to be drawn for a Get
    protected static final String GET_IMAGE_FILE = "myassets/droplets.png";
    //Dimensions of the Get  
    protected static final int GET_WIDTH = 40;
    protected static final int GET_HEIGHT = 10;
    //Speed that the playerBullet moves (in pixels) each time the game scrolls
    protected static final int GET_SCROLL_SPEED = 10;
    private int scrollSpeed;

    public PlayerBullets(){
        this(0, 0);        
    }
    
    public PlayerBullets(int x, int y){
        this(x, y, GET_IMAGE_FILE);
    }
    
    public PlayerBullets(int x, int y, String imageFileName){
        this(x, y, imageFileName, GET_SCROLL_SPEED);
        
    }
    public PlayerBullets(int x, int y, String imageFileName, int scrollSpeed) {
        super(x, y, GET_WIDTH, GET_HEIGHT, imageFileName);
        this.scrollSpeed = scrollSpeed;
    }
    
    @Override
    public int getScrollSpeed() {
        
        return scrollSpeed;
    }

    @Override
    public void scroll() {
        setX(getX() + scrollSpeed);
    }

    //this method is not used
    public void scroll(int windowHeight, int windowWidth) {}

    @Override
    //when a player collides with this bullet looses 1 (HP) point
    public int getLifeValue() {
        return -1;
    }
    
}

    

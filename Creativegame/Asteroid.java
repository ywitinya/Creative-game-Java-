

//Asteroids are entities the player needs to avoid colliding with.
//If a player collides with an asteroid dies.
public class Asteroid extends Entity implements Explodable, Scrollable {
    
    //Location of image file to be drawn for an Asteroid
    private static final String AVOID_IMAGE_FILE = "myassets/asteroid-sprite.png";
    //Dimensions of the Asteroid    
    private static final int AVOID_WIDTH = 75;
    private static final int AVOID_HEIGHT = 75;
    //other useful aspects of asteroid entity
    private static final int ASTEROID_SCROLL_SPEED = 10;
    private static final int THIS_LIFE = 1;
    private static final int EXPLOSION_SIZE = 100;
    private static final String EXPLODE_IMAGE_FILE = "myassets/asteroid-sprite.png";
    
    
    public Asteroid(){
        this(0, 0);        
    }
    
    public Asteroid(int x, int y){
        super(x, y, AVOID_WIDTH, AVOID_HEIGHT, AVOID_IMAGE_FILE);
        this.currentLife = THIS_LIFE; 
        this.life = THIS_LIFE; 
    }
    
    
    public int getScrollSpeed(){
        return ASTEROID_SCROLL_SPEED;
    }
    public void scroll(){
        setX(getX() - ASTEROID_SCROLL_SPEED);
    }    
    //implemented methods
    public void scroll(int windowHeight, int windowWidth) {}
    
    

    public int getLifeValue(){
       return -Player.STARTING_HP;
       
    }
    //returns explosion animation to be drawn on the screen
    public Entity Explode() {
        Entity Explosion = new Splash(this.x, this.y, EXPLOSION_SIZE, EXPLODE_IMAGE_FILE);
        return Explosion;
    }     
    
}

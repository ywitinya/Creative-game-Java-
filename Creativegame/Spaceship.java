import java.util.ArrayList;

public class Spaceship extends Entity implements Explodable, Scrollable {
    //Location of image file to be drawn for an Avoid
    private static final String SPACESHIP_IMAGE_FILE = "myassets/spaceshipBG.png";
    private static final String EXPLODE_IMAGE_FILE = "myassets/explosion-color-powder.png";
    //Dimensions of the spaceship    
    private static final int SPACESHIP_WIDTH = 300;
    private static final int SPACESHIP_HEIGHT = 450;
    private static final int EXPLOSION_SIZE = 500;
    //Speed that the spaceship moves each time the game scrolls
    private static final int SPACESHIP_SCROLL_SPEED = 1;
    //spaceship's resistance to shots
    private static final int THIS_LIFE = 100;
    //determine whether spaceship can shoot or not
    public static boolean canShoot;
    //useful booleans for scrolling 
    private int criticalPosition;
    private int counter;
    

    
    public Spaceship(){
        this(0, 0);        
    }
    
    public Spaceship(int x, int y){
        super(x, y, SPACESHIP_WIDTH, SPACESHIP_HEIGHT, SPACESHIP_IMAGE_FILE); 
        this.currentLife = THIS_LIFE;
        this.life = THIS_LIFE;
    }
    
    public int getLifeValue(){
        return -Player.STARTING_HP;
        
     }
    
    public int getScrollSpeed(){
        return SPACESHIP_SCROLL_SPEED;
    }
    
    //Move the avoid left by the scroll speed
    public void scroll(){
        
        if (getX() >= 600) {
            setX(getX() - SPACESHIP_SCROLL_SPEED);
        }
        else{
            canShoot = true; 
        }    
        
    }
    public static ArrayList<Entity> shoot() {
        Bullet bullet1 = new Bullet(800, 200, "myassets/asteroid-logocopy.png");
        Bullet bullet2 = new Bullet(800, 230, "myassets/asteroid-logocopy.png");
        Bullet bullet3 = new Bullet(800, 295, "myassets/asteroid-logocopy.png");
        Bullet bullet4 = new Bullet(800, 325, "myassets/asteroid-logocopy.png");
        Bullet bulletG1 = new Bullet(680, 50, "myassets/drop-delicate-blue.png");
        Bullet bulletG2 = new Bullet(680, 70, "myassets/drop-delicate-blue.png");
        Bullet bulletG3 = new Bullet(680, 450, "myassets/drop-delicate-blue.png");
        Bullet bulletG4 = new Bullet(680, 470, "myassets/drop-delicate-blue.png");
        ArrayList<Entity> mybullets = new ArrayList<Entity>();
        mybullets.add(bullet1);
        mybullets.add(bullet2);
        mybullets.add(bullet3);
        mybullets.add(bullet4);
        mybullets.add(bulletG1);
        mybullets.add(bulletG2);
        mybullets.add(bulletG3);
        mybullets.add(bulletG4);
        
        return mybullets;
    }
    public void stopShooting(ArrayList<Entity> displayList) {
        canShoot = false;
        for (int i = 0; i < displayList.size(); i++) {
            if (displayList.get(i) instanceof Bullet) {
                displayList.remove(displayList.get(i));
                
            }
        }
    }

    @Override
    public void scroll(int windowHeight, int windowWidth) {
        this.scroll();
        if (getX() <= (windowWidth/2) & counter==0) {
            criticalPosition = windowHeight;
            counter++;
            
            
        }
        if (criticalPosition ==  windowHeight & counter==1) {
            if (getX() < (int)(windowWidth/2) & getY() < (windowHeight)) {
                setX(getX() + 10);
                setY(getY()- 5);
            }
        }
        if (getY()==windowHeight) {
            setY(windowHeight);
            setX(getX());
        }

    }

    public Entity Explode() {
        Entity Explosion = new Splash(this.x, this.y, EXPLOSION_SIZE, EXPLODE_IMAGE_FILE);
        return Explosion;
    }     
    
}


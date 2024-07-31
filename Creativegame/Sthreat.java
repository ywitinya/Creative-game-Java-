import java.util.Random;

public class Sthreat extends Entity implements Scrollable, Explodable{
     //Location of image file to be drawn for a Get
     protected static final String GET_IMAGE_FILE = "myassets/super-metroid.png";
     //Dimensions of the Get  
     protected static final int GET_WIDTH = 50;
     protected static final int GET_HEIGHT = 50;
     //Speed that the Get moves (in pixels) each time the game scrolls
     protected static final int GET_SCROLL_SPEED = 1;
     protected static final int THIS_LIFE = 1;
     protected static final int EXPLOSION_SIZE = 75;
     protected static final String EXPLODE_IMAGE_FILE = "myassets/explosion-sprite.png";
     protected static final int FORTUNE_SIZE = 50;

     //some instance variables
     protected int criticalPosition;
     protected int counter;
   

    public Sthreat() {
        this(0,0);
    }

    public Sthreat(int x, int y){
        this(x, y, GET_IMAGE_FILE);  
    }
    
    public Sthreat(int x, int y, String imageFileName){
        super(x, y, GET_WIDTH, GET_HEIGHT, imageFileName);
        this.life = THIS_LIFE;
        this.currentLife = THIS_LIFE;
    }

   

    public int getCurrentLife(){
        return currentLife;
    }

    public int getLifeValue(){
        return -Player.STARTING_HP;
        
     }
    
    public int getScrollSpeed(){
        return GET_SCROLL_SPEED;
    }

    //Move the Get left by its scroll speed
    public void scroll(){
        setX(getX()+1);
        setY(getY()+3);
    }
    
    public void scroll(int windowHeight, int windowWidth) {
        
        if (criticalPosition == 0) {
            this.scroll();
        } 
        
        if (getY() >= (windowHeight+200) & counter==0) {
            criticalPosition = windowHeight;
            counter++;
               
        }
        else if(getY() <= -200) {
            counter = 0;
            criticalPosition = 0;
        }
        if (criticalPosition ==  windowHeight & counter!=0) {
                setX(getX() -2);
                setY(getY()-3);
                
            
        }
    }
    public Entity shoot() {
        if (getY() < 100 || getY() > 500) {
            return null;
        }
        Random rand = new Random();
        double randChance = rand.nextDouble();
        if (randChance <= 0.1) {
            Bullet bullet = new Bullet(this.x, this.y-10, "myassets/red-droplet.png", -10);
            
            return bullet;
        }
        return null;
    }

    @Override
    public Entity Explode() {
        Entity Explosion = new Splash(this.x, this.y, EXPLOSION_SIZE, EXPLODE_IMAGE_FILE);
        return Explosion;
    }
    public Entity throwFortune() {
        Random rand = new Random();
        double randChance = rand.nextDouble();
        if (randChance <= 0.1) {
            Entity fortune = new Fortune(this.x, this.y, "myassets/red-cross.png", FORTUNE_SIZE);
            return fortune;
        }
        return null;
    }    
}

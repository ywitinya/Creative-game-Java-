import java.util.ArrayList;

//The entity that the human player controls in the game window
//The player moves in reaction to player input
public class Player extends Entity {
    
   //Location of image file to be drawn for a Player
   protected static final String PLAYER_IMAGE_FILE = "myassets/spacecraft.png";
   //Dimensions of the Player
   protected static final int PLAYER_WIDTH = 75;
   protected static final int PLAYER_HEIGHT = 75;
   //Default speed that the Player moves (in pixels) each time the user moves it
   protected static final int DEFAULT_MOVEMENT_SPEED = 7;
   //Starting hit points
   protected static final int STARTING_HP = 3;

    //Current movement speed
    private int movementSpeed;
    //Remaining Hit Points (HP) -- indicates the number of "hits" (ie collisions
    //with Avoids) that the player can take before the game is over
    
    
    public Player(){
        this(0, 0);        
    }
    
    public Player(int x, int y){
        super(x, y, PLAYER_WIDTH, PLAYER_HEIGHT, PLAYER_IMAGE_FILE);  
        this.currentLife = STARTING_HP;
        this.life = STARTING_HP;
        this.movementSpeed = DEFAULT_MOVEMENT_SPEED;
    }
    
    
    //Retrieve and set the Player's current movement speed 
    public int getMovementSpeed(){
        return this.movementSpeed;
    }
    
    public void setMovementSpeed(int newSpeed){
        this.movementSpeed = newSpeed;
    }  
    
    //player movements
    public void moveUp(){
        this.y = Math.max(this.y - movementSpeed, 0);
    }

    public void moveDown(int upperBound) {
        upperBound -= getHeight();
        this.y = Math.min(this.y + movementSpeed, upperBound);
    }

    public void moveLeft() {
        this.x = Math.max(this.x - movementSpeed, 0);
    }

    public void moveright(int upperBound) {
        upperBound -= this.getWidth();
        this.x = Math.min(this.x + movementSpeed, upperBound);
    }

    
    public ArrayList<Entity> shoot() {
        
        ArrayList<Entity> bullets = new ArrayList<Entity>();
        int bulletX1 = this.getX()+this.getWidth()-10;
        int bulletY1 = this.getY()+(int)(this.getHeight()/2)-5;
        int bulletX2 = this.getX()+this.getWidth()-10;
        int bulletY2 = this.getY()+(int)(this.getHeight()/2)+ 5;
        int bulletX3 = this.getX()+this.getWidth()-10;
        int bulletY3 = this.getY()+(int)(this.getHeight()/2)-15;
        
        PlayerBullets bullet1 = new PlayerBullets(bulletX1, bulletY1);
        PlayerBullets bullet2 = new PlayerBullets(bulletX2, bulletY2);
        PlayerBullets bullet3 = new PlayerBullets(bulletX3, bulletY3);
        bullets.add(bullet2);
        bullets.add(bullet1);
        bullets.add(bullet3);

        return bullets;
    }
        
        

   

    public Entity died() {
        Entity splash = new Splash(this.x, this.y, 400, 200, "myassets/you-lose.png");
        return splash;
    }

    public Entity victory() {
        Entity splash = new Splash(300, 300, 600, 200, "myassets/you-win.png");
        return splash;
    }

    @Override
    public int getLifeValue() {
        return -1;
    }
    }
    

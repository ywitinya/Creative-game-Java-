import java.util.Random;

//Gets are entities that the player wants to collide with, as they increase
//their score.
public class StarThreat extends Sthreat {
    
    //Location of image file to be drawn for a Get
    protected static final String GET_IMAGE_FILE = "myassets/spaceship.png";
    
   
    
    public StarThreat(){
        this(0, 0);        
    }
    
    public StarThreat(int x, int y){
        this(x, y, GET_IMAGE_FILE);  
    }
    
    public StarThreat(int x, int y, String imageFileName){
        super(x, y, imageFileName);
        this.life = THIS_LIFE;
        this.currentLife = THIS_LIFE;
    }
    
    
    public int getScrollSpeed(){
        return GET_SCROLL_SPEED;
    }

    //Move the Get left by its scroll speed
    public void scroll(){
        setY(getY()+5);
    }

    public void scroll(int windowHeight, int windowWidth) {
        this.scroll();
        if (getY() >= (windowHeight) & counter==0) {
            criticalPosition = windowHeight;
            counter++;
            
            
        }
        if (criticalPosition ==  windowHeight & counter==1) {
            if (getY() > (int)(windowHeight/3)) {
                setX(getX() + 5);
                setY(getY()-10);
            }
        }
            if (criticalPosition ==  windowHeight & counter==1) {
                if (getY() <= (int)(windowHeight/3)) {
                    setX(getX() - 10);
                    setY(getY()-5);
                    
                }
        }    
        if (getX() <= (int)(windowWidth/4)&counter==1) {
            criticalPosition = windowWidth/4;
            counter++;
        }
        if (criticalPosition ==  windowWidth/4 & counter==2) {     
                    setX(getX() + 10);
                    setY(getY()+1);
                    if (getY() >= (windowHeight-50) & counter>1) {
                        criticalPosition = windowHeight;
                        counter++; 
                                
                    }
                }
        
        if (criticalPosition ==  windowHeight & counter==3) {
            
                setX(getX()-3);
                setY(getY()-10);
                if (getY() <= -50) {
                    counter=0;
                }
            
        }
             
        
    }
    

    public Entity shoot() {
        if (getY() > 0 & getX() >= 400) {
            Random rand = new Random();
            double randChance = rand.nextDouble();
            if (randChance <= 0.1) {
                Bullet bullet = new Bullet(this.x, this.y-10, "myassets/drop-delicate-blue.png");
                
                return bullet;
               
            }
        }
       
        return null;
    }
    
}

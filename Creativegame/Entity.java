//An Entity represents some indivdual thing that is drawn to the game window.
//Everything drawn and animated in the game window is an Entity, including the player.
//
//Each time the game window "refreshes" all entities in the game's display list
//are drawn according to their respective attributes.
public abstract class Entity {
    
    //Location of image file to be drawn for the Entity
    private String imageName;
    
    //The height and width of the entity (in pixels) to be drawn to the game window
    //Note that all Entities are ultimately drawn as rectangles in the game window.
    //An entity's image will be stretched to fit its rectangle per its height/width
    //This rectangle is also its "hitbox", governing its boundaries when determining collisions.
    private int height, width;
    //The x and y coordinate of the Entity to be drawn in the game window.
    protected int x, y;
    //Determines if the Entity is visible in the game window or not
    private boolean isVisible;
    //informs us if the entity is still alive or not
    protected boolean isDead;
    protected int currentLife;
    
    //represents how resistant an entity can be
    protected int life;
    
    public Entity(int x, int y, int width, int height, String imageName){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.imageName = imageName;
        this.isVisible = true; //by default, all newly instantiated Entities are visible
        this.isDead = false;
    }
    
    //Set and retrieve this entity's coordinates
    public int getX(){
        return x;
    }
    
    public void setX(int newX){
        this.x = newX;
    }
    
    public int getY(){
        return y;
    }
    
    public void setY(int newY){
        this.y = newY;
    }
    
    
    //Set and retrieve this entity's dimensions
    public int getHeight(){
        return height;
    }
    
    public void setHeight(int newHeight){
        this.height = newHeight;
    }
    
    public int getWidth(){
        return width;
    }
    
    public void setWidth(int newWidth){
        this.width = newWidth;
    }   
    
    
    //Set and retrieve this entity's image and visibility status
    public String getImageName(){
        return imageName;
    }
    
    public void setImageName(String newImageName){
        this.imageName = newImageName;
    }   
    
    public void setVisible(boolean isVisible){
        this.isVisible = isVisible;
    }
    
    public boolean isVisible(){
        return isVisible;
    }
    public void updateLife(Entity colEntity) {
        int lifechange = colEntity.getLifeValue();
        int newlife = this.currentLife + lifechange;
        this.setCurrentLife(newlife);
         
    }
    //Set the player's Life to a specific value. 
    private void setCurrentLife(int newlife) {
        newlife = Math.min(newlife, this.life);
        this.currentLife = Math.max(0, newlife);
        
        if (this.currentLife == 0) {
            isDead = true;
        }
    }

    public abstract int getLifeValue();

    //Retrieve the Player's current HP
    public int getCurrentLife() {
        return currentLife;
    }
    //Checks to see if this Entity is colliding with the argument Entity
    //Meaning any part of the two Entities are overlapping
    public boolean isCollidingWith(Entity other){
        //implement me!
        if (other.x < this.x){
            if ((Math.abs(this.y - other.y) <= other.height) & (other.y <= this.y)){
                return this.isCollidingWith(other.x+other.width, other.y+Math.abs(this.y - other.y));  
            }
            if ((Math.abs(this.y - other.y) <= this.height) & (other.y > this.y)){
                return this.isCollidingWith(other.x+other.width, other.y-Math.abs(this.y - other.y));  
            }

        }
        if (other.x >= this.x){
            if ((Math.abs(this.y - other.y) <= other.height) & (other.y <= this.y)){
                return this.isCollidingWith(other.x, other.y+Math.abs(this.y - other.y));  
            }
            if ((Math.abs(this.y - other.y) <= this.height) & (other.y > this.y)){
                return this.isCollidingWith(other.x, other.y-Math.abs(this.y - other.y));  
            }
             
        }
        
        return false;
           
    }
    
    //Checks to see if this Entity is colliding with the argument x,y coordinate
    //Meaning whether the argument coordinate is inside this Entity
    public boolean isCollidingWith(int x, int y){
        if (this.x <= x && this.x + this.width >= x){
            return (this.y <= y && this.y + this.height >= y);
        }
        return false;
    }
    
}

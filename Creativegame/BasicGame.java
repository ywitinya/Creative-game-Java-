import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

//A Simple version of the scrolling game, featuring Avoids, Gets, and RareGets
//Players must reach a score threshold to win
//If player runs out of HP (via too many Avoid collisions) they lose
public class BasicGame extends ScrollingGameEngine {

    // Dimensions of game window
    protected static final int DEFAULT_WIDTH = 900;
    protected static final int DEFAULT_HEIGHT = 600;

    // Starting Player coordinates
    protected static final int STARTING_PLAYER_X = 0;
    protected static final int STARTING_PLAYER_Y = 250;

    // Score needed to win the game
    protected static final int SCORE_TO_WIN = 300;

    // Maximum that the game speed can be increased to
    // (a percentage, ex: a value of 300 = 300% speed, or 3x regular speed)
    protected static final int MAX_GAME_SPEED = 300;
    // Interval that the speed changes when pressing speed up/down keys
    protected static final int SPEED_CHANGE = 20;

    protected static final String INTRO_SPLASH_FILE = "myassets/Splash-intro.png";
    // Key pressed to advance past the splash screen
    public static final int ADVANCE_SPLASH_KEY = KeyEvent.VK_ENTER;

    // Interval that Entities get spawned in the game window
    // ie: once every how many ticks does the game attempt to spawn new Entities
    protected static final int SPAWN_INTERVAL = 45;

    // A Random object for all your random number generation needs!
    protected static final Random rand = new Random();
    private static final int FINAL_STAGE = 4;

    // Player's current level
    protected int level;

    // Stores a reference to game's Player object for quick reference
    // (This Player will also be in the displayList)
    protected Player player;

    // other instance variables
    private int level2Rounds;
    private boolean isSpaceshipInstantiated;
    private boolean isStarthreatInstantiated;
    private int level4Rounds;
    private boolean hasWon;

    public BasicGame() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public BasicGame(int gameWidth, int gameHeight) {
        super(gameWidth, gameHeight);
    }

    // Performs all of the initialization operations that need to be done before the
    // game starts
    protected void pregame() {
        this.setBackgroundColor(Color.BLACK);
        // CreativeEntity get = new CreativeGet((int)(getWindowWidth()/2), 0);
        player = new Player(STARTING_PLAYER_X, STARTING_PLAYER_Y);
        displayList.add(player);
        level = 1;
        this.setTitleText("***DEMO*** Galaxy Shooting Game!");
        this.setSplashImage(INTRO_SPLASH_FILE);

    }

    // Called on each game tick
    protected void updateGame() {
        this.setBackgroundImage("myassets/newbg.jpg");
        // scroll all scrollable Entities on the game board
        scrollEntities();
        window.scroll();
        // Spawn new entities only at a certain interval

        if (ticksElapsed % SPAWN_INTERVAL == 0) {
            spawnEntities();
            garbageCollectOffscreenEntities();

        }
        // Update the title text on the top of the window
        setTitleText("HP:" + player.getCurrentLife() + ", Level:" + this.level);

    }

    // collect all exploded entities
    private void garbageCollectExplodedEntities() {
        for (int i = 0; i < displayList.size(); i++) {
            if (displayList.get(i) instanceof Splash) {
                displayList.remove(i);
            }
        }
    }

    // Scroll all scrollable entities per their respective scroll speeds
    protected void scrollEntities() {
        double shootingChance = rand.nextDouble();
        garbageCollectExplodedEntities();
        for (int i = 1; i < displayList.size(); i++) {

            // Scroll individual entities with their respective speed

            if (displayList.get(i) instanceof Scrollable) {
                if (displayList.get(i) instanceof Sthreat) {
                    ((Sthreat) displayList.get(i)).scroll(getWindowHeight(), getWindowWidth());
                    if (shootingChance <= 0.01) {
                        Entity bullet = ((Sthreat) displayList.get(i)).shoot();
                        if (bullet != null) {
                            displayList.add(bullet);
                        }

                    }

                } else {
                    ((Scrollable) displayList.get(i)).scroll();
                }

            }

            // Check for collisions
            if (!(displayList.get(i) instanceof PlayerBullets)) {
                Entity myEntity = displayList.get(i);
                if (player.isCollidingWith(displayList.get(i))) {
                    handlePlayerCollision(player, myEntity);
                }
            }

            else {
                PlayerBullets myBullet = (PlayerBullets) displayList.get(i);
                for (int j = 0; j < displayList.size(); j++) {
                    if ((displayList.get(j) instanceof Explodable)) {
                        if (myBullet.isCollidingWith(displayList.get(j))) {
                            handleAssetsCollision(displayList.get(j), (Entity) myBullet);
                        }
                    }

                }

            }

        }

    }

    // Handles "garbage collection" of the displayList
    // Removes entities from the displayList that have scrolled offscreen
    // (i.e. will no longer need to be drawn in the game window).
    protected void garbageCollectOffscreenEntities() {

        // **** implement me! ****
        for (int i = 0; i < displayList.size(); i++) {
            if (displayList.get(i) instanceof PlayerBullets) {
                if (displayList.get(i).getX() > getWindowWidth()) {
                    displayList.remove(i);
                }
            }
            if (displayList.get(i).getX() + displayList.get(i).getHeight() < 0) {
                displayList.remove(i);
            }
        }

    }

    private void handlePlayerCollision(Player player, Entity colEntity) {

        displayList.remove(colEntity);
        player.updateLife(colEntity);

        if (player.isDead) {

            displayList.remove((Entity) player);
            displayList.add(player.died());
        }

    }

    // Called whenever it has been determined that the Player collided with a
    // consumable
    protected void handleAssetsCollision(Entity collidedWith, Entity bullet) {

        // **** implement me! ****
        displayList.remove(bullet);
        if (collidedWith instanceof Explodable) {
            collidedWith.updateLife(bullet);

            if (collidedWith.isDead) {
                if (collidedWith instanceof Spaceship) {
                    ((Spaceship) collidedWith).stopShooting(displayList);
                }
                if (collidedWith instanceof Sthreat) {
                    Entity fortune = ((Sthreat) collidedWith).throwFortune();
                    if (fortune != null) {
                        displayList.add(fortune);
                    }

                }
                displayList.remove((Entity) collidedWith);
                displayList.add(((Explodable) collidedWith).Explode());

            }

        }

    }

    // Spawn new Entities on the right edge of the game board
    protected void spawnEntities() {
        // scrollable entities

        int starshapeThreatY = 0;
        int starshapeThreatX = getWindowWidth() / 2;

        // add bullets on screen
        ArrayList<Entity> bullet = player.shoot();
        for (int i = 0; i < bullet.size(); i++) {
            displayList.add(bullet.get(i));
        }

        // level##1 add Asteroid on the screen
        if (this.level == 1 & level4Rounds < 10) {
            int SThreatY = 0;
            int count = 0;
            int randomX = rand.nextInt(getWindowWidth() / 4, getWindowHeight() / 2);
            while (count < 2) {
                Sthreat avoid = new Sthreat(randomX, SThreatY);

                displayList.add(avoid);
                SThreatY -= 200;
                randomX += 200;
                count++;

            }
            level4Rounds++;

        }

        // level##2 add Asteroid on the screen
        if (this.level == 2 & level2Rounds < 10) {

            while (starshapeThreatY < getWindowHeight()) {
                int randomX = rand.nextInt(getWindowHeight());
                Asteroid asteroid = new Asteroid(getWindowWidth() + randomX, starshapeThreatY);

                displayList.add(asteroid);
                starshapeThreatY += 100;
                randomX += 100;

            }
            level2Rounds++;
        }

        // level##3 add starthreats on the screen

        if (level == 3 & !isStarthreatInstantiated) {
            this.isStarthreatInstantiated = true;
            for (int i = 0; i <= 41; i++) {
                Entity starshapeThreat = new StarThreat(starshapeThreatX, starshapeThreatY);
                displayList.add(starshapeThreat);
                starshapeThreatY -= starshapeThreat.getHeight();

            }

        }

        // level##4 add spaceship on the screen
        if (this.level == 4 & !isSpaceshipInstantiated) {
            Spaceship newAsset = new Spaceship(getWindowWidth(), 50);
            displayList.add(newAsset);
            this.isSpaceshipInstantiated = true;
        }

        if (Spaceship.canShoot) {
            ArrayList<Entity> mybullets = Spaceship.shoot();
            for (int i = 0; i < mybullets.size(); i++) {
                displayList.add(mybullets.get(i));
            }
        }

        if (level <= FINAL_STAGE) {
            for (int i = 0; i < displayList.size(); i++) {
                if (displayList.get(i) instanceof Explodable) {
                    return;
                }
            }
            if (level == FINAL_STAGE) {
                displayList.add(player.victory());
                hasWon = true;

            }
            this.level++;
        }

    }

    // Called once the game is over, performs any end-of-game operations
    protected void postgame() {
        if (player.getCurrentLife() != 0) {
            super.setTitleText("Game is over! You win!");
        } else {
            super.setTitleText("Game is over! You lose!");
        }

    }

    // Determines if the game is over or not
    // Game can be over due to either a win or lose state
    protected boolean isGameOver() {
        if (player.getCurrentLife() == 0 || hasWon) {
            return true;
        }

        return false;

    }

    // Reacts to a single key press on the keyboard
    protected void reactToKey(int key) {

        setDebugText("Key Pressed!: " + KeyEvent.getKeyText(key) + ",  DisplayList size: " + displayList.size());

        // if a splash screen is active, only react to the "advance splash" key...
        // nothing else!
        if (getSplashImage() != null) {
            if (key == ADVANCE_SPLASH_KEY)
                super.setSplashImage(null);

            return;
        }
        if (key == KEY_PAUSE_GAME) {
            isPaused = !isPaused;
        }
        if (key == KEY_TOGGLE_DEBUG) {
            isDebugEnabled = !isDebugEnabled;
        }
        // change the game speed
        if (key == SPEED_DOWN_KEY) {
            int newSpeed = Math.max(getGameSpeed() - SPEED_CHANGE, 1);// Clarify
            setGameSpeed(newSpeed);
        }
        if (key == SPEED_UP_KEY) {
            int newSpeed = Math.min(getGameSpeed() + SPEED_CHANGE, MAX_GAME_SPEED);
            setGameSpeed(newSpeed);
        }

        // move the player
        if (key == UP_KEY) {
            player.moveUp();
        }
        if (key == DOWN_KEY) {
            player.moveDown(getWindowHeight());
        }
        if (key == RIGHT_KEY) {
            player.moveright(getWindowWidth());
        }
        if (key == LEFT_KEY) {
            player.moveLeft();
        }
    }

    // Handles reacting to a single mouse click in the game window
    // Won't be used in Simple Game... you could use it in Creative Game though!
    protected MouseEvent reactToMouseClick(MouseEvent click) {
        if (click != null) { // ensure a mouse click occurred
            int clickX = click.getX();
            int clickY = click.getY();
            setDebugText("Click at: " + clickX + ", " + clickY);
        }
        return click;// returns the mouse event for any child classes overriding this method
    }

}

//all non scrollable Entities are defined as splash entities

public class Splash extends Entity{

    public Splash(int x, int y, int size, String string) {
        this(x, y, size, size, string);
    }

    public Splash(int x, int y, int height, int width, String string) {
        super(x, y, height, width, string);

    }

    //they dont affect player's life in case of collision 
    //it might barely happen
    public int getLifeValue() {
        return 0;
    }

}

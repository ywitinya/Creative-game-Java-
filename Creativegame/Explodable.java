//Interface to be implemented by any Entities which are "consumable"
//Consumable means two things happen when the player collides with the Entity:
//   -The consumable entity collided with is destroyed
//   -The player's score and/or HP are modified upon collision.
public interface Explodable {
    
    public Entity Explode();
    
}


// Monster Math is a text-based adventure game, designed to help children in grades five 
// and six improve their performance of simple arithmetic operations 
                                                                                                                                       
public class MonsterMath{
   public static void main(String[] args){
      // Player object holds information about the player
      Player player = new Player(); 
      // Room objects hold information about the room and the enemies in it     
      Room intro = new Room(0, 0, player); 
      Room rm_1_1 = new Room(1, 1, 1, 2, 3, 1, "Addition Antlion", "A flying insect, weak to addition", player);
      Room rm_1_2 = new Room(1, 2, 2, 3, 6, 2, "Sum Serpent", "A snake-spirit, weak to addition", player);
      Room rm_2_1 = new Room(2, 1, 2, 3, 6, 1, "Subtraction Skeleton", "An undead monster, weak to sutraction", player);
      Room rm_2_2 = new Room(2, 2, 3, 4, 9, 2, "Difference Demon", "An evil spirit, weak to subtraction", player);
      Room rm_3_1 = new Room(3, 1, 3, 4, 9, 1, "Multiplication Mandrake", "A plant-based monster, weak to multiplication", player);
      Room rm_3_2 = new Room(3, 2, 4, 5, 12, 2, "Product Pugwudgie", "A troll-like beast, weak to multiplication", player);
      Room boss = new Room(4, 2, 5, 10, 30, 10, "Dragon", "A giant, fire-breathing dragon. It has no weakness", player);
      
      //Intro
      intro.executeRoom(0, 0, player);
      
      // Level 1, Room 1
      rm_1_1.executeRoom(1, 1, player);
      
      //Level 1, Room 2
      rm_1_2.executeRoom(1, 2, player);
      
      // Level 2, Room 1
      rm_2_1.executeRoom(2, 1, player);
      
      // Level 2, Room 2
      rm_2_2.executeRoom(2, 2, player); 
      
      // Level 3, Room 1
      rm_3_1.executeRoom(3, 1, player);
      
      //Level 3, Room 2
      rm_3_2.executeRoom(3, 2, player);
      
      // Boss fight
      boss.executeRoom(4, 2, player);      
   }
}
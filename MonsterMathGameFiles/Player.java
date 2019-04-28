
// Player Class - used to create an object that holds player's information/current status 

public class Player{
   private String name;                         // Name that player enters
   private int maxHP;                           // Player's max HP
   private int currentHP;                       // Player's current HP
   private int xP;                              // Total amount of XP player has accumulated for defeating enemies
   private int questAns;                        // Number of questions player has answered during the game
   private int questCorr;                       // Number of questions player has answered correctly during the game 
   private boolean[] equipped = new boolean[3]; // Indicates if item is equipped
   
   public Player(){
      maxHP = 20; 
      currentHP = maxHP;
      xP = 0;
      questAns = 0;
      questCorr = 0;

   }
   
   public void setName (String input){
      name = input;
   }
   
   public String getName(){
      return name;
   }
   
   public void setMaxHP(int highHP){
      maxHP = highHP;
   }
   
   public int getMaxHP(){                                                                                                                 
      return maxHP;
   }
   
   public void setCurrentHP(int nowHP){
      currentHP = nowHP;
   }   
      
   public int getCurrentHP(){
      return currentHP;
   }
   
   public void setXP (int currentXP){
      xP = currentXP;
   }
   
   public int getXP(){
      return xP;
   }
   
   public void setQuestAns(int qa){
      questAns = qa;
   }
   
   public int getQuestAns(){
      return questAns;
   }
   
   public void setQuestCorr(int qc){
      questCorr = qc;
   }
   
   public int getQuestCorr(){
      return questCorr;
   }
   
   public void setItem(int index){
      equipped[index] = true;
   }
   
   public boolean getItem(int i){
      return equipped[i];
   }
   
   //Method that prints inventory if player selects 1 at "choose option" prompt 
   public void printInventory(Player player){
      System.out.printf("%n%s's Inventory:", getName());                           // Player's name
      System.out.printf("%n**********************");                                      
      System.out.printf("%nHealth: %d/%dHP", getCurrentHP(), getMaxHP());          // Current HP out of Maximum HP
      System.out.printf("%nExperience: %dXP", getXP());                            // XP
      System.out.printf("%nQuestions answered: %d", getQuestAns());                // Number of questions answered
      System.out.printf("%nQuestions correct: %d%n", getQuestCorr());              // Number of questions correct
      if (equipped[0] == true)                                                            
         System.out.println("Shield of Subtraction - Reduces damage dealt by enemy");     // Shield of Subtraction
      if (equipped[1] == true)
         System.out.println("Multiplication Mace - Increases damage you deal");           // Multiplication Mace
      if (equipped[2] == true)
         System.out.println("Arithmetic Armour - Reduces damage dealt by enemy");         // Arithmethic Armour
   }
}
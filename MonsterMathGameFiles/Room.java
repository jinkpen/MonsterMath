
import java.util.Scanner;
import java.util.Random;
import java.util.InputMismatchException;

/* Room Class - used to make Room objects that each hold information about a specific room in the game, including the room 
   type, the level of difficulty of the room, and the attributes of the enemy found in that room. This class includes methods 
   that execute sequences of events (executeRoom, executeRoomAfterKO, startRoomSequence, endRoomSequence, addFights), fights 
   with enemies (fight, bossFight, attackSequence, defense sequence), and interactions with the narrator, aka Math Wiz 
   (chooseOption, tutorial, changeMaxHP, restoreHP, giveItem) */

public class Room{
	private int roomType;               // Type of room (0 - none, 1 - add, 2 - subtract, 3 - multiply, 4 - boss)
	private int difficulty;             // Level of difficulty of the room (0 - none, 1 - easy, 2 - hard, 3 - boss)
	private int hitHP;                  // Amount of HP enemy loses if player answers question correctly
   private int missHP;                 // Amount of HP player loses if player answers question incorrectly
	private int enemyHP;                // Amount of HP enemy has remaining
	private int addXP;                  // Amount of XP player gets for defeating an enemy
	private int questAns;               // Number of questions player has answered in a room
	private int questCorr;              // Number of questions player has answered correctly in a room
	private String enemyName;           // Name of the enemy player will face in a given room
	private String enemyDescribe;       // Description of the enemy player will face in a given room

	Scanner kb = new Scanner(System.in);
   Random random = new Random();
                                                                                                                                    
	// Constructor for Intro room
	public Room(int rt, int d, Player player){
		roomType = rt;                   // Type of room
		difficulty = d;                  // Level of difficulty
	}

	// Constructor for rooms that contain enemies
	public Room(int rt, int d, int hp, int attackHP, int monsterHP, int xp, String monster, String monsterDescribe, Player player){
		roomType = rt;                   // Type of room
		difficulty = d;                  // Level of difficulty
		hitHP = hp;                      // Amount of HP enemy loses if player answers question correctly
      missHP = attackHP;               // Amount of HP player loses if player answers question incorrectly
		enemyHP = monsterHP;             // Amount of HP enemy has remaining
		addXP = xp;                      // Amount of XP player gets for defeating an enemy
		questAns = 0;                    // Number of questions player has answered in a room
		questCorr = 0;                   // Number of questions player has answered correctly in a room
		enemyName = monster;             // Name of the enemy player will face in room
		enemyDescribe = monsterDescribe; // Description of the enemy player will face in room
	}

	public int getRoomType(){
		return roomType;
	}

	public int getHitHP(){
		return hitHP;
	}
   
   public int getMissHP(){
		return missHP;
	}

	public int getEnemyHP(){
		return enemyHP;
	}

	public int getAddXP(){
		return addXP;
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

	public String getEnemyName(){
		return enemyName;
	}

	public String getEnemyDescribe(){
		return enemyDescribe;
	}

	// Method to execute a given room (called from the main method)	
   public void executeRoom(int rt, int d, Player player){
		// Intro room
		if (rt == 0){
			startRoomSequence(rt, d, player);
			tutorial(rt, player);
		}
		// Rooms 1_1, 1_2, 2_1, 2_2, 3_1, 3_2
		if (rt == 1 || rt == 2 || rt == 3){
			if (d == 1){
				startRoomSequence(rt, d, player); 
				tutorial(rt, player);
				fight(rt, d, player);
				//chooseOption(player);
				//fight(rt, d, player);
				//chooseOption(player);
				//fight(rt, d, player);
				addFights(rt, d, player);
				endRoomSequence(rt, d, player); 
			}
			if (d == 2){
				startRoomSequence(rt, d, player);
				chooseOption(player);
				fight(rt, d, player);
				//chooseOption(player);
				//fight(rt, d, player);
				//chooseOption(player);
				//fight(rt, d, player);
				addFights(rt, d, player);
				endRoomSequence(rt, d, player); 
			}
		}		
      // Boss fight room
		if (rt == 4){
			startRoomSequence(rt, d, player);
			bossFight(rt, d, player);
			endRoomSequence(rt, d, player);
		}
	}

	// Sets player back to start of room if they lose all of their HP (called from fight method)
	public void executeRoomAfterKO(int rt, int d, Player player){
		startRoomSequence(rt, d, player);
		chooseOption(player);
		fight(rt, d, player);
		chooseOption(player);
		fight(rt, d, player);
		chooseOption(player);
		fight(rt, d, player);
      // Room continues from where player was KOd after this method terminates
	}

	// Method that allows player to choose to quit, view their inventory, or keep moving
	public void chooseOption(Player player){
		int choice = -1;
		System.out.println("\nTo chose an option, type the matching number.");
		System.out.println("\t  [0] I quit. Get me out of here. \n\t  [1] Inventory \n\t  [2] I want to keep going!");
      while (choice != 0 && choice != 1 && choice != 2){
         try{
            System.out.print("\nEnter your choice (0, 1, or 2): ");
            choice = kb.nextInt();
         }
         catch(InputMismatchException exception){                // Catches input that is not an integer
            kb.next();
            System.out.println("\nI'm not sure what you are trying to say...");
            System.out.println("\nTo chose an option, type the matching number.");
		      System.out.println("\t  [0] I quit. Get me out of here. \n\t  [1] Inventory \n\t  [2] I want to keep going!");
         }
      }     
      while (choice != 2){
         if (choice == 0){          // Exit game
			   System.out.println("Okay, bye.");
			   System.exit(0);         // I know there are few situations where doing this won't cause major problems... YOLO 
         } 
         else if (choice == 1){     // Prints player inventory and asks them to chose another option
	         player.printInventory(player);
			   System.out.println("\nMath Wiz: Now what would you like to do?");
			   System.out.println("\t  [0] I quit. Get me out of here. \n\t  [1] Inventory \n\t  [2] I want to keep going!");
            choice = -1;
            
            while (choice != 0 && choice != 1 && choice != 2){
               try{
                  System.out.print("\nEnter your choice (0, 1, or 2): ");
                  choice = kb.nextInt();        // Player enters choice
               }
               catch(InputMismatchException exception){       // Catches input that is not an integer
                  kb.next();
                  System.out.println("\nI'm not sure what you are trying to say...");
                  System.out.println("\nTo chose an option, type the matching number.");
		            System.out.println("\t  [0] I quit. Get me out of here. \n\t  [1] Inventory \n\t  [2] I want to keep going!");
               }         
            }
         }
      }
   }  
       
	// Method that gives the beginning description of a given level	
   public void startRoomSequence(int rt, int d, Player player){
		String input;
		// Introduction
		if (rt == 0 && d == 0){
         printTitle();                    // Prints title in ASCII lettering
			System.out.println("\n\nYou are walking through a clearing at the edge of a forest." + 
                            " A voice from above calls out...");
			System.out.println("Voice: Welcome adventurer! What is your name? ");
			System.out.println("\nYou look up and see a wizard tied with a shimmering silver" +
                            " ribbon, high up in a tree.");
			System.out.print("You reply: Hi! My name is (enter your name): ");
			input = kb.nextLine();           // Player enters name
			player.setName(input);           // Player's name set 
			System.out.println("\nWizard: Hello, " + player.getName() + ". I am the Math Wiz, and I need your help.");

			// The player choses which question to ask. When the player selects question 2, the game continues.
			int reply = -1;
			String q1 = "What are you doing up there?", q2 = "What do you want me to do?";
			System.out.println("\nAsk the Math Wiz a question. Choose an option by typing the number.");
			System.out.println("\t  [1] " + q1 + " \n\t  [2] " + q2);
         while (reply != 1 && reply != 2){
            try {
               System.out.print("\nEnter your choice (1 or 2): ");
               reply = kb.nextInt();
            }
            catch(InputMismatchException exception){           
               kb.next();
               System.out.println("\nI'm not sure what you are trying to say! Please try again.");
               System.out.println("\nAsk the Math Wiz a question. Choose an option by typing the number.");
					System.out.println("\t  [1] " + q1 + " \n\t  [2] " + q2);
            }
         }         
			if (reply == 1){
				System.out.println("\n" + player.getName() + ": " + q1);
				System.out.println("\nMath Wiz: I have been robbed and bound to this tree" +
							          " with a spell that is broken only\n\t  when the caster" +
							          " is defeated. Do you think you can help me? ");
				while (reply != 2){
               try {
                  System.out.print("\nEnter 2 to find out what the Math Wiz wants: ");
                  reply = kb.nextInt();
               }	
               catch(InputMismatchException exception){
                  kb.next();
                  System.out.println("\nI'm not sure what you are trying to say! Please try again.");
               }
				}
			}
				System.out.println("\n" + player.getName() + ": " + q2);
				System.out.println("\nMath Wiz: Somewhere deep in this forest lies the dragon" +
						             " who did this to me. The dragon stole my\n\t  Arithmetic" +
						             " Amulet, and without it, I am unable to teach the Friendly" +
						             " Beasts of the\n\t  forest the math skills they need to" +
						             " protect themselves against the Math Monsters who\n\t  live" + 
                               " there.");
				System.out.println("Math Wiz: But you are human, so you can learn math without" +
						             " the Arithmetic Amulet! With my\n\t  guidance, you can" +
						             " venture deep into the forest to defeat the dragon and seize" +
						             " my\n\t  amulet, so that I can teach the Friendly Beasts math," +
						             " so they don't have to live\n\t  in fear of the Math Monsters."); 
				System.out.println("\nMath Wiz: " + player.getName() + ", will you help me?");
				System.out.println("\t  [1] No! get me out of here! \n\t  [2] Yes, I will help you!");
         reply = -1;
         while (reply != 2){
            try{
               System.out.print("\nEnter your choice (1 or 2): ");
               reply = kb.nextInt();
            }
            
            catch (InputMismatchException exception){	
               kb.next();			
               System.out.println("\nI'm not sure what you are trying to say! Please try again.");
               System.out.println("\t  [1] No! get me out of here! \n\t  [2] Yes, I will help you!");
            }
            if (reply == 1){
               System.out.println("Okay, bye.");
               System.exit(0);
            }              
         }
		}
		// Level 1, room 1
		if (rt == 1 && d == 1){
			System.out.println("\nYou walk into the forest. The canopy is high and the sun shines" +
					             " through. The brush is not too\n\t  thick, and you spot a path... ");
			chooseOption(player);
			// When path is chosen
			System.out.println("\nAs you step onto the path, You hear the Math Wiz...");
			System.out.println("\nMath Wiz: " + player.getName() + ", you have entered the Sunshine Lane." +
					             " Here, the monsters use magic that will not\n\t  pass through addition." + 
                            " They are also weak to addition attacks, so use your addition\n\t  skills" +
                            " to defeat them.");
		}
		// Level 1, room 2
		if (rt == 1 && d == 2){
			System.out.println("\nAs you step past the boulder, you hear rustling in the bushes...");
			System.out.println("Do you keep going?");
		}
		// Level 2, room 1
		if (rt == 2 && d == 1){
			System.out.println("\nYou crawl throught the rotting, hollow log on your hands and knees. You" +
					             " feel yourself moving down a \nhill, and you reach the end suddenly. There" +
					             " is almost no \nlight coming through the tree tops, and the tree trunks cast" +
					             " shadows that seem darker than black. \nYou can hear a brook running nearby." +
					             " You listen for the Math Wiz...");
			System.out.println("\nMath Wiz: " + player.getName() + ", you are in the Shadowy Ravine. Follow" +
					             " along the edge of the brook. When you get to the tree that \n\t  thas fallen" +
					             " over the brook, walk across it, but be careful! The monsters on the other \n\t" +
					             "  side are stronger than the ones you'll find here.");
			System.out.println("\nMath Wiz: Here in the Shadowy Ravine, you will find monsters whose attacks will not" +
					             "\n\t  go through subtraction. These same monsters are weak to subtraction attacks," +
					             " so you \n\t  must use your subtraction skills to defeat them.");
		}		
      // Level 2, room 2
		if (rt == 2 && d == 2){
			System.out.println("\nOnce you cross the brook, you see a stone path, and you hear the Math Wiz again...");
			System.out.println("\n Math Wiz: " + player.getName() + ", follow the stone path until you reach a rope" +
					             " hanging from a cliff. When you get there \n\t  listen for my voice.");
			System.out.println("\nWhen the Math wiz stops speaking, you see movement in the shadows...");
			System.out.println("Do you keep going?");
		}
		// Level 3, room 1
		if (rt == 3 && d == 1){
			System.out.println("\nYou climb up the rope. When you get to the top of the cliff, you notice that it" +
				                " is lighter up on the \nplateau, but the brush is thick. You spot a footpath cutting" +
					             " through the brush. \nYou listen for the Math Wiz...");
			System.out.println("\nMath Wiz: " + player.getName() + ", you have reached the Backwoods Plateau. Follow the" +
							       " footpath through the bushes. Halfway \n\t  through, there is a branch in the path: take" +
							       " the path to the right, but be careful. The \n\t  monsters down that path are stronger" +
							       " than theones you'll encounter before it.");
			System.out.println("\nMath Wiz: Here in the Backwoods Plateau, you will find monsters whose attacks will not" +
							       " go through \n\t  multiplication. These same monsters are weak to multiplication attacks," +
							       " so you must use your \n\t  multiplication skills to defeat them.");
		}
		// Level 3, room 2
		if (rt == 3 && d == 2){
         System.out.println("\nAs you start down the path, you hear the Math Wiz again...");
         System.out.println("\nMath Wiz: " + player.getName() + ", you are almost there! Keep following this path" +
                            " until you reach a small clearing with a \n\t  cave on the other side. When you get" +
                            " there, you will see a pile of rocks and bones, just to \n\t  your left. Hide behind" +
                            " it, and listen for my voice.");
         System.out.println("\nThe Math Wiz's voice fades away, and you hear branches breaking in the bushes." +
                            " \nDo you want to keep going?");
      }		
      // Boss fight room
		if (rt == 4 && d == 2){
         System.out.println("\nYou cross the clearing to the cave entrance...");
         System.out.println("There is a smoky smell coming from the cave...");
         System.out.println("\nYou enter the cave slowly and quietly...");         
         System.out.println("Past the entrance, the cave is dark except for the glowing green amulet in the" +
                            " back of the cave... \nbehind a giant dark mound... The dragon!");
         System.out.println("\nYou start to back away, but the dragon raises its head. Smoke blows out its" +
                            " nostrils...");
         System.out.println("\n" + player.getName() + ": I have come to take back the Arithmetic Amulet and" +
                            " free the Math Wiz!");
         System.out.println("\nThe dragon rises...");
         printDragon();                      // Prints ASCII art dragon
      }      
	}

	// Sequence that executes at the end of each room (except intro)
	 public void endRoomSequence(int rt, int d, Player player){
      // Level 1, room 1
      if (rt == 1 && d == 1){
         System.out.println("\nTo your left, you spot the boulder the Math Wiz told you about. The" +
                            " enemies beyond it are a bit tougher. \nDo you want to keep going?");
         chooseOption(player);
         restoreHP(player);                  // Restore player's currentHP if below 50%
      }
      // Level 1, room 2
      if (rt == 1 && d == 2){
         System.out.println("\nYou see the hollowed log, and listen for the Math Wiz...");
         changeMaxHP(rt, player);            // Increase player's Max HP and restore to full health
         giveItem(rt, player);               // Give player item if they correctly answer all questions in room 
         System.out.println("\nMath Wiz: To get to the dragon, you must crawl through the log." +
                            "\n\t  Do you want to keep going?");
         chooseOption(player);
      }
      // Level 2, room 1
      if (rt == 2 && d == 1){
         System.out.println("\nYou spot the fallen tree. \nDo you want to cross the brook and keep going?");
         chooseOption(player);
         restoreHP(player);                  // Restore player's currentHP if below 50%
      }
      // Level 2, room 2
      if (rt == 2 && d == 2){
         System.out.println("\nYou reach the cliff, and listen for the Math Wiz...");
         changeMaxHP(rt, player);            // Increase player's Max HP and restore to full health
         giveItem(rt, player);               // Give player item if they correctly answer all questions in room
         System.out.println("\nMath Wiz: To reach the dragon, you must climb the rope." +
                            "\nMath Wiz: Do you want to keep going?");
         chooseOption(player);
      }      
      // Level 3, room 1
      if (rt == 3 && d == 1){
         System.out.println("\nYou spot the split in the path. The path to the left is blocked by an old" +
                            " wooden gate. \nDo you take the path to the right and keep going?");
         chooseOption(player);
         restoreHP(player);                  // Restore player's currentHP if below 50%
      }
      // Level 3, room 2
      if (rt == 3 && d == 2){
         System.out.println("\nYou reach the end of the path, and duck in behind the pile of rock and" +
                            " bone. You peer around the pile and \nsee the cave, just across the clearing" +
                            " Your heart is pounding.\n");
         changeMaxHP(rt, player);            // Increase player's Max HP and restore to full health
         giveItem(rt, player);               // Give player item if they correctly answer all questions in room
         System.out.println(player.getName() + ": Math Wiz? Does the dragon live in that cave?");
         System.out.println("\nMath Wiz: Yes " + player.getName() + ", the dragon lives in that cave.\n");
         System.out.println(player.getName() + ": Do you really think that I can defeat the dragon?");
         System.out.println("\nMath Wiz: Of course you can, " + player.getName() + "! You have practiced" +
                            " all the skills you need to beat the dragon on your \n\t  journey here. The" +
                            " dragon is not weak to any arithmetic operation, and you will have use you" +
                            "\n\t addition, subtraction, and multiplication skills to defeat it.");
         System.out.println("\nMath Wiz: Do you want to keep going?");
         chooseOption(player);
      }
      // Boss fight room + conclusion to game
      if (rt == 4 && d == 2){
         System.out.println("\n\nYou walk deeper into the cave and grab the Arithmetic Amulet.");
         System.out.println("Suddenly, the Math Wiz appears at your side, and you hand over the Amulet...");
         System.out.println("\nMath Wiz: Thank you so much " + player.getName() + "! You defeated the" + 
                            " dragon, and got my Amulet back. \n\t  Thanks to you I can teach math to" +
                            " the Friendly Beasts of the forest, so they will be protected \n\t  from" +
                            " the Math Monsters");
         System.out.print("\nMath Wiz: I award you the title of ");
         
         /* Player awarded title based on XP or correct answer rate throughout the game
            High XP titles relate to perserverance (through answering extra questions)
            High correct rate titles relate to skill mastery */         
         if (player.getXP() >= 46)                                               // Master Trooper
            System.out.println("Master Trooper,\n\t  ... for showing the strength and determination to" +
                               " perservere through challenges! \n\t  Fantastic!");
         else if (player.getXP() >= 41)                                          // Expert Trooper
            System.out.println("Expert Trooper,\n\t  ... for having the strength to get through the" +
                               " challenges you faced! \n\t  Nice work!");
         else if (player.getXP() >= 36)                                          // Advances Trooper
            System.out.println("Advanced Trooper, \n\t  ... for your perserverance through adversity! " +
                               "\n\t  Great job!");
         else if (player.getQuestCorr() == player.getQuestAns())                 // Arithmetic Master
            System.out.println("Arithmetic Master,\n\t  ... for defeating all monsters without sustaining" +
                               " any damage! Wow! \n\t  That is incredible!");
         else if ((double)player.getQuestCorr() / player.getQuestAns() >= 0.95)  // Arithmetic Expert
            System.out.println("Arithmetic Expert,\n\t  ... for defeating all monsters with barely a" +
                               " scratch on you! \n\t  Fantastic work!");
         else if ((double)player.getQuestCorr() / player.getQuestAns() >= 0.9)   // Arithmetic Keener
            System.out.println("Arithmetic Keener,\n\t  ... for defeating all mosnters and not taking" +
                               " many hits! \n\t  Great job!");
         else                                                               // Math Pro - Standard title for completing game
            System.out.println("Math Pro,\n\t  ... for honing your math skills and getting back my Arithmetic" +
                               " Amulet!\n\t  Nice work!");
         System.out.println("\nMath Wiz: Thank you! And, don't forget to keep practicing your math skills!" +
                            "\n\t  You never know when you'll need them!");
         System.out.println("\nThe Math Wiz grabs your arm, and you feel a woosh...");
         System.out.println("\n... You wake up in the clearing, at the base of the tree where you first met" +
                            " the Math Wiz.\n... You look around, but you are alone.");
         System.out.println("\n" + player.getName() + ": Wait a minute... Math Wiz? Was that all just a dream?");
         System.out.println("\n... You hear no reply.");
         printWizard();
      }
   }
	
   // Tutorial method - Executes from executeRoom if its the intro or the first room of a level
	// Questions in math tutorials are randomly generated, and player can view more than one demo
	public void tutorial(int rt, Player player){
		int x = 0, y = 0, reply = -1;		
      // Inventory tutorial
		if (rt == 0){
         System.out.println("\nMath Wiz: " + player.getName() + ", there are a few things you should know" +
                            " before you start your adventure ");
         System.out.println("\nMath Wiz: Your HP is your 'hit points' and you lose HP if you are hit by a" +
                            " Math Monster\n\t  in battle. The amount of HP you lose depends on the Math" +
                            " Monster's strength.");
         System.out.println("\nMath Wiz: If your HP reaches 0 at any point in this adventure you be knocked" +
                            " out. I will\n\t  use my limited magic power to teleport you out of danger," +
                            " and restore your HP.");
         System.out.println("\nMath Wiz: You will accumulate XP, or 'experience points' when you defeat a" +
                            " Math Monster.\n\t  If you have a lot of XP at the end of the game, you will" +
                            " achieve a special title."); 
         System.out.println("\nMath Wiz: You can also earn special titles for taking little-to-no damage on" +
                            " your adventure.");                  
         System.out.println("\nMath Wiz: You can also obtain very powerful artifacts to aid you in the fight" +
                            " against evil,\n\t  but only if you perform perfectly againt the trickier Math" +
                            " Monsters.");                     
         System.out.println("\nMath Wiz: You can keep track of how you are doing, and what item you have" +
                            " equipped by checking\n\t  your inventory when you have the option."); 
         System.out.println("\nMath Wiz: Do you want to keep going "+ player.getName()+"?");
         chooseOption(player);
      }
		// Addition tutorial
		if (rt == 1){
			System.out.println("\nMath Wiz: Do you know how to add?");
			System.out.println("\t  [1] Yes! \n\t  [2] No, can you teach me?");
         while (reply != 1 && reply != 2){
            try {
               System.out.print("\nEnter your choice (1 or 2): ");
               reply = kb.nextInt();
            }
            catch(InputMismatchException exception){
               kb.next();
               System.out.println("\nI'm not sure what you are trying to say! Please try again.");
               System.out.println("\nMath Wiz: Do you know how to add?");
			      System.out.println("\t  [1] Yes! \n\t  [2] No, can you teach me?");
			   }
         }         
         while (reply == 2){
            Random random = new Random();            
				x = random.nextInt(51) + 1;
				y = random.nextInt(11) + 1;           
				System.out.println("\nMath Wiz: When you add two numbers, you increase the amount of"+
							          " one number by another number. \n\t  For example, you pick " + x +
							          " apples from one tree, and " + y + " apples from another tree. " +
							          " \n\t  How many apples do you have? \n\t  " + x + " + " + y + " = " + 
                               (x + y) + ", so you have " + (x + y) + " apples!");
				reply = -1;
            System.out.println("\nMath Wiz: Do you understand now?");
				System.out.println("\t [1] Yes, I've got it! \n\t [2] No, could you do another example?");
            while (reply != 1 && reply != 2){
               try {
                  System.out.print("\nEnter your choice (1 or 2): ");
                  reply = kb.nextInt();
               }
               catch(InputMismatchException exception){
                  kb.next();
                  System.out.println("\nI'm not sure what you are trying to say! Please try again.");
                  System.out.println("\nMath Wiz: Do you understand now?");
	               System.out.println("\t  [1] Yes! \n\t  [2] No, could you do another example?");
			      }
            }
         }
      }
      // Subtraction tutorial
		if (rt == 2){
         System.out.println("\nMath Wiz: Do you know how to subtract?");
			System.out.println("\t  [1] Yes! \n\t  [2] No, can you teach me?");
         while (reply != 1 && reply != 2){
            try {
               System.out.print("\nEnter your choice (1 or 2): ");
               reply = kb.nextInt();
            }
            catch(InputMismatchException exception){
               kb.next();
               System.out.println("\nI'm not sure what you are trying to say! Please try again.");
               System.out.println("\nMath Wiz: Do you know how to subtract?");
			      System.out.println("\t  [1] Yes! \n\t  [2] No, can you teach me?");
			   }
         }
         while (reply == 2){
            Random random = new Random();
            x = random.nextInt(51) + 21;
            y = random.nextInt(11) + 1;            
            System.out.println("\nMath Wiz: When you subtract, you decrease the amount of" + 
                               " one number by another number. \n\t  For example, you have " + x + 
                               " apples in your pile, and you give me " + y + " apples." +
                               " \n\t  How many apples do you have? \n\t  " + x + " - " + y + 
                               " = " + (x - y) + ", so you have " + (x - y) + " apples!");
			   reply = -1;
            System.out.println("\nMath Wiz: Do you understand now?");
				System.out.println("\t  [1] Yes, I've got it! \n\t  [2] No, could you do another example?");
			   while (reply != 1 && reply != 2){
               try {
                  System.out.print("\nEnter your choice (1 or 2): ");
                  reply = kb.nextInt();
               }
               catch(InputMismatchException exception){
                  kb.next();
                  System.out.println("\nI'm not sure what you are trying to say! Please try again.");
                  System.out.println("\nMath Wiz: Do you understand now?");
	               System.out.println("\t  [1] Yes! \n\t  [2] No, could you do another example?");
			      }
            }
         }
      }
		// Multiplication tutorial
		if (rt == 2){
         System.out.println("\nMath Wiz: Do you know how to multiply?");
			System.out.println("\t  [1] Yes! \n\t  [2] No, can you teach me?");
         while (reply != 1 && reply != 2){
            try {
               System.out.print("\nEnter your choice (1 or 2): ");
               reply = kb.nextInt();
            }
            catch(InputMismatchException exception){
               kb.next();
               System.out.println("\nI'm not sure what you are trying to say! Please try again.");
               System.out.println("\nMath Wiz: Do you know how to multiply?");
			      System.out.println("\t  [1] Yes! \n\t  [2] No, can you teach me?");              
			   }
         }                  
         while (reply == 2){
            Random random = new Random(); 
				x = random.nextInt(13) + 1;
				y = random.nextInt(13) + 1;
				System.out.println("\nMath Wiz: When you multiply, you are adding one number together" +
							          " as many times as the other number. \n\t  For example, you have " + x +
							          " piles of apples, with " + y + " apples in each pile." +
							          " \n\t  How many apples do you have? \n\t  " + x + " x " + y + " = " + 
							          (x * y) +  ", so you have " + (x * y) + " apples!");
				reply = -1;
            System.out.println("\nMath Wiz: Do you understand now?");
				System.out.println("\t  [1] Yes, I've got it! \n\t  [2] No, could you do another example?");
            while (reply != 1 && reply != 2){
               try {
                  System.out.print("\nEnter your choice (1 or 2): ");
                  reply = kb.nextInt();
               }
               catch(InputMismatchException exception){
                  kb.next();
                  System.out.println("\nI'm not sure what you are trying to say! Please try again.");
                  System.out.println("\nMath Wiz: Do you understand now?");
	               System.out.println("\t  [1] Yes! \n\t  [2] No, could you do another example?");
			      }
            }
         }
      }
	}

	// Fight method
	public void fight(int rt, int d, Player player){
		int hp = getEnemyHP();                          // Enemy's remaining HP
		int playerHP = player.getCurrentHP();           // Player's current HP
		int hitHP = getHitHP();                         // HP enemy loses if player answers correctly
		int missHP = getMissHP();                       // HP player loses if they answer incorrectly
		int addXP = getAddXP();                         // XP player earns for defeating an enemy
      int count = 0;                                      // Count of player/monster turns throughout fight
		String enemy = getEnemyName();
		String describe = getEnemyDescribe();

		// Change hitHP/missHP to reflect items that are equipped
		if (player.getItem(0) == true)                  // Check to see if item (Shield of Subtraction) is equipped
			missHP -= 1;                                 // If equipped, player takes one fewer HP damage when hit by enemy
		if (player.getItem(1) == true)                  // Check to see if item (Multiplication Mace) is equipped
			hitHP += 1;                                  // If equipped, player does one more HP damage
		if (player.getItem(2) == true)                  // Check to see if item (Arithmetic Armor) is equipped
			missHP -= 1;                                 // If equipped, player takes one fewer HP damage when hit by enemy

		// Print the description of the enemy
		System.out.printf("%n%s%n%s%nHP: %d%n", enemy, describe, hp);

		while (playerHP > 0 && hp > 0){                 // While both player and enemy have more than 0 HP
         count++;                                     // Increment count at the beginning of every turn
         if (count % 2 != 0)                          // If count is not even
				// Attack sequence executes
				hp = attackSequence(rt, d, hp, playerHP, hitHP, enemy, player);
			else
				// Defense sequence executes
				playerHP = defenseSequence(rt, d, hp, playerHP, missHP, enemy, player);
		}
		
      if (playerHP <= 0){
			// Executes if player loses all their HP, they are knocked out
			System.out.printf("%n Uh oh. You are knocked out...%n");
			System.out.printf("%n...%n");
			System.out.printf("%nMath Wiz: Wake up %s. I have brought you back a bit and restored your HP.%n",
					            player.getName());
			System.out.printf("%nYou get up and dust yourself off...%n");
			restoreHP(player);                           // Restore player HP
			executeRoomAfterKO(rt, d, player);           // Move player to beginning of current room
		} 
      
      else{
			// Player gets XP
			System.out.println("\nYou defeat the enemy!!!");
			System.out.println("You get " + addXP + "XP!");
			player.setXP(player.getXP() + addXP);
			player.setCurrentHP(playerHP);
		}
	}

	// Boss fight method
	public void bossFight(int rt, int d, Player player){
		int questionType = rt;                             // Random number passed into attack/defense sequence as rt 
                                                            // parameter to determine question operation		
      int hp = getEnemyHP();                             // Enemy's remaining HP
		int playerHP = player.getCurrentHP();              // Player's current HP
		int hitHP = getHitHP();                            // HP enemy loses if player answers correctly
		int missHP = getMissHP();                          // HP player loses if they answer incorrectly
		int addXP = getAddXP();                            // XP player earns for defeating an enemy
      int count = 0;                                         // Count of player/monster turns throughout fight
		String enemy = getEnemyName();
		String describe = getEnemyDescribe();

		// Change hitHP/missHP to reflect items that are equipped
		if (player.getItem(0) == true)
			missHP -= 1;
		if (player.getItem(1) == true)
			hitHP += 1;
		if (player.getItem(2) == true)
			missHP -= 1;

		// Print the description of the enemy
		System.out.printf("%n%s%n%s%nHP: %d%n", enemy, describe, hp);

		while (playerHP > 0 && hp > 0){                 // While the player and monster have more than 0 HP
			questionType = randomOperation();            /* Random number between 1 and 3 that is passed into attack or 
                                                         defense sequence methods' rt parameter, thereby determining which 
                                                         operation the question will be. */
         count++;                     
         if (count % 2 != 0)
				// Attack sequence executes
				hp = attackSequence(questionType, d, hp, playerHP, hitHP, enemy, player);
			else
				// Defense sequence executes
				playerHP = defenseSequence(questionType, d, hp, playerHP, missHP, enemy, player);
		}

		if (playerHP <= 0){
			// Executes if player loses all their HP
			System.out.printf("%n Uh oh. You are knocked out...%n");
			System.out.printf("%n...%n");
			System.out.printf("%nMath Wiz: Wake up %s. I have brought you back a bit and restored your HP%n",
					            player.getName());
			System.out.printf("%nYou get up and dust yourself off...%n");
			restoreHP(player);                        // Restore player HP
			executeRoomAfterKO(rt, d, player);        // Move player to beginning of current level
		}       
      else{
			// Executes if enemy loses all their HP
			System.out.println("\nYou defeat the Dragon! Well done!!!");
			System.out.println("You get " + addXP + "XP!");
			player.setXP(player.getXP() + addXP);     // Add XP to player's total
			player.setCurrentHP(playerHP);            // Player's current HP set to player object
		}
	}

   // Attack sequence for fight method. Executes on odd-numbered turns
	public int attackSequence(int rt, int d, int hp, int playerHP, int hitHP, String enemy, Player player){
		int x = randomX(rt, d);                      // Randomize first digit of question
		int y = randomY(rt, d);                      // Randomize second digit of question
		int question = 0;                            // Holds question
      int answer = -1;                             // Holds player's input answer
		String operation = "";

		// Switch statement to determine which operation executes and print the correct symbol
		switch (rt){
		   case 1:                 // Level 1
			   question = x + y;
            operation = " + ";
			   break;
		   case 2:                 // Level 2
			   question = x - y;
            operation = " - ";
			   break;
		   case 3:                 // Level 3
			   question = x * y;
            operation = " x ";
			   break;
		}
		
      System.out.printf("%nTo attack the %s answer the following question: %d%s%d = ?%n", enemy, x, operation, y);
		System.out.print("Enter your answer: ");
		while (!kb.hasNextInt()){                 // While the input is not an integer
			System.out.println("\nHey, that's not a number! Please try again!");
         System.out.printf("%nTo attack the %s answer the following question: %d%s%d = ?%n", enemy, x, operation, y);
         System.out.print("Enter your answer: ");
			kb.next(); 
		}		
      answer = kb.nextInt();
      player.setQuestAns(player.getQuestAns() + 1);      // Increase player's questions answered count
		setQuestAns(getQuestAns() + 1);                    // Increase room's questions answered count
		if (answer == question){                           // If answer is correct...
			player.setQuestCorr(player.getQuestCorr() + 1); // Increase player's questions correct count
			setQuestCorr(getQuestCorr() + 1);               // Increase room's questions correct count
			hp -= hitHP;                                    // Decrease the enemy's HP by amount of damage player deals
			System.out.printf("%nNice! You strike the %s and do %d damage! The %s now has %d HP.%n", 
                           enemy, hitHP, enemy, hp);
		} 
      else{                                              // If the player does not answer correctly...
			System.out.printf("%nOh no! You miss!");
			System.out.printf("%n%d%s%d = %d%n", x, operation, y, question);  // Print the correct answer
		}
		return hp;
	}
	
   // Defense sequence for fight method. Executes on even-numbered turns
	public int defenseSequence(int rt, int d, int hp, int playerHP, int missHP, String enemy, Player player){
		int x = randomX(rt, d);                      // Randomize first digit of question
		int y = randomY(rt, d);                      // Randomize second digit of question
		int question = 0;                            // Holds question
      int answer = -1;                             // Holds player's input answer
		String operation = "";

		// Switch statement to determine which operation executes and print the correct symbol
		switch (rt){
		   case 1:                 // Level 1
			   question = x + y;
            operation = " + ";
			   break;
		   case 2:                 // Level 2
			   question = x - y;
            operation = " - ";
			   break;
		   case 3:                 // Level 3
			   question = x * y;
            operation = " x ";
			   break;
		}

		System.out.printf("%nThe %s attacks! Answer the following question to block its attack: %d%s%d = ?%n", 
                         enemy, x,operation, y);
		System.out.print("Enter your answer: ");      
		while (!kb.hasNextInt()){                 // While the input is not an integer
			System.out.println("\nHey, that's not a number! Please try again!");
         System.out.printf("%nThe %s attacks! Answer the following question to block its attack: %d%s%d = ?%n", 
                            enemy, x,operation, y);
         System.out.print("Enter your answer: ");
		   kb.next(); 
		}
		answer = kb.nextInt();		
      player.setQuestAns(player.getQuestAns() + 1);         // Increase player's questions answered count
		setQuestAns(getQuestAns() + 1);                       // Increase room's questions answered count
		if (answer == question){                              // If answer is correct...
			player.setQuestCorr(player.getQuestCorr() + 1);    // Increase player's questions correct count
			setQuestCorr(getQuestCorr() + 1);                  // Increase room's questions correct count
			System.out.println("\nPhew! You blocked the attack and did not lose any HP.");
		} 
      else{
			playerHP -= missHP;                                // Decrease player's HP by amount of damage enemy deals
			System.out.printf("%nOuch! You didn't block the attack. You lose %d HP.%n", missHP); //
			System.out.printf("%d%s%d = %d%n", x, operation, y, question);
		}
		return playerHP;
	}

	/* Generates and returns a pseudorandom number between 1 and 3. Number that
	 is returned is passed as the rt (room type) argument to the
	 attackSequence or defenseSequence method, determining the question's
	 artihmetic operation */
	public int randomOperation() {
		int action = random.nextInt(3) + 1;
		return action;
	}

	// Generates and returns a pseudorandom number assigned as the 
   // x variable of question in a fight
	public int randomX(int rt, int d) {
		int x = 0;           // Holds generated number
		int a = 0, b = 0;    // Holds upper, lower bounds for x
		if (rt == 1){               			
         if (d == 1){         // Level 1, Room 1 (easy addition)
				a = 101;
				b = 1;
			}
			if (d == 2){         // Level 1, Room 2 (hard addition)
				a = 401;
				b = 100;
			}
		}
		if (rt == 2){          
			if (d == 1){         // Level 2, Room 1 (easy subtraction)
				a = 51;
				b = 50;
			}
			if (d == 2){         // Level 2, Room 2 (hard subtraction)
				a = 401;
				b = 100;
			}
		}
		if (rt == 3){           
			if (d == 1){         // Level 3, Room 1 (easy multiplication)
				a = 13;
				b = 1;
			}
			if (d == 2){         // Level 3, Room 2 (hard multiplication)
				a = 30;
				b = 1;
			}
		}
		x = random.nextInt(a) + b;
		return x;            // Return x to fight
	}

	// Generates and returns a pseudorandom number assigned as the 
   // y variable of a question in a fight
	public int randomY(int rt, int d){
		int y = 0;           // Holds generated number
		int a = 0, b = 0;    // Holds upper, lower bounds for y
		if (rt == 1){
			if (d == 1){         // Level 1, Room 1 (easy addition)
				a = 101;
				b = 1;
			}
			if (d == 2){         // Level 1, Room 2 (hard addition)
				a = 401;
				b = 100;            
			}
		}
		if (rt == 2){           
			if (d == 1){         // Level 2, Room 1 (easy subtraction)
				a = 51;
				b = 1;
			}
			if (d == 2){         // Level 2, Room 2 (hard subtraction)
				a = 101;
				b = 1;
			}
		}
		if (rt == 3){
			if (d == 1){         // Level 3, Room 1 (easy multiplication)
				a = 13;
				b = 1;
			}
			if (d == 2){         // Level 3, Room 2 (hard multiplication)
				a = 31;
				b = 1;
			}
		}
		y = random.nextInt(a) + b;
		return y;            // Return y to fight
	}

	// Add fights to a room if player's correct less than 60% of the time in that room
	public void addFights(int rt, int d, Player player){
		if ((double) getQuestCorr() / getQuestAns() < 0.6){
			chooseOption(player);
			fight(rt, d, player);
			chooseOption(player);
			fight(rt, d, player);
			// Add an additional fight if player's correct rate is still below %60
			if ((double) getQuestCorr() / getQuestAns() < 0.6){				           
            chooseOption(player);
				fight(rt, d, player);
			}
		}
	}

	// Give player item if they answer all questions in the hard room correctly	
   public void giveItem(int rt, Player player){
		if (getQuestAns() == getQuestCorr()){ // If a player correctly answers all questions in hard room
         player.setItem(rt - 1); // Equip item
         System.out.println("\nMath Wiz: Wow! You are doing so well! You have earned an item." +
		                      "\n\t  Check your inventory to see what I have given you.");					   
      }
	}

	// Restore player's HP at the end of a d = 1 ("easy") room if their HP is at less than 50%
	public void restoreHP(Player player) {
		if (player.getCurrentHP() < player.getMaxHP() / 2){
			System.out.println("Math Wiz: I see that you are in poor health. \n\t  I will restore your HP for you.");
			player.setCurrentHP(player.getMaxHP()); // Restores player's HP
		}
	}

	// Change player's max HP at the end of a level (only in roomType = 2)
	public void changeMaxHP(int rt, Player player){
		if (getRoomType() == 1)
			player.setMaxHP(25); 
		if (getRoomType() == 2)
			player.setMaxHP(30); 
		if (getRoomType() == 3)
			player.setMaxHP(50);
		System.out.printf("%nCongratulations, %s! You have reached the end of Level %d! Your maximum HP has" +
						      "%nbeen increased to %d and you have been restored to full health.%n",
				             player.getName(), rt, player.getMaxHP());
		player.setCurrentHP(player.getMaxHP()); // Restore player's currentHP to maxHP
	}
   
   // Prints game title
   public void printTitle(){
      System.out.println("                        _                           _   _      "); 
      System.out.println("  /\\/\\   ___  _ __  ___| |_ ___ _ __    /\\/\\   __ _| |_| |__   ");
      System.out.println(" /    \\ / _ \\| '_ \\/ __| __/ _ \\ '__|  /    \\ / _` | __| '_ \\  ");
      System.out.println("/ /\\/\\ \\ (_) | | | \\__ \\ ||  __/ |    / /\\/\\ \\ (_| | |_| | | | ");
      System.out.println("\\/    \\/\\___/|_| |_|___/\\__\\___|_|    \\/    \\/\\__,_|\\__|_| |_| ");
   }
   
   // Prints dragon
   public void printDragon(){
      System.out.println("         ^                       ^ ");
      System.out.println("         |\\   \\        /        /| ");
      System.out.println("        /  \\  |\\__  __/|       /  \\ ");
      System.out.println("       / /\\ \\ \\ _ \\/ _ /      /  \\ \\ ");
      System.out.println("      / / /\\ \\ {*}\\/{*}      /  / \\ \\ ");
      System.out.println("      | | | \\ \\( (00) )     /  // |\\ \\ ");
      System.out.println("      | | | |\\ \\(V\"\"V)\\    /  / | || \\| "); 
      System.out.println("      | | | | \\ |^--^| \\  /  / || || || "); 
      System.out.println("     / / /  | |( WWWW__ \\/  /| || || || ");
      System.out.println("    | | | | | |  \\______\\  / / || || || "); 
      System.out.println("    | | | / | | )|______\\ ) | / | || || ");
      System.out.println("    / / /  / /  /______/   /| \\ \\ || || ");
      System.out.println("   / / /  / /  /\\_____/  |/ /__\\ \\ \\ \\ \\ ");
      System.out.println("   | | | / /  /\\______/    \\   \\__| \\ \\ \\ ");
      System.out.println("   | | | | | |\\______ __    \\_    \\__|_| \\ ");
      System.out.println("   | | ,___ /\\______ _  _     \\_       \\  | ");
      System.out.println("   | |/    /\\_____  /    \\      \\__     \\ |    /\\ ");
      System.out.println("   |/ |   |\\______ |      |        \\___  \\ |__/  \\ ");
      System.out.println("   v  |   |\\______ |      |            \\___/     | ");
      System.out.println("      |   |\\______ |      |                    __/ ");
      System.out.println("       \\   \\________\\_    _\\               ____/ ");
      System.out.println("     __/   /\\_____ __/  /  )\\__,      _____/ ");
      System.out.println("    /  ___/  \\uuuu/ ___/___)    \\______/ ");
      System.out.println("    VVV  V        VVV  V  ");
   }
   
   // Prints wizard
   public void printWizard(){   
      System.out.println("                              '             .           . ");
      System.out.println("                     o       '   o  .     '   . O ");
      System.out.println("                  '   .   ' .   _____  '    .      . ");
      System.out.println("                   .     .   .mMMMMMMMm.  '  o  '   . ");
      System.out.println("                 '   .     .MMXXXXXXXXXMM.    .   '  ");
      System.out.println("                .       . /XX77:::::::77XX\\ .   .   . ");
      System.out.println("                   o  .  ;X7:::''''''':::7X;   .  ' ");
      System.out.println("                  '    . |::'.:'        '::| .   .  . ");
      System.out.println("                     .   ;:.:.            :;. o   . ");
      System.out.println("                  '     . \\'.:            /.    '   . ");
      System.out.println("                     .     `.':.        .'.  '    . ");
      System.out.println("                   '   . '  .`-._____.-'   .  . '  . ");
      System.out.println("                    ' o   '  .   O   .   '  o    ' ");
      System.out.println("                     . ' .  ' . '  ' O   . '  '   ' ");
      System.out.println("                      . .   '    '  .  '   . '  ' ");
      System.out.println("                       . .'..' . ' ' . . '.  . ' ");
      System.out.println("                        `.':.'        ':'.'.' ");
      System.out.println("                          `\\\\_  |     _//' ");
      System.out.println("                            \\(  |\\    )/ ");
      System.out.println("                            //\\ |_\\  /\\\\ ");
      System.out.println("                           (/ /\\(\" )/\\ \\) ");
      System.out.println("                            \\/\\ (  ) /\\/ ");
      System.out.println("                               |(  )| ");
      System.out.println("                               | \\( \\ ");
      System.out.println("                               |  )  \\ ");
      System.out.println("                               |      \\ ");
      System.out.println("                               |       \\ ");
      System.out.println("                               |        `.__, ");
      System.out.println("                               \\_________.-' ");
   }
}

/* To complete this game, the authors referenced the following sources:
   The layout of the game was vaguely inspired by a tutorial explaining how to write a text-based adventure game:
               http://www.javacoffeebreak.com/text-adventure/index.html
   Object-oriented programming concepts:
      Classes: http://docs.oracle.com/javase/tutorial/java/concepts/class.html
               http://docs.oracle.com/javase/tutorial/java/javaOO/classes.html
      Objects: http://docs.oracle.com/javase/tutorial/java/concepts/object.html
               http://docs.oracle.com/javase/tutorial/java/javaOO/objects.html
   Swtich statements: http://docs.oracle.com/javase/tutorial/java/nutsandbolts/switch.html
   Try blocks: http://docs.oracle.com/javase/tutorial/essential/exceptions/try.html
               http://stackoverflow.com/questions/18119211/how-to-check-if-user-input-is-not-an-int-value
   Catch blocks: http://docs.oracle.com/javase/tutorial/essential/exceptions/catch.html
   ASCII Art:
      Dragon: http://chris.com/ascii/index.php?art=creatures/dragons (adapted for Java output by Jessica)
      Text:   http://patorjk.com/software/taag/#p=display&f=Ogre&t=Monster%20Math (adapted for Java output by Jessica)
      Wizard: http://www.retrojunkie.com/asciiart/myth/wizards.htm (adapted for Java output by Jessica)
*/
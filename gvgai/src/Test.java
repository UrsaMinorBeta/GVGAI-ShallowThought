import core.ArcadeMachine;

import shallowThought.Secretary;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Diego
 * Date: 04/10/13
 * Time: 16:29
 * This is a Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class Test
{

    public static void main(String[] args)
    {
        //Available controllers:
        String sampleRandomController = "controllers.sampleRandom.Agent";
        String sampleOneStepController = "controllers.sampleonesteplookahead.Agent";
        String sampleMCTSController = "controllers.sampleMCTS.Agent";
        String sampleOLMCTSController = "controllers.sampleOLMCTS.Agent";
        String sampleGAController = "controllers.sampleGA.Agent";
        
        String YBCriber = "YBCriber.Agent";
        String YOLOBOT = "YOLOBOT.Agent";
        String TUDarmstadtTeam2 = "TUDarmstadtTeam2.Agent";
        String thorbjrn = "thorbjrn.Agent";
        String SJA862 = "SJA862.Agent";
        String Return42= "Return42.Agent";
        String psuko = "psuko.Agent";
        String NovTea = "NovTea.Agent";
        String MH2015= "MH2015.Agent";
        String alxio = "alxio.Agent";
        
        String shallowThought = "shallowThought.Agent";
        
        String tester = "controllers.Tester.Agent";

        //Available games:
        String gamesPath = "examples/gridphysics/";
        String games[] = new String[]{};
        String allGames[] = new String[]{};

        //Training Set 1 (2015; CIG 2014)
        games = new String[]{"aliens", "boulderdash", "butterflies", "chase", "frogs",
                "missilecommand", "portals", "sokoban", "survivezombies", "zelda"};

        //Training Set 2 (2015; Validation CIG 2014)
        //games = new String[]{"camelRace", "digdug", "firestorms", "infection", "firecaster",
        //      "overload", "pacman", "seaquest", "whackamole", "eggomania"};

        //Training Set 3 (2015)
        //games = new String[]{"bait", "boloadventures", "brainman", "chipschallenge",  "modality",
        //                              "painter", "realportals", "realsokoban", "thecitadel", "zenpuzzle"};

        //Training Set 4 (Validation GECCO 2015, Test CIG 2014)
        //games = new String[]{"roguelike", "surround", "catapults", "plants", "plaqueattack",
        //      "jaws", "labyrinth", "boulderchase", "escape", "lemmings"};

        //Training Set 5 (Validation CIG 2015, Test GECCO 2015)
        //games = new String[]{ "solarfox", "defender", "enemycitadel", "crossfire", "lasers",
          //                             "sheriff", "chopper", "superman", "waitforbreakfast", "cakybaky"};

        //Training Set 6 (Validation CEEC 2015)
        //games = new String[]{"lasers2", "hungrybirds" ,"cookmepasta", "factorymanager", "racebet2",
        //        "intersection", "blacksmoke", "iceandfire", "gymkhana", "tercio"};

        allGames = new String[]{"aliens", "boulderdash", "butterflies", "chase", "frogs",
                /*5*/"missilecommand", "portals", "sokoban", "survivezombies", "zelda",
                /*10*/"camelRace", "digdug", "firestorms", "infection", "firecaster",
                "overload", "pacman", "seaquest", "whackamole", "eggomania",
        		/*20*/"bait", "boloadventures", "brainman", "chipschallenge",  "modality",
        		"painter", "realportals", "realsokoban", "thecitadel", "zenpuzzle",
        		/*30*/"roguelike", "surround", "catapults", "plants", "plaqueattack",
        		"jaws", "labyrinth", "boulderchase", "escape", "lemmings",
        		/*40*/"solarfox", "defender", "enemycitadel", "crossfire", "lasers",
        		"sheriff", "chopper", "superman", "waitforbreakfast", "cakybaky",
        		/*50*/"lasers2", "hungrybirds" ,"cookmepasta", "factorymanager", "racebet2",
                "intersection", "blacksmoke", "iceandfire", "gymkhana", "tercio"};

        //Other settings
        boolean visuals = true;
        String recordActionsFile = null; //where to record the actions executed. null if not to save.
        int seed = new Random().nextInt();

        //Game and level to play
        games = allGames;  // if you don't distinguish between sets
        int gameIdx = 44;
        int levelIdx = 0; //level names from 0 to 4 (game_lvlN.txt).
        String game = gamesPath + games[gameIdx] + ".txt";
        String level1 = gamesPath + games[gameIdx] + "_lvl" + levelIdx +".txt";
		
        // if mode == 3 (replay)
        String readActionsFile = "actionsFile_aliens_lvl0.txt";  // Path to file to be replayed
        
        // This plays N games, in the first L levels, M times each.
        int N = 10, L = 5, M = 1;
        
        // mode 5 and 6:
        boolean saveActions = true;
        String[] levels = new String[L];  // if multiple levels are being played
        String[] actionFiles = new String[L*M];
    	
        
        int mode = 4;
        
        switch (mode) {
        case 1:
    		// 1. This starts a game, in a level, played by a human.
            ArcadeMachine.playOneGame(game, level1, recordActionsFile, seed);
    		break;
        case 2:
        	// 2. This plays a game in a level by the controller.
            ArcadeMachine.runOneGame(game, level1, visuals, shallowThought, recordActionsFile, seed);
            break;
        case 3:
        	// 3. This replays a game from an action file previously recorded
            ArcadeMachine.replayGame(game, level1, visuals, readActionsFile);
        	break;
        case 4:
        	// 4. This plays a single game, in L levels, M times :
    		levels = new String[L];
        	for(int j = 0; j < L; ++j){
        		levels[j] = gamesPath + games[gameIdx] + "_lvl" + j +".txt";
        	}
            ArcadeMachine.runGames(game, levels, M, psuko, null);
    		break;
        case 5:
        	//5. This plays N games, in the first L levels, M times each. Actions to file optional (set saveActions to true).
            for(int i = 0; i < N; ++i)
            {
                int actionIdx = 0;
                game = gamesPath + games[i] + ".txt";
                for(int j = 0; j < L; ++j){
                    levels[j] = gamesPath + games[i] + "_lvl" + j +".txt";
                    if(saveActions) for(int k = 0; k < M; ++k)
                        actionFiles[actionIdx++] = "actions_game_" + i + "_level_" + j + "_" + k + ".txt";
                }
                ArcadeMachine.runGames(game, levels, M, sampleMCTSController, saveActions? actionFiles:null);
            }
    		break;
        case 6:
        	//6. This plays all bots in N games, in the first L levels, M times each. Actions to file optional (set saveActions to true).
            String[] bots = new String[]{
            		shallowThought, YOLOBOT, YBCriber, TUDarmstadtTeam2, thorbjrn, SJA862,
            		Return42, psuko, NovTea, MH2015, alxio};
            for (int b = 0; b < bots.length; b++) {
            	for(int i = 0; i < N; ++i)
            	{
            		int actionIdx = 0;
            		game = gamesPath + games[i] + ".txt";
            		for(int j = 0; j < L; ++j){
            			levels[j] = gamesPath + games[i] + "_lvl" + j +".txt";
            			if(saveActions) for(int k = 0; k < M; ++k)
            				actionFiles[actionIdx++] = "records/" + bots[b].substring(0, bots[b].length()-6) + "_game_" + i + "_level_" + j + "_" + k + ".txt";
                	}
            		//for(int j = 0; j < L; ++j){
                    //  ArcadeMachine.playOneGame(game, levels[j], recordActionsFile, seed);
            		//}
            		//ArcadeMachine.runGames(game, levels, M, bots[b], saveActions? actionFiles:null);
            		ArcadeMachine.runGames(game, levels, M, bots[b], saveActions? actionFiles:null);
                }
            }
            break;
        case 7:
        	//7. Exercise! Executes game as often as is stated in the "exercises"
            // Set up exercise-session
            Secretary secretary = new Secretary();
            File file = new File("./src/shallowThought/learning/exercises.txt");  // exercises to do
            String[] actionLog;
            String[] ex = secretary.readExercise(file);
            game = gamesPath + ex[1] + ".txt";
            levelIdx = Integer.parseInt(ex[2]);
            String level = gamesPath + ex[1] + "_lvl" + levelIdx + ".txt";
            int timesToPlay = Integer.parseInt(ex[3].split("/",0)[0]);
            while (timesToPlay > 0) {
            	actionLog = new String[] {"./src/shallowThought/learning/record_"+timesToPlay+".txt"};
            	ArcadeMachine.runGames(game, new String[] {level}, 1, shallowThought, actionLog);
            	timesToPlay --;
            }
            secretary.solve(new File("./src/shallowThought/learning/learning.txt"), new File("./src/shallowThought/learning/solutions.txt"));
            //secretary.deleteFirstLine(file);  // enable to delete exercise after execution (and have best setting in solution
            break;
    	}
        
        
    }
}

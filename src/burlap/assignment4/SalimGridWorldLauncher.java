package burlap.assignment4;

import burlap.assignment4.util.AnalysisAggregator;
import burlap.assignment4.util.AnalysisRunner;
import burlap.assignment4.util.BasicRewardFunction;
import burlap.assignment4.util.BasicTerminalFunction;
import burlap.assignment4.util.MapPrinter;
import burlap.domain.singleagent.gridworld.GridWorldDomain;
import burlap.domain.singleagent.gridworld.GridWorldTerminalFunction;
import burlap.domain.singleagent.gridworld.SalimGridWorldDomain;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.RewardFunction;
import burlap.oomdp.singleagent.common.GoalBasedRF;
import burlap.oomdp.singleagent.environment.SimulatedEnvironment;
import burlap.oomdp.singleagent.explorer.VisualExplorer;
import burlap.oomdp.statehashing.HashableStateFactory;
import burlap.oomdp.statehashing.SimpleHashableStateFactory;
import burlap.oomdp.visualizer.Visualizer;
import burlap.oomdp.auxiliary.stateconditiontest.StateConditionTest;
import burlap.oomdp.auxiliary.stateconditiontest.TFGoalCondition;

public class SalimGridWorldLauncher {
	//These are some boolean variables that affect what will actually get executed
	private static boolean visualizeInitialGridWorld = true; //Loads a GUI with the agent, walls, and goal
	
	//runValueIteration, runPolicyIteration, and runQLearning indicate which algorithms will run in the experiment
	private static boolean runValueIteration = true; 
	private static boolean runPolicyIteration = true;
	private static boolean runQLearning = true;
	
	//showValueIterationPolicyMap, showPolicyIterationPolicyMap, and showQLearningPolicyMap will open a GUI
	//you can use to visualize the policy maps. Consider only having one variable set to true at a time
	//since the pop-up window does not indicate what algorithm was used to generate the map.
	private static boolean showValueIterationPolicyMap = true; 
	private static boolean showPolicyIterationPolicyMap = false;
	private static boolean showQLearningPolicyMap = false;
	private static boolean isSmallCase = true;
	
	private static Integer MAX_ITERATIONS = 100;
	private static Integer NUM_INTERVALS = 100;

	protected static int[][] smallCase = new int[][] { 
		{ 0, 0, 0, 0 },
		{ 0, 0, 0, 0 },
		{ 0, 1, 0, 0 },
		{ 0, 0, 0, 0 }, };

	// TOOD add large case
	protected static int[][] largeCase = new int[][] { 
		{ 0, 0, 0, 0 },
		{ 0, 0, 0, 0 },
		{ 0, 1, 0, 0 },
		{ 0, 0, 0, 0 }, };
		
			
//	private static Integer mapLen = map.length-1;

	public static void main(String[] args) {
		// convert to BURLAP indexing
		int[][] mdpMap = isSmallCase ? smallCase :largeCase; 
		int[][] map = MapPrinter.mapToMatrix(mdpMap);
		int maxX = map.length-1;
		int maxY = map[0].length-1;	
				
//		int dimension = SalimGridWorldDomain.getDimension(isSmallCase);
//		int WIDTH = dimension;
//		int HEIGHT = dimension;
//		GridWorldDomain gwdg = new SalimGridWorldDomain(WIDTH, HEIGHT);
//		
//		if(isSmallCase) {
//			((SalimGridWorldDomain)gwdg).setSmallCase();
//		} else {
//			((SalimGridWorldDomain)gwdg).setLargeCase();			
//		}
//		
//		TerminalFunction tf = new GridWorldTerminalFunction(WIDTH - 1, HEIGHT - 1);
//		//gwdg.setTf(tf);
//		StateConditionTest goalCondition = new TFGoalCondition(tf);
//		RewardFunction rf = new GoalBasedRF(goalCondition, 5.0, -.1); //Goal is at the top right grid
//		Domain domain = gwdg.generateDomain();
//
//		State initialState = new GridWorldState(new GridAgent(0, 0), new GridLocation(WIDTH - 1, HEIGHT - 1, "loc0"));
//		HashableStateFactory hashingFactory = new SimpleHashableStateFactory();
//
//		//SimulatedEnvironment env = new SimulatedEnvironment(domain, initialState);
//		SimulatedEnvironment env = new SimulatedEnvironment(domain, rf, tf, initialState);


		BasicGridWorld gen = new BasicGridWorld(map,maxX,maxY);
		Domain domain = gen.generateDomain();

		State initialState = BasicGridWorld.getExampleState(domain);

		TerminalFunction tf = new BasicTerminalFunction(maxX,maxY); //Goal is at the top right grid
		StateConditionTest goalCondition = new TFGoalCondition(tf);
		RewardFunction rf = new GoalBasedRF(goalCondition, 5.0, -.1); //Goal is at the top right grid
		//RewardFunction rf = new BasicRewardFunction(maxX,maxY); //Goal is at the top right grid

		SimulatedEnvironment env = new SimulatedEnvironment(domain, rf, tf, initialState);
		//Print the map that is being analyzed
		System.out.println("/////Easy Grid World Analysis/////\n");
		MapPrinter.printMap(MapPrinter.matrixToMap(map));
		
		if (visualizeInitialGridWorld) {
			visualizeInitialGridWorld(domain, gen, env);
		}
		
		AnalysisRunner runner = new AnalysisRunner(MAX_ITERATIONS,NUM_INTERVALS);
		if(runValueIteration){
			runner.runValueIteration(gen,domain,initialState, rf, tf, showValueIterationPolicyMap);
		}
		if(runPolicyIteration){
			runner.runPolicyIteration(gen,domain,initialState, rf, tf, showPolicyIterationPolicyMap);
		}
		if(runQLearning){
			runner.runQLearning(gen,domain,initialState, rf, tf, env, showQLearningPolicyMap);
		}
		AnalysisAggregator.printAggregateAnalysis();
	}



	private static void visualizeInitialGridWorld(Domain domain,
			BasicGridWorld gen, SimulatedEnvironment env) {
		Visualizer v = gen.getVisualizer();
		VisualExplorer exp = new VisualExplorer(domain, env, v);

		exp.addKeyAction("w", BasicGridWorld.ACTIONNORTH);
		exp.addKeyAction("s", BasicGridWorld.ACTIONSOUTH);
		exp.addKeyAction("d", BasicGridWorld.ACTIONEAST);
		exp.addKeyAction("a", BasicGridWorld.ACTIONWEST);

		exp.initGUI();

	}
	

}

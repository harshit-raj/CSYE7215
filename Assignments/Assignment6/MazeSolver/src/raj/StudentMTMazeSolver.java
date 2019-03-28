package raj;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class StudentMTMazeSolver extends SkippingMazeSolver{
    List<Direction> solution;
    private ForkJoinPool forkJoinPool;
    RecursiveAction task;
    volatile int choiceCount;

    public StudentMTMazeSolver(Maze maze)
    {
        super(maze);
    }

    @SuppressWarnings("serial")
    private class DFSTask extends RecursiveAction {

        Choice rootChoice;
        Direction comingFrom;

        public DFSTask(Choice rootChoice, Direction comingFrom) {
            this.rootChoice = rootChoice;
            this.comingFrom = comingFrom;
        }


        public void compute () {
            LinkedList<Choice> choiceStack = new LinkedList<Choice>();

            Choice currentChoice;
            try{
                choiceStack.push(this.rootChoice);
                while(!choiceStack.isEmpty()){
                    currentChoice = choiceStack.peek();
                    if(currentChoice.isDeadend()){
                        choiceStack.pop();
                        if(!choiceStack.isEmpty()) choiceStack.peek().choices.pop();
                        continue;

                    }
                    choiceCount++;
                    choiceStack.push(follow(currentChoice.at, currentChoice.choices.peek()));
                }

            }catch(SolutionFound s){
                Iterator<Choice> iter = choiceStack.iterator();
                LinkedList<Direction> solutionPath = new LinkedList<Direction>();
                while (iter.hasNext()){
                    currentChoice = iter.next();
                    solutionPath.push(currentChoice.choices.peek());
                }
                solutionPath.push(comingFrom);
                solution = solutionPath;
            }
        }


    }



    @SuppressWarnings("unchecked")
    public List<Direction> solve()
    {
        int size =0;
        forkJoinPool = new ForkJoinPool();
        try{
            Choice begin = firstChoice(maze.getStart());
            size = begin.choices.size();
            for(int i = 0; i< size; i++){
                Choice curr = follow(begin.at, begin.choices.peek());
                task = new DFSTask(curr,begin.choices.pop());
                forkJoinPool.execute(task);
                task.join();
            }


        }catch(SolutionFound s){



        }
        System.out.println("Choice count : "+ (choiceCount+size));
        forkJoinPool.shutdown();
//        markPath(solution,1);
        if(maze.display != null) maze.display.updateDisplay();
        if(solution != null){
            return pathToFullPath(solution);
        }
        else{
            return  null;
        }


    }

}

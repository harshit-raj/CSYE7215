package raj;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class StudentMTMazeSolver extends SkippingMazeSolver{
    List<Direction> solution;
    private ForkJoinPool forkJoinPool;
    RecursiveAction task;
    volatile int choiceCount;
    Boolean large;
    Boolean showChoiceCount = true;

    public StudentMTMazeSolver(Maze maze)
    {
        super(maze);
        large = maze.getHeight()*maze.getWidth() > 999999;
        //System.out.println("Height: "+ maze.getHeight()+" Width: "+maze.getWidth());
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
        List<RecursiveAction> DFSTaskList = new ArrayList<>();
        int size =0;
        forkJoinPool = new ForkJoinPool();
        try{
            Choice begin = firstChoice(maze.getStart());
            size = begin.choices.size();
            if(large){
                for(int i = 0; i< size; i++){
                    Choice curr = follow(begin.at, begin.choices.peek());
                    task = new DFSTask(curr,begin.choices.pop());
                    DFSTaskList.add(task);
                    forkJoinPool.execute(task);
                    //task.join();
                }
                DFSTaskList.forEach(RecursiveAction::join);
                forkJoinPool.shutdown();
            }
            else{
                for(int i = 0; i< size; i++){
                    Choice curr = follow(begin.at, begin.choices.peek());
                    task = new DFSTask(curr,begin.choices.pop());
                    DFSTaskList.add(task);
                    forkJoinPool.execute(task);
                    task.join();
                }
                forkJoinPool.shutdown();

            }

        }catch(SolutionFound s){



        }
        if(showChoiceCount){
            System.out.println("Choice count : "+ (choiceCount+size));
        }


        if(solution != null){

            if(maze.display != null){
                maze.display.updateDisplay();
                markPath(solution,1);
            }
            return pathToFullPath(solution);
        }
        else{
            return  null;
        }


    }

}

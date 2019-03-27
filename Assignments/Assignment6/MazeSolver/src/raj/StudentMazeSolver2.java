package raj;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

/**
 * This file needs to hold your solver to be tested.
 * You can alter the class to extend any class that extends MazeSolver.
 * It must have a constructor that takes in a Maze.
 * It must have the solve() method that returns the datatype List<Direction>
 *   which will either be a reference to a list of steps to take or will
 *   be null if the maze cannot be solved.
 */
public class StudentMazeSolver2 extends SkippingMazeSolver
{
    public StudentMazeSolver2(Maze maze)
    {
        super(maze);
    }
    static ForkJoinPool pool = new ForkJoinPool();




    public List<Direction> solve()
    {




        // TODO: Implement your code here

        Choice start = new Choice(
                maze.getStart(),
                null,
                maze.getMoves(
                        maze.getStart()
                )
        );
        List<Direction> result = pool.invoke(new DFSSolver(start));
        pool.shutdown();
        if (maze.display != null) maze.display.updateDisplay();
//        System.out.println("Size "+ result.size());
        //result.forEach(direction -> System.out.print(direction+" || "));
        return pathToFullPath(result);



        // throw new RuntimeException("Not yet implemented!");
    }


    class DFSSolver extends RecursiveTask<List<Direction>> {
        Choice ch;

        DFSSolver(Choice ch){

            this.ch = ch;
//            System.out.println("Visiting :"+ ch.at);
        }


        @Override
        protected List<Direction> compute() {
//            if(ch.isDeadend()){
//                //System.out.println("Deadend at "+ ch.at);
//                return null;
//            }
            List<Direction> directionList = new ArrayList<>();
            if(!ch.isDeadend()){
                List<DFSSolver> subtasks = new ArrayList<>();
               for(Direction direction: ch.choices){
                   try{
                       //System.out.println("At: "+ ch.at+ "Going: "+ direction);

                       subtasks.add(new DFSSolver(follow(ch.at,direction)));
                   }catch (SolutionFound e){
                       //System.out.println("Maze end at: "+ e.pos);
                       List<Direction> result= new LinkedList<>();
                       //result.add(direction);
                       //result.add(direction);

                        //System.out.println("Sol at "+ e.pos);
                       result.add(e.from.reverse());
                       result.add(ch.from.reverse());
                       //System.out.println(e.from.reverse());
                       //System.out.println(ch.from.reverse());
                       //System.out.println("Returning last result from "+ ch.at);
                       return result;
//                       System.out.println("End from : "+ ch.from);
//                       System.out.println("Direction: "+ d);


                   }

               }

//                List<Direction> directionList = subtasks
//                        .stream()
//                        .map(dfsSolver -> dfsSolver.compute())
//                        .filter(directions -> directions != null)
//                        .findFirst().get();
//
//
//
//               directionList.add(ch.from);

//                List<Direction> directionList;

               for(DFSSolver solver : subtasks){
                   directionList= solver.compute();
                   if(directionList != null){
                       //System.out.println("From: "+ ch.from);
                       if(ch.from != null){
//                           directionList.forEach(direction -> System.out.print(direction+" || "));
//                           System.out.println();
//                           System.out.println(ch.from.reverse());


                           directionList.add(ch.from.reverse());
                       }
//                       System.out.println("return dir at "+ ch.at);
                       return directionList;
                   }

               }

//               System.out.println("retuning null after loop: "+ (directionList == null)+ " "+ ch.at);






            }
//            System.out.println("Dead end"+ ch.at);

            return null;


        }

//        private Collection<DFSSolver> createSubtask() throws  SolutionFound{
//            List<DFSSolver> subTaskList = ch.choices
//                    .stream()
//                    .map(direction -> new DFSSolver(follow(ch.at,direction)))
//                    .collect(Collectors.toList());
//            return subTaskList;
//
//        }
    }



}


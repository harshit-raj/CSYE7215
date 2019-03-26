package raj;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * This file needs to hold your solver to be tested. 
 * You can alter the class to extend any class that extends MazeSolver.
 * It must have a constructor that takes in a Maze.
 * It must have the solve() method that returns the datatype List<Direction>
 *   which will either be a reference to a list of steps to take or will
 *   be null if the maze cannot be solved.
 */
public class StudentMTMazeSolver extends SkippingMazeSolver
{
    public StudentMTMazeSolver(Maze maze)
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
        pool.invoke(new DFSSolver(start));
        pool.shutdown();
        return null;



       // throw new RuntimeException("Not yet implemented!");
    }


    class DFSSolver extends RecursiveAction{
        Choice ch;

        DFSSolver(Choice ch){

            this.ch = ch;
            System.out.println("Visiting :"+ ch.at);
        }


        @Override
        protected void compute() {
            if(ch.isDeadend()){
                //System.out.println("Deadend at "+ ch.at);
            }
            else{
                for(Direction d: ch.choices){
                try{

                        new DFSSolver(follow(ch.at,d)).compute();

                }catch (SolutionFound e){
                    System.out.println("Maze end at: "+ ch.at);
                    System.out.println("End from : "+ ch.from);
                    System.out.println("Direction: "+ d);
                    break;

                }
                }

            }


        }
    }



}

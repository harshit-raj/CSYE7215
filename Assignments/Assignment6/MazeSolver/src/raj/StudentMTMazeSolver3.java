package raj;

import java.util.LinkedList;
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
public class StudentMTMazeSolver3 extends SkippingMazeSolver
{
    public StudentMTMazeSolver3(Maze maze)
    {
        super(maze);
    }
    static ForkJoinPool pool = new ForkJoinPool();

    static List<Direction> result = null;



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
        List<Direction> result1 = new LinkedList<>();
        pool.invoke(new DFSSolver(start,result1));
        pool.shutdown();
        result.forEach(direction -> System.out.print(direction+" || "));

        System.out.println();
        return pathToFullPath(result);



       // throw new RuntimeException("Not yet implemented!");
    }


    class DFSSolver extends RecursiveAction{
        Choice ch;
        //private List<Direction> dirRes;
        List<Direction>  dirList;



        DFSSolver(Choice ch,List<Direction> resultList){

            this.ch = ch;
            //System.out.println("Visiting :"+ ch.at);
            this.dirList = resultList;

        }




        @Override
        protected void compute() {
            if(ch.isDeadend()){
                //System.out.println("Deadend at "+ ch.at);
            }
            else{
                while(!ch.choices.isEmpty()){

                    try{


                        new DFSSolver(follow(ch.at,ch.choices.pop()),dirList).compute();
                        //ch.choices.pop();

                }catch (SolutionFound e){
//                    System.out.println("Maze end at: "+ ch.at);
//                    System.out.println("End from : "+ ch.from);
//                    System.out.println("Direction: "+ comingFrom);
                    dirList.add(e.from.reverse());
                    dirList.add(ch.from.reverse());
                    break;

                }
                }

            }


        }
    }



}

/*
BBC - TECHNICAL TEST STAGE - SOFTWARE ENGINEERING GRADUATE TRAINEE SCHEME
Frances Ramirez
Game Of Life program
*/
/*
After compilation and running on the command line/terminal,
It will demand for a valid user input.
If input is incorrect, it will keep demanding until a valid one is typed.
Accepted user inputs are:
    -default (integer) or d (integer)
    -random (integer) or r (integer)
    // these are not case sensitive
    // integer is the number of iterations you would like this program to run.

If default is chosen, then the default grid below is run the game of life with your specifiend iteration input.
You can change the grid if you would like to test it for another initial grid state.

If random is chosen, then this program will generate a random initial grid,
the min and max number of rows and columns are 10 and 20 respectively.
Change these values in the createRandomState() if you want to test for a larger random grid
This random initial grid will run the game of life with your specified iteration input
*/

import java.util.*;

public class GameOfLife {

    /*
     the default initial grid - the example on the handout
     change this to test for other state
     OR
     choose random state
    */
     // '-' means cell is not alive
     // '*' means cell is alive
    public static char[][] defaultInitState = { 
        { '-', '-', '-', '-', '-' }, 
        { '-', '-', '-', '-', '-' }, 
        { '-', '*', '*', '*', '-' }, 
        { '-', '-', '-', '-', '-' }, 
        { '-', '-', '-', '-', '-' }  
    };

        public static int iter; // number of iterations the program will run
        public static int row; // number of rows
        public static int col; // number of columns
        public static char[][] initState; // initial state. either the defaul above or a random state


    public static void main(String[] args) { 

        boolean isCmdCorrect = false; // is initial state command correct
        String userInput = "";
        // capturing user input from the command line or terminal
        while ( !isCmdCorrect ) {
            //instructions
            System.out.println("\nType 'Random' or 'Default' as your initial state " +
                                "followed by the number of iterations. " +
                                "\nE.g. 'R 10'" +
                                "\nOr ctrl+C to quit." );
            Scanner scanner = new Scanner(System.in);
            userInput = scanner.nextLine().toLowerCase();
            isCmdCorrect = isUserInputValid(userInput); // this will also set the global variables
        }

        System.out.println("InitialState: ");
        printState(initState);
        char[][] currentState = initState;
        for (int i = 1; i <= iter; i++) {
            System.out.println("Next State Iteration " + i);
            printState(currentState);
            currentState = nextState(currentState) ;
        }

    }

    //creates a random state of a  2x2 grid
    static char[][] createRandomState() {
        // the min and max number of rows and columns are 10 and 20 respectively
        //change these values if you want to test for a larger grid
        int numberOfRows = (int) (Math.random() * 20 + 10);
        int numberOfCols = (int) (Math.random() * 20 + 10);

        char[][] randomState = new char[numberOfRows][numberOfCols];

        for (int row = 0; row < numberOfRows; row++)  { 
            for (int col = 0; col < numberOfCols; col++) {
                // generates a random binary, 0 or 1. this is mapped to live (*) or not live(-) cell
                int randomNumber = (int) Math.round( Math.random() );
                randomState[row][col] = randomNumber==1 ? '*' : '-';
            }
        }

        return randomState;
    }

    // checking if user input is accepted.
    static boolean isUserInputValid(String userInput) {
        String[] splitInput = userInput.split(" ");

        if (splitInput.length != 2) {
            System.out.println("Command Incorrect. Please try again.1");
            return false;
        }

        try {
            Integer.parseInt(splitInput[1]);
        } catch (NumberFormatException ex) {
            System.out.println("Command Incorrect. Please try again.2");
            return false;
        }
        if ( splitInput[0].equals("random") || splitInput[0].equals("r") ) {
            char[][] randomState = createRandomState();
            initState = randomState;
            iter = Integer.parseInt(splitInput[1]);
            row = getDimensions(randomState)[0];
            col = getDimensions(randomState)[1];
            return true;
        } else if (splitInput[0].equals("default") || splitInput[0].equals("d")) {
            initState = defaultInitState;
            iter = Integer.parseInt(splitInput[1]);
            row = getDimensions(defaultInitState)[0];
            col = getDimensions(defaultInitState)[1];
            return true;
        } else {
            System.out.println("Command Incorrect. Please try again.3");
            return false;
        }
        
    }

    static char[][] nextState(char grid[][]) { 
        char[][] nextState = new char[row][col]; 
  
        // Visits every cell in the grid
        for (int currentRow = 0; currentRow < row; currentRow++) { 
            for (int currentCol = 0; currentCol < col; currentCol++) { 
                // finding no Of Neighbours that are alive 
                int aliveNeighbours = 0; 
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        // try catch to check if the current cell we are visiting on the edge of the gird
                        try{
                            aliveNeighbours += (grid[currentRow + i][currentCol + j]=='*') ? 1 : 0;
                        } catch (Exception e) {
                            continue;
                        }
                    }
                }

                // The current cell has been counted as a neighbour so we subtract this 
                aliveNeighbours -= grid[currentRow][currentCol]=='*' ? 1 : 0; 
                char currentCell = grid[currentRow][currentCol];
  
                // Scenario 1: Underpopulation 
                if (( currentCell== '*') && (aliveNeighbours < 2)) 
                    nextState[currentRow][currentCol] = '-'; 
  
                // Scenario 2: Overcrowding 
                else if ((currentCell == '*') && (aliveNeighbours > 3)) 
                    nextState[currentRow][currentCol] = '-'; 
  
                // Scenario 4: Creation of Life 
                else if ((currentCell == '-') && (aliveNeighbours == 3)) 
                    nextState[currentRow][currentCol] = '*'; 
  
                // Scenario 0: No interactions && Scenario 3: Survival 
                else
                    nextState[currentRow][currentCol] = currentCell; 
            } 
        }
        return nextState;
    }

    // printing the state of the grid
    static void printState(char grid[][]) { 
        for (int i = 0; i < row; i++)  { 
            for (int j = 0; j < col; j++) { 
                System.out.print('|');
                System.out.print(grid[i][j]);
            }
            System.out.print('|');
            System.out.println(); 
        } 

    }

    //return the dimensions of the grid = [numberOfRows,numberOfColumns]
    static int[] getDimensions(char[][] g)  {
        return new int[]{ g.length, g[0].length };
    }
}








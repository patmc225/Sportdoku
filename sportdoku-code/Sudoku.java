import java.util.*;

public class Sudoku
{
    static int[][] board;
    public static void main( String args[] )
	{
        loadBoard();
        
        System.out.println();
        System.out.println("Welcome to Sudoku");
        System.out.println();
        int [][] dBoard = displayBoard();
        toStringg(dBoard);
       
       

        while(!checkFull(dBoard))
        {
            System.out.print("Insert number(x, y, number):");
            Scanner scan = new Scanner(System.in);

            try {
                int row = scan.nextInt();
                int c = scan.nextInt();
                int num = scan.nextInt();
                if (board[c-1][row-1]==(num))
                {
                    dBoard[c-1][row-1] = num;
                    System.out.println();
                    toStringg(dBoard);
                }
                else 
                {
                        System.out.println("Incorrect!");
                }
            } catch (Exception e) { System.out.println("Invalid response"); }
        }    

        System.out.print("Congratulations! You solved the puzzle!");
    }


    static boolean checkFull(int[][] dBoard)
    {
        for (int row=0; row<9; row++)
        {
            for (int c=0; c<9; c++) 
            {
                if (dBoard[row][c]==0) return false;
            }
        }
        return true;
    }

    static int[][] displayBoard()
    {
        int [][] dBoard = new int[9][9];
        for (int row=0; row<9; row++)
        {
            for (int c=0; c<9; c++) 
            {
                dBoard[row][c]= board[row][c];
            }
        }
        int empties = 0;
        while (empties<50)
        {
            int row = (int)(Math.random()*9)+0;
            int c = (int)(Math.random()*9)+0;
            dBoard[row][c]=0;
            empties++;
        }
        return dBoard;
    }

    static void toStringg(int[][] sBoard)
    {
        System.out.println("    1 2 3   4 5 6   7 8 9");
        for (int row=0; row<9; row++)
        {
            if(row%3==0) System.out.println("   ------------------------");
            System.out.print(row+1 +" ");
            for (int c=0; c<9; c++) 
            {
                if(c%3==0) System.out.print("| ");
                if(sBoard[row][c]==0) System.out.print("  ");
                else System.out.print(sBoard[row][c]+ " ");
            }
            System.out.println("| ");
        }
        System.out.println("   ------------------------");
        System.out.println();
    }

    static void loadBoard()
    {
        board = new int[9][9];
        for (int row=0; row<9; row++)
        {
            int sum = 0;
            for (int c=0; c<9; c++)
            {
                int count = 0;
                board[row][c] =  (int)(Math.random()*9) + 1;
                while(!checkNum(board[row][c], row, c) && count<10)
                {
                    board[row][c] =  (int)(Math.random()*9) + 1;
                    count++;
                }
                sum++;
                if(count>9) 
                {
                    c = -1;
                    count = 0;
                    clearRow(row);
                }
                if(sum>511)
                {
                    row = 0;
                    c = -1;
                    sum = 0;
                    clearTable();
                }
            }
        }   
    }

    static void clearRow(int row)
    {
        for(int i=0;i<9;i++)
        {
            board[row][i] = 0;
        }
    }

    static void clearTable()
    {
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
                board[i][j] = 0;
        }
    }

    static boolean checkNum(int num, int row, int c )
    {
        if(num <1 || num>9) return false;

        for(int i = 0; i<8; i++)
            if(board[row][i]==board[row][c] && i!=c)
                return false;

        for(int i = 0; i<8; i++)
            if(board[i][c]==board[row][c] && i!=row)
                return false;

        for(int i = 0; i<3; i++)
        {
            for(int j = 0; j<3; j++)
            {
                if( ((row + i) < 9  &&  (i+row)/3==row/3) && ((c+j) < 9 &&   (j+c)/3==c/3)) 
                    if(board[row+i][c+j]==board[row][c] && !(i==0 && j==0)) return false;
                if( ((row + i) < 9  &&  (i+row)/3==row/3) && ((c-j) > -1  &&  (c-j)/3==c/3)) 
                    if(board[row+i][c-j]==board[row][c] && !(i==0 && j==0)) return false;
                if( ((row - i) > -1  &&  (row-i)/3==row/3) && ((c+j) < 9  &&  (j+c)/3==c/3)) 
                    if(board[row-i][c+j]==board[row][c] && !(i==0 && j==0)) return false;
                if( ((row - i) > -1  &&  (row-i)/3==row/3) && ((c-j) > -1  &&  (c-j)/3==c/3)) 
                    if(board[row-i][c-j]==board[row][c] && !(i==0 && j==0)) return false;
            }
        }
        return true;
    }
}

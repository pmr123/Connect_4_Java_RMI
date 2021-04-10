// Interface for Connect 4
import java.rmi.RemoteException;

public interface BoardInterface extends java.rmi.Remote {
    // Add Client to Game
    int addClient(BoardInterface bi) throws RemoteException;
    // Server function to play a turn
    void playTurn(int player,int column) throws RemoteException;
    // Client function to display board
    void displayBoard(int[][] board, int turn, int flag) throws RemoteException;
}

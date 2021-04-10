// Connect 4 Server
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class Server extends UnicastRemoteObject implements BoardInterface {
    private ArrayList<BoardInterface> clientList;
    private int[][] board;
 
    protected Server() throws RemoteException {
        clientList = new ArrayList<BoardInterface>();
        board = new int[6][7];
    }

    public synchronized int addClient(BoardInterface boardinterface) throws RemoteException {
        if(clientList.size() < 2){
            this.clientList.add(boardinterface);
            System.out.println("Player "+(clientList.size())+" Added");
            return clientList.size();
        }
        else{
            return 0;
        }
    }

    // Adds token to board
    public synchronized int addToken(int player, int column) throws RemoteException{
        int i;
        int flag = 0;
        for(i=5;i>=0;i--){
            if(board[i][column-1] == 0){
                board[i][column-1] = player;
                flag = 1;
                break;
            }
        }
        return flag;
    }

    // Checks if someone won
    public synchronized int checkWin() throws RemoteException{
        int c=0,r=0;
        // Check horizontal locations for win
        for(c=0;c<7-3;c++){
            for(r=0;r<6;r++){
                if(board[r][c] == 1 && board[r][c+1] == 1 && board[r][c+2] == 1 && board[r][c+3] == 1){
                    return 2;
                }
                if(board[r][c] == 2 && board[r][c+1] == 2 && board[r][c+2] == 2 && board[r][c+3] == 2){
                    return 3;
                }
            }
        }
        // Check vertical locations for win
        for(c=0;c<7;c++){
            for(r=0;r<6-3;r++){
                if(board[r][c] == 1 && board[r+1][c] == 1 && board[r+2][c] == 1 && board[r+3][c] == 1){
                    return 2;
                }
                if(board[r][c] == 2 && board[r+1][c] == 2 && board[r+2][c] == 2 && board[r+3][c] == 2){
                    return 3;
                }
            }
        }
        // Check positively sloped diaganols
        for(c=0;c<7-3;c++){
            for(r=0;r<6-3;r++){
                if(board[r][c] == 1 && board[r+1][c+1] == 1 && board[r+2][c+2] == 1 && board[r+3][c+3] == 1){
                    return 2;
                }
                if(board[r][c] == 2 && board[r+1][c+1] == 2 && board[r+2][c+2] == 2 && board[r+3][c+3] == 2){
                    return 3;
                }
            }
        }
        // Check negatively sloped diaganols
        for(c=0;c<7-3;c++){
            for(r=3;r<6;r++){
                if(board[r][c] == 1 && board[r-1][c+1] == 1 && board[r-2][c+2] == 1 && board[r-3][c+3] == 1){
                    return 2;
                }
                if(board[r][c] == 2 && board[r-1][c+1] == 2 && board[r-2][c+2] == 2 && board[r-3][c+3] == 2){
                    return 3;
                }
            }
        }
        return 0;
        
    }
 
    public synchronized void playTurn(int player,int column) throws RemoteException {
        int flag = 0;
        int added = 0;
        if(!(column>0 && column<8)){
            flag = 1;
            clientList.get(player-1).displayBoard(board, player, flag);
        }
        if(flag == 0){
            added = addToken(player, column);
            if(added == 0){
                flag = 1;
                clientList.get(player-1).displayBoard(board, player, flag);
            }
            else{
                flag = checkWin();
                if(player == 1){
                    for(int i=0; i<clientList.size(); i++) {
                        clientList.get(i).displayBoard(board, 2, flag);
                    }
                }
                if(player == 2){
                    for(int i=0; i<clientList.size(); i++) {
                        clientList.get(i).displayBoard(board, 1, flag);
                    }
                }
            }
        }
    }
 
    public synchronized void displayBoard(int[][] board, int turn, int flag) throws RemoteException{}

    public static void main(String[] arg) throws RemoteException, MalformedURLException {
        System.out.println("Starting Server");
        Naming.rebind("RMIServer", new Server());
        System.out.println("Server Started");
    }
 
}
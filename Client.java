// Connect 4 Client
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.net.MalformedURLException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Client extends UnicastRemoteObject implements BoardInterface , Runnable {
    private static final long serialVersionUID = 1L;
    private BoardInterface server;
    private String ClientName;
    private static int PlayerNum = 0;
    private static int PlayerTurn = 1;
    boolean chkExit = true;
 
    protected Client(BoardInterface boardinterface,String clientname) throws RemoteException {
        this.server = boardinterface;
        this.ClientName = clientname;
        this.PlayerNum = server.addClient(this);
    }

    // Displays board to the client
    // turn descibes who will have the next turn
    // flag = 1 if an illegal number is entered 
    // flag = 2 if Player 1 wins
    // flag = 3 if Player 2 wins
    public void displayBoard(int[][] board, int turn, int flag) throws RemoteException {
        if(flag==1){
            System.out.println("Please Enter A Valid Column Number");
        }
        if(flag==0 || flag==1){
            this.PlayerTurn = turn;
            if(PlayerTurn == PlayerNum){
                System.out.println("Your Turn");
            }
            else{
                System.out.println("Opponent's Turn");
            }
            System.out.println("\nBoard\n");
            for(int i=1;i<=7;i++)
            {
                System.out.print(i+" ");
            }
            System.out.println();
            System.out.println();
            for(int i=0;i<6;i++)
            {
                for(int j=0; j<7;j++)
                {
                    System.out.print(board[i][j]+" ");
                }
                System.out.println();
            }
            System.out.println();
            if(PlayerTurn == PlayerNum){
                if(flag==0){
                    System.out.println("Press ENTER To Keep Playing");
                }
                System.out.println();
            }
        }
        if(flag == 2 || flag == 3){
            System.out.println("Player "+(flag-1)+" Wins!!!!!");
            chkExit = false;
        }

    }
 
    public void playTurn(int player,int column) throws RemoteException {}
 
    public int addClient(BoardInterface boardinterface ) throws RemoteException { 
        return 0;
    }

    public void run() {
        
        System.out.println("Successfully Connected To RMI Server");
        System.out.println("NOTE : Type EXIT to Exit From The Service");
        System.out.println("You Are Player Number "+PlayerNum);
        Scanner scanner = new Scanner(System.in);
        String message;
            
        while(chkExit) {
            if(PlayerTurn != PlayerNum){
                System.out.println("Waiting For Other Player");
                System.out.println("Enter EXIT to Exit From The Game");
                message = scanner.nextLine();
                if(message.equals("EXIT")) {
                    chkExit = false;
                }
            }
            else{
                System.out.println("Enter Column Number To Play Your Turn Or EXIT to Exit From The Game");
                message = scanner.nextLine();
                int col = 1;
                int flag = 1;
                try {
                    col = Integer.parseInt(message);
                } catch (NumberFormatException nfe) {
                    flag = 0;
                }
                if(message.equals("EXIT")) {
                    chkExit = false;
                }
                else {
                    try {
                        if(flag==1){
                            server.playTurn(PlayerNum , col);
                        }
                    }
                    catch(RemoteException e) {
                        e.printStackTrace();
                    }
                } 
            } 
        } 
        System.out.println("\nSuccessfully Exited From The RMI Connect 4 Program\nThank You For Playing...");
    }
    public static void main(String[] args) throws MalformedURLException,RemoteException,NotBoundException {
        Scanner scanner = new Scanner(System.in);
        String clientName = "";
  
        System.out.println("\n~~ Welcome To RMI Connect 4 Program ~~\n");  
        System.out.print("Enter The Name : ");
        clientName = scanner.nextLine();
        System.out.println("\nConnecting To RMI Server...\n");

        BoardInterface boardinterface = (BoardInterface)Naming.lookup("rmi://localhost/RMIServer");
        Client clientobj = new Client(boardinterface , clientName);
        if(PlayerNum!=0){
            new Thread(clientobj).start();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clientobj.displayBoard(new int[6][7], PlayerTurn, 0);
        }
        else{
            System.out.println("Already 2 Clients Present\nPlease Try Again Later...");
        }
        
    }
 
}
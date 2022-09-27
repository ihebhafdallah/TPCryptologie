/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpcryptologie;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author IhebHafdallah
 */
public class Server {
    
    static int key = 1;
    
    // Declaring ANSI_RESET so that we can reset the color
    public static final String ANSI_RESET = "\u001B[0m";
  
    // Declaring the color
    // Custom declaration
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {      
        
        ServerSocket serverSocket = new ServerSocket (7777);
        Socket socket = serverSocket.accept();
        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
        TPCryptologie TPC = new TPCryptologie();
        
        for(;;) { 
            String chaine = (String)input.readObject();
            String chaine_decrypt = TPC.decrypt(key, chaine);
            
            if ("exit".equals(chaine_decrypt) || "EXIT".equals(chaine_decrypt)) {
                break;
            }
            
            System.out.println("------- Server -------");
            System.out.println(ANSI_RED + "Message sans cryptage : " + chaine + ANSI_RESET);
            System.out.println(ANSI_GREEN + "Message après décodage : " + chaine_decrypt + ANSI_RESET);
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter String: ");  
            String str = sc.nextLine();
            
            if ("exit".equals(str) || "EXIT".equals(str)) {
                str = TPC.crypt(key, str);
                output.writeObject(new String (str));
                break;
            }
            
            str = TPC.crypt(key, str);
            output.writeObject(new String (str));
        }
        System.out.println(ANSI_RED + "Fin de communication" + ANSI_RESET);
    }  
}

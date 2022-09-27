/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpcryptologie;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author IhebHafdallah
 */
public class Client {
    
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
    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
        
        InetAddress adr = InetAddress.getByName("127.0.0.1");
        Socket socket = new Socket (adr, 7777);
        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
        TPCryptologie TPC = new TPCryptologie();

        for(;;) {
            Scanner sc = new Scanner(System.in);   
            System.out.print("Enter String : ");  
            String str = sc.nextLine(); 
            
            if ("exit".equals(str) || "EXIT".equals(str)) {
                str = TPC.crypt(key, str);
                output.writeObject(new String (str));
                break;
            }
            
            str = TPC.crypt(key, str);
            output.writeObject(new String (str));
            String chaine = (String)input.readObject();
            String chaine_decrypt = TPC.decrypt(key, chaine);
            
            if ("exit".equals(chaine_decrypt) || "EXIT".equals(chaine_decrypt)) {
                break;
            }
            
            System.out.println("------- Client -------");
            System.out.println(ANSI_RED + "Message sans cryptage : " + chaine + ANSI_RESET);
            System.out.println(ANSI_GREEN + "Message après décodage : " + chaine_decrypt + ANSI_RESET);
        }
        System.out.println(ANSI_RED + "Fin de communication" + ANSI_RESET);
    }  
}

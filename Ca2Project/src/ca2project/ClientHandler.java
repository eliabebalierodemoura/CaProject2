/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca2project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @Eliabe ID:2022474
 */
public class ClientHandler implements Runnable {
    
    // we have anarray list for every client that we have instantiated
    // whenever a client sends a menssage we can loop our array list and send the msg to each client 
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;
    
    // this constructor is gona take the value passed in the Server class
    public ClientHandler(Socket socket){
        try{
            this.socket = socket;
           this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = bufferedReader.readLine();
            clientHandlers.add(this);
            broadcastMessage("Server: "+ clientUsername + " has entered the chat!");
            
        }catch(IOException e){
            // this method will close our sockets and also the streams
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }
    
    /*Everything on this run method is what is going to run on a separate thread, so 
    as we will have multiple threads our program is not going to be stuck as a thread is waiting for menssages
    and other for the rest of application
    */
    @Override
    public void run(){
        String messageFromClient;
        
        while(socket.isConnected()){
            try{
                
                messageFromClient = bufferedReader.readLine();
                broadcastMessage(messageFromClient);
            }catch(IOException e){
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }
    public void broadcastMessage(String messageToSend){
        for (ClientHandler clientHandler : clientHandlers){
       try{
           if (!clientHandler.clientUsername.equals(clientUsername)){
               clientHandler.bufferedWriter.write(messageToSend);
               clientHandler.bufferedWriter.newLine();
               clientHandler.bufferedWriter.flush();
           }
       }catch (IOException e ){     
           closeEverything(socket, bufferedReader, bufferedWriter);
       }
        }
    }
    
    //On this method we will be able to remove our user from array list
    public void removeClientHandler(){
        
        clientHandlers.remove(this);
        
        broadcastMessage("server: "+clientUsername+ " has left the chat!");
    }
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        
        removeClientHandler();
        try{
            if(bufferedReader != null){
                bufferedReader.close();
            }
            if(bufferedWriter != null){
                bufferedWriter.close();
            }
            
            if(socket != null){
                socket.close();
            }
            
            
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    
    
    
    
    
    
    
    
}

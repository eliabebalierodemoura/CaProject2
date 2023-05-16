/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ca2project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;



/**
 *
 * @author HP
 */
public class Client {

    /**
     * @param args the command line arguments
     */
   // public static void main(String[] args){
        
        private Socket socket;
        private BufferedReader bufferedReader;
        private BufferedWriter bufferedWriter;
        private String username;
        
        public Client (Socket socket, String username){
            try{
                this.socket = socket;
                this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                this.username = username;
            }catch(IOException e){
                closeEverything(socket, bufferedReader, bufferedWriter);
                
                
            }
        }
        //This method we are going to use for the user indentify theyself with a user name ID
        public void sendMessage(){
            try {
                bufferedWriter.write(username);
                bufferedWriter.newLine();
                bufferedWriter.flush();
                
                Scanner scanner = new Scanner(System.in);
                while (socket.isConnected()){
                    String messageToSend = scanner.nextLine();
                    bufferedWriter.write(username +":" + messageToSend);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }
                }catch(IOException e){
                    closeEverything(socket, bufferedReader, bufferedWriter);
                    
            }
    }
        // this where we use another thread, so like this we do not need to whole 
        //time wating and do not being able to send one  
    public void listenForMessage(){
        new Thread(new Runnable(){
            @Override
            public void run(){
                String msgFromGroupChat;
                
                while(socket.isConnected()){
                    try{
                        msgFromGroupChat = bufferedReader.readLine();
                        System.out.println(msgFromGroupChat);
                    }catch (IOException e){
                    closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }
    
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        
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
    // as we are using separated threads we are able to send and while receiving as well
    // we are using localhost
  public static void main(String[] args) throws IOException{
      Scanner scanner = new Scanner(System.in);
      System.out.println("Enter a username for the group chat: ");
      String username = scanner.nextLine();
      Socket socket = new Socket("localhost", 1234);
      Client client = new Client(socket, username);
      client.listenForMessage();
      client.sendMessage();
      
  }
  }

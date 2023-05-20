/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prog.server;

import ca2project.UserHandler;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 *
 * @Eliabe ID:2022474
 */
public class Server {
      private ServerSocket serverSocket;
      
      public Server(ServerSocket serverSocket){
          this.serverSocket = serverSocket;
          
    }
      public void startServer(){
          
      try{
          while(!serverSocket.isClosed()){
              
              Socket socket = serverSocket.accept();
              System.out.println("A new client has connected!");
              UserHandler userHandler = new UserHandler(socket);
              
              Thread thread = new Thread(userHandler);
              thread.start();
          }
          
          }catch(IOException e){
              
          }
      }
      
      public void closeServerSocket(){
          try{
              if(serverSocket != null){
                  serverSocket.close();
              }
          }catch (IOException e){
              e.printStackTrace();
          }
      }
      
      public static void main(String[] args) throws IOException{
          ServerSocket serverSocket = new ServerSocket(1234);
          Server server = new Server(serverSocket);
          server.startServer();
      }
}


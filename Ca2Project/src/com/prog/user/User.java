/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prog.user;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author HP
 */
public class User {
      // as we are using separated threads we are able to send and while receiving as well
    // we are using localhost
  public static void main(String[] args) throws IOException{
      Scanner scanner = new Scanner(System.in);
      System.out.println("Please Enter a username for the group chat: ");
      String username = scanner.nextLine();
      Socket socket = new Socket("localhost", 1234);
      ChatMSG client = new ChatMSG(socket, username);
      client.listenForMessage();
      client.sendMessage();
      
  }
  }

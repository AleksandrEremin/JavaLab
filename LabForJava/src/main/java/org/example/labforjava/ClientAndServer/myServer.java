package org.example.labforjava.ClientAndServer;

import com.google.gson.Gson;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public class myServer {
    int port = 3124;
    InetAddress ip = null;
    public void StartServer() {
        ServerSocket ss;
        Socket cs;
        InputStream is;
        OutputStream os;
        DataInputStream dis;
        DataOutputStream dos;

        try {
            ip = InetAddress.getLocalHost();
            ss = new ServerSocket(port, 0, ip);
            System.out.println("Server start\n");

            while(true)
            {
                cs = ss.accept();
                System.out.println("Client connect (" + cs.getPort() + ")");
                is = cs.getInputStream();
                os = cs.getOutputStream();
                dis = new DataInputStream(is);
                dos = new DataOutputStream(os);
                Gson gson = new Gson();
                String s = dis.readUTF();
                //gson.fromJson(s, Request.class);
            }

        } catch (IOException ignored) {System.out.println("Error");}
    }

    public static void main(String[] args) {
        new myServer().StartServer();
    }
}

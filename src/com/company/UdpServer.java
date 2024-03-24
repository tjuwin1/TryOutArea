//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.company;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.Semaphore;

public class UdpServer implements Runnable {
    private final int clientPort;
    public static boolean kill = false;
    private Semaphore sendLock = new Semaphore(1);
    private long lastSendMillis = 0L;
    private String lastUpdate = "";
    private String currentUpdate = "";

    public UdpServer(int clientPort) {
        this.clientPort = clientPort;
    }

    public void run() {
        while(!kill) {
            try {
                if (System.currentTimeMillis() - this.lastSendMillis >= 50L) {
                    this.lastSendMillis = System.currentTimeMillis();
                    this.sendLock.acquire();
                    if (this.currentUpdate.length() > 0) {
                        this.splitAndSend(this.currentUpdate);
                        this.currentUpdate = "";
                    }

                    this.sendLock.release();
                }
            } catch (InterruptedException var2) {
                var2.printStackTrace();
            }
        }

    }

    public void splitAndSend(String message) {
        int startIndex = 0;

        int endIndex;
        do {
            for(endIndex = Range.clip(startIndex + 600, 0, message.length() - 1); message.charAt(endIndex) != '%'; --endIndex) {
            }

            this.sendUdpRAW(message.substring(startIndex, endIndex + 1));
            startIndex = endIndex + 1;
        } while(endIndex != message.length() - 1);

    }

    private void sendUdpRAW(String message) {
        try {
            DatagramSocket serverSocket = new DatagramSocket();

            try {
                DatagramPacket datagramPacket = new DatagramPacket(message.getBytes(), message.length(), InetAddress.getByName("127.0.0.1"), this.clientPort);
                serverSocket.send(datagramPacket);
            } catch (Throwable var6) {
                try {
                    serverSocket.close();
                } catch (Throwable var5) {
                    var6.addSuppressed(var5);
                }

                throw var6;
            }

            serverSocket.close();
        } catch (IOException var7) {
            var7.printStackTrace();
        }

    }

    public void addMessage(String string) {
        if (!this.sendLock.tryAcquire()) {
            this.lastUpdate = string;
        } else {
            this.currentUpdate = string;
            this.sendLock.release();
        }

    }
}

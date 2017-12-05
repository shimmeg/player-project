package org.shimmeg.services.messaging;

import org.shimmeg.model.messaging.Message;
import org.shimmeg.settings.AppSettings;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DistributedMessageSystem extends AbstractMessageSystem {

    private ServerSocket serverSocket;
    private boolean isStopped = false;
    private ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public DistributedMessageSystem() {
        System.out.println("Messageing Service - Distributed Message System");
    }

    public void start() {
        System.out.println("Starting Distributed MS");
        openServerSocket();
        threadPool.execute(new ListenerRunnable());
        System.out.println("Distributed Messaging system started");
    }

    @Override
    public void sendMessage(Message msg) {
        System.out.println("Sending message to player with id " + msg.getReceiverId());
        Socket socket;
        try {
            InetAddress ipAddress = InetAddress.getByName(AppSettings.getServerHost());
            int serverPort = resolvePortByReceiverId(msg.getReceiverId());
            socket = new Socket(ipAddress, serverPort);

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

            outputStream.writeObject(msg);
            outputStream.flush();
            outputStream.close();
            socket.close();
            System.out.println("Finished sending message");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void sendStopMessage(int playerId) {
        Message stopMessage = Message.createStopMessageForPlayer(playerId);
        sendMessage(stopMessage);
    }

    @Override
    public void finishCommunication() {
        if (!isStopped) stop();
    }

    private int resolvePortByReceiverId(int receiverId) {
        //each app contains 10 players,
        return receiverId / 10 * 10;
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(AppSettings.getPort());
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port " + AppSettings.getPort(), e);
        }
    }

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop() {
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private class ListenerRunnable implements Runnable {

        @Override
        public void run() {
            while (!isStopped()) {
                Socket clientSocket;
                try {
                    System.out.println("Listener Runnable executed");
                    clientSocket = serverSocket.accept();
                } catch (IOException e) {
                    if (isStopped()) {
                        System.out.println("Server Stopped.");
                        break;
                    }
                    throw new RuntimeException("Error accepting client connection", e);
                }
                threadPool.execute(new MessagesProcessorRunnable(clientSocket));
            }
            threadPool.shutdown();
        }
    }

    private class MessagesProcessorRunnable implements Runnable {

        private Socket clientSocket = null;

        public MessagesProcessorRunnable(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        public void run() {
            try {
                processClientRequest(clientSocket);
            } catch (IOException | ClassNotFoundException e) {
                //report exception somewhere.
                e.printStackTrace();
            }
        }

        private void processClientRequest(Socket clientSocket) throws IOException, ClassNotFoundException {
            System.out.println("Starting processing message");
            InputStream input = clientSocket.getInputStream();
            ObjectInputStream objectInput = new ObjectInputStream(input);
            Message msg = (Message) objectInput.readObject();
            input.close();
            if (msg.getText().equals("stop")) {
                stop();
                return;
            }
            putMessageToQueue(msg);
            System.out.println("Messages added to Messages queue");
            notifyReceiver(msg.getReceiverId());
            System.out.println("Receiver notified " + msg.getReceiverId());
        }
    }
}

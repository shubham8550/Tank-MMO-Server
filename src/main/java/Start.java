import java.io.IOException;

public class Start {
    public static void main(String[] args) throws IOException{
        WorldStat worldStat=new WorldStat();

        Thread t1=new Streamer(worldStat);
        t1.start();











//        ServerSocket ss = new ServerSocket(Res.serverPort);
//        WorldStat worldStat=new WorldStat();
//        // running infinite loop for getting
//        // client request
//        System.out.println("Server started");
//
//        System.out.println("Waiting for a client ...");
//        while (true)
//        {
//            Socket s = null;
//
//            try
//            {
//                // socket object to receive incoming client requests
//                s = ss.accept();
//
//                System.out.println("A new client is connected : " + s);
//
//                // obtaining input and out streams
//                DataInputStream dis = new DataInputStream(s.getInputStream());
//                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
//
//                System.out.println("Assigning new thread for this client");
//
//                // create a new thread object
//                Thread t = new ClientHandler(s, dis, dos,worldStat);
//
//                // Invoking the start() method
//                t.start();
//
//            }
//            catch (Exception e){
//                s.close();
//                e.printStackTrace();
//            }
//        }
//


    }
}

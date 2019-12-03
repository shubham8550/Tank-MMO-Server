import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;

public class Streamer extends Thread {
    WorldStat worldStat;

    DatagramSocket socket = null;

    Streamer(WorldStat w) {
        this.worldStat = w;
    }

    @Override
    public void run() {

        try {
            socket = new DatagramSocket(Res.serverPort);
            byte[] incomingData = new byte[100000];//max stream size

            while (true) {
                System.out.println("Waiting for packet");

                DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
                socket.receive(incomingPacket);
                byte[] data = incomingPacket.getData();
                ByteArrayInputStream in = new ByteArrayInputStream(data);
                ObjectInputStream is = new ObjectInputStream(in);
                try {
                    WorldStat temp = (WorldStat) is.readObject();
                    upldateSinglerecord(temp);
                    System.out.println("Worldstat object received = " + temp);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                    //replying data
                InetAddress IPAddress = incomingPacket.getAddress();

                int port = incomingPacket.getPort();



                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ObjectOutputStream os = new ObjectOutputStream(outputStream);
                os.writeObject(worldStat);


                byte[] data1 = outputStream.toByteArray();
                DatagramPacket sendPacket = new DatagramPacket(data1, data1.length, IPAddress, port);
                socket.send(sendPacket);


                os.flush();
                outputStream.flush();
                data=new byte[10000];
                incomingData = new byte[10000];
                Thread.sleep(100);




            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void upldateSinglerecord(WorldStat temp) {
        if (worldStat.id.contains(temp.id.get(0))){

            int i=worldStat.id.indexOf(temp.id.get(0));

            //worldStat.id.add(i,temp.id.get(0));
            //worldStat.name.add(i,temp.name.get(0));
            //worldStat.carModle.add(i,temp.carModle.get(0));
            worldStat.face.add(i,temp.face.get(0));
            worldStat.X.add(i,temp.X.get(0));
            worldStat.Y.add(i,temp.Y.get(0));

            System.out.println( worldStat.id.size() +")" + temp.id.get(0)+"- LOCATION X - "+temp.X.get(0) +" Y - "+temp.Y.get(0) +" Face "+temp.face.get(0) );



        }else{

            // if player has no session
            worldStat.id.add(temp.id.get(0));
            worldStat.name.add(temp.name.get(0));
            worldStat.carModle.add(temp.carModle.get(0));
            worldStat.face.add(temp.face.get(0));
            worldStat.X.add(temp.X.get(0));
            worldStat.Y.add(temp.Y.get(0));

        }
    }


}

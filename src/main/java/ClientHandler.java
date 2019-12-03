
import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

class ClientHandler extends Thread
{

    final DataInputStream dis;
    final DataOutputStream dos;
     Socket s;
     WorldStat worldStat;


    // Constructor 
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos,WorldStat worldStat)
    {
        this.s = s;
        this.worldStat = worldStat;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run()
    {
        //sending client id
        System.out.println("Authorizing ID ");
        int clid=(worldStat.id.size());
        try {
            dos.writeUTF(Integer.toString(clid));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //worldStat.id.add(clid);
        worldStat.X.add(new Integer(0));
        worldStat.Y.add(new Integer(0));
        worldStat.face.add(new Character('n'));
        worldStat.carModle.add("Car.png");
        worldStat.name.add("anon");
        //client id sended end
        System.out.println("Authorized ID "+ clid +"\n");






        String line = "";










        while (true)
        {


            try
            {


                //upload data
                System.out.println("---------------");
                System.out.println("uploading");
                ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());

                os.writeObject(worldStat);

                //upload data end

                //download updated data
                System.out.println("Download");
                ObjectInputStream ise = new ObjectInputStream(s.getInputStream());

                WorldStat temp=(WorldStat) ise.readObject();

                //download updated data
                System.out.println("writing world update");
                worldStat.X.add(clid,temp.X.get(clid));
                worldStat.Y.add(clid,temp.Y.get(clid));
                worldStat.face.add(clid,temp.face.get(clid));





                //example for reading data
//                line = dis.readUTF();
//                System.out.println(line);
//
                if(line.equals("exit")){
                    break;
                }

            }
            catch(IOException i)
            {
                System.out.println(i+" ittss her");

                //break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try
        {
            // closing resources
            this.dis.close();
            this.dos.close();

        }catch(IOException e){
            e.printStackTrace();
        }

    }
} 
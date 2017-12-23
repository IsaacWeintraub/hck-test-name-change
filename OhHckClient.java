import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class OhHckClient {

    private PrintWriter out;
    private Socket socket;
    private BufferedReader in;
    private ClientSidePlayer player;

    public OhHckClient(String host, ClientSidePlayer player)
            throws UnknownHostException, IOException {
        socket = new Socket(host, OhHckServer.PORT);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.player = player;
    }

    public void start() throws IOException {
        String fromServer;
        while ((fromServer = in.readLine()) != null) {
            player.process(fromServer);
            if (fromServer.equals("STOP")) {
                break;
            }
        }
        socket.close();
        out.close();
        in.close();
    }

    protected void transmit(String message) {
        out.println(message);
    }
}
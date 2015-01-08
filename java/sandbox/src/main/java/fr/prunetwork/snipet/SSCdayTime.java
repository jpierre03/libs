import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.net.*;
import java.util.*;

class SSCdayTime {
  public static void main(String args[]) throws Exception {
    if (args.length != 1)
      System.out.println("usage : java SSCdayTime port");
    else {
      int port = Integer.parseInt(args[0]);
      InetSocketAddress inetAdress = new InetSocketAddress(port);
      ServerSocketChannel serverChannel = ServerSocketChannel.open();
      ServerSocket serverSocket = serverChannel.socket();
      serverSocket.bind(inetAdress);
      Charset charset = Charset.forName("US-ASCII");
      CharsetEncoder encoder = charset.newEncoder();
      while (true) {
        SocketChannel sockClient = serverChannel.accept(); 
        Date maintenant = new Date();
        CharBuffer reponse = CharBuffer.wrap(maintenant);
        encoder.reset();
        sockClient.write(encoder.encode(reponse));
        sockClient.close();
      }
    }
  }
}



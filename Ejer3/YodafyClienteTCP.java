//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class YodafyClienteTCP {

	public static void main(String[] args) {
		
		String buferEnvio;
		String buferRecepcion;
		int bytesLeidos=0;
		
		// Nombre del host donde se ejecuta el servidor:
		String host="localhost";
		// Puerto en el que espera el servidor:
		int port=42069;
		
		// Socket para la conexión TCP
		Socket socketServicio=null;
		
		try {

			socketServicio = new Socket(host,port);
			
			BufferedReader inReader = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));
			PrintWriter outPrint = new PrintWriter(socketServicio.getOutputStream(), true);
			
			// Si queremos enviar una cadena de caracteres por un OutputStream, hay que pasarla primero
			// a un array de bytes:
			buferEnvio = new String("Al monte del volcán debes ir sin demora");

			do{
				System.out.println("Sending data");
				outPrint.println(buferEnvio);
				buferRecepcion = inReader.readLine();
				System.out.println("Receiving data");
				System.out.println("Data size: "+buferRecepcion.lenght());
				System.out.println("Content: "+buferRecepcion);
			}while(true);
			socketServicio.close();

		} catch (UnknownHostException e) {
			System.err.println("Error: Nombre de host no encontrado.");
		} catch (IOException e) {
			System.err.println("Error de entrada/salida al abrir el socket.");
		}
	}
}

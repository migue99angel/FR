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
		
		// Nombre del host donde se ejecuta el servidor:
		String host="localhost";
		// Puerto en el que espera el servidor:
		int port = 42069;
		
		// Socket para la conexión TCP
		Socket socketServicio = null;
		
		try {

			socketServicio = new Socket(host,port);

			InputStream inputStream = socketServicio.getInputStream();
            OutputStream outputStream = socketServicio.getOutputStream();
			
			// Si queremos enviar una cadena de caracteres por un OutputStream, hay que pasarla primero
			// a un array de bytes:
			buferEnvio = new String("Al monte del volcan debes ir sin demora");

			// Enviamos el buffer
			PrintWriter outPrinter = new PrintWriter(outputStream, true);
            outPrinter.println(buferEnvio);
			
			// Limpiamos el buffer
			outPrinter.flush();

			// Leemos el buffer
			BufferedReader inReader = new BufferedReader(new InputStreamReader(inputStream));
            buferRecepcion = inReader.readLine();
			
			// Mostramos lo que recibimos
			System.out.println("Recibidos " + buferRecepcion);

			// Cerramos el socket
			socketServicio.close();
			
			// Excepciones:
		} catch (UnknownHostException e) {
			System.err.println("Error: Nombre de host no encontrado.");
		} catch (IOException e) {
			System.err.println("Error de entrada/salida al abrir el socket.");
		}
	}
}

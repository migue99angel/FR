import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
public class YodafyServidorIterativo {

	public static void main(String[] args) {
		// Puerto en el que espera el servidor:
		int port=42069;
		
		try {
			// Abrimos el socket en modo pasivo, escuchando el en puerto indicado por "port"
			ServerSocket socketServidor = new ServerSocket(port);
			
			do {
				
				// Aceptamos una nueva conexión
				Socket socketConexion = socketServidor.accept();
				
				// Creamos un objeto de la clase ProcesadorYodafy, pasándole como 
				// argumento el nuevo socket, para que realice el procesamiento
				// Este esquema permite que se puedan usar hebras más fácilmente.
				ProcesadorYodafy procesador = new ProcesadorYodafy(socketConexion);
				procesador.procesa();
				
			} while (true);
			
		} catch (IOException e) {
			System.err.println("Error al escuchar en el puerto "+port);
		}

	}

}

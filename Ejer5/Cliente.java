import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    private Socket socketConexion;
    private ServerSocket socketServidor;
    private DataInputStream bufferEntrada = null;
    private DataOutputStream bufferSalida = null;
    Scanner teclado = new Scanner(System.in);
    final String COMANDO_TERMINACION = "salir()";
    int port = 42069;
    String ip = "localhost";

    // Función para levantar la conexión desde el cliente
    public void levantarConexion() {
        try {
            socketServidor = new Socket(ip, port);
            System.out.println("Conectado a: " + socketConexion.getInetAddress().getHostName());
        } catch (Exception e) {
            System.out.println("Excepción al levantar conexión: " + e.getMessage());
            System.exit(0);
        }
    }

    // Abrimos los flujos desde el cliente
    public void abrirFlujos() {
        try {
            bufferEntrada = new DataInputStream(socketConexion.getInputStream());
            bufferSalida = new DataOutputStream(socketConexion.getOutputStream());
            bufferSalida.flush();

        } catch (IOException e) {
            System.err.println("Error en la apertura de flujos");
        }
    }

    // Enviamos el contenido de los flujos
    public void enviar(String s) {
        try {
            bufferSalida.writeUTF(s);
            bufferSalida.flush();
        } catch (IOException e) {
            System.err.println("Error al enviar");
        }
    }

    // Cerramos la conexión desde el cliente
    public void cerrarConexion() {
        try {
            bufferEntrada.close();
            bufferSalida.close();
            socketConexion.close();

            System.out.println("Conexión terminada");
        } catch (IOException e) {
            System.out.println("IOException on cerrarConexion()");
        }
    }

    // Ejecutamos una nueva conexión con la clase Thread
    public void ejecutarConexion() {
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    levantarConexion();
                    abrirFlujos();
                    recibirDatos();
                } finally {
                    cerrarConexion();
                }
            }
        });
        hilo.start();
    }

    // Recibimos los datos en los flujos
    public void recibirDatos() {
        String st = "";
        try {
            do {
                st = (String) bufferEntrada.readUTF();
                System.out.println("\n[Servidor] => " + st);
                System.out.print("\n[Tú] => ");
            } while (!st.equals(COMANDO_TERMINACION));
        } catch (IOException e) {
            cerrarConexion();
        }
    }

    // Escribimos nuestros datos y los enviamos
    public void escribirDatos() {
        String entrada = "";
        while (true) {
            System.out.print("[Tú] => ");
            entrada = teclado.nextLine();
            if(entrada.length() > 0)
                enviar(entrada);
        }
    }

    // Método principal
    public static void main(String[] argumentos) {
        Cliente cliente = new Cliente();
        Scanner escaner = new Scanner(System.in);

        cliente.ejecutarConexion();
        cliente.escribirDatos();
    }
}
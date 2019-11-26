import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class Servidor {

    private Socket socketConexion;
    private ServerSocket socketServidor;
    private DataInputStream bufferEntrada = null;
    private DataOutputStream bufferSalida = null;
    Scanner escaner = new Scanner(System.in);
    final String COMANDO_TERMINACION = "salir()";
    int port = 42069;

    // Función para intentar establecer una conexión
    public void levantarConexion() {
        try {
            socketServidor = new ServerSocket(port);
            socketConexion = socketServidor.accept();

            System.out.println("Conexión establecida con el cliente");

        } catch (IOException e) {
            System.err.println("Error al escuchar en el puerto " + port);
        }
    }

    // Lee los flujos y verifica que son correctos
    public void flujos() {
        try {
            bufferEntrada = new DataInputStream(socketConexion.getInputStream());
            bufferSalida = new DataOutputStream(socketConexion.getOutputStream());
            bufferSalida.flush();

        } catch (IOException e) {
            System.out.println("Error en la apertura de flujos");
        }
    }

    // Recibimos datos de entrada hasta que introducimos salir()
    public void recibirDatos() {
        String st = "";
        try {
            do {
                st = (String) bufferEntrada.readUTF();
                System.out.println("\n[Cliente] => " + st);
                System.out.println("\n[Tú] => ");
            } while (!st.equals(COMANDO_TERMINACION));
        } catch (IOException e) {
            cerrarConexion();
        }
    }


    // Enviamos nuestro texto
    public void enviar(String s) {
        try {
            bufferSalida.writeUTF(s);
            bufferSalida.flush();
        } catch (IOException e) {
            System.err.println("Error al enviar");
        }
    }

    // Escribimos nuestro texto
    public void escribirDatos() {
        while (true) {
            System.out.print("[Tú] => ");
            enviar(escaner.nextLine());
        }
    }

    // Cerramos la conexión
    public void cerrarConexion() {
        try {
            bufferEntrada.close();
            bufferSalida.close();
            socketConexion.close();

        } catch (IOException e) {
          System.err.print("Error al intentar cerrar la conexión");
        }
    }

    // Ejecutamos una nueva conexión con la clase Thread
    public void ejecutarConexion() {
        Thread nuevoHilo = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        levantarConexion();
                        flujos();
                        recibirDatos();
                    } finally {
                        cerrarConexion();
                    }
                }
            }
        });

        nuevoHilo.start();
    }

    // Método principal
    public static void main(String[] args){
        Servidor s = new Servidor();
        Scanner sc = new Scanner(System.in);

        s.ejecutarConexion();
        s.escribirDatos();
    }
}
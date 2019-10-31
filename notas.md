### Funcionamiento del socket en FTP ###

        **Servidor**               **Cliente**
         -SocketScaner(puerto)      -Socket(host,puerto)
         -accept()                  --------------------    
         -------------               OutputStream
         OutputSteam                 InputStream
         InputStream                 --------------------   
         -------------                  close()
         close()


### Funcionamiento del socket en UDP ###


        **Servidor**                **Cliente**
        -DatagramSocket(puerto)     -DatagramSocket()
        -DatagramPacket()           -DatagramPocket(host,puerto)
        -receive()                  -send()
        -----------------           ----------------------------
        close()                      close()
        
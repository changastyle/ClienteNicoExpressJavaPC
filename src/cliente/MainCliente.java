package cliente;

public class MainCliente
{
    public static void main(String[] args)
    {
        ManejadorCliente manejadorCliente = new ManejadorCliente();
        
        VentanaCliente ventanaCliente = new VentanaCliente(manejadorCliente);
        ventanaCliente.show(true);
        
    }
    
}

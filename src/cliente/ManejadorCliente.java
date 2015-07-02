package cliente;

import java.util.ArrayList;
import java.util.Scanner;
import serializable.ConjuntoDevuelto;
import serializable.ConjuntoJugadas;
import serializable.Jugada;
import serializable.*;

public class ManejadorCliente
{
    private ConjuntoJugadas conjuntoJugadasRealizadas ;
    
    private final String direccionIPServer = XMLAPI.XMLHandler.leer("config.xml","direccionIP");
    private final int puertoServer = XMLAPI.XMLHandler.leerInt("config.xml","port");
    private Cliente cliente;
    
    
    //IR SACANDO ESTO:
    private int maximaCantidadJugadasPosible;
    private final int numeroMaximo = 999;

    
    
    public ManejadorCliente()
    {
        System.out.println("DIRECCION IP DEL SERVER: " + direccionIPServer);
        System.out.println("PORT DEL SERVER: " +  puertoServer);
        
        //pedirParametrosAlServidor();
        /*
        conjuntoJugadasRealizadas = new ConjuntoJugadas();
        maximaCantidadJugadasPosible = 5;
        */
    }
    public ParametrosEncapsuladosParaClientes pedirParametrosAlServidor()
    {
        ParametrosEncapsuladosParaClientes parametrosEncapsuladosParaClientes = null;
        try
        {
            //ME CONECTO AL SERVER, ESTABLESCO CANALES, ESPERO A QUE ESTEN LISTOS:
            cliente = new Cliente(direccionIPServer, puertoServer);
            cliente.start();
            cliente.join();
            
            //RECIBO LOS PARAMETROS DEL SERVER:
            parametrosEncapsuladosParaClientes = (ParametrosEncapsuladosParaClientes) cliente.recibir();
            if (parametrosEncapsuladosParaClientes != null)
            {
                System.out.println("" +  parametrosEncapsuladosParaClientes.toString());
            }
        } 
        catch (Exception e)
        {
            System.out.println("ERROR: NO SE PUDO PEDIR PARAMETROS AL SERVIDOR.");
            e.printStackTrace();
        }
        return parametrosEncapsuladosParaClientes;
    }
    public int menu()
    {
        int opcion = 0;
        
        Scanner scanner = new Scanner (System.in);
        System.out.println("|------------------ OPCIONES -----------------|");
        System.out.println("|1.Agregar JUGADA                             |");
        System.out.println("|2.Enviar CONJUNTO                            |");
        System.out.println("|99.cerrar el progama.                        |");
        System.out.println("|---------------------------------------------|");
        System.out.print("opcion:");
        opcion = scanner.nextInt();
        
        return opcion;
    }
    
    public void seleccion()
    {
        int opcion = menu();
        
        switch (opcion)
        {
            case 1: 
                
                if(conjuntoJugadasRealizadas.getArrJugadas().size() < maximaCantidadJugadasPosible)
                {
                   Jugada jaux = pedirJugada();
                   conjuntoJugadasRealizadas.agregarJugada(jaux); 
                    System.out.println("" + mostrarConjuntoJugadas());
                }
                else
                {
                    System.out.println("YA TENES " + maximaCantidadJugadasPosible + " JUGADAS REALIZADAS");
                }
                
            break;
                
            case 2: 
                
                int dineroApostado = pedirDineroParaElConjuntoJugadas();
                //conjuntoJugadasRealizadas.setDineroApostado(dineroApostado);
                
                Cliente cliente = new Cliente(direccionIPServer , puertoServer);
                cliente.start(); 
                cliente.enviar(this.conjuntoJugadasRealizadas);
                ConjuntoDevuelto conjuntoDevuelto = (ConjuntoDevuelto) cliente.recibir();
                
                System.out.println("CONJUNTO DEVUELTO = " + conjuntoDevuelto.toString() );
                
            break;
                
            /*case 3: 
            
            break;*/
                
            case 99: 
                
                System.exit(0);
                
            break;
                
            default:
                
                System.out.println("OPCION INVALIDA");
                seleccion();
                
            break;
        }
    }
    public Jugada pedirJugada()
    {
        Jugada jugada = new Jugada();
        
        jugada.setNumero("" + pedirNumero());

        return jugada;
    }
    public int pedirNumero()
    {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("\n|--------------- A QUE NUMERO QUIERE JUGARLE ?? ------------------|");
        System.out.print("|Numero: #");
        int numero = scanner.nextInt();
        
        if(numero > numeroMaximo)
        {
            System.out.println("|NUMERO MAXIMO = " + numeroMaximo);
            numero = pedirNumero();
        }
        
        return numero;
    }
    public int pedirDineroParaElConjuntoJugadas()
    {
        int dinero = 0 ;
        
        System.out.print("INGRESE DINERO QUE DESEA APOSTAR: $" );
        Scanner scanner = new Scanner(System.in);
        dinero = scanner.nextInt();
        
        return dinero;
    }
    public String mostrarConjuntoJugadas()
    {
        String salida = "\n|--------------- HASTA EL MOMENTO ESTA CARGADO EL SIGUIENTE CONJUNTO   --------------|";
        
        for (Jugada jugada : conjuntoJugadasRealizadas.getArrJugadas())
        {
            salida += "\n|#" + jugada.getNumero() + "                                                                                 |";
        }
       
        salida += "\n|------------------------------------------------------------------------------------|\n";
        
        return salida;
    }

    
    /*GYS*/

    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package davidceducastur.es.tiendainformatica;

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author alu03d
 */
public class TiendaInformatica {
    
    private static Map<String, Cliente> clientes = new HashMap<>();
    private static Map<String, Articulo> articulos = new HashMap<>();
    private static List<Pedido> pedidos = new ArrayList<>();

    public static void main(String[] args) {
        TiendaInformatica t = new TiendaInformatica();
        //t.cargaDatos();
        t.leerArchivos();
        t.menuPrincipal();
        t.backup();
       
    }

    public void stock(String id, int unidadesPed) throws StockAgotado, StockInsuficiente {
        int n = articulos.get(id).getExistencias();
        if (n == 0) {
            throw new StockAgotado("Stock AGOTADO para el articulo: " + articulos.get(id).getDescripcion());
        } else if (n < unidadesPed) {
            throw new StockInsuficiente("No hay Stock suficiente. Me pide  " + unidadesPed + " de "
                    + articulos.get(id).getDescripcion()
                    + " y sólo se dispone de: " + n);
        }
    }

    public String generaIdPedido(String idCliente) {
        int contador = 0;
        String nuevoId;
        for (Pedido p : pedidos) {
            if (p.getClientePedido().getDni().equalsIgnoreCase(idCliente)) {
                contador++;
            }
        }
        contador++;
        nuevoId = idCliente + "-" + String.format("%03d", contador) + "/" + LocalDate.now().getYear();
        return nuevoId;
    }

    public void nuevoPedido() {
        //ARRAYLIST AUXILIAR PARA CREAR EL PEDIDO
        ArrayList<LineaPedido> CestaCompraAux = new ArrayList();
        String dniT, idT, opc, pedidasS;
        Scanner sc = new Scanner(System.in);
        int pedidas = 0;
        sc.nextLine();
        do {
            System.out.println("CLIENTE PEDIDO (DNI):");
            dniT = sc.nextLine().toUpperCase();
            //EN CUALQUIER MOMENTO PODEMOS SALIR DEL BUCLE TECLEANDO RETORNO
            if (dniT.isBlank()) {
                break;
            }
            if (!MetodosAux.validarDNI(dniT) || !clientes.containsKey(dniT)) {
                System.out.println("El DNI no es válido O NO ES CLIENTE DE LA TIENDA");
            };
        } while (!clientes.containsKey(dniT));

        if (!dniT.isBlank()) {
            System.out.println("\t\tCOMENZAMOS CON EL PEDIDO");
            System.out.println("INTRODUCE CODIGO ARTICULO (RETURN PARA TERMINAR): ");
            idT = sc.nextLine();

            while (!idT.isEmpty()) {
                if (!articulos.containsKey(idT)) {
                    System.out.println("El ID articulo tecleado no existe");
                } else {
                    System.out.print("(" + articulos.get(idT).getDescripcion() + ") - UNIDADES? ");
                    do {
                        pedidasS = sc.nextLine();
                    } while (!MetodosAux.esInt(pedidasS));

                    pedidas = Integer.parseInt(pedidasS);

                    try {
                        stock(idT, pedidas); // LLAMO AL METODO STOCK, PUEDEN SALTAR 2 EXCEPCIONES
                        CestaCompraAux.add(new LineaPedido(idT, pedidas));
                        articulos.get(idT).setExistencias(articulos.get(idT).getExistencias() - pedidas);
                    } catch (StockAgotado e) {
                        System.out.println(e.getMessage());
                    } catch (StockInsuficiente e) {
                        System.out.println(e.getMessage());
                        int disponibles = articulos.get(idT).getExistencias();
                        System.out.print("QUIERES LAS " + disponibles + " UNIDADES DISPONIBLES? (S/N) ");
                        opc = sc.next();
                        if (opc.equalsIgnoreCase("S")) {
                            CestaCompraAux.add(new LineaPedido(idT, disponibles));
                            articulos.get(idT).setExistencias(articulos.get(idT).getExistencias() - disponibles);
                        }
                    }
                }
                System.out.println("INTRODUCE CODIGO ARTICULO (RETURN PARA TERMINAR): ");
                idT = sc.nextLine();
            }

            //IMPRIMO EL PEDIDO Y SOLICITO ACEPTACION DEFINITIVA DEL MISMO 
            for (LineaPedido l : CestaCompraAux) {
                System.out.println(articulos.get(l.getIdArticulo()).getDescripcion() + " - (" + l.getUnidades() + ")");
            }
            System.out.println("ESTE ES TU PEDIDO. PROCEDEMOS? (S/N)   ");
            opc = sc.next();
            if (opc.equalsIgnoreCase("S")) {
                // ESCRIBO EL PEDIDO DEFINITIVAMENTE Y DESCUENTO LAS EXISTENCIAS PEDIDAS DE CADA ARTICULO
                LocalDate hoy = LocalDate.now();
                pedidos.add(new Pedido(generaIdPedido(dniT), clientes.get(dniT), hoy, CestaCompraAux));
            } else {
                for (LineaPedido l : CestaCompraAux) {
                    articulos.get(l.getIdArticulo()).setExistencias(articulos.get(l.getIdArticulo()).getExistencias() + l.getUnidades());
                }
            }
        }
    }

    
//<editor-fold defaultstate="collapsed" desc="GESTION MENUS">
    
    public void menuPrincipal() {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Menu Articulo");
            System.out.println("2. Menu Pedido");
            System.out.println("3. Menu cliente");
            System.out.println("4. Listas");
            System.out.println("9. Salir");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    menuArticulo();
                    break;
                case 2:
                    menuPedido();
                    break;
                case 3:
                    menuCliente();
                    break;
                case 4:
                    menuListas();
                    break;
            }
        } while (opcion != 9);
        sc.close();
    }
    
    public void menuArticulo() {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- MENU ARTICULO ---");
            System.out.println("1. Agregar Articulo");
            System.out.println("2. Listar Articulos");
            System.out.println("3. Importar articulos por seccion");
            System.out.println("4. Backup articulos por seccion");
            System.out.println("9. Salir");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    
                    break;
                case 2:
                    listaArticulos();
                    break;
                case 3:
                    leerArchivosSeccion();
                    break;
                case 4:
                    backupPorSeccion();
                    break;
            }
        } while (opcion != 9);
        sc.close();
    }
    
    public void menuPedido() {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- MENU PEDIDO ---");
            System.out.println("1. Nuevo pedido");
            System.out.println("2. Listar Pedidos");
            System.out.println("9. Salir");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    nuevoPedido();
                    break;
                case 2:
                    listarPedidosPorTotal();
                    break;
                case 3:
                    
                    break;
                case 4:
                    
                    break;
            }
        } while (opcion != 9);
        sc.close();
    }
    
    public void menuCliente() {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- MENU CLIENTE ---");
            System.out.println("1. Agregar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. backup en archivo csv");
            System.out.println("4. Leer clientes en archivos");
            System.out.println("9. Salir");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    
                    break;
                case 2:
                    listaClientes();
                    break;
                case 3:
                    clientesTxtBackup();
                    break;
                case 4:
                    clientesTxtLeer();
                    break;
            }
        } while (opcion != 9);
        sc.close();
    }
    
    public void menuListas() {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- LISTAS ---");
            System.out.println("1. Lista pedidos ");
            System.out.println("2. Lista Articulo (Por precio)");
            System.out.println("3. Lista Cliente (Por nombre)");
            System.out.println("9. Salir");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    listaPedidos();
                    //listarPedidosPorTotal2();
                    break;
                case 2:
                    listaArticulos();
                    break;
                case 3:
                    listaClientes();
                    break;
                case 4:
                    
                    break;
            }
        } while (opcion != 9);
        sc.close();
    }
        
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="GESTION LISTAS">
    
    public void listaPedidos(){
        Collections.sort(pedidos);
        for (Pedido p : pedidos) {
            System.out.println(p);
        }
        System.out.println("");
    }  
    
    public void listaArticulos(){
        /*ArrayList<Articulo> articulosAux = new ArrayList (articulos.values());
        Collections.sort(articulosAux, new ComparaArticuloPorPrecio());*/
        for (Articulo a : articulos.values()) {
            System.out.println(a);
        }
        System.out.println("");
    }
    
    public void listaClientes(){
       /* ArrayList<Cliente> clientesAux = new ArrayList (clientes.values());
        Collections.sort(clientesAux, new ComparaClientesPorOrden());^*/
        for (Cliente c : clientes.values()) {
            System.out.println(c);
        }
    }
    
    public void listarPedidosPorTotal(){
        pedidos.stream().sorted().forEach(System.out::println);
        System.out.println("");
        articulos.values().stream().sorted().forEach(System.out::println);
        System.out.println("");
        articulos.values().stream().sorted(new ComparaArticulosPorExistencias()).forEach(System.out::println);
        System.out.println("");
        articulos.values().stream().sorted(new ComparaArticuloPorPrecio()).forEach(System.out::println);
    }
    
    public void listarPedidosPorTotal2(){
        
        Scanner sc = new Scanner(System.in);
        pedidos.stream().sorted(Comparator.comparing(p -> totalPedido(p)))
                .forEach(p -> System.out.println(p + "\t - IMPORTE TOTAL:" + totalPedido(p)));
        
        System.out.println("Teclea el nombre de un cliente para ver su pedido:");
        String nombre = sc.next().toUpperCase();
        pedidos.stream().filter(p -> p.getClientePedido().getNombre().equals(nombre))
                .filter(p -> totalPedido(p)>500)
                .sorted(Comparator.comparing(p -> totalPedido((Pedido) p)).reversed())
                .forEach(p -> System.out.println(p + "\t - IMPORTE TOTAL:" + totalPedido(p)));
        
        System.out.println("Teclea la seleccion");
        char s=sc.next().charAt(0);
        articulos.values().stream().filter(a->a.getIdArticulo().charAt(0)==s)
                .sorted(new ComparaArticuloPorPrecio()).forEach(System.out::println);
              
    }
    
//</editor-fold> 
    
    public double totalPedido(Pedido p)
    {
        double total=0;
        for (LineaPedido l:p.getCestaCompra())
        {
            total+=(articulos.get(l.getIdArticulo()).getPvp())
                    *l.getUnidades();
        }
        return total;
    }
    
    public void backup() {
        try (ObjectOutputStream oosArticulos = new ObjectOutputStream(new FileOutputStream("articulos.dat"));
            ObjectOutputStream oosClientes = new ObjectOutputStream(new FileOutputStream("clientes.dat"));
            ObjectOutputStream oosPedidos = new ObjectOutputStream(new FileOutputStream("pedidos.dat"))) 
        {
            for (Articulo a : articulos.values()) {
                oosArticulos.writeObject(a);
            }
            
            for (Cliente c : clientes.values()) {
                oosClientes.writeObject(c);
            }
            
            for (Pedido p : pedidos) {
                oosPedidos.writeObject(p);
            }
            System.out.println("Copia de seguridad realizada con exito");
        } 
        catch (FileNotFoundException e) {
                System.out.println(e.toString());
        } 
        catch (IOException e) {
                System.out.println(e.toString());
        }
    }
    
    public void leerArchivos() {
        try (ObjectInputStream oisArticulos = new ObjectInputStream(new FileInputStream("articulos.dat"))){
            Articulo a;
            while ( (a=(Articulo)oisArticulos.readObject()) != null){
                 articulos.put(a.getIdArticulo(), a);
            } 
	} catch (FileNotFoundException e) {
                 System.out.println(e.toString());    
        } catch (EOFException e){
            
        } catch (ClassNotFoundException | IOException e) {
                System.out.println(e.toString()); 
        } 
        
        try (ObjectInputStream oisClientes = new ObjectInputStream(new FileInputStream("clientes.dat"))){
            Cliente c;
            while ( (c=(Cliente)oisClientes.readObject()) != null){
                 clientes.put(c.getDni(), c);
            } 
	} catch (FileNotFoundException e) {
                 System.out.println(e.toString());    
        } catch (EOFException e){
            
        } catch (ClassNotFoundException | IOException e) {
                System.out.println(e.toString()); 
        }
        
        
        try (ObjectInputStream oisPedidos = new ObjectInputStream(new FileInputStream("pedidos.dat"))){
            Pedido p;
            while ( (p=(Pedido)oisPedidos.readObject()) != null){
                 pedidos.add(p);
            } 
	} catch (FileNotFoundException e) {
                 System.out.println(e.toString());    
        } catch (EOFException e){
            
        } catch (ClassNotFoundException | IOException e) {
                System.out.println(e.toString()); 
        }
       
    }   
    
    public void backupPorSeccion() {
        Scanner sc = new Scanner(System.in);
        try (ObjectOutputStream oosPerifericos = new ObjectOutputStream(new FileOutputStream("Perifericos.dat"));
            ObjectOutputStream oosAlmacenamiento = new ObjectOutputStream(new FileOutputStream("Almacenamiento.dat"));
            ObjectOutputStream oosImpresoras = new ObjectOutputStream(new FileOutputStream("Impresoras.dat"));
            ObjectOutputStream oosMonitores = new ObjectOutputStream (new FileOutputStream("Monitores.dat"))) {
	   	   
            for (Articulo a : articulos.values()) {
                char seccion=a.getIdArticulo().charAt(0);
                switch (seccion) {
                    case '1':
                        oosPerifericos.writeObject(a);
                        break;
                    case '2':
                        oosAlmacenamiento .writeObject(a);
                        break;
                    case '3':
                        oosImpresoras.writeObject(a);
                        break;
                    case '4':
                        oosMonitores.writeObject(a);
                        break;
                }
            }
            System.out.println("Copia de seguridad realizada con exito.");
	    
        } catch (FileNotFoundException e) {
                 System.out.println(e.toString());                                                          
        } catch (IOException e) {
                 System.out.println(e.toString());
        } 
        /* PARA COMPROBAR QUE FUNCIONA, VERIFICAMOS QUE SE HAN CREADO LOS 4 ARCHIVOS EN LA CARPETA
        RAÍZ DEL PROYECTO CON LA FECHA Y HORA ACTUAL - 
        ... Y PARA COMPROBAR EL CONTENIDO DE LOS ARCHIVOS LEEREMOS/IMPRIMIREMOS "AL VUELO" SÓLO 1 DE ELLOS
         CUYA SECCION SOLICITAMOS POR TECLADO
        */
                
        System.out.println("Teclea la Seccion de los articulos CUYO ARCHIVO QUIERES COMPROBAR:");        
        char seccion=sc.next().charAt(0);
        String nombreArchivo=null;
        switch (seccion) {
                    case '1':
                        nombreArchivo="Perifericos.dat";
                        break;
                    case '2':
                        nombreArchivo="Almacenamiento.dat";
                        break;
                    case '3':
                        nombreArchivo="Impresoras.dat";
                        break;
                    case '4':
                        nombreArchivo="Monitores.dat";
                        break;
        }
        Articulo a;
        try (ObjectInputStream oisArticulos = new ObjectInputStream(new FileInputStream(nombreArchivo))){
            while ( (a=(Articulo)oisArticulos.readObject()) != null){
                System.out.println(a);
            } 
        } catch (FileNotFoundException e) {
                 System.out.println(e.toString());    
        } catch (EOFException e){
            
        } catch (ClassNotFoundException | IOException e) {
                System.out.println(e.toString()); 
        } 
    }   
    
    /* METODO PARA LEER DESDE EL ARCHIVO articulos.dat SÓLO LOS ARTICULOS DE UNA DETERMINADA SECCION INTRODUCIDA POR TECLADO

       LOS ARTICULOS DE LA SECCION ELEGIDA SE VAN CARGANDO EN UN ARRAYLIST AUXILIAR (articulosAux) Y SE MUESTRAN POR PANTALLA
     */

    public void leerArchivosSeccion() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Teclea la Seccion de los articulos que quieres recuperar:");        
        String id=sc.next();
        ArrayList<Articulo> articulosAux= new ArrayList();
        Articulo a;
        
        try (ObjectInputStream oisArticulos = new ObjectInputStream(new FileInputStream("articulos.dat"))){
            while ( (a=(Articulo)oisArticulos.readObject()) != null){
                if (id.equals("5")){
                    articulosAux.add(a);
                }else if (a.getIdArticulo().startsWith(id)){
                    articulosAux.add(a);
                }
            } 
        } catch (FileNotFoundException e) {
                 System.out.println(e.toString());    
        } catch (EOFException e){
            
        } catch (ClassNotFoundException | IOException e) {
                System.out.println(e.toString()); 
        } 
        
        articulosAux.forEach(System.out::println);
    }
    
    public void clientesTxtBackup() {
        try(BufferedWriter bfwClientes=new BufferedWriter(new FileWriter("clientes.csv"))){
            for (Cliente c : clientes.values()) {
                bfwClientes.write(c.getDni() + "," + c.getNombre() + "," + c.getTelefono() + "," + c.getEmail() + "\n");
            }
        }catch (FileNotFoundException e) {
                 System.out.println(e.toString());   
        }catch(IOException e){
            System.out.println(e.toString());
        }
    }  
    
    public void clientesTxtLeer() {
        // LEEMOS LOS CLIENTES DESDE EL ARCHIVO .csv A UNA COLECCION HASHMAP AUXILIAR Y LA IMPRIMIMOS
        HashMap <String,Cliente> clientesAux = new HashMap();
        try(Scanner scClientes=new Scanner(new File("clientes.csv"))){
            while (scClientes.hasNextLine()){
                String [] atributos = scClientes.nextLine().split("[,]");                                                              
                Cliente c=new Cliente(atributos[0],atributos[1],atributos[2],atributos[3]); 
                clientesAux.put(atributos[0], c);
            }
        }catch(IOException e){
            System.out.println(e.toString());
        }
        clientesAux.values().forEach(System.out::println);
    } 
    
    public void cargaDatos() {
        
        clientes.put("80580845T", new Cliente("80580845T", "ANA ", "658111111", "ana@gmail.com"));
        clientes.put("36347775R", new Cliente("36347775R", "LOLA", "649222222", "lola@gmail.com"));
        clientes.put("63921307Y", new Cliente("63921307Y", "JUAN", "652333333", "juan@gmail.com"));
        clientes.put("02337565Y", new Cliente("02337565Y", "EDU", "634567890", "edu@gmail.com"));

        articulos.put("1-11", new Articulo("1-11", "RATON LOGITECH ST ", 14, 15));
        articulos.put("1-22", new Articulo("1-22", "TECLADO STANDARD  ", 9, 18));
        articulos.put("2-11", new Articulo("2-11", "HDD SEAGATE 1 TB  ", 16, 80));
        articulos.put("2-22", new Articulo("2-22", "SSD KINGSTOM 256GB", 9, 70));
        articulos.put("2-33", new Articulo("2-33", "SSD KINGSTOM 512GB", 0, 200));
        articulos.put("3-22", new Articulo("3-22", "EPSON PRINT XP300 ", 5, 80));
        articulos.put("4-11", new Articulo("4-11", "ASUS  MONITOR  22 ", 5, 100));
        articulos.put("4-22", new Articulo("4-22", "HP MONITOR LED 28 ", 5, 180));
        articulos.put("4-33", new Articulo("4-33", "SAMSUNG ODISSEY G5", 12, 580));

        LocalDate hoy = LocalDate.now();
        pedidos.add(new Pedido("80580845T-001/2024", clientes.get("80580845T"), hoy.minusDays(1), new ArrayList<>(List.of(new LineaPedido("1-11", 3), new LineaPedido("4-22", 3)))));
        pedidos.add(new Pedido("80580845T-002/2024", clientes.get("80580845T"), hoy.minusDays(2), new ArrayList<>(List.of(new LineaPedido("4-11", 3), new LineaPedido("4-22", 2), new LineaPedido("4-33", 4)))));
        pedidos.add(new Pedido("36347775R-001/2024", clientes.get("36347775R"), hoy.minusDays(3), new ArrayList<>(List.of(new LineaPedido("4-22", 1), new LineaPedido("2-22", 3)))));
        pedidos.add(new Pedido("36347775R-002/2024", clientes.get("36347775R"), hoy.minusDays(5), new ArrayList<>(List.of(new LineaPedido("4-33", 3), new LineaPedido("2-11", 3)))));
        pedidos.add(new Pedido("63921307Y-001/2024", clientes.get("63921307Y"), hoy.minusDays(4), new ArrayList<>(List.of(new LineaPedido("2-11", 5), new LineaPedido("2-33", 3), new LineaPedido("4-33", 2)))));
    }
}

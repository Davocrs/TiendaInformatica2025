/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package davidceducastur.es.tiendainformatica;

/**
 *
 * @author alu03d
 */
    
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
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class Repaso {
    
    Scanner sc=new Scanner(System.in);
    private ArrayList<Pedido> pedidos;
    private HashMap <String, Articulo> articulos;
    private HashMap <String, Cliente> clientes;
   
    public Repaso() {
        pedidos = new ArrayList();
        articulos= new HashMap();
        clientes = new HashMap();
    }
    
    public static void main(String[] args) {
        Repaso t=new Repaso();
        t.cargaDatos();
        t.apiStreams();
        //t.backupPedidosClientes();
        //t.pedidosOrdenadosPorArticuloVendido();
        //t.listarArticulos();
        //t.articuloUsuariosLoHanComprado();
        //t.perdidosOrdenadosPorImporte();
        //t.clientesOrdenadosPorGasto();
        //t.examenPara7yMedio();
        //t.examenPara10();
    }

    public void cargaDatos(){
       clientes.put("80580845T",new Cliente("80580845T","ANA ","658111111","ana@gmail.com"));
       clientes.put("36347775R",new Cliente("36347775R","ANTONIO","649222222","antonio@gmail.com"));
       clientes.put("63921307Y",new Cliente("63921307Y","AURORA","652333333","aurora@gmail.com"));
       clientes.put("53472775R",new Cliente("53472775R","EMILIO","649222222","emilio@gmail.com"));
       clientes.put("43211307Y",new Cliente("43211307Y","EVA","652333333","eva@gmail.com"));
       clientes.put("02337565Y",new Cliente("02337565Y","MATEO","634567890","mateo@gmail.com"));
       clientes.put("35445638M",new Cliente("35445638M","MARIA","633478990","maria@gmail.com"));
     
       articulos.put("1-11",new Articulo("1-11","RATON LOGITECH ST ",14,15));
       articulos.put("1-22",new Articulo("1-22","TECLADO STANDARD  ",9,18));
       articulos.put("2-11",new Articulo("2-11","HDD SEAGATE 1 TB  ",16,80));
       articulos.put("2-22",new Articulo("2-22","SSD KINGSTOM 256GB",9,70));
       articulos.put("2-33",new Articulo("2-33","SSD KINGSTOM 512GB",0,200));
       articulos.put("3-22",new Articulo("3-22","EPSON PRINT XP300 ",5,80));
       articulos.put("4-11",new Articulo("4-11","ASUS  MONITOR  22 ",5,100));
       articulos.put("4-22",new Articulo("4-22","HP MONITOR LED 28 ",5,180));
       articulos.put("4-33",new Articulo("4-33","SAMSUNG ODISSEY G5",12,580));
       
       LocalDate hoy = LocalDate.now();
       pedidos.add(new Pedido("80580845T-001/2024",clientes.get("80580845T"),hoy.minusDays(1), new ArrayList<>
        (List.of(new LineaPedido("1-11",3),new LineaPedido("4-22",3)))));                                                                                                                                                               
       pedidos.add(new Pedido("80580845T-002/2024",clientes.get("80580845T"),hoy.minusDays(2), new ArrayList<>
        (List.of(new LineaPedido("4-11",3),new LineaPedido("4-22",2),new LineaPedido("4-33",4)))));
       pedidos.add(new Pedido("36347775R-001/2024",clientes.get("36347775R"),hoy.minusDays(3), new ArrayList<>
        (List.of(new LineaPedido("4-22",1),new LineaPedido("2-22",3)))));
       pedidos.add(new Pedido("36347775R-002/2024",clientes.get("36347775R"),hoy.minusDays(5), new ArrayList<>
        (List.of(new LineaPedido("4-33",3),new LineaPedido("2-11",3)))));
       pedidos.add(new Pedido("63921307Y-001/2024",clientes.get("63921307Y"),hoy.minusDays(4), new ArrayList<>
        (List.of(new LineaPedido("2-11",5),new LineaPedido("2-33",3),new LineaPedido("4-33",2)))));
       pedidos.add(new Pedido("53472775R-001/2025",clientes.get("53472775R"),hoy, new ArrayList<>
        (List.of(new LineaPedido("1-11",2),new LineaPedido("2-11",2)))));
       pedidos.add(new Pedido("43211307Y-001/2025",clientes.get("43211307Y"),hoy, new ArrayList<>
        (List.of(new LineaPedido("4-33",1)))));
     } 
    
    //<editor-fold defaultstate="collapsed" desc="PRUEBA EVALUABLE PERSISTENCIA 26-2-25 ">
    /************************************************************************************* 
    ÚLTIMA PRUEBA EVALUABLE ANTES DE LAS PRÁCTICAS EN EMPRESA 
    **************************************************************************************/
    
    public void examenPara7yMedio(){
        ArrayList<Cliente> clientesSin = new ArrayList();
        ArrayList<Cliente> clientesCon = new ArrayList();
               
        try(BufferedWriter bfwClientesCon=new BufferedWriter(new FileWriter("clientesCon.csv"));
            BufferedWriter bfwClientesSin=new BufferedWriter(new FileWriter("clientesSin.csv"))    ){
            
            for (Cliente c : clientes.values()) {
                /* ESTILO CLÁSICO 
                boolean tienePedido = false;
                for (Pedido p : pedidos) {
                    if (p.getClientePedido() == c) {
                        tienePedido = true;
                        break;
                    }
                } 
                if (tienePedido) {
                   bfwClientesCon.write(c.getDni() + "," + c.getNombre() + "," + c.getTelefono() + "," + c.getEmail() + "\n");
                } else{
                   bfwClientesSin.write(c.getDni() + "," + c.getNombre() + "," + c.getTelefono() + "," + c.getEmail() + "\n"); clientesSin.add(c);
                }*/

                /* CON STREAMS Y EL METODO anyMatch */
                if (pedidos.stream().anyMatch(p-> p.getClientePedido().equals(c))){
                   bfwClientesCon.write(c.getDni() + "," + c.getNombre() + "," + c.getTelefono() + "," + c.getEmail() + "\n");
                } else{
                   bfwClientesSin.write(c.getDni() + "," + c.getNombre() + "," + c.getTelefono() + "," + c.getEmail() + "\n");
                }
            }
        }catch (FileNotFoundException e) {
                 System.out.println(e.toString());   
        }catch(IOException e){
            System.out.println(e.toString());
        }
 
        try(Scanner scClientesCon=new Scanner(new File("clientesCon.csv"))){
            while (scClientesCon.hasNextLine()){
                String [] atributos = scClientesCon.nextLine().split("[,]");                                                              
                Cliente c=new Cliente(atributos[0],atributos[1],atributos[2],atributos[3]); 
                clientesCon.add(c);
            }
        }catch(IOException e){
            System.out.println(e.toString());
        }
        
        try(Scanner scClientesSin=new Scanner(new File("clientesSin.csv"))){
            while (scClientesSin.hasNextLine()){
                String [] atributos = scClientesSin.nextLine().split("[,]");                                                              
                Cliente c=new Cliente(atributos[0],atributos[1],atributos[2],atributos[3]); 
                clientesSin.add(c);
            }
        }catch(IOException e){
            System.out.println(e.toString());
        }
        System.out.println("\nLISTADOS EXAMEN VERSIÓN 7,5 PUNTOS:");
        System.out.println("\nCLIENTES CON PEDIDOS:");
        clientesCon.forEach(System.out::println);
        System.out.println("\nCLIENTES SIN PEDIDOS:");
        clientesSin.forEach(System.out::println);
    }
    
    public void examenPara10(){
        ArrayList<Cliente> clientesSin = new ArrayList();
        ArrayList<Cliente> clientesCon = new ArrayList();
        ArrayList<Cliente> clientesMas1000 = new ArrayList();
               
        try(BufferedWriter bfwClientesCon=new BufferedWriter(new FileWriter("clientesCon.csv"));
            BufferedWriter bfwClientesSin=new BufferedWriter(new FileWriter("clientesSin.csv"));
            BufferedWriter bfwClientesMas1000=new BufferedWriter(new FileWriter("clientesMas1000.csv")))
        {
        /* USAMOS EL MÉTODO totalCliente(c) PARA CALCULAR EL TOTAL GASTADO POR UN CLIENTE EN TODOS SUS PEDIDOS
           DESPUÉS VEREMOS ALTERNATIVAS CON PROGRAMACIÓN FUNCIONAL PARA DESARROLLAR ESTE MÉTODO
            
           EN ESTA VERSION DEL EJERCICIO SABEMOS QUE UN CLIENTE CON GASTO>1000 O  >0 TIENE PEDIDOS, EL RESTO NO
        */    
            for (Cliente c : clientes.values()){
                if (totalCliente(c)==0){
                    bfwClientesSin.write(c.getDni() + "," + c.getNombre() + "," + c.getTelefono() + "," + c.getEmail() + "\n");
                }else if (totalCliente(c)>=1000){
                      bfwClientesCon.write(c.getDni() + "," + c.getNombre() + "," + c.getTelefono() + "," + c.getEmail() + "\n");
                      bfwClientesMas1000.write(c.getDni() + "," + c.getNombre() + "," + c.getTelefono() + "," + c.getEmail() + "\n");
                    }else {
                       bfwClientesCon.write(c.getDni() + "," + c.getNombre() + "," + c.getTelefono() + "," + c.getEmail() + "\n");
                    }
            }
            /* EXACTAMENTE LO MISMO SE PUEDE HACER ESTRUCTURANDO LAS CONDICIONES DE OTRA FORMA     
                if (totalCliente(c)>0){
                    bfwClientesCon.write(c.getDni() + "," + c.getNombre() + "," + c.getTelefono() + "," + c.getEmail() + "\n");
                    bfwClientesMas1000.write(c.getDni() + "," + c.getNombre() + "," + c.getTelefono() + "," + c.getEmail() + "\n");
                   
                }else if (totalCliente(c)>0){
                       bfwClientesCon.write(c.getDni() + "," + c.getNombre() + "," + c.getTelefono() + "," + c.getEmail() + "\n");
                    }else {
                        bfwClientesSin.write(c.getDni() + "," + c.getNombre() + "," + c.getTelefono() + "," + c.getEmail() + "\n");
                    }
            */
            
        }catch (FileNotFoundException e) {
                 System.out.println(e.toString());   
        }catch(IOException e){
            System.out.println(e.toString());
        }
 
        try(Scanner scClientesCon=new Scanner(new File("clientesCon.csv"))){
            while (scClientesCon.hasNextLine()){
                String [] atributos = scClientesCon.nextLine().split("[,]");                                                              
                Cliente c=new Cliente(atributos[0],atributos[1],atributos[2],atributos[3]); 
                clientesCon.add(c);
            }
        }catch(IOException e){
            System.out.println(e.toString());
        }
        
        try(Scanner scClientesSin=new Scanner(new File("clientesSin.csv"))){
            while (scClientesSin.hasNextLine()){
                String [] atributos = scClientesSin.nextLine().split("[,]");                                                              
                Cliente c=new Cliente(atributos[0],atributos[1],atributos[2],atributos[3]); 
                clientesSin.add(c);
            }
        }catch(IOException e){
            System.out.println(e.toString());
        }
        
        try(Scanner scClientesSin=new Scanner(new File("clientesMas1000.csv"))){
            while (scClientesSin.hasNextLine()){
                String [] atributos = scClientesSin.nextLine().split("[,]");                                                              
                Cliente c=new Cliente(atributos[0],atributos[1],atributos[2],atributos[3]); 
                clientesMas1000.add(c);
            }
        }catch(IOException e){
            System.out.println(e.toString());
        }
        System.out.println("\nLISTADOS EXAMEN VERSIÓN 10 PUNTOS:");
        System.out.println("\nCLIENTES CON PEDIDOS:");
        clientesCon.forEach(System.out::println);
        System.out.println("\nCLIENTES SIN PEDIDOS:");
        clientesSin.forEach(System.out::println);
        System.out.println("\nCLIENTES CON MAS DE 1000€ GASTADOS:");
        clientesMas1000.forEach(System.out::println);
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="CLASES DE RECUPERACION">
    /*************************************************************************************
    CLASES DE RECUPERACIÓN
    DADO UN ID DE ARTICULO, ORDENAR LOS PEDIDOS DE MAYOR A MENOR NÚMERO DE UNIDADES VENDIDAS DE ESE ARTICULO 
    **************************************************************************************/
    
    public void pedidosOrdenadosPorArticuloVendido(){
        String id;
        do{
            System.out.println("Teclea idArticulo para contabilizar en pedidos:");
            id=sc.next();
        }while(!articulos.containsKey(id));
        
        System.out.println("Unidades vendidas del artículo: " + articulos.get(id).getDescripcion());
       
        final String id2 = id;
        pedidos.stream().sorted(Comparator.comparing(p-> articuloEnPedido2(id2, (Pedido)p)).reversed()).
                forEach(p-> System.out.println("Pedido " +p.getIdPedido() + "-" + p.getFechaPedido()  
                     + " : " + articuloEnPedido2(id2,p) + " unidades"  ));
    }  
      
    /* METODO articuloEnPedido CLÁSICO */
    public int articuloEnPedido(String idArticulo, Pedido p){
       int contador=0;
       for (LineaPedido l:p.getCestaCompra()){
           if (l.getIdArticulo().equals(idArticulo)){
               contador+=l.getUnidades();
               break;
           }
       }
       return contador;
    }
    
    /* MÉTODO articuloEnPedido PROGRAMACIÓN FUNCIONAL */
         
    public int articuloEnPedido2(String idArticulo, Pedido p){
        return p.getCestaCompra().stream().filter(l->l.getIdArticulo().equals(idArticulo))
            .mapToInt(LineaPedido::getUnidades).sum();
    }
    
    
    
    
    /****************************************************************************************
        EJERCICIO - LISTADO DE USUARIOS QUE HAN COMPRADO UN ARTÍCULO DETERMINADO 
                        
        IMPORTANTE TENER EN CUENTA QUE UN USUARIO PUEDE HABER COMPRADO EL MISMO ARTÍCULO EN PEDIDOS
        DISTINTOS Y NO DEBEN DE SALIR VARIAS LÍNEAS EN EL LISTADO PARA ESE USUARIO/A. 
        DEBE DE SALIR UNA ÚNICA LÍNEA CON EL TOTAL DE UNIDADES DEL ARTÍCULO COMPRADAS POR EL USUARIO
        ESTO ES UNA COMPLEJIDAD PARA EL EJERCICIO 
    *****************************************************************************************/
    public void articuloUsuariosLoHanComprado(){
        String id;
        do{
            System.out.println("Teclea idArticulo para contabilizar en pedidos:");
            id=sc.next();
        }while(!articulos.containsKey(id));
        
        System.out.println("Usuarios que han comprado el articulo: " + articulos.get(id).getDescripcion());
        
        /* METODO CLÁSICO 
        for (Cliente c:clientes.values()){
            int unidades=0;
            for (Pedido p:pedidos){
                if (p.getClientePedido().equals(c)){
                    for(LineaPedido l:p.getCestaCompra()){
                        if (l.getIdArticulo().equals(id)){
                            unidades+= l.getUnidades();
                        }
                    }
                }
            }
            if (unidades>0){
                System.out.println(c.getNombre()+
                  " ha comprado " + unidades + " unidades") ;
            }
        }*/
        
        // STREAMS 
        final String id2=id;
        for (Cliente c:clientes.values()){
            int unidades= pedidos.stream().filter(p-> p.getClientePedido().equals(c))
                    .mapToInt(p -> p.getCestaCompra().stream().filter(l->l.getIdArticulo().equals(id2))
                        .mapToInt(LineaPedido::getUnidades).sum()).sum();     
            
            if (unidades>0){
                System.out.println(c.getNombre() + ": " + unidades);
            }
        }                   
    }
    
    /**************************************************************************************
    ALGUNAS ORDENACIONES RELIZADAS CON COMPARATORS APOYADAS EN LOS METODOS totalPedido() totalCliente() 
    ***************************************************************************************/
    
    
    public void perdidosOrdenadosPorImporte(){
        pedidos.stream().sorted(Comparator.comparing(p->totalPedido((Pedido) p))
            .reversed()).forEach(p-> System.out.println(p.getIdPedido() + ":\t "+ totalPedido(p)));
    }
    
    public void clientesOrdenadosPorGasto(){
        clientes.values().stream().sorted(Comparator.comparing(c->totalCliente((Cliente) c))
            .reversed()).forEach(c-> System.out.println(c.getNombre() + ":\t "+ totalCliente(c)));
    }
    
    public double totalCliente(Cliente c){
        /* VERSIÓN CLÁSICA
        double total=0;
        for(Pedido p:pedidos){
            if (p.getClientePedido().equals(c)){
                total+=totalPedido(p);
            }
        }
        return total;*/
        
        /*VERSIÓN PROGRAMACIÓN FUNCIONAL */
        return pedidos.stream().filter(p-> p.getClientePedido().equals(c))
                .mapToDouble(p -> totalPedido(p)).sum();
    }
    
   
    public double totalPedido(Pedido p)
    {
        /* VERSIÓN CLÁSICA
        double total=0;
        for (LineaPedido l:p.getCestaCompra())
        {
            total+=(articulos.get(l.getIdArticulo()).getPvp())
                    *l.getUnidades();
        }
        return total;*/
        
         /*VERSIÓN PROGRAMACIÓN FUNCIONAL */
        return p.getCestaCompra().stream()
                .mapToDouble(l-> articulos.get(l.getIdArticulo()).getPvp()
                    *l.getUnidades()).sum();
    }    
    
    //ALTERNATIVA
    //TOTAL POR CLIENTE TODO EN UN MISMO MÉTODO, SIN NECESIDAD DE UTILIZAR totalPedido
    public double totalCliente2(Cliente c){
        return pedidos.stream().filter(p -> p.getClientePedido().equals(c))
            .mapToDouble(p -> p.getCestaCompra().stream()
            .mapToDouble(lp -> lp.getUnidades() * articulos.get(lp.getIdArticulo())
                    .getPvp()).sum()).sum();
    }
    
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="LISTADOS">
     /* EJERCICIO CON STREAMS DE LISTADOS DE ARTÍCULOS POR SECCIÓN */
    public void listarArticulos() {
        Scanner sc=new Scanner(System.in);
        String opcion;
        do{
            System.out.println("\n\n\n\n\n\t\t\t\tLISTAR ARTICULOS\n");
            System.out.println("\t\t\t\t0 - TODOS LOS ARTICULOS");
            System.out.println("\t\t\t\t1 - PERIFERICOS");
            System.out.println("\t\t\t\t2 - ALMACENAMIENTO");
            System.out.println("\t\t\t\t3 - IMPRESORAS");
            System.out.println("\t\t\t\t4 - MONITORES");
            System.out.println("\t\t\t\t9 - SALIR");
            do
                opcion=sc.next();
            while (!opcion.matches("[0-4,9]"));
            if (opcion!="9"){
                listados(opcion);
            }
        }while (!opcion.equals("9"));
    }
    public void listados (String seccion){
        String[] secciones={"TODAS","PERIFERICOS","ALMACENAMIENTO","IMPRESORAS","MONITORES"};
        int s=Integer.parseInt(seccion);
        if (seccion.equals("0")){ 
            System.out.println("LISTADO ARTICULOS DE LA SECCION: " + secciones[s]);
            articulos.values().stream().forEach(System.out::println);
        } else{
            System.out.println("LISTADO ARTICULOS DE LA SECCION: " + secciones[s]);
            articulos.values().stream().filter(a -> a.getIdArticulo().startsWith(seccion))
                    .forEach(System.out::println);
        }
    }
    
     /* PARA HACER LISTADOS ORDENADOS TAN SOLO HAY QUE AÑADIR .sorted() al stream()
    
    Si no le pasamos argumento al sorted() Java buscará en la clase Articulo a ver si hemos 
    implementado el interface Comparable y usará el criterio que hayamos programado en el método
    public int compareTo(Articulo a)
    
    Si creamos clases Comparator propias podemos usarlas para definir nuestras propias ordenaciones
    o bien utilizar el método Comparator.comparing(predicado)
    
    También podemos cambiar el sentido de las ordenaciones con .reversed();
    
    LAS POSIBILIDADES SON ILIMITADAS Y SE TRATA DE PROBAR, PROBAR Y PROBAR
    */
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="PERSISTENCIA - 1 ARCHIVO POR CLIENTE CON SUS PEDIDOS">
    /*************************************************************************************
    * EJERCICIO DE BACKUPS DE PEDIDOS POR CLIENTE - 1 ARCHIVO DE PEDIDOS POR CADA CLIENTE
    ***************************************************************************************/
    
    public void backupPedidosClientes() {
        boolean tienePedidos; 
        for (Cliente c:clientes.values()){
            tienePedidos=false;       
            for (Pedido p: pedidos ){
                if(p.getClientePedido().equals(c)){
                    tienePedidos=true;
                    break;
                }
            }
            if (tienePedidos){
                String archivo="PedidosCliente_" + c.getNombre()+".dat";
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo)))
                {
                   for (Pedido p: pedidos ){
                        if(p.getClientePedido().equals(c)) {
                            oos.writeObject(p);
                        }
                   }
                } catch (IOException e) {
                   System.out.println(e.toString());
                } 
                
            }
        }
        System.out.println("ARCHIVOS CREADOS CORRECTAMENTE\n");
        
        /*AHORA SOLICITAMOS EL DNI DE UN CLIENTE PARA MOSTRAR SUS PEDIDOS
        DESDE EL ARCHIVO .dat CORRESPONDIENTE
        */ 
        String dniT; 
        //NO PERMITIMOS ENTRADA DE DNIs NO VÁLIDOS O QUE NO ESTÁN EN LA TIENDA
        do{
            System.out.println("DNI CLIENTE:");
            dniT=sc.next().toUpperCase();    
        }while (!clientes.containsKey(dniT)||!MetodosAux.validarDNI(dniT));
        
        //COMPROBAMOS AHORA SI EL DNI TIENE PEDIDOS.
        //SI NO LOS TIENE NO SE CREÓ SU ARCHIVO
        tienePedidos=false;       
        for (Pedido p: pedidos ){
            if(p.getClientePedido().equals(clientes.get(dniT))) {
                tienePedidos=true;
                break;
            }
        }
        
        if (tienePedidos){
            String archivo="PedidosCliente_" + clientes.get(dniT).getNombre()+".dat";
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo)))
            {
                Pedido p=null;
                while ( (p=(Pedido)ois.readObject()) != null){
                     System.out.println("\nPEDIDO: " + p.getIdPedido() + " DE: " + p.getClientePedido().getNombre());
                     for (LineaPedido l:p.getCestaCompra()){
                         System.out.println(articulos.get(l.getIdArticulo()).getDescripcion()
                                 + "\t Unidades: " +l.getUnidades());
                     }
                } 
            } catch (EOFException e) {
                System.out.println("Fin archivo");
            } catch (IOException e) {
                System.out.println("No existen pedidos para ese DNI");
            } catch (ClassNotFoundException ex) {
            }
        }
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="EJERCICIOS STREAMS Collect">
      /******************************************
    EJEMPLOS VARIOS DE USO API STREAMS .collect
    *******************************************/  
    
    public void apiStreams(){
                   
        /* .collect() PERMITE "RECOLECTAR" LOS RESULTADOS DE PROCESAR LOS OBJETOS DE UN STREAM
        Y ALMACENARLOS EN CUALQUIER OTRA COLECCIÓN LIST-SET-MAP DESPUES DE HACERLES CUALQUIER
        TIPO DE PROCESAMIENTO QUE PODAMOS IMAGINAR. 
        
            .collect(Collectors.toList())
            .collect(Collectors.toSet())
            .collect(Collectors.toMap())
        
        utilizando  .map()
        LOS OBJETOS LOS PODREMOS "RECOLECTAR" COMPLETOS, O SÓLO EL ATRIBUTO O PARTE DEL ATRIBUTO
        QUE NOS INTERESE, ADEMÁS DE PODER AÑADIR A LA NUEVA COLECCIÓN VALORES CALCULADOS "A MAYORES"
        */
           
        /*****************************************************************************************
        EJEMPLO1 : ¿Cuántos artículos hay en cada Sección?
        ******************************************************************************************
        
        UTILIZANDO EL MÉTODO .Collectors.groupingBy() agrupo los articulos por secciones
        y para saber cuantos hay de cada seccion "cuenteo" el agrupamiento con Collectors.counting()

        previamente aislamos/mapeamos (map) la sección de cada articulo (primer caracter del atributo idArticulo)
        */
        
        Map<Character,Long> articulosEnSeccion= articulos.values().stream()
               .map(a->a.getIdArticulo().charAt(0))
               .collect(Collectors.groupingBy(a->a,Collectors.counting()));
        
        System.out.println(articulosEnSeccion);
        System.out.println("");
        
        /*EN PROGRAMACIÓN CLÁSICA PODEMOS OBTENER LOS MISMOS RESULTADOS
        PERO ES MÁS COMPLICADO YA QUE EL HASHMAP DE ARTICULOS NO ESTÁ ORDENADO
        Y NECESITAMOS USAR UNA LISTA AUXILIAR*/
        
        List <Articulo> articulosOrd= articulos.values().stream()
                .sorted(Comparator.comparing(Articulo::getIdArticulo))
                .collect(Collectors.toList());
        
        //UNA VEZ ORDENADA LA LISTA VAMOS CONTABILIZANDO SECCIONES
        HashMap<Character,Long> articulosEnSeccion2=new HashMap();
        char seccn='1';
        long total=0;
        for (Articulo a:articulosOrd){
            if ((a.getIdArticulo().charAt(0)==seccn)){
                total++;
            }else {
                articulosEnSeccion2.put(seccn,total);
                seccn=a.getIdArticulo().charAt(0);
                total=1;
            }      
        }
        //PARA ESCRIBIR EN EL HASHMAP LA ÚLTIMA SECCIÓN O SINO LA PERDERÍAMOS
        articulosEnSeccion2.put(seccn,total);
        
        
        for (char s:articulosEnSeccion2.keySet()){
            System.out.println("En la SECCION: " + s + " hay "
                    + articulosEnSeccion2.get(s) + " articulos");
        }
        
        /********************************************************************************************** 
        EJEMPLO2 : ¿Cuál es el artículo más barato y más caro de una sección introducida por teclado? 
        ***********************************************************************************************/
        
        String seccion;
        do{
            System.out.println("Teclea la Seccion para conocer articulo mas caro y mas barato:");
            seccion=sc.next();
        }while(!seccion.matches("[1-4]"));
        
        
        //TODO CON MÉTODOS DEL API DE STREAMS
        
        final String sec=seccion;
        var max= articulos.values().stream()
               .filter(a->a.getIdArticulo().startsWith(sec))
               .collect(Collectors.maxBy(Comparator.comparing(Articulo::getPvp)));
        
        var min= articulos.values().stream()
               .filter(a->a.getIdArticulo().startsWith(sec))
               .collect(Collectors.minBy(Comparator.comparing(Articulo::getPvp)));
        
        System.out.println(min);
        System.out.println(max);
        
        /* ESTILO CLASICO-MIXTO
        PODEMOS CREAR UN LISTA CON LOS ARTÍCULOS DE ESA SECCIÓN, ORDENARLA POR PRECIO CON UN COMPARATOR
        (OJO PORQUE EL COMPARATOR HAY QUE CREARLO A PARTE)
        Y TOMAR COMO MÁXIMO EL ÚLTIMO Y MÍNIMO EL 1º
        */ 
                    
        List<Articulo> listaMinMax = articulos.values().stream()
             .filter(a->a.getIdArticulo().startsWith(sec))
             .collect(Collectors.toList());
        
        Collections.sort(listaMinMax, new ComparaArticuloPorPrecio());
        System.out.println("");
        System.out.println(listaMinMax.getFirst());
        System.out.println(listaMinMax.getLast());
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="EJERCICIOS EXAMEN">
    
    // 1. Devuelve un Map<String, Double> con el DNI de cada cliente y su gasto total.
    public Map<String, Double> gastoTotalPorCliente() {
    return clientes.values().stream().collect(Collectors.toMap(Cliente::getDni,c -> totalCliente(c)
        ));
    }
    
    // 2. Guarda en clientesConPedidos.csv solo los clientes que han hecho al menos un pedido.
    public void guardarClientesConPedidosCSV() {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter("clientesConPedidos.csv"))) {
        clientes.values().stream()
            .filter(c -> pedidos.stream().anyMatch(p -> p.getClientePedido().equals(c)))
            .forEach(c -> {
                try {
                    bw.write(c.getDni() + "," + c.getNombre() + "," + c.getTelefono() + "," + c.getEmail());
                    bw.newLine();
                } catch (IOException e) {

                }
            });
        System.out.println("Clientes con pedidos guardados.");
    } catch (IOException e) {
        
     }
    }
    
    // 3. Devuelve el total de dinero gastado por todos los clientes en todos los pedidos.
    public double totalFacturadoTienda() {
    return pedidos.stream()
        .mapToDouble(p -> totalPedido(p))
        .sum();
    }
    
    // 4. Muestra todos los pedidos ordenados de mayor a menor importe.
    public void pedidosOrdenadosPorImporte() {
    pedidos.stream()
        .sorted(Comparator.comparing(p -> totalPedido((Pedido) p)).reversed())
        .forEach(p -> System.out.println(p.getIdPedido() + ": " + totalPedido(p)));
    }
    
    // 5. Guarda en un archivo clientesMas1000.csv los clientes que hayan gastado más de 1000€
    public void guardarClientesMas1000CSV() {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter("clientesMas1000.csv"))) {
        clientes.values().stream()
            .filter(c -> totalCliente(c) > 1000)
            .forEach(c -> {
                try {
                    bw.write(c.getDni() + "," + c.getNombre() + "," + c.getTelefono() + "," + c.getEmail());
                } catch (IOException e) {
                    System.out.println("Error al escribir: " + e.getMessage());
                }
            });
    } catch (IOException e) {
        System.out.println("Error general: " + e.getMessage());
    }
    }
    
    // 6. Devuelve el total gastado en monitores (idArticulo empieza por "4")
    public double totalGastoEnMonitores() {
    double total = 0;
    for (Pedido p : pedidos) {
        for (LineaPedido l : p.getCestaCompra()) {
            if (l.getIdArticulo().startsWith("4")) {
                Articulo a = articulos.get(l.getIdArticulo());
                total += a.getPvp() * l.getUnidades();
            }
        }
    }
    return total;
    }
  
    // 7. Muestra los clientes ordenados por el número total de artículos que han comprado (no el dinero)
    public void clientesOrdenadosPorUnidadesCompradas() {
    List<Cliente> lista = new ArrayList<>(clientes.values());

    lista.sort(Comparator.comparingInt(c -> {
        int total = 0;
        for (Pedido p : pedidos) {
            if (p.getClientePedido().equals(c)) {
                for (LineaPedido l : p.getCestaCompra()) {
                    total += l.getUnidades();
                }
            }
        }
        return -total; //
    }));

    for (Cliente c : lista) {
        int total = 0;
        for (Pedido p : pedidos) {
            if (p.getClientePedido().equals(c)) {
                for (LineaPedido l : p.getCestaCompra()) {
                    total += l.getUnidades();
                }
            }
        }
        System.out.println(c.getNombre() + " - unidades: " + total);
    }
    }

    // 8. Crea un Map<String, Integer> con el nombre del cliente y el número total de artículos comprados
    public Map<String, Integer> mapaNombreConUnidades() {
    Map<String, Integer> resultado = new HashMap<>();

    for (Cliente c : clientes.values()) {
        int total = 0;
        for (Pedido p : pedidos) {
            if (p.getClientePedido().equals(c)) {
                for (LineaPedido l : p.getCestaCompra()) {
                    total += l.getUnidades();
                }
            }
        }
        resultado.put(c.getNombre(), total);
    }

    return resultado;
    }

    // 9. Mostrar los clientes que han hecho pedidos, ordenados por nombre alfabéticamente.
    public void mostrarClientesConPedidosOrdenadosPorNombre() {
    clientes.values().stream().filter(c -> pedidos.stream().anyMatch(p -> p.getClientePedido().equals(c)))
        .sorted(Comparator.comparing(Cliente::getNombre)).forEach(System.out::println);
    }
    
    // 10. Devuelve el número total de unidades vendidas de artículos de la sección 4 (monitores)
    public int totalUnidadesVendidasSeccion4() {
    int total = 0;
    for (Pedido p : pedidos) {
        for (LineaPedido l : p.getCestaCompra()) {
            if (l.getIdArticulo().startsWith("4")) {
                total += l.getUnidades();
            }
        }
    }
    return total;
    }
    
    // 11. Muestra los artículos que no tienen existencias y ordénalos por precio descendente.
    public void mostrarArticulosSinStockOrdenados() {
    articulos.values().stream()
        .filter(a -> a.getExistencias() == 0)
        .sorted(Comparator.comparingDouble(Articulo::getPvp).reversed())
        .forEach(System.out::println);
    }
    
    // 12. Devuelve una lista con los clientes que nunca han hecho ningún pedido.
    public List<Cliente> clientesSinPedidos() {
    return clientes.values().stream()
        .filter(c -> pedidos.stream().noneMatch(p -> p.getClientePedido().equals(c)))
        .collect(Collectors.toList());
    }
    
    // 13. Crea un Map<Character, List<Cliente>> donde la clave sea la letra inicial del nombre.
    public Map<Character, List<Cliente>> agruparClientesPorInicial() {
    return clientes.values().stream()
        .collect(Collectors.groupingBy(c -> c.getNombre().charAt(0)));
    }
    
    // 14. Crea un Map<String, Integer> con el DNI del cliente y el nº de pedidos realizados.
    public Map<String, Integer> pedidosPorCliente() {
    return clientes.values().stream()
        .collect(Collectors.toMap(
            Cliente::getDni,
            c -> (int) pedidos.stream().filter(p -> p.getClientePedido().equals(c)).count()
        ));
    }
    
    // 15. Crea un Map<Character, Integer> donde la clave sea la sección (1, 2, 3...) y el valor el total de existencias.
    public Map<Character, Integer> existenciasPorSeccion() {
    return articulos.values().stream()
        .collect(Collectors.toMap(
            a -> a.getIdArticulo().charAt(0),
            a -> a.getExistencias(),
            Integer::sum // si hay claves repetidas, se suman
        ));
    }

    // 16. Lista de artículos con precio entre 50 y 200 ordenados por existencias
    public List<Articulo> articulosPrecioMedioOrdenadosPorStock() {
    return articulos.values().stream()
        .filter(a -> a.getPvp() >= 50 && a.getPvp() <= 200)
        .sorted(Comparator.comparingInt(Articulo::getExistencias).reversed())
        .collect(Collectors.toList());
    }
    
    // 17. Guardar artículos sin stock en archivo sinStock.txt
    public void guardarArticulosSinStock() {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter("sinStock.txt"))) {
        for (Articulo a : articulos.values()) {
            if (a.getExistencias() == 0) {
                bw.write(a.getIdArticulo() + " - " + a.getDescripcion());
            }
        }
        System.out.println("Artículos sin stock guardados.");
    } catch (IOException e) {
        System.out.println("Error: " + e.getMessage());
    }
    }

    // 18. Total gastado por un cliente concreto
    public double gastoClientePorDni(String dni) {
    return pedidos.stream().filter(p -> p.getClientePedido().getDni().equalsIgnoreCase(dni))
        .mapToDouble(p -> totalPedido(p)).sum();
    }
    
    // 19. Lista de artículos con más de 10 existencias, ordenados por ID
    public List<Articulo> articulosConStockOrdenadosPorId() {
    return articulos.values().stream()
        .filter(a -> a.getExistencias() > 10)
        .sorted(Comparator.comparing(Articulo::getIdArticulo))
        .collect(Collectors.toList());
    }
    
    // 20. Crea un archivo que contenga los datos de los clientes que hayan hecho al menos 1 pedido y cuyo gasto total supere 1500 €
    public void guardarClientesVIP() {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter("clientesVIP.txt"))) {
        clientes.values().stream()
            .filter(c -> pedidos.stream().anyMatch(p -> p.getClientePedido().equals(c))) 
            .filter(c -> totalCliente(c) > 1500) 
            .sorted(Comparator.comparingDouble(c -> -totalCliente(c)))
            .forEach(c -> {
                try {
                    bw.write(c.getDni() + " - " + c.getNombre() + " - " + totalCliente(c));
                    bw.newLine();
                } catch (IOException e) {
                    System.out.println("Error al escribir: " + e.getMessage());
                }
            });
        System.out.println("Clientes VIP guardados correctamente.");
    } catch (IOException e) {
        System.out.println("Error general: " + e.getMessage());
    }
    }
    
    // 21. Guardar en un archivo .txt los clientes que hayan hecho al menos un pedido.
    public void guardarClientesConPedidos() {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter("clientesConPedidos.txt"))) {
        for (Cliente c : clientes.values()) {
            if (pedidos.stream().anyMatch(p -> p.getClientePedido().equals(c))) {
                bw.write(c.getDni() + " - " + c.getNombre());
                bw.newLine();
            }
        }
    } catch (IOException e) {

    }
    }
    
    // 22. Devuelve el total gastado por todos los clientes (es decir, la suma de todos los pedidos).
    public double totalFacturado() {
    return pedidos.stream().mapToDouble(p -> totalPedido(p)).sum();
    }
    
    // 23. Muestra los artículos con más de 10 existencias, ordenados por precio de mayor a menor.
    public void articulosConStockOrdenados() {
    articulos.values().stream().filter(a -> a.getExistencias() > 10)
        .sorted(Comparator.comparingDouble(Articulo::getPvp).reversed())
        .forEach(System.out::println);
    }
    
    // 24. Crea un mapa donde la clave sea el DNI del cliente y el valor el total gastado.
    public Map<String, Double> gastoPorCliente() {
    return clientes.values().stream().collect(Collectors.toMap(Cliente::getDni,c -> totalCliente(c)));
    }

    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="CHULETA RESUMEN">
/*   

// 1. Filtrar + recolectar en lista
List<Tipo> lista = coleccion.stream()
    .filter(x -> condición)
    .collect(Collectors.toList());

// 2. Filtrar + recolectar en conjunto (para eliminar duplicados)
Set<Tipo> conjunto = coleccion.stream()
    .filter(x -> condición)
    .collect(Collectors.toSet());

// 3. Filtrar + guardar en mapa clave/valor
Map<Clave, Valor> mapa = coleccion.stream()
    .collect(Collectors.toMap(
        x -> x.getClave(),
        x -> x.getValor()
    ));

// 4. Guardar en mapa agrupando por atributo (groupingBy)
Map<Agrupado, List<Tipo>> agrupado = coleccion.stream()
    .collect(Collectors.groupingBy(x -> x.getAlgo()));

// 5. Ordenar por atributo
.stream().sorted(Comparator.comparing(Clase::getAtributo))

// 6. Ordenar descendente por atributo
.stream().sorted(Comparator.comparing(Clase::getAtributo).reversed())

// 7. Cálculo total con mapToDouble
double total = coleccion.stream()
    .mapToDouble(x -> x.getPrecio() * x.getCantidad())
    .sum();

// 8. Cálculo total con mapToInt
int unidades = coleccion.stream()
    .mapToInt(x -> x.getUnidades())
    .sum();

// 9. Obtener el máximo por atributo (máximo valor)
Optional<Tipo> maximo = coleccion.stream()
    .max(Comparator.comparing(x -> x.getAlgo()));

// 10. Crear una lista de claves de un mapa, ordenadas por su valor (sin Map.Entry)
List<Clave> lista = mapa.keySet().stream()
    .sorted((k1, k2) -> mapa.get(k2) - mapa.get(k1))
    .collect(Collectors.toList());
   
===========================================
💻 CHULETA EXAMEN – JAVA STREAMS & ARCHIVOS
===========================================

🔹 1. Persistencia – Archivos de texto o binarios

📄 Guardar en texto:
try (BufferedWriter bw = new BufferedWriter(new FileWriter("archivo.txt"))) {
    for (Tipo t : coleccion) {
        bw.write(...);
        bw.newLine();
    }
}

📄 Leer desde texto:
try (BufferedReader br = new BufferedReader(new FileReader("archivo.txt"))) {
    String linea;
    while ((linea = br.readLine()) != null) {
        ...
    }
}

📦 Guardar en binario:
try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("archivo.dat"))) {
    for (Tipo t : coleccion) {
        oos.writeObject(t);
    }
}

📦 Leer desde binario:
try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("archivo.dat"))) {
    while (true) {
        Tipo t = (Tipo) ois.readObject();
    }
} catch (EOFException e) { ... }

-------------------------------------------

🔹 2. Streams – Seleccionar y ordenar

Filtro + orden:
coleccion.stream()
    .filter(x -> condición)
    .sorted(Comparator.comparing(Clase::getAtributo))
    .forEach(System.out::println);

Orden descendente:
.sorted(Comparator.comparing(Clase::getAtributo).reversed())

-------------------------------------------

🔹 3. Streams – Cálculo numérico

Suma total (precio × unidades):
double total = coleccion.stream()
    .mapToDouble(x -> x.getPrecio() * x.getUnidades())
    .sum();

Suma de unidades:
int total = coleccion.stream()
    .mapToInt(x -> x.getUnidades())
    .sum();

-------------------------------------------

🔹 4. Streams – Recolectar elementos

Nueva lista filtrada:
List<Tipo> lista = coleccion.stream()
    .filter(...)
    .collect(Collectors.toList());

Nuevo mapa clave → valor:
Map<Clave, Valor> mapa = coleccion.stream()
    .collect(Collectors.toMap(
        x -> x.getClave(),
        x -> x.getValor()
    ));

-------------------------------------------

✅ CONSEJO FINAL:
- Usa try-with-resources para archivos
- mapToDouble + sum = cálculo total
- filter + sorted = selección ordenada
- collect = nueva colección (List, Set, Map)

¡Léelo antes del examen y lo revientas! 💥


*/
//</editor-fold>

}

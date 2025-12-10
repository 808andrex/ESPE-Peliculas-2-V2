package espe.movies.Util;


import espe.movies.Contenido.Genero;

import java.util.Scanner;

public class ScannerUtils {
    public static Scanner scanner = new Scanner(System.in);

    public static String capturarTexto(String mensaje){
        String entrada;
        while(true){
            System.out.print(mensaje+": ");
            entrada = scanner.nextLine().trim();
            if(entrada.isEmpty()){
                System.out.println("⚠️ Entrada invalida. No puede estar vacia.");
                continue;
            }
            if(!entrada.matches("^[a-zA-Z0-9 ñÑáéíóúÁÉÍÓÚ:.-]+$")){
                System.out.println("⚠️ Entrada invalida. evitar caracteres raros.");
                continue;
            }
            return  entrada;
        }
    }

    public static boolean capturarBooleano(String mensaje){
        while(true){
            System.out.print(mensaje+": ");
            String entrada = scanner.nextLine().toLowerCase();
            if(entrada.equals("true")||entrada.equals("verdadero")||entrada.equals("si")||entrada.equals("s")){
                return true;
            }else if(entrada.equals("false")||entrada.equals("falso")||entrada.equals("no")||entrada.equals("n")){
                return false;
            }else{
                System.out.println("❌ Entrada invalida. Escribe 'true' o 'false'.");
            }
        }
    }

    public static char capturarChar(String mensaje){
        while(true){
            System.out.print(mensaje+": ");
            String entrada = scanner.nextLine();

            if(entrada.length()==1){
                return entrada.charAt(0);
            }
            System.out.println("⚠️ Por favor, ingresa solo un caracter.");
        }
    }

    public static int capturarNumero(String mensaje){
        while(true){
            System.out.print(mensaje+": ");
            try {
                return Integer.parseInt(scanner.nextLine());
            }catch(NumberFormatException e){
                System.out.println("❌ Error: Debes ingresar un número entero válido.");
            }
        }
    }

    public static double capturarDecimal(String mensaje){
        while(true){
            System.out.print(mensaje+": ");
            try {
                return Double.parseDouble(scanner.nextLine());
            }catch(NumberFormatException e){
                System.out.println("❌ Error: Debes ingresar un numero decimal (ej: 4.5).");
            }
        }
    }

    public static Genero capturarGenero(String mensaje){
        System.out.println(mensaje+": ");
        System.out.println("----Opciones-----");
        for(Genero genero : Genero.values()){
            System.out.print(genero +" | ");
        }
        System.out.println();

        while (true){
            System.out.print("Eliga una opcion: ");
            String entrada = scanner.nextLine().toUpperCase().trim();

            try {
                return Genero.valueOf(entrada);
            }catch(IllegalArgumentException e){
                System.out.println("❌ Opcion no valida. Intenta escribir tal cual ves en la lista");
            }
        }
    }
}
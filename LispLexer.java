/*
 * Programa Java que realiza análisis léxico (Lexer) de expresiones LISP.
 *
 * Requisitos:
 *  a) Verificar que la expresión tenga igual cantidad de paréntesis de apertura y cierre.
 *  b) Generar la lista de tokens de la expresión.
 *
 * Ejemplo de entrada:
 *   (+ 2 (* V 8))
 *
 * Ejemplo de salida:
 *   Expresión correcta: Paréntesis balanceados.
 *   Tokens: (,+,2,(,*,V,8,),)
 *
 * Se implementa el análisis léxico de forma recursiva para diferir de un enfoque convencional.
 *
 * CITACIÓN: Parte de este código fue generado con la asistencia de ChatGPT.
 */

 import java.util.ArrayList;
 import java.util.List;
 import java.util.Scanner;
 
 public class LispLexer {
 
     public static void main(String[] args) {
         System.out.println("Ingrese la expresión LISP (finalice con una línea vacía):");
         Scanner scanner = new Scanner(System.in);
         StringBuilder inputBuilder = new StringBuilder();
         
         // Se leen múltiples líneas hasta una línea vacía.
         while (scanner.hasNextLine()) {
             String line = scanner.nextLine();
             if (line.trim().isEmpty()) {
                 break;
             }
             inputBuilder.append(line).append(" ");
         }
         scanner.close();
         
         String input = inputBuilder.toString().trim();
         
         // Tokenización recursiva.
         List<String> tokens = tokenize(input);
         
         // Verificación recursiva de balance de paréntesis.
         boolean balanced = checkBalance(tokens);
         
         if (balanced) {
             System.out.println("Expresión correcta: Paréntesis balanceados.");
         } else {
             System.out.println("Expresión incorrecta: Paréntesis desbalanceados.");
         }
         
         // Impresión de tokens en el formato solicitado.
         System.out.print("Tokens: ");
         System.out.print("(");
         for (int i = 0; i < tokens.size(); i++) {
             System.out.print(tokens.get(i));
             if (i < tokens.size() - 1) {
                 System.out.print(",");
             }
         }
         System.out.println(")");
     }
     
     // Método que inicia la tokenización de forma recursiva.
     private static List<String> tokenize(String input) {
         List<String> tokens = new ArrayList<>();
         tokenizeRec(input, 0, tokens, new StringBuilder());
         return tokens;
     }
     
     /**
      * Método recursivo para tokenizar la entrada.
      * Separa paréntesis y acumula caracteres para formar tokens.
      */
     private static void tokenizeRec(String input, int index, List<String> tokens, StringBuilder token) {
         if (index >= input.length()) {
             if (token.length() > 0) {
                 tokens.add(token.toString());
             }
             return;
         }
         
         char c = input.charAt(index);
         
         if (c == '(' || c == ')') {
             // Si se tiene un token en construcción, se añade antes de procesar el paréntesis.
             if (token.length() > 0) {
                 tokens.add(token.toString());
                 token.setLength(0);
             }
             tokens.add(String.valueOf(c));
             tokenizeRec(input, index + 1, tokens, token);
         } else if (Character.isWhitespace(c)) {
             if (token.length() > 0) {
                 tokens.add(token.toString());
                 token.setLength(0);
             }
             tokenizeRec(input, index + 1, tokens, token);
         } else {
             token.append(c);
             tokenizeRec(input, index + 1, tokens, token);
         }
     }
     
     // Verifica recursivamente si los paréntesis en la lista de tokens están balanceados.
     private static boolean checkBalance(List<String> tokens) {
         return checkBalanceRec(tokens, 0, 0);
     }
     
     // Método auxiliar recursivo para el balance de paréntesis.
     private static boolean checkBalanceRec(List<String> tokens, int index, int balance) {
         if (index == tokens.size()) {
             return balance == 0;
         }
         String token = tokens.get(index);
         if (token.equals("(")) {
             balance++;
         } else if (token.equals(")")) {
             balance--;
         }
         if (balance < 0) return false;
         return checkBalanceRec(tokens, index + 1, balance);
     }
 }
 
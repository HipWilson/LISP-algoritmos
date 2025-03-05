/*
 * Programa en Java que realiza analisis Lexer de expresiones LISP.
 * CITACION: Parte de este codigo fue generado con la asistencia de ChatGPT.
 */

 import java.util.ArrayList;
 import java.util.List;
 import java.util.Scanner;
 
 public class LispLexer {
 
     public static void main(String[] args) {
         System.out.println("Ingrese la expresion LISP dejando una línea vacía (un enter)):");
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
         
         // Tokenizacion recursiva.
         List<String> tokens = tokenize(input);
         
         // Verificacion recursiva de balance de parentesis.
         boolean balanced = checkBalance(tokens);
         
         if (balanced) {
             System.out.println("Expresion correcta: Los parentesis estan balanceados.");
         } else {
             System.out.println("Expresion incorrecta: Parentesis desbalanceados.");
         }
         
         // Impresion de tokens en el formato solicitado.
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
     
     // Metodo que inicia la tokenizacion de forma recursiva.
     private static List<String> tokenize(String input) {
         List<String> tokens = new ArrayList<>();
         tokenizeRec(input, 0, tokens, new StringBuilder());
         return tokens;
     }
     
     /**
      * Metodo para la entrada
      * Separa parentesis y acumula caracteres para formar tokens.
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
             // Si se tiene un token en construccion, se añade antes de procesar el parentesis.
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
     
     // Verifica si los parentesis en la lista de tokens estan balanceados.
     private static boolean checkBalance(List<String> tokens) {
        return checkBalanceRec(tokens, 0, 0);
    }
    
    // Metodo auxiliar recursivo para el balance de parentesis.
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
 
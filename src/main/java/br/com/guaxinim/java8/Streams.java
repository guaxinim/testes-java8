package br.com.guaxinim.java8;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Streams {

    public static void main(String ... args) {

        Usuario user1 = new Usuario("Usuario 5", 150);
        Usuario user2 = new Usuario("Usuario 2", 120);
        Usuario user3 = new Usuario("Usuario 3", 190);
        Usuario user4 = new Usuario("Marcos", 250);

        // List imutavel - usando Arrays.asList
        List<Usuario> usuarios = Arrays.asList(user1, user2, user3, user4);

        System.out.println("================== Collections");

        // Tornando os 2 usuarios com mais pontos como moderadores
        usuarios.sort(Comparator.comparing(Usuario::getPontos).reversed());
        usuarios.subList(0,2).forEach(Usuario::tornaModerador);

        // Filtrando os usuarios na lista. A maioria das linguagens tem um metodo filter para filtrar resultados na lista
        // Como se fosse fazer um if para cada elemento da lista e executar uma ação

        System.out.println("================== Streams");

        // Stream - Usuarios > 100
        usuarios.stream().filter(u -> u.getPontos() > 130); // O Stream não altera a collection original
        // usuarios.forEach(System.out::println);  // Nao funciona

        // Funciona da seguinte forma:
        // Um pipeline de operações
        usuarios.stream().filter(u -> u.getPontos() > 130).forEach(System.out::println);

        System.out.println("================== Streams 2");

        // Outro exemplo:
        usuarios.stream()
                .filter(u -> u.getPontos() > 100)
                .forEach(Usuario::tornaModerador);


        System.out.println("================== Collectors ");

        // Como atribuir o stream a uma lista?


    }
}

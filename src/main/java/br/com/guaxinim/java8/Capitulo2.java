package br.com.guaxinim.java8;

import java.util.*;
import java.util.function.*;

public class Capitulo2 {

    public static void main(String ... args) {
        Usuario user1 = new Usuario("Usuario 5", 150);
        Usuario user2 = new Usuario("Usuario 2", 120);
        Usuario user3 = new Usuario("Usuario 3", 190);
        Usuario user4 = new Usuario("Marcos", 250);

        // List imutavel - usando Arrays.asList
        List<Usuario> usuarios = Arrays.asList(user1, user2, user3);


        for (Usuario u: usuarios) {
            System.out.println(u.getNome());
        }

        // Uso do forEach criando uma classe anonima que implementa java.util.function.Consumer
        Consumer<Usuario> mostrador = new Consumer<Usuario>() {
            @Override
            public void accept(Usuario usuario) {
                System.out.println(usuario.getNome());
            }
        };
        usuarios.forEach(mostrador);

        // forEach normal
        usuarios.forEach(new Consumer<Usuario>() {
            public void accept(Usuario usuario) {
                System.out.println(usuario.getNome());
            }
        });

        // Forma 1 = Variavel com Lambda
        Consumer<Usuario> mostrador1 = (Usuario u) -> {System.out.println("forma1 " + u.getNome());};

        // Forma 2 = Variabel com Lambda de forma mais curta
        Consumer<Usuario> m = u -> System.out.println("forma2 " + u.getNome());

        // forEach com Lambda
        // Ou seja, como ele sabe que o esperado no método é um Consumer<? super Usuario>
        // Podemos passar diretamente o lambda
        usuarios.forEach(u -> System.out.println("forma3 " + u.getNome()));

        // Outro exemplo de lambda e uma interface funcional - Runnable
        new Thread(() -> {
            for (int i=0;i <1000; i++) {
                //System.out.println(i);
            }
        }).start();

        // No Lambda nao eh possivel declarar atributos, ou seja, ela nao guarda estado dentro dela
        // Somente para executar uma funcao

        // Interfaces com metodos com return ou sem (nao e necessario)
        Validador<String> validadorCEP1 = v1 -> {return v1.matches("[0-9]{5}-[0-9]{3}"); };
        Validador<String> validadorCEP2 = v2 -> v2.matches("[0-9]{5}-[0-9]{3}");

        final Integer numero = 300;
        Integer numero2 = 5000;

        // É possivel capturar variaveis locais finais (Assim como uma classe anonima), ou não declaradas como finais :) .. Maass
        // Se nao for declarada como final vc nao pode alterar ela depois do metodo.
        Runnable o = () -> {
            System.out.println("Ola " + numero + numero2);
        };

        // Na verdade no Java 8 nas classes anonimas vc pode acessar variaveis nao finais desde que nao altere as mesmas apos o uso.

        // Default Methods - chamando o defaultMethod andThen
        Consumer<Usuario> mostraMensagem = u ->
                System.out.println("antes de imprimir os nomes");
        Consumer<Usuario> imprimeNome = u ->
                System.out.println(u.getNome());
        usuarios.forEach(mostraMensagem.andThen(imprimeNome));

        // Removendo os usuarios atraves do metodo default removeIf de Collection
        // Passando um predicado
        // Obs. Nao pode ser chamado de uma lista imutavel

        List<Usuario> li = new ArrayList<>();
        li.add(user1);
        li.add(user2);
        li.add(user3);
        li.add(user4);

        System.out.println("================== Compare");

        // Se colocar entre colchetes tem que colocar o return, senao nao precisa
        // Comparator<Usuario> cp = (u1, u2) -> { return u1.getNome().compareTo(u2.getNome()); };
        Collections.sort(li, (us1, us2) -> us1.getNome().compareTo(us2.getNome()));
        li.forEach(dm -> System.out.println(dm.getNome()));

        System.out.println("================== List.sort");
        li.sort((uu1, uu2) -> uu1.getNome().compareTo(uu2.getNome()));

        System.out.println("================== List.sort - passando um Comparing");

        // Metodos default em interfaces tambem podem ser estaticos, a interface Comparator tem um metodo comparing que é estático
        // Nesse caso passamos
        li.sort(Comparator.comparing(uuu -> uuu.getNome()));
        li.forEach(dm -> System.out.println(dm.getNome()));

        System.out.println("----");

        li.sort(Comparator.comparing(uuu -> uuu.getPontos()));
        li.forEach(dm -> System.out.println(dm.getNome()));

        // Ou de forma mais polida:
        //Collections.sort(li, (us1, us2) -> String.CASE_INSENSITIVE_ORDER.compare(us1.getNome(), us2.getNome()));

        System.out.println("================== removeIf");
        // Predicate<Usuario> p = usuario -> usuario.getPontos() > 160;
        li.removeIf(usuario -> usuario.getPontos() > 160);


        System.out.println("================== Natural Order");
        List<String> lista = Arrays.asList("Banana", "Abacaxi", "Pera", "Mamão", "Cebola", "Caqui");
        lista.sort(Comparator.naturalOrder());
        lista.forEach(f -> System.out.println(f));
        // O naturalOrder e um comparator pre-criado com base no valor da lista

        // Abaixo criado na um lambda que retorna uma String e um Comparator na mao com base no lambda
        Function<Usuario, String> extraiNome = u -> u.getNome();
        Comparator<Usuario> comparator = Comparator.comparing(extraiNome);
        usuarios.sort(comparator);

        // Extraindo agora os pontos
        Function<Usuario, Integer> extraiPontos = u -> u.getPontos();
        Comparator<Usuario> comparator2 = Comparator.comparing(extraiPontos);
        usuarios.sort(comparator2);

        // Ha um problema acima
        // O extraiPontos gerara um metodo apply que recebe um Usuario e devolve Integer, em vez de int.
        // Isso gerara um AUTOBOXING toda vez que esse metodo for invocado, e isso vai acontecer muitissimas vezes pelo sort.
        // Em vez disso devemos usar metodos que trabalham com os primitivos:
        // ToInfFunction, ToLongFunction, ToDoubleFunction.
        ToIntFunction<Usuario> extraiPontos2 = u -> u.getPontos();
        Comparator<Usuario> comparator3 = Comparator.comparingInt(extraiPontos2);
        usuarios.sort(comparator);

        System.out.println("================== Method reference");

        // O recurso do method reference é tratado pelo compilador da mesma forma que uma expressão lambda
        Consumer<Usuario> tornaModerador = Usuario::tornaModerador;
        li.forEach(tornaModerador);

        // Usando em uma lista
        li.forEach(Usuario::tornaModerador);            // Nao ha reflection

        // Usando em um metodo estatico
        Comparator.comparing(Usuario::getNome);

        usuarios.sort(Comparator.comparingInt(Usuario::getPontos));

        System.out.println("================== Dois criterios no comparator - thenComparing");

        // Compara pelos pontos, se tiver dois iguais compara pelo nome
        Comparator<Usuario> c = Comparator.comparingInt(Usuario::getPontos).thenComparing(Usuario::getNome);
        usuarios.sort(c);

        // Posicionando os nulos no final:
        usuarios.sort(Comparator.nullsLast(Comparator.comparing(Usuario::getNome)));

        // Reverso
        usuarios.sort(Comparator.comparing(Usuario::getNome).reversed());

        System.out.println("================== Metodos de instancia");

        Usuario rodrigo = new Usuario("Rodrigo Sousa", 30);
        Runnable run = rodrigo::getNome;                        // Vai fazer com que o metodo da instancia seja invocado
        run.run();

        // Os dois abaixo sao equivalentes:
        Runnable bloco1 = rodrigo::tornaModerador;
        Runnable bloco2 = () -> rodrigo.tornaModerador();

        // Referenciamento metodos que recebem argumentos
        usuarios.forEach(System.out::println);
        //Equivale a:
        //usuarios.forEach(u -> System.out.println(u));


        System.out.println("================== Method reference com construtores - Constructor reference");

        //Usuario marcos = Usuario::new;        // Erro, nao eh interface funcional,
        //  Para isso temos a interface funcional Supllier<T>

        Function<String, Usuario> criadorUsuarios = Usuario::new;
        Usuario carlos = criadorUsuarios.apply("Carlos");
        Usuario robson = criadorUsuarios.apply("Robson");

        // Com 2 parametros
        BiFunction<String, Integer, Usuario> criadorBi = Usuario::new;
        Usuario joao = criadorBi.apply("Joao", 30);
        Usuario maria = criadorBi.apply("Maria", 30);

        

    }
}

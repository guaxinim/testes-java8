package br.com.guaxinim.java8;

@FunctionalInterface
public interface Validador<T> {
    boolean valida(T t);
}

package be.nitroxis.lang;

/**
 * A {@code Functor} that may throw a checked {@code Exception} during evaluation.
 *
 * @author Olivier Houyoux (olivier.houyoux@gmail.com)
 * @param <T> the type of value that this {@code Thrower} returns
 * @param <E> the type of checked {@code Exception} that this {@code Thrower} may throw
 */
@FunctionalInterface
public interface Thrower<T, E extends Exception> extends Functor {
 
    /**
     * Evaluates this {@code Thrower}.
     * 
     * @return the evaluation result
     * @throws E if the evaluation process fails
     */
    T evaluate() throws E;
}

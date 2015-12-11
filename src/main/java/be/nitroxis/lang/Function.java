package be.nitroxis.lang;

/**
 * A {@code Functor} that takes no arguments and returns a value.
 * 
 * @author Olivier Houyoux (olivier.houyoux@gmail.com)
 * @param<T> the type of value that this {@code Function} returns
 */
@FunctionalInterface
public interface Function<T> extends Functor {
    
    /**
     * Evaluates this {@code Function}.
     * 
     * @return the evaluation result
     */
    T evaluate();
}

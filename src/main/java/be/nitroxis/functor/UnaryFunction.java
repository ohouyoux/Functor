package be.nitroxis.functor;

/**
 * A {@code Functor} that takes one argument and returns a value.
 *
 * @author Olivier Houyoux (olivier.houyoux@gmail.com)
 * @param <A> the type of argument
 * @param <T> the type of value that this {@code UnaryFunction} returns
 */
public interface UnaryFunction<A, T> extends Functor {

    /**
     * Evaluates this {@code UnaryFunction}.
     * 
     * @param argument the argument passed to this {@code UnaryFunction}
     * @return the evaluation result
     */
    T evaluate(A argument);
}

package be.nitroxis.lang;

import java.util.List;

/**
 * A utility class for {@code Thrower} {@code Function}s. The {@code unit} and {@code bind} methods make {@code Thrower}
 * a monad. Note that we can only compose in this manner {@code Thrower}s which are throwing the same {@code Exception}
 * type.
 *
 * @author Olivier Houyoux (olivier.houyoux@gmail.com)
 * @see https://en.wikipedia.org/wiki/Monad_(functional_programming)
 */
public final class Throwers {

    /**
     * Promotes function {@code f} so that it works with computation that may throw checked {@code Exception} without
     * {@code f} having been written to handle errors.
     *
     * @param <A> the type of argument passed to the {@code UnaryFunction} that is being promoted
     * @param <T> the type of value that the {@code UnaryFunction} to be promoted returns
     * @param <E> the type of {@code Exception} that the promoted {@code UnaryFunction} may throw
     * @param f the {@code UnaryFunction} to be promoted
     * @return the promoted {@code UnaryFunction}
     */
    public static <A, T, E extends Exception> UnaryFunction<Thrower<A, E>, Thrower<T, E>>
        map(final UnaryFunction<A, T> f) {

        return a -> () -> f.evaluate(a.evaluate());
    }

    /**
     * Creates a new {@code Thrower} that completely preserves the value that we put in it.
     *
     * @param <A> the type of argument passed to the created {@code Thrower}
     * @param <E> the type of {@code Exception} that the created {@code Thrower} may throw
     * @param a the value to be preserved by the created {@code Thrower}
     * @return the new {@code Thrower}
     */
    public static <A, E extends Exception> Thrower<A, E> unit(final A a) {
        return () -> a;
    }

    /**
     * Applies a {@code UnaryFunction} {@code f} to the contents of the {@code Thrower} {@code a}, but not suffering any
     * consequences until later, whenever we decide to call {@code evaluate} on the result of bind.
     *
     * @param <A> the type of argument passed to the {@code UnaryFunction}
     * @param <B> the type of argument evaluated by the {@code Thrower} to be bound
     * @param <E> the type of {@code Exception} that the created {@code Thrower} may throw
     * @param a the {@code Thrower} to be bound
     * @param f the {@code UnaryFunction} to apply to the results of {@code a} (Kleisli Arrow for the {@code Thrower}
     *     monad - see PartialArrow for an easy way to create them)
     * @return a new {@code Thrower} that applies {@code f} to the results of {@code a}
     * @see http://blog.sigfpe.com/2006/06/monads-kleisli-arrows-comonads-and.html?showComment=1162433100000
     * @see PartialArrow
     */
    public static <A, B, E extends Exception> Thrower<B, E> bind(
        final Thrower<A, E> a,
        final UnaryFunction<A, Thrower<B, E>> f) {

        return () -> f.evaluate(a.evaluate()).evaluate();
    }

    /**
     * Collapses an entire {@code List} of {@code Thrower}s into a single {@code Thrower} that returns all their results
     * in one {@code List}.
     *
     * @param <A> the type of argument passed to {@code throwers}
     * @param <E> the type of {@code Exception} that any {@code Thrower} in {@code throwers} may throw
     * @param throwers the {@code Thrower}s to be collapsed
     * @return the {@code Thrower} that returns {@code throwers} results in one {@code List}
     */
    public static <A, E extends Exception> Thrower<List<A>, E> sequence(final List<Thrower<A, E>> throwers) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private Throwers() {
        // Does nothing
    }
}

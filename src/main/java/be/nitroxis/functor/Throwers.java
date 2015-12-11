package be.nitroxis.functor;

/**
 * A utility class for {@code Thrower} {@code Function}s.
 *
 * @author Olivier Houyoux (olivier.houyoux@gmail.com)
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

        // TODO replace this anonymous class with lambda expression
        return new UnaryFunction<Thrower<A, E>, Thrower<T, E>>() {
            
            @Override
            public Thrower<T, E> evaluate(final Thrower<A, E> a) {

                // TODO replace this anonymous class with lambda expression
                return new Thrower<T, E>() {
                    
                    @Override
                    public T evaluate() throws E {
                        return f.evaluate(a.evaluate());
                    }
                };
            }
        };
    }
    
    private Throwers() {
        // Does nothing
    }
}

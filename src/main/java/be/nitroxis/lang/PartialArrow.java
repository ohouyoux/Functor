package be.nitroxis.lang;

/**
 * Models a partial {@code UnaryFunction}, a function whose result is undefined for some values (it might throw an
 * {@code Exception} instead of returning a value)
 *
 * @author Olivier Houyoux (olivier.houyoux@gmail.com)
 * @param <A> the type of argument passed to the {@code PartialArrow}
 * @param <B> the type of argument evaluated by the {@code Thrower} to be created
 * @param <E> the type of {@code Exception} that the created {@code Thrower} may throw
 */
public abstract class PartialArrow<A, B, E extends Exception> implements UnaryFunction<A, Thrower<B, E>> {

    @Override
    public final Thrower<B, E> evaluate(final A a) {
        return () -> PartialArrow.this.doEvaluate(a);
    }

    // The Thrower stuff has been abstracted away, so concrete subclasses will only need to deal with how to turn A into
    // B or throw an E

    /**
     * Defines how to turn an {@code A} into a {@code B} or throw an {@code Exception}.
     *
     * @param a the argument to be evaluated
     * @return the result of turning {@code a}
     * @throws E if the evaluation process fails
     */
    protected abstract B doEvaluate(A a) throws E;
}

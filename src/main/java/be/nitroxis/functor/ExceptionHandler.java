package be.nitroxis.functor;

import java.io.IOException;

/**
 * Utility class that does know how to handle exception.
 *
 * @author Olivier Houyoux (olivier.houyoux@gmail.com)
 */
public final class ExceptionHandler {
    
    /**
     * Evaluates a {@code Functor} that should return an integer.
     * 
     * @param t the {@code Functor} to be evaluated
     * @return the evaluation result of {@code t} or Integer.MIN_VALUE if an {@code IOException} is thrown
     */
    public static Integer evaluate(final Thrower<Integer, IOException> t) {
        int length;
        
        try {
            length = t.evaluate();
        } catch (final IOException e) {
            length = Integer.MIN_VALUE;
        }
        
        return length;
    }
    
    private ExceptionHandler() {
        // Does nothing
    }
}

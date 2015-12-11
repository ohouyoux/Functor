package be.nitroxis.functor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Shows the benefits of using the {@code Throwers} utility class with a simple example.
 *
 * @author Olivier Houyoux (olivier.houyoux@gmail.com)
 */
public class Main {

    /*
     * This program reuses a function designed to operate on strings, so that it operates on operations that result in
     * strings but might throw exceptions.
     */
    
    // A unary function which can be used to get the length of a string
    public static UnaryFunction<String, Integer> length = new UnaryFunction<String, Integer>() {
        
        @Override
        public Integer evaluate(final String s) {
            return s.length();
        }
    };
    
    /*
     * To illustrate lazy error handling this program reuses a function designed to operate on strings, so that it 
     * operates on operations that result in strings but might throw exceptions. The exception handling is done
     * somewhere else, in ExceptionHandler, outside of the main program, in an error handling library function called
     * which we assume knows the right thing to do. Note that the main program has no try/catch block at all.
     * You don't have to handle exceptions at all in your code, even though you're performing actions that throw 
     * exceptions. You just pass the buck to somebody who knows how to handle exceptions. 
     * You're not really performing any IO actions, at least not syntactically speaking. ExceptionHandler is doing that
     * for you as well, since it's the one calling evaluate() on the Thrower. This doesn't make a difference to the 
     * compiled program except that the execution stack (and hence the stack trace, should things go terribly wrong)
     * will look a bit different.
     * If you're used to Inversion of Control, then this should be nothing new. In fact, in real life, you may want to 
     * inject something like ExceptionHandler as a dependency, using Spring or something similar.
     */
    
    public static void main(final String[] args) {
        Thrower<String, IOException> readLine = new Thrower<String, IOException>() {
            
            @Override
            public String evaluate() throws IOException {
                return new BufferedReader(new InputStreamReader(System.in)).readLine();
            }
        };

        UnaryFunction<Thrower<String, IOException>, Thrower<Integer, IOException>> f = Throwers.map(length);
        System.out.println(ExceptionHandler.evaluate(f.evaluate(readLine)));
    }
}

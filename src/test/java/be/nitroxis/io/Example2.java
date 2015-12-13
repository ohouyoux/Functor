package be.nitroxis.io;

import be.nitroxis.lang.PartialArrow;
import be.nitroxis.lang.Thrower;
import be.nitroxis.lang.Throwers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 * Shows the benefits of using the {@code Thrower} as a monad.
 *
 * @author Olivier Houyoux (olivier.houyoux@gmail.com)
 */
public class Example2 {

    // NetTalker just use connections, the construction of URLConnections will be implementation-specific
    public abstract class NetTalker<A> extends PartialArrow<URLConnection, A, IOException> {
        // Very clear intent: users have to to override evaluate to do something useful with a connection
    }

    public abstract class URLConnector<A> extends PartialArrow<A, URLConnection, IOException> {

    }

    public class DataReader extends NetTalker<String> {

        @Override
        protected String doEvaluate(final URLConnection conn) throws IOException {
            StringBuilder content = new StringBuilder();
            conn.connect();
            InputStream is = conn.getInputStream();
            
            try (final BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                String line;
                
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            }
            
            return content.toString();
        }
    }

    public class JarURLConnector extends URLConnector<String> {

        @Override
        protected URLConnection doEvaluate(final String s) throws IOException {
            URL url = new URL(s);
            JarURLConnection conn = (JarURLConnection) url.openConnection();
            Manifest manifest = conn.getManifest();
            Map<String, Attributes> entries = manifest.getEntries();
            
            if (entries.containsKey("Created-By")) {
                System.out.println("Connection built for JAR created by " + entries.get("Created-By"));
            }
            
            return conn;
        }
    }
    
    public static class NetTalkerExceptionHandler<A> {

        public A evaluate(final Thrower<A, IOException> t) {
            A r = null;
            
            try {
                r = t.evaluate();
            } catch (final IOException e) {
                System.err.println("");
                e.printStackTrace();
            }
            
            return r;
        }
    }

    public static void main(final String[] args) {
        Example2 ex = new Example2();
        URLConnector c = ex.new JarURLConnector();
        NetTalker<String> n = ex.new DataReader();
        Thrower<URLConnection, IOException> a = c.evaluate("jar:file:./src/test/resources/data.jar!/data.txt");
        Thrower<String, IOException> t = Throwers.bind(a, n);
        
        // You can take t and bind it to a PartialArrow that takes a String and writes it to a file for example, 
        // again returning a lazy Thrower. Then, when something calls evaluate() on it, the whole enchilada of 
        // operations will be executed in one go
        
        System.out.println(new NetTalkerExceptionHandler<String>().evaluate(t));
    }
}

package nl.hu.tosad;

import nl.hu.tosad.controller.Controller;

/**
 * Hello world!
 *
 */
public class App 
{
    private static Controller controller = new Controller();

    public static void main( String[] args )
    {
        controller.generate(1);
        controller.generate(2);
        controller.generate(3);
        controller.generate(4);
    }
}

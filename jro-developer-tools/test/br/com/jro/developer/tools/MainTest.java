/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jro.developer.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jairodealmeida
 */
public class MainTest {
    private static ArrayList<String[]> commands = new ArrayList<String[]>();
    public MainTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    	commands.add(null);
    	commands.add("-h".split(" "));
        commands.add("".split(" "));
        commands.add("-d".split(" "));
        commands.add("  ".split(" "));
        commands.add("-r".split(" "));
        commands.add("-r -r".split(" "));
        commands.add("-d".split(" "));
        commands.add("-d ".split(" "));
        commands.add("-d C:/test_false".split(" "));
        commands.add("-d C:\\test_false".split(" "));
        commands.add("-d C:\\ftp".split(" "));
        commands.add("-d null".split(" "));
        commands.add("C:\\ftp".split(" "));
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of main method, of class Main.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        try {
            for (Iterator<String[]> it = commands.iterator(); it.hasNext();) {
                String[] args = it.next();
                Main.main(args);
            }
        } catch (Exception e) {
            fail("woldn't pass here");
        }
    }

}
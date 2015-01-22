package com.netaporter;
import com.jdotsoft.jarloader.JarClassLoader;

/**
 * Created by a.makarenko on 2/7/14.
 */
public class TestLauncher {

        public static void main(String[] args) {
            JarClassLoader jcl = new JarClassLoader();
            try {
                jcl.invokeMain("cucumber.api.cli.Main", args);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        } // main()

    } // class MyAppLauncher

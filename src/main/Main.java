package main;

import persistentie.Connectie;

public class Main
{
    public static void main(String[] args)
    {
        if (args.length == 1)
        {
            Connectie.setSshPrivateKeyPath(args[0]);
        }
    }
}
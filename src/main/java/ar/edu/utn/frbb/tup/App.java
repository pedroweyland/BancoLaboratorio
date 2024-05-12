package ar.edu.utn.frbb.tup;

import ar.edu.utn.frbb.tup.banco.MenuBanco;
import ar.edu.utn.frbb.tup.entidades.Banco;

public class App
{
    public static void main( String[] args )
    {
        Banco banco = new Banco();
        MenuBanco menuBanco = new MenuBanco();

        menuBanco.menuInicio(banco);
    }

}

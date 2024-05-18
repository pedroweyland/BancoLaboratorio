package ar.edu.utn.frbb.tup;

import ar.edu.utn.frbb.tup.service.MenuBanco;
import ar.edu.utn.frbb.tup.model.Banco;

public class App
{
    public static void main(String[] args)
    {
        Banco banco = new Banco();
        MenuBanco menuBanco = new MenuBanco();

        menuBanco.menuInicio(banco);
    }

}

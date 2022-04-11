package lab5.client.command.tasksCommands.without_arguments;

import lab5.client.command.parsing.Command;
import lab5.client.command.parsing.CommandWithoutMovie;
import lab5.server.visitor.Visitor;

import java.io.BufferedReader;

public class ShowCommand implements CommandWithoutMovie {
    private String[] args;

    public String[] getArgs(){
        return args;
    }

    @Override
    public String toString(){
        return "show - вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    @Override
    public boolean execute(String[] arguments, BufferedReader in) {
        args = arguments;
        return true;
    }

    @Override
    public boolean valid(String[] args) {
        this.args = args;
        if (args.length!=0){
            System.out.println("команда 'show' должна быть без аргументов");
            return false;
        }
        else {
            return true;
        }
    }
}

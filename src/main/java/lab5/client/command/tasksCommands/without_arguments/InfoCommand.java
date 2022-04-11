package lab5.client.command.tasksCommands.without_arguments;

import lab5.client.command.parsing.Command;
import lab5.client.command.parsing.CommandWithoutMovie;
import lab5.server.visitor.Visitor;

import java.io.BufferedReader;

public class InfoCommand implements CommandWithoutMovie {
    private String[] args;

    public String[] getArgs(){
        return args;
    }

    public String toString(){
        return "info - вывести информацию о коллекции";
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
            System.out.println("команда 'info' должна быть без аргументов");
            return false;
        }
        else {
            return true;
        }
    }
}

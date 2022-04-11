package lab5.client.command.tasksCommands.with_arguments;

import lab5.client.command.parsing.Command;
import lab5.client.command.parsing.CommandWithMovie;
import lab5.client.command.parsing.CommandWithoutMovie;
import lab5.server.visitor.Visitor;
import lab5.server.entity.Movie;

import java.io.BufferedReader;

public class RemoveGreaterCommand implements CommandWithMovie {

    private Movie movie = null;

    private String[] args = null;

    public String toString(){
        return "remove_greater {element} - удалить из коллекции все элементы, превыщающие заданный";
    }

    @Override
    public Object accept(Visitor v, Movie movie) {
        return v.visit(this, movie);
    }

    public String[] getArgs(){
        return args;
    }

    public Movie getMovie(){
        return movie;
    }

    @Override
    public boolean execute(String[] arguments, BufferedReader in) {
//        ObjectParser objectParser = new ObjectParser();
        args = arguments;
        return true;
    }
    public boolean valid(String[] args) {
        this.args = args;
        if (args.length == 0){
            return true;
        }
        else{
            System.out.println("вы должны вводить значения полей в консоли");
            return false;
        }
    }
}


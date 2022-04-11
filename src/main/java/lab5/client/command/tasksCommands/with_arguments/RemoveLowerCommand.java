package lab5.client.command.tasksCommands.with_arguments;

import lab5.client.command.parsing.Command;
import lab5.client.command.parsing.CommandWithMovie;
import lab5.server.visitor.Visitor;
import lab5.server.entity.Movie;

import java.io.BufferedReader;

public class RemoveLowerCommand implements CommandWithMovie {

    private Movie movie = null;

    private String[] args = null;
    public String[] getArgs(){
        return args;
    }

    public String toString(){
        return "remove_lower {element} - удалить из коллекции все элементы, меньшие,чем заданный";
    }

    @Override
    public Object accept(Visitor v, Movie movie) {
        return v.visit(this, movie);
    }

    public Movie getMovie(){
        return movie;
    }

    @Override
    public boolean execute(String[] arguments, BufferedReader in) {
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

package lab5.client.command.tasksCommands.with_arguments;

import lab5.client.command.parsing.CommandWithMovie;
import lab5.client.command.parsing.ObjectParser;
import lab5.client.command.parsing.Command;
import lab5.server.visitor.Visitor;
//import lab5.entity.*;
import lab5.server.entity.Movie;

import java.io.BufferedReader;
import java.io.IOException;

public class AddCommand implements CommandWithMovie {
    private Movie movie = null;
    private String[] args = null;

    @Override
    public String toString() {
        return "add {element} - добавить новый элемент в коллекцию";
    }

    public Movie getMovie() {
        return movie;
    }

    @Override
    public Object accept(Visitor v, Movie movie) {
        return v.visit(this, movie);
    }
    public String[] getArgs(){
        return args;
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
            System.out.println("команда 'add' должна быть без аргументов");
            return false;
        }
        else {
            return true;
        }
    }
}
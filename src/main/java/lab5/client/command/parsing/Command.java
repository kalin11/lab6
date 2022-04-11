package lab5.client.command.parsing;

import lab5.server.entity.Movie;
import lab5.server.visitor.Visitor;

import java.io.BufferedReader;

public interface Command {
    boolean execute(String[] arguments, BufferedReader in);
    boolean valid(String[] args);
}

package lab5.client.command.parsing;

import lab5.server.entity.Movie;
import lab5.server.visitor.Visitor;

public interface CommandWithMovie extends Command{
    Object accept(Visitor v, Movie movie);
}

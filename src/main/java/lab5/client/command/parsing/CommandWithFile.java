package lab5.client.command.parsing;

import lab5.server.visitor.Visitor;

public interface CommandWithFile extends Command{
    Object accept(Visitor visitor, String file);
}

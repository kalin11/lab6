package lab5.client.command.parsing;

import lab5.server.visitor.Visitor;

public interface CommandWithoutMovie extends Command {
    Object accept(Visitor v);
}

package lab5.client.command.tasksCommands.without_arguments;

import lab5.client.command.parsing.Command;
import lab5.client.command.parsing.CommandWithMovie;
import lab5.client.command.parsing.CommandWithoutMovie;
import lab5.server.visitor.Visitor;

import java.io.BufferedReader;

public class ExitCommand implements CommandWithoutMovie {
    private String[] args;

    public String[] getArgs(){
        return args;
    }

    @Override
    public String toString() {
        return  "exit - завершить проограмму, без сохранения в файл";
    }

    @Override
    public Object accept(Visitor v) {
        throw new RuntimeException("How");
    }

    @Override
    public boolean execute(String[] arguments, BufferedReader in) {
        args = arguments;
        return false;
    }
    public boolean valid(String[] args) {
        this.args = args;
        if (args.length!=0){
            System.out.println("команда 'exit' должна быть без аргументов");
            return false;
        }
        else {
            return true;
        }
    }
}

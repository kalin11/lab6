package lab5.client.command.tasksCommands.with_arguments;

import lab5.client.command.CommandName;
import lab5.client.command.parsing.Command;
import lab5.client.command.parsing.CommandWithoutMovie;
import lab5.server.visitor.Visitor;
import lab5.server.entity.MovieGenre;

import java.io.BufferedReader;
import java.util.Locale;

public class CountByGenreCommand implements CommandWithoutMovie {

    private String[] args = null;

    public String toString(){
        return "count_by_genre genre - вывести количество элементов, значение поля genre которых равно заданному";
    }

    public String[] getArgs(){
        return args;
    }



    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    @Override
    public boolean execute(String[] arguments, BufferedReader in) {
        args = arguments;
            try{
                CommandName.valueOf(CommandName.class, arguments[0]);
            }catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                System.out.print("");
            }
        return true;
    }
    public boolean valid(String[] args) {
        this.args = args;
        try {
            if (args.length == 1) {
                MovieGenre movieGenre = MovieGenre.valueOf(args[0].toUpperCase(Locale.ROOT));
                return true;
            } else if (args.length == 0) {
                System.out.println("вы ввели команду без аргумента, что считать? вы можете ввести 'ACTION, DRAMA, HORROR, SCIENCE_FICTION'");
                return false;
            } else {
                System.out.println("некорректный ввод параметра");
                return false;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("некорректный тип параметра, попробуйте заново, вы можете ввести 'ACTION, DRAMA, HORROR, SCIENCE_FICTION'");
            return false;
        }
    }
//        if (args.length==0){
//            System.out.println("вы ввели команду без аргумента, что считать? вы можете ввести 'ACTION, DRAMA, HORROR, SCIENCE_FICTION'");
//            return false;
//        }
//        else if (args.length > 1){
//            System.out.println("вы ввели слишком много аругментов");
//            return false;
//        }
//        else return true;
//    }
}

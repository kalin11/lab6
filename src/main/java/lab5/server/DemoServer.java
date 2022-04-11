package lab5.server;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import lab5.Cmd;
import lab5.client.command.CommandName;
import lab5.client.command.parsing.Command;
import lab5.client.command.parsing.CommandLineParser;
import lab5.client.command.tasksCommands.with_arguments.*;
import lab5.client.command.tasksCommands.without_arguments.*;
import lab5.server.collection.CollectionReader;
import lab5.server.collection.LinkedCollection;
import lab5.server.visitor.VisitorImpl;

import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class DemoServer {
    private static int PORT = 9000;
    public static void main (String args[]) {
        String filePath = "laba.txt";
        LinkedCollection collection = new LinkedCollection();
        CollectionReader collectionReader = new CollectionReader(collection);
        collection = collectionReader.read(filePath);
        System.out.println(collection);
        VisitorImpl visitor = new VisitorImpl(collection, initCommands());
        ByteBuffer sharedBuffer = ByteBuffer.allocate(16384);
//        ByteBuffer buffer = ByteBuffer.allocate(1024);
        Selector selector = null;
        ServerSocket serverSocket = null;
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocket = serverSocketChannel.socket();
            serverSocket.bind(new InetSocketAddress(PORT));
            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            System.err.println("Unable to setup environment");
            System.exit(-1);
        }
        try {
            while (true) {
                int count = selector.select();
                // нечего обрабатывать
                if (count == 0) {
                    continue;
                }
                Set keySet = selector.selectedKeys();
                Iterator itor = keySet.iterator();
                while (itor.hasNext()) {
                    SelectionKey selectionKey = (SelectionKey) itor.next();
                    itor.remove();
                    Socket socket = null;
                    SocketChannel channel = null;
                    if (selectionKey.isAcceptable()) {
                        System.out.println("Got acceptable key");
                        try {
                            socket = serverSocket.accept();
                            System.out.println("Connection from: " + socket);
                            channel = socket.getChannel();
                        } catch (IOException e) {
                            System.out.println("не можем подсоеденить канал");
                            selectionKey.cancel();
                        }
                        if (channel != null) {
                            try {
                                System.out.println("Watch for something to read");
                                channel.configureBlocking(false);
                                channel.register(selector, SelectionKey.OP_READ);
                            } catch (IOException e) {
                                System.out.println("не можем заюзать канал");
                                selectionKey.cancel();
                            }
                        }
                    }
                    if (selectionKey.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        ByteBuffer data = ByteBuffer.allocate(1024);
                        socketChannel.read(data);
                        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data.array());
                        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                        Cmd cmd = (Cmd) objectInputStream.readObject();
                        if (cmd.getArguments() == null){
                            System.out.println("client's message - " + cmd.getName());
                        }
                        else {
                            System.out.println("client's message - " + cmd);
                        }
                        try {
                            //

                            //окей, то есть лист я передавать научился, было не так и сложно
                            //вопрос! как теперь организовать сериализацию объектов???
//                            System.out.println(Arrays.toString(list.toArray()).getBytes());
                            if (channel != null){
                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);

                                //отправить мы должны ответ команды, это может быть как список, объект Movie, еще чот
                                //здесь опять идет проверка на валидность
                                //далее мы запускаем какое-то исполнение команды и выхватываем результат
                                if (cmd.getArguments() == null){
                                    CommandLineParser parser = new CommandLineParser(cmd.getName(), initCommands());
                                    Object o = parser.exe(visitor);
                                    System.out.println(o);
                                    outputStream.writeObject(o);
                                    outputStream.flush();
                                    channel.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
                                    System.out.println("sending to client");
                                }
                                else {
//                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                            ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
                                    CommandLineParser parser = new CommandLineParser(cmd.toString(), initCommands());
                                    Object o = parser.exe(visitor);
                                    System.out.println(o);
                                    outputStream.writeObject(o);
                                    outputStream.flush();
                                    channel.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
                                    System.out.println("sending to client");
//                            channel.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
                                }
                                channel.configureBlocking(false);
                                channel.register(selector, SelectionKey.OP_ACCEPT);
                            }
                        } catch (IOException e) {
                            System.out.println("клиент отлетел");
                            selectionKey.cancel();
                        }
                        try {
                            System.out.println("закрываем поток...");
                            socketChannel.close();
                        } catch (IOException e) {
                            System.out.println("клиент отлетел");
                            selectionKey.cancel();
                        }
                    }
                    System.out.println("сервер ожидает дальнейших действий.");
                }
            }
        } catch (IOException e) {
            System.out.println("ошибка при select()");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static HashMap<CommandName, Command> initCommands() {
        HashMap<CommandName, Command> commands = new HashMap<>();
        commands.put(CommandName.HELP, new HelpCommand());
        commands.put(CommandName.EXIT, new ExitCommand());
        commands.put(CommandName.ADD, new AddCommand());
        commands.put(CommandName.SHOW, new ShowCommand());
        commands.put(CommandName.HEAD, new HeadCommand());
        commands.put(CommandName.INFO, new InfoCommand());
        commands.put(CommandName.AVERAGE_OF_LENGTH, new AverageOfLength());
        commands.put(CommandName.PRINT_UNIQUE_OSCARS_COUNT, new PrintUniqueOscarsCount());
        commands.put(CommandName.UPDATE_ID, new UpdateCommand());
        commands.put(CommandName.REMOVE_BY_ID, new RemoveCommand());
        commands.put(CommandName.COUNT_BY_GENRE, new CountByGenreCommand());
        commands.put(CommandName.REMOVE_GREATER, new RemoveGreaterCommand());
        commands.put(CommandName.CLEAR, new ClearCommand());
        commands.put(CommandName.REMOVE_LOWER, new RemoveLowerCommand());
        commands.put(CommandName.EXECUTE_SCRIPT, new ExecuteScriptCommand());
        return commands;
    }
}
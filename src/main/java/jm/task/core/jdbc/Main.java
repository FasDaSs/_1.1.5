package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        //userService.saveUser("Test1", "Test1", (byte)23);
        //userService.saveUser("Test2", "Test2", (byte)56);
        //userService.saveUser("Test3", "Test3", (byte)15);
        //userService.saveUser("Test4", "Test4", (byte)32);

        var users = userService.getAllUsers();
        for (var user: users) {
            System.out.println(user);
        }

        //userService.cleanUsersTable();
        //userService.dropUsersTable();

        //getSessionFactory().close();
    }
}

package com.company.views.service;

import com.company.bean.User;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.company.connection.ConnectionMeth.*;

public class UserService {
    List<User> users = new ArrayList<>();

    public List<User> returnUsers() throws SQLException {
        startConnection();
        ResultSet resultSet = statement.executeQuery("select * from user");
        while (resultSet.next()) {
            users.add(new User(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    resultSet.getString("surname"),
                    resultSet.getString("patronymic"),
                    resultSet.getString("number"),
                    resultSet.getDate("date")));
        }
        resultSet.close();
        endConnection();
        return users;
    }


}

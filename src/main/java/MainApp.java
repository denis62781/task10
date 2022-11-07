import java.sql.*;

public class MainApp {
    private static Connection connection;
    private static Statement statement;

    public static void main(String[] args) {
//        create_table();//Создание размеченой таблицы
//        create_data();//Создание данных
        zad();//Задания по методичке
    }


    /*Создайте таблицу студенты (students): id, имя, серия паспорта, номер паспорта;
    Создайте таблицу Предметы (subjects): id, название предмета;
    Создайте таблицу Успеваемость (progress): id, студент, предмет, оценка;
    Оценка может находиться в пределах от 2 до 5;
    При удалении студента из таблицы, вся его успеваемость тоже должна быть удалена;
    Пара серия-номер пасорта должны быть уникальны в таблице Студенты;*/
    public static void create_table() {
        try {
            connect();
            statement.executeUpdate("create table students(" +
                    "id serial PRIMARY KEY, " +
                    "name varchar NOT NULL, " +
                    "seriya int NOT NULL, " +
                    "number int NOT NULL," +
                    "UNIQUE(seriya)," +
                    "UNIQUE(number)" +
                    ")");
            statement.executeUpdate("create table predmet(" +
                    "namePredmet varchar PRIMARY KEY" +
                    ")");
            statement.executeUpdate("create table scoreStudents(" +
                    "id serial, " +
                    "id_st integer," +
                    "foreign key(id_st) references students(id) on delete cascade, " +
                    "namePredmet varchar NOT NULL, " +
                    "FOREIGN KEY (namePredmet) REFERENCES predmet(namePredmet) ON DELETE CASCADE," +
                    "score smallint NOT NULL," +
                    "CHECK(score >= 2 AND score <= 5)" +
                    ")");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnection();
        }
    }


//  Ввод данных в таблицу
    public static void create_data() {
        try {
            connect();


            statement.executeUpdate("insert into students(name, seriya, number) VALUES ('Bob', 4565, 123465)");
            statement.executeUpdate("insert into students(name, seriya, number) VALUES ('Bob1', 4566, 123746)");
            statement.executeUpdate("insert into students(name, seriya, number) VALUES ('Bob3', 3565, 214746)");
            statement.executeUpdate("insert into students(name, seriya, number) VALUES ('Bob2', 4567, 124746)");
            statement.executeUpdate("insert into students(name, seriya, number) VALUES ('Bob5', 5565, 555555)");


            statement.executeUpdate("insert into predmet(namePredmet) VALUES ('Физика')");
            statement.executeUpdate("insert into predmet(namePredmet) VALUES ('Биология')");
            statement.executeUpdate("insert into predmet(namePredmet) VALUES ('Информатика')");
            statement.executeUpdate("insert into predmet(namePredmet) VALUES ('Математика')");


            statement.executeUpdate("insert into scoreStudents(id_st, namePredmet, score) VALUES (1, 'Физика', 5)");
            statement.executeUpdate("insert into scoreStudents(id_st, namePredmet, score) VALUES (2, 'Физика', 4)");
            statement.executeUpdate("insert into scoreStudents(id_st, namePredmet, score) VALUES (3, 'Физика', 3)");
            statement.executeUpdate("insert into scoreStudents(id_st, namePredmet, score) VALUES (4, 'Физика', 5)");
            statement.executeUpdate("insert into scoreStudents(id_st, namePredmet, score) VALUES (5, 'Физика', 4)");

            statement.executeUpdate("insert into scoreStudents(id_st, namePredmet, score) VALUES (1, 'Математика', 2)");
            statement.executeUpdate("insert into scoreStudents(id_st, namePredmet, score) VALUES (2, 'Математика', 3)");
            statement.executeUpdate("insert into scoreStudents(id_st, namePredmet, score) VALUES (3, 'Математика', 3)");
            statement.executeUpdate("insert into scoreStudents(id_st, namePredmet, score) VALUES (4, 'Математика', 4)");
            statement.executeUpdate("insert into scoreStudents(id_st, namePredmet, score) VALUES (5, 'Математика', 3)");

            statement.executeUpdate("insert into scoreStudents(id_st, namePredmet, score) VALUES (1, 'Информатика', 4)");
            statement.executeUpdate("insert into scoreStudents(id_st, namePredmet, score) VALUES (2, 'Информатика', 2)");
            statement.executeUpdate("insert into scoreStudents(id_st, namePredmet, score) VALUES (3, 'Информатика', 2)");
            statement.executeUpdate("insert into scoreStudents(id_st, namePredmet, score) VALUES (4, 'Информатика', 3)");
            statement.executeUpdate("insert into scoreStudents(id_st, namePredmet, score) VALUES (5, 'Информатика', 2)");

            statement.executeUpdate("insert into scoreStudents(id_st, namePredmet, score) VALUES (1, 'Биология', 3)");
            statement.executeUpdate("insert into scoreStudents(id_st, namePredmet, score) VALUES (2, 'Биология', 2)");
            statement.executeUpdate("insert into scoreStudents(id_st, namePredmet, score) VALUES (3, 'Биология', 5)");
            statement.executeUpdate("insert into scoreStudents(id_st, namePredmet, score) VALUES (4, 'Биология', 4)");
            statement.executeUpdate("insert into scoreStudents(id_st, namePredmet, score) VALUES (5, 'Биология', 2)");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnection();
        }
    }


    /*Вывести список студентов, сдавших определенный предмет, на оценку выше 3;
    Посчитать средний бал по определенному предмету;
    Посчитать средний балл по определенному студенту;
    Найтитри премета, которые сдали наибольшее количество студентов;*/
    public static void zad() {
        try {
            connect();

            System.out.println("Студенты сдавшие физику больше чем на три: ");
            ResultSet rs = statement.executeQuery("SELECT s.name FROM scorestudents JOIN students s on scorestudents.id_st = s.id WHERE score > 3 and namepredmet = 'Физика';");
            while (rs.next()) {
                String name = rs.getString("name");
                System.out.println(name);
            }

            System.out.println("Средний балл: ");
            rs = statement.executeQuery("SELECT  avg (score) FROM scorestudents;");
            while (rs.next()) {
                System.out.println(rs.getInt("avg"));
            }

            System.out.println("Средний балл Боба: ");
            rs = statement.executeQuery("SELECT avg(score) FROM scorestudents\n" +
                    "WHERE id_st = (\n" +
                    "    SELECT id FROM students WHERE name = 'Bob'\n" +
                    ");");
            while (rs.next()) {
                System.out.println(rs.getInt("avg"));
            }

            rs = statement.executeQuery("SELECT namepredmet, COUNT(*) AS succes\n" +
                    "FROM scorestudents\n" +
                    "WHERE score > 2\n" +
                    "GROUP BY namepredmet\n" +
                    "HAVING COUNT(*) >= 3\n" +
                    "ORDER BY succes DESC;");
            while (rs.next()) {
                System.out.println(rs.getString("namepredmet"));
            }

            rs = statement.executeQuery("SELECT s.name, scorestudents.namepredmet, scorestudents.score FROM scorestudents JOIN students s on scorestudents.id_st = s.id WHERE score > 3;\n");
//            rs.next();
            while(rs.next()){
                System.out.println(rs.getString("name")+" "+rs.getString("namepredmet")+" "+rs.getString("score"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnection();
        }
    }

    public static void connect() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "834GLoL5");
            statement = connection.createStatement();

        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Не удалось подключиться");
        }
    }

    public static void disconnection() {
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

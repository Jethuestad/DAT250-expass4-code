package no.hvl.dat110.rest.counters;

import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.put;
import static spark.Spark.post;
import static spark.Spark.delete;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Hello world!
 */
public class App {

    static HashMap<Integer, Todo> todoMap = new HashMap<>();

    public static void main(String[] args) {

        if (args.length > 0) {
            port(Integer.parseInt(args[0]));
        } else {
            port(8080);
        }

        todoMap.put(0, new Todo());

        after((req, res) -> {
            res.type("application/json");
        });

        get("/hello", (req, res) -> "Hello World!");

        get("/todo", (req, res) -> {

            Gson gson = new Gson();

            return gson.toJson(todoMap);
        });

        get("/todo/:id", (req, res) -> {

            Gson gson = new Gson();

            int id = Integer.parseInt(req.params("id"));

            return todoMap.get(id).toJson();
        });

        //	get("/counters/summary", (req, res) -> todoMap.getSummary());

        //	get("/counters/description", (req, res) -> todo.getDescription());


        put("/todo/:id", (req, res) -> {

            Gson gson = new Gson();

            int id = Integer.parseInt(req.params("id"));

            todoMap.put(id, gson.fromJson(req.body(), Todo.class));

            return todoMap.get(id).toJson();

        });

        post("/todo", (req, res) -> {

            Gson gson = new Gson();

            int newId = todoMap.size();
            while (todoMap.containsKey(newId)) {
                newId += 1;
            }

            todoMap.put(newId, gson.fromJson(req.body(), Todo.class));

            return todoMap.get(newId).toJson();

        });

        delete("/todo/:id", (req, res) -> {

            int id = Integer.parseInt(req.params("id"));

            Todo deletedTodo = todoMap.get(id);
            todoMap.remove(id);

            return deletedTodo.toJson();
        });
    }

}

package ru.pda.servlet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.pda.controller.PostController;
import ru.pda.exception.NotFoundException;
import ru.pda.repository.PostRepository;
import ru.pda.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
  final static String POSTS = "/api/posts";
  final static String ID_POSTS = "/api/posts/\\d+";
  final static String GET = "GET";
  final static String POST = "POST";
  final static String DELETE = "DELETE";
  final static String PATH_DELIM = "/";
  private PostController controller;

  @Override
  public void init() {

//    final var repository = new PostRepository();
//    final var service = new PostService(repository);
//    controller = new PostController(service);
    final var context = new AnnotationConfigApplicationContext("ru.pda");
    controller = context.getBean(PostController.class);
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) {

    // если деплоились в root context, то достаточно этого
    try {
      final var path = req.getRequestURI();
      final var method = req.getMethod();

      // primitive routing
      if (method.equals(GET) && path.equals(POSTS)) {
        controller.all(resp);
        return;
      }
      if (method.equals(GET) && path.matches(ID_POSTS)) {
        // easy way
        controller.getById(getIdFromRequestURI(path), resp);
        return;
      }
      if (method.equals(POST) && path.equals(POSTS)) {
        controller.save(req.getReader(), resp);
        return;
      }
      if (method.equals(DELETE) && path.matches(ID_POSTS)) {
        // easy way
        controller.removeById(getIdFromRequestURI(path), resp);
        return;
      }
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } catch (NotFoundException e) {
      e.printStackTrace();
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } catch (Exception e) {
      e.printStackTrace();
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }

  private long getIdFromRequestURI(String path) {
    return Long.parseLong(path.substring(path.lastIndexOf(PATH_DELIM)+1));
  }
}


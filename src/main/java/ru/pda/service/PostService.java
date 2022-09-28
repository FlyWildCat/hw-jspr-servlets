package ru.pda.service;

import ru.pda.exception.NotFoundException;
import ru.pda.model.Post;
import ru.pda.repository.PostRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PostService {
  private final PostRepository repository;

  public PostService(PostRepository repository) {
    this.repository = repository;
  }

  public Collection<Post> all() {
    return repository.all();
  }

  public Post getById(long id) {
    return repository.getById(id).orElseThrow(NotFoundException::new);
  }

  public Post save(Post post) {
    return repository.save(post);
  }

  public void removeById(long id) {
    repository.removeById(id);
  }
}


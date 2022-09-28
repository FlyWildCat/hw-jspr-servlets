package ru.pda.repository;

import ru.pda.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepository {
  private final ConcurrentHashMap<Long, Post> storage;
  private final AtomicLong idCounter;

  public PostRepository() {
    this.storage = new ConcurrentHashMap<>();
    this.idCounter = new AtomicLong(0);
  }

  public List<Post> all() {
    return new ArrayList<>(storage.values());
  }

  public Optional<Post> getById(long id) {
    return Optional.ofNullable(storage.get(id));
  }

  public Post save(Post post) {
    final var id = post.getId();
    if (id == 0) {
      post.setId(idCounter.incrementAndGet());
      storage.put(post.getId(), post);
    }
    else if (storage.containsKey(id)) {
      storage.replace(id, post);
    } else {
      storage.put(id, post);
    }
    return post;
  }

  public void removeById(long id) {
    storage.remove(id);
  }
}

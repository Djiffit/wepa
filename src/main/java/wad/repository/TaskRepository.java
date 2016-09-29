package wad.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wad.domain.Tag;
import wad.domain.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long>{

    Task findByName(String name);

    PageRequest pr = new PageRequest(0, 500, Sort.Direction.DESC, "priority");

    @Query("SELECT t FROM Task t WHERE :tag MEMBER OF t.tags")
    List<Task> findByTag(@Param("tag") Tag tag);
}

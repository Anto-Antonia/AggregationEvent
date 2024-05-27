package com.example.aggregationevent.repository;

import com.example.aggregationevent.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    @Query("SELECT e from Event e where lower(e.title) like lower(concat('%', :keyword, '%'))")
    List<Event> findByTitleContainingKeyword(@Param("keyword") String keyword);

    @Query("select e from Event e where lower(e.title) like lower(concat('%', :title, '%'))")
    List<Event> findByTitleContainingIgnoreCase(@Param("title")String title);

    @Query("select e from Event e join e.user u where u.id = :id")
    List<Event> findByUserId(@Param("id")Integer id);
}

package io.anymobi.springbootwebjpa.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {

    Page<Post> findByTitleContains(String title, Pageable pageable);

    int countByTitleContains(String title);

    int deleteByTitleContains(String title);

    boolean existsByTitleContains(String title);



}


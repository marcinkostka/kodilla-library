package com.crud.kodillalibrary.repository;

import com.crud.kodillalibrary.domain.BookCopy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface BookCopyRepository extends CrudRepository<BookCopy, Long> {
    @Override
    List<BookCopy> findAll();

    Optional<BookCopy> findById(Long id);

    @Override
    BookCopy save(BookCopy bookCopy);

    @Query(nativeQuery = true)
    Integer getCountOfBookCopiesByBookTitleAndAvailable(@Param("TITLE") String title);
}
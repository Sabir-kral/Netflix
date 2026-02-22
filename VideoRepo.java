package az.developia.demo.Repo;


import az.developia.demo.Entity.AboneEntity;
import az.developia.demo.Entity.ManagerEntity;
import az.developia.demo.Entity.UserEntity;
import az.developia.demo.Entity.VideoEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VideoRepo extends JpaRepository<VideoEntity, Long> {

    List<VideoEntity> findByUploadedBy(String username);



    @Query(value = "select * from videos where active = true", nativeQuery = true)
    List<VideoEntity> getAllActive();
    @Query(value = "select * from videos where name = ?1",nativeQuery = true)
    List<VideoEntity> findByName(String name);


    @Query(value = "select * from videos where age_limit = ?1",nativeQuery = true)
    List<VideoEntity> findByAgeLimit(Long ageLimit);




}

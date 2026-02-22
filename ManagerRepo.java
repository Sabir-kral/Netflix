package az.developia.demo.Repo;

import az.developia.demo.Entity.ManagerEntity;
import az.developia.demo.Entity.UserEntity;
import az.developia.demo.Entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ManagerRepo extends JpaRepository<ManagerEntity,Long> {

    @Query(value = "select * from video where aktiv = false ",nativeQuery = true)
    Optional<VideoEntity> removeVideoByDeaktiv(Boolean aktiv);



    Optional<ManagerEntity> findByName(String name);
}

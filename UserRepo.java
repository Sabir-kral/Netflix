package az.developia.demo.Repo;

import az.developia.demo.Entity.AboneEntity;
import az.developia.demo.Entity.UserEntity;
import az.developia.demo.Entity.VideoEntity;
import az.developia.demo.Response.UserResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepo extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findManagerByUsername(String username);

    Optional<UserEntity> findByAbone(AboneEntity abone);


    @Transactional
    @Modifying
    @Query(value = "insert into user_plans(user_id,plan_id) select ?1,id from abone where basic = 1",nativeQuery = true)
    void assignPlanBasic(Long id);

    @Transactional
    @Modifying
    @Query(value = "insert into user_plans(user_id,plan_id) select ?1,id from abone where premium = 1",nativeQuery = true)
    void assignPlanPremium(Long id);
    @Transactional
    @Modifying
    @Query(value = "insert into user_plans(user_id,plan_id) select ?1, id from abone where standard = 1",nativeQuery = true)
    void assignPlanStandard(Long id);
}

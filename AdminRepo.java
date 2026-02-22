    package az.developia.demo.Repo;

    import az.developia.demo.Entity.*;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.stereotype.Repository;

    import java.time.LocalDate;
    import java.util.Optional;

    @Repository
    public interface AdminRepo extends JpaRepository<AdminEntity,Long> {



    }

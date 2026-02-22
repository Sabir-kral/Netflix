package az.developia.demo.Service;

import az.developia.demo.Entity.AboneEntity;
import az.developia.demo.Entity.UserEntity;
import az.developia.demo.Mapper.AboneMapper;
import az.developia.demo.Repo.AboneRepo;
import az.developia.demo.Repo.UserRepo;
import az.developia.demo.Request.AboneRequest;
import az.developia.demo.Response.AboneResponse;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AboneService {
    private final AboneRepo aboneRepo;
    private final UserRepo userRepo;
    private final LogService logService;

    public AboneResponse add(AboneRequest request){
        AboneEntity aboneEntity = new AboneEntity();
        aboneEntity.setName(request.getName());

        aboneRepo.save(aboneEntity);

        return AboneMapper.toDTO(aboneEntity);
    }

    public void delete(Long id){
        AboneEntity aboneEntity = aboneRepo.findById(id).orElseThrow(()->new RuntimeException("Not Found"));
        aboneRepo.delete(aboneEntity);

    }

    public void redakte(Long userId,String name){

        UserEntity user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        AboneEntity plan = aboneRepo.findByNameIgnoreCase(name)
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        user.getAbone().clear();

        user.getAbone().add(plan);

        userRepo.save(user);

    }

    public AboneEntity findById(Long id){
        return aboneRepo.findById(id).orElseThrow(()->new RuntimeException("Not Found"));
    }

    public List<AboneResponse> findAll(){
        return AboneMapper.toDTOLIst(aboneRepo.findAll());
    }

}

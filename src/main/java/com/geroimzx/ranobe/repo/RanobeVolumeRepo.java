package com.geroimzx.ranobe.repo;

import com.geroimzx.ranobe.model.RanobeVolume;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RanobeVolumeRepo extends CrudRepository<RanobeVolume, Long> {
    boolean existsByRanobePageIdAndVolumeNum(Long id, int volumeNum);
    RanobeVolume findByRanobePageIdAndVolumeNum(Long id, int volumeNum);
}

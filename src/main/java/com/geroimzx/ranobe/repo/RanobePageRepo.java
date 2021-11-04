package com.geroimzx.ranobe.repo;

import com.geroimzx.ranobe.model.RanobePage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface RanobePageRepo extends CrudRepository<RanobePage, Long> {

    RanobePage findByName(String name);

    @Transactional(readOnly = true)
    @Query("SELECT new com.geroimzx.ranobe.model.RanobePage(rp.id, rp.name) FROM RanobePage rp")
    RanobePage findIdByName(String name);

    @Transactional(readOnly = true)
    @Query("SELECT new com.geroimzx.ranobe.model.RanobePage(rp.id, rp.name, rp.posterFileUrl) FROM RanobePage rp")
    List<RanobePage> findAllOnlyBasicValue();

   /*//TODO:
    @Transactional(readOnly = true)
    @Query("SELECT new com.geroimzx.ranobe.model.RanobePage(rp.id, rp.name, rp.alternative_names, rp.releaseYear, rp.status, rp.country, rpg.genres_list, rp.volumeOrig, rp.volume, rp.description, rp.posterFileUrl)" +
            "  FROM RanobePage as rp, RanobePage.genresList as rpg, RanobeVolume as rv where rp.id=:id and rpg.ranobe_page_id=:id and rv.ranobe_page_id=:id")
    RanobePage findByIdL(Long id);//, rv.id, rv.name, rv.volume_num*/

    /*//TODO:
    @Transactional(readOnly = true)
    @Query("SELECT new com.geroimzx.ranobe.model.RanobePage(rp.id, rp.name, rp.alternative_names, rp.releaseYear, rp.volumeOrig, rp.volume, rp.description, rp.posterFileUrl)" +
            "  FROM RanobePage as rp where rp.id=:id")
    RanobePage findByIdL(Long id);//, rv.id, rv.name, rv.volume_num*/

    /*@Transactional(readOnly = true)
    @Query("SELECT new com.geroimzx.ranobe.model.RanobePage(rp.id, rp.name) FROM com.geroimzx.ranobe.model.RanobePage as rp where rp.id=:id")
    RanobePage findByIdL(Long id);//, rv.id, rv.name, rv.volume_num*/
}

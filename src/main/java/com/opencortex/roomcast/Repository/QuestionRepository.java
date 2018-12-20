package com.opencortex.roomcast.Repository;

import com.opencortex.roomcast.Model.Question;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends CrudRepository<Question, Long> {


}


//    @Modifying
////    @Query("update User u set u.status = :status where u.name = :name")
////    int updateUserSetStatusForName(@Param("status") Integer status, @Param("name") String name);
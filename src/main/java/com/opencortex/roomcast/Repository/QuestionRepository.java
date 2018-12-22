package com.opencortex.roomcast.Repository;

import com.opencortex.roomcast.Model.Question;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question, Long> {

    Iterable<Question> findAllByRoomId(long room_id);
    Iterable<Question> findQuestionsByIdAndRoom_Id(long questionId, long room_id);
}

package com.opencortex.roomcast.Repository;

import com.opencortex.roomcast.Model.RoomMessage;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<RoomMessage, Long> {

}

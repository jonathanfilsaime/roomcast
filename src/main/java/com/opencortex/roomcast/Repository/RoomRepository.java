package com.opencortex.roomcast.Repository;

import com.opencortex.roomcast.Model.RoomData;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<RoomData, Long> {

}

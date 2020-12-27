package com.travellingfreak.itinerary.api.dataproviders.repositories.planning.activities;

import com.travellingfreak.itinerary.api.dataproviders.model.planning.activities.Attachment;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends CrudRepository<Attachment, Long> {

}

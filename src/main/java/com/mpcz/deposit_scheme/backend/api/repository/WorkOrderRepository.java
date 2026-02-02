package com.mpcz.deposit_scheme.backend.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.mpcz.deposit_scheme.backend.api.domain.WorkOrder;

public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {

	Optional<WorkOrder> findByConsumersApplicationDetailConsumerApplicationId(Long consumerApplicationId);

}

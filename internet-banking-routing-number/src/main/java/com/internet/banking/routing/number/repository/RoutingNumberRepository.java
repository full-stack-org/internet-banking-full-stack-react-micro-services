package com.internet.banking.routing.number.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.internet.banking.routing.number.entity.RoutingNumberEntity;

public interface RoutingNumberRepository extends JpaRepository<RoutingNumberEntity, Long> {

	RoutingNumberEntity findByRoutingNumber(String routingNumber);

}

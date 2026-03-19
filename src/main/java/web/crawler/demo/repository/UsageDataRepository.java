package web.crawler.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import web.crawler.demo.repository.entity.UsageData;

public interface UsageDataRepository extends JpaRepository<UsageData, Integer> {

}

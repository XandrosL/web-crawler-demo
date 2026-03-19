package web.crawler.demo.repository.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import web.crawler.demo.domain.TitleFilter;

@Entity
@Table(name = "usage_data", schema = "metrics", indexes = {
        @Index(name = "idx_usage_data_title_filter", columnList = "title_filter")
})
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UsageData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "timestamp", nullable = false)
    LocalDateTime timestamp;

    @Column(name = "input_limit")
    Integer inputLimit;

    @Column(name = "input_filter")
    String inputFilter;

    @Enumerated(EnumType.STRING)
    @Column(name = "applied_filter", nullable = false)
    TitleFilter appliedFilter;

    @Column(name = "result_count", nullable = false)
    Integer resultCount;
}
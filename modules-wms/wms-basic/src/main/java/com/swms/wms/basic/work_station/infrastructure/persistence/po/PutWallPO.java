package com.swms.wms.basic.work_station.infrastructure.persistence.po;

import com.swms.utils.base.BaseUserPO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    indexes = {
        @Index(unique = true, name = "idx_put_wall_code_station", columnList = "putWallCode,workStationId"),
        @Index(unique = true, name = "idx_work_station_id", columnList = "workStationId")
    }
)
public class PutWallPO extends BaseUserPO {

    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.utils.id.IdGenerator")
    private Long id;

    @Column(nullable = false, columnDefinition = "bigint(11) comment '工作站ID'")
    private Long workStationId;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '播种墙编码'")
    private String putWallCode;

    @Column(nullable = false, columnDefinition = "varchar(128) comment '播种墙名称'")
    private String putWallName;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '容器规格'")
    private String containerSpecCode;

    private boolean deleted;

    private boolean enable;

    @Version
    private Long version;
}

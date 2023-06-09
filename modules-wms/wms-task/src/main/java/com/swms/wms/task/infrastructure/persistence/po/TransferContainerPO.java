package com.swms.wms.task.infrastructure.persistence.po;

import com.swms.utils.base.BaseUserPO;
import jakarta.persistence.*;
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
                @Index(name = "idx_transfer_container_code", columnList = "transferContainerCode")
        }
)
public class TransferContainerPO extends BaseUserPO {

    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.utils.id.IdGenerator")
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '周转容器编码'")
    private String transferContainerCode;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '工作站编码'")
    private String stationCode;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '播种墙格口'")
    private String putWallSlotCode;

    @Column(nullable = false, columnDefinition = "int(11) comment '第几个周转箱'")
    private Integer containerIndex = 0;

    @Column(nullable = false, columnDefinition = "int(11) comment '总共多少个周转箱'")
    private Integer total = 0;

    @Column(nullable = false, columnDefinition = "varchar(128) comment '周转箱目的地'")
    private String destination = "";
}
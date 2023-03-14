package com.swms.station;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.swms.utils.mq.MqClient;
import com.swms.wms.api.warehouse.dto.PutWallSlotDTO;
import com.swms.wms.api.warehouse.dto.WorkStationConfigDTO;
import jakarta.annotation.Nullable;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.redisson.codec.JsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Map;

@SpringBootApplication(scanBasePackages = {"com.swms"})
public class StationApplication {
    public static void main(String[] args) {
        SpringApplication.run(StationApplication.class, args);

    }

    @Autowired
    private MqClient mqClient;

    @Autowired
    private RedissonClient redissonClient;

    @Bean
    public void test() {
        new Thread(() -> {
            while (true) {

                WorkStationConfigDTO workStationConfigDTO = new WorkStationConfigDTO();
                workStationConfigDTO.setStationCode("123");
                mqClient.sendMessage("station", workStationConfigDTO);

                List<PutWallSlotDTO> putWallSlotDTOList = Lists.newArrayList();
                PutWallSlotDTO putWallSlotDTO = new PutWallSlotDTO();
                putWallSlotDTO.setStationCode("123");
                putWallSlotDTOList.add(putWallSlotDTO);
                mqClient.sendMessage("station1", putWallSlotDTOList);

                try {
                    Thread.sleep(1000l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


//        redissonClient.getTopic("station", new JsonJacksonCodec()).addListener(Map.class, new MessageListener<Map>() {
//            @Override
//            public void onMessage(CharSequence channel, Map msg) {
//                System.out.println("channel: " + channel + ", msg: " + msg);
//            }
//        });
    }
}

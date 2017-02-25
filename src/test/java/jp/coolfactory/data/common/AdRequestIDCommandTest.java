package jp.coolfactory.data.common;

import jp.coolfactory.data.module.AdRequest;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;

import static org.junit.Assert.*;

/**
 * Created by wangqi on 25/2/2017.
 */
public class AdRequestIDCommandTest {

    @Test
    public void handle() throws Exception {
        AdRequestIDCommand command = new AdRequestIDCommand();
        AdRequest req = genTalkingDataAdRequest();
        command.handle(req);
        String stat_id = req.getStat_id();
        System.out.println("stat_id: " + stat_id);
        assertNotNull(stat_id);
        assertNotNull(req.getStat_id());
    }

    public AdRequest genTalkingDataAdRequest() {
        LocalDateTime click_time = LocalDateTime.of(2017, Month.FEBRUARY, 25, 15, 02, 0);
        LocalDateTime install_time = LocalDateTime.of(2017, Month.FEBRUARY, 25, 15, 03, 40);
        AdRequest req = new AdRequest();
        req.setInstall_time(install_time.atZone(ZoneId.systemDefault()));
        //It's tdid
        req.setPlat_id("h774620efcf69f96f5d7fcd5987248d08");
        req.setIos_ifa("6AD83390-DB68-4108-B18A-03C02D337841");
        req.setMatch_type("1");
        req.setDevice_type("iPhone 7");
//        req.setDevice_type("iPad pro wifi");
        return req;
    }
}
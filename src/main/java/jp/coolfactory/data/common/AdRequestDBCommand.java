package jp.coolfactory.data.common;

import com.google.common.collect.Queues;
import jp.coolfactory.data.db.DBUtil;
import jp.coolfactory.data.module.AdRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * Save the AdRequest to database.
 *
 * Created by wangqi on 22/2/2017.
 */
public class AdRequestDBCommand implements Handler<AdRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdRequestDBCommand.class);

    private static final int DEFAULT_DRAIN_SIZE = 10;
    private static final int DEFAULT_WAIT_SECOND = 5;

    private static final String SQL =
            "insert into ad_{ (action,account_key,source,stat_id,app_key,os_version,device_id,device_type,device_brand," +
                    "device_carrier,device_model,lang,plat_id,user_agent,publisher_id,publisher_name,click_ip,click_time," +
                    "bundle_id,install_ip,install_time,agency_name,site_id,site_name,match_type,campaign_id,campaign_name," +
                    "ad_url,ad_name,region_name,country_code,currency_code,existing_user,imp_time,stat_click_id," +
                    "stat_impression_id,payout,referral_source,referral_url,revenue,revenue_usd,status,status_code," +
                    "tracking_id,ios_ifa,ios_ifv,google_aid,pub_camp_id,pub_camp_name,pub_camp_ref,pub_adset,pub_ad," +
                    "pub_keyword,pub_place,pub_sub_id,pub_sub_name,adv_camp_id,adv_camp_name,adv_camp_ref,adv_adset," +
                    "adv_ad,adv_keyword,adv_place,adv_sub_id,adv_sub_name,sdk,sdk_version,game_user_id,os_jailbroke," +
                    "pub_pref_id,pub_sub1,pub_sub2,pub_sub3,pub_sub4,pub_sub5,cost_model,cost,ip_from,ip_to,city_code," +
                    "metro_code) values ()";

    private BlockingQueue<AdRequest> queue = new LinkedBlockingQueue<>();

    @Override
    public CommandStatus handle(AdRequest adRequest) {
        try {
            queue.put(adRequest);
        } catch (InterruptedException e) {
            LOGGER.warn("handle is interrupted.");
            return CommandStatus.Fail;
        }
        return CommandStatus.Continue;
    }

    /**
     * Start the worker and run forever
     */
    public final void runWorker() {
        LOGGER.info("ScheduleWorker is activated.");
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<AdRequest> list = new ArrayList<>();
                    //If no objects avaiable, then wait
                    list.add(queue.take());
                    queue.drainTo(list, DEFAULT_DRAIN_SIZE);
                    DBUtil.insertBatch(SQL, AdRequestDBCommand::<AdRequest>insert, list);
                    //TODO Add CouldWatch here if necessary.
                    LOGGER.info("Save " + list.size() + " requests into database. ");
                } catch (Exception e) {
                    LOGGER.warn("DBCommand worker runs into an error", e);
                }
            }
        }, 0, DEFAULT_WAIT_SECOND, TimeUnit.SECONDS);
    }

    /**
     * Add the parameters into PreparedStatement as batch.
     * @param ps
     * @param req
     */
    public static void insert(PreparedStatement ps, AdRequest req) {
        try {
            ps.setString(1, req.getStat_id());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

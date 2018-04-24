package jp.coolfactory.anti_fraud.controller;

import java.sql.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import jp.coolfactory.anti_fraud.db.CheckType;
import jp.coolfactory.anti_fraud.module.AfAccount;
import jp.coolfactory.anti_fraud.module.AfCampaign;
import jp.coolfactory.anti_fraud.module.AfIPFilter;
import jp.coolfactory.anti_fraud.module.AfSite;
import jp.coolfactory.data.controller.Controller;
import jp.coolfactory.data.db.DBUtil;
import jp.coolfactory.data.db.ResultSetProcessor;
import jp.coolfactory.data.util.ConfigUtil;
import jp.coolfactory.data.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wangqi on 25/11/2016.
 */
public class AntiFraudController implements Controller {

    private final static Logger LOGGER = LoggerFactory.getLogger(AntiFraudController.class.getName());

    private static final AntiFraudController instance = new AntiFraudController();

    /**
     * The map that stores the AfAccount by account_name (not account_id)
     */
    private Map<String, AfAccount> _accounts = new ConcurrentHashMap<String, AfAccount>();

    /**
     * The map that stores the AfSite by external_id (the site_id from MAT)
     */
    private Map<String, AfSite> _sites = new ConcurrentHashMap<String, AfSite>();

    /**
     * The map that stores the AfCampaign by ID
     */
    private Map<Integer, AfCampaign> _campaigns = new ConcurrentHashMap<Integer, AfCampaign>();

    /**
     * The IP whitelist and blacklist filter, which is shared by all campaigns and sites.
     */
    private AfIPFilter _ipFilter = new AfIPFilter();

    private AntiFraudController() {
    }

    public static final AntiFraudController getInstance() {
        return instance;
    }

    @Override
    public void init() {
        loadAccounts();
        loadSites();
        loadCampaigns();
        loadIPFilter();
    }

    @Override
    public void reload() {

    }

    /**
     * Load the detection config
     * @return
     */
    public final void loadAccounts() {
        DBUtil.select(
                "select id, created, account_name, account_pass, account_email, timezone from "
                        + ConfigUtil.getAntiFraudDatabaseSchema()
                        + ".af_account",
                new ResultSetProcessor() {
                    @Override
                    public void process(ResultSet resultSet) throws SQLException {
                        while (resultSet.next() ) {
                            AfAccount account = createAccountFromResultSet(resultSet);
                        }
                    }
        });
    }

    /**
     * Load all sites
     */
    public final void loadSites() {
        DBUtil.select(
                "select s.id, s.created, s.name, s.external_id, s.account_id, a.account_name, s.adwords_link_id from "
                        + ConfigUtil.getAntiFraudDatabaseSchema()
                        + ".af_site s inner join af_account a on s.account_id = a.id",
                new ResultSetProcessor() {
                    @Override
                    public void process(ResultSet resultSet) throws SQLException {
                        while (resultSet.next() ) {
                            AfSite afsite = createSiteFromResultSet(resultSet);
                        }
                    }
                });
    }

    /**
     * Load all campaigns.
     */
    public final void loadCampaigns() {
        String sql = "select s.id, s.created, s.name, s.site_id, a.name site_name, s.include_countries, s.include_devices, s.include_lang, s.include_os, " +
                " s.exclude_countries, s.exclude_devices, s.exclude_lang, s.exclude_os, s.include_carrier, s.exclude_carrier, s.is_default, s.timezone from "
                + ConfigUtil.getAntiFraudDatabaseSchema()
                + ".af_campaign s inner join af_site a on s.site_id = a.id";
        DBUtil.select(sql,
                new ResultSetProcessor() {
                    @Override
                    public void process(ResultSet resultSet) throws SQLException {
                        while (resultSet.next() ) {
                            AfCampaign afCampaign = createCampaignFromResultSet(resultSet);
                        }
                    }
                });
    }

    /**
     * Load all ip filters.
     */
    public final void loadIPFilter() {
        DBUtil.select(
                "select ip, false as type from "
                        + ConfigUtil.getAntiFraudDatabaseSchema()
                        + ".ip_whitelist union "
                        + " select ip, true as type from "
                        + ConfigUtil.getAntiFraudDatabaseSchema()
                        + ".ip_blacklist",
                new ResultSetProcessor() {
                    @Override
                    public void process(ResultSet resultSet) throws SQLException {
                        ArrayList<String> whiteList = new ArrayList<String>(10);
                        ArrayList<String> blackList = new ArrayList<String>(500);
                        while (resultSet.next() ) {
                            String ip = resultSet.getString("ip");
                            boolean isBlackType = resultSet.getBoolean("type");
                            if ( isBlackType ) {
                                blackList.add(ip);
                            } else {
                                whiteList.add(ip);
                            }
                        }
                        _ipFilter.addToWhiteList(whiteList);
                        _ipFilter.addToBlackList(blackList);
                    }
                });
    }

    /**
     * It reloads given account object into memory by account_name.
     * If the account_name is null or empty, null will be returned.
     * @param account_name
     * @return
     */
    public final AfAccount reloadAccount(String account_name) {
        if ( account_name == null || account_name == "") {
            LOGGER.warn("The account_name is empty for reloadAccount.");
            return null;
        }
        DBUtil.select(
                "select id, created, account_name, account_pass, account_email from "
                        + ConfigUtil.getAntiFraudDatabaseSchema()
                        + ".af_account where account_name = ?",
                new ResultSetProcessor() {
                    @Override
                    public void process(ResultSet resultSet) throws SQLException {
                        if (resultSet.next()) {
                            createAccountFromResultSet(resultSet);
                        }
                    }
                }, account_name);
        return null;
    }


    /**
     * Get given account by its name. May be null.
     * @param accountName
     * @return
     */
    public final AfAccount getAccount(String accountName) {
        return _accounts.get(accountName);
    }

    /**
     * Get all accounts as an unmodifiable list.
     * @return
     */
    public final Collection<AfAccount> getAccounts() {
        return Collections.<AfAccount>unmodifiableCollection(_accounts.values());
    }

    /**
     * Get given site by its site name. May be null
     * @param af_site_id
     * @return
     */
    public final AfSite getSite(String af_site_id) {
        if (StringUtil.isNotEmptyString(af_site_id)) {
            return _sites.get(af_site_id);
        } else {
            return null;
        }
    }

    /**
     * Get all sites as an unmodifiable list.
     * @return
     */
    public final Collection<AfSite> getSites() {
        return Collections.<AfSite>unmodifiableCollection(_sites.values());
    }

    /**
     * Get the global IP filter.
     * @return
     */
    public final AfIPFilter getIPFilter() {
        return _ipFilter;
    }

    // ----------------------------------- Utility -----------------------------------

    private AfAccount createAccountFromResultSet(ResultSet resultSet) throws SQLException {
        AfAccount account = new AfAccount();
        account.setId(resultSet.getInt("id"));
        account.setCreated(resultSet.getDate("created"));
        account.setName(resultSet.getString("account_name"));
        account.setPass(resultSet.getString("account_pass"));
        account.setEmail(resultSet.getString("account_email"));
        account.setTimezone(resultSet.getString("timezone"));
        _accounts.put(account.getName(), account);
        return account;
    }

    private AfSite createSiteFromResultSet(ResultSet resultSet) throws SQLException {
        AfSite site = new AfSite();
        site.setId(resultSet.getInt("id"));
        site.setCreated(resultSet.getDate("created"));
        site.setName(resultSet.getString("name"));
        site.setExternalId(resultSet.getString("external_id"));
        site.setAccountId(resultSet.getInt("account_id"));
        site.setAccountName(resultSet.getString("account_name"));
        site.setAdwordsLinkId(resultSet.getString("adwords_link_id"));
        _sites.put(String.valueOf(site.getId()), site);
        AfAccount account = _accounts.get(site.getAccountName());
        if ( account != null ) {
            account.addSite(site);
        }
        return site;
    }

    private AfCampaign createCampaignFromResultSet(ResultSet resultSet) throws SQLException {
        AfCampaign campaign = new AfCampaign();
        campaign.setId(resultSet.getInt("id"));
        campaign.setCreated(resultSet.getDate("created"));
        campaign.setName(resultSet.getString("name"));
        campaign.setSiteId(resultSet.getInt("site_id"));
        campaign.setSiteName(resultSet.getString("site_name"));
        campaign.setDefault(resultSet.getBoolean("is_default"));
        campaign.setTimezone(resultSet.getString("timezone"));
        {
            String countryList = resultSet.getString("include_countries");
            campaign.parseStringValue(true, countryList, CheckType.COUNTRY);
            countryList = resultSet.getString("exclude_countries");
            campaign.parseStringValue(false, countryList, CheckType.COUNTRY);
        }
        {
            String deviceList = resultSet.getString("include_devices");
            campaign.parseStringValue(true, deviceList, CheckType.DEVICE);
            deviceList = resultSet.getString("exclude_devices");
            campaign.parseStringValue(false, deviceList, CheckType.DEVICE);
        }
        {
            String langList = resultSet.getString("include_lang");
            campaign.parseStringValue(true, langList, CheckType.LANG);
            langList = resultSet.getString("exclude_lang");
            campaign.parseStringValue(false, langList, CheckType.LANG);
        }
        {
            String osList = resultSet.getString("include_os");
            campaign.parseStringValue(true, osList, CheckType.OS);
            osList = resultSet.getString("exclude_os");
            campaign.parseStringValue(false, osList, CheckType.OS);
        }
        {
            String osList = resultSet.getString("include_carrier");
            campaign.parseStringValue(true, osList, CheckType.CARRIER);
            osList = resultSet.getString("exclude_carrier");
            campaign.parseStringValue(false, osList, CheckType.CARRIER);
        }
        this._campaigns.put(campaign.getId(), campaign);
        for ( AfSite site : _sites.values() ) {
            if ( campaign.getSiteId() == site.getId() ) {
                site.addCampaign(campaign);
                break;
            }
        }
        return campaign;
    }


}

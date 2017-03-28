package jp.coolfactory.anti_fraud.module;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wangqi on 24/11/2016.
 */
public class FraudDetectionFactory {

    private static final FraudDetectionFactory instance = new FraudDetectionFactory();

    private final ArrayList<FraudDetection> detectorList = new ArrayList<FraudDetection>();

    private FraudDetectionFactory() {
        detectorList.add(new FraudCommonDetection());
        detectorList.add(new FraudInstallSpeedDetection());
    }

    /**
     * Get the static instance of itself.
     * @return
     */
    public static final FraudDetectionFactory getInstance() {
        return instance;
    }

    /**
     * Get the proper Fraud type
     * @param params The parameter sent by request
     * @return Status
     */
    public Status evaluateInstall(HashMap<String, String> params) {
        Status status = Status.OK;
        for (FraudDetection detector : detectorList ) {
            if ( detector.doesItApply(params) ) {
                status = detector.evaluateInstall(params);
                if ( status != Status.OK ) {
                    return status;
                }
            }
        }
        return status;
    }

}

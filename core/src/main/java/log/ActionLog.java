package log;

import com.iliangfeng.core.utils.time.DateConvert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Dylan
 */
public class ActionLog implements ActionLogger {
    private static final String LOG_DATE_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS";
    private static final String LOG_SPLITER = " | ";
    private String action;
    private String requestId;
    private ActionResult result = ActionResult.SUCCESS;
    private Date requestDate = new Date();
    Logger logger = LoggerFactory.getLogger(ActionLog.class);
    Map<String, String> context = new TreeMap<>();

    @Override
    public void logContext(String key, String value) {
        logger.debug("{}={}", key, value);
        context.put(key, value);
    }

    void action(String action) {
        this.action = action;
        logger.debug("action={}", action);
    }

    void requestId(String requestId) {
        this.requestId = requestId;
        logger.debug("requestId={}", requestId);
    }

    void result(ActionResult result) {
        this.result = result;
        logger.debug("result={}", result);
    }

    void save() {
        logContext("elapsedTime", String.valueOf(System.currentTimeMillis() - requestDate.getTime()));
        LoggerFactory.getLogger("action").info(buildActionLog());
    }

    private String buildActionLog() {
        StringBuilder actionLogBuilder = new StringBuilder();
        actionLogBuilder.append("ts=").append(DateConvert.toString(requestDate, LOG_DATE_FORMAT))
            .append(LOG_SPLITER)
            .append("requestId=").append(this.requestId != null ? this.requestId : "unknown")
            .append(LOG_SPLITER)
            .append("action=").append(this.action)
            .append(LOG_SPLITER);
        for (Map.Entry<String, String> entry : context.entrySet()) {
            actionLogBuilder.append(entry.getKey()).append("=").append(entry.getValue())
                .append(LOG_SPLITER);
        }
        return actionLogBuilder.toString();
    }

    public String getAction() {
        return action;
    }

    public String getRequestId() {
        return requestId;
    }

    public ActionResult getResult() {
        return result;
    }
}

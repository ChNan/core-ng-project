package core.framework.api.module;

import core.framework.api.Module;
import core.framework.api.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;

/**
 * @author neo
 */
public final class SystemModule extends Module {
    private final String propertyFileName;
    private final Logger logger = LoggerFactory.getLogger(SystemModule.class);
    public SystemModule(String propertyFileName) {
        this.propertyFileName = propertyFileName;
    }

    @Override
    protected void initialize() {
        logger.info("[Debug-Start] SystemModule initialize");
        loadProperties(propertyFileName);

        property("sys.http.port").ifPresent(port -> http().port(Integer.parseInt(port)));

        property("sys.cache.host").ifPresent(host -> {
            if ("local".equals(host)) {
                cache().local();
            } else {
                cache().redis(host);
            }
        });

        property("sys.session.host").ifPresent(host -> {
            if ("local".equals(host)) {
                site().session().local();
            } else {
                site().session().redis(host);
            }
        });

        property("sys.cdn.host").ifPresent(hosts -> site().cdn().hosts(Strings.split(hosts, ',')));
        property("sys.cdn.version").ifPresent(version -> site().cdn().version(version));

        property("sys.log.actionLogPath").ifPresent(path -> {
            if ("console".equals(path)) {
                log().writeActionLogToConsole();
            } else {
                log().writeActionLogToFile(Paths.get(path));
            }
        });
        property("sys.log.traceLogPath").ifPresent(path -> {
            if ("console".equals(path)) {
                log().writeTraceLogToConsole();
            } else {
                log().writeTraceLogToFile(Paths.get(path));
            }
        });
        property("sys.log.remoteLogHost").ifPresent(host -> log().forwardLogToRemote(host));

        property("sys.rabbitMQ.host").ifPresent(hosts -> queue().hosts(Strings.split(hosts, ',')));
        property("sys.rabbitMQ.user").ifPresent(user -> queue().user(user));
        property("sys.rabbitMQ.password").ifPresent(password -> queue().password(password));

        property("sys.jdbc.url").ifPresent(url -> db().url(url));
        property("sys.jdbc.user").ifPresent(user -> db().user(user));
        property("sys.jdbc.password").ifPresent(password -> db().password(password));

        property("sys.redis.host").ifPresent(host -> redis().host(host));

        property("sys.elasticsearch.host").ifPresent(host -> search().host(host));

        property("sys.mongo.uri").ifPresent(uri -> mongo().uri(uri));
        logger.info("[Debug-End] SystemModule initialize");
    }
}

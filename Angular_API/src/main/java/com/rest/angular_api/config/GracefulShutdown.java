package com.rest.angular_api.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Gracefully Shutdown
 * - executable jar로 웹 컨테이너를 띄운 후, 업데이트등의 이슈로 서버를 재시작해야 하는 경우가 있다.
 * - 이런 경우네느 주의가 필요한데, 서버를 그냥 종료시켜 버리면 서버에서 열심히 처리중이었던 작업을 응답 못하고 종료해 버리기 때문이다.
 * - 그렇기 때문에, 처리중인 작업이 있다면 잘 마무리하고 종료시키는 작업이 필요하다. 이것을 Gracefully Shutdown이라고 한다.
 * - Linux에서는 작업을 종료시킬때, 몇가지 옵션을 제공하는데, kill 명령에 -TERM 혹은 -15를 옵션으로 주면 서버를 바로 종료시키지않고 서버에게 종료 요청을 보낸다.
 * - 해당 서버는 Terminate 요청을 받으면 현재 처리중인 작업을 모두 마무리하고 서버를 종료시킨다.
 *
 * GracefulShutdown class는 실행중인 작업을 마치고 종료하는 내용의 코드작성이며, 30초안에 종료되지 않으면 강제로 작업을 종료하도록 처리한다.
 *
 */
@Slf4j
public class GracefulShutdown implements TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {

    private static final int TIMEOUT = 30;

    private volatile Connector connector;

    @Override
    public void customize(Connector connector) {
        this.connector = connector;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        this.connector.pause();
        Executor executor = this.connector.getProtocolHandler().getExecutor();
        if (executor instanceof ThreadPoolExecutor) {
            try {
                ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
                threadPoolExecutor.shutdown();
                if(!threadPoolExecutor.awaitTermination(TIMEOUT, TimeUnit.SECONDS)) {
                    log.warn("Tomcat thread pool did not shut down gracefullt within " + TIMEOUT + " seconds." +
                            "proceeding with forceful shutdown");

                    threadPoolExecutor.shutdownNow();

                    if (!threadPoolExecutor.awaitTermination(TIMEOUT, TimeUnit.SECONDS)) {
                        log.error("Tomcat thread pool did not terminate");
                    }
                } else {
                    log.info("Tomcat thread pool has been gracefully shutdown ");
                }
            }catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}

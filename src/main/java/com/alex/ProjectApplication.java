package com.alex;

import com.alex.client.StackExchangeClient;
import com.alex.core.UpdateIndexTask;
import com.alex.db.ElasticClient;
import com.alex.resources.StackRes;
import com.alex.service.StackService;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProjectApplication extends Application<ProjectConfiguration> {

    public static void main(final String[] args) throws Exception {
        new ProjectApplication().run(args);
    }

    @Override
    public String getName() {
        return "Project";
    }

    @Override
    public void initialize(final Bootstrap<ProjectConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final ProjectConfiguration configuration,
                    final Environment environment) {
        final ElasticClient elasticClient = new ElasticClient();
        final StackService stackService = new StackService(elasticClient);
        final StackRes stackRes = new StackRes(stackService);
        environment.jersey().register(stackRes);

        final StackExchangeClient stackExchangeClient = new StackExchangeClient();
        Runnable r1 = new UpdateIndexTask(stackExchangeClient, elasticClient);
        Runnable r2 = new UpdateIndexTask(stackExchangeClient, elasticClient);

        ExecutorService pool = Executors.newFixedThreadPool(2);

        pool.execute(r1);
        pool.execute(r2);

        // pool shutdown ( Step 4)
        pool.shutdown();
    }

}

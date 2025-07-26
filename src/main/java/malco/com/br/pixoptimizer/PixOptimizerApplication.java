package malco.com.br.pixoptimizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class PixOptimizerApplication {
    public static final String GCP_PROJECT_DEVELOPMENT = "pixoptimizer";

    public static void main(String[] args) {
        Logger log = Logger.getLogger(PixOptimizerApplication.class.getSimpleName());
        String gcpProject = System.getenv(GCP_PROJECT_DEVELOPMENT);

        if (gcpProject == null) {
            gcpProject = "N/A";
        }

        String activeProfile;
        if (GCP_PROJECT_DEVELOPMENT.equals(gcpProject)) {
            activeProfile = "development";
        } else {
            activeProfile = "local";
        }

        log.info("GCP project = " + gcpProject + ", Active Profile = " + activeProfile);
        SpringApplication app = new SpringApplication(PixOptimizerApplication.class);
        app.setAdditionalProfiles(activeProfile);
        app.run(args);
    }

}

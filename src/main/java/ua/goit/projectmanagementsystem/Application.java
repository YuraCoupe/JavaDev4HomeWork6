package ua.goit.projectmanagementsystem;

import ua.goit.projectmanagementsystem.config.DatabaseManager;
import ua.goit.projectmanagementsystem.config.PostgresHikariProvider;
import ua.goit.projectmanagementsystem.config.PropertiesUtil;
import ua.goit.projectmanagementsystem.controller.PMSController;
import ua.goit.projectmanagementsystem.model.converter.*;
import ua.goit.projectmanagementsystem.repository.CompanyRepository;
import ua.goit.projectmanagementsystem.repository.DeveloperRepository;
import ua.goit.projectmanagementsystem.repository.ProjectRepository;
import ua.goit.projectmanagementsystem.repository.Repository;
import ua.goit.projectmanagementsystem.service.CompanyService;
import ua.goit.projectmanagementsystem.service.DeveloperService;
import ua.goit.projectmanagementsystem.service.ProjectService;
import ua.goit.projectmanagementsystem.view.Console;
import ua.goit.projectmanagementsystem.view.View;

public class Application {
    public static void main(String[] args) {
        PropertiesUtil util = new PropertiesUtil();

        DatabaseManager dbConnector = new PostgresHikariProvider(util.getHostname(), util.getPort(),
                util.getSchema(), util.getUser(), util.getPassword(), util.getJdbcDriver());

        Repository repository = new Repository(dbConnector);

        ProjectRepository projectRepository = new ProjectRepository(dbConnector);
        DeveloperRepository developerRepository = new DeveloperRepository(dbConnector);
        CompanyRepository companyRepository = new CompanyRepository(dbConnector);

        SkillConverter skillConverter = new SkillConverter();
        CompanyConverter companyConverter = new CompanyConverter();
        DeveloperConverter developerConverter = new DeveloperConverter(skillConverter, companyConverter);
        DeveloperShortConverter developerShortConverter = new DeveloperShortConverter();
        ProjectConverter projectConverter = new ProjectConverter();
        ProjectShortConverter projectShortConverter = new ProjectShortConverter();
        DeveloperProjectConverter developerProjectConverter = new DeveloperProjectConverter(developerConverter, projectConverter);

        ProjectService projectService = new ProjectService(projectRepository, developerShortConverter, developerConverter,
                projectConverter, projectShortConverter, developerProjectConverter);
        DeveloperService developerService = new DeveloperService(repository, developerShortConverter, developerConverter, developerProjectConverter);
        CompanyService companyService = new CompanyService(companyRepository, companyConverter);

        View view = new Console();

        PMSController pmsController = new PMSController(view, projectService, developerService, companyService);
        pmsController.run();
    }
}

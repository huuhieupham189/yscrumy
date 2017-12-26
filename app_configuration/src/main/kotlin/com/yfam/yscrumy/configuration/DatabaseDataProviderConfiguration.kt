package com.yfam.yscrumy.configuration

import com.yfam.yscrumy.core.data_provider.*
import com.yfam.yscrumy.data_provider.database.DatabaseDataProviderConfig
import com.yfam.yscrumy.data_provider.database.jpa.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import java.util.*
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
open class DatabaseDataProviderConfiguration {
    @Bean
    open fun databaseDataProviderConfig() = DatabaseDataProviderConfig(System.getenv("DATABASE_HOST")!!,
            System.getenv("DATABASE_PORT")!!.toInt(),
            System.getenv("DATABASE_CATALOG")!!,
            System.getenv("DATABASE_UTF8")!!.toBoolean(),
            System.getenv("DATABASE_USERNAME")!!,
            System.getenv("DATABASE_PASSWORD")!!,
            System.getenv("DATABASE_DEBUG")!!.toBoolean())

    @Bean
    @Primary
    open fun dataSource(config: DatabaseDataProviderConfig): DataSource {
        val dataSource = DriverManagerDataSource()
        dataSource.setDriverClassName("org.postgresql.Driver")
        dataSource.url = "jdbc:postgresql://${config.host}:${config.port}/${config.catalog}" +
                "?autoReconnect=true&verifyServerCertificate=false&useSSL=true" +
                if (config.utf8) "&useUnicode=yes&characterEncoding=UTF-8" else ""
        dataSource.username = config.username
        dataSource.password = config.password
        return dataSource
    }

    @Bean
    open fun entityManagerFactory(config: DatabaseDataProviderConfig,
                                  dataSource: DataSource): LocalContainerEntityManagerFactoryBean {
        val entityManagerFactoryBean = LocalContainerEntityManagerFactoryBean()
        entityManagerFactoryBean.dataSource = dataSource
        entityManagerFactoryBean.jpaVendorAdapter = HibernateJpaVendorAdapter()

        val additionalProperties = Properties()

        // configures the used database dialect. This allows Hibernate to create SQL
        // that is optimized for the used database.
        additionalProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")

        // specifies the action that is invoked to the database when
        // the Hibernate SessionFactory is created or closed.
        additionalProperties.put("hibernate.hbm2ddl.auto", "validate")

        //If the value of this property is true, Hibernate writes all SQL
        //statements to the console.
        additionalProperties.put("hibernate.show_sql", if (config.debug) "true" else "false");

        //If the value of this property is true, Hibernate will format the SQL
        //that is written to the console.
        additionalProperties.put("hibernate.format_sql", if (config.debug) "true" else "false");

        entityManagerFactoryBean.setJpaProperties(additionalProperties)
        return entityManagerFactoryBean
    }

    @Bean
    open fun unitOfWorkProvider(entityManagerFactory: EntityManagerFactory): UnitOfWorkProvider = JpaUnitOfWorkProvider(entityManagerFactory)

    @Bean
    open fun userRepository(): UserRepository = JpaUserRepository()

    @Bean
    open fun teamRepository(): TeamRepository = JpaTeamRepository()

    @Bean
    open fun projectRepository(): ProjectRepository = JpaProjectRepository()

    @Bean
    open fun pbiRepository(): PBIRepository = JpaPBIRepository()
@Bean
    open fun invitationRepository(): InvitationRepository=JpaInvitationRepository()
@Bean
    open fun project_memberRepository():Project_memberRepository=JpaProject_memberRepository()
    @Bean
    open fun sprintRepository():SprintRepository=JpaSprintRepository()
    @Bean
    open fun taskRepository():TaskRepository=JpaTaskRepository()
}
package br.mmarcalm;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import br.mmarcalm.persistence.Dao;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;
// import static com.tngtech.archunit.library.Architectures.layeredArchitecture;



@AnalyzeClasses(packages =  "main.java.br.mmarcalm")
public class FooArchitectTest {

    @ArchTest
    public static final ArchRule verificaDependenciaParaCamadaPersistencia = 
            classes().that().resideInAPackage("..persistence..")
            .should().onlyHaveDependentClassesThat().resideInAnyPackage("..persistence..", "..service..");

    @ArchTest
    public static final ArchRule verificaDependenciasNaCamadaDePersistencia = 
            noClasses().that().resideInAPackage("..persistence..")
            .should().dependOnClassesThat().resideInAnyPackage("..service..");

    @ArchTest
    public static final ArchRule verificaNomesClassesCamadaPersistencia = 
            classes().that().haveSimpleNameEndingWith("Dao")
            .should().resideInAPackage("..persistence..");

    @ArchTest
    public static final ArchRule verificaImplementacaoInterfaceDao = 
            classes().that().implement(Dao.class)
            .should().haveSimpleNameEndingWith("Dao");
    
    @ArchTest
    public static final ArchRule verificaDependenciasCiclicas = 
            slices().matching("br.mmarcalm.(*)..").should().beFreeOfCycles();

    /*
    @ArchTest
    public static final ArchRule verificaViolacaoCamadas = 
            layeredArchitecture()
            .layer("Service").definedBy("..service..")
            .layer("Persistence").definedBy("..persistence..")

            .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Service");

        */
}

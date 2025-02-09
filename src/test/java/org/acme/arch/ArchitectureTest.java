package org.acme.arch;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import jakarta.servlet.http.HttpServlet;
import org.slf4j.Logger;

@AnalyzeClasses(packages = "org.acme")
class ArchitectureTest {

    @ArchTest static final ArchRule NO_SYS_STREAM = NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;

    @ArchTest static final ArchRule NO_JUL = NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING;

    @ArchTest
    static final ArchRule LOGGER_STATIC_PRIVATE_FINAL =
            fields().that()
                    .haveRawType(Logger.class)
                    .should()
                    .beFinal()
                    .andShould()
                    .beStatic()
                    .andShould()
                    .bePrivate();

    @ArchTest
    static final ArchRule API_CONVENTIONS =
            classes()
                    .that()
                    .areAssignableTo(HttpServlet.class)
                    .should()
                    .haveNameMatching(".*Api")
                    .andShould()
                    .resideInAPackage("..api..")
                    .andShould()
                    .onlyBeAccessed()
                    .byAnyPackage("..api..", "..app..");
}

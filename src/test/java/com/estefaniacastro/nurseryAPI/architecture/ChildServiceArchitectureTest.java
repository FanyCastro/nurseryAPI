package com.estefaniacastro.nurseryAPI.architecture;

import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

/**
 * This class contains architectural tests for the ChildService layer,
 * ensuring adherence to hexagonal architecture principles.
 */
@AnalyzeClasses(packages = "com.nursery")
class ChildServiceArchitectureTest {


}

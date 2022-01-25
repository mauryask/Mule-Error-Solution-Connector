package com.apisero.driver;

import org.mule.runtime.extension.api.annotation.Configurations;
import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;

@Xml(prefix="error-solution")
@Extension(name="Error Solution")
@Configurations(ErrorSolutionConfiguration.class)
public class ErrorSolutionExtension{}


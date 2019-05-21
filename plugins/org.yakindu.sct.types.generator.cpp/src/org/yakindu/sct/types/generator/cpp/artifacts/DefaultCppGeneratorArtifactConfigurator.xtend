/**
 * Copyright (c) 2019 itemis AG - All rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * 
 * Contributors:
 * 	itemis AG
 * 
 */
package org.yakindu.sct.types.generator.cpp.artifacts

import com.google.inject.Inject
import java.util.Collection
import java.util.List
import org.eclipse.emf.ecore.util.EcoreUtil
import org.yakindu.base.expressions.expressions.BlockExpression
import org.yakindu.base.types.ComplexType
import org.yakindu.base.types.Constructor
import org.yakindu.base.types.Declaration
import org.yakindu.base.types.Operation
import org.yakindu.base.types.Package
import org.yakindu.base.types.Property
import org.yakindu.sct.generator.core.filesystem.ISCTFileSystemAccess
import org.yakindu.sct.types.generator.artifacts.GeneratorArtifact
import org.yakindu.sct.types.generator.artifacts.GeneratorArtifactConfiguration
import org.yakindu.sct.types.generator.artifacts.IGeneratorArtifactConfigurator
import org.yakindu.sct.types.generator.c.CExpressionsChecker
import org.yakindu.sct.types.generator.cpp.CppTargetPlatform
import org.yakindu.sct.types.generator.cpp.annotation.CoreCppGeneratorAnnotationLibrary
import org.yakindu.sct.types.generator.cpp.files.CppTypes

import static org.yakindu.sct.generator.core.filesystem.ISCTFileSystemAccess.*
import org.yakindu.base.types.TypeBuilder

class DefaultCppGeneratorArtifactConfigurator implements IGeneratorArtifactConfigurator {

	@Inject extension CppTypes
	@Inject protected extension CExpressionsChecker
	@Inject protected extension CoreCppGeneratorAnnotationLibrary
	@Inject protected extension TypeBuilder
	protected GeneratorArtifactConfiguration config
	
	override configure(Collection<Package> packages, ISCTFileSystemAccess fileSystemAccess) {
		config = new GeneratorArtifactConfiguration(fileSystemAccess)
		val scTypes = configureScTypes
		configureSources(packages, scTypes)
		config
	}
	
	def protected configureSources(Collection<Package> packages, GeneratorArtifact<?> scTypes) {
		for(p:packages) {
			val List<Declaration> sourceContents = newArrayList
			val List<Declaration> classContents = newArrayList
			
			val copyCT = p.eAllContents.filter(ComplexType).toList
			copyCT.forEach[ct | sourceContents.addAll(ct.features.filter(Property).filter[const].toList)]
			copyCT.forEach[ct | sourceContents.addAll(ct.features.filter(Operation).filter[body !== null].filter[!(it.isInnerConstructor) && !(it.isOcbDestructor)].toList)]
			classContents.addAll(EcoreUtil.copy(p).member)
			classContents.filter(ComplexType).forEach[ cT |
				cT.removeBodysFromOperations
			]
			
			val class = config.configure(p.member.get(0).name + CppTargetPlatform.HEADER_FILE_ENDING, null, classContents, false)
			val source = config.configure(p.member.get(0).name + CppTargetPlatform.SOURCE_FILE_ENDING, null, sourceContents, false)
			
			source.addDependencies(class)
			class.addDependencies(scTypes)
			addExpressionDependendingHeaders(source)
		}
	}
	
	def addExpressionDependendingHeaders(GeneratorArtifact<List<Declaration>> artifact) {
		val expressions = artifact.content.filter(Operation).map[body].filter(BlockExpression).map[expressions].flatten
		if(modOnAssignment(expressions)) {
			artifact.addDependencies("math.h")
		} else if (modOnNumericalMulitplyDivide(expressions)){
			artifact.addDependencies("math.h")
		}
	}
	
	def protected ComplexType removeBodysFromOperations(ComplexType cT){
		cT.features.filter(ComplexType).forEach[ innerCT | 
			innerCT.removeBodysFromOperations
		]
		cT.features.filter(Operation).forEach [ op | 
			if(!(op.isInnerConstructor || op.isDefaultConstructor || (op instanceof Constructor))) {
				if(op.body === null) {
					op._annotateWith(pureAnnotation)
				}
				op.body = null
			}
			if(op.isDefaultConstructor || op.isDefaultDestructor){
				op.static = true
			}
		]
		cT
	}
	
	def protected configureScTypes() {
		val filePath = "sc_types.h"
		config.configure(filePath, LIBRARY_TARGET_FOLDER_OUTPUT, createScTypes, false)
	}
	
}
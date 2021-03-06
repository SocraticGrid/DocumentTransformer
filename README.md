DocumentTransformer
======================================================================================
 
 Author: Jerry Goodnough (jgoodnough@cognitivemedicine.com)
 
 Commiters: Jerry Goodnough (jgoodnough@cognitivemedicine.com)
 
======================================================================================
 Notes:  
======================================================================================
 Implements SocraticGrid Document based transformations

 
======================================================================================
 Major Usage:
======================================================================================

Used to run document based transformation chains. Currently supports Serial Transformation
and Cumulative Transformations and Merge Transformations

======================================================================================
 Change History:
======================================================================================

Version 1.12 ---------------------------------------

  o   Direct access to the Saxon Parser for XLST 2.0


Version 1.11 ---------------------------------------

  o   Fix Transform error


Version 1.10 ---------------------------------------

  o   Now Supports MergeTransformations

  o   CumlativeTransform now passing IT


Version 1.9 ----------------------------------------

  o   Experimental CumlativeTransform now in places - Model being refined.

  o   Integration with Failsafe testing and Unit testing Mockito now supported.

  o   Fixed Multithreading issue and moved use templates in the XSLT transformers.

  o   Add the notion of Cumulative Transformation. 

  o   Abstracted Simple Transformation.

  o   Added the AbstractBrancher TransformationStep to allow a Transform step to invoke
      other transform steps based on a choice.

  o   Updated Package Organization.

  o   Added a Property based choice maker.

  o   Added a StaticChoice.

  o   Added XPath Based Property Fetches.

  o   Added XPath based Choices.

  o   Rename TransformPipe to SerialTransformPipeline.

  o   Moved all transformation steps to the subpackage transformtionsteps.

  o   Fixed issue with Transformation steps that do not change the result. The inputstream will now attempt to reset. 


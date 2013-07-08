package jannovar.annotation;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.HashMap;

/* serialization */
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import jannovar.exception.JannovarException;
import jannovar.io.SerializationManager;
import jannovar.io.UCSCKGParser;
import jannovar.common.Constants;
import jannovar.common.VariantType;
import jannovar.reference.TranscriptModel;
import jannovar.reference.Chromosome;
import jannovar.annotation.AnnotationList;
import jannovar.exome.Variant;
import jannovar.exception.AnnotationException;


import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Assert;


/**
 * This class is intended to perform unuit testing on variants that
 * are intergenic. 
 */
public class BlockSubAnnotationTest implements Constants {

    
   
    private static HashMap<Byte,Chromosome> chromosomeMap = null;

  
    /**
     * Set up test by importing Chromosome objects from serialized file.
     */
    @SuppressWarnings (value="unchecked")
    @BeforeClass 
	public static void setUp() throws IOException, JannovarException {
	ArrayList<TranscriptModel> kgList=null;
	java.net.URL url = SynonymousAnnotationTest.class.getResource("/ucsc.ser");
	String path = url.getPath();
	SerializationManager manager = new SerializationManager();
	kgList = manager.deserializeKnownGeneList(path);
	chromosomeMap = Chromosome.constructChromosomeMapWithIntervalTree(kgList);
    }

    @AfterClass public static void releaseResources() { 
	chromosomeMap = null;
	System.gc();
    }

/**
 *<P>
 * annovar: MST1P9:uc010ock.2:exon2:c.117_121del:p.39_41del,
 * chr1:17087544GCTGT>-
 *</P>
 */
@Test public void testBlocSubByHand1() throws AnnotationException  {
    byte chr = 16;
    int pos = 21848622;
    String ref = "CGCTGAGGGTGGAGCTGAGGGTAGAGCTGAGGGTGGA";
    String alt = "CGCTGAGGGTAGAGCTGAGGGTGGA";
    Chromosome c = chromosomeMap.get(chr); 
    if (c==null) {
	Assert.fail("Could not identify chromosome \"" + chr + "\"");
    } else {
	AnnotationList ann =c.getAnnotationList(pos,ref,alt); 
	VariantType varType = ann.getVariantType();
	Assert.assertEquals(VariantType.NON_FS_SUBSTITUTION,varType);
	String annot = ann.getVariantAnnotation();
	Assert.assertEquals("LOC100132247(uc002djq.3:exon7:c.993_1029TCCACCCTCAGCTCTACCCTCAGCG,uc010vbn.1:exon8:c.1050_1086TCCACCCTCAGCTCTACCCTCAGCG,uc002djr.3:exon9:c.1050_1086TCCACCCTCAGCTCTACCCTCAGCG)",annot);
	}
}






}
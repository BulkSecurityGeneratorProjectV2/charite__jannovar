package de.charite.compbio.jannovar.vardbs.gnomad;

import com.google.common.io.Files;
import de.charite.compbio.jannovar.utils.ResourceUtils;
import de.charite.compbio.jannovar.vardbs.base.DBAnnotationOptions;
import htsjdk.variant.vcf.VCFFileReader;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.io.PrintWriter;

public class GnomadGenomesAnnotationDriverBaseTest {

	protected String gnomadVCFPath;
	protected String fastaPath;
	protected VCFFileReader vcfReader;
	protected DBAnnotationOptions options;

	@BeforeEach
	public void setUp() throws Exception {
		options = DBAnnotationOptions.createDefaults();

		File tmpDir = Files.createTempDir();

		// Setup exomes VCF file
		gnomadVCFPath = tmpDir + "/gnomad.genomes.vcf.gz";
		ResourceUtils.copyResourceToFile("/gnomad.genomes.r2.0.1.sites.head.vcf.gz", new File(gnomadVCFPath));
		String genomesTbiPath = tmpDir + "/gnomad.genomes.vcf.gz.tbi";
		ResourceUtils.copyResourceToFile("/gnomad.genomes.r2.0.1.sites.head.vcf.gz.tbi", new File(genomesTbiPath));

		// Setup reference FASTA file
		fastaPath = tmpDir + "/chr1.fasta";
		ResourceUtils.copyResourceToFile("/chr1.fasta", new File(fastaPath));
		String faiPath = tmpDir + "/chr1.fasta.fai";
		ResourceUtils.copyResourceToFile("/chr1.fasta.fai", new File(faiPath));

		// Header of VCF file
		String vcfHeader = "##fileformat=VCFv4.0\n"
			+ "#CHROM\tPOS\tID\tREF\tALT\tQUAL\tFILTER\tINFO\tFORMAT\tindividual\n";

		// Write out file to use in the test
		String testVCFPath = tmpDir + "/test_var_in_exac.vcf";
		PrintWriter writer = new PrintWriter(testVCFPath);
		writer.write(vcfHeader);
		writer.write("1\t10611\t.\tC\tA,G,T\t.\t.\t.\tGT\t0/1\n");
		writer.close();

		vcfReader = new VCFFileReader(new File(testVCFPath), false);
	}

}

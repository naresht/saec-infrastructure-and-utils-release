package com.bfds.saec.util;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.bfds.saec.util.FileUtils;
import org.junit.Test;

/*
 * 
 * dbCashedCheckPrefix = "SAEC_DBPDCK_ddMMyyyyHHmmS" SAEC_DBPDCK_*
 * 
 * dbStopAckPrefix = "SAEC_DBStopAck_ddMMyyyyHHmmS" SAEC_DBStopAck_*
 * 
 * dbStopConfirmPrefix = "SAEC_DBstopconfirm_ddMMyyyyHHmmS";
 * 
 * ssCashedCheckPrefix = "SSC_PAIDCHECK.txt";
 * 
 * ibIndividualAddressResearchPrefix = "InfoAge_DDA_yyyyMMddHmm" InfoAge_<DDA>_*.xml
 * 
 * ibCorporateAddressResearchPrefix = "InfoAge_DDA_yyyyMMddHmm"
 */

public class SaecBatchFileFilterTest {

	@org.junit.Before
	public void init() throws Exception {

		createDirectories();
		createInBoundFiles();

	}

	@Test
	public void testFilesCountForDBCashedCheck() {

		List<File> files = FileUtils.getFilesWithFilePattern(
                new File("target/NDMSHARE/SAEC/Deutsche/Inbound"),
                "SAEC_DBPDCK_[0-9]*.txt");

		assertThat(files.size()).isEqualTo(3);

	}

	@Test
	public void testFilesNameDBCashedCheck() {

		List<File> files = FileUtils.getFilesWithFilePattern(
				new File("target/NDMSHARE/SAEC/Deutsche/Inbound"),
				"SAEC_DBPDCK_[0-9]*.txt");

		for (File file : files) {
			assertThat(file.getName()).startsWith("SAEC_DBPDCK_");
		}
		// assertThat(files).onProperty("name").containsSequence("SAEC_DBPDCK_");

	}

	@Test
	public void testFilesCountDBStopAck() {

		List<File> files = FileUtils.getFilesWithFilePattern(
				new File("target/NDMSHARE/SAEC/Deutsche/Inbound"),
				"SAEC_DBStopAck_[0-9]*.txt");

		assertThat(files.size()).isEqualTo(3);

	}

	@Test
	public void testFilesNameDBStopAck() {

		List<File> files = FileUtils.getFilesWithFilePattern(
				new File("target/NDMSHARE/SAEC/Deutsche/Inbound"),
				"SAEC_DBStopAck_");

		for (File file : files) {
			assertThat(file.getName()).startsWith("SAEC_DBStopAck_");
		}
		// assertThat(files).onProperty("name").startsWith("SAEC_DBStopAck_");

	}

	@Test
	public void testFilesCountForDBStopConfirm() {

		List<File> files = FileUtils.getFilesWithFilePattern(
				new File("target/NDMSHARE/SAEC/Deutsche/Inbound"),
				"SAEC_DBstopconfirm_[0-9]*.txt");

		assertThat(files.size()).isEqualTo(3);

	}

	@Test
	public void testFilesNameDBStopConfirm() {

		List<File> files = FileUtils.getFilesWithFilePattern(
				new File("target/NDMSHARE/SAEC/Deutsche/Inbound"),
				"SAEC_DBstopconfirm_");
		for (File file : files) {
			assertThat(file.getName()).startsWith("SAEC_DBstopconfirm_");
		}
		// assertThat(files).onProperty("name").startsWith("SAEC_DBstopconfirm_");

	}

	@Test
	public void testFilesCountSSCashedCheck() {

		List<File> files = FileUtils.getFilesWithFilePattern(
				new File("target/NDMSHARE/SAEC/SSB/Inbound"),
				"SSC_PAIDCHECK[0-9]*.txt");

		assertThat(files.size()).isEqualTo(3);

	}

	@Test
	public void testFilesNameSSCashedCheck() {

		List<File> files = FileUtils.getFilesWithFilePattern(
				new File("target/NDMSHARE/SAEC/SSB/Inbound"), "SSC_PAIDCHECK");
		for (File file : files) {
			assertThat(file.getName()).startsWith("SSC_PAIDCHECK");
		}
		// assertThat(files).onProperty("name").startsWith("SSC_PAIDCHECK");

	}

	@Test
	public void testFilesCountIBIndividualAddressResearch() {

		List<File> files = FileUtils.getFilesWithFilePatternDDA(
				new File("target/NDMSHARE/SAEC/Infoage/Inbound"), "InfoAge_"
						+ getDDA() + "_[0-9]*.xml", "");

		assertThat(files.size()).isEqualTo(3);
	}

	@Test
	public void testFilesNameIBIndividualAddressResearch() {

		List<File> files = FileUtils.getFilesWithFilePatternDDA(
				new File("target/NDMSHARE/SAEC/Infoage/Inbound"), "InfoAge_"
						+ getDDA() + "_[0-9]", getDDA());
		for (File file : files) {
			assertThat(file.getName()).startsWith("InfoAge_" + getDDA() + "");
		}
		// assertThat(files).onProperty("name").startsWith("InfoAge_" + getDDA()
		// +"", "");

	}

	@Test
	public void testFilesCountIBCorporateAddressResearch() {

		List<File> files = FileUtils.getFilesWithFilePatternDDA(
				new File("target/NDMSHARE/SAEC/Infoage/Inbound"), "InfoAge_"
						+ getDDA() + "_[0-9]*.xml", getDDA());

		assertThat(files.size()).isEqualTo(3);
	}

	@Test
	public void testFilesIBCorporateAddressResearch() {

		List<File> files = FileUtils.getFilesWithFilePatternDDA(
				new File("target/NDMSHARE/SAEC/Infoage/Inbound"), "InfoAge_"
						+ getDDA() + "_", getDDA());
		for (File file : files) {
			assertThat(file.getName()).startsWith("InfoAge_" + getDDA() + "");
		}
		// assertThat(files).onProperty("name").startsWith("InfoAge_" + getDDA()
		// +"");

	}

	private void createInBoundFiles() throws IOException {

		createDBCashedCheckFiles();

		createDBStopAckFiles();

		createDBstopconfirmFiles();

		createSSCashedCheckFiles();

		createInfoAgeIBFiles();

	}

	private void createInfoAgeIBFiles() throws IOException {
		(new File(
				"target/NDMSHARE/SAEC/Infoage/Inbound/InfoAge_0123456789_11111.xml"))
				.createNewFile();
		(new File(
				"target/NDMSHARE/SAEC/Infoage/Inbound/InfoAge_0123456789_12222.xml"))
				.createNewFile();
		(new File(
				"target/NDMSHARE/SAEC/Infoage/Inbound/InfoAge_0123456789_3333.xml"))
				.createNewFile();
		(new File("target/NDMSHARE/SAEC/Infoage/Inbound/NMInfoAge_1234.xml"))
				.createNewFile();
		(new File("target/NDMSHARE/SAEC/Infoage/Inbound/NMInfoAge_12345.xml"))
				.createNewFile();
	}

	private void createSSCashedCheckFiles() throws IOException {
		(new File("target/NDMSHARE/SAEC/SSB/Inbound/SSC_PAIDCHECK.txt"))
				.createNewFile();
		(new File("target/NDMSHARE/SAEC/SSB/Inbound/SSC_PAIDCHECK123.txt"))
				.createNewFile();
		(new File("target/NDMSHARE/SAEC/SSB/Inbound/SSC_PAIDCHECK1234.txt"))
				.createNewFile();
		(new File("target/NDMSHARE/SAEC/SSB/Inbound/NMSSC_PAIDCHECK1234.txt"))
				.createNewFile();
		(new File("target/NDMSHARE/SAEC/SSB/Inbound/NMSSC_PAIDCHECK12345.txt"))
				.createNewFile();
	}

	private void createDBstopconfirmFiles() throws IOException {
		(new File(
				"target/NDMSHARE/SAEC/Deutsche/Inbound/SAEC_DBstopconfirm_.txt"))
				.createNewFile();
		(new File(
				"target/NDMSHARE/SAEC/Deutsche/Inbound/SAEC_DBstopconfirm_123.txt"))
				.createNewFile();
		(new File(
				"target/NDMSHARE/SAEC/Deutsche/Inbound/SAEC_DBstopconfirm_1234.txt"))
				.createNewFile();
		(new File(
				"target/NDMSHARE/SAEC/Deutsche/Inbound/NMSAEC_DBstopconfirm_1234.txt"))
				.createNewFile();
		(new File(
				"target/NDMSHARE/SAEC/Deutsche/Inbound/NMSAEC_DBstopconfirm_12345.txt"))
				.createNewFile();
	}

	private void createDBStopAckFiles() throws IOException {
		(new File("target/NDMSHARE/SAEC/Deutsche/Inbound/SAEC_DBStopAck_.txt"))
				.createNewFile();
		(new File(
				"target/NDMSHARE/SAEC/Deutsche/Inbound/SAEC_DBStopAck_123.txt"))
				.createNewFile();
		(new File(
				"target/NDMSHARE/SAEC/Deutsche/Inbound/SAEC_DBStopAck_1234.txt"))
				.createNewFile();
		(new File(
				"target/NDMSHARE/SAEC/Deutsche/Inbound/NMSAEC_DBStopAck_1234.txt"))
				.createNewFile();
		(new File(
				"target/NDMSHARE/SAEC/Deutsche/Inbound/NMSAEC_DBStopAck_12345.txt"))
				.createNewFile();
	}

	private void createDBCashedCheckFiles() throws IOException {
		(new File("target/NDMSHARE/SAEC/Deutsche/Inbound/SAEC_DBPDCK_.txt"))
				.createNewFile();
		(new File("target/NDMSHARE/SAEC/Deutsche/Inbound/SAEC_DBPDCK_123.TXT"))
				.createNewFile();
		(new File("target/NDMSHARE/SAEC/Deutsche/Inbound/SAEC_DBPDCK_1234.TXT"))
				.createNewFile();
		(new File(
				"target/NDMSHARE/SAEC/Deutsche/Inbound/NMSAEC_DBPDCK_1234.txt"))
				.createNewFile();
		(new File(
				"target/NDMSHARE/SAEC/Deutsche/Inbound/NMSAEC_DBPDCK_12345.txt"))
				.createNewFile();
	}

	private void createDirectories() {
		(new File("target/NDMSHARE/SAEC/Deutsche/Inbound")).mkdirs();
		(new File("target/NDMSHARE/SAEC/DSTO/Inbound")).mkdirs();
		(new File("target/NDMSHARE/SAEC/Infoage/Inbound")).mkdirs();
		(new File("target/NDMSHARE/SAEC/SSB/Inbound")).mkdirs();
	}

	// Method to get the default DDA number
	public static String getDDA() {
		return "0123456789";
	}

}

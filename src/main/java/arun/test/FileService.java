package arun.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import eu.medsea.mimeutil.MimeUtil;

public class FileService {

	//Configure Mime Detector upon the bean is instantiated
	public void init() {

		MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
		MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.ExtensionMimeDetector");
		MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.OpendesktopMimeDetector");
	}

	//Scan configured directory to return --> filename, file mime type, file size, file extension
	public List<String[]> getFileInfo() throws IOException {

		List<String[]> fileInfo = new ArrayList<>();

		try (Stream<Path> filePaths = Files
				.walk(Paths.get(System.getProperty("user.dir") + File.separator + "TestDirectory"))) {

			filePaths.filter(Files::isRegularFile).forEach(file -> {
										
				fileInfo.add( 
						
						new String[] {
								file.getFileName().toString(),
								FilenameUtils.getExtension(file.getFileName().toString()),
								FileUtils.byteCountToDisplaySize(file.toFile().length()).toString(),
								MimeUtil.getMimeTypes(file.toFile()).toString()		
							});			

			});
		}
		return fileInfo;
	}

	//Retrieve files by mime type
	public List<String[]> filterFilesByMimeType(String mimeType) throws IOException {

		List<String[]> filteredFileInfo = new ArrayList<>();
		
		try (Stream<Path> filePaths = Files
				.walk(Paths.get(System.getProperty("user.dir") + File.separator + "TestDirectory"))) {

			filePaths.filter(Files::isRegularFile)

					.filter(file -> MimeUtil.getMimeTypes(file.toFile()).equals(mimeType))

					.forEach(file -> {

						System.out.println("------ Files Filtered by Mime Type --------");

						filteredFileInfo.add( 
								
								new String[] {									
										file.getFileName().toString(),
										FilenameUtils.getExtension(file.getFileName().toString()),
										FileUtils.byteCountToDisplaySize(file.toFile().length()).toString(),
										MimeUtil.getMimeTypes(file.toFile()).toString(),
										file.toString()									
									});	
													
					});
		}
		return filteredFileInfo;
	}
}

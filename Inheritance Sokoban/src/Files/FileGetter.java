package Files;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class FileGetter {

	// Gets a list of files in a given directory
	// used this website
	// https://www.baeldung.com/java-list-directory-files
	public Set<String> listFilesUsingDirectoryStream(String dir) throws IOException {
		Set<String> fileList = new HashSet<>();
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dir))) {
			for (Path path : stream) {
				if (!Files.isDirectory(path)) {
					fileList.add(path.getFileName().toString());
				}
			}
		}
		return fileList;
	}
}
